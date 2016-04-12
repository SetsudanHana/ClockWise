package clock.wise.dao.impl;

import clock.wise.dao.UserDao;
import clock.wise.model.User;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserDaoImpl extends CrudRepositoryImpl< User, Long > implements UserDao
{
    public UserDaoImpl()
    {
        super( User.class );
    }
}
