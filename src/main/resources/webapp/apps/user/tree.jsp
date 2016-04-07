<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">组织管理</t:fragment>
<t:fragment name="_header">
	<script>
		var item;
		var selectedUser= [];
		function OK(){
			var zTree = $.fn.zTree.getZTreeObj("tree");
			/*
			var nodes = zTree.getCheckedNodes(true);
			for(i=0;i<selectedUser.length;i++){
				nodes[nodes.length]=selectedUser[i];
			}
			*/
			var x = opener?opener:parent;
			x.<%=request.getParameter("callback")%>(selectedUser);
		}
		
		var zTree;
		var demoIframe;
	 
		var setting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				expandSpeed: "fast"
			},
			async: {
				enable: true,
				url:"${pageContext.request.contextPath}/user/department.action?scopeId=${id}&s=${scope}&t=${type}&selectedId=${param.selectedId}",
				autoParam:["id","type"],
				otherParam:{},
				dataFilter: filter
			},
			data:{
				enable: true
			},
			callback: {
				beforeClick: function(treeId, treeNode) {
					item = treeNode;
				},
				onNodeCreated:loadedNode,
				<s:if test="#request.type.indexOf('u')>=0">
				onClick:getUser,
				</s:if>
				onCheck:checkSelectedDepts
			},
			
			check:{
				chkStyle:'${select}',
				chkboxType:{ "Y" : "", "N" : "" },
				radioType:'all',
				enable: true
			}
		};
		
		function loadedNode(event,treeId,node){
			if(node.getParentNode()!=null){
				if(node.type=="Position"){
					node.department=node.getParentNode();
				}else{
					node.parent=node.getParentNode();
				}
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.expandNode(node);
		}
		
		var users = [];
		
		var lastPage = 0;
		var pagesize = 0;
		var total =0;
		function getUser(event, treeId, treeNode, clickFlag){
			users = [];
			lastPage = 0;
			pagesize = 0;
			total = 0;
			getUserPage(treeNode.id, treeNode.type,null, 1);
		}
		
		function search(id,type,page){
			users = [];
			lastPage = 0;
			pagesize = 0;
			total = 0;
			getUserPage(id, type,$('#seb').val(), 1);
		}
		
		
		function getUserPage(id,type,name,page){
			if(page <= lastPage){
				var newNodes = [];
				for(i=0;i<pagesize;i++){
					newNodes[i]=users[(page-1)+i];
				}
				drawTable(id,type,name,page,newNodes);
				return;
			}
			
			if(!name) name='';
			$.ajax({
			    url: '${pageContext.request.contextPath}/user/listUser.action?id='+id+'&type='+type+'&name='+name,
			    type: 'post',
			    dataType: "text",
			    error: function(e,err){
			        Frm.alert(e.message+"/"+err);
			    },
			    success: function(data){
			    	
			    	var data = eval("(" + data + ")");
			    	var newNodes = data.list;
			    	lastPage = page;
			    	pagesize = data.page.size;
			    	total = data.page.total;
			    	drawTable(id,type,name,page,newNodes);
			    	//$('#treeDiv').height(Math.max($('#usertable').height(),$('#tree').height()+30));
			    }
			    
			});
		}
		
		var selectedId="";
		
		
		function checkSelected(){
			selectedId="";
			for(i=0;i<selectedUser.length;i++){
				if(selectedUser[i]!=null)
				selectedId+=selectedUser[i].id+";";
			}
		}
		
		function drawTable(id,type,name,page,newNodes){
			checkSelected();
			var html="<form id='userform'><input name='name' value='"+name+"' id='seb'/><input type='button' value='搜索' onclick='search(\""+id+"\",\""+type+"\",1)'/>"
				+"<table class='table-list'><tr class='table-list-head'><td width='50'>选择</td><td>登录名</td><td>姓名</td><td>部门</td><td>岗位</td></tr>";
	    	for(i=0;i<newNodes.length;i++){
	    		users[users.length]=newNodes[i];
	    		html+="<tr class='table-list-data'>";
	    		html+="<td><input type='${select}' "+(selectedId.indexOf(newNodes[i].id)>=0?'checked="checked"':'')+" onchange='checkSelectedUser(this)' name='user' class='user' value='{id:\""+newNodes[i].id+"\",name:\""+newNodes[i].name+"\",username:\""+newNodes[i].username+"\",departmentId:\""+newNodes[i].departmentId+"\",department:\""+newNodes[i].department+"\",positionId:\""+newNodes[i].positionId+"\",position:\""+newNodes[i].position+"\",type:\"user\"}'/></td>";
	    		html+="<td>"+newNodes[i].username+"</td><td>"+newNodes[i].name+"</td><td>"+newNodes[i].department+"</td><td>"+newNodes[i].position+"</td></tr>";
	    	}
	    	if(total>0){
		    	html+="<tr><td align='center' colspan='5'>当前第"+page+"页，共"+(Math.ceil(total/pagesize))+"页"
		    	if(page>1)
		    		html+="<a href=\"javascript:getUserPage('"+id+"','"+type+"','"+name+"',"+(page-1)+")\">上一页</a> ";
				if(page*pagesize<total)
					html+="<a href=\"javascript:getUserPage('"+id+"','"+type+"','"+name+"',"+(page+1)+")\">下一页</a> ";
		    	html+="</td></tr>";
			}else{
				html+="<tr><td align='center' colspan='5'>没有用户信息</td></tr>";
			}
	    	html+="</table></form>";
			$('#usertable').html(html);
		}
		
		function checkSelectedUser(inp){
			var x = eval("("+inp.value+")");
			makeSelected(x,inp.checked);
				
				
			if('${select}'=='checkbox' || !inp.checked){
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var nodes = zTree.getCheckedNodes(true);
			for(i=0;i<nodes.length;i++){
				zTree.checkNode(nodes[i],false,false);
			}
		}
		
		function makeSelected(x,checked){
			var contains = false;
			var html = "";
			if('${select}'=='checkbox'){
				for(i=0;i<selectedUser.length;i++){
					if(selectedUser[i]!=null && selectedUser[i].id==x.id){
						contains=true;
						if(!checked){
							selectedUser[i]=null;
						}
					}
					if(selectedUser[i]!=null)
						html+="<a href='javascript:removeUser("+i+")' title='点击删除' style='text-decoration: underline;'>"+selectedUser[i].name+";</a> "; 
				}
			}else{
				selectedUser=[];
			}
			if(!contains && checked){
				selectedUser[selectedUser.length]=x;
				html+="<a href='javascript:removeUser("+selectedUser.length+")' title='点击删除'  style='text-decoration: underline;'>"+x.name+";</a> "; 
			}
			$('#selectedUser').html(html);
		}
		
		
		function removeUser(i){
			selectedUser[i]=null;
			var html = "";
			for(i=0;i<selectedUser.length;i++){
				if(selectedUser[i]!=null)
					html+="<a href='javascript:removeUser("+i+")' title='点击删除' style='text-decoration: underline;'>"+selectedUser[i].name+";</a> "; 
			}
			$('#selectedUser').html(html);
		}
		
		function checkSelectedDepts(event,treeId,node){
			makeSelected(node,node.checked);
			if('${select}'=='checkbox'||!node.checked){
				return;
			}
			var chks = $('.user').each(function(index,data){
				if(!data.checked){
					return;
				}
				data.checked=false;
			});
		}
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			if('${type}'.indexOf('u')>=0 || '${type}'.indexOf('p')>=0 ){
				return childNodes;
			}
			var childNodes1 = [];
			for (var i=0, l=childNodes.length; i<l; i++) {
				if(childNodes[i].type=='BusinessUnit'){
					childNodes1[childNodes1.length] = childNodes[i];
				}
				if(childNodes[i].type=='Organization'){
					childNodes1[childNodes1.length] = childNodes[i];
				}
				if(childNodes[i].type=='Department'  && ('${type}'.indexOf('d')>=0 || '${type}'.indexOf('p')>=0|| '${type}'.indexOf('u')>=0)  ){
					childNodes1[childNodes1.length] = childNodes[i];
				}
				if(childNodes[i].type=='Position' && '${type}'.indexOf('p')>=0 ){
					childNodes1[childNodes1.length] = childNodes[i];
				}
			}
			return childNodes1;
		}
		
		function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("tree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				Frm.alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], "refresh", true);
			}
		}
		
		function refreshParentNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("tree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				Frm.alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].getParentNode())
					zTree.reAsyncChildNodes(nodes[i].getParentNode(), "refresh", true);
				zTree.selectNode(nodes[i]);
			}
		}
		
		function refreshDelay(w){
			w.setTimeout(w.refreshNode,5000);
		}
		function refreshParentDelay(w){
			w.setTimeout(w.refreshParentNode,5000);
		}
		
		var zNodes =[];
 		<s:iterator value="#request.list" status="sta">
			zNodes[zNodes.length]={id:'${id}',  name:"${name}", open:false};
		</s:iterator>
 		<s:iterator value="#request.companies" status="sta">
			zNodes[zNodes.length]={id:'${id}', name:"${name}", open:false};
		</s:iterator>
		$(document).ready(function(){
			var t = $("#tree");
			t = $.fn.zTree.init(t, setting);
			var zTree = $.fn.zTree.getZTreeObj("tree");
			<s:if test="#parameters.showTree[0]=='false'">
			$('#tdleft').hide();
			<s:if test="#request.position!=null">
			getUserPage('${position}', 'position',null, 1);
			</s:if>
			<s:elseif test="%{id!=null}">
			getUserPage('${id}', 'department',null, 1);
			</s:elseif>
			<s:elseif test="#parameters.showTree[0]=='false'">
			getUserPage('', '',null, 1);
			</s:elseif>
			</s:if>
		});
		addOnLoad(function(){
			for(var i =0;i<selectedUser.length;i++){
				var u = selectedUser[i];
				$.ajax({
					type:'post',
					url:'editUser.action?id='+selectedUser[i].id+'&_output=xml',
					data:'',
					dataType:'xml',
					success:function(data){
						if(!$(data).find('username').text()){
							return;
						}
						u.username=$(data).find('username').text();
						u.email=$(data).find('email').text();
					}
				});
			}
		});
	</script>
	<style>
</style>
</t:fragment>

<t:tpl name="simple" param="support=tree">
	<div style="border: 1px solid #dedede;margin-left: 3px;padding: 3px;">已选择对象：<span id='selectedUser'>
	<s:set name="sid" value="#parameters.selectedId[0].split(',')"/>
	<s:set name="sname" value="#parameters.selectedNames[0].split(',')"/>
	<s:iterator value="#sid" status="st">
		<a href="javascript:removeUser(${st.index })">
		<s:if test="#sname!=null && #sname.length>#st.index">
			<s:property value="#sname[#st.index]"/>;
			<script>selectedUser[${st.index }]={id:'<s:property value="#sid[#st.index]"/>',name:'<s:property value="#sname[#st.index]"/>'};</script>
		</s:if>
		<s:else>
			<s:property value="#sid[#st.index]"/>;
			<script>selectedUser[${st.index }]={id:'<s:property value="#sid[#st.index]"/>',name:'<s:property value="#sid[#st.index]"/>'};</script>
		</s:else>
		</a>
	</s:iterator>
	</span></div>
	<table id="treeview" border="0" width="100%">
		<tr>
		<td valign="top" width="300" id='tdleft'>
		<div id="treeDiv" style="float: left;border: 1px solid #aaa;min-width: 280px;">
		<ul id="tree" class="ztree" style="width:auto; overflow:auto;"></ul>
		</div>
		</td>
		<s:if test="#request.type.indexOf('u')>=0">
		<td style="min-width:500px;" valign="top">
		<div id="usertable" style="margin-left: 3px;width: 100%">
		</div>
		</td>
		</s:if>
		</tr>
	</table>
	
</t:tpl>

