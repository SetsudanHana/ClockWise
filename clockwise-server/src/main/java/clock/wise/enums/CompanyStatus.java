package clock.wise.enums;

public enum CompanyStatus {
    ACTIVE( "Company is active" ),
    EXPECTING( "Company is waiting for activation" ),
    DISABLED( "Company is disabled" );

    private String value;

    CompanyStatus( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
