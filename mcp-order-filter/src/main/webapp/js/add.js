window.onload = function() {};
$(document).ready(function() {
	var basePath = $("#basePath").val();
	var Page = function(){};
	
	Page.prototype.init = function()
	{
		var self = this;
		self.mode = 0;
		self.document = document.getElementById('edit').contentDocument;
		self.document.designMode = "on";
		try {
			self.document.execCommand("undo", false, null);
		}  catch (e) {
		    alert("This demo is not supported on your level of Mozilla.");
		}
	}
	
	/**
	 * 切换到源码模式
	 */
	Page.prototype.viewSource = function()
	{
		var self = this;
		self.mode = 1;
		var html = self.document.createTextNode(self.document.body.innerHTML);
		self.document.body.innerHTML = "";
		self.document.body.appendChild(html);
	}
	
	/**
	 * 切换到正常的编辑模式
	 */
	Page.prototype.viewNormal = function()
	{
		var self = this;
		self.mode = 0;
		var html = self.document.createRange();
		html.selectNodeContents(self.document.body);
		self.document.body.innerHTML = html.toString();
	}
	
	/**
	 * 编辑器获得焦点
	 */
	Page.prototype.focus = function()
	{
		var self = this;
		$('#edit').focus();
	}
	
	Page.prototype.getContent = function()
	{
		var self = this;
		if(self.mode == 0)
		{
			return self.document.body.innerHTML;
		}
		else
		{
			var html = self.document.createRange();
			html.selectNodeContents(self.document.body);
			return html.toString();
		}
	}
	
	var curPage = new Page();
	curPage.init();
	
	var viewSource = $('#view_source');
	viewSource.click(function(){
		if(curPage.mode == 0)
		{
			$(this).html("编辑模式");
			curPage.viewSource();
		}
		else
		{
			$(this).html("查看源码");
			curPage.viewNormal();
		}
	});
	
	var submit = $('#submit');
	submit.click(function(){
		var url = basePath + '/pages/addJson.htm';
		var title = $('#title').val();
		var content = curPage.getContent();
		if(title.length == 0)
		{
			alert("标题不能为空！");
			$('#title').focus();
			return;
		}
		if(content.length == 0)
		{
			alert("内容不能为空！");
			curPage.focus();
			return;
		}
		var data = {title:title, content:content};
		var mydata = JSON.stringify(data);
		jQuery.ajax({
			type : 'POST',
			url : url,
			data : mydata,
			contentType : 'application/json',
			dataType : 'json',
			success : function(data) {
				if(data.status == "true")
				{
					alert("发表成功");
					window.location = basePath + '/pages/list.htm';
				}
			},
			error : function(data) {
				alert("error");
			}
	    });
	});
});