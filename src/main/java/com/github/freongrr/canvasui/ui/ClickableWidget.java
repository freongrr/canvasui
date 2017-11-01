package com.github.freongrr.canvasui.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOverListener;
import com.github.freongrr.canvasui.events.MouseUpListener;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseDownListener;
import com.github.freongrr.canvasui.events.MouseMoveListener;
import com.github.freongrr.canvasui.events.MouseOutListener;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.events.MouseWheelEvent;
import com.github.freongrr.canvasui.events.MouseWheelListener;

/**
 * This class provides a default implementation of {@link Clickable} and {@link Widget}.
 * 
 * @author fabien
 */
public abstract class ClickableWidget extends BaseWidget implements Clickable {
    private List<MouseDownListener> mouseDownListeners = new ArrayList<MouseDownListener>();
    private List<MouseUpListener> mouseUpListeners = new ArrayList<MouseUpListener>();
    private List<MouseMoveListener> mouseMoveListeners = new ArrayList<MouseMoveListener>();
    private List<MouseOverListener> mouseOverListeners = new ArrayList<MouseOverListener>();
    private List<MouseOutListener> mouseOutListeners = new ArrayList<MouseOutListener>();
    private List<MouseWheelListener> mouseWheelListeners = new ArrayList<MouseWheelListener>();

    @Override
    public void addMouseDownListener(MouseDownListener listener) {
        mouseDownListeners.add(listener);
    }

    @Override
    public void removeMouseDownListener(MouseDownListener listener) {
        mouseDownListeners.remove(listener);
    }

    @Override
    public void fireMouseDownEvent(MouseDownEvent me) {
        for (MouseDownListener l : new ArrayList<MouseDownListener>(mouseDownListeners)) {
            if (!me.isCancelled()) {
                l.onMouseDown(me);
            }
        }
    }

    @Override
    public void addMouseUpListener(MouseUpListener listener) {
        mouseUpListeners.add(listener);
    }

    @Override
    public void removeMouseUpListener(MouseUpListener listener) {
        mouseUpListeners.remove(listener);
    }

    @Override
    public void fireMouseUpEvent(MouseUpEvent me) {
        for (MouseUpListener l : new ArrayList<MouseUpListener>(mouseUpListeners)) {
            if (!me.isCancelled()) {
                l.onMouseUp(me);
            }
        }
    }

    @Override
    public void addMouseMoveListener(MouseMoveListener listener) {
        mouseMoveListeners.add(listener);
    }

    @Override
    public void removeMouseMoveListener(MouseMoveListener listener) {
        mouseMoveListeners.remove(listener);
    }

    @Override
    public void fireMouseMoveEvent(MouseMoveEvent me) {
        for (MouseMoveListener l : new ArrayList<MouseMoveListener>(mouseMoveListeners)) {
            if (!me.isCancelled()) {
                l.onMouseMove(me);
            }
        }
    }

    @Override
    public void addMouseOverListener(MouseOverListener listener) {
        mouseOverListeners.add(listener);
    }

    @Override
    public void removeMouseOverListener(MouseOverListener listener) {
        mouseOverListeners.remove(listener);
    }

    @Override
    public void fireMouseOverEvent(MouseOverEvent me) {
        for (MouseOverListener l : new ArrayList<MouseOverListener>(mouseOverListeners)) {
            if (!me.isCancelled()) {
                l.onMouseOver(me);
            }
        }
    }

    @Override
    public void addMouseOutListener(MouseOutListener listener) {
        mouseOutListeners.add(listener);
    }

    @Override
    public void removeMouseOutListener(MouseOutListener listener) {
        mouseOutListeners.remove(listener);
    }

    @Override
    public void fireMouseOutEvent(MouseOutEvent me) {
        for (MouseOutListener l : new ArrayList<MouseOutListener>(mouseOutListeners)) {
            if (!me.isCancelled()) {
                l.onMouseOut(me);
            }
        }
    }

    @Override
    public void addMouseWheelListener(MouseWheelListener listener) {
        mouseWheelListeners.add(listener);
    }

    @Override
    public void removeMouseWheelListener(MouseWheelListener listener) {
        mouseWheelListeners.remove(listener);
    }

    @Override
    public void fireMouseWheelEvent(MouseWheelEvent me) {
        for (MouseWheelListener l : new ArrayList<MouseWheelListener>(mouseWheelListeners)) {
            if (!me.isCancelled()) {
                l.onMouseWheel(me);
            }
        }
    }
}
