<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title"><s:text name="Title.edit.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updateTitle').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
var validate_rules={
	'title.id' : {required:true,rangelength:[1,32]}
	,'title.name' : {required:!false,rangelength:[0,200]}
	,'title.info' : {required:!true,rangelength:[0,500]}
	,'title.orderNo' : {required:!true,number:true}
}
</script>
</t:fragment>
<t:tpl menuId="__title" param="support=form">
	<s:form action="updateTitle">
	<s:hidden name="title.id"/>
		<table class="table-form">
	   		<tr>
	   				<td class="form-feild"><span class="required"><s:text name="Title.name"/></span></td>
	   				<td>
					<s:textfield name="title.name"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="Title.info"/></span></td>
	   				<td>
					<s:textfield name="title.info"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="Title.orderNo"/></span></td>
	   				<td>
					<s:textfield name="title.orderNo"/>
   					</td>
   			</tr>
			<tr><td></td><td><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>
