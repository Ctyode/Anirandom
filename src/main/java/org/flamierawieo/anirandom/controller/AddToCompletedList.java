package org.flamierawieo.anirandom.controller;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.flamierawieo.anirandom.orm.mapping.Review;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class AddToCompletedList extends Base {

    @RequestMapping("/anime/add_to_completed_list")
    public String get(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        if(user == null) {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
        ObjectId animeObjectId = new ObjectId(animeId);
        Anime anime = datastore.get(Anime.class, new ObjectId(animeId));
        if(anime == null) {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "nonexistent anime");
            }});
        }
        Review updatedReview = new Review();
        updatedReview.anime = datastore.get(Anime.class, animeObjectId);
        if(user.planToWatchList != null && user.planToWatchList.contains(anime)) {
            return jsonify(new LinkedHashMap() {{
                put("status", "questionable");
                put("info", "anime is already in user's plan to watch");
            }});
        }
        if(user.completedList != null && user.completedList.contains(updatedReview)) {
            return jsonify(new LinkedHashMap() {{
                put("status", "questionable");
                put("info", "anime is already in user's completed list");
            }});
        }
        List<Review> reviews;
        if(user.completedList == null) {
            reviews = new ArrayList<>();
        } else {
            reviews = user.completedList;
        }
        reviews.add(updatedReview);
        datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", reviews));
        return jsonify(new LinkedHashMap() {{
            put("status", "success");
            put("info", "nice!");
        }});
    }

}
