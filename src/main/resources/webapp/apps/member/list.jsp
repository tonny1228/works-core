<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Member.list.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)"  class="button-main button-icon-selall"><s:text name="button.selall"/></a>
	<a onclick="delSelected('.chk','#delete','lock.action')" class="button-main button-icon-lock"><s:text name="button.lock"/></a>
	<a onclick="delSelected('.chk','#delete','unlock.action')" class="button-main button-icon-unlock"><s:text name="button.unlock"/></a>
	<a onclick="delSelected('.chk','#delete')" type="button" class="button-main button-icon-del"><s:text name="button.delete"/></a>
</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--

addOnLoad(function(){
	$('#psize').buttonset();
});

//-->
</script>
</t:fragment>
<t:tpl menuId="__member">

	<s:form action="list" namespace="/member" method="get">
	<div class="form-search ui-corner-all">
		用户名: <s:textfield name="username" value="%{#parameters.username}" size="20"/>
		姓名: <s:textfield name="name" value="%{#parameters.name}" size="10"/>
		邮箱: <s:textfield name="email" value="%{#parameters.email}"/>
		类型: <s:select name="userId" list="#{'member':'普通','vip':'VIP' }" headerValue="全部" headerKey=""/>
		每页: <span id='psize'><s:radio name="pagesize" value="%{#request.list.pagesize}" list="{10,20,30,50,100}"/></span> 条
		<s:submit value="查询" cssClass="button-icons-search"/>
	</div>
	</s:form>
		<s:form name="delForm" action="delete">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td><s:text name="Member.username"/></td>
				<td><s:text name="Member.name"/></td>
				<td><s:text name="Member.userId"/></td>
				<td><s:text name="Member.status"/></td>
				<td><s:text name="Member.gender"/></td>
				<td><s:text name="Member.email"/></td>
				<td><s:text name="Member.score"/></td>
			</tr>
		<s:iterator value="#request.list">
			<s:set name="sta" value="%{status.toString()}"/>
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/></td>
				<td><a href="view.action?id=${id}"><s:property value="username" escape="false"/></a></td>
				<td><s:property value="name" escape="false"/></td>
				<td><s:property value="userId" escape="false"/></td>
				<td>${_status[sta] }</td>
				<td><s:property value="gender" escape="false"/></td>
				<td><s:property value="email" escape="false"/></td>
				<td><s:property value="score" escape="false"/></td>
			</tr>
		</s:iterator>
		</table>
		<t:page bindData="#request.list"/>
		</s:form>
</t:tpl>

