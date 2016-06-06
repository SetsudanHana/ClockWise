package clock.wise.model.roles;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_SUPER_ADMIN( "ROLE_SUPER_ADMIN" ),
    ROLE_ADMIN( "ROLE_ADMIN" ),
    ROLE_TEAM_LEADER( "ROLE_TEAM_LEADER" ),
    ROLE_DEVELOPER( "ROLE_DEVELOPER" ),;

    String authority;

    private Role( String authority ) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
