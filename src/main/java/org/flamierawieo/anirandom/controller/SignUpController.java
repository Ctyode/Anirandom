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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.flamierawieo.anirandom.Security.*;

@RestController
public class SignUpController {

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("password_c") String passwordConfirmation,
                       @RequestParam("email") String email,
                       @RequestParam("back") String back,
                       HttpServletResponse response) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if(new Validation().registrationData(username, password, passwordConfirmation, email) &&
           new AvailabilityCheck().availabilityCheck(username, email)) {
            String accessToken = randomAccessToken();
            List<String> accessTokens = new ArrayList<>();
            accessTokens.add(accessToken);
            MongoConfig.mongoDatabase.getCollection("users").insert(new BasicDBObject()
                    .append("username", username)
                    .append("password", pbkdf2WithHmacSHA1(password))
                    .append("email", email)
                    .append("access_tokens", accessTokens));
            response.sendRedirect("/");
        } else {
            response.sendRedirect(back);
        }
    }

}
