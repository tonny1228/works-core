<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="role_include.jsp" %>
<t:fragment name="_title">角色列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('addRole.action')" class="button-main button-icon-add">新建角色</a>
	<a onclick="delSelected('.chk','#deleteRole')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__role">
		<s:form action="deleteRole"> 
		<table class="table-list">
			<tr class="table-list-head">
				<td width="30"></td>
				<td width="150">编号</td>
				<td>名称</td>
				<td >备注</td>
				<td width="100">操作</td>
			</tr>
			<s:iterator id="role" value="#request.list">
			<tr class="table-list-data">
				<td>
				<s:checkbox name="id" fieldValue="%{id}" cssClass="chk" disabled="%{#role.id=='everyone'}"/>
				</td>
				<td>${role.id}</td>
				<td><s:a href="editRole.action?id=%{#role.id}">${role.name}</s:a> </td>
				<td>${role.description}</td>
				<td><s:a href="userPrivilege.action?id=%{#role.id}&type=role">配置权限</s:a></td>
			</tr>
			</s:iterator>
		</table>
		</s:form>
</t:tpl>