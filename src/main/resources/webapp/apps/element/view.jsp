<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Element.view.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="f('edit.action?id=${element.id}')"  class="button-main button-icon-edit"><s:text name="button.modify"/></a>
	<a onclick="del()" class="button-main button-icon-del"><s:text name="button.delete" /></a>
</t:fragment>
<t:fragment>
	<script>
	function del(){
		Frm.confirm('确定要删除吗？','提示',function(cfm){
			if(cfm){
				f('delete.action?id=${element.id}');
			}
		});
	}
	</script>
</t:fragment>
<t:tpl menuId="__element">
		<table class="table-view">
   			<tr>
   				<td class="form-feild"><s:text name="Element.name"/></td>
   				<td>${element.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.dataType"/></td>
   				<td>${element.dataType}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.viewType"/></td>
   				<td>${element.viewType}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.options"/></td>
   				<td><s:property value="element.options.replaceAll('\\n','<br/>')" escapeHtml="false"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.defaultValue"/></td>
   				<td>${element.defaultValue}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.required"/></td>
   				<td>${bool[element.required]}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.minLength"/></td>
   				<td>${element.minLength}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.maxLength"/></td>
   				<td>${element.maxLength}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.regex"/></td>
   				<td>${element.regex}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.catelog"/></td>
   				<td>${element.catelog}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.updateTime"/></td>
   				<td><s:date name="element.updateTime" format="yyyy-MM-dd hh:mm"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.admin"/></td>
   				<td>${element.admin}</td>
   			</tr>
		</table> 
</t:tpl>