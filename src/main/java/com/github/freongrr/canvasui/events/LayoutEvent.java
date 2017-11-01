package com.github.freongrr.canvasui.events;

import com.github.freongrr.canvasui.ui.Layoutable;

public class LayoutEvent extends Event
{
   private Layoutable source;

   public LayoutEvent( Layoutable source )
   {
      this.source = source;
   }

   public Layoutable getSource()
   {
      return source;
   }

   public void setSource( Layoutable source )
   {
      this.source = source;
   }
}
