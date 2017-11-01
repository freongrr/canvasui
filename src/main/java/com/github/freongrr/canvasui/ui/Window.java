package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.AttachEvent;
import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.layouts.FixedLayout;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.events.DetachEvent;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.layouts.FitLayout;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Shape;

/**
 * This class defines a simple window. The window is also a {@link Panel} with a {@link FixedLayout}
 * by default.
 * 
 * @author fabien
 */
public class Window extends Panel {
    private String title = "Window";
    private Vector minimumSize;
    private boolean active = true;
    private Focusable focusedWidget;
    // TODO
    // private WindowState state = WindowState.NORMAL;

    final private WindowDragListener dragListener;

    public Window() {
        offset = new Vector(0, 0);
        size = new Vector(100, 100);
        minimumSize = new Vector(100, 100);
        dragListener = new WindowDragListener(this);
        setVisible(false);
        setLayout(new FitLayout());
    }

    // Widget methods

    @Override
    public void onAttach(AttachEvent ae) {
        super.onAttach(ae);
        Canvas canvas = getTopLevel();
        canvas.getDragManager().addDragListener(dragListener, Window.this);
    }

    @Override
    public void onDetach(DetachEvent de) {
        Canvas canvas = getTopLevel();
        super.onDetach(de);
        canvas.getDragManager().removeDragListener(dragListener);
    }

    @Override
    public Rectangle getDrawArea() {
        return getLookAndFeel().getWindowDrawArea(this);
    }

    /**
     * Returns the minimum size of the window. It can be overriden using
     * {@link Window#setMinimumSize(Vector)}.
     * 
     * @param size
     */
    @Override
    public Vector getMinimumSize() {
        Vector contentMinimum = super.getMinimumSize();
        return Vector.getMaximum(contentMinimum, minimumSize);
    }

    // Clickable methods

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Shape getEventArea() {
        return getLookAndFeel().getWindowShape(this);
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        // TODO : only draw the needed area
        getLookAndFeel().drawWindow(context, this);
        super.draw(context, area);
    }

    // Window methods

    /**
     * Sets the minimum size of the window.
     * 
     * @param size
     */
    public void setMinimumSize(Vector size) {
        this.minimumSize = size;
    }

    /**
     * Returns the title of the window.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the window.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns whether the windows is active (selected) or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether the windows is active (selected) or not.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the focused widget.
     */
    public Focusable getFocusedWidget() {
        return focusedWidget;
    }

    /**
     * Sets the focused widget.
     */
    public void setFocusedWidget(Focusable widget) {
        this.focusedWidget = widget;
        // TODO : fire events here?
    }

    /**
     * Shows the window.
     */
    public void show() {
        setVisible(true);
    }

    /**
     * Hides the window.
     */
    public void hide() {
        setVisible(false);
    }

    /**
     * Activates the window.
     */
    public void activate() {
        if (!isActive()) {
            getTopLevel().setActiveWindow(this);
        }
    }

    // events

    @Override
    public void onMouseDown(MouseDownEvent event) {
        super.onMouseDown(event);

        if (!event.isCancelled()) {
            // focus the deepest widget under the click
            Widget deepestWidget = getDeepestClickableWidget(event);
            while (deepestWidget != null) {
                if (deepestWidget instanceof Focusable) {
                    ((Focusable) deepestWidget).focus();
                    break;
                }
                deepestWidget = deepestWidget.getParent();
            }
        }

        if (!event.isCancelled()) {
            activate();
        }
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        super.onMouseMove(event);

        if (!event.isCancelled()) {
            ResizingDirection direction = dragListener.getResizingDirection(event.getPosition());
            if (direction != null) {
                MousePointer pointer = MousePointer.NORMAL;
                if (direction == ResizingDirection.NORTH)
                    pointer = MousePointer.RESIZED_N;
                else if (direction == ResizingDirection.SOUTH)
                    pointer = MousePointer.RESIZED_S;
                else if (direction == ResizingDirection.EAST)
                    pointer = MousePointer.RESIZED_E;
                else if (direction == ResizingDirection.WEST)
                    pointer = MousePointer.RESIZED_W;
                else if (direction == ResizingDirection.NORTH_EAST)
                    pointer = MousePointer.RESIZED_NE;
                else if (direction == ResizingDirection.NORTH_WEST)
                    pointer = MousePointer.RESIZED_NW;
                else if (direction == ResizingDirection.SOUTH_EAST)
                    pointer = MousePointer.RESIZED_SE;
                else if (direction == ResizingDirection.SOUTH_WEST)
                    pointer = MousePointer.RESIZED_SW;

                // ?
                getTopLevel().getContext().setMousePointer(pointer);
            } else {
                Shape title = getLookAndFeel().getWindowTitleShape(Window.this);
                if (title.contains(event.getPosition())) {
                    getTopLevel().getContext().setMousePointer(MousePointer.MOVE);
                } else {
                    // Only sets the pointer back to normal if it is set to a value we set earlier
                    // Otherwise we override what other widgets set
                    MousePointer p = getTopLevel().getContext().getMousePointer();
                    if (p == MousePointer.MOVE || p == MousePointer.RESIZED_N
                            || p == MousePointer.RESIZED_S || p == MousePointer.RESIZED_E
                            || p == MousePointer.RESIZED_W || p == MousePointer.RESIZED_NE
                            || p == MousePointer.RESIZED_NW || p == MousePointer.RESIZED_SE
                            || p == MousePointer.RESIZED_SW) {
                        getTopLevel().getContext().setMousePointer(MousePointer.NORMAL);
                    }
                }
            }
        }
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        super.onMouseOut(event);

        if (!event.isCancelled()) {
            getTopLevel().getContext().setMousePointer(MousePointer.NORMAL);
        }
    }

    // TODO : use flags or a state?
    public boolean isMoving() {
        return dragListener.isMoving();
    }

}
