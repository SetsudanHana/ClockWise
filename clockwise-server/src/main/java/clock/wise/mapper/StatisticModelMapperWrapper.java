package clock.wise.mapper;

import clock.wise.converter.StatisticConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StatisticModelMapperWrapper extends ModelMapperWrapper
{
    @Override
    protected List< AbstractConverter > convertersList()
    {
        return Arrays.asList( new StatisticConverter() );
    }
}
