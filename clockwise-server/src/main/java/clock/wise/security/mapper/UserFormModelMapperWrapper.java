package clock.wise.security.mapper;

import clock.wise.mapper.ModelMapperWrapper;
import clock.wise.security.converter.UserFormDtoConverter;
import clock.wise.security.converter.UserToUserFormConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFormModelMapperWrapper extends ModelMapperWrapper {
    @Override
    protected List< AbstractConverter > convertersList() {
        List< AbstractConverter > converters = new ArrayList<>();
        converters.add( new UserFormDtoConverter() );
        converters.add( new UserToUserFormConverter() );
        return converters;
    }
}
