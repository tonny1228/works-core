<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title">ID生成器列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('add.action')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.chk','#delete')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__idgenerator">
		<s:form name="delForm" action="delete">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>编号</td>
				<td>名称</td>
				<td>表达式</td>
				<td>分隔符</td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="ids" fieldValue="%{id}" cssClass="chk"/></td>
				<td>${id }</td>
				<td><s:a href="edit.action?id=%{id}">${name }</s:a></td>
				<td>${expression }</td>
				<td>${spliter }</td>
			</tr>
		</s:iterator>
		</table>
		</s:form>
</t:tpl>

