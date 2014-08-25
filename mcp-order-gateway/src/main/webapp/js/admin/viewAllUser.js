window.onload = function() {};
$(document).ready(function() {
	$(".deleteButton").click(function() {
		alert($(this).attr("userid"));
	});
});