package clock.wise.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "clock.wise.db")
@EntityScan(basePackages = "clock.wise")
public class DataConfiguration {
}
