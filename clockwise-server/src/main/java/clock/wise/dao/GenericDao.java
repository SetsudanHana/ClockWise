package clock.wise.dao;

/**
 * Created by Gniewko on 2016-04-11.
 */
import java.io.Serializable;
import java.util.List;

public interface GenericDao< T, PK extends Serializable >
{
    T persist( T newInstance );

    T find( PK id );

    T update( T transientObject );

    void delete( T persistentObject );

    List< T > findAll();
}