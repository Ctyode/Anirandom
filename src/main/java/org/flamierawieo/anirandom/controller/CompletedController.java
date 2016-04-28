package org.flamierawieo.anirandom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CompletedController extends BaseController {

    private static String template;

    static {
        try {
            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/completed.html")), "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
            template = "Can someone unfuck the situation, please?";
        }
    }

    @RequestMapping("/completed")
    public String handle(HttpServletRequest request) {
        return render(template, getContext(request));
    }

}
