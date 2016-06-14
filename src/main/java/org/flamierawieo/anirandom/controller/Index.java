package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class Index extends Base {

    private static String template;

    static {
//        try {
        template = "index.html";
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
    }

    @RequestMapping("/")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request));
    }

}