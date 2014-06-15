package soa.premisebroker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import soa.common.config.WebConfiguration;
import soa.common.controller.MethodNotAllowedInterceptor;
import soa.premisebroker.eventhandlers.BidderEventHandler;
import soa.premisebroker.eventhandlers.OfferEventHandler;
import soa.premisebroker.eventhandlers.ReservationEventHandler;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Offer;
import soa.premisebroker.model.Premise;

@Configuration
public class BrokerWebConfiguration extends WebConfiguration {
	@Bean
	public BidderEventHandler bidderEventHandler() {
		return new BidderEventHandler();
	}

	@Bean
	public ReservationEventHandler reservationEventHandler() {
		return new ReservationEventHandler();
	}

	@Bean
	public OfferEventHandler offerEventHandler() {
		return new OfferEventHandler();
	}

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		super.configureRepositoryRestConfiguration(config);
		config.exposeIdsFor(Premise.class, Bidder.class, Offer.class);
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
		
		mnaInterceptor.addInterceptedUrl("/premiseReservations/[0-9]*", "DELETE");
		
		mnaInterceptor.addInterceptedUrl("/offers/[0-9]*", "PUT");
		
		mnaInterceptor.addInterceptedUrl("/favouriteOffers/[0-9]*", "PUT", "PATCH", "DELETE");
		
		mnaInterceptor.addInterceptedUrl("/bidders/[0-9]*", "PUT", "PATCH",
				"GET", "DELETE");
		
		mapping.setInterceptors(new Object[] { mnaInterceptor });
		return mapping;
	}
	// @Bean
	// public ResourceProcessor<Resource<PremiseReservation>> userProcessor(){
	// return new ResourceProcessor<Resource<PremiseReservation>>() {
	// @Override public Resource<PremiseReservation>
	// process(Resource<PremiseReservation> resource) {
	// resource.add(new Link(resource.g))
	// resource.add(new Link(resource.getId().getHref() +
	// "/premiseReservations", "premiseReservations"));
	// resource.add(new Link(resource.getId().getHref() + "/offers", "offers"));
	// return resource;
	// }
	// };
	// }
}
