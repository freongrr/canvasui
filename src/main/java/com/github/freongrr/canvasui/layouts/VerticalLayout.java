package com.github.freongrr.canvasui.layouts;

public class VerticalLayout extends GridLayout {
    public VerticalLayout(int rows) {
        super(1, rows);
    }

    @Override
    protected GridLayoutData newPositionLayoutData(int column, int row) {
        boolean extend = isDefaultExtendY();
        boolean fillX = isDefaultFillX();
        boolean fillY = isDefaultFillY();
        return new VerticalLayoutData(row, extend, fillX, fillY);
    }
}
