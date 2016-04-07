<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ taglib uri="/struts-tags" prefix="s"%>{
"topics":[
				<s:if test="#request.list.size>0">
				<s:iterator value="#request.list" id="p" begin="0" end="0">
					{
					"id":"<s:property value="id" />",
					"name":"<s:property value="name" escape="false"/>",
					"user":[
							<s:iterator value="#p.users" begin="0" end="0">
								{"id":"","name":""}
							</s:iterator>
							<s:iterator value="#p.users" begin="1">
								,{"id":"","name":""}
							</s:iterator>
						]
					}
				</s:iterator>
				<s:iterator value="#request.list" begin="1">
					,{
					"id":"<s:property value="id" />",
					"name":"<s:property value="name" escape="false"/>",
					"user":[
							<s:iterator value="#p.users" begin="0" end="0">
								{"id":"","name":""}
							</s:iterator>
							<s:iterator value="#p.users" begin="1">
								,{"id":"","name":""}
							</s:iterator>
						]
					}
					}
				</s:iterator>
				</s:if>
	]
}