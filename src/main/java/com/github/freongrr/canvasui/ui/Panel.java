package com.github.freongrr.canvasui.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.layouts.GridLayout;
import com.github.freongrr.canvasui.events.LayoutEvent;
import com.github.freongrr.canvasui.events.LayoutListener;
import com.github.freongrr.canvasui.layouts.FixedLayout;
import com.github.freongrr.canvasui.layouts.Layout;
import com.github.freongrr.canvasui.layouts.LayoutData;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * This class defines a panel: a {@link Container} whose elements are placed using a layout.
 * 
 * 
 * @author fabien
 */
public class Panel extends Container implements Layoutable {
    protected Layout layout;
    // This is not the best way to store the layout data.
    // TODO : Should we the data in each widget?
    final protected Map<Widget, LayoutData> layoutDatas = new HashMap<Widget, LayoutData>();
    final protected List<LayoutListener> layoutListeners = new ArrayList<LayoutListener>();

    private Vector contentMinimumSize = null;

    public Panel() {
        // TODO : Comment faire ??
        offset = new Vector(0, 0);
        size = new Vector(0, 0);

        // Default layout
        layout = new FixedLayout();
    }

    // Methods of Widget and Clickable

    @Override
    public Rectangle getDrawArea() {
        return getAbsoluteRectangle();
    }

    @Override
    public Shape getEventArea() {
        return getAbsoluteRectangle();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Vector getMinimumSize() {
        if (contentMinimumSize == null) {
            updateMinimumSize();
        }
        return contentMinimumSize;
    }

    @Override
    protected void drawContent(GraphicContext context, Rectangle area) {
        // TODO : evidemment il faudrait eviter de faire ca...
        layout();

        // Debug !
        if (layout instanceof GridLayout) {
            GridLayout gl = (GridLayout) layout;
            //gl.drawBorders( context, this, layoutDatas );
        }

        super.drawContent(context, area);
    }

    // Methods of container

    /**
     * Adds a widget to the panel using the default layout data.
     * 
     * @param widget
     */
    @Override
    public void add(Widget widget) {
        this.add(widget, layout.getDefaultLayoutData(this, layoutDatas));
    }

    /**
     * Adds a widget to the panel using specific layout data.
     * 
     * @param widget
     * @param data
     */
    public void add(Widget widget, LayoutData data) {
        suspendEvents();
        try {
            super.add(widget);
            layoutDatas.put(widget, data);
            layout();
        } finally {
            resumeEvents();
        }
    }

    @Override
    public void remove(Widget widget) {
        suspendEvents();
        try {
            super.remove(widget);
            layoutDatas.remove(widget);
            layout();
        } finally {
            resumeEvents();
        }
    }

    // Methods of Panel

    /**
     * Returns the current layout.
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Set the layout.
     * 
     * @throws IllegalStateException
     *             if widgets have already been added
     * @throws NullPointerException
     *             if the layout argument is null
     */
    public void setLayout(Layout layout) {
        if (this.getWidgets().size() > 0) {
            throw new IllegalStateException("Can't change the layout of a panel with children");
        }
        if (layout == null) {
            throw new NullPointerException("Can't set a null layout");
        }

        this.layout = layout;
        layout();
    }

    /**
     * Update the minimum size
     * 
     * @return
     */
    private void updateMinimumSize() {
        contentMinimumSize = getLayout().getMinimumSize(this, layoutDatas);
    }

    @Override
    public void layout() {
        // TODO : isRendered() ?
        if (isVisible() && isAttached()) {
            suspendEvents();
            try {
                updateMinimumSize();
                layout.layout(this, layoutDatas);
            } finally {
                resumeEvents();
            }
        }
    }

    @Override
    public void addLayoutListener(LayoutListener listener) {
        layoutListeners.add(listener);
    }

    @Override
    public void removeLayoutListener(LayoutListener listener) {
        layoutListeners.remove(listener);
    }

    @Override
    public void fireLayoutEvent(LayoutEvent re) {
        for (LayoutListener l : layoutListeners) {
            if (!re.isCancelled()) {
                l.onLayout(re);
            }
        }
    }
}
