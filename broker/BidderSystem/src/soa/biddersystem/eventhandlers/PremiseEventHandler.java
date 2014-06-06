package soa.biddersystem.eventhandlers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import soa.biddersystem.model.Offer;
import soa.biddersystem.model.Premise;
import soa.biddersystem.repository.OfferRepository;
import soa.biddersystem.repository.PremiseRepository;
import soa.common.security.SecurityEventHandlerSupportBean;

@RepositoryEventHandler(Premise.class)
public class PremiseEventHandler {

	@Autowired
	private PremiseRepository premiseRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;
	@Autowired
	private SecurityEventHandlerSupportBean securityEventHandlerSupportBean;

	@HandleAfterCreate
	public void afterCreate(Premise premise) {
		URI offerLocation = restTemplate.postForLocation(
				env.getProperty("premisebroker.createPremise.uri"),
				getPremiseToSend(premise));
		premise.setForeignLocation(offerLocation.toString());
		premiseRepository.save(premise);
	}

	@HandleAfterSave
	public void afterSave(Premise premise) throws RestClientException,
			URISyntaxException {
		restTemplate.put(new URI(premise.getForeignLocation()),
				getPremiseToSend(premise));
	}

	private HttpEntity<Premise> getPremiseToSend(Premise premise) {
		return securityEventHandlerSupportBean.addAuthenticationHeader(premise,
				env.getProperty("premisebroker.password"),
				env.getProperty("premisebroker.username"));
	}
}
