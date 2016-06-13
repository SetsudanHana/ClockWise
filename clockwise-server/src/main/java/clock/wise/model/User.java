package clock.wise.model;

import clock.wise.enums.UserStatus;
import clock.wise.model.roles.Role;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table( name = "Users", uniqueConstraints = @UniqueConstraint( columnNames = { "username", "email" } ) )
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Email
    private String email;

    @Column
    private Role role;

    @Column
    @Enumerated( EnumType.STRING )
    private UserStatus status;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "companyId" )
    private Company company;

    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "activationLink_id" )
    private ActivationLink activationLink;

    public User() {
    }

    public User( String username, String password, String email, Role role ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        return Collections.singletonList( role );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setRole( Role role ) {
        this.role = role;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany( Company company ) {
        this.company = company;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus( UserStatus status ) {
        this.status = status;
    }

    public ActivationLink getActivationLink() {
        return activationLink;
    }

    public void setActivationLink( ActivationLink activationLink ) {
        this.activationLink = activationLink;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals( getStatus() );
    }

    public boolean isExpecting() {
        return UserStatus.EXPECTING.equals( getStatus() );
    }

    public boolean isDisabled() {
        return UserStatus.DISABLED.equals( getStatus() );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
