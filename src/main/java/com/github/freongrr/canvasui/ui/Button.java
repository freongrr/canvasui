package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOutListener;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseOverListener;
import com.github.freongrr.canvasui.events.MouseUpListener;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseDownListener;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.shapes.Shape;

public class Button extends FormWidget implements MouseDownListener, MouseUpListener,
        MouseOverListener, MouseOutListener {

    private String label;
    private Margin margin = new Margin(5, 5, 2, 2);
    private boolean activated = false;
    private boolean hightlighted = false;

    public Button(String label) {
        if (label == null) {
            label = "";
        }
        this.label = label;

        offset = new Vector(0, 0);
        updateSize();

        addMouseDownListener(this);
        addMouseUpListener(this);
        addMouseOverListener(this);
        addMouseOutListener(this);
    }

    public Button() {
        this("");
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        getLookAndFeel().drawButton(context, this);
    }

    @Override
    public Rectangle getDrawArea() {
        // TODO : We should ask the LnF
        return getAbsoluteRectangle();
    }

    @Override
    public Shape getEventArea() {
        return getAbsoluteRectangle();
    }

    @Override
    public Vector getMinimumSize() {
        double minWidth = 0;
        double minHeight = 0;
        if (isAttached()) {
            GraphicContext gc = getTopLevel().getContext();
            Vector textSize = gc.getTextSize(null, label);

            minWidth = Math.max(textSize.x, 16);
            minHeight = Math.max(textSize.y, 16);

            minWidth += margin.left + margin.right;
            minHeight += margin.top + margin.bottom;
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

    public boolean isActivated() {
        return activated;
    }

    protected void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isHightlighted() {
        return hightlighted;
    }

    protected void setHightlighted(boolean hightlighted) {
        this.hightlighted = hightlighted;
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    protected void updateSize() {
        setSize(new Vector(0, 0));
    }

    @Override
    public void onMouseDown(MouseDownEvent me) {
        if (!me.isCancelled() && isEnabled()) {
            this.setActivated(true);
        }
    }

    @Override
    public void onMouseUp(MouseUpEvent me) {
        if (!me.isCancelled() && isEnabled()) {
            this.setActivated(false);
            fireRedrawEvent();
        }
    }

    @Override
    public void onMouseOver(MouseOverEvent me) {
        if (!me.isCancelled() && isEnabled()) {
            this.setHightlighted(true);
            fireRedrawEvent();
        }
    }

    @Override
    public void onMouseOut(MouseOutEvent me) {
        if (!me.isCancelled() && isEnabled() && isHightlighted()) {
            this.setHightlighted(false);
            fireRedrawEvent();
        }
    }
}
