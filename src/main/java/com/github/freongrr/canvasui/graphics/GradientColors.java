package com.github.freongrr.canvasui.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation idenpendant structure for gradients.
 * 
 * @author fabien
 */
public class GradientColors
{
   public List<GradientPoint> points;

   public GradientColors()
   {
      points = new ArrayList<GradientPoint>();
   }

   public void addPoint( GradientPoint point )
   {
      points.add( point );
   }

   public void addPoint( Color color, double offset )
   {
      points.add( new GradientPoint( color, offset ) );
   }

   @Override
   public String toString()
   {
      return "GradientColors [points=" + points + "]";
   }
}
