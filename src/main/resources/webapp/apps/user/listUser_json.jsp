<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ taglib uri="/struts-tags" prefix="s"%>
{list:[
	<s:if test="#request.list.size>0">
		<s:iterator value="#request.list" begin="0" end="0">
			{
			id:"<s:property value="id" />",
			name:"<s:property value="name" escape="false"/>",
			username:"<s:property value="username" escape="false"/>",
			department:"<s:property value="position.department.name" escape="false"/>",
			departmentId:"<s:property value="position.department.id" escape="false"/>",
			position:"<s:property value="position.name" escape="false"/>",
			positionId:"<s:property value="position.id" escape="false"/>",
			type:"user",
			icon:"${pageContext.request.contextPath}/images/user.gif"
			<s:if test="#parameters.t[0].indexOf('u')<0">
			,nocheck:true
			</s:if>
			}
		</s:iterator>
		<s:iterator value="#request.list" begin="1">
			,{
			id:"<s:property value="id" />",
			name:"<s:property value="name" escape="false"/>",
			username:"<s:property value="username" escape="false"/>",
			department:"<s:property value="position.department.name" escape="false"/>",
			departmentId:"<s:property value="position.department.id" escape="false"/>",
			position:"<s:property value="position.name" escape="false"/>",
			positionId:"<s:property value="position.id" escape="false"/>",
			type:"user",
			icon:"${pageContext.request.contextPath}/images/user.gif"
			<s:if test="#parameters.t[0].indexOf('u')<0">
			,nocheck:true
			</s:if>
			}
		</s:iterator>
	</s:if>
],
page:{page:${page},total:<s:property value="#request.list.total"/>,size:${pagesize}}
}
