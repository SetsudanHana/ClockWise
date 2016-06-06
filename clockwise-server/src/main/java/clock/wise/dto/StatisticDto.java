package clock.wise.dto;

import java.util.Date;

public class StatisticDto {
    private Long id;
    private int mouseClickedCount;
    private int keyboardClickedCount;
    private int mouseMovementCount;
    private Date date;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }
}
