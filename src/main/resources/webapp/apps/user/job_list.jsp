<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="job_include.jsp" %>
<t:fragment name="_title"><s:text name="Job.list.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall"><s:text name="button.selall"/></a>
	<a onclick="f('${pageContext.request.contextPath}/user/addJob.action')" class="button-main button-icon-add"><s:text name="button.add"/></a>
	<a onclick="delSelected('.chk','#deleteJob')" class="button-main button-icon-del"><s:text name="button.delete"/></a>
</t:fragment>
<t:tpl menuId="__job">
		<s:form name="delForm" action="deleteJob">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td><s:text name="Job.name"/></td>
				<td><s:text name="Job.info"/></td>
				<td><s:text name="Job.orderNo"/></td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
					<td><a href="viewJob.action?id=${id}">${name}</a></td>
					<td>${info}</td>
					<td>${orderNo}</td>
			</tr>
		</s:iterator>
		</table>
		</s:form>
</t:tpl>
