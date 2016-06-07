$(function() {
  (function($auth) {
    $auth.find(".auth-button").click(function() {
      if($auth.attr("section") === undefined) {
        $auth.attr("section", $(this).attr("data-target"));
      } else {
        $auth.removeAttr("section");
      }
    });
    $auth.find(".goto-button").click(function() {
      $auth.attr("section", $(this).attr("data-target"));
    });
  })($(".auth"));
});