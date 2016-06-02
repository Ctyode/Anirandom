$(function() {
    $("section").each(function() {
        var $section = $(this);
        var $drop_down = $section.find(".drop-down-more");
        $drop_down.find(".more").click(function() {
            $drop_down.toggleClass("show");
        });
        $drop_down.find(".remove").click(function() {
            $.getJSON("/anime/remove_from_plan_to_watch", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["status"] === "success") {
                    $section.addClass("hidden");
                    setTimeout(function() {
                        $section.remove();
                    }, 300);
                }
            });
        });
        $drop_down.find(".move").click(function() {
            $.getJSON("/anime/move_to_completed", {anime: $(this).attr("data-anime-id")}, function (data) {
                if(data["status"] === "success") {
                    console.log("blablabla")
                    $section.addClass("hidden");
                    setTimeout(function() {
                        $section.remove();
                    }, 300);
                }
            });
        });
    });

});

$(function() {
    var $search = $(".search");
    $search.click(function() {
        var hasClass = $(this).hasClass("open");
        $("this").removeClass("open");
        if(!hasClass) {
            $(this).addClass("open");
        }
    });

    function search(query) {
      if (query.length < 3) {
        return Bacon.once([]);
      }
      return Bacon.fromPromise($.getJSON("/search", {s: query}));
    }

    var text = $search.find("input[type=text]")
      .asEventStream('keydown')
      .debounce(300)
      .map(function(event) {
        return event.target.value;
      }).skipDuplicates();

    var suggestions = text.flatMapLatest(search);
//    text.awaiting(suggestions).onValue(function(x) {
//      if (x) $('#results').html('Searching...');
//    });

    suggestions.onValue(function(v) {
        console.log(v);
    });
});