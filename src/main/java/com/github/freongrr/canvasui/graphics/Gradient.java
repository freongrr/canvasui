package com.github.freongrr.canvasui.graphics;

public abstract class Gradient
{
   public GradientColors definition;

   public Gradient()
   {
      definition = new GradientColors();
   }

   public Gradient( GradientColors copy )
   {
      definition = copy;
   }
}
