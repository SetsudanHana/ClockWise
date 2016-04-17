package clock.wise.converter;

import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.mapper.UserModelMapperWrapper;
import clock.wise.model.Company;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CompanyConverter extends AbstractConverter< Company, CompanyDto >
{
    @Autowired
    private UserModelMapperWrapper userModelMapperWrapper;

    @Override
    protected CompanyDto convert( final Company company )
    {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );

        if ( company.getUsers() != null && !company.getUsers().isEmpty() )
        {
            List< UserDto > usersListDto = new ArrayList<>();
            for ( final User user : company.getUsers() )
            {
                UserDto userDto = userModelMapperWrapper.getModelMapper().map(user, UserDto.class);
                usersListDto.add( userDto );
            }
            companyDto.setUsers( usersListDto );
        }

        return companyDto;
    }
}
