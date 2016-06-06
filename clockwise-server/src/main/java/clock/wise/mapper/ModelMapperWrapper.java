package clock.wise.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.List;

public abstract class ModelMapperWrapper {

    private ModelMapper modelMapper = new ModelMapper();

    public ModelMapperWrapper() {
        for ( AbstractConverter converter : convertersList() ) {
            modelMapper.addConverter( converter );
        }
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected abstract List< AbstractConverter > convertersList();
}
