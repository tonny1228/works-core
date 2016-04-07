<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="role_include.jsp" %>
<t:fragment name="_title">新建角色</t:fragment>
<t:fragment >
<script type="text/javascript">
<!--
var validate_rules={
	'role.id' : {required:true,rangelength:[1,30],regex:/[a-zA-Z0-9]+/},
	'role.name' : {required:true,rangelength:[2,30]}
}

//-->
</script>
</t:fragment>
<t:tpl menuId="__role" param="support=form">
		<s:form action="saveRole">
		<table class="table-form">
			<tr>
				<td class="form-feild">id</td>
				<td><s:textfield name="role.id" /></td>
			</tr>
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="role.name"/></td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td><s:textfield name="role.description"/></td>
			</tr>
			<tr>
				<td></td>
				<td>
				<s:submit value="保存"/>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>