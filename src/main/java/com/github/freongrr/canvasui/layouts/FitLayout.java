package com.github.freongrr.canvasui.layouts;

import java.util.Map;

import com.github.freongrr.canvasui.events.RedrawEvent;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Composite;
import com.github.freongrr.canvasui.ui.Widget;

/**
 * A fit layout only contains one element (TODO : enforce?) occupying the whole
 * area of the panel.
 * 
 * @author fabien
 */
public class FitLayout implements Layout
{
   @Override
   public LayoutData getDefaultLayoutData( Composite composite, Map<Widget, LayoutData> data )
   {
      return null;
   }

   @Override
   public void layout( Composite composite, Map<Widget, LayoutData> data )
   {
      if( data.size() > 0 )
      {
         Widget w = data.keySet().iterator().next();
         if( !w.getSize().equals( composite.getSize() ) )
         {
            w.setOffset( new Vector( 0, 0 ) );
            w.setSize( composite.getSize() );
            w.fireRedrawEvent( new RedrawEvent( w ) );
         }
      }
   }

   @Override
   public Vector getMinimumSize( Composite composite, Map<Widget, LayoutData> data )
   {
      if( data.size() > 0 )
      {
         Widget w = data.keySet().iterator().next();
         return w.getMinimumSize();
      }
      return new Vector();
   }
}
