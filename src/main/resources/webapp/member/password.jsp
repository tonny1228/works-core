<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_nav">会员中心</t:fragment><t:fragment name="_title">修改密码</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updatePassword').submit()" class="button-main button-icon-save" type="button">更新密码</a>
</t:fragment>
<t:fragment>
<script>
var validate_rules = {
		'password' : {required:true,rangelength:[1,30]},
		'newpassword' : {required:true,rangelength:[1,30],equalTo:'#confirm'},
		'confirm' : {equalTo:'#newpassword'}
};
var validate_msg = {
		'password' : {required:'请输入现在的密码'},
		'newpassword' : {required:'请输入新的密码'},
		'newpassword' : {equalTo:'确认密码与新密码不一致'},
		'confirm' : {equalTo:'确认密码与新密码不一致'}
};
</script>
</t:fragment>
<t:tpl theme="member" menuId="__password" param="support=form">
	<s:form action="updatePassword" namespace="/member">
		<table class="table-form" title="修改密码">
	   		<tr>
	   				<td class="form-feild">原密码</td>
	   				<td>
					<s:password name="password" id="password" title="请输入原密码"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild">新密码</td>
	   				<td>
					<s:password name="newpassword" id="newpassword" title="请输入新密码" onblur="$('#updatePassword').validate().element($('#confirm').get(0))"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild">确认密码</td>
	   				<td>
					<s:password name="confirm" id="confirm"  title="请再次输入新的密码" onblur="$('#updatePassword').validate().element($('#newpassword').get(0))"/>
   					</td>
   			</tr>
		</table> 
	</s:form>
</t:tpl>