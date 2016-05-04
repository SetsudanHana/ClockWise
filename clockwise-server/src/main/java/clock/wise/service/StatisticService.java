package clock.wise.service;

import clock.wise.dto.StatisticDto;

public interface StatisticService
{
    StatisticDto createOrUpdate( final StatisticDto statisticDto );

    StatisticDto findStatisticByUserId( final Long userId );

    StatisticDto updateUserStatistics( final Long userId, final StatisticDto statisticDto );
}
