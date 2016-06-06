package clock.wise.model;

import clock.wise.enums.CompanyStatus;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = { "name", "email" } ) )
public class Company implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    @Email
    private String email;

    @Column
    @Enumerated( EnumType.STRING )
    private CompanyStatus status;

    @OneToMany( mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List< User > users = new ArrayList<>();

    @OneToMany( mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List< ActivationLink > activationLinks = new ArrayList<>();

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List< User > getUsers() {
        return users;
    }

    public void setUsers( List< User > users ) {
        this.users = users;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public void setStatus( CompanyStatus status ) {
        this.status = status;
    }

    public boolean isActive() {
        return CompanyStatus.ACTIVE.equals( getStatus() );
    }

    public boolean isExpecting() {
        return CompanyStatus.EXPECTING.equals( getStatus() );
    }

    public boolean isDisabled() {
        return CompanyStatus.DISABLED.equals( getStatus() );
    }

    public List< ActivationLink > getActivationLinks() {
        return activationLinks;
    }

    public void setActivationLinks( List< ActivationLink > activationLinks ) {
        this.activationLinks = activationLinks;
    }
}
