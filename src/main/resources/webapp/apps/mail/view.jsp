<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">查看邮件</t:fragment>
<t:fragment>
	<script>
	function del(){
		Frm.confirm('确定要删除吗？','提示',function(cfm){
			if(cfm){
				f('delete.action?id=${mail.id}');
			}
		});
	}
	</script>
</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="del()" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:tpl menuId="__mail">
	<s:form action="update" namespace="/mail">
		<table class="table-view">
   			<tr>
   				<td class="form-feild">编号</td>
   				<td>${mail.id}</td>
   			</tr>
			<tr>
   				<td class="form-feild">收件人</td>
   				<td>${mail.to}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">抄送</td>
   				<td>${mail.cc}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">暗送</td>
   				<td>${mail.bcc}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">标题</td>
   				<td>${mail.title}</td>
   			</tr>
			<tr>
   				<td class="form-feild">发送时间</td>
   				<td>${mail.sendDate}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">更新时间</td>
   				<td>${mail.updateDate}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">更新人</td>
   				<td>${mail.updateUser}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">内容</td>
   				<td>${mail.content}</td>
   			</tr>
   			
   			
		</table> 
	</s:form>
</t:tpl>