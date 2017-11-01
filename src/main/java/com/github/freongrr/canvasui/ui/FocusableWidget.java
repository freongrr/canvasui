package com.github.freongrr.canvasui.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.freongrr.canvasui.events.AttachEvent;
import com.github.freongrr.canvasui.events.DetachEvent;
import com.github.freongrr.canvasui.events.FocusListener;
import com.github.freongrr.canvasui.events.KeyDownEvent;
import com.github.freongrr.canvasui.events.KeyPressEvent;
import com.github.freongrr.canvasui.events.KeyPressListener;
import com.github.freongrr.canvasui.events.KeyUpEvent;
import com.github.freongrr.canvasui.events.KeyUpListener;
import com.github.freongrr.canvasui.events.FocusEvent;
import com.github.freongrr.canvasui.events.KeyDownListener;

/**
 * This class adds {@link Focusable} capabilities to a {@link Clickable} widget.
 * 
 * @author fabien
 */
public abstract class FocusableWidget extends ClickableWidget implements Focusable {

    private List<FocusListener> focusListeners = new ArrayList<FocusListener>();
    private List<KeyDownListener> keyDownListeners = new ArrayList<KeyDownListener>();
    private List<KeyUpListener> keyUpListeners = new ArrayList<KeyUpListener>();
    private List<KeyPressListener> keyPressListeners = new ArrayList<KeyPressListener>();

    @Override
    public void onAttach(AttachEvent ae) {
        super.onAttach(ae);
        addFocusListener(getTopLevel());
    }

    @Override
    public void onDetach(DetachEvent de) {
        super.onDetach(de);
        removeFocusListener(getTopLevel());
    }

    @Override
    public void focus() {
        if (!isFocused()) {
            Window window = getParentWindow();
            if (window != null) {
                window.setFocusedWidget(this);
            }
            fireFocusEvent();
        }
    }

    @Override
    public boolean isFocused() {
        // There is only one focused widget per window
        // Also, it is only really focused when the window is active
        Window win = getParentWindow();
        return win != null && win.isActive() && win.getFocusedWidget() == this;
    }

    @Override
    public void addFocusListener(FocusListener listener) {
        focusListeners.add(listener);
    }

    @Override
    public void removeFocusListener(FocusListener listener) {
        focusListeners.remove(listener);
    }

    @Override
    public void fireFocusEvent(FocusEvent ke) {
        for (FocusListener l : new ArrayList<FocusListener>(focusListeners)) {
            if (!ke.isCancelled()) {
                l.onFocus(ke);
            }
        }
    }

    @Override
    public void addKeyDownListener(KeyDownListener listener) {
        keyDownListeners.add(listener);
    }

    @Override
    public void removeKeyDownListener(KeyDownListener listener) {
        keyDownListeners.remove(listener);
    }

    @Override
    public void fireKeyDownEvent(KeyDownEvent ke) {
        for (KeyDownListener l : new ArrayList<KeyDownListener>(keyDownListeners)) {
            if (!ke.isCancelled()) {
                l.onKeyDown(ke);
            }
        }
    }

    @Override
    public void addKeyUpListener(KeyUpListener listener) {
        keyUpListeners.add(listener);
    }

    @Override
    public void removeKeyUpListener(KeyUpListener listener) {
        keyUpListeners.remove(listener);
    }

    @Override
    public void fireKeyUpEvent(KeyUpEvent ke) {
        for (KeyUpListener l : new ArrayList<KeyUpListener>(keyUpListeners)) {
            if (!ke.isCancelled()) {
                l.onKeyUp(ke);
            }
        }
    }

    @Override
    public void addKeyPressListener(KeyPressListener listener) {
        keyPressListeners.add(listener);
    }

    @Override
    public void removeKeyPressListener(KeyPressListener listener) {
        keyPressListeners.remove(listener);
    }

    @Override
    public void fireKeyPressEvent(KeyPressEvent ke) {
        for (KeyPressListener l : new ArrayList<KeyPressListener>(keyPressListeners)) {
            if (!ke.isCancelled()) {
                l.onKeyPress(ke);
            }
        }
    }

    /**
     * Useful method for firing Focus events
     */
    private void fireFocusEvent() {
        fireFocusEvent(new FocusEvent(this));
    }
}
