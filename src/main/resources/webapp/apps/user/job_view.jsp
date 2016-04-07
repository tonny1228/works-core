<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="job_include.jsp" %>
<t:fragment>
	<script>
	function del(){
		Frm.confirm('确定要删除当前职务"${job.name}"吗？','提示',function(cfm){
			if(cfm){
				f('deleteJob.action?id=${job.id}');
			}
		});
	}
	</script>
</t:fragment>
<t:fragment name="_title"><s:text name="Job.view.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="f('editJob.action?id=${job.id}')"  class="button-icon-edit button-main" ><s:text name="button.modify"/></a>
	<a onclick="del()"  class="button-main button-icon-del"><s:text name="button.delete" /></a>
</t:fragment>
<t:tpl menuId="__job">
		<table class="table-view">
   			<tr>
   				<td class="form-feild"><s:text name="Job.name"/></td>
					<td>${job.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Job.info"/></td>
					<td>${job.info}</td>
   			</tr>
		</table> 
		
		<div class="title-sub">职级</div>
		<div style="text-align: right;width: 600px;">
		<div class="button-subs">
		<a onclick="f('${pageContext.request.contextPath}/user/addJobLevel.action?jobId=${job.id }')" class="button"><s:text name="button.add"/></a>
		</div>
		<table class="table-list">
			<tr class="table-list-head">
				<td><s:text name="JobLevel.name"/></td>
				<td><s:text name="JobLevel.info"/></td>
				<td><s:text name="JobLevel.level"/></td>
				<td>操作</td>
			</tr>
		<s:iterator value="job.jobLevels">
			<tr class="table-list-data">
					<td><a href="editJobLevel.action?id=${id}">${name}</a></td>
					<td>${info}</td>
					<td>${level}</td>
					<td><a href="deleteJobLevel.action?id=${id}&jobId=${job.id}">删除</a></td>
			</tr>
		</s:iterator>
		</table>
		</div>
</t:tpl>
