package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.dao.AnimeDao;
import org.flamierawieo.anirandom.orm.dao.BaseDao;
import org.flamierawieo.anirandom.orm.dao.UserDao;
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
import java.util.Map;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class Completed extends Base {

    public Map<String, Object> getContext(HttpServletRequest request, String username) {
        Map<String, Object> context = super.getContext(request);
        User authorizedUser = getAuthorizedUser(request);
        User user = new UserDao().getUserByUsername(username);
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

    @RequestMapping("/anime/add_to_completed_list")
    public String addToCompletedList(HttpServletRequest request,
                                     @RequestParam(value = "anime") String animeId) {
        AnimeDao animeDao = new AnimeDao();
        UserDao userDao = new UserDao();
        User user = getAuthorizedUser(request);
        Anime anime = animeDao.getAnimeById(new ObjectId(animeId));
        BaseDao.DaoResponse response = userDao.addToCompleted(user, anime);
        return jsonify(new LinkedHashMap() {{
            put("success", response.isSuccess());
            put("info", response.getInfo());
        }});
    }

    @RequestMapping("/anime/remove_from_completed")
    public String removeFromCompleted(HttpServletRequest request,
                                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        Anime anime = new AnimeDao().getAnimeById(new ObjectId(animeId));
        BaseDao.DaoResponse response = new UserDao().removeFromCompleted(user, anime);
        return jsonify(new LinkedHashMap() {{
            put("success", response.isSuccess());
            put("info", response.getInfo());
        }});
    }

    @RequestMapping("/anime/edit_completed_list")
    public String editCompleted(HttpServletRequest request,
                                @RequestParam(value = "anime") String animeId,
                                @RequestParam(value = "review") String reviewText,
                                @RequestParam(value = "rating", defaultValue = "0") String rating) {
        User user = getAuthorizedUser(request);
        if(user == null) {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
        Anime anime = new AnimeDao().getAnimeById(new ObjectId(animeId));
        Review review = new Review();
        review.anime = anime;
        reviewText = reviewText.replaceAll("\\s+", " ").trim();
        if(reviewText.length() > 0) {
            review.review = reviewText;
        }
        int r = (int) Float.parseFloat(rating);
        if(r > 10) {
            r = 10;
        } else if(r < 0) {
            r = 0;
        }
        review.rating = r;
        BaseDao.DaoResponse response = new UserDao().editCompleted(user, review);
        return jsonify(new LinkedHashMap() {{
            put("success", response.isSuccess());
            put("info", response.getInfo());
        }});
    }

}
