<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment>
		<script type="text/javascript">
	<!--
if(parent!=window) {
	parent.location=window.location.href;
}
$(function(){
	var validator = $("#loginForm").validate({
		submitHandler:function(form){
			form['login'].disabled=true;
			form.submit();
		},
		rules: {           //定义验证规则,其中属性名为表单的name属性
			username: {
				required: true,
				minlength: 2
			},
			password: {
				required: true
			},
			verify: {
				required: true,
				rangelength: [4,4]
			}
		},
		messages: {       //自定义验证消息
			username: {
				required: "请输入用户名！",
				minlength: $.format("用户名至少要{0}个字符！")
			},
			password: {
				required: "请输入密码！"
			},
			verify: {
				required: "请输入验证码！",
				rangelength: $.format("验证码{0}个字符！")  
			}
		},
		errorPlacement:jQueryErrorPlacement,
	    highlight: jQueryHighlight,
	    success: jQuerySuccess
   		
	});
}); 


addOnLoad(function(){
	
		$('#save').validate({   
			rules:{
				'member.username' : {required:true,rangelength:[4,20]},
				'member.password' : {required:true,rangelength:[1,20],equalTo:'#con'}
			},
			errorPlacement:jQueryErrorPlacement,
			success:jQuerySuccess
		});
});
	//-->
	</script>
		<style>
.main {width: 600px;height: 200px;text-align: center;margin: 0 auto;margin-top: 20px;border: 1px solid #44b6dc;}
input[type='text'] ,input[type='password'] {
	width: 100%;height: 25px;padding: 0px;font-size: 16px;padding-left: 5px;padding-top: 4px;padding-right: -5px;
}
table {
	border: 0px;
}
.t{font-size: 36px;margin-bottom: 40px;}
.dv{margin: 3px;height: 60px;}
</style>
</t:fragment>
<t:tpl name="simple" theme="member" param="support=form">
		<div style="height: 50px;background-color: #065FB9;"></div>
		<div style="width: 1002px;margin: auto;margin-top: 60px">
		<div style="float: left;width: 450px;height: 100%;">
			<s:form action="save" namespace="/member" border="0">
				<div class="t">注册</div>
				
				<div class="dv">
					<div>用户名</div>
					<s:textfield name="member.username"/>
				</div>
				<div class="dv">
					<div>密码</div>
					<s:textfield name="member.password"/>
				</div>
				<div class="dv">
					<div>再输入一次密码</div>
					<s:textfield name="confirm" id="con"/>
				</div>
				<div class="dv"><s:submit value="注册"/></div>
			</s:form>
		 	<div class="dv">
				<div>为什么要注册？</div>
				<div></div>
			</div>
		</div>
		<div style="width: 1px;height: 300px;float: left;background-color: #aaaaaa;margin-left: 50px;margin-right: 50px;margin-top: 30px"> </div>
		<div style="float: left;width: 400px;">
			<s:form name="login" action="login" id="loginForm" namespace="/member">
				<div class="t">登录</div>
				<div class="dv">
					<div>用户名</div>
					<s:textfield name="username" value="tonny" />
				</div>
				<div class="dv">
					<div>密码</div>
					<s:textfield name="password" value="1234"  />
				</div>
				<div class="dv">
					<s:hidden name="url" value="%{#parameters.url}"></s:hidden>
					<s:actionerror theme="simple" />
				</div>
				<div class="dv"><s:submit name="login" value="登录"/></div>
				<div class="dv">
					<div>没有账号？立即<a href="register.jsp">注册</a>!</div>
					<div><a href="getPassword.jsp">找回密码</a></div>
				</div>
			</s:form>
		</div>
		<div style="clear: both;border-top: 1px solid #aaaaaa;">
			©2012 <a href="mailto:tonny1228@163.com">Tonny</a>
		</div>
		</div>
</t:tpl>