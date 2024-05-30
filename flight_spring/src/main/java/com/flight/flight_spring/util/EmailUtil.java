package com.flight.flight_spring.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailUtil {

    @Value("${com.flightreservation.flightreservation.itinerary.email.body}")
    private String EMAIL_BODY = "Please find your Itinerary attached";

    @Value("${com.flightreservation.flightreservation.itinerary.email.subject")
    private String EMAIL_SUBJECT = "Itinerary for your flight Flight";

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER= LoggerFactory.getLogger(EmailUtil.class);

    public void sendIternary(String toAddress, String filePath){
        LOGGER.info("Inside sendItinerary");
        MineMessage message= javaMailSender.createMineMessage();

        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject(EMAIL_SUBJECT);
            messageHelper.setText(EMAIL_BODY);
            messageHelper.addAttachment("Itinearary", new File(filePath));
            javaMailSender.send(message);
            LOGGER.info("Email sent: "+message);
        } catch (MessagingException e) {
            LOGGER.error(" Error Inside sendItinerary(): {}",e);
            System.out.println("Exception inside sendItinerary" + e);
        }
    }
}
