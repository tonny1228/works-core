<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment>
	<style>
	a{cursor: pointer;}
	input{width: 200px;}
	</style>
	<script>
	var validate_rules = {
			'oldpassword' : {required:true,rangelength:[1,30]},
			'newpassword' : {required:true,rangelength:[1,30],equalTo:'#confirm'},
			'confirm' : {equalTo:'#newpassword'}
	};
	var validate_msg = {
			'oldpassword' : {required:'请输入现在的密码'},
			'newpassword' : {required:'请输入新的密码'},
			'newpassword' : {equalTo:'确认密码与新密码不一致'},
			'confirm' : {equalTo:'确认密码与新密码不一致'}
	};
	
	</script>
	</t:fragment>
<t:fragment name="_title">修改密码</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#updateMyPassword').submit()" class="button-main button-icon-save">更新密码</a></t:fragment>
<t:tpl menuId="__setting" param="support=form">
		<s:form action="updateMyPassword">
		<table class="table-form" title="${_title }">
			<tr>
				<td class="form-feild">现密码</td>
				<td><s:password name="oldpassword" cssClass="ip" title="请输入现在的密码"/></td>
			</tr>
			<tr>
				<td class="form-feild">新密码</td>
				<td><s:password id="newpassword" name="newpassword" cssClass="ip" title="请输入新的密码" onchange="$('#updateMyPassword').validate().element($('#confirm').get(0))"/></td>
			</tr>
			<tr>
				<td class="form-feild">确认密码</td>
				<td><s:password id="confirm" name="confirm" cssClass="ip"  title="请再次输入新的密码" onchange="$('#updateMyPassword').validate().element($('#newpassword').get(0))"/></td>
			</tr>
		</table>
		</s:form>
</t:tpl>
