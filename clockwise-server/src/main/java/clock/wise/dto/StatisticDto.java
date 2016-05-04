package clock.wise.dto;

public class StatisticDto
{
    private Long id;
    private int mouseClickedCount;
    private int keyboardClickedCount;

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
}
