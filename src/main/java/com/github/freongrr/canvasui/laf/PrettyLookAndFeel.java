package com.github.freongrr.canvasui.laf;

import com.github.freongrr.canvasui.graphics.Color;
import com.github.freongrr.canvasui.graphics.Gradient;
import com.github.freongrr.canvasui.graphics.GraphicContext;
import com.github.freongrr.canvasui.graphics.LinearGradient;
import com.github.freongrr.canvasui.shapes.Margin;
import com.github.freongrr.canvasui.shapes.Vector;
import com.github.freongrr.canvasui.graphics.Font;
import com.github.freongrr.canvasui.graphics.GradientColors;
import com.github.freongrr.canvasui.graphics.HorizontalAlign;
import com.github.freongrr.canvasui.graphics.VerticalAlign;
import com.github.freongrr.canvasui.graphics.WindowStyle;
import com.github.freongrr.canvasui.shapes.Rectangle;
import com.github.freongrr.canvasui.ui.Window;

public class PrettyLookAndFeel extends SimpleLookAndFeel
{
   protected PrettyLookAndFeel()
   {
      super();

      // style for active windows
      activeWindowStyle = new WindowStyle();

      // TODO : use color/titleColor
      Color highlight = new Color( 255, 255, 255 );
      Color base = new Color( 240, 240, 255 );
      Color text = new Color( 32, 32, 92 );
      Color itext = new Color( 128, 128, 128 );

      activeWindowStyle.setBorderWidth( 1 );
      activeWindowStyle.setBorderColor( new Color( 0, 0, 0 ) );
      activeWindowStyle.setBorderCurve( 5 );

      activeWindowStyle.setShadowOffset( new Vector( 2, 2 ) );
      activeWindowStyle.setShadowBlur( 10 );
      activeWindowStyle.setShadowColor( new Color( 0, 0, 0, 0.5 ) );

      GradientColors gradient = new GradientColors();
      gradient.addPoint( base, 0.25 );
      gradient.addPoint( highlight, 1 );
      activeWindowStyle.setGradient( gradient );
      activeWindowStyle.setColor( base );

      GradientColors titleGradient = new GradientColors();
      titleGradient.addPoint( highlight, 0 );
      titleGradient.addPoint( highlight, 0.3 );
      titleGradient.addPoint( base, 0.5 );
      titleGradient.addPoint( base, 1 );
      activeWindowStyle.setTitleGradient( titleGradient );
      activeWindowStyle.setTitleColor( highlight );

      activeWindowStyle.setTitleFont( new Font( "sans", 12, text ) );
      activeWindowStyle.setTitleHAlignement( HorizontalAlign.LEFT );
      activeWindowStyle.setTitleVAlignement( VerticalAlign.MIDDLE );
      activeWindowStyle.setTitleMargin( new Margin( 0 ) );
      activeWindowStyle.setTitleHeight( 24 );

      // Style for inactive window
      inactiveWindowStyle = new WindowStyle( activeWindowStyle );

      inactiveWindowStyle.setBorderColor( new Color( 64, 64, 64 ) );
      inactiveWindowStyle.setColor( new Color( 220, 220, 255 ) );
      inactiveWindowStyle.setShadowColor( new Color( 0, 0, 0, 0.25 ) );

      GradientColors igradient = new GradientColors();
      igradient.addPoint( new Color( 240, 240, 240 ), 0.25 );
      igradient.addPoint( new Color( 255, 255, 255 ), 1 );
      inactiveWindowStyle.setGradient( igradient );

      GradientColors ititleGradient = new GradientColors();
      ititleGradient.addPoint( highlight, 0 );
      ititleGradient.addPoint( highlight, 0.3 );
      ititleGradient.addPoint( base, 0.5 );
      ititleGradient.addPoint( base, 1 );
      inactiveWindowStyle.setTitleGradient( ititleGradient );

      inactiveWindowStyle.setTitleHAlignement( HorizontalAlign.LEFT );
      inactiveWindowStyle.setTitleMargin( new Margin( 0 ) );

      inactiveWindowStyle.setTitleFont( new Font( "sans", 12, itext ) );
   }

   protected void drawWindowBorder( GraphicContext context, Window window )
   {
      WindowStyle style = getCurrentWindowStyle( window );

      // Draw the border (if the border width is greater than 0)
      if( style.getBorderWidth() > 0 )
      {
         context.save();

         double borderWidth = style.getBorderWidth();

         Rectangle rect = getWindowRectangle( window );
         rect = new Rectangle( rect, new Margin( borderWidth / 2 ) );

         // Shadow
         if( style.getShadowOffset() != null &&
             !style.getShadowOffset().isZero() )
         {
            context.setShadowOffset( style.getShadowOffset() );
            context.setShadowBlur( style.getShadowBlur() );
            context.setShadowColor( style.getShadowColor() );
         }

         context.setFillStyle( style.getBorderColor() );
         context.fillRoundRectangle( rect, style.getBorderCurve() );

         context.restore();
      }
   }

   protected void drawWindowBackground( GraphicContext context, Window window )
   {
      WindowStyle style = getCurrentWindowStyle( window );

      context.save();

      // TODO : the curve should be different at the top and bottom and on the
      // sides. it should also be smaller if the border is thick
      double c = style.getBorderCurve(); // * 0.75;

      // if there is no border, the window has to render the shadow
      if( style.getBorderWidth() == 0 && style.getShadowOffset() != null &&
          !style.getShadowOffset().isZero() )
      {
         context.setShadowOffset( style.getShadowOffset() );
         context.setShadowBlur( style.getShadowBlur() );
         context.setShadowColor( style.getShadowColor() );
      }

      // gradient
      if( style.getGradient() != null )
      {
         Vector start = new Vector( 0, window.getTop() );
         Vector end = new Vector( 0, window.getBottom() );
         Gradient linear = new LinearGradient( style.getGradient(), start, end );
         context.setFillStyle( linear );
      }
      // solid
      else
      {
         context.setFillStyle( style.getColor() );
      }

      Rectangle rect = getWindowRectangle( window );
      context.fillRoundRectangle( rect, c );

      context.restore();
   }

   protected void drawWindowTitle( GraphicContext context, Window window )
   {
      WindowStyle style = getCurrentWindowStyle( window );

      double titleHeight = style.getTitleHeight();

      Rectangle titleRectangle = getWindowTitleRectangle( window );

      // Title background
      if( style.getTitleHeight() > 0 )
      {
         context.save();

         // gradient
         if( style.getTitleGradient() != null )
         {
            Vector start = new Vector( 0, titleRectangle.getTop() );
            Vector end = new Vector( 0, titleRectangle.getBottom() );
            Gradient linear = new LinearGradient( style.getTitleGradient(),
                                                  start, end );
            context.setFillStyle( linear );
         }
         // solid
         else
         {
            context.setFillStyle( style.getTitleColor() );
         }

         // TODO : the curve should be different at the top and bottom and on
         // the sides. it should also be smaller if the border is thick

         // top curve
         double tc = style.getBorderCurve() * 0.9;
         // bottom curve
         double bc = 0;

         context.fillRoundRectangle( titleRectangle, tc, bc );

         context.restore();
      }

      // Title text
      if( !"".equals( window.getTitle() ) )
      {
         context.save();

         double titleWidth = titleRectangle.width;
         double titleX = titleRectangle.left + 10; // TODO
         double titleY = titleRectangle.top + titleHeight / 2;
         context.setTextFont( style.getTitleFont() );
         context.setTextHorizontalAlign( style.getTitleHAlignement() );
         context.setTextVerticalAlign( style.getTitleVAlignement() );
         context.fillText( window.getTitle(), titleX, titleY, titleWidth );

         context.restore();
      }
   }
}
