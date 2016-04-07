<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="resource_include.jsp" %>
<t:fragment name="_title">添加资源</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#saveSystemResource').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
var validate_rules={
	'resource.id' : {required:true,rangelength:[5,32]}
	,'resource.name' : {required:!false,rangelength:[2,32]}
	,'resource.packageName' : {required:!false,rangelength:[2,32]}
};
</script>
</t:fragment>
<t:tpl menuId="__auth_resource" param="support=form">
		<s:form action="saveSystemResource">
		<table class="table-form">
			<tr>
				<td class="form-feild">编号</td>
				<td><s:textfield name="resource.id" value="%{#parameters.parentId[0].substring(0,#parameters.parentId[0].lastIndexOf('.'))}" title="访问接口的编号，字母组合"/></td>
			</tr>
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="resource.name" /></td>
			</tr>
			<tr>
				<td class="form-feild">包</td>
				<td><s:textfield name="resource.packageName" value="%{#request.parent.packageName}"/></td>
			</tr>
			<tr>
				<td class="form-feild">权限类型</td>
				<td><s:select name="resource.api" list="#apis" headerKey="" headerValue="无"/></td>
			</tr>
			<tr>
				<td class="form-feild">链接</td>
				<td><s:textfield name="resource.url"/></td>
			</tr>
			<tr>
				<td class="form-feild">类型</td>
				<td><s:radio list="#types" name="resource.type" value="'0'" /></td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td><s:textarea name="resource.description"/></td>
			</tr>
			<tr>
				<td></td>
				<td>
				<s:submit value="保存"/>
				<s:if test="%{#parameters.parentId!=null}">
				<s:hidden name="resource.parent.id" value="%{#parameters.parentId[0]}"/>
				</s:if>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>