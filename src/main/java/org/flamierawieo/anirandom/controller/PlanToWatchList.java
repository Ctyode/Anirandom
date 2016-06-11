package org.flamierawieo.anirandom.controller;

//import org.flamierawieo.anirandom.orm.Anime;
//import org.flamierawieo.anirandom.orm.User;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;

//@RestController
public class PlanToWatchList extends Base {

//    private static String template;
//
//    static {
//        try {
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/plan_to_watch.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
//    }
//
//    @RequestMapping("/{username}/plan_to_watch")
//    public String get(HttpServletRequest request, @PathVariable(value="username") String username) {
//        Map<String, Object> context = getContext(request);
//        User user = datastore.createQuery(User.class).filter("username =", username).get();
//        System.out.println(user.planToWatchList);
//        if(user != null && user.planToWatchList != null) {
//            context.put("plan_to_watch_list", user.planToWatchList.stream().map(Anime::toMap).collect(Collectors.toList()));
//        }
//        return render(template, context);
//    }

}
