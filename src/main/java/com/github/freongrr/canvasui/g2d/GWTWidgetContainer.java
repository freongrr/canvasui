package com.github.freongrr.canvasui.g2d;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.github.freongrr.canvasui.shapes.Shape;
import com.github.freongrr.canvasui.ui.FocusableWidget;

public class GWTWidgetContainer extends FocusableWidget {

    private Widget gwtWidget;
    private PopupPanel gwtWindow;

    public GWTWidgetContainer() {
        gwtWindow = new PopupPanel();
        gwtWindow.setStyleName(null);
    }

    public Widget getWidget() {
        return gwtWidget;
    }

    public void setWidget(Widget gwtWidget) {
        this.gwtWidget = gwtWidget;
        gwtWindow.setWidget(gwtWidget);
    }

    public PopupPanel getWindow() {
        return gwtWindow;
    }

    @Override
    public void draw(GraphicContext context, Rectangle area) {
        if (gwtWidget != null) {
            Vector positon = getAbsolutePosition();
            Vector size = getSize();

            UIObject surface = ((G2DContext) getTopLevel().getContext()).getSurface();
            int left = surface.getAbsoluteLeft() + (int) positon.x + 200;
            int top = surface.getAbsoluteTop() + (int) positon.y;

            gwtWindow.setPopupPosition(left, top);
            gwtWindow.setPixelSize((int) size.x, (int) size.y);

            gwtWidget.setPixelSize((int) size.x, (int) size.y);

            gwtWindow.show();
        }
    }

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
}
