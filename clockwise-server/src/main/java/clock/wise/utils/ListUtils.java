package clock.wise.utils;

import java.util.Collection;

/**
 * Created by Allocer on 2016-06-04.
 */
public class ListUtils
{
    public static boolean isEmpty( final Collection list )
    {
        return list != null && !list.isEmpty();
    }

    public static boolean isNotEmpty( final Collection list )
    {
        return !isEmpty(list);
    }

}
