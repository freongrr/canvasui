package com.github.freongrr.canvasui.shapes;

/**
 * Implementation idenpendant structure for vectors.
 * 
 * TODO : make immutable
 * 
 * @author fabien
 */
public final class Vector
{
   public final double x;
   public final double y;

   public Vector()
   {
      x = 0;
      y = 0;
   }

   public Vector( double x, double y )
   {
      this.x = x;
      this.y = y;
   }

   public Vector( Vector copy )
   {
      this( copy.x, copy.y );
   }

   public double getX()
   {
      return x;
   }

   public double getY()
   {
      return y;
   }

   @Override
   public String toString()
   {
      return "Vector [x=" + x + ", y=" + y + "]";
   }

   @Override
   public boolean equals( Object obj )
   {
      Vector v = (Vector)obj;
      return x == v.x && y == v.y;
   }

   public boolean isZero()
   {
      return x == 0 && y == 0;
   }

   public Vector substract( Vector vector )
   {
      return new Vector( x - vector.x, y - vector.y );
   }

   public Vector add( Vector vector )
   {
      return new Vector( x + vector.x, y + vector.y );
   }

   public double distanceTo( Vector vector )
   {
      double deltaX = x - vector.x;
      double deltaY = y - vector.y;
      return Math.sqrt( deltaX * deltaX + deltaY * deltaY );
   }

   /**
    * Returns a new Vector as: ( max(x1,x2), max(y1,y2) ).
    * 
    * @param v1
    * @param v2
    * @return
    */
   public static Vector getMaximum( Vector v1, Vector v2 )
   {
      return new Vector( Math.max( v1.x, v2.x ), Math.max( v1.y, v2.y ) );
   }

   /**
    * Returns a new Vector as: ( min(x1,x2), min(y1,y2) ).
    * 
    * @param v1
    * @param v2
    * @return
    */
   public static Vector getMinimum( Vector v1, Vector v2 )
   {
      return new Vector( Math.min( v1.x, v2.x ), Math.min( v1.y, v2.y ) );
   }
}
