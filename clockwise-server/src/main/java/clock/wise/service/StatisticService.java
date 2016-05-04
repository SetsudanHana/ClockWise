package clock.wise.service;

import clock.wise.dto.StatisticDto;

public interface StatisticService
{
    StatisticDto createOrUpdate( final StatisticDto statisticDto, final Long userId );

    StatisticDto findStatisticByUserId( final Long userId );
}
