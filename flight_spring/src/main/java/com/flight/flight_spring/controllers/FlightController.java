package com.flight.flight_spring.controllers;


import com.flight.flight_spring.domains.Flight;
import com.flight.flight_spring.repositories.FlightRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class FlightController {

    private static final Logger LOGGER= LoggerFactory.getLogger(FlightController.class);

    @Autowired
    FlightRepository flightRepository;

    @RequestMapping("findFlights")
    public String findFlights(@RequestParam("source") String source, @RequestParam("destination") String destination,
                              @RequestParam("departDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departDate, ModelMap modelMap) {

        LOGGER.info("Inside findFlights() From:" + source + "To:" + destination + "departDate:" + departDate);
        List<Flight> flights = flightRepository.findFlights(source, destination, departDate);
        modelMap.addAttribute("flights", flights);
        LOGGER.info("Flight found are"+ flights.size());
        return "flight/displayFlights";
    }

    @RequestMapping("admin/showAddFlight")
    public String showAddFlight() { return "flight/addFlight"; }

    @RequestMapping("admin/addFlight")
    public String addFlight(@ModelAttribute("flight") Flight flight, ModelMap modelmap) {
        flightRepository.save(flight);
        modelmap.addAttribute("msg", "Flight Added Successfully");
        return "flight/addFlight";
    }
}
