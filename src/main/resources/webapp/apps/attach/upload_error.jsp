<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/tpl" prefix="t"%>
<script>
	var msg = "${error}";
	var type = msg.substring(0,msg.indexOf(":"));
	var value = msg.substring(msg.indexOf(":")+1);
	if("num"==type){
		alert('最多允许上传'+value+'个文件');
	}
	if("maxsize"==type){
		alert('单个文件允许最大为'+value);
	}
	window.history.back();
</script>
