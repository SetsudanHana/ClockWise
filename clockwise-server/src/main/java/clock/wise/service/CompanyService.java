package clock.wise.service;

import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;

import java.util.List;

public interface CompanyService
{
    CompanyDto createOrUpdate( final CompanyDto companyDto );

    CompanyDto findByName( final String name );

    CompanyDto findById( final Long id );

    List< CompanyDto > findAll();

    List< UserDto > findAllCompanyUsers( final Long id );

    void removeCompanyById( final Long id );

    void removeCompanyByEntity( final CompanyDto company );
}
