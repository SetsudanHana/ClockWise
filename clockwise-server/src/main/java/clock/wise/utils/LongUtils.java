package clock.wise.utils;

/**
 * Created by Allocer on 2016-06-04.
 */
public class LongUtils {
    public static boolean isEmpty( final Long number ) {
        return number == null;
    }

    public static boolean isNotEmpty( final Long number ) {
        return !isEmpty( number );
    }
}
