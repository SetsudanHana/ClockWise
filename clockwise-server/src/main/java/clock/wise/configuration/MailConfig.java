package clock.wise.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig
{
    @Value( "${email.host}" )
    private String host;

    @Value( "${email.port}" )
    private Integer port;

    @Value( "${email.username}" )
    private String username;

    @Value( "${email.password}" )
    private String password;

    @Bean
    public JavaMailSender javaMailService()
    {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost( host );
        javaMailSender.setPort( port );
        javaMailSender.setUsername( username );
        javaMailSender.setPassword( password );
        javaMailSender.setDefaultEncoding( "UTF-8" );

        javaMailSender.setJavaMailProperties( getMailProperties() );

        return javaMailSender;
    }

    private Properties getMailProperties()
    {
        Properties properties = new Properties();
        properties.setProperty( "mail.transport.protocol", "smtp" );
        properties.setProperty( "mail.smtp.auth", "true" );
        properties.setProperty( "mail.smtp.starttls.enable", "true" );
        properties.setProperty( "mail.debug", "false" );
        properties.setProperty( "mail.smtp.ssl.trust", "smtp.gmail.com" );
        properties.setProperty( "mail.smtp.ssl.enable", "true" );

        return properties;
    }
}