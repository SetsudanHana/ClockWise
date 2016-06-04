package clock.wise.service.impl;

import clock.wise.dao.CompanyDao;
import clock.wise.dao.UserDao;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.enums.MailTemplateEnum;
import clock.wise.mapper.CompanyModelMapperWrapper;
import clock.wise.mapper.UserModelMapperWrapper;
import clock.wise.model.Company;
import clock.wise.model.User;
import clock.wise.service.CompanyService;
import clock.wise.service.MailService;
import clock.wise.utils.ListUtils;
import clock.wise.utils.LongUtils;
import org.apache.commons.lang.StringUtils;
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
    private final static Logger logger = Logger.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyModelMapperWrapper companyModelMapperWrapper;
    @Autowired
    private UserModelMapperWrapper userModelMapperWrapper;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public CompanyDto createOrUpdate( final CompanyDto companyDto )
    {
        Company company = companyModelMapperWrapper.getModelMapper().map(companyDto, Company.class);
        if ( ListUtils.isNotEmpty(company.getUsers()) )
        {
            for ( final User user : company.getUsers() )
            {
                user.setCompany(company);
            }
        }

        Company saved = companyDao.save(company);
        if ( companyDao.exists(saved.getId()) )
        {
            logger.info("Company " + saved.getName() + " has been changed");
        }

        mailService.sendMessage(companyDto, MailTemplateEnum.NEW_COMPANY_CREATED);

        return companyModelMapperWrapper.getModelMapper().map(saved, CompanyDto.class);
    }

    @Override
    @Transactional
    public CompanyDto findByName( final String name )
    {
        if ( StringUtils.isNotEmpty(name) )
        {
            logger.error("Error while searching company by name: " + name);
            throw new IllegalArgumentException("Company's name cannot be null or empty");
        }

        return companyModelMapperWrapper.getModelMapper().map(companyDao.findByName(name), CompanyDto.class);
    }

    @Override
    @Transactional
    public CompanyDto findById( final Long id )
    {
        if ( LongUtils.isNotEmpty(id) )
        {
            logger.error("Error while searching company by id: " + id);
            throw new IllegalArgumentException("Id cannot be null");
        }

        return companyModelMapperWrapper.getModelMapper().map(companyDao.findOne(id), CompanyDto.class);
    }

    @Override
    @Transactional
    public List< CompanyDto > findAll()
    {
        List< CompanyDto > companiesListDto = new ArrayList<>();
        Iterable< Company > companies = companyDao.findAll();

        if ( companies == null )
        {
            throw new EntityNotFoundException("Ther is no companies in database");
        }

        for ( final Company company : companies )
        {
            CompanyDto companyDto = companyModelMapperWrapper.getModelMapper().map(company, CompanyDto.class);
            companiesListDto.add(companyDto);
        }

        return companiesListDto;
    }

    @Override
    @Transactional
    public List< UserDto > findAllCompanyUsers( final Long id )
    {
        List< UserDto > companyUsers = new ArrayList<>();

        List< User > users = userDao.findByCompanyId(id);
        if ( ListUtils.isEmpty(users) )
        {
            throw new EntityNotFoundException("There is no users in database");
        }

        for ( final User user : users )
        {
            UserDto userDto = userModelMapperWrapper.getModelMapper().map(user, UserDto.class);
            companyUsers.add(userDto);
        }

        return companyUsers;
    }

    @Override
    @Transactional
    public UserDto findCompanyUserById( final Long companyId, final Long userId )
    {
        if ( LongUtils.isEmpty(companyId) || LongUtils.isEmpty(userId) )
        {
            logger.error("UserId or CompanyId cannot be null");
            throw new IllegalArgumentException("UserId or CompanyId cannot be null");
        }

        User companyUser = companyDao.findCompanyUserById(companyId, userId);

        return userModelMapperWrapper.getModelMapper().map(companyUser, UserDto.class);
    }

    @Override
    @Transactional
    public void addUserToCompany( final Long companyId, final Long userId )
    {
        if ( LongUtils.isEmpty(companyId) || LongUtils.isEmpty(userId) )
        {
            logger.error("UserId or CompanyId cannot be null");
            throw new IllegalArgumentException("UserId or CompanyId cannot be null");
        }

        if ( !companyDao.exists(companyId) || !userDao.exists(userId) )
        {
            logger.error("Company or user does not exist in database");
            throw new EntityNotFoundException("Company or user does not exist in database");
        }

        Company company = companyDao.findOne(companyId);
        User user = userDao.findOne(userId);
        user.setCompany(company);

        logger.info("User id: " + userId + " has been added to company id: " + companyId);
    }

    @Override
    @Transactional
    public void removeCompanyById( final Long id )
    {
        if ( LongUtils.isEmpty(id) )
        {
            logger.error("Error while removing company by id: " + id);
            throw new IllegalArgumentException("Id cannot be null");
        }
        else if ( !companyDao.exists(id) )
        {
            logger.error("Company with id: " + id + " does not exist");
            throw new EntityNotFoundException("Company with id: " + id + " does not exist");
        }

        companyDao.delete(id);
        logger.info("Company " + id + " removed");
    }

    @Override
    @Transactional
    public void removeCompanyByEntity( final CompanyDto companyDto )
    {
        if ( !companyDao.exists(companyDto.getId()) )
        {
            logger.error("Company with id: " + companyDto.getId() + " does not exist");
            throw new EntityNotFoundException("Company with id: " + companyDto.getId() + " does not exist");
        }

        Company company = companyDao.findOne(companyDto.getId());
        companyDao.delete(company);
        logger.info("Company " + companyDto.getId() + " removed");
    }
}
