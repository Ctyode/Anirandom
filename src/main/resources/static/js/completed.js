$(function() {
    $("section").each(function() {
        $section = $(this);
        var $drop_down = $section.find(".drop-down-more");
        $drop_down.find(".more").click(function() {
            $drop_down.toggleClass("show");
        });
        var $hidden_edit = $section.find(".hidden-edit");
        $drop_down.find(".edit").click(function() {
            $hidden_edit.css('visibility', 'visible');
        });
        var $title_span = $section.find(".anime-title span");
        $title_span.attr("data-anime-title", $title_span.html());
        $hidden_edit.find("input[type=submit]").click(function() {
            var anime = $hidden_edit.find("input[name=anime]").val();
            var rating = $hidden_edit.find("input[name=rating]").val();
            var review = $hidden_edit.find("textarea").val();
            console.log({
                anime:  anime,
                rating: rating,
                review: review
            });
            $.getJSON("/anime/edit_completed_list", {
                anime:  anime,
                rating: rating,
                review: review
            }, function(data) {
                console.log(data);
            });
        });
    });
});