package clock.wise.model;

import clock.wise.enums.ActivationLinkStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = { "hash" } ) )
public class ActivationLink implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String link;

    @Column
    private String hash;

    @Column
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdDate;

    @Column
    @Enumerated( EnumType.STRING )
    private ActivationLinkStatus status;

    @OneToOne
    private Company company;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink( String link ) {
        this.link = link;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany( Company company ) {
        this.company = company;
    }

    public ActivationLinkStatus getStatus() {
        return status;
    }

    public void setStatus( ActivationLinkStatus status ) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash( String hash ) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }
}
