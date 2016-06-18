package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.flamierawieo.anirandom.orm.mapping.Review;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class Completed extends Base {

    public Map<String, Object> getContext(HttpServletRequest request, String username) {
        Map<String, Object> context = super.getContext(request);
        User authorizedUser = getAuthorizedUser(request);
        User user = datastore.createQuery(User.class).filter("username", username).get();
        if (user != null && user.completedList != null) {
            context.put("other", !user.equals(authorizedUser));
            context.put("completed_list", user.completedList.stream().map(Review::toMap).collect(Collectors.toList()));
            context.put("has_unreviewed_animes", user.completedList.stream().filter(a -> a.review == null).findFirst().isPresent());
            context.put("has_reviewed_animes", user.completedList.stream().filter(a -> a.review != null && a.review.length() > 0).findFirst().isPresent());
        }
        return context;
    }

    @RequestMapping("/profile/{username}/completed")
    public String get(@PathVariable("username") String otherUsername,
                      HttpServletRequest request) throws IOException, PebbleException {
        return render("completed.html", getContext(request, otherUsername));
    }


    @RequestMapping("/anime/remove_from_completed")
    public String removeFromCompletedList(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", user.completedList.stream()
                    .filter(r -> !r.anime.id.equals(animeObjectId)).collect(Collectors.toList())));
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

    @RequestMapping(value = "/anime/edit_completed_list")
    public String editCompletedList(HttpServletRequest request,
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
