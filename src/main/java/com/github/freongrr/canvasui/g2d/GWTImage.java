package com.github.freongrr.canvasui.g2d;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Vector;
import gwt.g2d.client.graphics.Surface;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.ui.BaseWidget;

public class GWTImage extends BaseWidget {

    protected String source;
    protected Image image;

    public GWTImage() {
        image = new Image();
    }

    public GWTImage(String source) {
        this();
        setSource(source);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        image.setUrl(source);
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        Surface s = ((G2DContext) context).getSurface();

        Vector position = getAbsolutePosition();

        s.drawImage(ImageElement.as(image.getElement()), position.x, position.y);

        // Vector size = getSize();
        // gwt.g2d.client.math.Rectangle rect = new gwt.g2d.client.math.Rectangle(position.x,
        // position.y, size.x, size.y);
        //
        // s.drawImage(ImageElement.as(image.getElement()), rect);
    }

    @Override
    public Rectangle getDrawArea() {
        return getAbsoluteRectangle();
    }

}
