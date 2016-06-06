package clock.wise.dao;

import clock.wise.model.User;
import clock.wise.model.roles.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository< User, Long > {
    User findOneByUsername( String username );

    User findOneByRole( Role role );

    User findOneByEmail( String email );

    List< User > findByCompanyId( Long companyId );
}