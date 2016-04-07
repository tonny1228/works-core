<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
	<t:fragment>
		<SCRIPT type="text/javascript">
		
		function o(title,u,w,h){
			var x = new FrameWindow(title,u,w,h);
			x.show();
		}
		function initTree(){
			var tree = new Ext.tree.TreePanel({   
		        el:"tree",   
		        animate:false,
		        layout:'fit',
			    split: true,
		        enableDD:true,
		        containerScroll: false,
		        rootVisible:false

		    });
		   	tree.on("click",function(node,event){
		   		node.select();
			}); 
			var expand = function(parent){
				parent.removeAll(true);
			    $.ajax({
				    url: (parent.id=='root'?'/user/rootDepartment.action?id='+parent.id:'/user/subDepartment.action?id='+parent.id),
				    type: 'post',
				    dataType: "json",
				    error: function(e,err){
				        alert(e.message+"///"+err);
				    },
				    success: function(list){
				    	 $.each(list.topics,function(i,a){
				    	 	var col = new Ext.tree.TreeNode({
						        text: a.name,
						        draggable:false,
						        leaf:false,
						        expandable:true,
						        id:a.id,
						        icon:a.type=='department'?(a.idLayer?'../images/icon/dept.gif':'../images/icon/com.gif'):('../images/icon/role.png'),
						        listeners:{
									"expand":a.type=='department'?expand:function(node){
										node.removeAll(true);
										$.each(a.user,function(j,u){
											if(j==0) return;
								    	 	var user = new Ext.tree.TreeNode({
										        text: u.name,
										        draggable:false,
										        leaf:true,
										        expandable:false,
										        id:u.id,
										        icon:'../images/icon/user.gif',
										        listeners:{
										        	"click":function(node,e){
														o('编辑','/user/editUser.action?id='+u.id,580,620);
													}
										        }
									    	});
									    	node.appendChild(user);
									    });
									},
									"click":function(node,e){
										o(a.type=='department'?'编辑':'编辑',a.type=='department'?"/user/editDepartment.action?id="+a.id:"/user/editPosition.action?id="+a.id,580,300);
									}
								}
					    	});
					    	parent.appendChild(col);
					    });
				    }
				});			
			};
			
			var root = new Ext.tree.TreeNode({
		        text: '用户管理',
		        draggable:false,
		        id:'root',
		       	expanded:false,
		       	expandable:true,
		       	qtip:'',
		       	listeners:{
					"expand":function(node){
						expand(node);
					}
				}
		    });
			
		    tree.setRootNode(root);
		    tree.render();
		    root.expand(true);
			
		} 
		addOnLoad(initTree);
		</SCRIPT>	
	</t:fragment>
	<%@ include file="user_include.jsp" %>
	<t:fragment name="_title">用户管理</t:fragment>
	<t:fragment name="_ctlbnt">
	<a onclick="f('/user/addDepartment.action')">新建部门</a>	
	</t:fragment>
<t:tpl>
	<div id="tree" style="margin-left:10px;height: 100%"></div>
</t:tpl>
