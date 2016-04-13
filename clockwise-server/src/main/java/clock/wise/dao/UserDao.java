package clock.wise.dao;

import clock.wise.model.User;
import clock.wise.model.roles.Role;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository< User, Long >
{
    User findOneByUsername( String username );

    User findOneByRole( Role role );
}
