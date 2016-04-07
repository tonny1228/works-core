<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:tpl>
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
	</t:fragment>
	<div class="main">
			<div style="font-size: 24px;text-align: center">成功了</div>
			<div class="msg" style="text-align: center">
				<a onclick="history.back(1)">返回</a>
			</div>
	</div>
</t:tpl>

