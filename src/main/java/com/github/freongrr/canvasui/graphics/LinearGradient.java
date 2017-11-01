package com.github.freongrr.canvasui.graphics;

import com.github.freongrr.canvasui.shapes.Vector;

/**
 * Actual implementation of a gradient
 * 
 * @author fabien
 */
public class LinearGradient extends Gradient
{
   public Vector start;
   public Vector end;

   public LinearGradient()
   {
      super();
      this.start = new Vector();
      this.end = new Vector();
   }

   public LinearGradient( Vector start, Vector end )
   {
      super();
      this.start = new Vector();
      this.end = new Vector();
   }

   public LinearGradient( GradientColors copy, Vector start, Vector end )
   {
      super( copy );
      this.start = start;
      this.end = end;
   }

   @Override
   public String toString()
   {
      return "LinearGradient [start=" + start + ", end=" + end +
             ", definition=" + definition + "]";
   }
}
