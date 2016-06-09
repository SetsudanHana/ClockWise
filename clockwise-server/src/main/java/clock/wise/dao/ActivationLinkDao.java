package clock.wise.dao;

import clock.wise.model.ActivationLink;
import org.springframework.data.repository.CrudRepository;

public interface ActivationLinkDao extends CrudRepository< ActivationLink, Long > {
    ActivationLink findByHash( final String hash );
}
