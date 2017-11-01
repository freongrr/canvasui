package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.events.DragListener;
import com.github.freongrr.canvasui.laf.LookAndFeel;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.events.DragEvent;
import com.github.freongrr.canvasui.shapes.Shape;

/**
 * This implementation allow moving and resizing windows.
 * 
 * @author fabien
 */
public class WindowDragListener implements DragListener
{
   private Window window;
   private boolean isMoving;
   private ResizingDirection resizingDirection;

   public WindowDragListener( Window window )
   {
      this.window = window;
   }

   public Window getWindow()
   {
      return window;
   }

   public void setWindow( Window window )
   {
      this.window = window;
   }

   public boolean isMoving()
   {
      return isMoving;
   }

   public void setMoving( boolean isMoving )
   {
      this.isMoving = isMoving;
   }

   public boolean isResizing()
   {
      return resizingDirection != null;
   }

   public ResizingDirection getResizingDirection()
   {
      return resizingDirection;
   }

   public void setResizingDirection( ResizingDirection resizingDirection )
   {
      this.resizingDirection = resizingDirection;
   }

   public ResizingDirection getResizingDirection( Vector pos )
   {
      LookAndFeel laf = window.getLookAndFeel();
      boolean north = laf.getWindowNorthBorderShape( window ).contains( pos );
      boolean south = laf.getWindowSouthBorderShape( window ).contains( pos );
      boolean east = laf.getWindowEastBorderShape( window ).contains( pos );
      boolean west = laf.getWindowWestBorderShape( window ).contains( pos );

      ResizingDirection direction = null;
      if( north )
      {
         if( east )
         {
            direction = ResizingDirection.NORTH_EAST;
         }
         else if( west )
         {
            direction = ResizingDirection.NORTH_WEST;
         }
         else
         {
            direction = ResizingDirection.NORTH;
         }
      }
      else if( south )
      {
         if( east )
         {
            direction = ResizingDirection.SOUTH_EAST;
         }
         else if( west )
         {
            direction = ResizingDirection.SOUTH_WEST;
         }
         else
         {
            direction = ResizingDirection.SOUTH;
         }
      }
      else if( east )
      {
         direction = ResizingDirection.EAST;
      }
      else if( west )
      {
         direction = ResizingDirection.WEST;
      }

      return direction;
   }

   @Override
   public void onDragStart( DragEvent de )
   {
      // detect were the mouse is
      Vector position = de.getCurrentPosition();

      this.resizingDirection = getResizingDirection( position );
      if( resizingDirection == null )
      {
         Shape title = window.getLookAndFeel().getWindowTitleShape( window );
         if( title.contains( position ) )
         {
            isMoving = true;
         }
      }
   }

   @Override
   public void onDragEnd( DragEvent de )
   {
      isMoving = false;
      resizingDirection = null;
   }

   @Override
   public void onDrag( DragEvent de )
   {
      if( isMoving )
      {
         Vector newOffset = window.getOffset().add( de.getDelta() );
         window.setOffset( newOffset );
      }
      else if( resizingDirection != null )
      {
         double newLeft = window.getLeft();
         double newTop = window.getTop();
         double newWidth = window.getWidth();
         double newHeight = window.getHeight();
         
         Vector minSize = window.getMinimumSize();

         if( resizingDirection == ResizingDirection.NORTH ||
             resizingDirection == ResizingDirection.NORTH_EAST ||
             resizingDirection == ResizingDirection.NORTH_WEST )
         {
            newHeight = newHeight - de.getDeltaY();
            if( newHeight < minSize.y )
            {
               newHeight = minSize.y;
            }
            newTop = newTop + window.getHeight() - newHeight;
         }
         else if( resizingDirection == ResizingDirection.SOUTH ||
                  resizingDirection == ResizingDirection.SOUTH_EAST ||
                  resizingDirection == ResizingDirection.SOUTH_WEST )
         {
            newHeight = newHeight + de.getDeltaY();
            if( newHeight < minSize.y )
            {
               newHeight = minSize.y;
            }
         }

         if( resizingDirection == ResizingDirection.WEST ||
             resizingDirection == ResizingDirection.NORTH_WEST ||
             resizingDirection == ResizingDirection.SOUTH_WEST )
         {
            newWidth = newWidth - de.getDeltaX();
            if( newWidth < minSize.x )
            {
               newWidth = minSize.x;
            }
            newLeft = newLeft + window.getWidth() - newWidth;
         }
         else if( resizingDirection == ResizingDirection.EAST ||
                  resizingDirection == ResizingDirection.NORTH_EAST ||
                  resizingDirection == ResizingDirection.SOUTH_EAST )
         {
            newWidth = newWidth + de.getDeltaX();
            if( newWidth < minSize.x )
            {
               newWidth = minSize.x;
            }
         }

         window.setOffset( new Vector( newLeft, newTop ) );
         window.setSize( new Vector( newWidth, newHeight ) );
      }
   }
}
