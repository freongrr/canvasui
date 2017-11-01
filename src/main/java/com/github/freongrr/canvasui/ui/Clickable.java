package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseDownListener;
import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOutListener;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseOverListener;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.events.MouseUpListener;
import com.github.freongrr.canvasui.events.MouseWheelEvent;
import com.github.freongrr.canvasui.events.MouseWheelListener;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.events.MouseMoveListener;

/**
 * A widget that receive mouse events
 * 
 * @author fabien
 */
public interface Clickable extends Widget {
    
    /**
     * Returns the area receiving events.
     */
    public Shape getEventArea();

    public boolean isEnabled();

    public void addMouseDownListener( MouseDownListener listener );
    public void addMouseUpListener( MouseUpListener listener );
    public void addMouseMoveListener( MouseMoveListener listener );
    public void addMouseOverListener( MouseOverListener listener );
    public void addMouseOutListener( MouseOutListener listener );
    public void addMouseWheelListener( MouseWheelListener listener );

    public void removeMouseDownListener( MouseDownListener listener );
    public void removeMouseUpListener( MouseUpListener listener );
    public void removeMouseMoveListener( MouseMoveListener listener );
    public void removeMouseOverListener( MouseOverListener listener );
    public void removeMouseOutListener( MouseOutListener listener );
    public void removeMouseWheelListener( MouseWheelListener listener );

    public void fireMouseDownEvent( MouseDownEvent me );
    public void fireMouseUpEvent( MouseUpEvent me );
    public void fireMouseMoveEvent( MouseMoveEvent me );
    public void fireMouseOverEvent( MouseOverEvent me );
    public void fireMouseOutEvent( MouseOutEvent me );
    public void fireMouseWheelEvent( MouseWheelEvent me );
}
