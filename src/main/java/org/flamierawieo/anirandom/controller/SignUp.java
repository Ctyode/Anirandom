package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.Validation;
import org.flamierawieo.anirandom.orm.dao.UserDao;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.flamierawieo.anirandom.Security.*;

@RestController
public class SignUp extends Base {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void post(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("password_c") String passwordConfirmation,
                     @RequestParam("email") String email,
                     @RequestParam("back") String back,
                     HttpServletResponse response) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        UserDao userDao = new UserDao();
        if(new Validation().registrationData(username, password, passwordConfirmation, email)) {
            String accessToken = randomAccessToken();
            List<String> accessTokens = new ArrayList<>();
            accessTokens.add(accessToken);
            User user = new User();
            user.username = username;
            user.email = email;
            user.password = pbkdf2WithHmacSHA1(password);
            user.accessTokens = accessTokens;
            new UserDao().createUser(user);
            response.addCookie(new Cookie("access_token", accessToken));
            response.sendRedirect("/");
        } else {
            response.sendRedirect(back);
        }
    }

}
