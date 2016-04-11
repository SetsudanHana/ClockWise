package clock.wise.dao.implementation;

import clock.wise.dao.UserDao;
import clock.wise.model.User;
import clock.wise.model.User_;
import clock.wise.model.roles.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

@Repository
public class UserDaoImpl extends GenericDaoImpl< User, Long > implements UserDao
{
    @PersistenceContext
    protected EntityManager entityManager;

    public UserDaoImpl()
    {
        super( User.class );
    }

    @Override
    public User findOneByUsername( String username )
    {
        initializeCriteriaBuilder();
        Predicate predicate = criteriaBuilder.equal( rootEntry.get( User_.username ), username );
        CriteriaQuery< User > query = criteriaQuery.where( predicate );

        return entityManager.createQuery( query ).getSingleResult();
    }

    @Override
    public User findOneByRole( Role role )
    {
        initializeCriteriaBuilder();
        Predicate predicate = criteriaBuilder.equal( rootEntry.get( User_.role ), role );
        CriteriaQuery< User > query = criteriaQuery.where( predicate );

        return entityManager.createQuery( query ).getSingleResult();
    }

}
