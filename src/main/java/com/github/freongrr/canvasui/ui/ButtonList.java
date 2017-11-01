package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.layouts.HorizontalLayout;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * A simple button list
 * 
 * @author fabien
 */
public class ButtonList extends Panel {

    public ButtonList() {
        HorizontalLayout layout = new HorizontalLayout(1);
        layout.setDefaultExtend(true);
        layout.setDefaultFillX(true);
        layout.setDefaultFillY(false);
        layout.setBorder(new Margin(5));
        layout.setPadding(new Vector(5, 0));
        setLayout(layout);

        setSize(new Vector(0, 24));

        super.add(new Label());
    }

    @Override
    public void add(Widget widget) {
        assert widget instanceof Button;

        HorizontalLayout layout = ((HorizontalLayout) getLayout());
        int position = layout.getColumns();
        layout.setColumns(position + 1);

        super.add(widget);
    }
}
