package team.ojt7.recruitment;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import team.ojt7.recruitment.event.UserUpdateEvent;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.security.IdUsernamePasswordAuthentication;

@SpringBootApplication
public class RecruitmentManagementSystemApplication {
	
	@Autowired
	private HttpSession session;

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentManagementSystemApplication.class, args);
	}
	
	@Bean
	public MessageSource messageSource() {
		var messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
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
	
	@EventListener(UserUpdateEvent.class)
	public void listenUserUpdateEvent(UserUpdateEvent event) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		IdUsernamePasswordAuthentication auth = (IdUsernamePasswordAuthentication) securityContext.getAuthentication();
		User user = event.getUser();
		if (Objects.equals(user.getId(), auth.getId())) {
			session.setAttribute("loginUser", user);
			auth = new IdUsernamePasswordAuthentication(user.getId(), user.getCode(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
			securityContext.setAuthentication(auth);
		}
		
	}

}
