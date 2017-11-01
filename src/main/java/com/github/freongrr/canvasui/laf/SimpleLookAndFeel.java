package com.github.freongrr.canvasui.laf;

import com.github.freongrr.canvasui.graphics.Color;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Label;
import com.github.freongrr.canvasui.ui.TextField;
import com.github.freongrr.canvasui.ui.Widget;
import com.github.freongrr.canvasui.ui.Window;
import com.github.freongrr.canvasui.graphics.Font;
import com.github.freongrr.canvasui.graphics.HorizontalAlign;
import com.github.freongrr.canvasui.graphics.VerticalAlign;
import com.github.freongrr.canvasui.graphics.WindowStyle;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.ui.Button;

public class SimpleLookAndFeel extends LookAndFeel {
    private static final int RESIZE_HANDLE_SIZE = 5;

    protected WindowStyle activeWindowStyle;
    protected WindowStyle inactiveWindowStyle;

    protected SimpleLookAndFeel() {
        // style for active windows
        activeWindowStyle = new WindowStyle();

        activeWindowStyle.setBorderWidth(1);
        activeWindowStyle.setBorderColor(new Color(0, 0, 0));

        activeWindowStyle.setColor(new Color(240, 240, 240));

        activeWindowStyle.setTitleColor(new Color(160, 160, 255));
        activeWindowStyle.setTitleFont(new Font("sans serif", 12, new Color(0, 0, 128)));
        activeWindowStyle.setTitleHAlignement(HorizontalAlign.CENTER);
        activeWindowStyle.setTitleVAlignement(VerticalAlign.MIDDLE);
        activeWindowStyle.setTitleMargin(new Margin(2));
        activeWindowStyle.setTitleHeight(24);

        activeWindowStyle.setTextFieldColor(new Color(255, 255, 255));

        // Style for inactive window
        inactiveWindowStyle = new WindowStyle(activeWindowStyle);

        inactiveWindowStyle.setBorderColor(new Color(64, 64, 64));

        inactiveWindowStyle.setTitleColor(new Color(160, 160, 160));
        inactiveWindowStyle.setTitleFont(new Font("sans", 12, new Color(64, 64, 64)));
    }

    protected WindowStyle getActiveWindowStyle() {
        return activeWindowStyle;
    }

    protected void setActiveWindowStyle(WindowStyle activeWindowStyle) {
        this.activeWindowStyle = activeWindowStyle;
    }

    protected WindowStyle getInactiveWindowStyle() {
        return inactiveWindowStyle;
    }

    protected void setInactiveWindowStyle(WindowStyle inactiveWindowStyle) {
        this.inactiveWindowStyle = inactiveWindowStyle;
    }

    protected WindowStyle getCurrentWindowStyle(Window window) {
        return window.isActive() ? getActiveWindowStyle() : getInactiveWindowStyle();
    }

    protected WindowStyle getCurrentWindowStyle(Widget widget) {
        while (widget != null) {
            if (widget instanceof Window) {
                return getCurrentWindowStyle((Window) widget);
            }
            widget = widget.getParent();
        }

        throw new NullPointerException("Cannot find parent Window");
    }

    @Override
    public void drawWindow(GraphicContext context, Window window) {
        drawWindowBorder(context, window);
        drawWindowBackground(context, window);
        drawWindowTitle(context, window);
    }

    protected void drawWindowBorder(GraphicContext context, Window window) {
        WindowStyle style = getCurrentWindowStyle(window);

        // Draw the border (if the border width is greater than 0)
        if (style.getBorderWidth() > 0) {
            context.save();

            double borderWidth = style.getBorderWidth();

            Rectangle rectangle = getWindowRectangle(window);
            rectangle = new Rectangle(rectangle, new Margin(borderWidth / 2));

            context.setFillStyle(style.getBorderColor());
            context.fillRectangle(rectangle);

            context.restore();
        }
    }

    protected void drawWindowBackground(GraphicContext context, Window window) {
        WindowStyle style = getCurrentWindowStyle(window);

        context.save();

        Rectangle rectangle = getWindowRectangle(window);
        context.setFillStyle(style.getColor());
        context.fillRectangle(rectangle);

        context.restore();
    }

    protected void drawWindowTitle(GraphicContext context, Window window) {
        WindowStyle style = getCurrentWindowStyle(window);

        Margin m = style.getTitleMargin();
        double titleHeight = style.getTitleHeight();

        Rectangle titleRectangle = getWindowTitleRectangle(window);

        // Title background
        if (style.getTitleHeight() > 0) {
            context.save();

            context.setFillStyle(style.getTitleColor());
            context.fillRectangle(titleRectangle);

            context.restore();
        }

        // Title text
        if (!"".equals(window.getTitle())) {
            context.save();

            double titleWidth = titleRectangle.width - m.left - m.right;
            double titleX = titleRectangle.left + m.left + titleWidth / 2;
            double titleY = titleRectangle.top + m.top + titleHeight / 2;
            context.setTextFont(style.getTitleFont());
            context.setTextHorizontalAlign(style.getTitleHAlignement());
            context.setTextVerticalAlign(style.getTitleVAlignement());
            context.fillText(window.getTitle(), titleX, titleY, titleWidth);

            context.restore();
        }
    }

    /**
     * Useful method to get the actual borders of the window (including the margin and the title)
     */
    public Rectangle getWindowRectangle(Window window) {
        WindowStyle style = getCurrentWindowStyle(window);
        Margin tm = style.getTitleMargin();
        double th = style.getTitleHeight();
        th = th + tm.top + tm.bottom;
        Vector p = window.getAbsolutePosition();
        Vector s = window.getSize();
        return new Rectangle(p.x, p.y - th, s.x, s.y + th);
    }

    /**
     * Useful method to get the actual borders of the title (including the margin and the title)
     */
    protected Rectangle getWindowTitleRectangle(Window window) {
        WindowStyle style = getCurrentWindowStyle(window);
        Margin m = style.getTitleMargin();
        Rectangle g = getWindowRectangle(window);
        return new Rectangle(g.getLeft() + m.left, g.getTop() + m.top, g.getWidth() - m.left
                - m.right, style.getTitleHeight());
    }

    @Override
    public Rectangle getWindowDrawArea(Window window) {
        // TOOD : add the shadow
        return getWindowRectangle(window);
    }

    @Override
    public Shape getWindowShape(Window window) {
        return getWindowRectangle(window);
    }

    @Override
    public Shape getWindowNorthBorderShape(Window window) {
        Rectangle g = getWindowRectangle(window);
        return new Rectangle(g.left, g.top, g.width, RESIZE_HANDLE_SIZE);
    }

    @Override
    public Shape getWindowSouthBorderShape(Window window) {
        Rectangle g = getWindowRectangle(window);
        return new Rectangle(g.left, g.getBottom() - RESIZE_HANDLE_SIZE, g.width,
                RESIZE_HANDLE_SIZE);
    }

    @Override
    public Shape getWindowWestBorderShape(Window window) {
        Rectangle g = getWindowRectangle(window);
        return new Rectangle(g.left, g.top, RESIZE_HANDLE_SIZE, g.height);
    }

    @Override
    public Shape getWindowEastBorderShape(Window window) {
        Rectangle g = getWindowRectangle(window);
        return new Rectangle(g.getRight() - RESIZE_HANDLE_SIZE, g.top, RESIZE_HANDLE_SIZE, g.height);
    }

    @Override
    public Shape getWindowTitleShape(Window window) {
        return getWindowTitleRectangle(window);
    }

    @Override
    public void drawButton(GraphicContext context, Button button) {
        WindowStyle style = activeWindowStyle;

        context.save();

        Vector position = button.getAbsolutePosition();
        Vector size = button.getSize();
        Rectangle rect = new Rectangle(position, size);

        // Border
        Rectangle rect2 = new Rectangle(rect, new Margin(1));
        context.setFillStyle(style.getBorderColor());
        context.fillRectangle(rect2);

        // The color depends on the state
        // TODO : make a ButtonStyle class to store all the possible colors
        if (!button.isEnabled()) {
            context.setFillStyle(new Color(128, 128, 128));
        } else if (button.isActivated()) {
            context.setFillStyle(style.getColor());
        } else if (button.isHightlighted()) {
            context.setFillStyle(new Color(255, 255, 255));
        } else {
            context.setFillStyle(style.getTitleColor());
        }

        // Background
        context.fillRectangle(rect);

        context.restore();

        if (button.isFocused()) {
            drawButtonFocusBox(context, button);
        }

        // Button text
        if (!"".equals(button.getLabel())) {
            double width = size.getX();
            double height = size.getY();

            double left = position.getX() + width / 2;
            double top = position.getY() + height / 2;

            if (button.isActivated()) {
                left++;
                top++;
            }

            context.save();

            // context.setTextFont( style.getTitleFont() );
            context.setTextHorizontalAlign(HorizontalAlign.CENTER);
            context.setTextVerticalAlign(VerticalAlign.MIDDLE);

            context.fillText(button.getLabel(), left, top, width);

            context.restore();
        }
    }

    @Override
    public void drawFocusBox(GraphicContext context, Rectangle rectangle) {
        context.save();
        context.setLineStyle(new Color(128, 128, 128, 0.5));
        context.strokeRectangle(rectangle);
        context.restore();
    }

    /**
     * Helper method to draw the focus box in a button. This is pure calculation. The drawing is
     * actually done by drawFocusBox.
     * 
     * @param context
     * @param button
     */
    protected void drawButtonFocusBox(GraphicContext context, Button button) {
        double width = button.getWidth();
        double height = button.getHeight();

        Vector position = button.getAbsolutePosition();
        double left = position.getX() + width / 2;
        double top = position.getY() + height / 2;

        if (button.isActivated()) {
            left++;
            top++;
        }

        Vector textSize = context.getTextSize(null, button.getLabel());
        Rectangle textArea = new Rectangle(left - textSize.x / 2, top - textSize.y / 2, textSize.x,
                textSize.y);
        textArea = new Rectangle(textArea, new Margin(2, 2, 0, 0));
        drawFocusBox(context, textArea);

        // There's probably something smart to do here...
        // Surface s = ((G2DContext) context).getSurface();
        // s.drawFocusRing(button.getGWTWidget(), left, top);
    }

    @Override
    public void drawLabel(GraphicContext context, Label label) {
        // Button text
        if (!"".equals(label.getLabel())) {
            Vector position = label.getAbsolutePosition();
            Vector size = label.getSize();

            Margin m = label.getMargin();

            double width = size.getX() - m.left - m.right;
            double height = size.getY() - m.top - m.bottom;

            double left = position.getX() + m.left;
            double top = position.getY() + m.top;

            HorizontalAlign hAlign = label.getHorizontalAlignement();
            VerticalAlign vAlign = label.getVerticalAlignement();

            if (hAlign == HorizontalAlign.CENTER) {
                left = left + width / 2;
            } else if (hAlign == HorizontalAlign.RIGHT) {
                left = left + width;
            }

            if (vAlign == VerticalAlign.MIDDLE) {
                top = top + height / 2;
            } else if (vAlign == VerticalAlign.BOTTOM) {
                top = top + height;
            }

            context.save();

            context.setTextHorizontalAlign(label.getHorizontalAlignement());
            context.setTextVerticalAlign(label.getVerticalAlignement());

            context.fillText(label.getLabel(), left, top, width);

            context.restore();
        }
    }

    @Override
    public void drawTextField(GraphicContext context, TextField textField) {
        // Draw the widget
        drawTextFieldBackground(context, textField);

        Rectangle rectangle = textField.getDrawArea();
        Rectangle textArea = new Rectangle(rectangle, new Margin(-2, -2, -4, -4));

        double left = textArea.getLeft();
        double top = textArea.getTop() + textArea.getHeight() / 2 + 5;

        // TODO : store somewhere
        Font textFont = new Font("sans serif", 12, new Color(0, 0, 0));

        // TODO : have a "scroll" position for the text

        // Draw the text
        String value = textField.getValue();
        if (!value.equals("")) {
            // Draw the selection background
            if (textField.getSelectionStart() != -1) {
                int selStart = textField.getSelectionStart();
                int selEnd = textField.getSelectionEnd();

                String beforeSelection = value.substring(0, selStart);
                String selected = value.substring(selStart, selEnd);

                Vector selStartSize = context.getTextSize(textFont, beforeSelection);
                Vector selectionSize = context.getTextSize(textFont, selected);

                Color backColor = new Color(128, 128, 128);
                Rectangle selRect = new Rectangle(textArea.left + selStartSize.x, textArea.top,
                        selectionSize.x, textArea.height);

                context.save();
                context.setClippingRectangle(textArea);
                context.setFillStyle(backColor);
                context.fillRectangle(selRect);
                context.restore();
            }

            context.save();
            context.setTextFont(textFont);
            context.setClippingRectangle(textArea);
            context.fillText(value, left, top);
            context.restore();
        }

        // Draw the carret
        if (textField.isFocused()) {
            context.save();
            context.setLineStyle(new Color(0, 0, 0));

            String beforeCarret = value.substring(0, textField.getCarretPosition());
            Vector textSize = context.getTextSize(textFont, beforeCarret);

            double carretPosition = textArea.left + textSize.x + 1;
            context.strokeLine(carretPosition, textArea.getTop(), carretPosition, textArea
                    .getBottom());
            context.restore();
        }
    }

    protected void drawTextFieldBackground(GraphicContext context, TextField textField) {
        WindowStyle style = getCurrentWindowStyle(textField);
        Rectangle rectangle = textField.getDrawArea();

        context.save();

        // Draw the background
        context.setFillStyle(style.getTextFieldColor());
        context.fillRectangle(rectangle);

        // Draw the border
        Rectangle border = new Rectangle(rectangle, new Margin(1, 0.5, 0, 0.5));
        context.setLineStyle(new Color(0, 0, 0));
        context.strokeRectangle(border);

        context.restore();
    }
}
