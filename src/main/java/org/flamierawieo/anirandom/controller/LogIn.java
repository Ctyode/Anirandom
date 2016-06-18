package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.orm.dao.BaseDao;
import org.flamierawieo.anirandom.orm.dao.UserDao;
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
        UserDao userDao = new UserDao();
        User user = userDao.getUserByUsername(username);
        if(user != null) {
            System.out.println(user.password);
            System.out.println(pbkdf2WithHmacSHA1(password));
            if(user.password.equals(pbkdf2WithHmacSHA1(password))) {
                String accessToken = randomAccessToken();
                BaseDao.DaoResponse r = userDao.addAccessToken(user, accessToken);
                if(r.isSuccess()) {
                    response.addCookie(new Cookie("access_token", accessToken));
                }
                response.sendRedirect("/");
            } else {
                response.sendRedirect(back);
            }
        } else {
            response.sendRedirect(back);
        }
    }

}
