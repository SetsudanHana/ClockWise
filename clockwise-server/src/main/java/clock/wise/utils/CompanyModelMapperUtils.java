package clock.wise.utils;

import clock.wise.converter.CompanyConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyModelMapperUtils
{
    public ModelMapper modelMapper = new ModelMapper();

    public CompanyModelMapperUtils()
    {
        modelMapper.addConverter( new CompanyConverter() );
    }

    public ModelMapper getModelMapper()
    {
        return modelMapper;
    }
}
