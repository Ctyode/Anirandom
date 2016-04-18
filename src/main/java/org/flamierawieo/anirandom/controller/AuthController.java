package org.flamierawieo.anirandom.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.flamierawieo.anirandom.Security;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RestController
public class AuthController {

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("back") String back,
                       HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("peedor " + username + " " + password);
        try {
            response.sendRedirect(back);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
        BasicDBObject filter = new BasicDBObject();
        filter.append("username", username);
        DBObject user = dbCollection.findOne(filter);

        if(user != null && new Security().pbkdf2WithHmacSHA1(password).equals(user.get("password"))) {
            response.addCookie(new Cookie("accesstoken", "hui"));
            response.setHeader("Location", "/");
        } else {
            response.setHeader("Location", back);
        }
    }

}
