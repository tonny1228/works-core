<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">子用户列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('addUser.action')" class="button-main button-icon-add">添加</a>
	<!-- 
	<a onclick="mail()" class="button-main button-icon-mail">发邮件</a>
	 -->
</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--
function mail(){
	$('#add').submit();
}

addOnLoad(function(){
	$('#psize').buttonset();
});

//-->
</script>
</t:fragment>

<t:tpl menuId="__buuser">
	<s:form action="userList" namespace="/user/sub" method="get">
	<div class="form-search ui-corner-all">
		用户名: <s:textfield name="username" value="%{#parameters.username}"/>
		姓名: <s:textfield name="name" value="%{#parameters.name}"/>
		每页: <span id='psize'><s:radio name="pagesize" value="%{#request.list.pagesize}" list="{10,20,30,50,100}"/></span> 条
		<s:submit value="查询" cssClass="button-icon-search"/>
	</div>
	</s:form>
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>用户名</td>
				<td>姓名</td>
				<td width="40">性别</td>
				<td width="100">生日</td>
				<td width="80">电话</td>
				<td width="80">手机</td>
				<td width="250">邮箱</td>
			</tr>
			<s:set name="sex" value='#{"0":"男","1":"女" }'/>
			<s:iterator id="user" value="#request.list">
			<tr class="table-list-data">
				<td><s:checkbox name="mail.to" fieldValue="%{email}" cssClass="chk"/></td>
				<td><s:a href="editUser.action?id=%{#user.id}&frame=default">${user.username}</s:a> </td>
				<td>${user.name}</td>
				<td>
				<s:if test="%{#sex!=null}">
				<s:property value="%{#sex[#request.user.sex]}"/>
				</s:if>
				</td>
				<td>
				<s:if test="birthday!=null">
				<s:date name="birthday" format="yyyy-MM-dd"/>
				</s:if>
				</td>
				<td>${user.telNo}</td>
				<td>${user.mobileNo}</td>
				<td id='${user.id}'>${user.email}</td>
				
			</tr>
			</s:iterator>
		</table>
        <t:offsetPage bindData="#request.list" />
</t:tpl>