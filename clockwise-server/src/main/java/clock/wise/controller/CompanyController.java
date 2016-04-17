package clock.wise.controller;

import clock.wise.dto.CompanyDto;
import clock.wise.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/companies" )
public class CompanyController
{
    @Autowired
    private CompanyService companyService;

    @RequestMapping( method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    public CompanyDto createCompany( @RequestBody final CompanyDto companyDto )
    {
        return companyService.createOrUpdate( companyDto );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public CompanyDto findById( @PathVariable( "id" ) final Long id )
    {
        return companyService.findById( id );
    }

    @RequestMapping( method = RequestMethod.GET )
    public CompanyDto findByName( @RequestParam( value = "name" ) final String name )
    {
        return companyService.findByName( name );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public CompanyDto updateCompany( @PathVariable( "id" ) final Long id, @RequestBody final CompanyDto companyDto )
    {
        companyDto.setId( id );
        return companyService.createOrUpdate( companyDto );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void removeCompanyById( @PathVariable( "id" ) final Long id )
    {
        companyService.removeCompanyById( id );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void removeCompanyByEntity( @RequestBody final CompanyDto companyDto )
    {
        companyService.removeCompanyByEntity( companyDto );
    }
}
