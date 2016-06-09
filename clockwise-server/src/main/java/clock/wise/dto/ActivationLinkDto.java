package clock.wise.dto;

import clock.wise.enums.ActivationLinkStatus;

import java.util.Date;

public class ActivationLinkDto {
    private Long id;
    private String link;
    private Date createdDate;
    private ActivationLinkStatus status;
    private Long companyId;
    private Long userId;

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

    public ActivationLinkStatus getStatus() {
        return status;
    }

    public void setStatus( ActivationLinkStatus status ) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId( Long companyId ) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }
}
