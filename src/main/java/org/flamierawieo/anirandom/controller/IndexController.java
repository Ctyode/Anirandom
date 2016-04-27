package org.flamierawieo.anirandom.controller;

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
        try {
            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")), "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
            template = "Can someone unfuck the situation, please?";
        }
    }

    @RequestMapping("/love_plan_to_watch")
    public void addToPlanToWatch(HttpServletRequest request,
                                 @RequestParam(value = "anime_id", defaultValue = "undefined") String animeId) {
        // TODO optimize and validate
        /*if(!"undefined".equals(animeId)) {
            DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
            String accessToken = getCookies(request).get("access_token");
            if (accessToken != null) {
                DBObject user = dbCollection.findOne(new BasicDBObject("access_tokens", accessToken));
                List<ObjectId> planToWatchList = (List<ObjectId>) user.get("plan_to_watch_list");
                if (planToWatchList == null) {
                    planToWatchList = new ArrayList<>();
                }
                ObjectId animeObjectId = new ObjectId(animeId);
                if (!planToWatchList.contains(animeObjectId)) {
                    planToWatchList.add(animeObjectId);
                }
                user.put("plan_to_watch_list", planToWatchList);
                dbCollection.update(new BasicDBObject("_id", user.get("_id")), user);
            }
        }*/
    }

    @RequestMapping("/")
    public String handle(HttpServletRequest request) {
        return render(template, getContext(request));
    }

}