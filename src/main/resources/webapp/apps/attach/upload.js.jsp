<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#session.logined_user!=null">
	<s:set name="_attm" value="''"/>
</s:if>
<s:else>
	<s:set name="_attm" value="'m'"/>
</s:else>
<!-- 
var _att_id;
function delAtt(id){
	_att_id = id;
	Frm.confirm('确定要删除吗？','提示',function(cfm){
		if(cfm){
			$.get('${pageContext.request.contextPath}/${_attm}file/deleteRef.action?id='+_att_id, {}, function (data, textStatus){
				$('#att_'+_att_id).remove();
			});
		}
	});
}

var FileUpload=function(title,w,h,catalog,type,fileext){
	this.objectId = null;
	this.title = title;
	this.w=w;
	this.h=h;
	this.catalog=catalog;
	this.type=type;
	this.fileext = fileext;
	this.win = null;
	this.container = null;
	this.num = 0;
	this.singleMaxSize=0;
	this.show=function(){
		var name = 'uploaed'+Math.floor(Math.random()*2000);
		eval('window.'+name+"=null;");
		var item = this;
		var func = function(id, name, url, mime, ext,ref){
			item.uploaded(id, name, url, mime, ext,ref);
		}; 
		eval('window.'+name+'=func;');
		this.win = new FrameWindow({id:'uploadWindow',title:title,url:'${pageContext.request.contextPath}/${_attm}file/load.action?objectId='+this.objectId+'&catalog='+this.catalog+'&type='+this.type+'&callback='+name+'&fileext='+this.fileext+'&num='+this.num+'&singleMaxSize='+this.singleMaxSize,width:this.w,height:this.h})
		this.win.show();
	};
	this.uploaded=function(files){
		for(var m=0;m<files.length;m++){
			var id=files[m].id;
			var name = files[m].filename;
			var mime = files[m].mimetype;
			var size = files[m].size;
			var fileext = files[m].fileext;
			var mime = files[m].mimetype;
			var ref = files[m].ref;
			var url = '${pageContext.request.contextPath}'+files[m].path;
			var div =  $('<div class="fileitem"></div>');
			div.attr('id','att_'+ref);
			var a;
			if(this.type=='add'){
				if(mime!=null && mime.indexOf('image/')==0){
					a= $('<img class="uimg" width="128"/>');
					a.attr('src',url);
					a.attr('alt',name);
				}else{
					a= $('<a href="'+url+'"  title="'+name+'">'+name+'</a>');
				}
				var b = $('<a class="icon-del"  href="javascript:delAtt(\''+ref+'\')"  title="删除'+name+'"></a>');
				a.appendTo(div);
				b.appendTo(div);
				div.appendTo($("#"+this.container));
			}else{
				a = $("#"+this.container);
				if(a.tagName =='a' || a.tagName =='A'){
					a.attr('href',url);
					a.attr('title',name);
					a.text(name);
				}else if(a.tagName =='img' || a.tagName =='IMG'){
					a= $('<img class="uimg" width="128"/>');
					a.attr('src',url);
					a.attr('alt',name);
				}else {
					a.html('');
					if(mime!=null && mime.indexOf('image/')==0){
						a= $('<img class="uimg" width="128"/>');
						a.attr('src',url);
						a.attr('alt',name);
					}else{
						a= $('<a href="'+url+'"  title="'+name+'">'+name+'</a>');
					}
					var b = $('<a class="icon-del"  href="javascript:delAtt(\''+ref+'\')"  title="删除'+name+'"></a>');
					a.appendTo(div);
					b.appendTo(div);
					div.appendTo($("#"+this.container));
				}
			}
			div.html(div.html()+'<div class="upload_status">上传完成</div>');
		}
		this.win.hide();
	};
}


var FileUploadDialog=function(config){
	this.config = config;
	this.show=function(){
		var name = 'uploaed'+Math.floor(Math.random()*2000);
		eval('window.'+name+"=null;");
		var item = this;
		var func = function(files){
			item.config.callback(files);
			item.win.hide();
		}; 
		eval('window.'+name+'=func;');
		this.win = new FrameWindow({id:'uploadWindow',
			title:this.config.title,
			url:'${pageContext.request.contextPath}/${_attm}file/load.action?objectId='+this.config.objectId+'&catalog='+this.config.catalog+'&type='+this.config.type+'&callback='+name+'&fileext='+this.config.fileext+'&num='+this.config.num+'&singleMaxSize='+this.config.singleMaxSize,
			width:500,
			height:400})
		this.win.show();
	};
}

 -->