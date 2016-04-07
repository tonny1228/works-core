<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Form.add.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#save').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
	<script>
	var validate_rules={
			'form.name' : {required:true,rangelength:[2,37]},
		};
	</script>
</t:fragment>
<t:tpl menuId="__form" param="support=form">
	<s:form action="save">
		<table class="table-form">
   			<tr>
   				<td class="form-feild"><span class="required"><s:text name="Form.name"/></span>
   				</td>
   				<td><s:textfield name="form.name" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><span><s:text name="Form.info"/></span>
   				</td>
   				<td><s:textfield name="form.info" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><span><s:text name="Form.catelog"/></span>
   				</td>
   				<td><s:textfield name="form.catelog" /></td>
   			</tr>
			<tr><td></td><td><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>