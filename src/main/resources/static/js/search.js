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
        return Bacon.once({results: []});
      }
      return Bacon.fromPromise($.getJSON("/search.json", {s: query}));
    }

    var text = $search.find("input[type=text]")
      .asEventStream('keydown')
      .debounce(300)
      .map(function(event) {
        return event.target.value;
      }).skipDuplicates();

    var suggestions = text.flatMapLatest(search);

    // what the fuck am i doing?
    var item = function(p) {
        return '<li>' + '<div class="image" style="background-image:url('+ p["image"] +')"></div>' +
               '<div class="title-container">' +
               '<div class="title">' + p["title"] + '</div>' +
               '<div class="year">('+ p["year"] + ')</div>' +
               '</div>' +
               '<div class="stars"><div class="stars-fill" style="width: ' + (p["rating"] * 10) +'%"></div></div>' +
               '<div class="rating">' + p["rating"] + '</div>' +
               '</div>' +
               '<div class="drop-down-search">' +
               '<div class="more"></div>' +
               '<div class="bubble-search" data-anime-id=' + p["_id"] + '>' +
               '<div class="search-add-to add-to-plan-to-watch">Plan to watch</div>' +
               '<div class="search-add-to add-to-completed">Completed</div>' +
               '</div></div>' + '</li>'
    }
    suggestions.onValue(function(v) {
        var $list = $(".search ul");
        $list.html("")
        for(var i = 0; i < v.results.length; i++) {
            $list.append(item(v.results[i]));
        }

        $list.find("li").each(function() {
            var $li = $(this);
            var $drop_down_search = $li.find(".drop-down-search");
              $drop_down_search.find(".more").click(function() {
              $drop_down_search.toggleClass("show");
            });
            $drop_down_search.find(".add-to-plan-to-watch").click(function () {
                $.getJSON("/anime/add_to_plan_to_watch_list", {anime: $drop_down_search.find(".bubble-search").attr("data-anime-id")}, function (data) {
                    console.log(data);
                });
            });
            $drop_down_search.find(".add-to-completed").click(function () {
                $.getJSON("/anime/add_to_completed_list", {anime: $drop_down_search.find(".bubble-search").attr("data-anime-id")}, function (data) {
                    console.log(data);
                });
            });
        });
    });
});