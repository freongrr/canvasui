package com.github.freongrr.canvasui.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.github.freongrr.canvasui.events.AttachEvent;
import com.github.freongrr.canvasui.events.AttachListener;
import com.github.freongrr.canvasui.events.DetachEvent;
import com.github.freongrr.canvasui.events.DetachListener;
import com.github.freongrr.canvasui.events.KeyDownEvent;
import com.github.freongrr.canvasui.events.KeyDownListener;
import com.github.freongrr.canvasui.events.KeyPressEvent;
import com.github.freongrr.canvasui.events.KeyPressListener;
import com.github.freongrr.canvasui.events.KeyUpEvent;
import com.github.freongrr.canvasui.events.KeyUpListener;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseDownListener;
import com.github.freongrr.canvasui.events.MouseEvent;
import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseMoveListener;
import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOutListener;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseOverListener;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.events.MouseUpListener;
import com.github.freongrr.canvasui.events.MouseWheelEvent;
import com.github.freongrr.canvasui.events.MouseWheelListener;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * A container contains widgets.
 * 
 * @author fabien
 */
public abstract class Container extends FocusableWidget implements Composite, MouseDownListener,
        MouseUpListener, MouseMoveListener, MouseOverListener, MouseOutListener,
        MouseWheelListener, KeyDownListener, KeyUpListener, KeyPressListener, AttachListener,
        DetachListener {

    final private List<Widget> widgets;

    public Container() {
        widgets = new LinkedList<Widget>();

        addMouseDownListener(this);
        addMouseUpListener(this);
        addMouseMoveListener(this);
        addMouseOverListener(this);
        addMouseOutListener(this);
        addMouseWheelListener(this);
        addKeyDownListener(this);
        addKeyUpListener(this);
        addKeyPressListener(this);
    }

    // Methods of Composite

    @Override
    public List<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public void add(Widget widget) {
        suspendEvents();
        try {
            // add the widget "above" the others
            int zIndex = getMaxZIndex();
            widgets.add(widget);
            widget.setZIndex(zIndex + 1);
            widget.setParent(this);
        } finally {
            resumeEvents();
        }
    }

    @Override
    public void remove(Widget widget) {
        suspendEvents();
        try {
            widget.setParent(null);
            widgets.remove(widget);
        } finally {
            resumeEvents();
        }
    }

    @Override
    public void removeAll() {
        for (Widget w : new LinkedList<Widget>(widgets)) {
            remove(w);
        }
    }

    // Methods of Widget/BaseWidget

    @Override
    public void onAttach(AttachEvent ae) {
        super.onAttach(ae);
        for (Widget w : widgets) {
            w.fireAttachEvent(ae);
        }
    }

    @Override
    public void onDetach(DetachEvent de) {
        removeMouseDownListener(this);
        removeMouseUpListener(this);
        removeMouseMoveListener(this);
        removeMouseOverListener(this);
        removeMouseOutListener(this);
        removeMouseWheelListener(this);
        removeKeyDownListener(this);
        removeKeyUpListener(this);
        removeKeyPressListener(this);

        super.onDetach(de);
        
        for (Widget w : widgets) {
            w.fireDetachEvent(de);
        }
    }

    // Container methods

    /**
     * Returns the highest zindex in the child widgets.
     */
    protected int getMaxZIndex() {
        int zIndex = 0;
        for (Widget w : widgets) {
            if (w.getZIndex() > zIndex) {
                zIndex = w.getZIndex();
            }
        }

        return zIndex;
    }

    /**
     * This comparator returns widget sorted by reverse zIndex: from the front to the back.
     */
    private static final Comparator<Widget> zIndexComparator = new Comparator<Widget>() {
        @Override
        public int compare(Widget w1, Widget w2) {
            return new Integer(w2.getZIndex()).compareTo(w1.getZIndex());
        }
    };

    /**
     * Returns the visible widgets with the most visible (in the front) widgets first.
     * 
     * @param area
     *            only returns the widgets interecting with this area
     */
    protected List<Widget> getVisibleWidgets(Rectangle area) {
        List<Widget> visible = new ArrayList<Widget>(widgets.size());
        for (Widget w : widgets) {
            if (w.isVisible() && (area == null || w.getDrawArea().intersect(area))) {
                visible.add(w);
            }
        }

        // Sort the result list
        Collections.sort(visible, zIndexComparator);

        return visible;
    }

    /**
     * Returns the visible widgets with the most visible (in the front) widgets first.
     */
    protected List<Widget> getVisibleWidgets() {
        return getVisibleWidgets(null);
    }

    /**
     * Returns the clickable widget under the mouse.
     * 
     * @param event
     *            a mouse event
     * @return a {@link Clickable} widget
     */
    public Clickable getClickableWidget(MouseEvent me) {
        Vector position = new Vector(me.getX(), me.getY());

        for (Widget w : getVisibleWidgets()) {
            if (w instanceof Clickable) {
                Clickable eb = (Clickable) w;
                if (eb.isEnabled() && eb.getEventArea().contains(position)) {
                    return eb;
                }
            }
        }

        return null;
    }

    /**
     * Returns the deepest clickable widget under the mouse.
     * 
     * @param event
     *            a mouse event
     * @return a {@link Clickable} widget
     */
    public Clickable getDeepestClickableWidget(MouseEvent me) {
        Clickable widget = getClickableWidget(me);

        while (widget != null) {
            // keeps looking for elements until we hit the bottom
            if (widget instanceof Container) {
                Clickable widget2 = ((Container) widget).getClickableWidget(me);
                // stop looking for a deeper widget
                if (widget2 == null || widget2 == widget) {
                    break;
                }
                widget = widget2;
            } else {
                break;
            }
        }

        return widget;
    }

    /**
     * Helper method to get the focused widget.
     */
    protected Focusable getFocusedWidget() {
        Canvas c = getTopLevel();
        if (c != null) {
            Window w = c.getActiveWindow();
            if (w != null) {
                return w.getFocusedWidget();
            }
        }
        return null;
    }

    // Events

    @Override
    public void onMouseDown(MouseDownEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseDownEvent(event);
        }
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseUpEvent(event);
        }
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseOutEvent(event);
        }
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseOverEvent(event);
        }
    }

    @Override
    public void onMouseWheel(MouseWheelEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseWheelEvent(event);
        }
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        Clickable eb = getClickableWidget(event);
        if (eb != null) {
            eb.fireMouseMoveEvent(event);
        }
    }

    @Override
    public void onKeyDown(KeyDownEvent ke) {
        Focusable f = getFocusedWidget();
        if (f != null) {
            f.fireKeyDownEvent(ke);
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent ke) {
        Focusable f = getFocusedWidget();
        if (f != null) {
            f.fireKeyUpEvent(ke);
        }
    }

    @Override
    public void onKeyPress(KeyPressEvent ke) {
        Focusable f = getFocusedWidget();
        if (f != null) {
            f.fireKeyPressEvent(ke);
        }
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        // The Container class is transparent so we only need to redraw the children widgets. Of
        // course, another implementation may want to draw a background or border (like Panel,
        // Windows, etc).
        drawContent(context, area);
    }

    /**
     * This method draws the content of the widget but not the container itself.
     * 
     * @param context
     * @param area
     */
    protected void drawContent(GraphicContext context, Rectangle area) {
        try {
            context.save();
            Window win = getParentWindow();
            if (win != null) {
                // Sets the clipping region
                context.setClippingRectangle(getParentWindow().getAbsoluteRectangle());
            }

            // Loops on the widgets in the reverse order to draw the deepest one first
            List<Widget> widgets = getVisibleWidgets(area);
            Collections.reverse(widgets);
            for (Widget w : widgets) {
                w.draw(context, area);
            }
        } finally {
            context.restore();
        }
    }
}
