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
import soa.biddersystem.repository.OfferRepository;
import soa.common.security.SecurityEventHandlerSupportBean;

@RepositoryEventHandler(Offer.class)
public class OfferEventHandler {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Autowired
	private SecurityEventHandlerSupportBean securityEventHandlerSupportBean;

	@HandleAfterCreate
	public void afterCreate(Offer offer) {
		URI offerLocation = restTemplate.postForLocation(
				env.getProperty("premisebroker.createOffer.uri"),
				getOfferToSend(offer));

		offer.setForeignLocation(offerLocation.toString());
		offerRepository.save(offer);
	}

	@HandleAfterSave
	public void afterSave(Offer offer) throws RestClientException,
			URISyntaxException {
		restTemplate.put(new URI(offer.getForeignLocation()),
				getOfferToSend(offer));
	}

	private HttpEntity<Offer> getOfferToSend(Offer offer) {
		return securityEventHandlerSupportBean.addAuthenticationHeader(offer,
				env.getProperty("premisebroker.password"),
				env.getProperty("premisebroker.username"));
	}

}
