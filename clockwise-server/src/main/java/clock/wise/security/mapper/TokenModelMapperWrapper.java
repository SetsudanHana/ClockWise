package clock.wise.security.mapper;

import clock.wise.mapper.ModelMapperWrapper;
import clock.wise.security.converter.TokenConverter;
import clock.wise.security.converter.TokenDtoConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenModelMapperWrapper extends ModelMapperWrapper {
    @Override
    protected List<AbstractConverter> convertersList() {
        List<AbstractConverter> converters = new ArrayList<>();
        converters.add(new TokenConverter());
        converters.add(new TokenDtoConverter());
        return converters;
    }
}
