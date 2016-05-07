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
public class AddToCompletedListController extends BaseController {

    @RequestMapping("/anime/add_to_completed_list")
    public String addAnimeToUsersCompletedList(HttpServletRequest request,
                                               @RequestParam(value = "anime") String animeId,
                                               @RequestParam(value = "review") String review,
                                               @RequestParam(value = "rating") String rating) {
        // TODO: can be optimized
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            Review updatedReview = new Review();
            Anime anime = datastore.get(Anime.class, new ObjectId(animeId));
            updatedReview.anime = datastore.get(Anime.class, animeObjectId);
            if(anime != null) {
                if(!user.planToWatchList.contains(anime)) {
                    if (!user.completedList.contains(updatedReview)) {
                        List<Review> reviews = user.completedList;
                        reviews.add(updatedReview);
                        datastore.update(user, datastore.createUpdateOperations(User.class).add("completedList", reviews));
                        return jsonify(new LinkedHashMap() {{
                            put("status", "success");
                            put("info", "nice!");
                        }});
                    } else {
                        return jsonify(new LinkedHashMap() {{
                            put("status", "questionable");
                            put("info", "anime is already in user's completed list");
                        }});
                    }
                } else {
                    return jsonify(new LinkedHashMap() {{
                        put("status", "questionable");
                        put("info", "anime is already in user's plan to watch");
                    }});
                }
            } else {
                return jsonify(new LinkedHashMap() {{
                    put("status", "fail");
                    put("error", "nonexistent anime");
                }});
            }
        } else {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
    }

}
