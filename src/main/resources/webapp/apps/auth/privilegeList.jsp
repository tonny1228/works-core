<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="privilege_include.jsp" %>
<t:fragment name="_title">权限列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('addPrivilege.action')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.chk','#deletePrivilege')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__auth">
		<s:form action="deletePrivilege">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>名称</td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
				<td><s:a href="editPrivilege.action?id=%{id}" ><s:property value="name" escape="false"/></s:a></td>
			</tr>
		</s:iterator>
		</table>
		</s:form>
</t:tpl>

