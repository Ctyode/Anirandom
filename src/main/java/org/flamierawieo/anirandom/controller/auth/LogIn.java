package org.flamierawieo.anirandom.controller.auth;

import org.flamierawieo.anirandom.controller.Base;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.flamierawieo.anirandom.Security.*;

@RestController
public class LogIn extends Base {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void post(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("back") String back,
                     HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        User user = datastore.createQuery(User.class).filter("username", username).get();
        if(user != null) {
            if(user.password.equals(pbkdf2WithHmacSHA1(password))) {
                String accessToken = randomAccessToken();
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
