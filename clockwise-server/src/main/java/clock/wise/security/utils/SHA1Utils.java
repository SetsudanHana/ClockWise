package clock.wise.security.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Utils {

    private MessageDigest mMessageDigest;
    private static SHA1Utils mInstance;

    private SHA1Utils() throws NoSuchAlgorithmException {
        mMessageDigest = MessageDigest.getInstance( "SHA-1" );
    }

    public static SHA1Utils getInstance() {
        if ( mInstance == null ) {
            try {
                mInstance = new SHA1Utils();
            }
            catch ( NoSuchAlgorithmException e ) {
                return null;
            }
        }
        return mInstance;
    }

    public String encode( String value ) {
        try {
            mMessageDigest.reset();
            mMessageDigest.update( value.getBytes( "UTF-8" ) );
            return byteArrayToHexString( mMessageDigest.digest() );
        }
        catch ( UnsupportedEncodingException e ) {
            return null;
        }
    }

    private String byteArrayToHexString( byte[] b ) {
        String result = "";
        for ( int i = 0; i < b.length; i++ ) {
            result +=
                    Integer.toString( ( b[ i ] & 0xff ) + 0x100, 16 ).substring( 1 );
        }
        return result;
    }

}
