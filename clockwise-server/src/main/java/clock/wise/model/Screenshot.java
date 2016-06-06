package clock.wise.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Screenshot {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Basic( fetch = FetchType.LAZY, optional = false )
    private byte[] image;

    @Lob
    @Basic( fetch = FetchType.LAZY )
    private byte[] thumbnail;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "userId" )
    private User user;

    @Temporal( TemporalType.TIMESTAMP )
    @Basic( optional = false )
    private Date date;

    public Screenshot() {

    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getImage() {
        return image;
    }

    public User getUser() {
        return user;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public void setImage( byte[] image ) {
        this.image = image;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public void setThumbnail( byte[] thumbnail ) {
        this.thumbnail = thumbnail;
    }
}
