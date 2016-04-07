<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="resource_include.jsp" %>
<t:fragment name="_title">资源管理</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updateSystemResource').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
	<a onclick="del('deleteSystemResource.action?id=${resource.id}')" class="button-main button-icon-del"><s:text name="button.delete"/></a>
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
		<div class="title-sub">接口描述</div>
		<s:form action="updateSystemResource">
		<table width="650" class="table-form">
			<tr>
				<td class="form-feild">编号</td>
				<td><s:textfield name="resource.id" readonly="true" title="访问接口的编号，字母组合"/></td>
			</tr>			
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="resource.name" /></td>
			</tr>
			<tr>
				<td class="form-feild">包</td>
				<td><s:textfield name="resource.packageName"/></td>
			</tr>			
			<tr>
				<td class="form-feild">权限类型</td>
				<td><s:select name="resource.api" list="#apis" headerKey="" headerValue="无"/></td>
			</tr>
			<tr>
				<td class="form-feild">链接</td>
				<td><s:textfield name="resource.url" /></td>
			</tr>
			<tr>
				<td class="form-feild">类型</td>
				<td><s:radio list="#types" name="resource.type" /></td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td><s:textarea name="resource.description"/></td>
			</tr>
		</table>
		</s:form>
		<s:if test="resource.parent==null">
		<div class="title-sub">子接口</div>
		<div class="button-subs">
		    <a onclick="selAll('.cb',this)">全选</a>
		    <a onclick="f('addSystemResource.action?parentId=${resource.id}')" class="ui-button-success">添加</a>
		    <a onclick="delSelected('.cb','#deleteSystemResource')" class="ui-button-danger">删除</a>
		</div>
		<s:form action="deleteSystemResource">
			<table align="center" class="table-list">
				<tr class="table-list-head">
					<td width="30">选择</td>
					<td>名称</td>
					<td>接口</td>
					<td>类型</td>
					<td>链接</td>
					<td>描述</td>
				</tr>
				<s:iterator value="resource.subs">
					<tr class="table-list-data">
						<td><s:checkbox name="id" fieldValue="%{id}" cssClass="cb" />
						</td>
						<td><s:a href="editSystemResource.action?id=%{id}">
								<s:property value="name" escape="false" />
							</s:a>
						</td>
						<td>${api}
						</td>
						<td><s:property value="#types[type]" escape="false" />
						</td>
						<td><s:property value="description" escape="false" />
						</td>
						<td>${url }
						</td>
					</tr>
				</s:iterator>
			</table>
			
			<s:hidden name="parentId" value="%{#request.resource.id}" />
		</s:form>
		</s:if>
</t:tpl>