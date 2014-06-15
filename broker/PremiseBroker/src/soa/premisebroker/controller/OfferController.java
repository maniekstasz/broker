package soa.premisebroker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Offer;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.OfferRepository;

@Controller
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BidderRepository bidderRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Offer post(@RequestBody Offer offer){
		Bidder bidder = bidderRepository.findOne(new Long(1));
		offer.setBidder(bidder);
		Offer res  = offerRepository.save(offer);
		return res;
	}
}
