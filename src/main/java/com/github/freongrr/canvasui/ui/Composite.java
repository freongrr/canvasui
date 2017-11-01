package com.github.freongrr.canvasui.ui;

import java.util.List;

/**
 * This widget is composed of other widgets.
 * 
 * @author fabien
 */
public interface Composite extends Widget {
    /**
     * Returns the children composing the widget.
     * 
     * @return a list of widgets
     */
    public List<Widget> getWidgets();

    /**
     * Adds a widget to the composition.
     * 
     * @param widget
     *            a widget
     */
    public void add(Widget widget);

    /**
     * Removes a widget from the composition.
     * 
     * @param widget
     *            a widget
     */
    public void remove(Widget widget);

    /**
     * Removes all the widgets from the composition.
     * 
     * @param widget
     *            a widget
     */
    public void removeAll();
}
