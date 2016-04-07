<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">用户列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
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

<t:tpl menuId="__user">
	<s:form action="userList" namespace="/user" method="get">
	<div class="form-search ui-corner-all">
		用户名: <s:textfield name="username" value="%{#parameters.username}"/>
		姓名: <s:textfield name="name" value="%{#parameters.name}"/>
		每页: <span id='psize'><s:radio name="pagesize" value="%{#request.list.pagesize}" list="{10,20,30,50,100}"/></span> 条
		<s:submit value="查询" cssClass="search"/>
	</div>
	</s:form>
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>用户名</td>
				<td>姓名</td>
				<td>邮箱</td>
				<td>性别</td>
			</tr>
			<s:set name="sex" value="#{0:'男',1:'女' }"/>
			<s:iterator id="user" value="#request.list">
			<s:if test="#user.username=='admin' || #user.username=='anonymous'|| #user.username=='vip'|| #user.username=='member'">
			<tr class="table-list-data">
				<s:if test="#parameters.select!=null && #parameters.select[0]=='radio'">
				<td><s:radio name="mail.to" list="%{email}" cssClass="chk" disabled="true"/></td>
				</s:if>
				<s:else>
				<td><s:checkbox name="mail.to" fieldValue="%{email}" cssClass="chk" disabled="true"/></td>
				</s:else>
				<td><s:a href="editUser.action?id=%{#user.id}&frame=default">${user.username}</s:a> </td>
				<td>${user.name}</td>
				<td id='${user.id}'>${user.email}</td>
				<td><s:property value="%{#sex[@java.lang.Integer@valueOf(#user.sex)]}"/></td>
			</tr>
			</s:if>
			<s:else>
			<tr class="table-list-data">
				<td><s:checkbox name="mail.to" fieldValue="%{email}" cssClass="chk"/></td>
				<td><s:a href="editUser.action?id=%{#user.id}&frame=default">${user.username}</s:a> </td>
				<td>${user.name}</td>
				<td id='${user.id}'>${user.email}</td>
				<td>
				<s:if test="sex!=null">
				<s:property value="%{#sex[@java.lang.Integer@valueOf(#user.sex)]}"/>
				</s:if>
				</td>
			</tr>
			</s:else>
			</s:iterator>
		</table>
		<t:page bindData="#request.list" />
</t:tpl>