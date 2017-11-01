package com.github.freongrr.canvasui.ui;

/**
 * This class defines the possible states for a window.
 * 
 * @author fabien
 */
public final class WindowState
{
   public static final WindowState NORMAL = new WindowState( "Normal" );
   public static final WindowState MAXIMIZED = new WindowState( "Maximized" );

   private String type;

   private WindowState( String type )
   {
      this.type = type;
   }

   @Override
   public String toString()
   {
      return "WindowState: " + type;
   }
}
