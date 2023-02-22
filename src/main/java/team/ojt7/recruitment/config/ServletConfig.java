package team.ojt7.recruitment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc	
@Configuration
public class ServletConfig implements WebMvcConfigurer {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/storage/**")
          .addResourceLocations("/storage/");
        
        registry
        	.addResourceHandler("/resources/**")
        	.addResourceLocations("classpath:/static/resources/");
    }
}
