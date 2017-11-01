package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * This class defines a simple text entry widget.
 * 
 * TODO : mouse the keyboard events, value modification, selection handling to a TextEntryWidget
 * class. We could need it for other widgets.
 * 
 * @author fabien
 */
public class TextField extends BaseTypableWidget {

    protected String value = "";

    /**
     * Returns the value of the text field.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the text field.
     */
    public void setValue(String value) {
        if (value == null) {
            value = "";
        }
        if (!this.value.equals(value)) {
            this.value = value;
            fireRedrawEvent();
        }
    }

    /* Methods of Typable */

    @Override
    public String getTypedString() {
        return getValue();
    }

    @Override
    public void setTypedString(String value) {
        setValue(value);
    }

    /* Methods of Widget */

    @Override
    public Shape getEventArea() {
        return getAbsoluteRectangle();
    }

    @Override
    public Rectangle getDrawArea() {
        return getAbsoluteRectangle();
    }

    @Override
    public Vector getMinimumSize() {
        return new Vector(24, 24);
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        getLookAndFeel().drawTextField(context, this);
    }
}
