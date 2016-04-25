package org.flamierawieo.anirandom.controller;

import com.mongodb.BasicDBObject;
import org.flamierawieo.anirandom.orm.User;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.flamierawieo.anirandom.Security.*;
import static org.flamierawieo.anirandom.mongo.MongoConfig.datastore;

@RestController
public class SignInController {

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("back") String back,
                       HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        User user = datastore.createQuery(User.class).filter("username", username).get();
        if(user != null) {
            if(user.password.equals(pbkdf2WithHmacSHA1(password))) {
                String accessToken = randomAccessToken();
                // TODO: can be optimized
                datastore.update(datastore.createQuery(User.class).filter("username", username), datastore.createUpdateOperations(User.class).add("accessTokens", accessToken));
                response.addCookie(new Cookie("access_token", accessToken));
                response.sendRedirect("/");
            } else {
                response.sendRedirect(back);
            }
        } else {
            response.sendRedirect(back);
        }
    }

}
