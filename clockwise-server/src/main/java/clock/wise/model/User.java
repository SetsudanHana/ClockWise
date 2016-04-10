package clock.wise.model;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;

    protected User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
