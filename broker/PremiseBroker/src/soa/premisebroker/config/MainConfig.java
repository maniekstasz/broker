package soa.premisebroker.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.tempuri.DebtCollectorLocator;

import soa.common.config.AbstractMainConfig;
import soa.common.security.Auditor;
import soa.common.security.SecurityUserRepository;
import soa.premisebroker.controller.BankController;
import soa.premisebroker.controller.OfferController;
import soa.premisebroker.eventhandlers.PaymentEventHandler;
import soa.premisebroker.extern.BankRequester;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.extern.DebtCollectorRequester;
import soa.premisebroker.finance.Biller;
import soa.premisebroker.finance.Invoicer;
import soa.premisebroker.finance.PaymentService;
import soa.premisebroker.model.User;

@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { "soa.common.repository",
		"soa.premisebroker.repository" })
@Configuration
@ImportResource("classpath:securityContext.xml")
public class MainConfig extends AbstractMainConfig {

	@Autowired
	private Environment env;

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
		return new Invoicer(env.getProperty("bank.accountNr"));
	}

	@Bean
	public SecurityUserRepository<User> securityUserRepository() {
		return new SecurityUserRepository<User>();
	}

	@Bean
	public Auditor<User> auditor() {
		return new Auditor<User>();
	}

	@Bean
	public OfferController offerController() {
		return new OfferController();
	}

	@Bean
	public RestTemplate restTemplate() throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.useTLS().build();
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
				sslContext, new AllowAllHostnameVerifier());
		HttpClient httpClient = HttpClientBuilder.create()
				.setSSLSocketFactory(connectionFactory).build();
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(
				httpClient));
	}

	@Bean
	public BankController bankCotroller() {
		return new BankController();
	}

	@Bean
	public PaymentEventHandler paymentEventHandler() {
		return new PaymentEventHandler();
	}

	@Bean
	public BankRequester bankRequester() {
		return new BankRequester();
	}

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}
	
	@Bean
	public DebtCollectorLocator debtCollectorLocator(){
		return new DebtCollectorLocator();
	}
	@Bean
	public DebtCollectorRequester collectorRequester(){
		return new DebtCollectorRequester();
	}
	

}
