package clock.wise.service.impl;

import clock.wise.dao.CompanyDao;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.mapper.UserModelMapperWrapper;
import clock.wise.model.Company;
import clock.wise.model.User;
import clock.wise.service.CompanyService;
import clock.wise.utils.CompanyModelMapperUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService
{
    private final static Logger logger = Logger.getLogger( CompanyServiceImpl.class );

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private CompanyModelMapperUtils companyModelMapper;
    @Autowired
    private UserModelMapperWrapper userModelMapperWrapper;

    @Override
    @Transactional
    public CompanyDto createOrUpdate( final CompanyDto companyDto )
    {
        Company company = companyModelMapper.getModelMapper().map( companyDto, Company.class );
        Company saved = companyDao.save( company );
        logger.info( "Company " + saved.getName() + " has been changed" );

        return companyModelMapper.getModelMapper().map( saved, CompanyDto.class );
    }

    @Override
    @Transactional
    public CompanyDto findByName( final String name )
    {
        if ( name == null || name.isEmpty() )
        {
            logger.error( "Error while searching company by name: " + name );
            throw new IllegalArgumentException( "Company's name cannot be null or empty" );
        }

        return companyModelMapper.getModelMapper().map( companyDao.findByName( name ), CompanyDto.class );
    }

    @Override
    @Transactional
    public CompanyDto findById( final Long id )
    {
        if ( id == null )
        {
            logger.error( "Error while searching company by id: " + id );
            throw new IllegalArgumentException( "Id cannot be null" );
        }

        return companyModelMapper.getModelMapper().map( companyDao.findOne( id ), CompanyDto.class );
    }

    @Override
    @Transactional
    public List< CompanyDto > findAll()
    {
        List< CompanyDto > companiesListDto = new ArrayList<>();
        Iterable< Company > companies = companyDao.findAll();
        for ( final Company company : companies )
        {
            CompanyDto companyDto = companyModelMapper.getModelMapper().map( company, CompanyDto.class );
            companiesListDto.add( companyDto );
        }

        return companiesListDto;
    }

    @Override
    @Transactional
    public List< UserDto > findAllCompanyUsers( final Long id )
    {
        Company company = companyDao.findOne( id );
        List< UserDto > companyUsers = new ArrayList<>();
        for ( final User user : company.getUsers() )
        {
            UserDto userDto = userModelMapperWrapper.getModelMapper().map(user, UserDto.class);
            companyUsers.add( userDto );
        }

        return companyUsers;
    }

    @Override
    @Transactional
    public void removeCompanyById( final Long id )
    {
        if ( id == null )
        {
            logger.error( "Error while removing company by id: " + id );
            throw new IllegalArgumentException( "Id cannot be null" );
        }

        companyDao.delete( id );
        logger.info( "Company " + id + " removed" );
    }

    @Override
    @Transactional
    public void removeCompanyByEntity( final CompanyDto companyDto )
    {
        if ( !companyDao.exists( companyDto.getId() ) )
        {
            logger.error( "Company with id: " + companyDto.getId() + " does not exist" );
            throw new EntityNotFoundException( "Company with id: " + companyDto.getId() + " does not exist" );
        }

        Company company = companyDao.findOne( companyDto.getId() );
        companyDao.delete( company );
        logger.info( "Company " + companyDto.getId() + " removed" );
    }
}
