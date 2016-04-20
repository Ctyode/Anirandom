package org.flamierawieo.anirandom.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.flamierawieo.anirandom.Security.*;

@RestController
public class SignInController {

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("back") String back,
                       HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
        DBObject user = dbCollection.findOne(new BasicDBObject("username", username));
        if(user != null && pbkdf2WithHmacSHA1(password).equals(user.get("password"))) {
            String accessToken = randomAccessToken();
            List<String> accessTokens = (List) user.get("access_tokens");
            if(accessTokens == null) {
                accessTokens = new ArrayList<>();
            }
            accessTokens.add(accessToken);
            while(accessTokens.size() > 10) {
                accessTokens.remove(0);
            }
            user.put("access_tokens", accessTokens);
            dbCollection.update(new BasicDBObject("_id", user.get("_id")), user);
            response.addCookie(new Cookie("access_token", accessToken));
            response.sendRedirect("/");
        } else {
            response.sendRedirect(back);
        }
    }

}
