package com.github.freongrr.canvasui.layouts;

import java.util.Map;

import com.github.freongrr.canvasui.graphics.Color;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Composite;
import com.github.freongrr.canvasui.ui.Widget;

/**
 * This layout is aligns widgets on a grid.
 * 
 * <p>
 * <table border="0" cellpadding="0" cellspacing="0"style="border: black 1px solid;background-color: #FFC0C0;padding: 0 10 10 10;text-align: center;color: black">
 * <tr>
 * <td>
 * border
 * <table border="0" cellpadding="0" cellspacing="0" width="100%">
 * <tr>
 * <td style="border: black 1px solid;background-color: #C0FFFF;padding: 0 10 10 10;text-align: center;color: black">
 * margin
 * <table border="0" cellpadding="0" cellspacing="0" width="100%"style="border: black 1px solid;background-color: #C0C0FF;text-align: center;vertical-align: middle;color: black;height: 30px">
 * <tr>
 * <td>widget</td>
 * </tr>
 * </table>
 * </td>
 * <td style="border-top: black 1px solid;background-color: #FFFFC0;color: black;width:15px;">
 * &nbsp;</td>
 * <td style="border: black 1px solid;background-color: #C0FFFF;padding: 0 10 10 10;text-align: center;vertical-align: middle;color: black">
 * margin
 * <table border="0" cellpadding="0" cellspacing="0" width="100%"style="border: black 1px solid;background-color: #C0C0FF;text-align: center;vertical-align: middle;color: black;height: 30px">
 * <tr>
 * <td>widget</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td style="border-left: black 1px solid;border-right: black 1px solid;background-color: #FFFFC0;text-align: center;color: black;">
 * padding</td>
 * </tr>
 * <tr>
 * <td>
 * <table border="0" cellpadding="0" cellspacing="0" width="100%">
 * <tr>
 * <td style="border: black 1px solid;background-color: #C0FFFF;padding: 0 10 10 10;text-align: center;vertical-align: middle;color: black">
 * margin
 * <table border="0" cellpadding="0" cellspacing="0" width="100%"style="border: black 1px solid;background-color: #C0C0FF;text-align: center;vertical-align: middle;color: black;height: 30px">
 * <tr>
 * <td style="padding: 10px">widget</td>
 * </tr>
 * </table>
 * </td>
 * <td style="border-bottom: black 1px solid;background-color: #FFFFC0;text-align: center;vertical-align: middle;color: black;width: 15px;">
 * &nbsp;</td>
 * <td style="border: black 1px solid;background-color: #C0FFFF;padding: 0 10 10 10;text-align: center;vertical-align: middle;color: black">
 * margin
 * <table border="0" cellpadding="0" cellspacing="0" width="100%"style="border: black 1px solid;background-color: #C0C0FF;text-align: center;vertical-align: middle;color: black;height: 30px">
 * <tr>
 * <td style="padding: 10px">widget</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * </table>
 * </p>
 */
public class GridLayout implements Layout {
    protected int columns;
    protected int rows;
    // TODO : generalize to Panel?
    protected Margin border = new Margin();
    protected Margin margin = new Margin();
    protected Vector padding = new Vector();
    protected boolean defaultExtendX = true;
    protected boolean defaultExtendY = true;
    protected boolean defaultFillX = true;
    protected boolean defaultFillY = true;

    public GridLayout(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Margin getBorder() {
        return border;
    }

    public void setBorder(Margin border) {
        this.border = border;
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public Vector getPadding() {
        return padding;
    }

    public void setPadding(Vector padding) {
        this.padding = padding;
    }

    public boolean isDefaultExtendX() {
        return defaultExtendX;
    }

    public boolean isDefaultExtendY() {
        return defaultExtendY;
    }

    public void setDefaultExtend(boolean defaultExtend) {
        this.defaultExtendX = defaultExtend;
        this.defaultExtendY = defaultExtend;
    }

    public void setDefaultExtendX(boolean defaultExtendX) {
        this.defaultExtendX = defaultExtendX;
    }

    public void setDefaultExtendY(boolean defaultExtendY) {
        this.defaultExtendY = defaultExtendY;
    }

    public boolean isDefaultFillX() {
        return defaultFillX;
    }

    public boolean isDefaultFillY() {
        return defaultFillY;
    }

    public void setDefaultFill(boolean defaultFill) {
        this.defaultFillX = defaultFill;
        this.defaultFillY = defaultFill;
    }

    public void setDefaultFillX(boolean defaultFillX) {
        this.defaultFillX = defaultFillX;
    }

    public void setDefaultFillY(boolean defaultFillY) {
        this.defaultFillY = defaultFillY;
    }

    @Override
    public String toString() {
        return "GridLayout [columns=" + columns + ", rows=" + rows + "]";
    }

    @Override
    public LayoutData getDefaultLayoutData(Composite composite, Map<Widget, LayoutData> data) {
        Widget[][] widgets = getWidgetArray(data);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (widgets[x][y] == null) {
                    return newPositionLayoutData(x, y);
                }
            }
        }

        return newPositionLayoutData(0, 0);
    }

    /**
     * Create a new grid layout data.
     * 
     * @param column
     * @param row
     * @return
     */
    protected GridLayoutData newPositionLayoutData(int column, int row) {
        boolean extendX = isDefaultExtendX();
        boolean extendY = isDefaultExtendY();
        boolean fillX = isDefaultFillX();
        boolean fillY = isDefaultFillY();
        return new GridLayoutData(column, row, extendX, extendY, fillX, fillY);
    }

    /**
     * Return the widgets in a matrix. The first coordinate is the column.
     * 
     * @param data
     * @return
     */
    protected Widget[][] getWidgetArray(Map<Widget, LayoutData> data) {
        Widget[][] widgets = new Widget[columns][];

        for (int x = 0; x < columns; x++) {
            widgets[x] = new Widget[rows];
        }

        for (Widget w : data.keySet()) {
            if (w != null) {
                GridLayoutData d = (GridLayoutData) data.get(w);

                int row = d.getRow();
                if (row < 0) {
                    row = rows - row;
                }

                int column = d.getColumn();
                if (column < 0) {
                    column = columns - column;
                }

                // if we have already placed something here we throw an exception
                if (widgets[column][row] != null) {
                    throw new IllegalStateException("Invalid position: row:" + row + ", column:"
                            + column);
                }

                widgets[column][row] = w;
            }
        }

        return widgets;
    }

    /**
     * Lays out the container widgets. This method is actually quite simple because all the work is
     * done in getBlockSizes.
     */
    @Override
    public void layout(Composite composite, Map<Widget, LayoutData> data) {
        Widget[][] widgets = getWidgetArray(data);
        Vector[][] sizes = getBlockSizes(composite, data);

        // Lay out the blocks, starting from the top, left
        double left = border.left;
        double top = border.top;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Widget w = widgets[x][y];
                Vector s = sizes[x][y];

                if (w != null) {
                    GridLayoutData d = (GridLayoutData) data.get(w);

                    // move/resize the widget
                    double widgetLeft, widgetTop;
                    double widgetWidth, widgetHeight;

                    // if we fill horizontally
                    if (d.isFillX()) {
                        // place the widget on the left and make it as large as the
                        // block
                        widgetLeft = left + margin.left;
                        widgetWidth = s.x;
                    } else {
                        // otherwise keep its width and place it in the center
                        widgetLeft = left + margin.left + s.x / 2 - w.getSize().x / 2;
                        widgetWidth = w.getSize().x;
                    }

                    // if we fill vertically
                    if (d.isFillY()) {
                        // place the widget at the top and make it as tall as the
                        // block
                        widgetTop = top + margin.top;
                        widgetHeight = s.y;
                    } else {
                        // otherwise keep its height and place it in the middle
                        widgetTop = top + margin.top + s.y / 2 - w.getSize().y / 2;
                        widgetHeight = w.getSize().y;
                    }

                    Vector newOffset = new Vector(widgetLeft, widgetTop);
                    Vector newSize = new Vector(widgetWidth, widgetHeight);

                    w.setOffset(newOffset);
                    w.setSize(newSize);
                }

                // move to the next column
                left += s.x + margin.left + margin.right + padding.x;

                // move to the next row
                if (x == columns - 1) {
                    left = border.left;
                    top += s.y + margin.top + margin.bottom + padding.y;
                }
            } // columns
        } // rows
    }

    /**
     * Calculates the size of all the blocks in the grid. This method is really long and
     * complicated. There is probably an easier way to do that...
     * 
     * @param composite
     *            the parent container
     * @param data
     *            the layout data
     * @return a matric of vector, the first coordinate being the column
     */
    protected Vector[][] getBlockSizes(Composite composite, Map<Widget, LayoutData> data) {
        Widget[][] widgets = getWidgetArray(data);

        int fixedRows = 0;
        int fixedColumns = 0;

        // Calculate the size of the rows and columns by searching the size of the
        // largest widget in a fixed block for each row and column
        double[] columnWidth = new double[columns];
        double[] rowHeight = new double[rows];

        double[] minWidth = new double[columns];
        double[] minHeight = new double[rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                // -1 is a place holder meaning we have found no fixed block in the
                // row/column.
                columnWidth[x] = -1;
                rowHeight[y] = -1;

                Widget w = widgets[x][y];
                if (w != null) {
                    Vector size = w.getSize();
                    Vector minSize = w.getMinimumSize();

                    GridLayoutData d = (GridLayoutData) data.get(w);
                    if (d.isExtendX()) {
                        if (minSize.x > minWidth[x]) {
                            minWidth[x] = minSize.x;
                        }
                    } else {
                        if (size.x > columnWidth[x]) {
                            columnWidth[x] = size.x;
                        }
                    }

                    if (d.isExtendY()) {
                        if (minSize.y > minHeight[y]) {
                            minHeight[y] = minSize.y;
                        }
                    } else {
                        if (!d.isExtendY() && size.y > rowHeight[y]) {
                            rowHeight[y] = size.y;
                        }
                    }
                }
            }
        }

        // Adds everything to have the total fixed width and height
        double fixedWidth = 0;
        for (double w : columnWidth) {
            if (w != -1) {
                fixedColumns++;
                fixedWidth += w;
            }
        }

        double fixedHeight = 0;
        for (double h : rowHeight) {
            if (h != -1) {
                fixedRows++;
                fixedHeight += h;
            }
        }

        double extendingWidth = 0;
        double extendingHeight = 0;

        // The available width/height is the difference between the total
        // width/height and the size of all the fixed blocks.
        Vector panelSize = composite.getSize();
        
        double panelWidth = panelSize.x;

        panelWidth = panelWidth - border.left - border.right;
        panelWidth -= (columns - 1) * padding.x;
        panelWidth -= columns * (margin.left + margin.right);

        double panelHeight = panelSize.y;

        panelHeight = panelHeight - border.top - border.bottom;
        panelHeight -= (rows - 1) * padding.x;
        panelHeight -= rows * (margin.top + margin.bottom);

        boolean ok = false;
        while (columns != fixedColumns && !ok) {
            ok = true;

            extendingWidth = (panelWidth - fixedWidth) / (columns - fixedColumns);

            // Loops on the columns to see if a column requires a larger width that
            // the extendingWidth we have just computed.
            for (int x = 0; x < columns; x++) {
                double width = minWidth[x];
                if (columnWidth[x] == -1 && width > extendingWidth) {
                    // We can't use the defaut width and consider this column much
                    // like a fixed column
                    columnWidth[x] = width;
                    fixedWidth += width;
                    fixedColumns++;

                    // Loop again to calculate a new width for extending columns
                    ok = false;
                    break;
                }
            }
        }

        ok = false;
        while (rows != fixedRows && !ok) {
            ok = true;

            extendingHeight = (panelHeight - fixedHeight) / (rows - fixedRows);

            // Loops on the rows to see if a row requires a taller height that what
            // we have just computed.
            for (int x = 0; x < rows; x++) {
                double height = minHeight[x];
                if (rowHeight[x] == -1 && height > extendingHeight) {
                    // We can't use the defaut height and consider this row much like
                    // a fixed row
                    rowHeight[x] = height;
                    fixedHeight += height;
                    fixedRows++;

                    // Loop again to calculate a new width for extending columns
                    ok = false;
                    break;
                }
            }
        }

        // Sets the size on the column/rows
        for (int x = 0; x < columns; x++) {
            if (columnWidth[x] == -1) {
                columnWidth[x] = extendingWidth;
            }
        }

        for (int y = 0; y < rows; y++) {
            if (rowHeight[y] == -1) {
                rowHeight[y] = extendingHeight;
            }
        }

        // Finally we can compute the size of the widgets
        Vector[][] sizes = new Vector[columns][rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                sizes[x][y] = new Vector(columnWidth[x], rowHeight[y]);
            }
        }

        return sizes;
    }

    @Override
    public Vector getMinimumSize(Composite composite, Map<Widget, LayoutData> data) {
        Widget[][] widgets = getWidgetArray(data);

        // Calculate the minimum size of the rows and columns by comparing the
        // minimum size of the widgets each row and column
        double[] columnWidth = new double[columns];
        double[] rowHeight = new double[rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Widget w = widgets[x][y];
                if (w != null) {
                    GridLayoutData d = (GridLayoutData) data.get(w);

                    // if the widget is imposing it's size it's easy. if it expends
                    // we use the minimum size.
                    Vector size = w.getSize();
                    Vector minSize = w.getMinimumSize();

                    double widgetWidth = d.isExtendX() ? minSize.x : size.x;
                    double widgetHeight = d.isExtendY() ? minSize.y : size.y;

                    if (widgetWidth > columnWidth[x]) {
                        columnWidth[x] = widgetWidth;
                    }
                    if (widgetHeight > rowHeight[y]) {
                        rowHeight[y] = widgetHeight;
                    }
                }
            }
        }

        // Adds everything to have the minimum width and height
        double minimumWidth = 0;
        for (double w : columnWidth) {
            minimumWidth += w;
        }

        double minimumHeight = 0;
        for (double h : rowHeight) {
            minimumHeight += h;
        }

        // adds the padding, margin and border
        minimumWidth += (columns - 1) * padding.x;
        minimumWidth += columns * (margin.left + margin.right);
        minimumWidth += border.left + border.right;

        minimumHeight += (rows - 1) * padding.y;
        minimumHeight += rows * (margin.top + margin.bottom);
        minimumHeight += border.top + border.bottom;

        return new Vector(minimumWidth, minimumHeight);
    }

    // DEBUG

    public void drawBorders(GraphicContext gc, Composite composite, Map<Widget, LayoutData> data) {
        gc.save();

        // TODO : getDrawArea does not work, we may need an other method
        Rectangle geometry = new Rectangle(composite.getAbsolutePosition(), composite.getSize());

        // border
        Rectangle r = geometry;
        gc.setFillStyle(new Color(255, 0, 0));
        gc.fillRectangle(r);

        // margin
        r = new Rectangle(r, new Margin(-border.left, -border.top, -border.right, -border.bottom));
        gc.setFillStyle(new Color(0, 255, 255));
        gc.fillRectangle(r);

        // padding
        gc.setFillStyle(new Color(255, 255, 0));

        double left = geometry.left + border.left;
        double top = geometry.top + border.top;

        Vector[][] sizes = getBlockSizes(composite, data);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Vector s = sizes[x][y];

                if (x < columns - 1) {
                    double paddingLeft = left + s.x + margin.left + margin.right;

                    // vertical padding
                    r = new Rectangle(paddingLeft, top, padding.x, s.y + margin.top + margin.bottom);
                    gc.fillRectangle(r);

                    // move to the next column
                    left += s.x + margin.left + margin.right + padding.x;
                } else if (y != rows - 1) {
                    double paddingTop = top + s.y + margin.top + margin.bottom;
                    double paddingWidth = geometry.width - border.left - border.right;

                    // move to the next row
                    left = geometry.left + border.left;
                    top += s.y + margin.top + margin.bottom + padding.y;

                    // vertical padding
                    r = new Rectangle(left, paddingTop, paddingWidth, padding.y);
                    gc.fillRectangle(r);

                }
            } // columns
        } // rows

        // padding
        gc.setFillStyle(new Color(0, 128, 255));

        left = geometry.left + border.left + margin.left;
        top = geometry.top + border.top + margin.top;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Vector s = sizes[x][y];

                r = new Rectangle(left, top, s.x, s.y);
                gc.fillRectangle(r);

                if (x < columns - 1) {
                    // move to the next column
                    left += s.x + margin.left + margin.right + padding.x;
                } else if (y != rows - 1) {
                    // move to the next row
                    left = geometry.left + border.left + margin.left;
                    top += s.y + margin.top + margin.bottom + padding.y;
                }
            } // columns
        } // rows

        gc.restore();
    }
}
