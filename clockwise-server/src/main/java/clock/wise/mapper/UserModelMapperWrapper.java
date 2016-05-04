package clock.wise.mapper;

import clock.wise.converter.UserModelConverter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserModelMapperWrapper extends ModelMapperWrapper {
    @Override
    protected List<AbstractConverter> convertersList() {
        List<AbstractConverter> converters = new ArrayList<>();
        converters.add(new UserModelConverter());
        return converters;
    }
}
