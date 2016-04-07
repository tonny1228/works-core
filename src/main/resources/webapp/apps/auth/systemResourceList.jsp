<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="resource_include.jsp" %>
<t:fragment name="_title">资源管理</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.cb',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('addSystemResource.action')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.cb','#deleteSystemResource')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__auth_resource">
	<s:form action="deleteSystemResource">
		<table align="center" class="table-list">
			<tr class="table-list-head">
				<td width="30">选择</td>
				<td>名称</td>
				<td>接口</td>
				<td>类型</td>
				<td>描述</td>
			</tr>
			<s:iterator value="#request.list">
				<tr class="table-list-data">
					<td><s:checkbox name="id" fieldValue="%{id}" cssClass="cb" />
					</td>
					<td><s:a href="editSystemResource.action?id=%{id}">
							<s:property value="name" escape="false" />
						</s:a>
					</td>
					<td>${apis[api]}
					</td>
					<td>${types[type] }
					</td>
					<td><s:property value="description" escape="false" />
					</td>
				</tr>
			</s:iterator>
		</table>
	</s:form>
</t:tpl>
