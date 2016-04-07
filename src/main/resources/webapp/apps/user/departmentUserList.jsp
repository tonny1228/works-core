<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>

<t:fragment>
<script type="text/javascript">
<!--
function mail(){
	$('#add').submit();
}



function selAll1(it,ele){
	if(ele){
		if($(ele).text()=='全选'){
			$(it).each(function(i,item){
				if(!item.disabled&&item.value)
					item.checked=true;
			});
			$(ele).text('全不选');
		}else{
			$(it).each(function(i,item){
				item.checked=false;
			});
			$(ele).text('全选');
		}
	}else{
		$(it).each(function(i,item){
			if(!item.disabled&&item.value)
				item.checked=true;
		});
	}
}



//-->
</script>
</t:fragment>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">部门用户列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('addUser.action')" class="button-main button-icon-add">添加</a>
	<a onclick="mail()" class="bnt mail">发邮件</a>
</t:fragment>

<t:tpl>
	<s:form action="userList" namespace="/user" method="get">
	<div>
		用户名: <s:textfield name="username" value="%{#parameters.username}"/>
		姓名: <s:textfield name="name" value="%{#parameters.name}"/>
		每页: <s:textfield name="pagesize" value="%{#request.list.pagesize}"/>条
		<s:submit value="查询"/>
	</div>
	</s:form>
	<s:form action="add" namespace="/mail">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>用户名</td>
				<td>姓名</td>
				<td>生日/年龄</td>
				<td>电话</td>
				<td>手机</td>
				<td>邮箱</td>
				<td>性别</td>
			</tr>
			<s:set name="sex" value="#{0:'男',1:'女' }"/>
			<s:iterator id="user" value="#request.list">
			<s:if test="#user.username=='admin' || #user.username=='anonymous'">
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{email}" cssClass="chk" disabled="true"/></td>
				<td>${user.username}</td>
				<td>${user.name}</td>
				<td><s:date name="birthday" format="yyyy-MM-dd"/> / <s:property value="%{(@java.lang.System@currentTimeMillis()-#user.birthday.getTime())/1000/3600/24/365}"/> </td>
				<td>${user.telNo}</td>
				<td>${user.mobileNo}</td>
				<td id='${user.id}'>${user.email}</td>
				<td><s:property value="%{#sex[@java.lang.Integer@valueOf(#user.sex)]}"/></td>
			</tr>
			</s:if>
			<s:else>
			<tr class="table-list-data">
				<td><s:checkbox name="id" fieldValue="%{email}" cssClass="chk"/></td>
				<td><s:a href="editUser.action?id=%{#user.id}&frame=default">${user.username}</s:a> </td>
				<td>${user.name}</td>
				<td>
				<s:if test="birthday!=null">
				<s:date name="birthday" format="yyyy-MM-dd"/> / <s:property value="%{(@java.lang.System@currentTimeMillis()-#user.birthday.getTime())/1000/3600/24/365}"/>
				</s:if>
				</td>
				<td>${user.telNo}</td>
				<td>${user.mobileNo}</td>
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
	</s:form>
		<div align="center"><script type="text/javascript">setpage(${page},<s:property value="#request.list.total"/>,${pagesize});</script></div>
</t:tpl>