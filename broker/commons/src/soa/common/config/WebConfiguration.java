package soa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import soa.common.security.UserSecurityEventHandler;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class WebConfiguration extends RepositoryRestMvcConfiguration {
//	@Override
//	public void configureContentNegotiation(
//			ContentNegotiationConfigurer configurer) {
//		configurer.defaultContentType(MediaType.APPLICATION_JSON);
//	}

	@Bean
	public UserSecurityEventHandler userSecurityEventHandler() {
		return new UserSecurityEventHandler();
	}
}
