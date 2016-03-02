package org.flamierawieo.anirandom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class IndexController {

    private static String content;

    static {
        try {
            content = new String(Files.readAllBytes(Paths.get("src/main/resources/static/index.html")), "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
            content = "Can someone unfuck the situation, please?";
        }
    }

    @RequestMapping("/")
    public String handle() {
        return content;
    }

}
