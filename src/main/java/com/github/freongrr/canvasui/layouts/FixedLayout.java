package com.github.freongrr.canvasui.layouts;

import java.util.Map;

import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Composite;
import com.github.freongrr.canvasui.ui.Widget;

/**
 * Implementation of {@link Layout} using fixed position.
 * 
 * @author fabien
 */
public class FixedLayout implements Layout {
    @Override
    public LayoutData getDefaultLayoutData(Composite composite, Map<Widget, LayoutData> data) {
        return null;
    }

    @Override
    public void layout(Composite composite, Map<Widget, LayoutData> data) {
        // no need to fire a redraw event
        for (Widget w : data.keySet()) {
            Vector size = w.getSize();
            Vector min = w.getMinimumSize();
            double minWidth = size.x;
            double minHeight = size.y;
            if (size.x < min.x) {
                minWidth = min.x;
            }
            if (size.y < min.y) {
                minHeight = min.y;
            }
            w.setSize(new Vector(minWidth, minHeight));
        }
    }

    @Override
    public Vector getMinimumSize(Composite composite, Map<Widget, LayoutData> data) {
        return new Vector();
    }
}
