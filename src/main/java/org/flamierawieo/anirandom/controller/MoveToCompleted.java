package org.flamierawieo.anirandom.controller;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.Anime;
import org.flamierawieo.anirandom.orm.Review;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class MoveToCompleted extends Base {

    @RequestMapping("/anime/move_to_completed")
    public String get(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            datastore.update(user, datastore.createUpdateOperations(User.class).set("planToWatchList", user.planToWatchList.stream()
                    .filter(a -> !a.id.equals(animeObjectId)).collect(Collectors.toList())));
            Review updatedReview = new Review();
            Anime anime = datastore.get(Anime.class, new ObjectId(animeId));
            List<Review> reviews = user.completedList.stream().filter(r -> !r.anime.equals(anime)).collect(Collectors.toList());
            updatedReview.anime = anime;
            if(anime == null) {
                return jsonify(new LinkedHashMap() {{
                    put("status", "fail");
                    put("error", "nonexistent anime");
                }});
            } else {
                reviews.add(updatedReview);
            }
            datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", reviews));
            return jsonify(new LinkedHashMap() {{
                put("status", "success");
                put("info", "nice!");
            }});
        } else {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }

    }

}
