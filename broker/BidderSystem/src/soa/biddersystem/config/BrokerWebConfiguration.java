package soa.biddersystem.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import soa.biddersystem.eventhandlers.OfferEventHandler;
import soa.biddersystem.eventhandlers.PremiseEventHandler;
import soa.biddersystem.eventhandlers.ReservationEventHandler;
import soa.biddersystem.model.Premise;
import soa.common.config.WebConfiguration;
import soa.common.controller.MethodNotAllowedInterceptor;

@Configuration
public class BrokerWebConfiguration extends WebConfiguration{

	@Bean
	public ReservationEventHandler reservationEventHandler() {
		return new ReservationEventHandler();
	}

	@Bean 
	public PremiseEventHandler premiseEventHandler(){
		return new PremiseEventHandler();
	}
	@Bean
	public OfferEventHandler offerEventHandler(){
		return new OfferEventHandler();
	}
	
	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		super.configureRepositoryRestConfiguration(config);
		config.exposeIdsFor(Premise.class);
	}
	
	@Override
	@Bean
	public RequestMappingHandlerMapping repositoryExporterHandlerMapping() {
		RequestMappingHandlerMapping mapping = super
				.repositoryExporterHandlerMapping();
		MethodNotAllowedInterceptor mnaInterceptor = new MethodNotAllowedInterceptor();
		mnaInterceptor.addInterceptedUrl("/users/[0-9]*", "PUT", "PATCH",
				"GET", "DELETE");
		mnaInterceptor.addInterceptedUrl("/users", "GET");
		
		mnaInterceptor.addInterceptedUrl("/premises/[0-9]*", "PUT");
		
		mnaInterceptor.addInterceptedUrl("/premiseReservations/[0-9]*", "DELETE", "PUT");
		
		mnaInterceptor.addInterceptedUrl("/offers/[0-9]*", "PUT");
		
		mapping.setInterceptors(new Object[] { mnaInterceptor });
		return mapping;
	}
}
