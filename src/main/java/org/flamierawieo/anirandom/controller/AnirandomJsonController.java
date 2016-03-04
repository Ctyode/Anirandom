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
        if(Math.random() < 0.5) {
            return new Anime("Cowboy Bebop", "In the year 2071, humanity has colonized several of the planets and moons of the solar system leaving the now uninhabitable surface of planet Earth behind. The Inter Solar System Police attempts to keep peace in the galaxy, aided in part by outlaw bounty hunters, referred to as \"Cowboys\". The ragtag team aboard the spaceship Bebop are two such individuals.", "http://cdn.myanimelist.net/images/anime/4/19644l.jpg", 8.83);
        } else {
            return new Anime("Fairy Tail", "A superficial but kind-hearted celestial mage, Lucy Heartfilia, joins a famous wizarding guild in Magnolia Town called Fairy Tail, where the members use their powers to earn rewards in exchange for fulfilling quests. Here she befriends an impetuous but faithful dragon-slayer wizard, Natsu Dragneel, who is scouring the land for his missing father. The two form a bond with fellow mages Erza Scarlet and Gray Fullbuster, and together they tackle daunting challenges, taking on several powerful allies and foes along the way.", "http://cdn.myanimelist.net/images/anime/5/18179l.jpg", 8.30);
        }
    }

}