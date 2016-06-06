package clock.wise.dao;


import clock.wise.model.Statistic;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface StatisticDao extends CrudRepository< Statistic, Long > {

    List< Statistic > findByUserId( Long userId );

    List< Statistic > findByDateBetween( final Date startDate, Date endDate );

    List< Statistic > findByUserIdAndDateBetween( final Long userId, final Date startDate, final Date endDate );

}
