package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.Anime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnirandomJsonController {

    @RequestMapping("/anirandom.json")
    public Anime handle(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
                        @RequestParam(value = "year", defaultValue = "undefined") String year,
                        @RequestParam(value = "rating", defaultValue = "undefined") String rating) {
        return new Anime("Cowboy Bebop", "In the year 2071, humanity has colonized several of the planets and moons of the solar system leaving the now uninhabitable surface of planet Earth behind. The Inter Solar System Police attempts to keep peace in the galaxy, aided in part by outlaw bounty hunters, referred to as \"Cowboys\". The ragtag team aboard the spaceship Bebop are two such individuals.", "http://cdn.myanimelist.net/images/anime/4/19644l.jpg", 8.83);
    }

}