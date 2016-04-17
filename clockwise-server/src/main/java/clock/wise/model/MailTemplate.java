package clock.wise.model;

import clock.wise.enums.MailTemplateEnum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MailTemplate implements Serializable
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    @Enumerated( EnumType.STRING )
    private MailTemplateEnum mailType;

    @Column( length = 2500 )
    private String content;

    @Column
    private String subject;

    public MailTemplate()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public MailTemplateEnum getMailType()
    {
        return mailType;
    }

    public void setMailType( MailTemplateEnum mailType )
    {
        this.mailType = mailType;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }
}