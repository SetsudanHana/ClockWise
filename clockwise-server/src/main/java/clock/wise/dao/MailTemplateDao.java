package clock.wise.dao;

import clock.wise.enums.MailTemplateEnum;
import clock.wise.model.MailTemplate;
import org.springframework.data.repository.CrudRepository;

public interface MailTemplateDao extends CrudRepository< MailTemplate, Long >
{
    MailTemplate findByMailType( final MailTemplateEnum mailType );
}