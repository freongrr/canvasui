package com.github.freongrr.canvasui.graphics;

/**
 * Implementation independant structure for gradient offsets.
 * 
 * @author fabien
 */
public final class GradientPoint
{
   public Color color;
   public double offset;
   
   public GradientPoint( Color color, double offset )
   {
      super();
      this.color = color;
      this.offset = offset;
   }

   @Override
   public String toString()
   {
      return "GradientPoint [color=" + color + ", offset=" + offset + "]";
   }
}
