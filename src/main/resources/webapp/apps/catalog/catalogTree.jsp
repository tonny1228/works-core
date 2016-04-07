<%@ page language="java" pageEncoding="utf-8"%>
var _ctCount=0;
var CatalogTree=function(title,w,h,id){
	if(typeof(title)!='string'){
		return new CatalogTree2(title);
	}
	this.title = title;
	this.w=w;
	this.h=h;
	this.id=id;
	this.type=0;
	this.root = null;
	this.callback=null;
	this.async=false;
	this.multi=false;
	this.event=null;
	this.selected=null;
	this.depth = 0;
	this.win = null;
	this.index = _ctCount++;
	this.show=function(){
		var name = 'catalogTree'+this.index;
		eval('window.'+name+"=null;");
		var item = this;
		var func = function(id, name,layer,nameLayer){
			item.callback(id, name,layer,nameLayer);
			item.win.hide();
		}; 
		eval('window.'+name+'=func;');
		var url = '${pageContext.request.contextPath}/catalog/tree.action?';
		url+='callback='+name;
		url+='&id='+id;
		if(this.root)
			url+='&root='+this.root;
		if(this.selected)
			url+='&selected='+this.selected;
		if(this.async)
			url+='&async='+this.async;
		if(this.multi)
			url+='&multi=multi';
		if(this.event)
			url+='&event='+this.event;
		if(this.depth)
			url+='&depth='+this.depth;
		this.win = new FrameWindow(this.title,url,this.w,this.h)
		this.win.show();
	};
}

var CatalogTree2=function(c){
	this.win = null;
	this.config = c;
	this.index = _ctCount++;
	this.show=function(){
		var name = 'catalogTree'+this.index;
		eval('window.'+name+"=null;");
		var item = this;
		var func = function(id, name,layer,nameLayer){
			item.config.callback(id, name,layer,nameLayer);
			item.win.hide();
		}; 
		eval('window.'+name+'=func;');
		var url = '${pageContext.request.contextPath}/catalog/tree.action?';
		url+='callback='+name;
		if(this.config.id)
			url+='&id='+this.config.id;
		if(this.config.root)
			url+='&root='+this.config.root;
		if(this.config.selected)
			url+='&selected='+this.config.selected;
		if(this.config.async)
			url+='&async='+this.config.async;
		if(this.config.multi)
			url+='&multi=multi';
		if(this.config.event)
			url+='&event='+this.config.event;
		if(this.config.depth)
			url+='&depth='+this.config.depth;
		var c = this.config;
		this.win = new FrameWindow({title:this.config.title,url:url,width:this.config.width,height:this.config.height,id:this.config.id,buttons:{"确定":function(){
			var a = $('#frmframewindow'+c.id).get(0);
			if(a.contentWindow.OK)
				a.contentWindow.OK();
			$('#framewindow'+c.id).dialog('close');
		}}});
		this.win.show();
	};
}

CatalogTree.open=function(config){
	var tree = new CatalogTree2(config);
	tree.show();
}