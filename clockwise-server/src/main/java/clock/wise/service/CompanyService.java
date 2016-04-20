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

    UserDto findCompanyUserById( final Long companyId, final Long userId );

    void addUserToCompany( final Long companyId, final Long userId );

    void removeCompanyById( final Long id );

    void removeCompanyByEntity( final CompanyDto company );
}
