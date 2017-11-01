package com.github.freongrr.canvasui.layouts;

public class HorizontalLayout extends GridLayout {
    public HorizontalLayout(int columns) {
        super(columns, 1);
    }

    @Override
    protected GridLayoutData newPositionLayoutData(int column, int row) {
        boolean extend = isDefaultExtendX();
        boolean fillX = isDefaultFillX();
        boolean fillY = isDefaultFillY();
        return new HorizontalLayoutData(column, extend, fillX, fillY);
    }
}
