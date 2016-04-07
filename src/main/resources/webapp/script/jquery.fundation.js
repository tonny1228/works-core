
$(document).ready(function() {
	//alert(onloadFunction.length);
     for(var i=0;i<onloadFunction.length;i++){
		//alert(i+onloadFunction[i]);
		try{
			onloadFunction[i]();
     	}catch(e){
     		Frm.alert(onloadFunction[i]+":"+e);
     	}
     }
});



function delSelected(datacss,formcss,action,msg,notSelectmsg){
	var checked = false;
	$(datacss).each(function(i,item){
		if(item.checked && !item.disabled) {checked = true;return};
	});
	if(!checked){
		Frm.alert(notSelectmsg?notSelectmsg:'没有选择数据','提示');
		return;
	}
	
	Frm.confirm(msg?msg:'确定要删除指定数据吗？','提示',function(cfm){
		if(cfm){
			if(action)
				$(formcss).attr('action',action);
			$(formcss).submit();
		}
	});
}

function getSelAll(css){
	var check = false;
 		var ids="";
 		$(css).each(
 			function(i,t){
 				if(t.checked){
 					check = true;
 					ids+=t.value+",";
 				}
 			}
 		);
 		
 		
 		if(!check){
 			Frm.alert('没有选择数据','警告');
 			return ids;
 		}
 	return ids.substring(0,ids.length-1);
}


function getSelOne(css){
	var check = false;
	var m =-1;
	var item ;
	$('.'+css).each(
		function(i,t){
			if(check && t.checked){
				Frm.alert('只能选择一条数据','警告');
				item = null;
				return null;
			}
			if(!check && t.checked){
				check = true;
				m=i;
				item = t;
			}
		}
	);
	
	
	if(!check){
		Frm.alert('没有选择数据','警告');
		return null;
	}else{
		return item;
	}
}

function showTips(message,showSeconds){
	if(!isRootWindow()){
		parent.showTips(message,showSeconds);
		return;
	}
	if(!showSeconds){
		showSeconds = 5000;
	}
	$('#_tipmessage').text(message);
	$( "#_autotips" ).animate({
		opacity: 0
	}, 0 );
	$('#_autotips').show();
	$( "#_autotips" ).animate({
		opacity: 1
	}, 500 );
	if(showSeconds)
		setTimeout(autoCloseTips,showSeconds);
}

function autoCloseTips(){
	$( "#_autotips" ).animate({
		opacity: 0
	}, 2000 );
	setTimeout(closeTips,2000);
}


function closeTips(){
	$('#_autotips').hide();
}




