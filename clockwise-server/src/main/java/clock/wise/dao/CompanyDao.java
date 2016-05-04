package clock.wise.dao;

import clock.wise.model.Company;
import clock.wise.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyDao extends CrudRepository< Company, Long >
{
    Company findByName( final String name );

    @Query( "from User u join u.company c where c.id=:companyId and u.id=:userId" )
    User findCompanyUserById( @Param( "companyId" ) final Long companyId, @Param( "userId" ) final Long userId );
}
