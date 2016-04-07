<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">新建邮件</t:fragment>
<t:fragment>
<script>

	function getEditorHTMLContents(EditorName)
	{ 
	    var oEditor = FCKeditorAPI.GetInstance(EditorName); 
	    if(oEditor.EditorDocument == null || oEditor.EditorDocument == undefined){
	    	Frm.alert('请关闭查询源代码状态,否则不能保存内容！');
	    	return false;
	    }
	    return(oEditor.GetXHTML(true)); 
	}
	
	 //获取编辑器中文字内容（在博客发布时获取无html代码摘要使用）
	function getEditorTextContents(EditorName)
	{ 
	    var oEditor = FCKeditorAPI.GetInstance(EditorName); 
	    if(oEditor.EditorDocument == null || oEditor.EditorDocument == undefined){
	    	Frm.alert('请关闭查询源代码状态,否则不能保存内容！');
	    	return false;
	    }
	    return(oEditor.EditorDocument.body.innerText);  
	}
	//设置编辑器中内容
	function SetEditorContents(EditorName, ContentStr)
	{ 
	    var oEditor = FCKeditorAPI.GetInstance(EditorName) ; 
	    oEditor.SetHTML(ContentStr) ; 
	}
	
	function initFck(){
		var editor = CKEDITOR.replace( 'EditorDefault', {
			extraPlugins : 'autogrow',
			autoGrow_maxHeight : 500,
			fullPage : true,
		});
	}
	
	addOnLoad(initFck);
	/**/
	var validate_rules={
					'mail.to' : {required:true,rangelength:[10,1500]},
					'mail.title' : {required:true,rangelength:[1,80]}
	}
	
	function set(id,name,url,type){
		var photo=document.createElement('a');
		photo.href=url;
		photo.title=name;
		photo.appendChild(document.createTextNode(name));
		document.getElementById(type).appendChild(photo);
	}
	function setgrad(id,name,url){
		//set(id,name,url,'grad');
	}
	function o(u,w,h){
		window.x = new FrameWindow('附件上传',u,w,h);
		window.x.show();
	}
	</script>
</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#save').submit()" class="button-main button-icon-save">发送</a></t:fragment>
<t:tpl menuId="__mail" param="support=ckeditor">
	<s:form action="save">
		<s:token name="attach"></s:token>
		<s:hidden id="attach_object_id" name="token" value="temp_%{attach}"></s:hidden>
		<table class="table-form" width="100%">
   			<tr>
   				<td class="form-feild">收件人</td>
   				<td><s:textfield name="mail.to" cssStyle="width:99%;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">抄送</td>
   				<td><s:textfield name="mail.cc" cssStyle="width:99%;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">暗送</td>
   				<td><s:textfield name="mail.bcc" cssStyle="width:99%;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">标题</td>
   				<td><s:textfield name="mail.title"  cssStyle="width:99%;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">附件</td>
   				<td>
   				<div id="grad">
				</div>
   				<a onclick="o('../file/load.action?objectId=temp_${attach}&catalog=mail&type=add&callback=setgrad',500,400)" class="bnt upload">上传</a></td>
   				</td>
   			</tr>
   			<tr>
   				<td class="form-feild">内容</td>
   				<td><s:textarea id="EditorDefault" name="mail.content" cssStyle="width:99%;height:400px;"/></td>
   			</tr>
			<tr><td colspan="2" align="right"><s:submit value="发送" cssclass="save" cssStyle="width:60px;"/></td></tr>
		</table> 
	</s:form>
</t:tpl>