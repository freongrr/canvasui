package com.github.freongrr.canvasui.graphics;

import com.github.freongrr.canvasui.shapes.Circle;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.ui.MousePointer;

/**
 * This class is a wrapper for graphic context.
 * TODO : move to an other package ?
 * @author fabien
 *
 */
public interface GraphicContext
{
   /* Rendering methods */

   public abstract void save();
   public abstract void restore();

   // Line style (for strokeXXX methods)
   public abstract void setLineStyle( Color color );
   public abstract void setLineStyle( Gradient gradient );
   public abstract void setLineWidth( double width );

   // Fill style (for fillXXX methods)
   public abstract void setFillStyle( Color color );
   public abstract void setFillStyle( Gradient gradient );

   // Text style (for both strokeXXX and fillXXX methods)
   public abstract void setTextFont( Font font );
   public abstract void setTextHorizontalAlign( HorizontalAlign align );
   public abstract void setTextVerticalAlign( VerticalAlign align );

   // Shadow style (for both strokeXXX and fillXXX methods)
   public abstract void setShadowColor( Color color );
   public abstract void setShadowOffset( Vector offset );
   public abstract void setShadowBlur( double blur );

   // Fill / Stroke methods
   public abstract void fillCircle( Circle circle );
   public abstract void strokeCircle( Circle circle );

   public abstract void fillRectangle( Rectangle rectangle );
   public abstract void strokeRectangle( Rectangle rectangle );

   public abstract void fillRoundRectangle( Rectangle rectangle, double curve );
   public abstract void strokeRoundRectangle( Rectangle rectangle, double curve );

   public abstract void fillRoundRectangle( Rectangle rectangle, double topcurve, double bottomcurve );
   public abstract void strokeRoundRectangle( Rectangle rectangle, double topcurve, double bottomcurve );

   public abstract void fillText( String text, double x, double y );
   public abstract void strokeText( String text, double x, double y );

   public abstract void fillText( String text, double x, double y, double maxWidth );
   public abstract void strokeText( String text, double x, double y, double maxWidth );
   
   public abstract void strokeLine( double fromX, double fromY, double toX, double toY );

   // Misc methods
   public abstract MousePointer getMousePointer();
   public abstract void setMousePointer( MousePointer pointer );
   public abstract Vector getTextSize( Font font, String text );
   public abstract void setClippingRectangle(Rectangle rectangle);
}
