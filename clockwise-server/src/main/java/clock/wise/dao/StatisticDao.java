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

    List< Statistic > findByDateBetween( final Date startDate, Date endDate );

    List< Statistic > findByUserIdAndDateBetween( final Long userId, final Date startDate, final Date endDate );

}
