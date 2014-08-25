window.onload = function() {};
$(document).ready(function() {
	var basePath = $("#basePath").val();
	var Page = function(){};
	Page.prototype.toAddPage = function()
	{
		window.location = basePath + "/pages/add.htm";
	};
	Page.prototype.deleteItem = function(id)
	{
		var url = basePath + '/pages/deleteJson.htm';
		var data = {id:id};
		var mydata = JSON.stringify(data);
		jQuery.ajax({
			type : 'POST',
			url : url,
			data : mydata,
			contentType : 'application/json',
			dataType : 'json',
			success : function(data) {
				$('tr[dataid="' + id + '"]').remove();
			},
			error : function(data) {
				alert("error");
			}
	    });
	};
	var curPage = new Page();
	$("#add").click(function()
	{
		curPage.toAddPage();
	});
	
	$(".deleteButton").click(function() {
		curPage.deleteItem($(this).attr("dataid"));
	});
});