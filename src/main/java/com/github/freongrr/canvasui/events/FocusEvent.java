package com.github.freongrr.canvasui.events;

import com.github.freongrr.canvasui.ui.Focusable;

public class FocusEvent extends Event {
    private Focusable focusedWidget;

    public FocusEvent() {
    }

    public FocusEvent(Focusable focusedWidget) {
        this.focusedWidget = focusedWidget;
    }

    public Focusable getFocusedWidget() {
        return focusedWidget;
    }

    public void setFocusedWidget(Focusable focusedWidget) {
        this.focusedWidget = focusedWidget;
    }

}
