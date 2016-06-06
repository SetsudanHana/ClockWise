package clock.wise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ErrorDto implements Serializable {
    @JsonProperty( "Error" )
    private String message;
    @JsonProperty( "Exception" )
    private String error;

    public ErrorDto( final Throwable cause ) {
        this.message = cause.getMessage();
        this.error = cause.getClass().getSimpleName();
    }
}