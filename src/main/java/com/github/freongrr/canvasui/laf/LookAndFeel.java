package com.github.freongrr.canvasui.laf;

import java.util.HashMap;
import java.util.Map;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.ui.TextField;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.ui.Button;
import com.github.freongrr.canvasui.ui.Label;
import com.github.freongrr.canvasui.ui.Window;

/**
 * TODO : plein de choses !!!
 * 
 * @author fabien
 */
public abstract class LookAndFeel {
    public static final String SIMPLE = "simple";
    public static final String PRETTY = "pretty";
    public static final String GLOSSY = "glossy";

    public static final String DEFAULT = GLOSSY;

    private static Map<String, LookAndFeel> instances = new HashMap<String, LookAndFeel>();
    private static LookAndFeel defaultLookAndFeel;

    public static LookAndFeel get() {
	if (defaultLookAndFeel == null) {
	    defaultLookAndFeel = get(DEFAULT);
	}

	return defaultLookAndFeel;
    }

    public static LookAndFeel get(String name) {
	LookAndFeel lnf = instances.get(name);
	if (lnf == null) {
	    if (SIMPLE.equals(name)) {
		lnf = new SimpleLookAndFeel();
	    } else if (PRETTY.equals(name)) {
		lnf = new PrettyLookAndFeel();
	    } else if (GLOSSY.equals(name)) {
		lnf = new GlossyLookAndFeel();
	    } else {
		throw new RuntimeException("Unknown look and feel: " + name);
	    }
	    instances.put(name, lnf);
	}
	return lnf;
    }

    public LookAndFeel getDefaultLookAndFeel() {
	return get();
    }

    public void setDefaultLookAndFeel(LookAndFeel lnf) {
	defaultLookAndFeel = lnf;
	// TODO : Event ?
    }

    /**
     * Draws a window.
     * 
     * @param context
     *            the graphic context
     * @param window
     *            the window
     */
    public abstract void drawWindow(GraphicContext context, Window window);

    /**
     * Returns the area where the window is drawn (for invalidation purpose).
     */
    public abstract Rectangle getWindowDrawArea(Window window);

    /**
     * Returns a shape representing the window (for events).
     */
    public abstract Shape getWindowShape(Window window);

    /**
     * Returns a shape representing the title of a window.
     */
    public abstract Shape getWindowTitleShape(Window window);

    // TODO : we need multiple border zones:
    // N, S, E, W, NE, NW, SE, SW

    /**
     * Returns a shape representing the north border of a window.
     */
    public abstract Shape getWindowNorthBorderShape(Window window);

    /**
     * Returns a shape representing the south border of a window.
     */
    public abstract Shape getWindowSouthBorderShape(Window window);

    /**
     * Returns a shape representing the east border of a window.
     */
    public abstract Shape getWindowEastBorderShape(Window window);

    /**
     * Returns a shape representing the west border of a window.
     */
    public abstract Shape getWindowWestBorderShape(Window window);

    /**
     * Draws a button.
     * 
     * @param context
     *            the graphic context
     * @param button
     */
    public abstract void drawButton(GraphicContext context, Button button);

    /**
     * Draws a focus box.
     * 
     * @param context
     *            the graphic context
     * @param rectangle
     */
    public abstract void drawFocusBox(GraphicContext context,
	    Rectangle rectangle);

    /**
     * Draws a label.
     * 
     * @param context
     *            the graphic context
     * @param label
     *            a label
     */
    public abstract void drawLabel(GraphicContext context, Label label);

    /**
     * Draws a text field.
     * 
     * @param context
     *            the graphic context
     * @param textField
     *            the text field to draw
     */
    public abstract void drawTextField(GraphicContext context,
	    TextField textField);
}
