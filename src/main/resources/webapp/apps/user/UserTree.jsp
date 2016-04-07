var _utCount=0;
var UserTree=function(config){
	this.config = config;
	this.win = null;
	this.index = _utCount++;
	this.callback=null;
	this.show=function(){
		var name = 'userTree'+this.index;
		eval('window.'+name+"=null;");
		var item = this;
		var func = function(items){
			item.config.callback(items);
			//item.callback(items);
			item.win.hide();
		}; 
		eval('window.'+name+'=func;');
		var url = '${pageContext.request.contextPath}/user/tree.action?';
		url+='callback='+name;
		url+='&select='+this.config.select;
		if(this.config.selectedId)
			url+='&selectedId='+this.config.selectedId;
		if(this.config.selectedNames)
			url+='&selectedNames='+this.config.selectedNames;
		url+='&type='+this.config.type;
		url+='&showTree='+this.config.showTree;
		if(this.config.posName)
			url+='&posName='+this.config.posName;
		url+='&scope='+this.config.scope;
		this.win = new FrameWindow(this.config.title,url,this.config.width,this.config.height)
		this.win.show();
	};
	
}
UserTree.open=function(config){
	var tree = new UserTree(config);
	tree.show();
}