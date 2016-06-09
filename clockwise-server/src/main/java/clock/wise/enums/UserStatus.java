package clock.wise.enums;

public enum UserStatus {
    ACTIVE( "User is active" ),
    EXPECTING( "User is waiting for activation" ),
    DISABLED( "User is disabled" );

    private String value;

    UserStatus( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
