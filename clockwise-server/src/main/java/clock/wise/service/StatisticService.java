package clock.wise.service;

import clock.wise.dto.StatisticDto;

import java.util.List;

public interface StatisticService
{
    StatisticDto createOrUpdate( final StatisticDto statisticDto, final Long userId );

    List<StatisticDto> findStatisticsByUserId( final Long userId );
}
