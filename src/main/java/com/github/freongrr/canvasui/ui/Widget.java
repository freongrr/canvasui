package com.github.freongrr.canvasui.ui;

import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.laf.LookAndFeel;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.shapes.Rectangle;

/**
 * Interface defining the base properties of a widget.
 * 
 * @author fabien
 */
public interface Widget extends Attachable, Redrawable
{
   /**
    * Returns the parent of the widget.
    */
   public Composite getParent();

   /**
    * Sets the parent of the widget.
    */
   public void setParent( Composite parent );

   /**
    * Returns if the widget is visible.
    */
   public boolean isVisible();

   /**
    * Sets the visibility of the widget.
    */
   public void setVisible( boolean visible );

   /**
    * Returns whether the widget is attached or not.
    */
   public boolean isAttached();

   /**
    * Sets whether the widget is attached or not.
    */
   public void setAttached( boolean attached );

   /**
    * Sets the relative position.
    */
   public abstract void setOffset( Vector offset );

   /**
    * Returns the relative position.
    */
   public abstract Vector getOffset();

   /**
    * Sets the size.
    */
   public abstract void setSize( Vector size );

   /**
    * Returns the size.
    */
   public abstract Vector getSize();

   /**
    * Returns the minimum size.
    */
   public abstract Vector getMinimumSize();

   /**
    * Returns the Z index (the depth of the widget)
    */
   public int getZIndex();

   /**
    * Sets the Z index (the depth of the widget)
    */
   public void setZIndex( int zIndex );

   /**
    * Returns the absolute position of a widget.
    */
   public Vector getAbsolutePosition();

   /**
    * Returns the rectlangle the widget is drawn in.
    */
   public abstract Rectangle getDrawArea();

   /**
    * Draws the widget using the graphic context.
    */
   public abstract void draw( GraphicContext context, Rectangle area );

   /**
    * Returns the look and feel defined for this widget. If no look and feel is
    * defined, the default look and feel is returned.
    */
   public LookAndFeel getLookAndFeel();

   /**
    * Sets the look and feel for the widget.
    */
   public void setLookAndFeel( LookAndFeel lookAndFeel );
}
