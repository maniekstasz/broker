package soa.biddersystem.eventhandlers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

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
import soa.biddersystem.model.Premise;
import soa.biddersystem.model.PremiseDto;
import soa.biddersystem.repository.OfferRepository;
import soa.biddersystem.repository.PremiseRepository;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.utils.LocationUtils;

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

	private String PREMISES_URL;
	private String PREMISES_URL_WITH_SLASH;

	@PostConstruct
	public void postConstruct() {
		PREMISES_URL = env.getProperty("premisebroker.createPremise.uri");
		PREMISES_URL_WITH_SLASH = PREMISES_URL + "/";
	}

	@HandleAfterCreate
	public void afterCreate(Premise premise) {
		try {
			URI offerLocation = restTemplate.postForLocation(PREMISES_URL,
					new PremiseDto(premise, PREMISES_URL_WITH_SLASH
							+ premise.getOffer().getForeignId(), true));
			premise.setForeignId(LocationUtils.getIdFromLocation(offerLocation
					.toString()));
			premiseRepository.save(premise);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
	}

	@HandleAfterSave
	public void afterSave(Premise premise) throws RestClientException,
			URISyntaxException {
		try {
			restTemplate
					.exchange(
							new URI(PREMISES_URL_WITH_SLASH
									+ premise.getForeignId()),
							HttpMethod.PATCH, new HttpEntity<PremiseDto>(
									new PremiseDto(premise,
											PREMISES_URL_WITH_SLASH
													+ premise.getOffer()
															.getForeignId(),
											false)), void.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
	}

	@HandleAfterDelete
	public void afterDelete(Premise premise) throws RestClientException,
			URISyntaxException {
		try {
			restTemplate.delete(new URI(PREMISES_URL_WITH_SLASH
					+ premise.getForeignId()));
		} catch (HttpClientErrorException e) {
		}
	}

}
