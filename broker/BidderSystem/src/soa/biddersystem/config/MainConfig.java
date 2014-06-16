package soa.biddersystem.config;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;
import soa.biddersystem.controller.FinanceController;
import soa.biddersystem.controller.ReservationController;
import soa.biddersystem.finance.PaymentService;
import soa.biddersystem.model.User;
import soa.common.config.AbstractMainConfig;
import soa.common.security.Auditor;
import soa.common.security.SecurityUserRepository;

@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { "soa.common.repository",
		"soa.biddersystem.repository" })
@Configuration
@ImportResource("classpath:securityContext.xml")
public class MainConfig extends AbstractMainConfig {

	@Autowired
	private Environment env;

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}

	@Bean
	public FinanceController financeController() {
		return new FinanceController();
	}

	@Bean
	public ReservationController reservationController() {
		return new ReservationController();
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
	public RestTemplate restTemplate() throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.useTLS().build();
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
				sslContext, new AllowAllHostnameVerifier());

		String auth = env.getProperty("premisebroker.username") + ":"
				+ env.getProperty("premisebroker.password");
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
				.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		HttpClient httpClient = HttpClientBuilder
				.create()
				.setDefaultHeaders(
						Arrays.asList(new BasicHeader("Authorization",
								authHeader)))
				.setSSLSocketFactory(connectionFactory).build();
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(
				httpClient));
	}
	
	

}
