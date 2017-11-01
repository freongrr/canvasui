package com.github.freongrr.canvasui.shapes;

/**
 * Implementation idenpendant structure for circles.
 * 
 * @author fabien
 */
public class Circle implements Shape
{
   public Vector position = new Vector();
   public double radius;

   public Circle( Vector position, double radius )
   {
      super();
      this.position = position;
      this.radius = radius;
   }

   @Override
   public String toString()
   {
      return "Circle [position=" + position + ", radius=" + radius + "]";
   }

   @Override
   public boolean contains( Vector coordinate )
   {
      return position.distanceTo( coordinate ) < radius;
   }
}
