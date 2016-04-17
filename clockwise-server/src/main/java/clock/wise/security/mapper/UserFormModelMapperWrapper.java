package clock.wise.security.mapper;

import clock.wise.mapper.ModelMapperWrapper;
import clock.wise.security.converter.UserFormDtoConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserFormModelMapperWrapper extends ModelMapperWrapper {
    @Override
    protected List<AbstractConverter> convertersList() {
        return Arrays.asList(new UserFormDtoConverter());
    }
}
