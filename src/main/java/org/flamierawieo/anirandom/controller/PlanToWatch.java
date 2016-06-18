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
public class PlanToWatch extends Base {

    public Map<String, Object> getContext(HttpServletRequest request, String username) {
        Map<String, Object> context = super.getContext(request);
        User authorizedUser = getAuthorizedUser(request);
        User user = datastore.createQuery(User.class).filter("username", username).get();
        if (user != null && user.planToWatchList != null) {
            context.put("other", !user.equals(authorizedUser));
            context.put("plan_to_watch_list", user.planToWatchList.stream().map(Anime::toMap).collect(Collectors.toList()));
        }
        return context;
    }

    @RequestMapping("/profile/{username}/plan_to_watch")
    public String get(@PathVariable("username") String otherUsername,
                      HttpServletRequest request) throws IOException, PebbleException {
        return render("plan_to_watch.html", getContext(request, otherUsername));
    }

    @RequestMapping("/anime/move_to_completed")
    public String moveToCompleted(HttpServletRequest request,
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

    @RequestMapping("/anime/remove_from_plan_to_watch")
    public String removeFromPlanToWatch(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            datastore.update(user, datastore.createUpdateOperations(User.class).set("planToWatchList", user.planToWatchList.stream()
                    .filter(a -> !a.id.equals(animeObjectId)).collect(Collectors.toList())));
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