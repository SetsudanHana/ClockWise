package clock.wise.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Statistic implements Serializable
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private int mouseClickedCount;

    @Column
    private int keyboardClickedCount;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    public Statistic()
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

    public int getMouseClickedCount()
    {
        return mouseClickedCount;
    }

    public void setMouseClickedCount( int mouseClickedCount )
    {
        this.mouseClickedCount = mouseClickedCount;
    }

    public int getKeyboardClickedCount()
    {
        return keyboardClickedCount;
    }

    public void setKeyboardClickedCount( int keyboardClickedCount )
    {
        this.keyboardClickedCount = keyboardClickedCount;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }
}
