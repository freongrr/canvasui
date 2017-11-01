package com.github.freongrr.canvasui.events;

import com.github.freongrr.canvasui.shapes.Vector;

/**
 * Base mouse event.
 * 
 * @author fabien
 */
public class MouseEvent extends Event
{
   private int x, y;
   private boolean altKey;
   private boolean shiftKey;
   private boolean controlKey;
   private boolean metaKey;

   public MouseEvent()
   {
   }

   public MouseEvent( int x, int y )
   {
      this.x = x;
      this.y = y;
   }

   public int getX()
   {
      return x;
   }

   public void setX( int x )
   {
      this.x = x;
   }

   public int getY()
   {
      return y;
   }

   public void setY( int y )
   {
      this.y = y;
   }

   public boolean isAltKey()
   {
      return altKey;
   }

   public void setAltKey( boolean altKey )
   {
      this.altKey = altKey;
   }

   public boolean isShiftKey()
   {
      return shiftKey;
   }

   public void setShiftKey( boolean shiftKey )
   {
      this.shiftKey = shiftKey;
   }

   public boolean isControlKey()
   {
      return controlKey;
   }

   public void setControlKey( boolean controlKey )
   {
      this.controlKey = controlKey;
   }

   public boolean isMetaKey()
   {
      return metaKey;
   }

   public void setMetaKey( boolean metaKey )
   {
      this.metaKey = metaKey;
   }
   
   public Vector getPosition()
   {
      return new Vector( x, y );
   }
}
