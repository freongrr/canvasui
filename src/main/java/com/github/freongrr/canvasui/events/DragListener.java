package com.github.freongrr.canvasui.events;

public interface DragListener extends Listener
{
   public void onDragStart( DragEvent de );

   public void onDragEnd( DragEvent de );

   public void onDrag( DragEvent de );
}
