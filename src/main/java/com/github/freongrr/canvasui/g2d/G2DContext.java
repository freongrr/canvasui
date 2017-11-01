package com.github.freongrr.canvasui.g2d;

import com.github.freongrr.canvasui.graphics.Color;
import com.github.freongrr.canvasui.graphics.Gradient;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.graphics.LinearGradient;
import com.github.freongrr.canvasui.shapes.Circle;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.MousePointer;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.TextAlign;
import gwt.g2d.client.graphics.TextBaseline;
import gwt.g2d.client.graphics.TextMeasurer;
import gwt.g2d.client.graphics.shapes.CircleShape;
import gwt.g2d.client.graphics.shapes.Shape;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.github.freongrr.canvasui.graphics.Font;
import com.github.freongrr.canvasui.graphics.GradientPoint;
import com.github.freongrr.canvasui.graphics.HorizontalAlign;
import com.github.freongrr.canvasui.graphics.VerticalAlign;

/**
 * Implementation of {@link GraphicContext} using the gwt-2d library.
 * 
 * @author fabien
 */
public class G2DContext implements GraphicContext {
    private Surface surface;

    public G2DContext(Surface surface) {
        this.surface = surface;
    }

    public Surface getSurface() {
        return surface;
    }

    /* Convertion methods */

    protected static gwt.g2d.client.graphics.Color convert(Color c) {
        return new gwt.g2d.client.graphics.Color(c.r, c.g, c.b, c.alpha);
    }

    protected static gwt.g2d.client.graphics.Gradient convert(Gradient g) {
        if (g instanceof LinearGradient) {
            return convert((LinearGradient) g);
        }
        throw new IllegalArgumentException("Unexpected gradient " + g);
    }

    protected static gwt.g2d.client.graphics.LinearGradient convert(LinearGradient g) {
        Vector2 start = convert(g.start);
        Vector2 end = convert(g.end);
        gwt.g2d.client.graphics.LinearGradient gradient = new gwt.g2d.client.graphics.LinearGradient(
                start, end);
        for (GradientPoint p : g.definition.points) {
            gradient.addColorStop(p.offset, convert(p.color));
        }
        return gradient;
    }

    protected static gwt.g2d.client.math.Vector2 convert(Vector vector) {
        return new gwt.g2d.client.math.Vector2(vector.x, vector.y);
    }

    protected static gwt.g2d.client.math.Rectangle convert(Rectangle rect) {
        return new gwt.g2d.client.math.Rectangle(rect.left, rect.top, rect.width, rect.height);
    }

    protected static gwt.g2d.client.math.Circle convert(Circle c) {
        return new gwt.g2d.client.math.Circle(convert(c.position), c.radius);
    }

    protected static String convert(Font f) {
        return f.family + " " + f.size + "pt";
    }

    protected TextAlign convert(HorizontalAlign align) {
        if (align == HorizontalAlign.LEFT) {
            return TextAlign.LEFT;
        } else if (align == HorizontalAlign.CENTER) {
            return TextAlign.CENTER;
        } else if (align == HorizontalAlign.RIGHT) {
            return TextAlign.RIGHT;
        }
        throw new IllegalArgumentException("Unexpected alignement " + align);
    }

    protected TextBaseline convert(VerticalAlign align) {
        if (align == VerticalAlign.TOP) {
            return TextBaseline.TOP;
        } else if (align == VerticalAlign.MIDDLE) {
            return TextBaseline.MIDDLE;
        } else if (align == VerticalAlign.BOTTOM) {
            return TextBaseline.BOTTOM;
        }
        throw new IllegalArgumentException("Unexpected alignement " + align);
    }

    // Render methods

    @Override
    public void save() {
        surface.save();
    }

    @Override
    public void restore() {
        surface.restore();
    }

    @Override
    public void setLineStyle(Color color) {
        surface.setStrokeStyle(convert(color));
    }

    @Override
    public void setLineStyle(Gradient gradient) {
        surface.setStrokeStyle(convert(gradient));
    }

    @Override
    public void setLineWidth(double width) {
        surface.setLineWidth(width);
    }

    @Override
    public void setFillStyle(Color color) {
        surface.setFillStyle(convert(color));
    }

    @Override
    public void setFillStyle(Gradient gradient) {
        surface.setFillStyle(convert(gradient));
    }

    @Override
    public void setTextFont(Font font) {
        surface.setFont(convert(font));
        surface.setFillStyle(convert(font.color));
        surface.setStrokeStyle(convert(font.color));
    }

    @Override
    public void setTextHorizontalAlign(HorizontalAlign align) {
        surface.setTextAlign(convert(align));
    }

    @Override
    public void setTextVerticalAlign(VerticalAlign align) {
        surface.setTextBaseline(convert(align));
    }

    @Override
    public void setShadowColor(Color color) {
        surface.setShadowColor(convert(color));
    }

    @Override
    public void setShadowOffset(Vector offset) {
        surface.setShadowOffset(convert(offset));
    }

    @Override
    public void setShadowBlur(double blur) {
        surface.setShadowBlur(blur);
    }

    @Override
    public void fillCircle(Circle circle) {
        surface.fillShape(new CircleShape(convert(circle)));
    }

    @Override
    public void strokeCircle(Circle circle) {
        surface.strokeShape(new CircleShape(convert(circle)));
    }

    @Override
    public void fillRectangle(Rectangle rectangle) {
        surface.fillRectangle(convert(rectangle));
    }

    @Override
    public void strokeRectangle(Rectangle rectangle) {
        surface.strokeRectangle(convert(rectangle));
    }

    protected Shape getRoundRectangle(Rectangle rectangle, double topcurve, double bottomcurve) {
        ShapeBuilder builder = new ShapeBuilder();

        double left = rectangle.getLeft();
        double right = rectangle.getRight();
        double top = rectangle.getTop();
        double bottom = rectangle.getBottom();

        builder.drawLineSegment(left + topcurve, top, right - topcurve, top);
        builder.drawQuadraticCurveTo(right, top, right, top + topcurve);
        builder.drawLineTo(right, bottom - bottomcurve);
        builder.drawQuadraticCurveTo(right, bottom, right - bottomcurve, bottom);
        builder.drawLineTo(left + bottomcurve, bottom);
        builder.drawQuadraticCurveTo(left, bottom, left, bottom - bottomcurve);
        builder.drawLineTo(left, top + topcurve);
        builder.drawQuadraticCurveTo(left, top, left + topcurve, top);

        return builder.build();
    }

    @Override
    public void fillRoundRectangle(Rectangle rectangle, double curve) {
        fillRoundRectangle(rectangle, curve, curve);
    }

    @Override
    public void strokeRoundRectangle(Rectangle rectangle, double curve) {
        strokeRoundRectangle(rectangle, curve, curve);
    }

    @Override
    public void fillRoundRectangle(Rectangle rectangle, double topcurve, double bottomcurve) {
        surface.fillShape(getRoundRectangle(rectangle, topcurve, bottomcurve));
    }

    @Override
    public void strokeRoundRectangle(Rectangle rectangle, double topcurve, double bottomcurve) {
        surface.strokeShape(getRoundRectangle(rectangle, topcurve, bottomcurve));
    }

    @Override
    public void fillText(String text, double x, double y) {
        surface.fillText(text, x, y);
    }

    @Override
    public void strokeText(String text, double x, double y) {
        surface.strokeText(text, x, y);
    }

    @Override
    public void fillText(String text, double x, double y, double maxWidth) {
        surface.fillText(text, x, y, maxWidth);
    }

    @Override
    public void strokeText(String text, double x, double y, double maxWidth) {
        surface.strokeText(text, x, y, maxWidth);
    }

    @Override
    public void strokeLine(double fromX, double fromY, double toX, double toY) {
        ShapeBuilder builder = new ShapeBuilder();
        builder.drawLineSegment(fromX, fromY, toX, toY);
        Shape shape = builder.build();
        surface.strokeShape(shape);
    }

    @Override
    public MousePointer getMousePointer() {
        String style = surface.getStyleName();
        if (style.startsWith("cursor-")) {
            String type = style.substring(7);
            MousePointer p = MousePointer.getFromType(type);
            if (p != null) {
                return p;
            }
        }
        return MousePointer.NORMAL;
    }

    @Override
    public void setMousePointer(MousePointer pointer) {
        if (pointer == null) {
            setMousePointer(MousePointer.NORMAL);
        } else {
            surface.setStyleName("cursor-" + pointer.getType());
        }
    }

    @Override
    public Vector getTextSize(Font font, String text) {
        TextMeasurer measurer = TextMeasurer.DEFAULT_TEXT_MEASURER;

        surface.save();
        if (font != null) {
            surface.setFont(convert(font));
        }
        double height = measurer.measureTextHeight(surface, text);
        double width = measurer.measureTextWidth(surface, text);
        surface.restore();

        return new Vector(width, height);
    }

    @Override
    public void setClippingRectangle(Rectangle rectangle) {
        surface.clipRectangle(convert(rectangle));
    }
}
