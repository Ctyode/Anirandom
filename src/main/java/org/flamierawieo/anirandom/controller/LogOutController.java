package org.flamierawieo.anirandom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogOutController {

    // Yes, that's it...

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void handle(HttpServletResponse response) {
        response.addCookie(new Cookie("access_token", null));
    }

}
