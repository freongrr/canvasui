package com.github.freongrr.canvasui.events;

import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Clickable;

public class DragEvent extends Event
{
   private Clickable source;
   private Vector previousPosition;
   private Vector currentPosition;

   public DragEvent()
   {
   }

   public Clickable getSource()
   {
      return source;
   }

   public void setSource( Clickable source )
   {
      this.source = source;
   }

   public Vector getPreviousPosition()
   {
      return previousPosition;
   }

   public void setPreviousPosition( Vector previousPosition )
   {
      this.previousPosition = previousPosition;
   }

   public Vector getCurrentPosition()
   {
      return currentPosition;
   }

   public void setCurrentPosition( Vector currentPosition )
   {
      this.currentPosition = currentPosition;
   }

   public double getDeltaX()
   {
      return getCurrentPosition().x - getPreviousPosition().x;
   }

   public double getDeltaY()
   {
      return getCurrentPosition().y - getPreviousPosition().y;
   }

   public Vector getDelta()
   {
      return new Vector( getDeltaX(), getDeltaY() );
   }
}
