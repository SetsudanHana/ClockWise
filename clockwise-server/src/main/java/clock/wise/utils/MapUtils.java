package clock.wise.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    private static Map< String, String > map = new HashMap<>();

    public static Map< String, String > put( final String key, final String value ) {
        map.put( key, value );
        return map;
    }

    public static Map< String, String > getMap() {
        Map< String, String > copy = new HashMap<>( map );
        map = new HashMap<>();

        return copy;
    }
}
