package spring.app;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfigSpringJdbc {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public LocationDaoJdbcTemplate springJdbcLocationDao() {
        return new LocationDaoJdbcTemplate(dataSource());
    }

    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        return flyway;
    }
}
