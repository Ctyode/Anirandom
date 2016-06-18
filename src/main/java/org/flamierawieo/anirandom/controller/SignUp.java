package org.flamierawieo.anirandom.controller;

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
import java.util.regex.Pattern;

import static org.flamierawieo.anirandom.Security.*;

@RestController
public class SignUp extends Base {

    private static final String usernameRegexp = "[_\\w]{4,16}";
    private static final String emailRegexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";

    public boolean validate(String username, String password, String passwordConfirmation, String email) {
        return Pattern.matches(usernameRegexp, username) &&
                Pattern.matches(emailRegexp, email) &&
                password.length() > 5 && password.equals(passwordConfirmation);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void post(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("password_c") String passwordConfirmation,
                     @RequestParam("email") String email,
                     @RequestParam("back") String back,
                     HttpServletResponse response) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        UserDao userDao = new UserDao();
        if(validate(username, password, passwordConfirmation, email)) {
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
