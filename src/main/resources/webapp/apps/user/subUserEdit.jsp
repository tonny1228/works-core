<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_ctlbnt">
	<a onclick="$('#saveUser').submit()" class="button-main button-icon-save">保存</a>
</t:fragment>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">编辑用户</t:fragment>
<t:fragment>
<script type="text/javascript">
<!--
addOnLoad(function() {
	$('form').buttonset();
});
var validate_rules={
		'user.username' : {required:true,rangelength:[4,30]},
		'user.name' : {required:true,rangelength:[1,30]},
		'user.password':{required:true,rangelength:[1,20],equalTo:'#saveUser_confirm'},
		'user.birthday':{date:true},
		'user.telNo':{maxlength:30},
		'user.mobileNo':{maxlength:30},
		'user.email':{email:true},
		'user.zip':{digits:true,maxlength:20},
		'user.address':{maxlength:100}
};
function OK(){
	$('#saveUser').submit();
}
//-->
</script>
</t:fragment>
<t:tpl menuId="__subuser" param="support=form">
    <%@ include file="user_edit_form.jsp"%>
</t:tpl>
