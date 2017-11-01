package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.LayoutEvent;
import com.github.freongrr.canvasui.events.LayoutListener;
import com.github.freongrr.canvasui.events.Listener;

public interface Layoutable extends Listener
{
   public void addLayoutListener( LayoutListener listener );

   public void removeLayoutListener( LayoutListener listener );

   public void fireLayoutEvent( LayoutEvent le );

   public void layout();
}
