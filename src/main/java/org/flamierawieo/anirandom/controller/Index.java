package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class Index extends Base {

    @RequestMapping("/")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render("index.html", getContext(request));
    }

}