<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:tpl name="simple">
	<t:fragment>
	<style>
	.main {
		width: 480;
		height:180;
		text-align: left;
		margin: 100 auto;
		border: 1px solid #44b6dc;
		padding: 0;
		background: #efefef;
	}
	
	
	.msg{margin: 10px;position:relative;}
	</style>
<script type="text/javascript">
<!--
parent.deleteNode('${param.id}');
//-->
</script>
	</t:fragment>
	<div class="main">
		<s:if test="">
		
		</s:if>
		<div style="font-size: 24px;text-align: center">删除成功</div>
	</div>
</t:tpl>

