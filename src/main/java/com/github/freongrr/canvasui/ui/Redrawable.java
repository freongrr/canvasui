package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.RedrawEvent;
import com.github.freongrr.canvasui.events.RedrawListener;

public interface Redrawable
{
   public void addRedrawListener( RedrawListener listener );
   public void removeRedrawListener( RedrawListener listener );
   public void fireRedrawEvent( RedrawEvent ae );
}
