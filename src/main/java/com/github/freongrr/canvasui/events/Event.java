package com.github.freongrr.canvasui.events;

import java.util.LinkedList;
import java.util.List;

/**
 * Base event.
 * 
 * @author fabien
 */
public class Event implements Cancelable
{
   private Object nativeEvent;
   private boolean cancelled;
   private List<CancelListener> cancelListeners = new LinkedList<CancelListener>();

   public Object getNativeEvent()
   {
      return nativeEvent;
   }

   public void setNativeEvent( Object nativeEvent )
   {
      this.nativeEvent = nativeEvent;
   }

   public boolean isCancelled()
   {
      return cancelled;
   }

   public void setCancelled( boolean cancelled )
   {
      this.cancelled = cancelled;
   }
   
   public void cancel()
   {
      setCancelled( true );
      fireCancelEvent( new CancelEvent( nativeEvent ) );
   }

   @Override
   public void addCancelListener( CancelListener listener )
   {
      cancelListeners.add( listener );
   }

   @Override
   public void removeCancelListener( CancelListener listener )
   {
      cancelListeners.add( listener );
   }

   @Override
   public void fireCancelEvent( CancelEvent ce )
   {
      for( CancelListener l : cancelListeners )
      {
         if( !ce.isCancelled() )
         {
            l.onCancel( ce );
         }
      }
   }
}
