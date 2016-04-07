<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="job_include.jsp" %>
<t:fragment name="_title"><s:text name="Job.add.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#saveJob').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
	var validate_rules = {
		'job.id' : {required:false,rangelength:[0,0]}
		,'job.name' : {required:!false,rangelength:[0,200]}
		,'job.info' : {required:!true,rangelength:[0,500]}
		,'job.orderNo' : {required:!true,number:true}
	};
</script>
</t:fragment>
<t:tpl menuId="__job" param="support=form">
	<s:form action="saveJob">
		<table class="table-form">
	   		<tr>
	   				<td class="form-feild"><span class="required"><s:text name="Job.name"/></span></td>
	   				<td>
					<s:textfield name="job.name"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="Job.info"/></span></td>
	   				<td>
					<s:textfield name="job.info"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><span><s:text name="Job.orderNo"/></span></td>
	   				<td>
					<s:textfield name="job.orderNo" value="0"/>
   					</td>
   			</tr>
			<tr><td></td><td><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>
