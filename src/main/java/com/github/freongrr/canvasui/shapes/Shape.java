package com.github.freongrr.canvasui.shapes;

public interface Shape
{
   /**
    * Returns true if the shape contains the coordinates passed in parameter.
    * 
    * @param coordinate
    * @return
    */
   public boolean contains( Vector coordinate );
}
