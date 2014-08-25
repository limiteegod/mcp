window.onload = function() {};
$(document).ready(function() {
	var basePath = $("#basePath").val();
	var Page = function(){};
	
	Page.prototype.init = function()
	{
		var self = this;
	}
	
	var submit = $('#submit');
	submit.click(function(){
		var url = basePath + '/main/interface.htm';
		var message = $('#message').val();
		var data = {message:message};
		jQuery.ajax({
			type : 'POST',
			url : url,
			data : data,
			success : function(data) {
				alert(JSON.stringify(data));
			},
			error : function(data) {
				alert("error");
			}
	    });
	});
});