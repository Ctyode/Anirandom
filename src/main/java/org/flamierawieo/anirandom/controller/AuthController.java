package org.flamierawieo.anirandom.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("back") String back,
                       HttpServletResponse response) {
        if("flamie".equals(username) && "sasihui".equals(password)) {
            response.addCookie(new Cookie("accesstoken", "8344531aa151a97f714c695bb1f1f8ee"));
            response.setHeader("Location", "/");
        } else {
            response.setHeader("Location", back);
        }
    }

}
