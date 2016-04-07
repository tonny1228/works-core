

var _fwCount=0;


var FrameWindow=function(title,u,w,h,oncomplate){
	if(typeof(title)!='string'){
		return new FrameWindow2(title);
	}
	this.title = title;
	this.u = u;
	this.w = w;
	this.h = h;
	this.oncomplate = oncomplate;
	this.button='go';
	this.id = _fwCount++;
	this.show = function(){
		$("#framewindow"+this.id).dialog('open');
		var frm = $('#frm'+this.id);
		frm.attr('src',u);
	};
	this.hide = function(){$("#framewindow"+this.id).dialog('close');};
		
	if(document.getElementById('framewindow'+this.id)==null){
		$("body").append('<div id="framewindow'+this.id+'"></div>');
	}
	$("#framewindow"+this.id).dialog({
		autoOpen:false,
		title:title,
		modal: true,
		width:w,
		height:h,
		position:['center',$(document.body).offset().top],
		resizable:true,
		closeOnEscape:true,
		open:function(){
			if($("#"+this.id).html()==''){
				$("#"+this.id).html('<iframe frameBorder="0" id="frm'+this.id+'" src="'+u+'" width="100%" height="100%" scrolling="auto"></iframe>');
			}else{
				var frm = $('#frm'+this.id);
				frm.attr('src',u);
			}
		},
		buttons:{
				"保存":function(){
					var a = $('#frm'+this.id).get(0);
					if(a.contentWindow.OK)
						a.contentWindow.OK();
					if(oncomplate){
						oncomplate();
					}
					$(this).dialog('close');
					
				}
		}
	});
}


var FrameWindow2=function(config){
	this.config=config;
	this.show = function(){
		$("#framewindow"+this.config.id).dialog('open');
	};
	this.hide = function(){$("#framewindow"+this.config.id).dialog('close');};
		
	if(document.getElementById('framewindow'+this.config.id)==null){
		$("body").append('<div id="framewindow'+this.config.id+'"></div>');
	}
	$("#framewindow"+this.config.id).dialog({
		autoOpen:false,
		title:this.config.title,
		modal: true,
		width:this.config.width,
		height:this.config.height,
		position:['center',$(document.body).offset().top+100],
		resizable:true,
		closeOnEscape:true,
		open:function(){
			if($("#"+this.id).html()==''){
				$("#"+this.id).html('<iframe frameBorder="0" id="frm'+this.id+'" src="'+config.url+'" width="100%" height="100%" scrolling="auto"></iframe>');
			}else{
				var frm = $('#frm'+this.id);
				frm.attr('src',config.url);
			}
		},
		buttons:this.config.buttons
	});
}

var Dialog=function(config){
	this.config=config;
	this.show = function(){
		if(!this.config.divId){
			$("#diglog"+this.config.id).dialog('open');
		}else{
			$('#'+this.config.divId).dialog('open');
		}
	};
	this.hide = function(){
		if(!this.config.divId){
			$("#diglog"+this.config.id).dialog('close');
		}else{
			$('#'+this.config.divId).dialog('close');
		}
	};
	if(!this.config.divId && document.getElementById('diglog'+this.config.id)==null){
		$("body").append('<div id="diglog'+this.config.id+'">'+this.config.html+'</div>');
		$("#diglog"+this.config.id).dialog({
			autoOpen:false,
			title:this.config.title,
			modal: true,
			width:this.config.width,
			height:this.config.height,
			position:['center',$(document.body).offset().top+100],
			resizable:true,
			closeOnEscape:true,
			buttons:this.config.buttons
		});
	}
	if(this.config.divId){
		$('#'+this.config.divId).dialog({
			autoOpen:false,
			title:this.config.title,
			modal: true,
			width:this.config.width,
			height:this.config.height,
			position:['center',$(document.body).offset().top+100],
			resizable:true,
			closeOnEscape:true,
			buttons:this.config.buttons
		});
	}
	
}

Frm.alert=function(msg,type){
	if(!isRootWindow()){
		parent.Frm.alert(msg,type);
		return;
	}
	if($("#dialogalert").length==0){
		$("body").append('<div id="dialogalert"></div>');
		$("#dialogalert").dialog({
		   autoOpen:false,
		   title:(type?type:'提示'),
		   modal: true,
		   width:300,
		   position:['center','center'],
		   resizable:false,
		   closeOnEscape:true,
		   buttons:{
			   '确定':function(){
		       $(this).dialog('close');
		    }
		   }
		 });
	}
	$("#dialogalert").html(msg);
	$("#dialogalert").dialog('open');	
}

Frm.confirm=function(msg,type,fun){
	/*
	Ext.MessageBox.confirm(type,msg,function(bnt){
		fun(bnt=='yes');
	});
	*/
//	var cfm = confirm(msg);
//	fun(cfm);
	if(!isRootWindow() && fun==null){
		parent.Frm.confirm(msg,type,fun);
		return;
	}
	if($("#dialogconfirm").length==0)
	 {
		$("body").append('<div id="dialogconfirm"></div>');
		$("#dialogconfirm").dialog({
			autoOpen:false,
			title:type?type:'提示',
			modal: true,
			width:300,
			resizable:false,
			buttons:{
				'确定':function(){
					fun(true);
					$(this).dialog('close');
				},
				'取消':function(){
					fun(false);
					$(this).dialog('close');
				}
			}
		});
	 }
	$("#dialogconfirm").html(msg);
	$("#dialogconfirm").dialog('open');	
}




function enableButton(id){
	if($('#'+id).attr('class').indexOf('ui-button')>=0){
		$('#'+id).button('enable');
	}else{
		$('#'+id).attr('disabled',false);
	}
		
}

function disableButton(id){
	if($('#'+id).attr('class').indexOf('ui-button')>=0){
		$('#'+id).button('disable');
	}else{
		$('#'+id).attr('disabled',true);
	}
}
