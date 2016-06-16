package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.Anime;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

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

}