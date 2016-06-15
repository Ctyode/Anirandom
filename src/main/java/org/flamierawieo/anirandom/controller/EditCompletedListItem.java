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
public class EditCompletedListItem extends Base {

    @RequestMapping(value = "/anime/edit_completed_list")
    public String get(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId,
                      @RequestParam(value = "review") String review,
                      @RequestParam(value = "rating") String rating) {
        User user = getAuthorizedUser(request);
        if(user == null) {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
        ObjectId animeObjectId = new ObjectId(animeId);
        Anime anime = datastore.get(Anime.class, animeObjectId);
        if(anime == null) {
            return jsonify(new LinkedHashMap() {{
                put("status", "questionable");
                put("info", "nonexistent anime, removing from list");
            }});
        }
        List<Review> reviews = user.completedList.stream().filter(r -> !r.anime.equals(anime)).collect(Collectors.toList());
        Review updatedReview = new Review();
        updatedReview.anime = anime;
        updatedReview.review = review.replaceAll("\\s+", " ").trim();
        if(updatedReview.review.length() == 0) {
            updatedReview.review = null;
        }
        int r = (int) Float.parseFloat(rating);
        if(r > 10) {
            r = 10;
        } else if(r < 0) {
            r = 0;
        }
        updatedReview.rating = r;
        reviews.add(updatedReview);
        datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", reviews));
        return jsonify(new LinkedHashMap() {{
            put("status", "success");
            put("info", "nice!");
        }});
    }

}
