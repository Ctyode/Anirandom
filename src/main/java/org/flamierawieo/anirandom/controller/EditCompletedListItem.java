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
public class EditCompletedListItem extends BaseController {

    @RequestMapping("/anime/edit_completed_list")
    public String editCompletedListItem(HttpServletRequest request,
                                        @RequestParam(value = "anime") String animeId,
                                        @RequestParam(value = "review") String review,
                                        @RequestParam(value = "rating") String rating) {
        // FIXME
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            List<Review> reviews = user.completedList.stream().filter(r -> !r.anime.id.equals(animeObjectId)).collect(Collectors.toList());
            Review updatedReview = new Review();
            updatedReview.anime = datastore.get(Anime.class, animeObjectId);
            updatedReview.review = review;
            int r = Integer.parseInt(rating);
            if(r > 10) {
                r = 10;
            } else if(r < 0) {
                r = 0;
            }
            updatedReview.rating = r;
            reviews.add(updatedReview);
            datastore.update(user, datastore.createUpdateOperations(User.class).add("completedList", reviews));
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
