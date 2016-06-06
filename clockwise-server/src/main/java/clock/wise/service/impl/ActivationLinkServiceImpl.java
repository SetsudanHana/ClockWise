package clock.wise.service.impl;

import clock.wise.dao.ActivationLinkDao;
import clock.wise.dao.CompanyDao;
import clock.wise.dto.ActivationLinkDto;
import clock.wise.enums.ActivationLinkStatus;
import clock.wise.enums.CompanyStatus;
import clock.wise.mapper.ActivationLinkModelMapperWrapper;
import clock.wise.model.ActivationLink;
import clock.wise.service.ActivationLinkService;
import clock.wise.utils.ActivationLinkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
public class ActivationLinkServiceImpl implements ActivationLinkService {
    @Autowired
    private ActivationLinkDao activationLinkDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ActivationLinkModelMapperWrapper activationLinkModelMapperWrapper;

    @Value( "${server.link.api.address}" )
    private String serverLinkApi;

    private static final String COMPANIES = "/companies";
    private static final String USERS = "/users";

    @Override
    @Transactional
    public ActivationLinkDto createLinkForCompany( final Long id ) {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setCreatedDate( new Date() );
        activationLink.setStatus( ActivationLinkStatus.NOT_ACTIVATED );
        activationLink.setCompany( companyDao.findOne( id ) );

        String hash = generateHash();
        activationLink.setHash( hash );
        activationLink.setLink( serverLinkApi + hash + COMPANIES );

        ActivationLink saved = activationLinkDao.save( activationLink );
        return activationLinkModelMapperWrapper.getModelMapper().map( saved, ActivationLinkDto.class );
    }

    @Override
    @Transactional
    public ActivationLinkDto activateCompanyByLink( final String hash ) {
        ActivationLink activationLink = activationLinkDao.findByHash( hash );
        if ( activationLink == null ) {
            throw new EntityNotFoundException( "Activation link has not been found" );
        }
        activationLink.setStatus( ActivationLinkStatus.ACTIVATED );
        activationLink.getCompany().setStatus( CompanyStatus.ACTIVE );

        ActivationLink saved = activationLinkDao.save( activationLink );
        return activationLinkModelMapperWrapper.getModelMapper().map( saved, ActivationLinkDto.class );
    }

    private String generateHash() {
        return ActivationLinkUtils.generateLinkHash();
    }
}
