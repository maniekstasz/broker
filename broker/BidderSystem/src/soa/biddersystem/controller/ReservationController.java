package soa.biddersystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import soa.biddersystem.model.Premise;
import soa.biddersystem.model.PremiseReservation;
import soa.biddersystem.repository.PremiseRepository;
import soa.biddersystem.repository.PremiseReservationRepository;
import soa.common.model.PremiseReservationDto;
import soa.common.model.AbstractReservation.ReservationStatus;
import soa.common.webservices.response.ReservationRequestResponse;

@Controller
public class ReservationController {

	@Autowired
	private PremiseReservationRepository reservationRepository;

	@Autowired
	private PremiseRepository premiseRepository;

	@RequestMapping(value = "/foreignReservation", method = {RequestMethod.POST, RequestMethod.PUT})
	@ResponseBody
	public ReservationRequestResponse handleReservationCreate(
			@RequestBody PremiseReservationDto resDto,HttpServletRequest request) {
		if(request.getMethod().equals("PUT"))
			return handleReservationUpdate(resDto);
		Long count = reservationRepository.isAvailableByForeign(
				resDto.getReservedFrom(), resDto.getReservedTo(),
				resDto.getPremiseId());

		Premise premise = premiseRepository.findByForeignId(resDto
				.getPremiseId());
		PremiseReservation premiseReservation = new PremiseReservation();

		premiseReservation.setUserNotes(resDto.getUserNotes());
		premiseReservation.setPremise(premise);
		premiseReservation.setReservedFrom(resDto.getReservedFrom());
		premiseReservation.setReservedTo(resDto.getReservedTo());
		premiseReservation.setForeignId(resDto.getForeignId());
		if (count > 0) {
			premiseReservation.setBidderNotes("Not available");
			premiseReservation.setStatus(ReservationStatus.DECLINED);
		} else {
			premiseReservation.setBidderNotes("Payment on the spot");
			premiseReservation.setStatus(ReservationStatus.ACCEPTED);
		}
		reservationRepository.save(premiseReservation);
		return new ReservationRequestResponse(premiseReservation.getStatus(),
				premiseReservation.getBidderNotes());
	}

//	@RequestMapping(value = "/foreignReservation", method = RequestMethod.PATCH)
//	@ResponseBody
	private ReservationRequestResponse handleReservationUpdate(
			@RequestBody PremiseReservationDto resDto) {
		PremiseReservation premiseReservation = reservationRepository
				.findByForeignId(resDto.getForeignId());
		if(premiseReservation == null) return null;
		premiseReservation.setUserNotes(resDto.getUserNotes());

		if (premiseReservation.getReservedFrom().getTime() != resDto.getReservedFrom().getTime() 
				|| premiseReservation.getReservedTo().getTime() != resDto.getReservedTo().getTime()) {
			premiseReservation.setReservedFrom(resDto.getReservedFrom());
			premiseReservation.setReservedTo(resDto.getReservedTo());

			Long count = reservationRepository.isAvailableByForeign(
					resDto.getReservedFrom(), resDto.getReservedTo(),
					resDto.getPremiseId());
			if (count > 0) {
				premiseReservation.setBidderNotes("Not available");
				premiseReservation.setStatus(ReservationStatus.DECLINED);
			} else {
				premiseReservation.setBidderNotes("Payment on the spot");
				premiseReservation.setStatus(ReservationStatus.ACCEPTED);
			}
		}else{
			premiseReservation.setStatus(resDto.getStatus());
		}

		reservationRepository.save(premiseReservation);
		return new ReservationRequestResponse(premiseReservation.getStatus(),
				premiseReservation.getBidderNotes());
	}
}
