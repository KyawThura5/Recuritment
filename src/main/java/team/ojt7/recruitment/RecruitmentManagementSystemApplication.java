package team.ojt7.recruitment;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecruitmentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentManagementSystemApplication.class, args);
	}
	
	@Bean
	public PoolProperties poolProperties() {
		var poolProperties = new PoolProperties();
        poolProperties.setUrl("jdbc:mysql://localhost:3306/recruitment");
        poolProperties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        poolProperties.setUsername("burger");
        poolProperties.setPassword("Burger12345");
        poolProperties.setJmxEnabled(true);
        poolProperties.setTestWhileIdle(false);
        poolProperties.setTestOnBorrow(true);
        poolProperties.setValidationQuery("SELECT 1");
        poolProperties.setTestOnReturn(false);
        poolProperties.setValidationInterval(30000);
        poolProperties.setTimeBetweenEvictionRunsMillis(30000);
        poolProperties.setMaxActive(100);
        poolProperties.setInitialSize(10);
        poolProperties.setMaxWait(10000);
        poolProperties.setRemoveAbandonedTimeout(60);
        poolProperties.setMinEvictableIdleTimeMillis(30000);
        poolProperties.setMinIdle(10);
        poolProperties.setLogAbandoned(true);
        poolProperties.setRemoveAbandoned(true);
        return poolProperties;
	}
	
	@Bean
    public DataSource dataSource(@Autowired PoolProperties pool) {
        return new DataSource(pool);
    }

}
