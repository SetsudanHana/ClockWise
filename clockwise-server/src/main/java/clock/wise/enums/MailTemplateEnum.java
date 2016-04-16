package clock.wise.enums;

public enum MailTemplateEnum
{
    HEADER( "Header" ),
    FOOTER( "Footer" ),
    NEW_USER_REGISTERED( "New user registered" ),
    USER_PASSWORD_UPDATE( "User password updated" ),
    USER_PASSWORD_RESET( "User password reset" );

    private String value;

    MailTemplateEnum( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}