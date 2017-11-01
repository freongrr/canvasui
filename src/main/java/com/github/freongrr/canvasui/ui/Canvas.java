package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.FocusListener;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.events.FocusEvent;
import com.github.freongrr.canvasui.events.LayoutListener;
import com.github.freongrr.canvasui.events.RedrawEvent;
import com.github.freongrr.canvasui.events.RedrawListener;

/**
 * This widget is the top-level element of the user interface. The implementing class should take
 * care of the event handling and rendering.
 * 
 * @author fabien
 */
public abstract class Canvas extends Container implements RedrawListener, LayoutListener,
        FocusListener {

    private DragManager dragManager;
    private Window activeWindow = null;

    // Clickable methods

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAttached() {
        return true;
    }

    @Override
    public Canvas getTopLevel() {
        return this;
    }

    // Composite / Container methods

    @Override
    public void add(Widget widget) {
        super.add(widget);

        // The last window added is the active window
        if (widget instanceof Window) {
            setActiveWindow((Window) widget);
        }
    }

    // Events

    @Override
    public void onFocus(FocusEvent de) {
        // TODO : in fact, the canvas is not listening for redraw even on itself...
        Focusable f = de.getFocusedWidget();
        f.fireRedrawEvent(new RedrawEvent(f));
    }

    // Canvas methods

    /**
     * Helpful method to add and show a window.
     * 
     * @param window
     */
    public void addAndShow(Window window) {
        suspendEvents();
        try {
            add(window);
            window.show();
        } finally {
            resumeEvents();
        }
    }

    /**
     * Returns the active window
     */
    public Window getActiveWindow() {
        return activeWindow;
    }

    /**
     * Sets the window.
     * 
     * This method is in Canvas because it only makes sense to have active and inactive window in
     * the context of a canvas.
     * 
     * @param window
     */
    public void setActiveWindow(Window window) {
        this.activeWindow = window;

        for (Widget w : getVisibleWidgets()) {
            if (w instanceof Window && w != activeWindow) {
                ((Window) w).setActive(false);
            }
        }

        activeWindow.setActive(true);
        activeWindow.setZIndex(getMaxZIndex() + 1);
    }

    /**
     * Returns the drag event handler.
     */
    public DragManager getDragManager() {
        if (dragManager == null) {
            dragManager = new DragManager();
            dragManager.bind(this);
        }
        return dragManager;
    }

    /**
     * Returns the graphic context.
     * 
     * TODO : should we keep that?
     */
    public abstract GraphicContext getContext();

    /**
     * Adds the canvas to an external rendering API.
     */
    public abstract void go(Object root);

    /**
     * Draws everything.
     * 
     * @deprecated this method should not be called directely. The implementing class should take
     *             care of that when the top level element is spawn.
     */
    public abstract void draw();
}
