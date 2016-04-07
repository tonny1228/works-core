<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment>
	<script>
	function del(){
		Frm.confirm('确定要删除吗？','提示',function(cfm){
			if(cfm){
				f('delete.action?id=${member.id}');
			}
		});
	}
	</script>
</t:fragment>
<t:fragment name="_title"><s:text name="Member.view.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<s:if test="%{member.status==100}">
	<a onclick="f('lock.action?id=${member.id}')"  class="button-main button-icon-lock" type="button">锁定</a>
	</s:if>
	<s:else>
	<a onclick="f('unlock.action?id=${member.id}')"  class="button-main button-icon-unlock" type="button">解锁</a>
	</s:else>
	<a onclick="del()" type="button" class="button-main button-icon-del"><s:text name="button.delete" /></a>
</t:fragment>
<t:tpl>
		<table class="table-form">
   			<tr>
   				<td class="form-feild"><s:text name="Member.username"/></td>
   				<td>${member.username}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.name"/></td>
   				<td>${member.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.userId"/></td>
   				<td>${member.userId}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.regDate"/></td>
   				<td>
   				<s:date name="member.regTime" format="yyyy-MM-dd"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.tel"/></td>
   				<td>${member.telNo}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.mobile"/></td>
   				<td>${member.mobileNo}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.email"/></td>
   				<td>${member.email}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.gender"/></td>
   				<td>${member.sex}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.info"/></td>
   				<td>${member.info}</td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Member.birth"/></td>
   				<td><s:date name="member.birthday" format="yyyy-MM-dd"/></td>
   			</tr>
		</table> 
</t:tpl>