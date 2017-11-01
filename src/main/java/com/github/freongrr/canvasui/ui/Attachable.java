package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.AttachEvent;
import com.github.freongrr.canvasui.events.AttachListener;
import com.github.freongrr.canvasui.events.DetachEvent;
import com.github.freongrr.canvasui.events.DetachListener;

public interface Attachable
{
   public void addAttachListener( AttachListener listener );
   public void removeAttachListener( AttachListener listener );
   public void fireAttachEvent( AttachEvent ae );

   public void addDetachListener( DetachListener listener );
   public void removeDetachListener( DetachListener listener );
   public void fireDetachEvent( DetachEvent de );
}
