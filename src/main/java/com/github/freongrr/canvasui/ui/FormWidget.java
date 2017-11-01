package com.github.freongrr.canvasui.ui;

/**
 * This class represents a widget used in a form.
 * 
 * TODO : have a Form class? TODO : maybe we should name it "Disablable Widget"?
 * 
 * @author fabien
 */
public abstract class FormWidget extends FocusableWidget {

    private boolean enabled = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            fireRedrawEvent();
        }
    }
}
