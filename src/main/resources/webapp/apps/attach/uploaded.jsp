<%@page import="works.tonny.apps.core.Attachment"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<script>
		var attachs=[];
		<s:iterator value="#request.attach" var="attachment" status="sta">
		attachs[attachs.length]={id:'${attachment.id }',filename:'${attachment.filename}',path:'/filedown/${attachment.path}?id=${attachment.id }',size:0${attachment.filesize },mimetype:'${attachment.mimetype }',fileext:'${attachment.fileext }',ref:'${ref[sta.index]}'};
		</s:iterator>
		<s:if test="%{#parameters.callback!=null}">
		var x= (opener!=null?opener:parent);x.${param.callback }(attachs);
		window.close();
		</s:if>
		<s:else>
		parent.OnUploadCompleted(attachs);
		</s:else>
		</script>
	</head>
</html>