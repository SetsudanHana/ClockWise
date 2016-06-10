package clock.wise.dto;

import clock.wise.enums.CompanyStatus;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
    private Long id;
    private String name;
    private String email;
    private CompanyStatus status;
    private List< UserDto > users = new ArrayList<>();

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

    public List< UserDto > getUsers() {
        return users;
    }

    public void setUsers( List< UserDto > users ) {
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
}
