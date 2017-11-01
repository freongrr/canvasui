package com.github.freongrr.canvasui.g2d;

import com.github.freongrr.canvasui.events.Event;
import com.github.freongrr.canvasui.events.LayoutEvent;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Canvas;
import com.github.freongrr.canvasui.ui.Widget;
import com.github.freongrr.canvasui.ui.Window;
import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.ImageDataAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.github.freongrr.canvasui.events.RedrawEvent;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.ui.Focusable;
import com.github.freongrr.canvasui.ui.Typable;

/**
 * Implementation of {@link Canvas} using the &lt;canvas&gt; tag, backed by the the gwt-2d library.
 * 
 * @author fabien
 */
public class G2DCanvas extends Canvas {

    private final static Boolean MOVING_WINDOW_BUFFER = false;

    private G2DContext gc;
    private Surface surface;
    private int suspendEvents = 0;
    private List<Event> waitingEvents = new LinkedList<Event>();

    // Optimisation for moving windows
    private ImageDataAdapter backgroundBuffer = null;
    private ImageDataAdapter windowBuffer = null;
    private Window movingWindow = null;

    public TextBox dummyText;

    /**
     * This constructor create a canvas with a defaut size.
     */
    public G2DCanvas() {
        this(100, 100);
    }

    /**
     * Creates a canvas with the specified size.
     */
    public G2DCanvas(int width, int height) {
        size = new Vector(width, height);
        offset = new Vector();

        surface = new Surface(width, height);
        gc = new G2DContext(surface);

        G2DEventHandler eventManager = new G2DEventHandler(this);

        // as the top level element, this is the only object registered for
        // events
        surface.addMouseDownHandler(eventManager);
        surface.addMouseMoveHandler(eventManager);
        surface.addMouseOutHandler(eventManager);
        surface.addMouseOverHandler(eventManager);
        surface.addMouseUpHandler(eventManager);
        surface.addMouseWheelHandler(eventManager);

        dummyText = new TextBox();
        dummyText.getElement().setAttribute("style", "display: none");
        dummyText.addKeyDownHandler(eventManager);
        dummyText.addKeyPressHandler(eventManager);
        dummyText.addKeyUpHandler(eventManager);

        // Focusing the widget in setFocusedWidget doesn't work well on iOS
        // Doing it works, but the keyboard might always appear...
        surface.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent event) {
                updateDummyText();
            }
        });
    }

    // Methods of Widget

    @Override
    public Rectangle getDrawArea() {
        int width = surface.getWidth();
        int height = surface.getHeight();

        // before the Surface is drawn the width and height is 0
        // but we can use the initial size instead
        if (width == 0 || height == 0) {
            Vector initialSize = getSize();
            width = (int) initialSize.getX();
            height = (int) initialSize.getY();
        }

        // TODO : position
        return new Rectangle(0, 0, width, height);
    }

    @Override
    public Shape getEventArea() {
        return getDrawArea();
    }

    // Methods of Canvas

    @Override
    public GraphicContext getContext() {
        return gc;
    }

    @Override
    public void go(Object panel) {
        ((HasWidgets) panel).add(surface);
        ((HasWidgets) panel).add(dummyText);
        draw(gc, null);
    }

    @Override
    public void draw() {
        this.draw(gc, null);
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        surface.clear();

        if (MOVING_WINDOW_BUFFER) {
            // First check if there is a window moving
            checkMovingWindow();

            // If the window has just started moving we need to create a buffer
            if (movingWindow != null && windowBuffer == null) {
                Rectangle rect = movingWindow.getDrawArea();

                movingWindow.draw(context, null);
                surface.save();
                surface.setFillStyle(new Color(52, 91, 131, 0.5));
                surface.fillRectangle(G2DContext.convert(rect));
                surface.restore();
                windowBuffer = surface.getImageData(rect.left, rect.top, rect.width, rect.height);
                surface.clear();
            }
        }

        // If we have a buffer we don't have to redraw everything
        if (MOVING_WINDOW_BUFFER && backgroundBuffer != null) {
            surface.putImageData(backgroundBuffer, 0, 0);
        } else {
            if (area == null) {
                area = getDrawArea();
            }

            // Transparent background...
//            surface.save();
//            surface.setFillStyle(KnownColor.BISQUE);
//            surface.fillRectangle(G2DContext.convert(area));
//            surface.restore();

            // draw the content of the container
            super.draw(context, area);

            // Keep the image in a buffer
            if (MOVING_WINDOW_BUFFER && movingWindow != null) {
                backgroundBuffer = surface.getImageData(0, 0, getSize().getX(), getSize().getY());
            }
        }

        // Finally draw the moving window
        if (MOVING_WINDOW_BUFFER && movingWindow != null) {
            Rectangle rect = movingWindow.getDrawArea();
            surface.putImageData(windowBuffer, rect.getLeft(), rect.getTop());
        }
    }

    @Override
    protected void drawContent(GraphicContext context, Rectangle area) {
        List<Widget> widgets = getVisibleWidgets(area);
        Collections.reverse(widgets);
        for (Widget w : widgets) {
            // Doesn't draw the moving window
            if (w != movingWindow) {
                w.draw(context, area);
            }
        }
    }

    @Override
    public void suspendEvents() {
        suspendEvents++;
    }

    @Override
    public void resumeEvents() {
        suspendEvents--;

        if (suspendEvents <= 0 && waitingEvents.size() > 0) {
            suspendEvents = 0;

            // only fires the first one
            Event e = waitingEvents.get(0);
            waitingEvents.clear();
            onRedraw((RedrawEvent) e);
        }
    }

    public void resetEvents() {
        suspendEvents = 0;
        waitingEvents.clear();
    }

    // events

    @Override
    public void onRedraw(RedrawEvent event) {
        // TODO : rethink and redo everyhing
        if (suspendEvents > 0) {
            waitingEvents.add(event);
        } else {
            draw();
        }
    }

    @Override
    public void onLayout(LayoutEvent event) {
        // TODO : rethink and redo everyhing
    }

    // Methods of FocusListener

    @Override
    public void onFocus(com.github.freongrr.canvasui.events.FocusEvent de) {
        super.onFocus(de);
        updateDummyText();
    }

    // Methods of G2DCanvas

    /**
     * Update the content and the position of the dummy widget.
     * 
     * @param widget
     */
    private void updateDummyText() {
        Window activeWindow = getActiveWindow();
        Focusable focusedWidget = activeWindow != null ? activeWindow.getFocusedWidget() : null;
        if (dummyText != null && focusedWidget != null && focusedWidget instanceof Typable) {
            Typable widget = (Typable) focusedWidget;

            int left = surface.getAbsoluteLeft();
            int top = surface.getAbsoluteLeft();

            // HACK : On iOS (and Android?) I display the text field over the focused widget
            String ua = Navigator.getUserAgent();
            if (ua != null && ua.toLowerCase().contains("mobile")) {
                Vector position = widget.getAbsolutePosition();
                Vector size = widget.getSize();

                left = left + (int) position.x - 4;
                top = top + (int) position.y - 2;
                int width = (int) size.x;
                int height = (int) size.y;

                String style = "position: absolute; left:" + left + "px; top: " + top
                        + "px; width: " + width + "px; height: " + height + "px;";
                dummyText.getElement().setAttribute("style", style);
            }
            // Otherwise it is hidden and is only used to catch events
            else {
                String style = "position: absolute; left:-50px; top:-50px; width: 10px; height: 10px; z-index: -1;";
                dummyText.getElement().setAttribute("style", style);
            }

            String text = widget.getTypedString();
            dummyText.setText(text);

            surface.setFocus(false);
            dummyText.setFocus(true);
        } else {
            String style = "display: none";
            dummyText.getElement().setAttribute("style", style);
        }
    }

    private void checkMovingWindow() {
        if (movingWindow != null) {
            // check if it stopped moving
            if (!movingWindow.isMoving()) {
                movingWindow = null;
                backgroundBuffer = null;
                windowBuffer = null;
            }
        }

        if (movingWindow == null) {
            for (Widget w : getVisibleWidgets()) {
                if (w instanceof Window && ((Window) w).isMoving()) {
                    movingWindow = (Window) w;
                    break;
                }
            }
        }
    }
}
