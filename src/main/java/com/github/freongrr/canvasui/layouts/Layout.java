package com.github.freongrr.canvasui.layouts;

import java.util.Map;

import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.Composite;
import com.github.freongrr.canvasui.ui.Widget;

/**
 * This interface defines a Layout, which is used to place widgets inside a
 * panel.
 * 
 * @author fabien
 */
public interface Layout
{
   // TODO : should we keep the widgets/data as a member variables?

   /**
    * Layout of the widgets.
    * 
    * @param composite
    *           the parent widget
    * @param an
    *           association of widget/layout data
    */
   public abstract void layout( Composite composite, Map<Widget, LayoutData> data );

   /**
    * Returns the default {@link LayoutData} given the a composite and a set of
    * {@link LayoutData}.
    * 
    * @param composite
    *           the parent widget
    * @param an
    *           association of widget/layout data
    */
   public abstract LayoutData getDefaultLayoutData( Composite composite, Map<Widget, LayoutData> data );

   /**
    * Returns the minimum size of the panel.
    * 
    * @param composite
    *           the parent widget
    * @param an
    *           association of widget/layout data
    */
   public abstract Vector getMinimumSize( Composite composite, Map<Widget, LayoutData> data );
}
