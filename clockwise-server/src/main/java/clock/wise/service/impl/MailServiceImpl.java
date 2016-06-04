package clock.wise.service.impl;

import clock.wise.dao.MailTemplateDao;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
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
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService
{
    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);
    private static final String MESSAGE_CONTENT_TYPE = "text/html; charset=UTF-8";

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public void sendMessage( final UserDto userDto, final MailTemplateEnum mailType )
    {
        try
        {
            MailTemplate mailTemplate = mailTemplateDao.findByMailType(mailType);
            Map< String, String > parameters = createParameters(mailTemplate, userDto);
            MimeMessage message = createMessage(mailTemplate, userDto.getEmail(), parameters);

            mailSender.send(message);
            logger.info("Email: " + mailType.getValue() + " to user: " + userDto.getEmail() + " has been sent");
        }
        catch ( MessagingException e )
        {
            logger.error("Error while sending email to user: " + userDto.getEmail());
            throw new MailSenderException("Error while sending email to user: " + userDto.getEmail());
        }
    }

    @Override
    @Transactional
    public void sendMessage( final CompanyDto companyDto, final MailTemplateEnum mailType )
    {
        try
        {
            MailTemplate mailTemplate = mailTemplateDao.findByMailType(mailType);
            Map< String, String > parameters = createParameters(mailTemplate, companyDto);
            MimeMessage message = createMessage(mailTemplate, companyDto.getEmail(), parameters);

            mailSender.send(message);
            logger.info("Email: " + mailType.getValue() + " to company: " + companyDto.getEmail() + " has been sent");
        }
        catch ( MessagingException e )
        {
            logger.error("Error while sending email to user: " + companyDto.getEmail());
            throw new MailSenderException("Error while sending email to user: " + companyDto.getEmail());
        }
    }

    private MimeMessage createMessage( final MailTemplate mailTemplate, final String email, final Map< String, String > parameters ) throws MessagingException
    {
        String mailContent = createMailContent(mailTemplate);

        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject(mailTemplate.getSubject());

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(email);

        VelocityContext context = new VelocityContext();
        if ( MapUtils.isNotEmpty(parameters) )
        {
            for ( final Map.Entry< String, String > entry : parameters.entrySet() )
            {
                context.put(entry.getKey(), entry.getValue());
            }
        }

        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "Error while evaluating email", mailContent);
        message.setContent(writer.toString(), MESSAGE_CONTENT_TYPE);

        return message;
    }

    private Map< String, String > createParameters( final MailTemplate mailTemplate, final UserDto userDto )
    {
        Map< String, String > parameters = new HashMap<>();
        switch ( mailTemplate.getMailType() )
        {
            case NEW_USER_REGISTERED:
                parameters.put("username", userDto.getUsername());
                parameters.put("email", userDto.getEmail());
                break;
            case USER_PASSWORD_UPDATE:
                parameters.put("username", userDto.getUsername());
                break;
            case USER_PASSWORD_RESET:
                parameters.put("username", userDto.getUsername());
                parameters.put("password", userDto.getPassword());
                break;
        }

        return parameters;
    }

    private Map< String, String > createParameters( final MailTemplate mailTemplate, final CompanyDto companyDto )
    {
        Map< String, String > parameters = new HashMap<>();
        switch ( mailTemplate.getMailType() )
        {
            case NEW_COMPANY_CREATED:
                parameters.put("company", companyDto.getName());
                parameters.put("link", "https://localhost:8080/create/new/user");
                break;
        }
        return parameters;
    }

    private String createMailContent( final MailTemplate mailTemplate )
    {
        StringBuilder builder = new StringBuilder();

        String mailBody = mailTemplate.getContent();
        String mailHeader = mailTemplateDao.findByMailType(MailTemplateEnum.HEADER).getContent();
        String mailFooter = mailTemplateDao.findByMailType(MailTemplateEnum.FOOTER).getContent();

        builder.append(mailHeader);
        builder.append(mailBody);
        builder.append(mailFooter);

        return builder.toString();
    }
}