package com.github.freongrr.canvasui.graphics;

import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * Needed properties to draw a window.
 * 
 * @author fabien
 */
public class WindowStyle {
    private double borderWidth;
    private double borderCurve;
    private Color borderColor;

    private Vector shadowOffset;
    private double shadowBlur;
    private Color shadowColor;

    private Color color;
    private GradientColors gradient;

    private Color titleColor;
    private GradientColors titleGradient;

    private Margin titleMargin;
    private double titleHeight;

    private Font titleFont;
    private HorizontalAlign titleHAlignement;
    private VerticalAlign titleVAlignement;

    private Color textFieldColor;
    private GradientColors textFieldGradient;

    public WindowStyle() {
    }

    public WindowStyle(WindowStyle ws) {
	this.borderWidth = ws.borderWidth;
	this.borderCurve = ws.borderCurve;
	this.borderColor = ws.borderColor;

	this.shadowOffset = ws.shadowOffset;
	this.shadowBlur = ws.shadowBlur;
	this.shadowColor = ws.shadowColor;

	this.color = ws.color;
	this.gradient = ws.gradient;

	this.titleColor = ws.titleColor;
	this.titleGradient = ws.titleGradient;

	this.titleMargin = ws.titleMargin;
	this.titleHeight = ws.titleHeight;

	this.titleFont = ws.titleFont;
	this.titleHAlignement = ws.titleHAlignement;
	this.titleVAlignement = ws.titleVAlignement;

	this.textFieldColor = ws.textFieldColor;
	this.textFieldGradient = ws.textFieldGradient;
    }

    public double getBorderWidth() {
	return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
	this.borderWidth = borderWidth;
    }

    public double getBorderCurve() {
	return borderCurve;
    }

    public void setBorderCurve(double borderCurve) {
	this.borderCurve = borderCurve;
    }

    public Color getBorderColor() {
	return borderColor;
    }

    public void setBorderColor(Color borderColor) {
	this.borderColor = borderColor;
    }

    public Vector getShadowOffset() {
	return shadowOffset;
    }

    public void setShadowOffset(Vector shadowOffset) {
	this.shadowOffset = shadowOffset;
    }

    public double getShadowBlur() {
	return shadowBlur;
    }

    public void setShadowBlur(double shadowBlur) {
	this.shadowBlur = shadowBlur;
    }

    public Color getShadowColor() {
	return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
	this.shadowColor = shadowColor;
    }

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    public GradientColors getGradient() {
	return gradient;
    }

    public void setGradient(GradientColors gradient) {
	this.gradient = gradient;
    }

    public Color getTitleColor() {
	return titleColor;
    }

    public void setTitleColor(Color titleColor) {
	this.titleColor = titleColor;
    }

    public GradientColors getTitleGradient() {
	return titleGradient;
    }

    public void setTitleGradient(GradientColors titleGradient) {
	this.titleGradient = titleGradient;
    }

    public Margin getTitleMargin() {
	return titleMargin;
    }

    public void setTitleMargin(Margin titleMargin) {
	this.titleMargin = titleMargin;
    }

    public double getTitleHeight() {
	return titleHeight;
    }

    public void setTitleHeight(double titleHeight) {
	this.titleHeight = titleHeight;
    }

    public Font getTitleFont() {
	return titleFont;
    }

    public void setTitleFont(Font titleFont) {
	this.titleFont = titleFont;
    }

    public HorizontalAlign getTitleHAlignement() {
	return titleHAlignement;
    }

    public void setTitleHAlignement(HorizontalAlign titleHAlignement) {
	this.titleHAlignement = titleHAlignement;
    }

    public VerticalAlign getTitleVAlignement() {
	return titleVAlignement;
    }

    public void setTitleVAlignement(VerticalAlign titleVAlignement) {
	this.titleVAlignement = titleVAlignement;
    }

    public Color getTextFieldColor() {
	return textFieldColor;
    }

    public void setTextFieldColor(Color textFieldColor) {
	this.textFieldColor = textFieldColor;
    }

    public GradientColors getTextFieldGradient() {
	return textFieldGradient;
    }

    public void setTextFieldGradient(GradientColors textFieldGradient) {
	this.textFieldGradient = textFieldGradient;
    }
}
