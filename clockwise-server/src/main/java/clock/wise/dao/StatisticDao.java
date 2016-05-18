package clock.wise.dao;


import clock.wise.model.Statistic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatisticDao extends CrudRepository<Statistic, Long> {

    List<Statistic> findByUserId(Long userId);

}
