package clock.wise.converter;

import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.model.Company;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

import java.util.ArrayList;
import java.util.List;

public class CompanyConverter extends AbstractConverter< Company, CompanyDto >
{
    @Override
    protected CompanyDto convert( final Company company )
    {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );
        List< UserDto > usersDtoList = new ArrayList<>();
        for ( User user : company.getUsers() )
        {
            usersDtoList.add( new UserModelConverter().convert( user ) );
        }
        companyDto.setUsers( usersDtoList );
        return companyDto;
    }
}
