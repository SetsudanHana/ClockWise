package clock.wise.model;

import clock.wise.model.roles.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private Role role;
    private boolean expired;
    private boolean locked;
    private boolean enabled;
    private boolean credentialExpired;

    protected User() {

    }

    public User(String username, String password, Role role, boolean expired, boolean locked, boolean enabled, boolean credentialExpired) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.expired = expired;
        this.locked = locked;
        this.enabled = enabled;
        this.credentialExpired = credentialExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
