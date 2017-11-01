package com.github.freongrr.canvasui.shapes;

/**
 * Implementation idenpendant structure for rectangles.
 * 
 * @author fabien
 */
public final class Rectangle implements Shape
{
   public final double left;
   public final double top;
   public final double width;
   public final double height;

   public Rectangle( double left, double top, double width, double height )
   {
      super();
      this.left = left;
      this.top = top;
      this.width = width;
      this.height = height;
   }

   public Rectangle( Rectangle copy )
   {
      this( copy.left, copy.top, copy.width, copy.height );
   }

   public Rectangle( Vector position, Vector size )
   {
      this( position.x, position.y, size.x, size.y );
   }

   public Rectangle( Rectangle rect, Margin margin )
   {
      this( rect.left - margin.left, rect.top - margin.top, rect.width +
                                                            margin.left +
                                                            margin.right,
            rect.height + margin.top + margin.bottom );
   }

   public double getLeft()
   {
      return left;
   }

   public double getRight()
   {
      return left + width;
   }

   public double getTop()
   {
      return top;
   }

   public double getBottom()
   {
      return top + height;
   }

   public double getWidth()
   {
      return width;
   }

   public double getHeight()
   {
      return height;
   }

   @Override
   public String toString()
   {
      return "Rectangle [left=" + left + ", top=" + top + ", width=" + width +
             ", height=" + height + "]";
   }

   @Override
   public boolean contains( Vector coordinate )
   {
      double cx = coordinate.x;
      double cy = coordinate.y;

      double x1 = getLeft();
      double x2 = getRight();
      double y1 = getTop();
      double y2 = getBottom();

      return cx >= x1 && cx <= x2 && cy >= y1 && cy <= y2;
   }

   /**
    * Returns true if the rectangle contains entirely the area passed as a
    * parameter.
    * 
    * @param area
    * @return
    */
   public boolean contains( Rectangle area )
   {
      return left < area.getLeft() && top < area.getTop() &&
             getRight() > area.getRight() && getBottom() > area.getBottom();
   }

   /**
    * Returns true if the reclangle intersects with the area passed as a
    * parameter.
    * 
    * @param area
    * @return
    */
   public boolean intersect( Rectangle area )
   {
      return left < area.getRight() && top < area.getBottom() &&
             getRight() >= area.getLeft() && getBottom() >= area.getTop();
   }
}
