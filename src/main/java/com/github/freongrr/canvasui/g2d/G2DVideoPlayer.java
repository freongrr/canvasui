package com.github.freongrr.canvasui.g2d;

import com.github.freongrr.canvasui.shapes.Vector;
import gwt.g2d.client.media.Video;

public class G2DVideoPlayer extends GWTWidgetContainer
{
   private Video video;

   public G2DVideoPlayer( String source )
   {
      offset = new Vector( 0, 0 );
      size = new Vector( 0, 0 );

      // Invisible video object
      video = new Video( source );
      video.setControls( true );
      video.setAutobuffer( false );
      setWidget( video );
   }

//   @Override
//   public Vector getMinimumSize()
//   {
//      int minWidth = video.getVideoWidth();
//      int minHeight = video.getVideoHeight();
//      return new Vector( minWidth, minHeight );
//   }
}
