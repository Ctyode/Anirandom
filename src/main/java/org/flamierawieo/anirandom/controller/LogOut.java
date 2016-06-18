package org.flamierawieo.anirandom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LogOut {

    @RequestMapping("/logout")
    public void get(HttpServletResponse response) throws IOException {
        response.addCookie(new Cookie("access_token", null));
        response.sendRedirect("/");
    }

}
