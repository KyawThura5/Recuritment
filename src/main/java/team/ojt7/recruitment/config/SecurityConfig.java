package team.ojt7.recruitment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
    		.passwordEncoder(passwordEncoder())
    		.withUser(
				User.builder()
				.username("admin")
				.password("admin")
				.roles("ADMIN")
				.authorities("ADMIN")
				.build()
    		);
    	
//        auth.jdbcAuthentication()
//            .passwordEncoder(passwordEncoder())
//            .dataSource(dataSource)
//            .usersByUsernameQuery("SELECT `email`, `password`, `approved` FROM `user` WHERE `email` = ?")
//            .authoritiesByUsernameQuery("SELECT `email`, `role` FROM `user` WHERE `email` = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            
            .antMatchers("/login", "/signup", "/resources/**").permitAll()
            .antMatchers("/**").authenticated();

        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("employeeCode")
            .passwordParameter("password")
            .successForwardUrl("/")
            .defaultSuccessUrl("/", true)
            .failureUrl("/fail");

        http.logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/");

        http.exceptionHandling()
            .accessDeniedPage("/unauthorized");
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
}
