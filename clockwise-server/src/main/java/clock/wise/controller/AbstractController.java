package clock.wise.controller;

import clock.wise.dtos.ErrorDto;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

public abstract class AbstractController
{
    @ExceptionHandler( EntityNotFoundException.class )
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    protected ErrorDto handleEntityNotFoundException( EntityNotFoundException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( DataIntegrityViolationException.class )
    @ResponseStatus( value = HttpStatus.CONFLICT )
    protected ErrorDto handleDataIntegrityViolationException( DataIntegrityViolationException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( IllegalArgumentException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleIllegalArgumentException( IllegalArgumentException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( NumberFormatException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleNumberFormatException( NumberFormatException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( NullPointerException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleNullPointerException( NullPointerException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( MessagingException.class )
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    protected ErrorDto handleMessagingException( MessagingException ex )
    {
        return new ErrorDto( ex );
    }

    @ExceptionHandler( IndexOutOfBoundsException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleIndexOutOfBoundsException( IndexOutOfBoundsException ex )
    {
        return new ErrorDto( ex );
    }
}