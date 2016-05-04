package clock.wise.dao;


import clock.wise.model.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatisticDao extends CrudRepository< Statistic, Long >
{
    @Query( "from User u join u.statistic s where s.id=:userId" )
    Statistic findOneByUserId( @Param( "userId" ) final Long userId );
}
