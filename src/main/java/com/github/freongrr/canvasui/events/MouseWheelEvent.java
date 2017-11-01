package com.github.freongrr.canvasui.events;

public class MouseWheelEvent extends MouseEvent
{
   private double delta;

   public double getDelta()
   {
      return delta;
   }

   public void setDelta( double delta )
   {
      this.delta = delta;
   }
}
