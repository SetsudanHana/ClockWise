package clock.wise.db;

import clock.wise.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
}
