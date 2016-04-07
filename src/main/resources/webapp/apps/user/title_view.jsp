<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_nav"><s:text name="Title.view.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="f('edit.action?id=${title.id}')"  class="button-main button-icon-edit"><s:text name="button.modify"/></a>
	<a onclick="del()" class="button-main button-icon-del"><s:text name="button.delete" /></a>
</t:fragment>
<t:fragment>
	<script>
	function del(){
		Frm.confirm('确定要删除吗？','提示',function(cfm){
			if(cfm){
				f('delete.action?id=${title.id}');
			}
		});
	}
	</script>
</t:fragment>
<t:tpl menuId="__title">
		<table class="table-form">
   			<tr>
   				<td class="form-feild"><s:text name="Title.id"/></td>
				<td>${title.id}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Title.name"/></td>
				<td>${title.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Title.info"/></td>
				<td>${title.info}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Title.orderNo"/></td>
				<td>${title.orderNo}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Title.userInfos"/></td>
   			</tr>
		</table> 
</t:tpl>
