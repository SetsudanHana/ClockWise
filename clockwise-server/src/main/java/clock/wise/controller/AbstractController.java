package clock.wise.controller;

import clock.wise.dto.ErrorDto;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

public abstract class AbstractController
{
    private final static Logger logger = Logger.getLogger( AbstractController.class );

    @ExceptionHandler( EntityNotFoundException.class )
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    protected ErrorDto handleEntityNotFoundException( EntityNotFoundException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( DataIntegrityViolationException.class )
    @ResponseStatus( value = HttpStatus.CONFLICT )
    protected ErrorDto handleDataIntegrityViolationException( DataIntegrityViolationException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( IllegalArgumentException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleIllegalArgumentException( IllegalArgumentException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( NumberFormatException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleNumberFormatException( NumberFormatException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( NullPointerException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleNullPointerException( NullPointerException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( MessagingException.class )
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    protected ErrorDto handleMessagingException( MessagingException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( IndexOutOfBoundsException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleIndexOutOfBoundsException( IndexOutOfBoundsException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( UsernameNotFoundException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    protected ErrorDto handleUsernameNotFoundException( UsernameNotFoundException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }

    @ExceptionHandler( BadCredentialsException.class )
    @ResponseStatus( value = HttpStatus.UNAUTHORIZED )
    protected ErrorDto handleBadCredentialsException( BadCredentialsException ex )
    {
        logger.error( "Exception: " + ex.getMessage() );
        return new ErrorDto( ex );
    }
}