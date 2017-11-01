package com.github.freongrr.canvasui.layouts;

public class HorizontalLayoutData extends GridLayoutData
{
   public HorizontalLayoutData( int column )
   {
      super( column, 0 );
   }

   public HorizontalLayoutData( int column, boolean extend, boolean fillX, boolean fillY )
   {
      super( column, 0, extend, true, fillX, fillY );
   }
}
