package com.github.freongrr.canvasui.layouts;

public class GridLayoutData implements LayoutData
{
   private int column;
   private int row;
   private boolean extendX = true;
   private boolean extendY = true;
   private boolean fillX = true;
   private boolean fillY = true;

   public GridLayoutData( int column, int row )
   {
      this.column = column;
      this.row = row;
   }

   public GridLayoutData( int column, int row, boolean extendX, boolean extendY, boolean fillX, boolean fillY )
   {
      this( column, row );
      this.extendX = extendX;
      this.extendY = extendY;
      this.fillX = fillX;
      this.fillY = fillY;
   }

   public int getColumn()
   {
      return column;
   }

   public void setColumn( int column )
   {
      this.column = column;
   }

   public int getRow()
   {
      return row;
   }

   public void setRow( int row )
   {
      this.row = row;
   }

   /**
    * Returns true if the block is to extend to fill up the space available to
    * the panel. Otherwise the size of the block is determined by the size of
    * the content. Default to true;
    */
   public boolean isExtendX()
   {
      return extendX;
   }

   public void setExtendX( boolean extendX )
   {
      this.extendX = extendX;
   }

   /**
    * Returns true if the block is to extend to fill up the space available to
    * the panel. Otherwise the size of the block is determined by the size of
    * the content. Default to true;
    */
   public boolean isExtendY()
   {
      return extendY;
   }

   public void setExtendY( boolean extendY )
   {
      this.extendY = extendY;
   }

   /**
    * Returns true is the element contained in the block should be resized
    * horizontally to fill the block. Default to true;
    */
   public boolean isFillX()
   {
      return fillX;
   }

   public void setFillX( boolean fillX )
   {
      this.fillX = fillX;
   }

   /**
    * Returns true is the element contained in the block should be resized
    * vertically to fill the block. Default to true;
    */
   public boolean isFillY()
   {
      return fillY;
   }

   public void setFillY( boolean fillY )
   {
      this.fillY = fillY;
   }

   @Override
   public String toString()
   {
      return "GridLayoutData [column=" + column + ", extendX=" + extendX +
             ", extendY=" + extendY + ", fillX=" + fillX + ", fillY=" + fillY +
             ", row=" + row + "]";
   }

   
}
