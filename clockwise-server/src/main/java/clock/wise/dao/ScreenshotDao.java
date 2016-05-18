package clock.wise.dao;

import clock.wise.model.Screenshot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreenshotDao extends CrudRepository<Screenshot, Long> {

    List<Screenshot> findByUserId(Long userId);

}
