package com.github.freongrr.canvasui.laf;

import com.github.freongrr.canvasui.graphics.Color;
import com.github.freongrr.canvasui.graphics.Font;
import com.github.freongrr.canvasui.graphics.Gradient;
import com.github.freongrr.canvasui.graphics.GradientColors;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.graphics.HorizontalAlign;
import com.github.freongrr.canvasui.graphics.LinearGradient;
import com.github.freongrr.canvasui.graphics.VerticalAlign;
import com.github.freongrr.canvasui.graphics.WindowStyle;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Button;
import com.github.freongrr.canvasui.ui.TextField;
import com.github.freongrr.canvasui.ui.Window;

public class GlossyLookAndFeel extends PrettyLookAndFeel {

    protected GlossyLookAndFeel() {
        super();

        // style for active windows
        activeWindowStyle = new WindowStyle();

        // Color scheme
        // TODO : use a darker border color?
        Color borderColor = new Color(153, 187, 232);
        // Color highlightColor = new Color( 255, 255, 255 );
        Color innerColor = new Color(223, 232, 246);
        Color innerColor2 = new Color(255, 255, 255);

        Color text = new Color(32, 32, 92);
        Color itext = new Color(128, 128, 128);

        activeWindowStyle.setBorderWidth(1);
        activeWindowStyle.setBorderColor(borderColor);
        activeWindowStyle.setBorderCurve(4);

        activeWindowStyle.setShadowOffset(new Vector(2, 2));
        activeWindowStyle.setShadowBlur(8);
        activeWindowStyle.setShadowColor(new Color(0, 0, 0, 0.5));

        GradientColors innerGradient = new GradientColors();
        innerGradient.addPoint(innerColor, 0.8);
        innerGradient.addPoint(innerColor2, 1);
        activeWindowStyle.setGradient(innerGradient);
        activeWindowStyle.setColor(innerColor);

        GradientColors titleGradient = new GradientColors();
        titleGradient.addPoint(new Color(218, 231, 246), 0);
        titleGradient.addPoint(new Color(205, 222, 243), 0.42);
        titleGradient.addPoint(new Color(171, 199, 236), 0.45);
        titleGradient.addPoint(new Color(184, 207, 238), 0.48);
        titleGradient.addPoint(new Color(201, 219, 242), 1);
        activeWindowStyle.setTitleGradient(titleGradient);
        // pas vraiment la meme chose
        activeWindowStyle.setTitleColor(innerColor);

        activeWindowStyle.setTitleFont(new Font("sans serif", 12, text));
        activeWindowStyle.setTitleHAlignement(HorizontalAlign.LEFT);
        activeWindowStyle.setTitleVAlignement(VerticalAlign.MIDDLE);
        activeWindowStyle.setTitleMargin(new Margin(0));
        activeWindowStyle.setTitleHeight(24);

        GradientColors textFieldGradient = new GradientColors();
        textFieldGradient.addPoint(new Color(200, 200, 230), 0);
        textFieldGradient.addPoint(new Color(255, 255, 255), 0.5);
        activeWindowStyle.setTextFieldGradient(textFieldGradient);

        // Style for inactive window
        inactiveWindowStyle = new WindowStyle(activeWindowStyle);
//        inactiveWindowStyle.setShadowColor(new Color(0, 0, 0, 0.25));
        inactiveWindowStyle.setTitleFont(new Font("sans serif", 12, itext));
    }

    protected void drawWindowBorder(GraphicContext context, Window window) {
    }

    protected void drawWindowBackground(GraphicContext context, Window window) {
        WindowStyle style = getCurrentWindowStyle(window);

        Color borderColor = style.getColor();
        GradientColors innerGradient = style.getGradient();
        GradientColors titleGradient = style.getTitleGradient();

        // TODO : mettre dans WindowStyle?
        Color highlightColor = new Color(255, 255, 255);
        Color titleHightlightColor = new Color(238, 244, 250);

        Rectangle windowRect = getWindowRectangle(window);
        Rectangle titleRect = getWindowTitleRectangle(window);

        context.save();

        // Shadow
        if (style.getShadowOffset() != null && !style.getShadowOffset().isZero()) {
            context.setShadowOffset(style.getShadowOffset());
            context.setShadowBlur(style.getShadowBlur());
            context.setShadowColor(style.getShadowColor());
        }

        // Border

        Rectangle br = new Rectangle(windowRect, new Margin(1));
        context.setFillStyle(borderColor);
        context.fillRoundRectangle(br, 4);
        context.restore();

        // Border hightlight

        context.save();
        context.setFillStyle(highlightColor);
        context.fillRoundRectangle(windowRect, 2);
        context.restore();

        // Inner color

        context.save();
        Rectangle ir = new Rectangle(windowRect, new Margin(-1));
        // context.setFillStyle( innerColor );
        Vector start = new Vector(0, window.getTop());
        Vector end = new Vector(0, window.getBottom());
        LinearGradient grad = new LinearGradient(innerGradient, start, end);
        context.setFillStyle(grad);
        context.fillRectangle(ir);
        context.restore();

        // Title

        context.save();
        context.setFillStyle(titleHightlightColor);
        context.fillRectangle(new Rectangle(titleRect));
        context.restore();

        context.save();
        context.setLineStyle(borderColor);
        context.strokeLine(window.getLeft(), window.getTop() - 1, window.getRight(), window
                .getTop() - 1);
        context.restore();

        context.save();
        Vector titleGradientTop = new Vector(0, titleRect.getTop() + 1);
        Vector titleGradientBottom = new Vector(0, titleRect.getBottom() + 1);
        Gradient titleGrad = new LinearGradient(titleGradient, titleGradientTop,
                titleGradientBottom);
        Rectangle innerTitleRectangle = new Rectangle(titleRect, new Margin(-1));
        context.setFillStyle(titleGrad);
        context.fillRectangle(innerTitleRectangle);
        context.restore();
    }

    protected void drawWindowTitle(GraphicContext context, Window window) {
        WindowStyle style = getCurrentWindowStyle(window);
        Rectangle titleRect = getWindowTitleRectangle(window);

        // Title text
        if (!"".equals(window.getTitle())) {
            context.save();

            double titleWidth = titleRect.getWidth();
            double titleX = titleRect.getLeft() + 10; // TODO
            double titleY = titleRect.getTop() + style.getTitleHeight() / 2;
            context.setTextFont(style.getTitleFont());
            context.setTextHorizontalAlign(style.getTitleHAlignement());
            context.setTextVerticalAlign(style.getTitleVAlignement());
            context.fillText(window.getTitle(), titleX, titleY, titleWidth);

            context.restore();
        }
    }

    @Override
    public void drawButton(GraphicContext context, Button button) {
        WindowStyle style = activeWindowStyle;

        // TODO : make a ButtonStyle class to store all the possible colors
        Color borderColor = style.getColor();
        Color highlightColor = new Color(238, 244, 250);

        GradientColors gradient = new GradientColors();

        // The color depends on the state
        if (!button.isEnabled()) {
            gradient.addPoint(new Color(228, 228, 228), 0);
            gradient.addPoint(new Color(215, 215, 215), 0.42);
            gradient.addPoint(new Color(181, 181, 181), 0.45);
            gradient.addPoint(new Color(194, 194, 194), 0.48);
            gradient.addPoint(new Color(211, 211, 211), 1);
        } else if (button.isActivated()) {
            gradient.addPoint(new Color(238, 251, 255), 0);
            gradient.addPoint(new Color(225, 242, 255), 0.42);
            gradient.addPoint(new Color(191, 219, 256), 0.45);
            gradient.addPoint(new Color(204, 227, 258), 0.48);
            gradient.addPoint(new Color(221, 239, 255), 1);
        } else if (button.isHightlighted()) {
            gradient.addPoint(new Color(238, 251, 255), 0);
            gradient.addPoint(new Color(225, 242, 255), 0.42);
            gradient.addPoint(new Color(191, 219, 256), 0.45);
            gradient.addPoint(new Color(204, 227, 258), 0.48);
            gradient.addPoint(new Color(221, 239, 255), 1);
        } else {
            gradient.addPoint(new Color(218, 231, 246), 0);
            gradient.addPoint(new Color(205, 222, 243), 0.42);
            gradient.addPoint(new Color(171, 199, 236), 0.45);
            gradient.addPoint(new Color(184, 207, 238), 0.48);
            gradient.addPoint(new Color(201, 219, 242), 1);
        }

        Vector position = button.getAbsolutePosition();
        Vector size = button.getSize();
        Rectangle buttonRect = new Rectangle(position, size);

        // Border

        context.save();
        Rectangle br = new Rectangle(buttonRect, new Margin(1));
        context.setFillStyle(borderColor);
        context.fillRoundRectangle(br, 4);
        context.restore();

        // Border hightlight

        context.save();
        context.setFillStyle(highlightColor);
        context.fillRoundRectangle(buttonRect, 2);
        context.restore();

        // Inner gradient

        context.save();
        Vector gradientTop = new Vector(0, buttonRect.getTop() + 1);
        Vector gradientBottom = new Vector(0, buttonRect.getBottom() + 1);
        Gradient lg = new LinearGradient(gradient, gradientTop, gradientBottom);
        br = new Rectangle(buttonRect, new Margin(-1));
        context.setFillStyle(lg);
        context.fillRectangle(br);
        context.restore();

        if (button.isFocused()) {
            drawButtonFocusBox(context, button);
        }

        // Button text
        if (!"".equals(button.getLabel())) {
            context.save();

            double width = size.getX();
            double height = size.getY();

            double left = position.getX() + width / 2;
            double top = position.getY() + height / 2;

            if (button.isActivated()) {
                left++;
                top++;
            }

            // context.setTextFont( style.getTitleFont() );
            context.setTextHorizontalAlign(HorizontalAlign.CENTER);
            context.setTextVerticalAlign(VerticalAlign.MIDDLE);

            context.fillText(button.getLabel(), left, top, width);

            context.restore();
        }
    }

    @Override
    protected void drawTextFieldBackground(GraphicContext context, TextField textField) {
        WindowStyle style = getCurrentWindowStyle(textField);
        Rectangle rectangle = textField.getDrawArea();

        context.save();

        // Draw the background
        if (style.getTextFieldGradient() != null) {
            GradientColors grad = style.getTextFieldGradient();
            Vector start = new Vector(0, rectangle.getTop());
            Vector end = new Vector(0, rectangle.getBottom());
            context.setFillStyle(new LinearGradient(grad, start, end));
        } else {
            context.setFillStyle(style.getTextFieldColor());
        }
        context.fillRectangle(rectangle);

        // Draw the border
        Rectangle border = new Rectangle(rectangle, new Margin(1, 0.5, 0, 0.5));
        context.setLineStyle(new Color(0, 0, 0));
        context.strokeRectangle(border);

        context.restore();
    }
}
