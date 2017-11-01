package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.MouseOutEvent;
import com.github.freongrr.canvasui.events.MouseOutListener;
import com.github.freongrr.canvasui.events.MouseOverEvent;
import com.github.freongrr.canvasui.events.MouseOverListener;

public abstract class BaseTypableWidget extends FormWidget implements Typable {

    protected String internalString = "";
    protected int position = 0;
    protected int start = -1;
    protected int end = -1;

    class TextCursorListener implements MouseOutListener, MouseOverListener {
        @Override
        public void onMouseOver(MouseOverEvent me) {
            if (!me.isCancelled() && isAttached()) {
                getTopLevel().getContext().setMousePointer(MousePointer.TEXT);
            }
        }

        @Override
        public void onMouseOut(MouseOutEvent me) {
            if (!me.isCancelled() && isAttached()) {
                getTopLevel().getContext().setMousePointer(MousePointer.NORMAL);
            }
        }
    }

    public BaseTypableWidget() {
        super();

        TextCursorListener l = new TextCursorListener();
        addMouseOverListener(l);
        addMouseOutListener(l);

        // Uses an external keyboard event manager
        new KeyboardEntryListener().bind(this);
    }

    @Override
    public int getCarretPosition() {
        return position;
    }

    @Override
    public void setCarretPosition(int position) {
        if (this.position != position) {
            this.position = position;
            fireRedrawEvent();
        }
    }

    @Override
    public int getSelectionStart() {
        return start;
    }

    @Override
    public void setSelectionStart(int start) {
        if (this.start != start) {
            this.start = start;
            fireRedrawEvent();
        }
    }

    @Override
    public int getSelectionEnd() {
        return end;
    }

    @Override
    public void setSelectionEnd(int end) {
        if (this.end != end) {
            this.end = end;
            fireRedrawEvent();
        }
    }

    @Override
    public void setSelection(int start, int end) {
        if (this.start != start || this.end != end) {
            this.start = start;
            this.end = end;
            fireRedrawEvent();
        }
    }
}
