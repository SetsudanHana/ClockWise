package clock.wise.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Statistic implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private int mouseClickedCount;

    @Column
    private int mouseMovementCount;

    @Column
    private int keyboardClickedCount;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "userId" )
    private User user;

    @Column
    @Temporal( TemporalType.TIMESTAMP )
    private Date date;

    public Statistic() {
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public int getMouseClickedCount() {
        return mouseClickedCount;
    }

    public void setMouseClickedCount( int mouseClickedCount ) {
        this.mouseClickedCount = mouseClickedCount;
    }

    public int getKeyboardClickedCount() {
        return keyboardClickedCount;
    }

    public void setKeyboardClickedCount( int keyboardClickedCount ) {
        this.keyboardClickedCount = keyboardClickedCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public int getMouseMovementCount() {
        return mouseMovementCount;
    }

    public void setMouseMovementCount( int mouseMovementCount ) {
        this.mouseMovementCount = mouseMovementCount;
    }
}
