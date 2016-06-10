$(function() {
  (function($auth) {
    $(document.body).click(function() {
      $auth.removeAttr("section");
    });
    $auth.click(function(e) {
      e.stopPropagation();
    });
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