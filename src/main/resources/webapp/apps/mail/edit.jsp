<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">编辑Mail</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#update').submit()">保存</a>
</t:fragment>
<t:tpl menuId="__mail">
	<s:form action="update" namespace="/mail">
		<s:hidden name="mail.id" />
		<table class="table-form">
			<tr>
   				<td class="form-feild"><span class="required">收件人</span></td>
   				<td><s:textfield name="mail.to" cssStyle="width:99%;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">抄送</td>
   				<td><s:textfield name="mail.cc"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">暗送</td>
   				<td><s:textfield name="mail.bcc"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">主题</td>
   				<td><s:textfield name="mail.title"/></td>
   			</tr>
			<tr>
   				<td class="form-feild">附件</td>
   				<td>
   				<div id="grad">
				</div>
   				<a onclick="o('../file/load.action?objectId=temp_${attach}&catalog=mail&type=add&callback=setgrad',500,400)" class="bnt upload">上传</a></td>
   				</td>
   			</tr>
   			<tr>
   				<td class="form-feild">内容</td>
   				<td><s:textarea name="mail.content"/></td>
   			</tr>
			<tr><td colspan="2"><s:submit value="保存"/></td></tr>
		</table> 
	</s:form>
</t:tpl>