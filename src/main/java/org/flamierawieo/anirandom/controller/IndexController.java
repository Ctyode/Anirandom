package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class IndexController extends BaseController {

    private static String template;

    static {
//        try {
        template = "src/main/resources/templates/index.html";
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
    }

    @RequestMapping("/")
    public String handle(HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request));
    }

}