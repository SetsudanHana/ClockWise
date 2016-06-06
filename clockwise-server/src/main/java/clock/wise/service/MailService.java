package clock.wise.service;

import clock.wise.enums.MailTemplateEnum;

import java.util.Map;

public interface MailService {
    void sendMessage( final Map< String, String > parameters, final MailTemplateEnum mailType );
}
