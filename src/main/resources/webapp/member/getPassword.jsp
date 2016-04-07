<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment>
		<script type="text/javascript">
	<!--
	addOnLoad(function(){
		
			$('#sendUrl').validate({   
				rules:{
					'email' : {required:true,rangelength:[4,20],email:true}
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
	width: 200px;height: 25px;padding: 0px;font-size: 16px;padding-left: 5px;padding-top: 4px;padding-right: -5px;
}
table {
	border: 0px;
}
.t{font-size: 36px;margin-bottom: 40px;}
.dv{margin: 3px;height: 60px;}
</style>
</t:fragment>
<t:tpl name="blank">
		<div style="height: 50px;background-color: #065FB9;"></div>
		<div style="width: 1002px;margin: auto;margin-top: 60px">
		<s:form action="sendUrl" namespace="/member">
			<div class="t">请输入注册时邮箱：</div>
			<div class="dv">
			<s:textfield name="email"></s:textfield>
			<div class="tip">填写正确的注册邮箱，发送密码到邮箱</div>
			</div>
			<div class="dv"><s:submit value="确认"></s:submit></div>
			
		</s:form>
		<div style="clear: both;border-top: 1px solid #aaaaaa;">
			©2012 <a href="mailto:tonny1228@163.com">Tonny</a>
		</div>
		</div>
</t:tpl>