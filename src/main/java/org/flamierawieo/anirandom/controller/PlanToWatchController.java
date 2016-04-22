package org.flamierawieo.anirandom.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PlanToWatchController {

    private static String template;

    static {
        try {
            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/plan_to_watch.html")), "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
            template = "Can someone unfuck the situation, please?";
        }
    }

    @RequestMapping("/plan_to_watch")
    public void handle(HttpRequest httpRequest) {

    }

}