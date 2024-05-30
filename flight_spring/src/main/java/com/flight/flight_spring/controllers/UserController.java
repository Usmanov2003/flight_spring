package com.flight.flight_spring.controllers;

import com.flight.flight_spring.domains.User;
import com.flight.flight_spring.exceptions.UserAlreadyRegistered;
import com.flight.flight_spring.exceptions.UserNotFound;
import com.flight.flight_spring.repositories.UserRepository;
import com.flight.flight_spring.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/showReq")
    public String showRegistrationPage() {
        LOGGER.info("Inside showRegistrationPage()");
        return "login/registration";
    }

    @RequestMapping(value = "/regiterUser", method= RequestMethod.POST)
    public String register(@ModelAttribute("user" ) User user) {

        LOGGER.info("{} Inside register()", user.getEmail());
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent()) {
            LOGGER.error("user is already registered with email {}", user.getEmail());
            throw new UserAlreadyRegistered("Email exists: " + user.getEmail());
        }
        LOGGER.info("email Exists:" + user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login/login";
    }

    @RequestMapping("/showLogin")
    public String showLoginPage(){
        LOGGER.info("Inside showLoginPage");
        return "login/login";
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelmap) {
        LOGGER.info("{} Inside login()", email);
        Optional<User> foundUser= userRepository.findByEmail(email);

        if(!foundUser.isPresent()) {
            LOGGER.error("Email not found: " + email);
            throw new UserNotFound("Email not found: " + email);
        }
        LOGGER.info("Email Exists: " + email);
        boolean loginResponse = securityService.login(email, password);
        if(loginResponse) {
            modelMap.addAttribute("msg", "Successfully logged in");
            return "flights/findFlights";
        } else {
            LOGGER.info("user entered Invalid creadentails email:{} and password:{}", email, password);
            modelMap.addAttribute("msg", "Invalid username or password");
        }
        return "login/lognin";
    }
}
