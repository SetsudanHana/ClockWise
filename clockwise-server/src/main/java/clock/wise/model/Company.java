package clock.wise.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = { "name", "email" } ) )
public class Company implements Serializable
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToMany( mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List< User > users = new ArrayList<>();

    public Company()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public List< User > getUsers()
    {
        return users;
    }

    public void setUsers( List< User > users )
    {
        this.users = users;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }
}
