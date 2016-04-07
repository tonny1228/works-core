<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Element.list.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall"><s:text name="button.selall"/></a>
	<a onclick="f('add.action')" class="button-main button-icon-add"><s:text name="button.add"/></a>
	<a onclick="delSelected('.chk','#delete')" class="button-main button-icon-del"><s:text name="button.delete"/></a>
</t:fragment>
<t:tpl menuId="__element">
		<s:form name="delForm" action="delete">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td><s:text name="Element.name"/></td>
				<td><s:text name="Element.dataType"/></td>
				<td><s:text name="Element.viewType"/></td>
				<td><s:text name="Element.required"/></td>
				<td><s:text name="Element.minLength"/></td>
				<td><s:text name="Element.maxLength"/></td>
				<td><s:text name="Element.regex"/></td>
				<td><s:text name="Element.catelog"/></td>
				<td><s:text name="Element.updateTime"/></td>
				<td><s:text name="Element.admin"/></td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
				<td><a href="view.action?id=${id }"><s:property value="name" escape="false"/></a></td>
				<td><s:property value="dataType" escape="false"/></td>
				<td>${type[dataType][viewType] }</td>
				<td>${bool[required]}</td>
				<td><s:property value="minLength" escape="false"/></td>
				<td><s:property value="maxLength" escape="false"/></td>
				<td><s:property value="regex" escape="false"/></td>
				<td><s:property value="catelog" escape="false"/></td>
				<td><s:date name="updateTime" format="yyyy-MM-dd"/></td>
				<td><s:property value="admin" escape="false"/></td>
			</tr>
		</s:iterator>
		</table>
		<t:page bindData="#request.list"/>
		</s:form>
</t:tpl>

