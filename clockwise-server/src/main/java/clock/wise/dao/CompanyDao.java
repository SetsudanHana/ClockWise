package clock.wise.dao;

import clock.wise.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyDao extends CrudRepository< Company, Long >
{
    Company findByName( final String name );
}
