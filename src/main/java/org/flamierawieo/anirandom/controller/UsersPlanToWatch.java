package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.Anime;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UsersPlanToWatch extends Base {

    private static String template;

    static {
//        try {
        template = "plan_to_watch.html";
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/plan_to_watch.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
    }

    @Override
    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = super.getContext(request);
        User user = getAuthorizedUser(request);
        if(user != null && user.planToWatchList != null) {
            context.put("plan_to_watch_list", user.planToWatchList.stream().map(Anime::toMap).collect(Collectors.toList()));
        }
        return context;
    }

    @RequestMapping("/plan_to_watch")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request));
    }

}