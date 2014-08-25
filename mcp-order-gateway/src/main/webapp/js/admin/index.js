window.onload = function() {};
$(document).ready(function() {
	var windowWidth = $(window).width();
	var windowHeight = $(window).height();
	var headHeight = $('#head').height();
	var tHeight = windowHeight - headHeight - 1;
	$('#left').height(tHeight);
	$('#cindex').height(tHeight);
});