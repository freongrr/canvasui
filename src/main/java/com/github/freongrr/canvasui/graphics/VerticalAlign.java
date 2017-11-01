package com.github.freongrr.canvasui.graphics;

/**
 * Implementation indenpendant enum for vertical alignements.
 * 
 * @author fabien
 */
public class VerticalAlign
{
   public final static VerticalAlign TOP = new VerticalAlign( "top" );
   public final static VerticalAlign MIDDLE = new VerticalAlign( "middle" );
   public final static VerticalAlign BOTTOM = new VerticalAlign( "bottom" );

   private String type;

   private VerticalAlign( String type )
   {
      this.type = type;
   }

   @Override
   public String toString()
   {
      return type;
   }
}
