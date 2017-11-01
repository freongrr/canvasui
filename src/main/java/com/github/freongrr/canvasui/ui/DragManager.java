package com.github.freongrr.canvasui.ui;

import java.util.HashMap;
import java.util.Map;

import com.github.freongrr.canvasui.events.DragEvent;
import com.github.freongrr.canvasui.events.DragListener;
import com.github.freongrr.canvasui.events.MouseDownEvent;
import com.github.freongrr.canvasui.events.MouseDownListener;
import com.github.freongrr.canvasui.events.MouseMoveEvent;
import com.github.freongrr.canvasui.events.MouseMoveListener;
import com.github.freongrr.canvasui.events.MouseUpEvent;
import com.github.freongrr.canvasui.events.MouseUpListener;
import com.github.freongrr.canvasui.shapes.Vector;

/**
 * This class listens for mouse clicks and movement and translates them to a
 * DragEvent.
 * 
 * @author fabien
 * 
 */
public class DragManager implements MouseDownListener, MouseUpListener,
      MouseMoveListener
{
   private Container container;
   private Clickable source;
   private boolean isDragging;
   private Vector previousPosition;
   private Map<DragListener, Clickable> dragListeners;

   public DragManager()
   {
      dragListeners = new HashMap<DragListener, Clickable>();
   }

   public void bind( Container container )
   {
      container.addMouseDownListener( this );
      container.addMouseUpListener( this );
      container.addMouseMoveListener( this );

      setContainer( container );
   }

   public Container getContainer()
   {
      return container;
   }

   public void setContainer( Container container )
   {
      this.container = container;
   }

   public Clickable getSource()
   {
      return source;
   }

   public void setSource( Clickable widget )
   {
      this.source = widget;
   }

   public boolean isDragging()
   {
      return isDragging;
   }

   public void setDragging( boolean isDragging )
   {
      this.isDragging = isDragging;
   }

   public Vector getPreviousPosition()
   {
      return previousPosition;
   }

   public void setPreviousPosition( Vector previousPosition )
   {
      this.previousPosition = previousPosition;
   }

   public void addDragListener( DragListener listener )
   {
      addDragListener( listener, null );
   }

   public void addDragListener( DragListener listener, Clickable filter )
   {
      dragListeners.put( listener, filter );
   }

   public void removeDragListener( DragListener listener )
   {
      dragListeners.remove( listener );
   }

   protected void fireDragStartEvent( DragEvent event )
   {
      for( DragListener l : dragListeners.keySet() )
      {
         if( !event.isCancelled() )
         {
            // Only fire the evenr on the right source
            Clickable eb = dragListeners.get( l );
            if( eb == null || eb == event.getSource() )
            {
               l.onDragStart( event );
            }
         }
      }
   }

   protected void fireDragEndEvent( DragEvent event )
   {
      for( DragListener l : dragListeners.keySet() )
      {
         if( !event.isCancelled() )
         {
            // Only fire the evenr on the right source
            Clickable eb = dragListeners.get( l );
            if( eb == null || eb == event.getSource() )
            {
               l.onDragEnd( event );
            }
         }
      }
   }

   protected void fireDragEvent( DragEvent event )
   {
      for( DragListener l : dragListeners.keySet() )
      {
         if( !event.isCancelled() )
         {
            // Only fire the evenr on the right source
            Clickable eb = dragListeners.get( l );
            if( eb == null || eb == event.getSource() )
            {
               l.onDrag( event );
            }
         }
      }
   }

   @Override
   public void onMouseDown( MouseDownEvent me )
   {
      if( !isDragging )
      {
         // Look up the widget
         Clickable source = container.getClickableWidget( me );
         if( source != null )
         {
            Vector firstPosition = new Vector( me.getX(), me.getY() );
            setPreviousPosition( firstPosition );

            // Most of the time, the drag event should only convern one widget.
            // We could fire the event directly to the widget but it it would
            // make no sense to use events...
            // Another solution would be to fire the event to the listener by
            // order of deth, but the
            setSource( source );
            setDragging( true );

            DragEvent de = new DragEvent();
            de.setSource( source );
            de.setPreviousPosition( firstPosition );
            de.setCurrentPosition( firstPosition );
            fireDragStartEvent( de );

            // if the event has been cancelled we assume we are not dragging
         }
      }
   }

   @Override
   public void onMouseUp( MouseUpEvent me )
   {
      if( isDragging() )
      {
         setDragging( false );

         Vector oldPosition = getPreviousPosition();
         Vector newPosition = new Vector( me.getX(), me.getY() );

         setPreviousPosition( newPosition );

         DragEvent de = new DragEvent();
         de.setSource( getSource() );
         de.setPreviousPosition( oldPosition );
         de.setCurrentPosition( newPosition );

         fireDragEndEvent( de );
      }
   }

   @Override
   public void onMouseMove( MouseMoveEvent me )
   {
      if( isDragging() )
      {
         Vector oldPosition = getPreviousPosition();
         Vector newPosition = new Vector( me.getX(), me.getY() );

         setPreviousPosition( newPosition );

         DragEvent de = new DragEvent();
         de.setSource( getSource() );
         de.setPreviousPosition( oldPosition );
         de.setCurrentPosition( newPosition );

         fireDragEvent( de );
      }
   }
}
