package com.github.freongrr.canvasui.shapes;

import java.util.ArrayList;

public class CompositeShape implements Shape
{
   private ArrayList<Shape> shapes;

   public CompositeShape()
   {
      shapes = new ArrayList<Shape>();
   }
   
   public ArrayList<Shape> getShapes()
   {
      return shapes;
   }

   public void setShapes( ArrayList<Shape> shapes )
   {
      this.shapes = shapes;
   }

   public void add( Shape shape )
   {
      this.shapes.add( shape );
   }

   @Override
   public boolean contains( Vector coordinate )
   {
      for( Shape s : getShapes() )
      {
         if( s.contains( coordinate ) )
         {
            return true;
         }
      }
      return false;
   }

}
