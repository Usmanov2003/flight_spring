package com.flight.flight_spring.services;

import com.flight.flight_spring.domains.Flight;
import com.flight.flight_spring.domains.Passenger;
import com.flight.flight_spring.domains.Reservation;
import com.flight.flight_spring.dto.ReservationRequest;
import com.flight.flight_spring.repositories.FlightRepository;
import com.flight.flight_spring.repositories.PassengerRepository;
import com.flight.flight_spring.repositories.ReservationRepository;
import com.flight.flight_spring.util.EmailUtil;
import com.flight.flight_spring.util.PdfGenerator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    @Value("${com.flightreservation.flightreservation.itinerary.dirpath}")
    private String ITINERARY_DIR;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private EmailUtil emailUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    @Override
    public Reservation bookFlight(ReservationRequest reservationRequest) {
        LOGGER.info("Inside bookFlight()");
        Long flightId = reservationRequest.getFlightId();
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        if(!flightOptional.isPresent()){
            throw new FlightNotFound("No flight found with id" + flightId);
        }
        LOGGER.info("flight found with id" + flightId);
        Flight flight=flightOptional.get();
        Passenger passenger=new Passenger();
        passenger.setFirstName(reservationRequest.getPassengerFirstName());
        passenger.setMiddleName(reservationRequest.getPassengerMiddleName());
        passenger.setLastName(reservationRequest.getPassengerLastName());
        passenger.setEmail(reservationRequest.getPassengerEmail());
        passenger.setPhone(reservationRequest.getPassengerPhone());

        passengerRepository.save(passenger);
        LOGGER.info("Saved the passenger:" + passenger);

        Reservation reservation=new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedin(false);
        final Reservation savedReservation = reservationRepository.save(reservation);
        LOGGER.info("Saving the reservation:" + reservation);


        String filePath = ITINERARY_DIR + savedReservation.getId()
                + ".pdf";
        LOGGER.info("Generating  the itinerary");
        pdfGenerator.generateItenary(savedReservation,filePath);
        LOGGER.info("Emailing the Itinerary");
        emailUtil.sendItenary("dlulla@akamai.com",filePath);

        return savedReservation;
    }
}
