package org.flamierawieo.anirandom.controller;

import com.mongodb.BasicDBObject;
import org.flamierawieo.anirandom.AvailabilityCheck;
import org.flamierawieo.anirandom.Validation;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RegisterController {

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("password_c") String passwordConfirmation,
                       @RequestParam("email") String email,
                       @RequestParam("back") String back,
                       HttpServletResponse response) throws IOException {
        if(new Validation().registrationData(username, password, passwordConfirmation, email) &&
           new AvailabilityCheck().availabilityCheck(username, email)) {
            MongoConfig.mongoDatabase.getCollection("users").insert(new BasicDBObject()
                    .append("username", username)
                    .append("password", password) // TODO: password encryption
                    .append("email", email));
            response.sendRedirect("/");
        } else {
            response.sendRedirect(back);
        }
    }

}
