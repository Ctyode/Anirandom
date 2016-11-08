package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.dao.AnimeDao;
import org.flamierawieo.anirandom.orm.dao.BaseDao;
import org.flamierawieo.anirandom.orm.dao.UserDao;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class PlanToWatch extends Base {

    public Map<String, Object> getContext(HttpServletRequest request, String username) {
        Map<String, Object> context = super.getContext(request);
        User authorizedUser = getAuthorizedUser(request);
        User user = new UserDao().getUserByUsername(username);
        if (user != null && user.planToWatchList != null) {
            context.put("other", !user.equals(authorizedUser));
            context.put("plan_to_watch_list", user.planToWatchList.stream().map(Anime::toMap).collect(Collectors.toList()));
        } else {
            context.put("other", true);
            context.put("plan_to_watch_list", Collections.emptyList());
        }
        return context;
    }

    @RequestMapping("/profile/{username}/plan_to_watch")
    public String get(@PathVariable("username") String otherUsername,
                      HttpServletRequest request) throws IOException, PebbleException {
        return render("plan_to_watch.html", getContext(request, otherUsername));
    }

    @RequestMapping("/anime/add_to_plan_to_watch_list")
    public String addToPlanToWatch(HttpServletRequest request,
                                   @RequestParam(value = "anime") String animeId) {
        UserDao dao = new UserDao();
        User user = getAuthorizedUser(request);
        Anime anime = new AnimeDao().getAnimeById(new ObjectId(animeId));
        if (user != null) {
            BaseDao.DaoResponse daoResponse = dao.addToPlanToWatch(user, anime);
            return jsonify(new LinkedHashMap() {{
                put("success", daoResponse.isSuccess());
                put("info", daoResponse.getInfo());
            }});
        } else {
            return jsonify(new LinkedHashMap() {{
                put("success", false);
                put("info", "not authorized");
            }});
        }
    }

    @RequestMapping("/anime/move_to_completed")
    public String moveToCompleted(HttpServletRequest request,
                                  @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        UserDao dao = new UserDao();
        Anime anime = new AnimeDao().getAnimeById(new ObjectId(animeId));
        BaseDao.DaoResponse daoResponse = dao.moveToCompleted(user, anime);
        return jsonify(new LinkedHashMap() {{
            put("success", daoResponse.isSuccess());
            put("info", daoResponse.getInfo());
        }});
    }

    @RequestMapping("/anime/remove_from_plan_to_watch")
    public String removeFromPlanToWatch(HttpServletRequest request,
                                        @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        UserDao dao = new UserDao();
        Anime anime = new AnimeDao().getAnimeById(new ObjectId(animeId));
        BaseDao.DaoResponse daoResponse = dao.removeFromPlanToWatch(user, anime);
        return jsonify(new LinkedHashMap() {{
            put("success", daoResponse.isSuccess());
            put("info", daoResponse.getInfo());
        }});
    }

}