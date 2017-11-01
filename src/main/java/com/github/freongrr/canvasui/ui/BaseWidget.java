package com.github.freongrr.canvasui.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.freongrr.canvasui.events.AttachEvent;
import com.github.freongrr.canvasui.events.DetachEvent;
import com.github.freongrr.canvasui.events.RedrawEvent;
import com.github.freongrr.canvasui.laf.LookAndFeel;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.events.AttachListener;
import com.github.freongrr.canvasui.events.DetachListener;
import com.github.freongrr.canvasui.events.RedrawListener;

/**
 * Typical implementation of {@link Widget}.
 * 
 * @author fabien
 */
public abstract class BaseWidget implements Widget, AttachListener, DetachListener {
    private LookAndFeel lookAndFeel = null;
    private Composite parent = null;

    protected Vector offset;
    protected Vector size;
    private int zIndex = -1;

    private boolean visible = true;
    private boolean attached = false;

    final private List<AttachListener> attachListeners = new ArrayList<AttachListener>();
    final private List<DetachListener> detachListeners = new ArrayList<DetachListener>();
    final private List<RedrawListener> redrawListeners = new ArrayList<RedrawListener>();

    public BaseWidget() {
        offset = new Vector();
        size = new Vector();

        addAttachListener(this);
        addDetachListener(this);
    }

    @Override
    public Composite getParent() {
        return parent;
    }

    @Override
    public void setParent(Composite parent) {
        Composite oldParent = this.parent;

        // if a parent is attached or an existing parent cut, we fire
        // attach/detach events
        if (parent == null) {
            if (oldParent != null && oldParent.isAttached()) {
                fireDetachEvent(new DetachEvent());
            }
        }

        this.parent = parent;

        if (parent != null) {
            if (parent.isAttached()) {
                fireAttachEvent(new AttachEvent());
            }
        }
    }

    @Override
    public void onAttach(AttachEvent ae) {
        setAttached(true);
        addRedrawListener(getTopLevel());
    }

    @Override
    public void onDetach(DetachEvent de) {
        setAttached(false);
        removeRedrawListener(getTopLevel());
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        boolean oldVisible = this.visible;
        this.visible = visible;
        if (oldVisible != visible) {
            fireRedrawEvent();
        }
    }

    @Override
    public boolean isAttached() {
        return attached;
    }

    @Override
    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    @Override
    public Vector getOffset() {
        return offset;
    }

    @Override
    public void setOffset(Vector offset) {
        Vector oldOffset = this.offset;
        this.offset = offset;
        if (!oldOffset.equals(offset)) {
            fireRedrawEvent();
        }
    }

    @Override
    public Vector getSize() {
        return size;
    }

    @Override
    public void setSize(Vector size) {
        Vector checkedSize = Vector.getMaximum(size, getMinimumSize());
        if (!checkedSize.equals(this.size)) {
            this.size = checkedSize;
            fireRedrawEvent();
        }
    }

    @Override
    public Vector getMinimumSize() {
        return new Vector(0, 0);
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void setZIndex(int zIndex) {
        int oldIndex = this.zIndex;
        this.zIndex = zIndex;
        if (oldIndex != zIndex) {
            fireRedrawEvent();
        }
    }

    /**
     * Returns the absolute position of a widget.
     */
    @Override
    public Vector getAbsolutePosition() {
        double x = 0;
        double y = 0;
        Vector offset;

        Widget parent = this;
        while (parent != null) {
            offset = parent.getOffset();
            x += offset.x;
            y += offset.y;

            parent = parent.getParent();
        }

        return new Vector(x, y);
    }

    @Override
    public LookAndFeel getLookAndFeel() {
        LookAndFeel laf = this.lookAndFeel;

        // if the look and feel is not defined
        if (laf == null) {
            // if there is a parent: returns the look and feel of the parent
            if (parent != null) {
                laf = parent.getLookAndFeel();
            }
            // otherwise (or when we reach the top) returns the default look and
            // feel
            else {
                laf = LookAndFeel.get();
            }
        }

        return laf;
    }

    @Override
    public void setLookAndFeel(LookAndFeel lookAndFeel) {
        LookAndFeel oldLookAndFeel = this.lookAndFeel;
        this.lookAndFeel = lookAndFeel;
        if (oldLookAndFeel != lookAndFeel) {
            fireRedrawEvent();
        }
    }

    /**
     * Returns the top level element.
     */
    public Canvas getTopLevel() {
        Widget parent = this;
        while (parent != null) {
            if (parent instanceof Canvas) {
                return (Canvas) parent;
            }

            parent = parent.getParent();
        }

        return null;
    }

    /**
     * Returns the window the widget is in
     */
    public Window getParentWindow() {
        Widget parent = this;
        while (parent != null) {
            if (parent instanceof Window) {
                return (Window) parent;
            }

            parent = parent.getParent();
        }

        return null;
    }

    // Events stuff

    @Override
    public void addAttachListener(AttachListener listener) {
        attachListeners.add(listener);
    }

    @Override
    public void removeAttachListener(AttachListener listener) {
        attachListeners.remove(listener);
    }

    @Override
    public void fireAttachEvent(AttachEvent ae) {
        for (AttachListener l : attachListeners) {
            if (!ae.isCancelled()) {
                l.onAttach(ae);
            }
        }
    }

    @Override
    public void addDetachListener(DetachListener listener) {
        detachListeners.add(listener);
    }

    @Override
    public void removeDetachListener(DetachListener listener) {
        detachListeners.remove(listener);
    }

    @Override
    public void fireDetachEvent(DetachEvent de) {
        for (DetachListener l : detachListeners) {
            if (!de.isCancelled()) {
                l.onDetach(de);
            }
        }
    }

    @Override
    public void addRedrawListener(RedrawListener listener) {
        redrawListeners.add(listener);
    }

    @Override
    public void removeRedrawListener(RedrawListener listener) {
        redrawListeners.remove(listener);
    }

    @Override
    public void fireRedrawEvent(RedrawEvent re) {
        for (RedrawListener l : redrawListeners) {
            if (!re.isCancelled()) {
                l.onRedraw(re);
            }
        }
    }

    /**
     * Useful method: we usually want to fire a redraw event for the current widget.
     */
    public void fireRedrawEvent() {
        // TODO : should we do that here?
        if (isAttached()) {
            fireRedrawEvent(new RedrawEvent(this));
        }
    }

    /**
     * This method can be used to implement {@link Widget#getDrawArea()} or
     * {@link Clickable#getEventArea()}.
     * 
     * @return a rectangle starting from the absolute position and of the size of the widget.
     */
    public Rectangle getAbsoluteRectangle() {
        return new Rectangle(getAbsolutePosition(), size);
    }

    /**
     * Returns the top offset of the widget.
     */
    public double getTop() {
        return offset.y;
    }

    /**
     * Returns the left offset of the widget.
     */
    public double getLeft() {
        return offset.x;
    }

    /**
     * Returns the width of the widget.
     */
    public double getWidth() {
        return size.x;
    }

    /**
     * Returns the height of the widget.
     */
    public double getHeight() {
        return size.y;
    }

    /**
     * Returns the right of the widget (left + width).
     */
    public double getRight() {
        return offset.x + size.x;
    }

    /**
     * Returns the bottom of the widget (top + height).
     */
    public double getBottom() {
        return offset.y + size.y;
    }

    /**
     * Disable redraw events.
     * 
     * @deprecated quick solution before we do it properly
     */
    public void suspendEvents() {
        Canvas c = getTopLevel();
        if (c != null) {
            c.suspendEvents();
        }
    }

    /**
     * Resume events.
     * 
     * @deprecated quick solution before we do it properly
     */
    public void resumeEvents() {
        Canvas c = getTopLevel();
        if (c != null) {
            c.resumeEvents();
        }
    }
}
