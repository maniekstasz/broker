package soa.biddersystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import soa.biddersystem.controller.FinanceController;
import soa.biddersystem.finance.PaymentService;
import soa.common.config.AbstractMainConfig;

@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { "soa.common.repository",
		"soa.biddersystem.repository" })
@Configuration
@ImportResource("classpath:securityContext.xml")
public class MainConfig extends AbstractMainConfig {

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}
	
	@Bean
	public FinanceController financeController(){
		return new FinanceController();
	}

}
