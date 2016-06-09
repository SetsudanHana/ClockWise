package clock.wise.service.impl;

import clock.wise.dao.ActivationLinkDao;
import clock.wise.dao.CompanyDao;
import clock.wise.dao.UserDao;
import clock.wise.dto.ActivationLinkDto;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.enums.ActivationLinkStatus;
import clock.wise.enums.CompanyStatus;
import clock.wise.enums.UserStatus;
import clock.wise.mapper.ActivationLinkModelMapperWrapper;
import clock.wise.mapper.CompanyModelMapperWrapper;
import clock.wise.mapper.UserModelMapperWrapper;
import clock.wise.model.ActivationLink;
import clock.wise.model.Company;
import clock.wise.model.User;
import clock.wise.service.ActivationLinkService;
import clock.wise.utils.ActivationLinkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class ActivationLinkServiceImpl implements ActivationLinkService {
    @Autowired
    private ActivationLinkDao activationLinkDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivationLinkModelMapperWrapper activationLinkModelMapperWrapper;
    @Autowired
    private UserModelMapperWrapper userModelMapperWrapper;
    @Autowired
    private CompanyModelMapperWrapper companyModelMapperWrapper;

    @Value( "${server.link.api.address}" )
    private String serverLinkApi;

    private static final String COMPANIES = "/companies";
    private static final String USERS = "/users";

    @Override
    @Transactional
    public ActivationLinkDto createLinkForCompany( final Long companyId ) {
        ActivationLink activationLink = createLink();
        activationLink.setLink( serverLinkApi + activationLink.getHash() + COMPANIES );
        Company company = companyDao.findOne( companyId );
        activationLink.setCompany( company );
        company.setActivationLink( activationLink );

        return saveLinkAndGetLinkDto( activationLink );
    }

    @Override
    @Transactional
    public ActivationLinkDto createLinkForUser( final Long userId ) {
        ActivationLink activationLink = createLink();
        activationLink.setLink( serverLinkApi + activationLink.getHash() + USERS );
        User user = userDao.findOne( userId );
        activationLink.setUser( user );
        user.setActivationLink( activationLink );

        return saveLinkAndGetLinkDto( activationLink );
    }

    @Override
    @Transactional
    public CompanyDto activateCompanyByLink( final String hash ) {
        if ( StringUtils.isEmpty( hash ) ) {
            throw new IllegalArgumentException( "Activate link hash is null. Company cannot be activate." );
        }

        ActivationLink activationLink = prepareActivationLink( hash );
        Company company = activationLink.getCompany();
        company.setStatus( CompanyStatus.ACTIVE );

        company.setActivationLink( null );
        activationLinkDao.delete( activationLink.getId() );
        Company saved = companyDao.save( company );

        return companyModelMapperWrapper.getModelMapper().map( saved, CompanyDto.class );
    }

    @Override
    @Transactional
    public UserDto activateUserByLink( final String hash ) {
        if ( StringUtils.isEmpty( hash ) ) {
            throw new IllegalArgumentException( "Activate link hash is null. User cannot be activate." );
        }

        ActivationLink activationLink = prepareActivationLink( hash );
        User user = activationLink.getUser();
        user.setStatus( UserStatus.ACTIVE );

        user.setActivationLink( null );
        activationLinkDao.delete( activationLink.getId() );
        User saved = userDao.save( user );

        return userModelMapperWrapper.getModelMapper().map( saved, UserDto.class );
    }

    private ActivationLink prepareActivationLink( final String hash ) {
        ActivationLink activationLink = activationLinkDao.findByHash( hash );
        if ( activationLink == null ) {
            throw new EntityNotFoundException( "Activation link has not been found" );
        }
        return activationLink;
    }

    private ActivationLinkDto saveLinkAndGetLinkDto( final ActivationLink activationLink ) {
        ActivationLink saved = activationLinkDao.save( activationLink );
        return activationLinkModelMapperWrapper.getModelMapper().map( saved, ActivationLinkDto.class );
    }

    private ActivationLink createLink() {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setCreatedDate( new Date() );
        activationLink.setStatus( ActivationLinkStatus.NOT_ACTIVATED );
        activationLink.setHash( generateActivationLinkHash() );

        return activationLink;
    }

    private String generateActivationLinkHash() {
        return ActivationLinkUtils.generateLinkHash();
    }
}
