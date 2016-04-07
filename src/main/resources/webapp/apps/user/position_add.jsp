<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">岗位管理</t:fragment>
<t:fragment>
	<script>
	var validate_rules={
			'position.name' : {required:true,rangelength:[1,30]},
			'jobLevel' : {required:true}
		};
	</script>
</t:fragment>
<t:tpl menuId="__position" param="support=form">
		<s:form action="savePosition">
		<table class="table-form">
			<tr>
				<td class="form-feild">部门</td>
				<td>${parent.name}</td>
			</tr>
			<tr>
				<td class="form-feild required">编号</td>
				<td><s:textfield name="position.id"/></td>
			</tr>
			<tr>
				<td class="form-feild required">名称</td>
				<td><s:textfield name="position.name"/></td>
			</tr>
			<tr>
				<td class="form-feild required">职务/职级</td>
				<td><s:doubleselect name="job" list="#request.jobs" listKey="id" listValue="name" doubleListKey="id" doubleListValue="name" doubleName="jobLevel" doubleList="jobLevels"/></td>
			</tr>
			<tr>
				<td class="form-feild">上级岗位</td>
				<td><s:select name="position.treeNode.parentId" headerKey="" headerValue="" list="#request.positions" listKey="id" listValue="name"/></td>
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
				<s:hidden name="parentId" value="%{#request.parent.id}"></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>
