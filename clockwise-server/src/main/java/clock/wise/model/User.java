package clock.wise.model;

import clock.wise.model.roles.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table( name = "Users", uniqueConstraints = @UniqueConstraint( columnNames = { "username", "email" } ) )
public class User implements UserDetails, Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "companyId" )
    private Company company;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List< Statistic > statistic = new ArrayList<>();

    public User()
    {
    }

    public User( String username, String password, String email, Role role )
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities()
    {
        return Collections.singletonList( role );
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    public Long getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public Role getRole()
    {
        return role;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void setRole( Role role )
    {
        this.role = role;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public Company getCompany()
    {
        return company;
    }

    public void setCompany( Company company )
    {
        this.company = company;
    }

    public List< Statistic > getStatistic()
    {
        return statistic;
    }

    public void setStatistic( List< Statistic > statistic )
    {
        this.statistic = statistic;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

}
