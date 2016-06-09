package clock.wise.dto;

import clock.wise.enums.UserStatus;
import clock.wise.model.roles.Role;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Long companyId;
    private UserStatus status;
    private Long activationLinkId;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole( Role role ) {
        this.role = role;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId( Long companyId ) {
        this.companyId = companyId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus( UserStatus status ) {
        this.status = status;
    }

    public Long getActivationLinkId() {
        return activationLinkId;
    }

    public void setActivationLinkId( Long activationLinkId ) {
        this.activationLinkId = activationLinkId;
    }
}
