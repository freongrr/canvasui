package com.github.freongrr.canvasui.ui;

/**
 * This interface represents a Widget that need keyboard input. By opposition, a {@link Focusable}
 * widget can receive keyboard events, but they are not as important.
 * 
 * @author fabien
 * 
 */
public interface Typable extends Focusable {

    /**
     * Returns the value of the widget as a String.
     */
    public String getTypedString();

    /**
     * Sets the value of the widget as a String.
     */
    public void setTypedString(String value);

    /**
     * Returns the position of the carret.
     */
    public int getCarretPosition();

    /**
     * Sets the position of the carret.
     */
    public void setCarretPosition(int position);

    /**
     * Returns the start of the selection.
     */
    public int getSelectionStart();

    /**
     * Sets the start of the selection.
     */
    public void setSelectionStart(int start);

    /**
     * Returns the start of the selection.
     */
    public int getSelectionEnd();

    /**
     * Sets the start of the selection.
     */
    public void setSelectionEnd(int end);

    /**
     * Sets the selection bounds.
     */
    public void setSelection(int start, int end);
}
