package com.github.freongrr.canvasui.events;

/**
 * Base keyboard event.
 * 
 * @author fabien
 */
public class KeyboardEvent extends Event {
    public static final int KEY_ALT = 18;
    public static final int KEY_BACKSPACE = 8;
    public static final int KEY_CTRL = 17;
    public static final int KEY_DELETE = 46;
    public static final int KEY_DOWN = 40;
    public static final int KEY_END = 35;
    public static final int KEY_ENTER = 13;
    public static final int KEY_ESCAPE = 27;
    public static final int KEY_HOME = 36;
    public static final int KEY_LEFT = 37;
    public static final int KEY_PAGEDOWN = 34;
    public static final int KEY_PAGEUP = 33;
    public static final int KEY_RIGHT = 39;
    public static final int KEY_SHIFT = 16;
    public static final int KEY_TAB = 9;
    public static final int KEY_UP = 38;

    private int keyCode = -1;
    private char charCode;

    private boolean altKey;
    private boolean shiftKey;
    private boolean controlKey;
    private boolean metaKey;

    public KeyboardEvent() {
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public char getCharCode() {
        return charCode;
    }

    public void setCharCode(char charCode) {
        this.charCode = charCode;
    }

    public boolean isAltKey() {
        return altKey;
    }

    public void setAltKey(boolean altKey) {
        this.altKey = altKey;
    }

    public boolean isShiftKey() {
        return shiftKey;
    }

    public void setShiftKey(boolean shiftKey) {
        this.shiftKey = shiftKey;
    }

    public boolean isControlKey() {
        return controlKey;
    }

    public void setControlKey(boolean controlKey) {
        this.controlKey = controlKey;
    }

    public boolean isMetaKey() {
        return metaKey;
    }

    public void setMetaKey(boolean metaKey) {
        this.metaKey = metaKey;
    }

    protected String getModifiersDebug() {
        String modifiers = "";
        if (isShiftKey()) {
            modifiers += "SHIFT + ";
        }
        if (isAltKey()) {
            modifiers += "ALT + ";
        }
        if (isControlKey()) {
            modifiers += "CTRL + ";
        }
        if (isMetaKey()) {
            modifiers += "META + ";
        }
        return modifiers;
    }

    @Override
    public String toString() {
        String displayedChar = charCode == '\0' ? "\\0" : String.valueOf(charCode);
        return "KeyboardEvent [" + getModifiersDebug() + keyCode + ": " + displayedChar + "]";
    }

    public boolean isTypableKey() {
        return false;
    }

    public boolean isLeftArrow() {
        return keyCode == KEY_LEFT;
    }

    public boolean isRightArrow() {
        return keyCode == KEY_RIGHT;
    }

    public boolean isUpArrow() {
        return keyCode == KEY_UP;
    }

    public boolean isDowArrow() {
        return keyCode == KEY_DOWN;
    }

    public boolean isHomeKey() {
        return keyCode == KEY_HOME;
    }

    public boolean isEndKey() {
        return keyCode == KEY_END;
    }

    public boolean isDeleteKey() {
        return keyCode == KEY_DELETE;
    }

    public boolean isBackspaceKey() {
        return keyCode == KEY_BACKSPACE;
    }

    public boolean isTabKey() {
        return keyCode == KEY_TAB;
    }
}
