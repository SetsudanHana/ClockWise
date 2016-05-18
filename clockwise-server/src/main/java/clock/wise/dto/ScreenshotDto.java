package clock.wise.dto;

import java.util.Date;

public class ScreenshotDto {

    private Long id;
    private Long userId;
    private String base64Data;
    private Date date;

    public ScreenshotDto() {

    }

    public Date getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
