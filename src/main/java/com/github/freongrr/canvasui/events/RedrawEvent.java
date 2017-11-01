package com.github.freongrr.canvasui.events;

import java.util.ArrayList;
import java.util.List;

import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.ui.Widget;

public class RedrawEvent extends Event
{
   private List<Rectangle> invalidAreas;
   private Widget source;

   public RedrawEvent( Widget source )
   {
      this.source = source;
      this.invalidAreas = new ArrayList<Rectangle>();
      this.invalidAreas.add( source.getDrawArea() );
   }

   public RedrawEvent( Widget source, Rectangle ... areas )
   {
      this.source = source;
      this.invalidAreas = new ArrayList<Rectangle>();
      for( Rectangle r : areas )
      {
         invalidAreas.add( r );
      }
   }

   public Widget getSource()
   {
      return source;
   }

   public void setSource( Widget source )
   {
      this.source = source;
   }

   public List<Rectangle> getInvalidAreas()
   {
      return invalidAreas;
   }

   public void setInvalidAreas( List<Rectangle> invalidAreas )
   {
      this.invalidAreas = invalidAreas;
   }
}
