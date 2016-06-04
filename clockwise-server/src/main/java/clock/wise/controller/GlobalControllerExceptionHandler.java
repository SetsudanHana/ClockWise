package clock.wise.controller;

import clock.wise.dto.ErrorDto;
import clock.wise.exceptions.InvalidPasswordException;
import clock.wise.exceptions.MailSenderException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice( annotations = RestController.class )
public class GlobalControllerExceptionHandler
{
    private static final Logger logger = Logger.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler( EntityNotFoundException.class )
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    @ResponseBody
    protected ErrorDto handleEntityNotFoundException( EntityNotFoundException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( DataIntegrityViolationException.class )
    @ResponseStatus( value = HttpStatus.CONFLICT )
    @ResponseBody
    protected ErrorDto handleDataIntegrityViolationException( DataIntegrityViolationException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( IllegalArgumentException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleIllegalArgumentException( IllegalArgumentException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( NumberFormatException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleNumberFormatException( NumberFormatException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( NullPointerException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleNullPointerException( NullPointerException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( MessagingException.class )
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    @ResponseBody
    protected ErrorDto handleMessagingException( MessagingException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( IndexOutOfBoundsException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleIndexOutOfBoundsException( IndexOutOfBoundsException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( UsernameNotFoundException.class )
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    @ResponseBody
    protected ErrorDto handleUsernameNotFoundException( UsernameNotFoundException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( BadCredentialsException.class )
    @ResponseStatus( value = HttpStatus.UNAUTHORIZED )
    @ResponseBody
    protected ErrorDto handleBadCredentialsException( BadCredentialsException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( InvalidPasswordException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleInvalidPasswordException( InvalidPasswordException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( MailSenderException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleMailSenderException( MailSenderException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }

    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    @ResponseBody
    protected ErrorDto handleConstraintViolationException( ConstraintViolationException ex )
    {
        logger.error("Exception: " + ex.getMessage());
        return new ErrorDto(ex);
    }
}
