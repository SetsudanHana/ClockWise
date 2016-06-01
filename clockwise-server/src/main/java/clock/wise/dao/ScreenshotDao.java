package clock.wise.dao;

import clock.wise.model.Screenshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreenshotDao extends CrudRepository<Screenshot, Long> {

    List<Screenshot> findByUserId(Long userId, Sort sort);

    Page<Screenshot> findByUserId(Long userId, Pageable pageable);

}
