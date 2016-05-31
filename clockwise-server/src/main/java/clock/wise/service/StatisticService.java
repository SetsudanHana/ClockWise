package clock.wise.service;

import clock.wise.dto.StatisticDto;

import java.util.Date;
import java.util.List;

public interface StatisticService
{
    StatisticDto createOrUpdateStatistic( final StatisticDto statisticDto, final Long userId );

    List< StatisticDto > createOrUpdateStatistics( final List< StatisticDto > statistics, final Long userId );

    List< StatisticDto > findStatisticsByUserId( final Long userId );

    List< StatisticDto > findStatsBetweenDates( final Date startDate, final Date endDate );

    List< StatisticDto > findStatsBetweenDatesForUser( final Long userId, final Date startDate, final Date endDate );
}
