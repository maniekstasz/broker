package soa.common.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import soa.common.controller.IndexController;
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
	
	@Override
		protected void configureRepositoryRestConfiguration(
				RepositoryRestConfiguration config) {
			// TODO Auto-generated method stub
			config.setBaseUri(URI.create("/api"));
			config.useHalAsDefaultJsonMediaType(false);
			config.setDefaultMediaType(MediaType.APPLICATION_JSON);
			config.setReturnBodyOnCreate(true);
		}
}
