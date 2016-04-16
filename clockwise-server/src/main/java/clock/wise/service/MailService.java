package clock.wise.service;

import clock.wise.dto.UserDto;
import clock.wise.enums.MailTemplateEnum;

public interface MailService
{
    void sendMessage( final UserDto userDto, final MailTemplateEnum mailTemplate );
}
