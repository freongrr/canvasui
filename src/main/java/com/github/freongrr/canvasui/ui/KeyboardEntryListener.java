package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.KeyDownEvent;
import com.github.freongrr.canvasui.events.KeyPressEvent;
import com.github.freongrr.canvasui.events.KeyPressListener;
import com.github.freongrr.canvasui.events.KeyDownListener;
import com.github.freongrr.canvasui.events.KeyUpEvent;
import com.github.freongrr.canvasui.events.KeyUpListener;

/**
 * This implementation allow moving and resizing windows.
 * 
 * @author fabien
 */
public class KeyboardEntryListener implements KeyDownListener, KeyUpListener, KeyPressListener {

    private Typable widget;

    public KeyboardEntryListener() {
    }

    public void bind(Typable widget) {
        widget.addKeyDownListener(this);
        widget.addKeyUpListener(this);
        widget.addKeyPressListener(this);
        this.widget = widget;
    }

    public void unbind(Typable widget) {
        widget.removeKeyDownListener(this);
        widget.removeKeyUpListener(this);
        widget.removeKeyPressListener(this);
        this.widget = null;
    }

    @Override
    public void onKeyDown(KeyDownEvent ke) {
        // Ugly...
        ((BaseWidget) widget).getTopLevel().suspendEvents();

        int position = widget.getCarretPosition();
        int selStart = widget.getSelectionStart();
        int selEnd = widget.getSelectionEnd();
        String value = widget.getTypedString();
        // move left
        if (ke.isLeftArrow()) {
            if (position > 0) {
                // if selecting
                if (ke.isShiftKey()) {
                    // new selection
                    if (selStart == -1) {
                        widget.setSelection(position - 1, position);
                    }
                    // expend the current selection
                    else if (position > selStart) {
                        widget.setSelectionEnd(position - 1);
                    } else {
                        widget.setSelectionStart(position - 1);
                    }
                } else {
                    widget.setSelection(-1, -1);
                }
                widget.setCarretPosition(position - 1);
            }
        }
        // move right
        else if (ke.isRightArrow()) {
            if (position < value.length()) {
                // if selecting
                if (ke.isShiftKey()) {
                    // new selection
                    if (selStart == -1) {
                        widget.setSelection(position, position + 1);
                    }
                    // expend the current selection
                    else if (position > selStart) {
                        widget.setSelectionEnd(position + 1);
                    } else {
                        widget.setSelectionStart(position + 1);
                    }
                } else {
                    widget.setSelection(-1, -1);
                }
                widget.setCarretPosition(position + 1);
            }
        }
        // move to the begining of the line
        else if (ke.isHomeKey()) {
            // if selecting
            if (ke.isShiftKey() && position != 0) {
                // new selection
                if (selStart == -1) {
                    widget.setSelection(0, position);
                }
                // expend the current selection
                else {
                    widget.setSelectionStart(0);
                }
            } else {
                widget.setSelection(-1, -1);
            }
            widget.setCarretPosition(0);
        }
        // move to the end of the line
        else if (ke.isEndKey()) {
            // if selecting
            if (ke.isShiftKey() && position != value.length()) {
                // new selection
                if (selStart == -1) {
                    widget.setSelection(position, value.length());
                }
                // expend the current selection
                else {
                    widget.setSelectionEnd(value.length());
                }
            } else {
                widget.setSelection(-1, -1);
            }
            widget.setCarretPosition(value.length());
        } else if (ke.isDeleteKey()) {
            // delete the selection
            if (selStart != -1) {
                value = value.substring(0, selStart) + value.substring(selEnd);
                widget.setCarretPosition(selStart);
                widget.setTypedString(value);
                widget.setSelection(-1, -1);
            }
            // delete one character at the current position
            else if (position < value.length()) {
                value = value.substring(0, position) + value.substring(position + 1);
                widget.setTypedString(value);
            }
        } else if (ke.isBackspaceKey()) {
            // delete the selection
            if (selStart != -1) {
                value = value.substring(0, selStart) + value.substring(selEnd);
                widget.setCarretPosition(selStart);
                widget.setTypedString(value);
                widget.setSelection(-1, -1);
            }
            // delete one character before the current position
            else if (position > 0) {
                value = value.substring(0, position - 1) + value.substring(position);
                widget.setTypedString(value);
                widget.setCarretPosition(position - 1);
            }
        }
        // Input caracter
        else if (ke.getKeyCode() != 0 && !ke.isTabKey()) {
            // delete the selection
            if (selStart != -1) {
                value = value.substring(0, selStart) + value.substring(selEnd);
                widget.setCarretPosition(selStart);
                widget.setSelection(-1, -1);
            }

            char newChar = ke.getCharCode();
            value = value.substring(0, position) + newChar + value.substring(position);

            widget.setTypedString(value);
            widget.setCarretPosition(position + 1);
        }

        // debug
        //widget.setTypedString(ke.toString());

        // Ugly...
        ((BaseWidget) widget).getTopLevel().resumeEvents();
    }

    @Override
    public void onKeyPress(KeyPressEvent ke) {
        // nothing to do
    }

    @Override
    public void onKeyUp(KeyUpEvent ke) {
        // nothing to do
    }
}
