package clock.wise.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = { "name" } ) )
public class Company implements Serializable
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @OneToMany( mappedBy = "company", cascade = CascadeType.ALL )
    private List< User > users;

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
}
