<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_ctlbnt">
	<a onclick="$('#saveUser').submit()" class="button-main button-icon-save">保存</a>
</t:fragment>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">添加用户</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--
addOnLoad(function() {
    //$("#regtime").datepicker( "option", { disabled: true } );]
	// $("#regtime").datepicker('option','maxDate',new Date());  
	$('form').buttonset();
});

var validate_rules={
		'user.username' : {required:true,rangelength:[4,30]},
		'user.name' : {required:true,rangelength:[1,30]},
		'user.password':{required:true,rangelength:[1,20],equalTo:'#saveUser_confirm'},
		'confirm':{required:true,rangelength:[1,20],equalTo:'#saveUser_user_password'},
		'user.birthday':{date:true},
		'user.telNo':{maxlength:30},
		'user.mobileNo':{maxlength:30},
		'user.email':{email:true},
		'user.zip':{digits:true,maxlength:20},
		'user.address':{maxlength:100}
};

var validate_msg = {
		'user.password' : {equalTo:'确认密码与密码不一致'},
		'confirm' : {equalTo:'确认密码与密码不一致'}
};

function OK(){
	$('#saveUser').submit();
}
//-->
</script>
</t:fragment>
<t:tpl menuId="__user" param="support=form">
	<t:authCheck auth="UserEntityService.create" errorMessage="<div>您没有权限</div>">
		<s:form action="saveUser">
		<s:hidden name="positionId" value="%{#parameters.positionId}"/>
		<table class="table-form column2" title="添加用户">
			<tr>
				<td class="form-feild">用户名</td>
				<td><s:textfield name="user.username"/></td>
				<td class="form-feild">姓名</td>
				<td><s:textfield name="user.name"/></td>
			</tr>
			<tr>
				<td class="form-feild">性别</td>
				<td><s:radio name="user.sex" list="#{'0':'男','1':'女' }" value="0"/></td>
				<td class="form-feild">状态</td>
				<td ><s:radio name="user.status" list="#{'ACTIVE':'激活','INACTIVE':'未激活','LOCKED':'锁定'}" value="'ACTIVE'"/></td>
			</tr>
			<tr>
				<td class="form-feild">密码</td>
				<td><s:password name="user.password"  onchange="$('#saveUser').validate().element($('#saveUser_confirm').get(0))"/></td>
				<td class="form-feild">确认密码</td>
				<td><s:password name="confirm"  onchange="$('#saveUser').validate().element($('#saveUser_user_password').get(0))"/></td>
			</tr>
			
			<tr>
				<td class="form-feild">出生日期</td>
				<td colspan="3">
				<t:datetime name="user.birthday" id="birthday" yearRange="1900:%{new java.util.Date().getYear()+1900}" />
				</td>
			</tr>
			<tr>
				<td class="form-feild">电话</td>
				<td><s:textfield name="user.telNo" /></td>
				<td class="form-feild">手机</td>
				<td><s:textfield name="user.mobileNo" /></td>
			</tr>
			<tr>
				<td class="form-feild">电子邮箱</td>
				<td colspan="3"><s:textfield name="user.email"/></td>
			</tr>
			<tr>
				<td class="form-feild">地址</td>
				<td colspan="3"><s:textfield name="user.address"/></td>
			</tr>
			<tr>
				<td class="form-feild">邮编</td>
				<td><s:textfield name="user.zip"/></td>
				<td class="form-feild">注册日期</td>
				<td>
				<t:datetime name="user.regTime"  id="regtime">
					<s:param name="value">
						<s:date name="%{new java.util.Date()}" format="yyyy-MM-dd"/>
					</s:param>
				</t:datetime>
				
				</td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td colspan="3"><s:textarea name="user.info" value="%{#request.user.info}" rows="4" cols="55"/></td>
			</tr>
		</table>
		</s:form>
	</t:authCheck>
</t:tpl>
