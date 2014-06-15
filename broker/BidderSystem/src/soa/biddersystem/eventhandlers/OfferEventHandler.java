package soa.biddersystem.eventhandlers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import soa.biddersystem.model.Offer;
import soa.biddersystem.model.OfferDto;
import soa.biddersystem.repository.OfferRepository;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.utils.LocationUtils;

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
		try {
			OfferDto offerDto = new OfferDto(offer, true);
			URI offerLocation = restTemplate.postForLocation(
					env.getProperty("premisebroker.createOffer.uri"), offerDto);
			offer.setForeignId(LocationUtils.getIdFromLocation(offerLocation
					.toString()));
			offerRepository.save(offer);
		} catch (HttpClientErrorException e) {
		}
	}

	@HandleAfterSave
	public void afterSave(Offer offer) throws RestClientException,
			URISyntaxException {
		try {
			OfferDto offerDto = new OfferDto(offer, false);
			restTemplate.exchange(
					new URI(env.getProperty("premisebroker.createOffer.uri")
							+ "/" + offer.getForeignId()), HttpMethod.PATCH,
					new HttpEntity<OfferDto>(offerDto), void.class);
		} catch (HttpClientErrorException e) {
		}
	}

	@HandleAfterDelete
	public void afterDelete(Offer offer) throws RestClientException,
			URISyntaxException {
		try {
			restTemplate.delete(new URI(env
					.getProperty("premisebroker.createOffer.uri")
					+ "/"
					+ offer.getForeignId()));
		} catch (HttpClientErrorException e) {
		}
	}

}
