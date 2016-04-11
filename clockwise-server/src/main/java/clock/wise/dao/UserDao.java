package clock.wise.dao;

import clock.wise.model.User;
import clock.wise.model.roles.Role;

public interface UserDao extends GenericDao< User, Long >
{

    User findOneByUsername( String username );

    User findOneByRole( Role role );

}
