package clock.wise.mapper;

import clock.wise.converter.CompanyConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyModelMapperWrapper extends ModelMapperWrapper {
    @Override
    protected List< AbstractConverter > convertersList() {
        List< AbstractConverter > converters = new ArrayList<>();
        converters.add( new CompanyConverter() );
        return converters;
    }

}
