<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ taglib uri="/struts-tags" prefix="s"%>
[//<s:property value="#request.list"/>
	<s:if test="#request.list.size>0">
		<s:iterator value="#request.list" begin="0" end="0">
			{
			id:"<s:property value="id" />",
			name:"<s:property value="name" escape="false"/>",
			isParent:true,
			parent:null,
			type:"<s:property value="type" escape="false"/>",
			checked:<s:property value="#parameters.selectedId!=null && #parameters.selectedId[0].indexOf(id)>=0"/>,
			<s:if test="type.toString()=='Department'">
			icon:"${pageContext.request.contextPath}/images/dept.gif"
			<s:if test="#parameters.t[0].indexOf('d')<0">
			,nocheck:true
			</s:if>
			</s:if>
			<s:if test="type.toString()=='Organization'">
			icon:"${pageContext.request.contextPath}/images/com.gif"
			<s:if test="#parameters.t[0].indexOf('o')<0">
			,nocheck:true
			</s:if>
			</s:if>
			<s:if test="type.toString()=='BusinessUnit'">
			icon:"${pageContext.request.contextPath}/images/com.gif"
			<s:if test="#parameters.t[0].indexOf('b')<0">
			,nocheck:true
			</s:if>
			</s:if>
			}
		</s:iterator>
		<s:iterator value="#request.list" begin="1">
			,{
			id:"<s:property value="id" />",
			name:"<s:property value="name" escape="false"/>",
			isParent:true,
			parent:null,
			type:"<s:property value="type" escape="false"/>",
			checked:<s:property value="#parameters.selectedId!=null && #parameters.selectedId[0].indexOf(id)>=0"/>,
			<s:if test="type.toString()=='Department'">
			icon:"${pageContext.request.contextPath}/images/dept.gif"
			<s:if test="#parameters.t[0].indexOf('d')<0">
			,nocheck:true
			</s:if>
			</s:if>
			<s:if test="type.toString()=='Organization'">
			icon:"${pageContext.request.contextPath}/images/com.gif"
			<s:if test="#parameters.t[0].indexOf('o')<0">
			,nocheck:true
			</s:if>
			</s:if>
			<s:if test="type.toString()=='BusinessUnit'">
			icon:"${pageContext.request.contextPath}/images/com.gif"
			<s:if test="#parameters.t[0].indexOf('b')<0">
			,nocheck:true
			</s:if>
			</s:if>
			}
		</s:iterator>
	</s:if>
	<s:if test="#request.list.size>0">
		<s:iterator value="#request.positions">
		,{
		id:"<s:property value="id" />",
		name:"<s:property value="name" escape="false"/>",
		isParent:false,
		type:"Position",
		department:null,
		checked:<s:property value="#parameters.selectedId!=null && #parameters.selectedId[0].indexOf(id)>=0"/>,
		icon:"${pageContext.request.contextPath}/images/pos.png"
		<s:if test="#parameters.t[0].indexOf('p')<0">
			,nocheck:true
		</s:if>
		}
		</s:iterator>
	</s:if>
	<s:elseif test="#request.positions.size>0">
		<s:iterator value="#request.positions" begin="0" end="0">
		{
		id:"<s:property value="id" />",
		name:"<s:property value="name" escape="false"/>",
		isParent:false,
		type:"Position",
		department:null,
		checked:<s:property value="#parameters.selectedId!=null && #parameters.selectedId[0].indexOf(id)>=0"/>,
		icon:"${pageContext.request.contextPath}/images/pos.png"
		<s:if test="#parameters.t[0].indexOf('p')<0">
			,nocheck:true
		</s:if>
		}
		</s:iterator>
		<s:iterator value="#request.positions" begin="1">
		,{
		id:"<s:property value="id" />",
		name:"<s:property value="name" escape="false"/>",
		isParent:false,
		type:"Position",
		department:null,
		checked:<s:property value="#parameters.selectedId!=null && #parameters.selectedId[0].indexOf(id)>=0"/>,
		icon:"${pageContext.request.contextPath}/images/pos.png"
		<s:if test="#parameters.t[0].indexOf('p')<0">
			,nocheck:true
		</s:if>
		}
		</s:iterator>
	
	</s:elseif>
]
