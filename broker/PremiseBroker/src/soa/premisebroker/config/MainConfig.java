package soa.premisebroker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import soa.common.config.AbstractMainConfig;
import soa.common.security.AuthenticationProvider;
import soa.premisebroker.eventhandlers.BidderEventHandler;
import soa.premisebroker.eventhandlers.PaymentEventHandler;
import soa.premisebroker.eventhandlers.ReservationEventHandler;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.finance.Biller;
import soa.premisebroker.finance.Invoicer;
import soa.premisebroker.repository.BidderRepository;

@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { "soa.common.repository",
		"soa.premisebroker.repository" })
@Configuration
@ImportResource("classpath:securityContext.xml")
public class MainConfig extends AbstractMainConfig {

	@Bean
	public BidderRequester bidderRequester() {
		return new BidderRequester();
	}

	@Bean
	public Biller biller() {
		return new Biller();
	}

	@Bean
	public Invoicer invoicer() {
		return new Invoicer();
	}
	


}
