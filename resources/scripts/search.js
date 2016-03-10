$(document).ready(function() {
	console.log("Page ready!");
	
	var li = document.getElementsByTagName("span");
	
	for (var i=0; i<li.length; i++) {
		var curr = li[i];
		if (curr.innerText >= 3)
			$(curr).addClass("is-success");
		else if (curr.innerText >= 1.5)
			$(curr).addClass("is-warning");
		else
			$(curr).addClass("is-danger");
	}
});	
