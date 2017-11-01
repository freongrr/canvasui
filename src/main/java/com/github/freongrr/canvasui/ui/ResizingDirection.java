package com.github.freongrr.canvasui.ui;

public class ResizingDirection
{
   public static final ResizingDirection NORTH = new ResizingDirection( "n" );
   public static final ResizingDirection SOUTH = new ResizingDirection( "s" );
   public static final ResizingDirection EAST = new ResizingDirection( "e" );
   public static final ResizingDirection WEST  = new ResizingDirection( "w" );
   public static final ResizingDirection NORTH_EAST = new ResizingDirection( "ne" );
   public static final ResizingDirection NORTH_WEST = new ResizingDirection( "nw" );
   public static final ResizingDirection SOUTH_EAST = new ResizingDirection( "se" );
   public static final ResizingDirection SOUTH_WEST = new ResizingDirection( "sw" );
   
   private String type;
   
   private ResizingDirection(String type)
   {
      this.type = type;
   }
   
   public String getType()
   {
      return type;
   }

   @Override
   public String toString()
   {
      return "ResizingDirection [type=" + type + "]";
   }
}
