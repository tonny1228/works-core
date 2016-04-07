	function edit(url){
		var item = null;
		$("input").each(function() {
			if($(this).attr("name")== "id" && $(this).attr("checked")){
				if(item){
					alert("只能选择编辑一条记录");
					return;
				}
				item = $(this);
			}
		});
		if(!item){
			alert("请选择一条记录");
			return;
		}
		window.location=url+item.attr("value");
	}
	
	function del(form,url){
		var item = null;
		$("input").each(function() {
			if($(this).attr("name")== "id" && $(this).attr("checked")){
				item = $(this);
			}
		});
		if(!item){
			Frm.alert("请选择记录",'提示');
			return;
		}
		Frm.confirm("确定要删除吗？",'提示',function(res){
			if(!res) return;
			if(url)
				form.action=url;
			form.submit();
		});
		
	}
