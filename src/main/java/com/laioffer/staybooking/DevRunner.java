package com.laioffer.staybooking;

import com.laioffer.staybooking.security.JwtHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DevRunner implements ApplicationRunner {

    static private final Logger logger = LoggerFactory.getLogger(DevRunner.class);

    static private JwtHandler jwtHandler = null;
    public DevRunner(
            JwtHandler jwtHandler
    ) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        String token =jwtHandler.generateToken("starry_nights");
        logger.info("Token is: " + token);
    }
}