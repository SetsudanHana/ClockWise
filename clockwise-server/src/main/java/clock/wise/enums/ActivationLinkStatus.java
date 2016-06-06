package clock.wise.enums;

public enum ActivationLinkStatus {
    ACTIVATED( "Link was used to activate" ),
    NOT_ACTIVATED( "Link is waiting for activation" );

    private String value;

    ActivationLinkStatus( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
