package clock.wise.converter;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.Screenshot;
import org.imgscalr.Scalr;
import org.modelmapper.AbstractConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class DtoToScreenshotConverter extends AbstractConverter< ScreenshotDto, Screenshot > {

    private static final int THUMBNAIL_HEIGHT = 100;
    private static final int THUMBNAIL_WIDTH = 100;

    @Override
    protected Screenshot convert( ScreenshotDto source ) {
        Screenshot out = new Screenshot();
        out.setDate( source.getDate() );
        out.setImage( Base64.getDecoder().decode( source.getBase64Data() ) );
        InputStream in = new ByteArrayInputStream( out.getImage() );
        try {
            BufferedImage thumbnailBuffer = Scalr.resize( ImageIO.read( in ), Scalr.Method.QUALITY, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( thumbnailBuffer, "jpg", baos );
            baos.flush();
            out.setThumbnail( baos.toByteArray() );
            baos.close();
        }
        catch ( IOException e ) {
            //IGNORED, IF THERE IS SOMETHING WRONG WE WONT REACH TRY ANYWAY
        }
        return out;
    }

}
