package clock.wise.converter;

import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.model.Company;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyConverter extends AbstractConverter< Company, CompanyDto > {
    @Override
    protected CompanyDto convert( final Company company ) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );
        companyDto.setEmail( company.getEmail() );
        companyDto.setStatus( company.getStatus() );
        List< UserDto > usersDtoList = company.getUsers().stream().map( user -> new UserModelConverter().convert( user ) ).collect( Collectors.toList() );
        companyDto.setUsers( usersDtoList );

        return companyDto;
    }
}
