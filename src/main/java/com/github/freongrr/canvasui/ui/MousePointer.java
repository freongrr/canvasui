package com.github.freongrr.canvasui.ui;

public class MousePointer {
    public static final MousePointer NORMAL = new MousePointer("standard");
    public static final MousePointer MOVE = new MousePointer("move");
    public static final MousePointer RESIZED_N = new MousePointer("resize-n");
    public static final MousePointer RESIZED_S = new MousePointer("resize-s");
    public static final MousePointer RESIZED_E = new MousePointer("resize-e");
    public static final MousePointer RESIZED_W = new MousePointer("resize-w");
    public static final MousePointer RESIZED_NE = new MousePointer("resize-ne");
    public static final MousePointer RESIZED_NW = new MousePointer("resize-nw");
    public static final MousePointer RESIZED_SE = new MousePointer("resize-se");
    public static final MousePointer RESIZED_SW = new MousePointer("resize-sw");
    public static final MousePointer TEXT = new MousePointer("text");

    private String type;

    private MousePointer(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static MousePointer getFromType(String type) {
        if (type.equals(NORMAL.getType())) {
            return NORMAL;
        } else if (type.equals(MOVE.getType())) {
            return MOVE;
        } else if (type.equals(RESIZED_E.getType())) {
            return RESIZED_E;
        } else if (type.equals(RESIZED_W.getType())) {
            return RESIZED_W;
        } else if (type.equals(RESIZED_N.getType())) {
            return RESIZED_N;
        } else if (type.equals(RESIZED_S.getType())) {
            return RESIZED_S;
        } else if (type.equals(RESIZED_NE.getType())) {
            return RESIZED_NE;
        } else if (type.equals(RESIZED_NW.getType())) {
            return RESIZED_NW;
        } else if (type.equals(RESIZED_SE.getType())) {
            return RESIZED_SE;
        } else if (type.equals(RESIZED_SW.getType())) {
            return RESIZED_SW;
        } else if (type.equals(TEXT.getType())) {
            return TEXT;
        }
        return null;
    }

    @Override
    public String toString() {
        return "MousePointer [type=" + type + "]";
    }
}
