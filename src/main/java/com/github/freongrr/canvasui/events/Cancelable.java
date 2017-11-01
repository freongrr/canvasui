package com.github.freongrr.canvasui.events;

public interface Cancelable
{
   public void addCancelListener( CancelListener listener );
   public void removeCancelListener( CancelListener listener );
   public void fireCancelEvent( CancelEvent ce );
}
