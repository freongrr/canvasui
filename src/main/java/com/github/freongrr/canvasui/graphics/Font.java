package com.github.freongrr.canvasui.graphics;

/**
 * Implementation independant structure for fonts.
 * 
 * @author fabien
 */
public final class Font
{
   public String family;
   public double size;
   public Color color;

   // TODO : other style

   public Font()
   {
      this( "sans", 12 );
   }

   public Font( String family, double size )
   {
      this( family, size, new Color() );
   }

   public Font( String family, double size, Color color )
   {
      this.family = family;
      this.size = size;
      this.color = color;
   }

   @Override
   public String toString()
   {
      return "Font [family=" + family + ", size=" + size + ", color=" + color +
             "]";
   }
}
