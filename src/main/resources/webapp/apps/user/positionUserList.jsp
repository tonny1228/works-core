<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">用户管理 &gt; 列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="f('addUser.action?positionId=${param.positionId}')" class="button-main button-icon-add">添加</a>
	<a onclick="userTree();" class="button-main button-icon-add">从已有用户中添加</a>
</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--
function userTree(){
//	window.x = new FrameWindow('选择职位','tree.action?callback=selectPosition',300,400);
//	x.show();
	UserTree.open({title:'选择用户',width:600,height:550,type:'u',showTree:'false',callback:addUser});
}
var success=0;
function addUser(user){
	for(i=0;i<user.length;i++){
		//alert(user);
		submitUser(user[i].id,i,user.length);
	}
}

function submitUser(id,idx,total){
	$.ajax({
	    url: 'updateUserinfo.action',
	    type: 'post',
	    data:'id='+id+'&positionId=${param.positionId}&position=${param.positionId}',
	    dataType: "text",
	    error: function(e,err){
	        Frm.alert(e.message+"/"+err);
	    },
	    success: function(data){
	    	success++;
	    	if(idx+1==total){
	    		//showTips('已添加'+success+'个用户',5000);
	    		f(window.location+'&_tips=tip_save_success');
	    	}
	    }
	});
}

	
//-->
</script>
</t:fragment>
<t:tpl menuId="__user" param="support=userTree">
	<s:form action="positionUserList" method="get">
		<s:hidden name="positionId" value="%{#parameters.positionId}"/>
	<div class="form-search">
		用户名: <s:textfield name="username" value="%{#parameters.username}"/>
		姓名: <s:textfield name="name" value="%{#parameters.name}"/>
		每页: <s:textfield name="pagesize" value="%{#request.list.pagesize}"/>条
		<s:submit value="查询"/>
	</div>
	</s:form>
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>用户名</td>
				<td>姓名</td>
				<td>电话</td>
				<td width="100">删除用户此岗位</td>
			</tr>
			<s:iterator id="user" value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{email}" cssClass="chk"/></td>
				<td><a href="editUser.action?id=${id }">${user.username}</a></td>
				<td>${user.name}</td>
				<td>${user.telNo}</td>
				<td align="center"><a href="javascript:del('deleteUserOfPosition.action?id=${user.id}&positionId=${param.positionId}','您确定要从此岗位中删除该用户吗?')">删除</a></td>
			</tr>
			</s:iterator>
		</table>
		<t:page bindData="#request.list"/>
</t:tpl>