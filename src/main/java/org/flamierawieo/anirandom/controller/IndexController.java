package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.Anime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public Anime handle(@RequestParam(value = "genre", defaultValue = "action") String genre) {
        return new Anime(0, "Cowboy Bebop");
    }

}
