<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="job_include.jsp" %>
<t:fragment name="_title"><s:text name="JobLevel.add.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#saveJobLevel').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
	var validate_rules={
		'name' : {required:!false,rangelength:[0,200]}
		,'info' : {required:!true,rangelength:[0,500]}
		,'level' : {required:!true,number:true}
	};
</script>
</t:fragment>
<t:tpl menuId="__job" param="support=form">
	<s:form action="saveJobLevel">
		<s:hidden name="id" value="%{#parameters.jobId[0]}"></s:hidden>
		<table class="table-form">
	   		<tr>
	   				<td class="form-feild"><span class="required"><s:text name="JobLevel.name"/></span></td>
	   				<td>
					<s:textfield name="name"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="JobLevel.info"/></span></td>
	   				<td>
					<s:textfield name="info"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="JobLevel.level"/></span></td>
	   				<td>
					<s:select name="level" list="{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36}"/>
   					</td>
   			</tr>
			<tr><td></td><td ><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>
