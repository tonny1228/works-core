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
<t:fragment name="_title">系统用户</t:fragment>
<t:fragment name="_ctlbnt">
</t:fragment>

<t:tpl menuId="__sysuser">
		<table class="table-list">
			<tr class="table-list-head">
				<td>用户名</td>
				<td>姓名</td>
				<td>电话</td>
				<td>手机</td>
				<td>邮箱</td>
			</tr>
			<s:iterator id="user" value="#request.list">
			<tr class="table-list-data">
				<td><s:a href="editUser.action?id=%{#user.id}&frame=default">${user.username}</s:a> </td>
				<td>${user.name}</td>
				<td>${user.telNo}</td>
				<td>${user.mobileNo}</td>
				<td id='${user.id}'>${user.email}</td>
			</tr>
			</s:iterator>
		</table>
</t:tpl>