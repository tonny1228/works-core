<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_nav"> 表单管理</t:fragment><t:fragment name="_title"><s:text name="FormElement.add.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#save').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
var validate_rules={
		'elementId' : {required:true,rangelength:[1,50]},
		'form' : {required:true,rangelength:[1,50]},
		'formElement.name' : {required:true,rangelength:[1,50]},
		'formElement.orderNo' : {required:true,rangelength:[1,50]}
	};
</script>
</t:fragment>
<t:tpl menuId="__form" param="support=form">
	<s:form action="save">
		<table class="table-form">
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.formId"/>
   				</td>
   				<td>${form.name} <s:hidden name="form" value="%{#request.form.id}"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.name"/>
   				</td>
   				<td><s:textfield name="formElement.name" /></td>
   			</tr>
			<tr>
   				<td class="form-feild"><s:text name="FormElement.elementId"/>
   				</td>
   				<td><s:select name="elementId" list="#request.elements" listKey="id" listValue="name" headerKey="" headerValue="请选择" onchange="this.form.save_formElement_name.value=this.options[this.selectedIndex].text"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.foreignForm"/>
   				</td>
   				<td><s:textfield name="formElement.foreignForm" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.foreignKey"/>
   				</td>
   				<td><s:textfield name="formElement.foreignKey" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Form.title"/>
   				</td>
   				<td><s:radio name="titled" list="#{0:'正常',1:'标题' }" value="0"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.list"/>
   				</td>
   				<td><s:radio name="formElement.list" list="#{0:'不显示',1:'显示' }" value="0"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.search"/>
   				</td>
   				<td><s:radio name="formElement.search" list="#{0:'不检索',1:'检索' }" value="0"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="FormElement.orderNo"/>
   				</td>
   				<td><s:textfield name="formElement.orderNo" value="1"/></td>
   			</tr>
			<tr><td></td><td><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>