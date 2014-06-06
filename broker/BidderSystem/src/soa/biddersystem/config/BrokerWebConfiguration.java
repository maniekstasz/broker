package soa.biddersystem.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import soa.biddersystem.eventhandlers.OfferEventHandler;
import soa.biddersystem.eventhandlers.ReservationEventHandler;
import soa.common.config.WebConfiguration;

@Configuration
public class BrokerWebConfiguration {

	@Bean
	public ReservationEventHandler reservationEventHandler() {
		return new ReservationEventHandler();
	}

	
	@Bean
	public OfferEventHandler offerEventHandler(){
		return new OfferEventHandler();
	}
}
