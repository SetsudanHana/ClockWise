package clock.wise.enums;

public enum MailTemplateEnum {
    HEADER( "Header" ),
    FOOTER( "Footer" ),

    NEW_USER_REGISTERED( "New user registered" ),
    USER_PASSWORD_UPDATE( "User password updated" ),
    USER_PASSWORD_RESET( "User password reset" ),

    NEW_COMPANY_CREATED_AND_NOT_ACTIVATED( "New company created and need to be activated via link" ),
    NEW_COMPANY_CREATED_AND_ACTIVATED( "New company created and is activated" ),

    COMPANY_ACTIVATED( "Company activated" ),
    COMPANY_DEACTIVATED( "Company deactivated" ),
    USER_ACTIVATED( "User activated" ),
    USER_DEACTIVATED( "User deactivated" ),

    USER_DELETED( "User has been deleted" ),
    COMPANY_DELETED( "Company has been deleted)" );


    private String value;

    MailTemplateEnum( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}