package clock.wise.converter;

import clock.wise.dto.StatisticDto;
import clock.wise.model.Statistic;
import org.modelmapper.AbstractConverter;

public class StatisticConverter extends AbstractConverter< Statistic, StatisticDto >
{
    @Override
    protected StatisticDto convert( final Statistic source )
    {
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setId( source.getId() );
        statisticDto.setKeyboardClickedCount( source.getKeyboardClickedCount() );
        statisticDto.setMouseClickedCount( source.getMouseClickedCount() );
        statisticDto.setMouseMovementCount( source.getMouseMovementCount() );
        statisticDto.setDate( source.getDate() );

        return statisticDto;
    }
}
