package clock.wise.utils;

import clock.wise.converter.UserModelConverter;
import org.modelmapper.ModelMapper;

public class UserModelMapperUtils
{
    public static ModelMapper modelMapper = new ModelMapper();

    public UserModelMapperUtils()
    {
        modelMapper.addConverter( new UserModelConverter() );
    }
}
