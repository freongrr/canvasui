package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.graphics.HorizontalAlign;
import com.github.freongrr.canvasui.graphics.VerticalAlign;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Rectangle;

/**
 * Very basic label.
 * 
 * TODO : text alignement
 * 
 * @author fabien
 */
public class Label extends BaseWidget {

    private String label;
    private HorizontalAlign horizontalAlignement = HorizontalAlign.CENTER;
    private VerticalAlign verticalAlignement = VerticalAlign.MIDDLE;
    private Margin margin = new Margin(5, 5, 2, 2);

    public Label(String label) {
        if (label == null) {
            label = "";
        }

        this.label = label;

        offset = new Vector(0, 0);
        updateSize();
    }

    public Label() {
        this("");
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        getLookAndFeel().drawLabel(context, this);
    }

    @Override
    public Rectangle getDrawArea() {
        // TODO : We should ask the LnF
        return getAbsoluteRectangle();
    }

    @Override
    public Vector getMinimumSize() {
        double minWidth = 0;
        double minHeight = 0;
        if (isAttached()) {
            GraphicContext gc = getTopLevel().getContext();
            Vector textSize = gc.getTextSize(null, label);

            minWidth = margin.left + textSize.x + margin.right;
            minHeight = margin.top + textSize.y + margin.bottom;
        }

        return new Vector(minWidth, minHeight);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        updateSize();
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public HorizontalAlign getHorizontalAlignement() {
        return horizontalAlignement;
    }

    public void setHorizontalAlignement(HorizontalAlign horizontalAlignement) {
        this.horizontalAlignement = horizontalAlignement;
    }

    public VerticalAlign getVerticalAlignement() {
        return verticalAlignement;
    }

    public void setVerticalAlignement(VerticalAlign verticalAlignement) {
        this.verticalAlignement = verticalAlignement;
    }

    protected void updateSize() {
        setSize(new Vector(0, 0));
    }
}
