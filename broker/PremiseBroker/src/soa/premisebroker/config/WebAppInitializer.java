package soa.premisebroker.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import soa.common.config.WebConfiguration;

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {MainConfig.class, QuartzConfig.class  };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfiguration.class,BrokerWebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		Filter securityFilter = new DelegatingFilterProxy(
				"springSecurityFilterChain");
		return new Filter[] { securityFilter };
	}
}
