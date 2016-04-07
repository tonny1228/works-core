<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updateUserinfo').submit()" class="button-main button-icon-save">保存</a>
</t:fragment>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">编辑用户</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--
var  validate_rules={
		'positionId': {required:true}
};
function userTree(){
//	window.x = new FrameWindow('选择职位','tree.action?callback=selectPosition',300,400);
//	x.show();
	UserTree.open({title:'选择职位',width:400,height:400,type:'p',scope:'b',callback:selectPosition});
}


function selectPosition(pos){
	var id = pos[0].id;
	var name = pos[0].name;
	$('#positions').html($('#positions').html()+'<input type="radio" name="positionId" id="saveUser_default'+
			id+'" value="'+id+'"/><label for="saveUser_default'+id+'">'+name+'</label><input type="hidden" name="position" value="'+id+'"/>');
}

//-->
</script>
</t:fragment>
<t:tpl menuId="__user"  param="support=userTree,form">
	<t:authCheck auth="UserEntityService.update" errorMessage="<div>您没有权限</div>">
		<s:form action="updateUserinfo">
		<table id="tab" class="table-form">
			<tr>
				<td class="form-feild">职称</td>
				<td><s:select name="title" list="#request.titles" value="%{user.title.id}" listKey="id" listValue="name" headerKey="" headerValue="无"/></td>
			</tr>
			<tr>
				<td class="form-feild">职位</td>
				<td id="positions">
				<s:iterator var="pos" value="%{user.positions}">
					<s:radio name="positionId" list="pos" listKey="id" listValue="name" value="%{user.position.id }"/><input type="hidden" name="position" value="${id}"/>
				</s:iterator>
				</td>
			</tr>
			<tr><td></td><td><a onclick="userTree()" class="button">添加职位</a></td></tr>
		</table>
		<s:hidden name="id" value="%{#request.user.id}"></s:hidden>
		</s:form>
	</t:authCheck>
</t:tpl>
