<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">岗位管理</t:fragment>
<t:fragment name="_ctlbnt">
	<a class="button-main button-icon-add" onclick="f('addUser.action?positionId=${position.id}&_theme=simple')">新建用户</a>
	<a class="button-main button-icon-list" onclick="f('positionUserList.action?positionId=${position.id}')">用户列表</a>		
	<a class="button-main button-icon-move" onclick="movePosition()">移动</a>		
</t:fragment>	
<t:fragment>
	<script>
	var validate_rules={
		'position.name' : {required:true,rangelength:[1,30]},
		'jobLevel' : {required:true}
	};
	addOnLoad(function (){
		if('${param.refresh}')
			parent.refreshNodeQuietly('${param.refresh}');
	});
	
	function movePosition(){
		UserTree.open({title:'移动到',type:'dbo',scope:'o',width:350,height:500,callback:function(t){
			Frm.confirm('您将移动此岗位到'+(t[0].name)+'?','',function(cfm){
				if(cfm){
					f('movePosition.action?id=${position.id}&forward=editPosition.action%3fid%3d${position.id}&departmentId='+t[0].id);
				}
			});
		}});
	}

	</script>
</t:fragment>
	
<t:tpl param="support=userTree">
		<s:form action="updatePosition">
		<table class="table-form">
			<tr>
				<td class="form-feild">部门</td>
				<td>${parent.name}</td>
			</tr>
			<tr>
				<td class="form-feild required">编号</td>
				<td><s:textfield name="position.id" readonly="true"/></td>
			</tr>
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="position.name" /></td>
			</tr>
			<tr>
				<td class="form-feild">职务/职级</td>
				<td><s:doubleselect name="job" list="#request.jobs" listKey="id" listValue="name" doubleListKey="id" doubleListValue="name" doubleName="jobLevel" value="%{position.jobLevel.job.id}" doubleValue="%{position.jobLevel.id}" doubleList="jobLevels"/></td>
			</tr>
			<tr>
				<td class="form-feild">上级岗位</td>
				<td><s:select name="position.treeNode.parentId"  headerKey="" headerValue="" list="#request.positions" listKey="id" listValue="name" /></td>
			</tr>
			<tr>
				<td class="form-feild">部门负责人</td>
				<td><s:checkbox name="position.commander"/></td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td><s:textarea name="position.description"/></td>
			</tr>
			<tr>
				<td></td>
				<td>
				<s:submit value="保存"/>
				<a class="button ui-button-danger" onclick="del('deletePosition.action?id=${position.id}&deptId=${parent.id}')" >删除</a>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>
