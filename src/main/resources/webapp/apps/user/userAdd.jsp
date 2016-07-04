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
    <%@ include file="user_add_form.jsp"%>
</t:tpl>
