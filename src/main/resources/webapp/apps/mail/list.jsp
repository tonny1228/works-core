<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">邮件列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('${pageContext.request.contextPath}/mail/add.action')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.chk','#delete')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__mail">
		<s:form name="delForm" action="delete" namespace="/mail">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>主题</td>
				<td>收件人</td>
				<td>发送日期</td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
				<td><s:a href="view.action?id=%{id}"><s:property value="title" escape="false"/></s:a></td>
				<td><s:property value="to" escape="false"/></td>
				<td><s:date name="sendDate"/></td>
			</tr>
		</s:iterator>
		</table>
		<div align="center"><script type="text/javascript">setpage(${page},<s:property value="#request.list.total"/>,${pagesize});</script></div>
		</s:form>
</t:tpl>

