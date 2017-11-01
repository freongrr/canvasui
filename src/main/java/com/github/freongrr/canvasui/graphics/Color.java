package com.github.freongrr.canvasui.graphics;

/**
 * Implementation idenpendant structure for colors.
 * 
 * @author fabien
 */
public final class Color
{
   public int r;
   public int g;
   public int b;
   public double alpha;

   public Color()
   {
      this( 0, 0, 0, 1 );
   }

   public Color( int r, int g, int b, double alpha )
   {
      this.r = r;
      this.g = g;
      this.b = b;
      this.alpha = alpha;
   }

   public Color( int r, int g, int b )
   {
      this( r, g, b, 1 );
   }

   @Override
   public String toString()
   {
      return "Color [" + r + ", " + g + ", " + b + ", a=" + alpha + "]";
   }
}
