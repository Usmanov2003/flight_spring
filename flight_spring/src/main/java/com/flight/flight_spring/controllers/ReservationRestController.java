package com.flight.flight_spring.controllers;


import com.flight.flight_spring.domains.Reservation;
import com.flight.flight_spring.dto.ReservationUpdateRequest;
import com.flight.flight_spring.exceptions.ReservationNotFound;
import com.flight.flight_spring.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;


import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class ReservationRestController {

    @Autowired
    ReservationRepository reservationRepository;
    private static final Logger LOGGER= LoggerFactory.getLogger(ReservationRestController.class);


    @RequestMapping("/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        LOGGER.info("Inside findReservation() for id: " + id);
        Optional<Reservation> reservation= reservationRepository.findById(id);
        if(!reservation.isPresent()) {
            LOGGER.error("No reservation exist with id"+ id);
            throw new ReservationNotFound("No reservation exist with id"+id);
        }
        return reservation.get();
    }

    @RequestParam(value = "/reservations", method = RequestMethod.POST)
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest reservationUpdateRequest) {
        LOGGER.info("insde updateReservation() for " + reservationUpdateRequest);
        if(!reservation.isPresent()) {
            LOGGER.error("No reservation exist with id" + reservationUpdateRequest.getId());
            throw new ReservationNotFound("No reservation exist with id" + reservationUpdateRequest.getId());
        }
        reservation.get().setNumberOfBags(reservationUpdateRequest.getId());
        throw new ReservationNotFound("No reservation exist with id"+reservationUpdateRequest.getId());
    }
    reservation.get().setNumberOfBags(reservationUpdateRequest.getNumberOfBags());
    reservation.get().setCheckedIN(reservationUpdateRequest.isCheckedIn());
    LOGGER.info("saving Reservation");
    return reservationRepository.save(reservation.get());
    }
}
