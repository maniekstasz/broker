package soa.common.config;

import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import soa.common.controller.IndexController;
import soa.common.security.Auditor;
import soa.common.security.AuthenticationProvider;
import soa.common.security.SecurityEventHandlerSupportBean;

//import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaAuditing(setDates = true, auditorAwareRef = "auditor")
public abstract class AbstractMainConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl("jdbc:mysql:" + env.getProperty("database.url")
				+ "?characterEncoding=UTF-8");
		source.setUsername(env.getProperty("database.username"));
		source.setPassword(env.getProperty("database.password"));
		return source;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(env.getProperty(
				"hibernate.packagesToScan", String[].class));
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.hbm2ddl.auto", "update");
		prop.setProperty("connection.characterEncoding", "UTF-8");
		prop.setProperty("hibernate.connection.useUnicode", "yes");
		factoryBean.setJpaProperties(prop);
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);

		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManagerFactory()
				.getObject());
		return transactionManager;
	}

	@Bean
	public RestTemplate restTemplate() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs,
						String string) {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLContext.setDefault(ctx);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new RestTemplate();
	}

	@Bean
	public Auditor auditor() {
		return new Auditor();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new AuthenticationProvider();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityEventHandlerSupportBean securityEventHandlerSupportBean() {
		return new SecurityEventHandlerSupportBean();
	}

	@Bean
	public JavaMailSender javamailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setDefaultEncoding("UTF-8");
		javaMailSenderImpl.setUsername(env.getProperty("mail.username"));
		javaMailSenderImpl.setPassword(env.getProperty("mail.password"));
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		javaMailSenderImpl.setJavaMailProperties(props);
		return javaMailSenderImpl;
	}

	@Bean
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");

		return resolver;
	}

	@Bean
	public IndexController indexController() {
		return new IndexController();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/app/**").addResourceLocations("/app/**");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/**");
	}

	// @Bean
	// public ObjectMapper objectMapper() {
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper
	// .registerModule(new Hibernate4Module()
	// .configure(
	// com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS,
	// true));
	// return objectMapper;
	// }
	//
	// @Override
	// public void configureMessageConverters(
	// List<HttpMessageConverter<?>> converters) {
	// MappingJackson2HttpMessageConverter jacksonConverter = new
	// MappingJackson2HttpMessageConverter();
	// jacksonConverter.setObjectMapper(objectMapper());
	// converters.add(jacksonConverter);
	// }

}
