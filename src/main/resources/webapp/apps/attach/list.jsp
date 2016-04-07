<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment>
<script>
	function OpenFile(id,fileUrl,name)
	{
		$.ajax({
		    url: '/file/ref.action?objectId=${param.objectId}&catalog=${param.catalog}&id='+id,
		    type: 'post',
		    dataType: "text",
		    error: function(e,err){
		        alert(e.message+"/"+err);
		    },
		    success: function(){
		    	opener.SetUrl(encodeURI( fileUrl ).replace( '#', '%23' ),0,0,name ) ;
				close() ;
				opener.focus() ;
		    }
		    
		});
	}	
</script>
</t:fragment>
<t:fragment name="_nav">附件管理</t:fragment>
<t:fragment name="_title">文件列表</t:fragment>
<t:tpl name="simple">
	<s:if test="#parameters.type[0]=='image'">
		<s:iterator value="#request.list">
		<div style="float: left;width: 175px;height: 120px;overflow: hidden;margin: 5px;border: 1px solid #cccccc;text-align: center;">
			<s:a href="javascript:OpenFile('%{id}','%{path}','%{filename}');"><img alt="${filename}" src="${path}" border="0" width="160"></s:a>
			<s:a href="javascript:OpenFile('%{id}','%{path}','%{title}');"><s:property value="title" escape="false"/></s:a>
		</div>
		</s:iterator>
	</s:if>
	<s:else>
		<table class="table-list">
			<tr class="table-list-head">
				<td>文件名</td>
				<td>标题</td>
				<td width="60">大小</td>
				<td width="100">录入时间</td>
				<td width="60">上传人</td>
			</tr>
		<s:iterator value="#request.list">
			<tr class="table-list-data">
				<td><s:a href="javascript:OpenFile('%{id}','%{path}','%{filename}');"><s:property value="filename" escape="false"/></s:a></td>
				<td><s:a href="javascript:OpenFile('%{id}','%{path}','%{title}');"><s:property value="title" escape="false"/></s:a></td>
				<td><s:property value="%{@org.llama.library.utils.Formatter@formatByte(filesize)}" escape="false"/></td>
				<td><s:date name="updateTime" format="yyyy-MM-dd hh:mm"/></td>
				<td><s:property value="admin" escape="false"/></td>
			</tr>
		</s:iterator>
		</table>
	</s:else>
	<div align="center" style="clear: both;"><script type="text/javascript">setpage(${page},<s:property value="#request.list.total"/>,${pagesize});</script></div>
</t:tpl>

