package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.FocusListener;
import com.github.freongrr.canvasui.events.KeyDownEvent;
import com.github.freongrr.canvasui.events.KeyPressEvent;
import com.github.freongrr.canvasui.events.KeyPressListener;
import com.github.freongrr.canvasui.events.KeyUpEvent;
import com.github.freongrr.canvasui.events.KeyUpListener;
import com.github.freongrr.canvasui.events.FocusEvent;
import com.github.freongrr.canvasui.events.KeyDownListener;

/**
 * Focusable widget
 * 
 * @author fabien
 */
public interface Focusable extends Widget {
    
    /**
     * Focus the widget.
     */
    public void focus();

    /**
     * Returns true if the widget is focused.
     */
    public boolean isFocused();

    public void addFocusListener( FocusListener listener );
    public void addKeyDownListener( KeyDownListener listener );
    public void addKeyUpListener( KeyUpListener listener );
    public void addKeyPressListener( KeyPressListener listener );

    public void removeFocusListener( FocusListener listener );
    public void removeKeyDownListener( KeyDownListener listener );
    public void removeKeyUpListener( KeyUpListener listener );
    public void removeKeyPressListener( KeyPressListener listener );

    public void fireFocusEvent( FocusEvent me );
    public void fireKeyDownEvent( KeyDownEvent me );
    public void fireKeyUpEvent( KeyUpEvent me );
    public void fireKeyPressEvent( KeyPressEvent me );
}
