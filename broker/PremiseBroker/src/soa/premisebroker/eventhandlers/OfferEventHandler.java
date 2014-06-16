package soa.premisebroker.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;

import soa.common.security.LoggedUser;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Offer;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.OfferRepository;

@RepositoryEventHandler(Offer.class)
public class OfferEventHandler {

	@Autowired
	private BidderRepository bidderRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private SecurityEventHandlerSupportBean securitySupportBean;

	@HandleBeforeCreate
	public void authorize(Offer offer) throws AccessDeniedException {
		LoggedUser user = isAuthorize();
		offer.setBidder(bidderRepository.findByCreatedById(user.getId()));
	}

	@HandleBeforeSave
	@HandleBeforeDelete
	public void authorizeChange(Offer offer) {
		isAuthorize();
	}

	private LoggedUser isAuthorize() throws AccessDeniedException {
		LoggedUser user = securitySupportBean.getLoggedUser();
		Bidder bidder = bidderRepository.findByCreatedById(user.getId());
		if (bidder == null || !bidder.getVerified())
			throw new AccessDeniedException("Not authorized");
		return user;
	}

}
