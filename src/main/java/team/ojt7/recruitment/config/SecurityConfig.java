package team.ojt7.recruitment.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import team.ojt7.recruitment.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.jdbcAuthentication()
            .passwordEncoder(passwordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("SELECT `code`, `password`, if(`is_deleted` = 0, true, false) FROM `user` WHERE `code` = ?")
            .authoritiesByUsernameQuery("SELECT `code`, `role` FROM `user` WHERE `code` = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
        	.antMatchers("/user/**").hasAuthority("ADMIN")
            .antMatchers("/department/search").authenticated()
            .antMatchers("/department/**").hasAuthority("ADMIN")
            .antMatchers("/position/search").authenticated()
            .antMatchers("/position/**").hasAnyAuthority("ADMIN", "DEPARTMENT_HEAD")
            .antMatchers("/recruitmentresource/**/search").authenticated()
            .antMatchers("/recruitmentresource/**/detail").authenticated()
            .antMatchers("/recruitmentresource/**/**").hasAnyAuthority("ADMIN", "HIRING_MANAGER")
            .antMatchers("/team/search").authenticated()
            .antMatchers("/team/**").hasAnyAuthority("ADMIN", "DEPARTMENT_HEAD")
            .antMatchers("/vacancy/search").authenticated()
            .antMatchers("/vacancy/**").hasAuthority("DEPARTMENT_HEAD")
            .antMatchers("/login", "/resources/**").permitAll()
            .antMatchers("/", "/profile/**").authenticated();

        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("employeeCode")
            .passwordParameter("password")
            .successHandler(loginSuccessHandler);

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
