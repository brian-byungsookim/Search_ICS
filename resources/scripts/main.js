$("#search_bar").keypress(function(e) {
	if (e.which == 13) {
	 	var params = document.getElementsByTagName("input")[0].value;
		window.location = createSearchRequest(params);
	}
});

$(".modal-background, .notification > .delete").on("click", function() {
	$("#modal").removeClass("is-active");
});

$("#about").on("click", function() {
	$("#modal").addClass("is-active");
});

function createSearchRequest(searchTerms) {
	return "searchQuery?param=" + encodeURI(searchTerms.trim()) + "&page=1";
}
