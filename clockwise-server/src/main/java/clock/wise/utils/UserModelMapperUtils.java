package clock.wise.utils;

import clock.wise.converter.UserModelConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapperUtils
{

    public ModelMapper modelMapper = new ModelMapper();

    public UserModelMapperUtils()
    {
        modelMapper.addConverter( new UserModelConverter() );
    }

    public ModelMapper getModelMapper()
    {
        return modelMapper;
    }
}
