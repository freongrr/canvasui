package com.github.freongrr.canvasui.shapes;

/**
 * Holder class for widget magins.
 * 
 * @author fabien
 */
public final class Margin
{
   public double left = 0;
   public double right = 0;
   public double top = 0;
   public double bottom = 0;

   public Margin()
   {
   }

   public Margin( double left, double right, double top, double bottom )
   {
      this.left = left;
      this.right = right;
      this.top = top;
      this.bottom = bottom;
   }

   public Margin( double n )
   {
      this( n, n, n, n );
   }

   @Override
   public String toString()
   {
      return "Margin [left=" + left + ", right=" + right + ", top=" + top +
             ", bottom=" + bottom + "]";
   }

   public Margin invert()
   {
      return new Margin( -left, -right, -top, -bottom );
   }
}
