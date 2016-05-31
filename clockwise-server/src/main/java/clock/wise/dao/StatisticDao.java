package clock.wise.dao;


import clock.wise.model.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface StatisticDao extends CrudRepository< Statistic, Long >
{

    List< Statistic > findByUserId( Long userId );

    @Query( "select s from Statistic s where s.date between :startDate and :endDate" )
    List< Statistic > findStatsBetweenDates( @Param( "startDate" ) @Temporal( TemporalType.DATE ) final Date startDate, @Param( "endDate" ) @Temporal( TemporalType.DATE ) final Date endDate );

    @Query( "select s from Statistic s where s.date between :startDate and :endDate and s.user.id = :userId" )
    List< Statistic > findStatsBetweenDatesForUser( @Param( "userId" ) final Long userId, @Param( "startDate" ) @Temporal( TemporalType.DATE ) final Date startDate, @Param( "endDate" ) @Temporal( TemporalType.DATE ) final Date endDate );

}
