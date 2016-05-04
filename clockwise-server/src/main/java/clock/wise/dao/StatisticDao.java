package clock.wise.dao;


import clock.wise.model.Statistic;
import org.springframework.data.repository.CrudRepository;

public interface StatisticDao extends CrudRepository< Statistic, Long >
{
}
