package com.flight.flight_spring.controllers;

import com.flight.flight_spring.domains.Flight;
import com.flight.flight_spring.domains.Reservation;
import com.flight.flight_spring.exceptions.FlightNotFound;
import com.flight.flight_spring.repositories.FlightRepository;
import com.flight.flight_spring.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @RequestMapping ("ShowCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap){
        LOGGER.info("showCompleteReservation() invoke with the Flight Id: " + flightId);
        Optional<Flight> flight = flightRepository.findById(flightId);

        if(!flight.isPresent()) {
            LOGGER.error("flight not found: {}", flight.toString());
            throw new FlightNotFound("flightId"+ flightId);
        }
        LOGGER.info("Flight found: {}", flight.toString());
        modelMap.addAttribute("flight", flight.get());
        return "reservation/completeReservation";
    }

    @RequestMapping(value = "completeReservation", method= RequestMethod.POST)
    public String completeReservation(Reservation reservationRequest, ModelMap modelMap){
        LOGGER.info("completeReservation() invoke with the Reservation: " + reservationRequest.toString());
        Reservation reservation=reservationService.bookFlight(reservationRequest);
        modelMap.addAttribute("msg", "Reservation created successfully and the reservation id is" + reservation.getId());
        return "reservation/reservationConfirmation";
    }

}
