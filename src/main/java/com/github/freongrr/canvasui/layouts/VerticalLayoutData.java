package com.github.freongrr.canvasui.layouts;

public class VerticalLayoutData extends GridLayoutData
{
   public VerticalLayoutData( int row )
   {
      super( 0, row );
   }

   public VerticalLayoutData( int row, boolean extend, boolean fillX, boolean fillY )
   {
      super( 0, row, true, extend, fillX, fillY );
   }
}
