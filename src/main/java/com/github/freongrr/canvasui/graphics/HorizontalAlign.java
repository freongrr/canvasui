package com.github.freongrr.canvasui.graphics;

/**
 * Implementation indenpendant enum for horizontal alignements.
 * 
 * @author fabien
 */
public class HorizontalAlign
{
   public final static HorizontalAlign LEFT = new HorizontalAlign( "left" );
   public final static HorizontalAlign CENTER = new HorizontalAlign( "center" );
   public final static HorizontalAlign RIGHT = new HorizontalAlign( "right" );

   private String type;

   private HorizontalAlign( String type )
   {
      this.type = type;
   }

   @Override
   public String toString()
   {
      return type;
   }
}
