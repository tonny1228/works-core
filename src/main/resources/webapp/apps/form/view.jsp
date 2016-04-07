<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Form.view.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="f('edit.action?id=${form.id}')"  class="button-main button-icon-edit"><s:text name="button.modify"/></a>
	<a onclick="f('write.action?id=${form.id}')"  class="button-main button-icon-edit"><s:text name="Form.bnt.write"/></a>
	<a onclick="del('delete.action?id=${form.id}')" class="button-main button-icon-del"><s:text name="button.delete" /></a>
</t:fragment>
<t:tpl menuId="__form">
		<div class="title-sub">数据模型：</div>
		<table class="table-view">
   			<tr>
   				<td class="form-feild"><s:text name="Form.name"/></td>
   				<td>${form.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Form.info"/></td>
   				<td>${form.info}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Form.catelog"/></td>
   				<td>${form.catelog}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Form.updateTime"/></td>
   				<td><s:date name="form.updateTime" format="yyyy-MM-dd"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Form.admin"/></td>
   				<td>${form.admin}</td>
   			</tr>
		</table> 
		<div class="title-sub">数据项：</div>
		<div class="button-subs">
			<a onclick="selAll('.chk',this)" class="button"><s:text name="button.selall"/></a>
			<a onclick="f('../formElement/add.action?form=${form.id}')"  class="button"><s:text name="FormElement.add.title"/></a>
			<a onclick="delSelected('.chk','#delete')" class="button"><s:text name="button.delete"/></a>
		</div>
		<s:form name="delForm" action="delete" namespace="/formElement">
			<s:hidden name="form" value="%{#parameters.id[0]}"></s:hidden>
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td><s:text name="FormElement.name"/></td>
				<td><s:text name="FormElement.elementId"/></td>
				<td><s:text name="Form.title"/></td>
				<td><s:text name="FormElement.list"/></td>
				<td><s:text name="FormElement.search"/></td>
				<td><s:text name="FormElement.orderNo"/></td>
				<td><s:text name="FormElement.updateTime"/></td>
				<td><s:text name="FormElement.admin"/></td>
			</tr>
		<s:iterator value="#request.form.formElements">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
				<td><a href="../formElement/edit.action?id=${id}"><s:property value="name" escape="false"/></a></td>
				<td><s:property value="element.name" escape="false"/></td>
				<td>
				<s:if test="form.title==id">是</s:if>
				</td>
				<td><s:property value="list" escape="false"/></td>
				<td><s:property value="search" escape="false"/></td>
				<td><s:property value="orderNo" escape="false"/></td>
				<td><s:date name="updateTime" format="yyyy-MM-dd"/></td>
				<td><s:property value="admin" escape="false"/></td>
			</tr>
		</s:iterator>
		</table>
		</s:form>
</t:tpl>