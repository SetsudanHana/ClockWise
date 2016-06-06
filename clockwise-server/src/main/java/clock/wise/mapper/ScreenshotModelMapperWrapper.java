package clock.wise.mapper;

import clock.wise.converter.DtoToScreenshotConverter;
import clock.wise.converter.ScreenshotToDtoConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScreenshotModelMapperWrapper extends ModelMapperWrapper {

    @Override
    protected List< AbstractConverter > convertersList() {
        List< AbstractConverter > converters = new ArrayList<>();
        converters.add( new ScreenshotToDtoConverter() );
        converters.add( new DtoToScreenshotConverter() );
        return converters;
    }

}
