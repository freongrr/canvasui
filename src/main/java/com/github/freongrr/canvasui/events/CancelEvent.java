package com.github.freongrr.canvasui.events;

public class CancelEvent extends Event
{
   private Object nativeEvent;

   public CancelEvent( Object nativeEvent )
   {
      super();
      this.nativeEvent = nativeEvent;
   }

   public Object getNativeEvent()
   {
      return nativeEvent;
   }

   public void setNativeEvent( Object nativeEvent )
   {
      this.nativeEvent = nativeEvent;
   }

   @Override
   public String toString()
   {
      return "CancelEvent [nativeEvent=" + nativeEvent + "]";
   }
}
