package clock.wise.service.impl;

import clock.wise.dao.MailTemplateDao;
import clock.wise.enums.MailTemplateEnum;
import clock.wise.exceptions.MailSenderException;
import clock.wise.model.MailTemplate;
import clock.wise.service.MailService;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = Logger.getLogger( MailServiceImpl.class );
    private static final String MESSAGE_CONTENT_TYPE = "text/html; charset=UTF-8";

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMessage( final Map< String, String > params, final MailTemplateEnum mailType ) {
        try {
            MailTemplate mailTemplate = mailTemplateDao.findByMailType( mailType );
            if ( mailTemplate == null ) {
                throw new EntityNotFoundException( "Mail template does not exist" );
            }
            Map< String, String > parameters = createParameters( mailTemplate, params );
            MimeMessage message = createMessage( mailTemplate, params.get( "email" ), parameters );

            mailSender.send( message );
            logger.info( "Email: " + mailType.getValue() + " to: " + params.get( "email" ) + " has been sent" );
        }
        catch ( MessagingException e ) {
            logger.error( "Error while sending email: " + params.get( "email" ) );
            throw new MailSenderException( "Error while sending email: " + params.get( "email" ) );
        }
    }

    private MimeMessage createMessage( final MailTemplate mailTemplate, final String email, final Map< String, String > parameters ) throws MessagingException {
        String mailContent = createMailContent( mailTemplate );

        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject( mailTemplate.getSubject() );

        MimeMessageHelper messageHelper = new MimeMessageHelper( message, true );
        messageHelper.setTo( email );

        VelocityContext context = new VelocityContext();
        if ( MapUtils.isNotEmpty( parameters ) ) {
            for ( final Map.Entry< String, String > entry : parameters.entrySet() ) {
                context.put( entry.getKey(), entry.getValue() );
            }
        }

        StringWriter writer = new StringWriter();
        Velocity.evaluate( context, writer, "Error while evaluating email", mailContent );
        message.setContent( writer.toString(), MESSAGE_CONTENT_TYPE );

        return message;
    }

    private Map< String, String > createParameters( final MailTemplate mailTemplate, final Map< String, String > params ) {
        Map< String, String > parameters = new HashMap<>();
        switch ( mailTemplate.getMailType() ) {
            case NEW_USER_REGISTERED:
                parameters.put( "username", params.get( "username" ) );
                parameters.put( "email", params.get( "email" ) );
                parameters.put( "link", params.get( "link" ) );
                break;
            case USER_PASSWORD_UPDATE:
                parameters.put( "username", params.get( "username" ) );
                break;
            case USER_PASSWORD_RESET:
                parameters.put( "username", params.get( "username" ) );
                parameters.put( "password", params.get( "password" ) );
                break;
            case USER_ACTIVATED:
                parameters.put( "username", params.get( "username" ) );
                break;
            case USER_DEACTIVATED:
                parameters.put( "username", params.get( "username" ) );
                break;
            case USER_DELETED:
                parameters.put( "username", params.get( "username" ) );
                break;

            case NEW_COMPANY_CREATED_AND_NOT_ACTIVATED:
                parameters.put( "company", params.get( "company" ) );
                parameters.put( "link", params.get( "link" ) );
                break;
            case NEW_COMPANY_CREATED_AND_ACTIVATED:
                parameters.put( "company", params.get( "company" ) );
                break;
            case COMPANY_ACTIVATED:
                parameters.put( "company", params.get( "name" ) );
                break;
            case COMPANY_DEACTIVATED:
                parameters.put( "company", params.get( "name" ) );
                break;
            case COMPANY_DELETED:
                parameters.put( "company", params.get( "name" ) );
                break;
        }

        return parameters;
    }

    private String createMailContent( final MailTemplate mailTemplate ) {
        StringBuilder builder = new StringBuilder();

        String mailBody = mailTemplate.getContent();
        String mailHeader = mailTemplateDao.findByMailType( MailTemplateEnum.HEADER ).getContent();
        String mailFooter = mailTemplateDao.findByMailType( MailTemplateEnum.FOOTER ).getContent();

        builder.append( mailHeader );
        builder.append( mailBody );
        builder.append( mailFooter );

        return builder.toString();
    }
}