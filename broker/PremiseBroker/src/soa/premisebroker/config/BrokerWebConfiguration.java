package soa.premisebroker.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import soa.common.config.WebConfiguration;
import soa.premisebroker.eventhandlers.BidderEventHandler;
import soa.premisebroker.eventhandlers.OfferEventHandler;
import soa.premisebroker.eventhandlers.PaymentEventHandler;
import soa.premisebroker.eventhandlers.ReservationEventHandler;

@Configuration
public class BrokerWebConfiguration {
	@Bean
	public BidderEventHandler bidderEventHandler() {
		return new BidderEventHandler();
	}

	@Bean
	public PaymentEventHandler paymentEventHandler() {
		return new PaymentEventHandler();
	}

	@Bean
	public ReservationEventHandler reservationEventHandler() {
		return new ReservationEventHandler();
	}

	
	@Bean
	public OfferEventHandler offerEventHandler(){
		return new OfferEventHandler();
	}
}
