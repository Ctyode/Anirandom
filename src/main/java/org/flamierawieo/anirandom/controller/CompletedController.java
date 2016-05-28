package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.Review;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class CompletedController extends BaseController {

    private static String template;

    static {
//        try {
            template = "src/main/resources/templates/completed.html";
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/completed.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
    }

    @Override
    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = super.getContext(request);
        User user = getAuthorizedUser(request);
        if(user != null && user.completedList != null) {
            context.put("completed_list", user.completedList.stream().map(Review::toMap).collect(Collectors.toList()));
        }
        return context;
    }

    @RequestMapping("/completed")
    public String handle(HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request));
    }

}
