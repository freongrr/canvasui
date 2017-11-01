package com.github.freongrr.canvasui.g2d;

import java.util.LinkedList;
import java.util.List;

import com.github.freongrr.canvasui.events.CancelEvent;
import com.github.freongrr.canvasui.events.CancelListener;
import com.github.freongrr.canvasui.events.KeyDownEvent;
import com.github.freongrr.canvasui.events.KeyPressEvent;
import com.github.freongrr.canvasui.events.KeyUpEvent;
import com.github.freongrr.canvasui.events.KeyboardEvent;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseEvent;
import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseWheelEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.ui.Clickable;
import com.github.freongrr.canvasui.ui.Container;

/**
 * This class manages the event for the {@link G2DContext} implementation.
 * 
 * @author fabien
 */
public class G2DEventHandler implements MouseDownHandler, MouseUpHandler, MouseOutHandler,
        MouseOverHandler, MouseWheelHandler, MouseMoveHandler, KeyDownHandler, KeyPressHandler,
        KeyUpHandler {

    private Container container;
    private List<Clickable> oldUnderTheMouse;
    private KeyDownEvent keyDownEvent;

    public G2DEventHandler(Container container) {
        this.container = container;
    }

    /* Events */

    private void initMouseEvent(MouseEvent me,
            com.google.gwt.event.dom.client.MouseEvent<?> gwtEvent) {
        me.setNativeEvent(gwtEvent);
        me.addCancelListener(new CancelListener() {
            @Override
            public void onCancel(CancelEvent ce) {
                ((DomEvent<?>) ce.getNativeEvent()).stopPropagation();
            }
        });

        me.setX(gwtEvent.getX());
        me.setY(gwtEvent.getY());
        me.setAltKey(gwtEvent.isAltKeyDown());
        me.setShiftKey(gwtEvent.isShiftKeyDown());
        me.setControlKey(gwtEvent.isControlKeyDown());
        me.setMetaKey(gwtEvent.isMetaKeyDown());

        // other properties

        if (me instanceof MouseWheelEvent) {
            ((MouseWheelEvent) me)
                    .setDelta(((com.google.gwt.event.dom.client.MouseWheelEvent) gwtEvent)
                            .getDeltaY());
        }
    }

    private void initKeyboardEvent(KeyboardEvent ke,
            com.google.gwt.event.dom.client.KeyEvent<?> gwtEvent) {
        ke.setNativeEvent(gwtEvent);
        ke.addCancelListener(new CancelListener() {
            @Override
            public void onCancel(CancelEvent ce) {
                ((DomEvent<?>) ce.getNativeEvent()).stopPropagation();
            }
        });

        ke.setAltKey(gwtEvent.isAltKeyDown());
        ke.setShiftKey(gwtEvent.isShiftKeyDown());
        ke.setControlKey(gwtEvent.isControlKeyDown());
        ke.setMetaKey(gwtEvent.isMetaKeyDown());

        // GWT has a strange way of generating events:
        // - the char code is only available in KeyPressEvents
        // - the key code is only available in KeyDownEvents and KeyUpEvents
        if (gwtEvent instanceof com.google.gwt.event.dom.client.KeyCodeEvent<?>) {
            com.google.gwt.event.dom.client.KeyCodeEvent<?> keyCodeEvent = (com.google.gwt.event.dom.client.KeyCodeEvent<?>) gwtEvent;
            ke.setKeyCode(keyCodeEvent.getNativeKeyCode());
        } else if (gwtEvent instanceof com.google.gwt.event.dom.client.KeyPressEvent) {
            com.google.gwt.event.dom.client.KeyPressEvent gwtKeyPress = (com.google.gwt.event.dom.client.KeyPressEvent) gwtEvent;
            ((KeyPressEvent) ke).setCharCode(gwtKeyPress.getCharCode());
        }
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent event) {
        MouseDownEvent me = new MouseDownEvent();
        initMouseEvent(me, event);
        container.fireMouseDownEvent(me);
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseUp(com.google.gwt.event.dom.client.MouseUpEvent event) {
        MouseUpEvent me = new MouseUpEvent();
        initMouseEvent(me, event);
        container.fireMouseUpEvent(me);
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseMove(com.google.gwt.event.dom.client.MouseMoveEvent event) {
        MouseMoveEvent me = new MouseMoveEvent();
        initMouseEvent(me, event);

        initMouseEvent(me, event);
        container.fireMouseMoveEvent(me);

        // TODO : this process is rather heavy. We should only do that if
        // somebody is listening for MouseOut/MouseOver events?

        // looks for all the widgets under the mouse pointer
        List<Clickable> underTheMouse = new LinkedList<Clickable>();
        Clickable clickable = container.getClickableWidget(me);
        while (clickable != null) {
            underTheMouse.add(clickable);

            // keeps looking for elements until we hit the bottom
            if (clickable instanceof Container) {
                Clickable clickable2 = ((Container) clickable).getClickableWidget(me);
                // found the same element
                if (clickable2 == clickable) {
                    break;
                }

                clickable = clickable2;
            } else {
                break;
            }
        }

        // Compare the list of elements with the previous one
        if (oldUnderTheMouse != null) {
            for (Clickable oldElement : oldUnderTheMouse) {
                // if an old element is not under the mouse anymore
                if (!underTheMouse.contains(oldElement)) {
                    // fires a MouseOut event
                    MouseOutEvent mover = new MouseOutEvent();
                    initMouseEvent(mover, event);
                    oldElement.fireMouseOutEvent(mover);
                }
            }
        }

        for (Clickable element : underTheMouse) {
            // if an element was not under the mouse before
            if (oldUnderTheMouse == null || !oldUnderTheMouse.contains(element)) {
                // we fire a MouseOver event
                MouseOverEvent mover = new MouseOverEvent();
                initMouseEvent(mover, event);
                element.fireMouseOverEvent(mover);
            }
        }

        oldUnderTheMouse = underTheMouse;
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseOver(com.google.gwt.event.dom.client.MouseOverEvent event) {
        // This event is fired when we move over the canvas
        // We handle mouse oven in onMouseMove
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseOut(com.google.gwt.event.dom.client.MouseOutEvent event) {
        // This event is fired when we move over the canvas
        // We handle mouse oven in onMouseMove
    }

    /**
     * Converts a GWT mouse event and dispatch it to the container.
     */
    @Override
    public void onMouseWheel(com.google.gwt.event.dom.client.MouseWheelEvent event) {
        MouseWheelEvent me = new MouseWheelEvent();
        initMouseEvent(me, event);
        container.fireMouseWheelEvent(me);
    }

    /**
     * Converts a GWT keyboard event and dispatch it to the container.
     */
    @Override
    public void onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent event) {
        KeyDownEvent ke = new KeyDownEvent();
        initKeyboardEvent(ke, event);

        // We can't fire the event right away because it's not complete:
        // Half of the information is in KeyCodeEvent and the other in KeyPressEvent.
        keyDownEvent = ke;
    }

    /**
     * Converts a GWT keyboard event and dispatch it to the container.
     */
    @Override
    public void onKeyPress(com.google.gwt.event.dom.client.KeyPressEvent event) {
        KeyPressEvent ke = new KeyPressEvent();
        initKeyboardEvent(ke, event);

        // Complete the events with each others
        if (keyDownEvent != null) {
            ke.setKeyCode(keyDownEvent.getKeyCode());
            keyDownEvent.setCharCode(ke.getCharCode());
        }

        // test just in case
        if (keyDownEvent != null) {
            container.fireKeyDownEvent(keyDownEvent);
        }
        container.fireKeyPressEvent(ke);
    }

    /**
     * Converts a GWT keyboard event and dispatch it to the container.
     */
    @Override
    public void onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent event) {
        KeyUpEvent ke = new KeyUpEvent();
        initKeyboardEvent(ke, event);

        // Complete the event with the previous one
        if (keyDownEvent != null) {
            ke.setKeyCode(keyDownEvent.getKeyCode());
        }

        container.fireKeyUpEvent(ke);

        keyDownEvent = null;
    }
}
