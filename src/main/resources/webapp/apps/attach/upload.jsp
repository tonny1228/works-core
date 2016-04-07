<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-fn" prefix="fn"%>
<t:fragment>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/apps/attach/attach.css"/>
	<script type="text/javascript">
<!--
	var tabs = new Array();
	var id = 0;
	var curr;
	addOnLoad(function (){
		/*
		$('.tabpage').each(function(i,item){
			$(item).hide();
			tabs[i]=$(item);
		});
		var idx ='${parameters.tab[0]}';
		<s:if test="%{#parameters.page!=null}">idx='1';</s:if>
		
		$('.tab a').each(function(i,item){
			if(!idx && i==0){
				curr =  $(item);
			}
			if(idx && $(item).attr('class')=='current'){
				$(item).attr('class','');
			}
			if(idx && ($(item).attr('onclick')+'').indexOf('show('+idx)>=0){
				$(item).attr('class','current');
				curr = $(item);
			}
			
		});
		show(0,$('.tabheader a:eq(0)'));
		show(idx?parseInt(idx):0,$('.tabheader a:eq('+(idx?parseInt(idx):0)+')'));
		*/
		$( "#tabs" ).tabs();
		$(".fileimg").each(function(i,img){
			var file = eval("({"+img.title+"})");
			if(file.mime && file.mime.indexOf('image')==0){
				$(img).html('<img src="${pageContext.request.contextPath}/filedown/'+file.path+'?id='+file.id+'&w=120" class="image" title="'+file.filename+'['+file.title+']" onclick="selectFile(\''+file.id+'\',\''+file.filename+'\',\''+file.path+'\')"/>');
			}else	
				$(img).html('<img src="${pageContext.request.contextPath}/images/invis.gif" class="image '+file.fileext+'" title="'+file.filename+'['+file.title+']" onclick="selectFile(\''+file.id+'\',\''+file.filename+'\',\''+file.path+'\')"/>');
		});
	});
		
		
	function OK(){
		$('#dispatchUpload').submit();
	}
	
	function show(idx,item){
		tabs[id].hide();
		tabs[idx].show();
		curr.attr('class','');
		item.attr('class','current');
		curr = item;
		id=idx;
	}
	
	function selectFile(id,filename,path){
		window.location="ref.action?id="+id+"&catalog=${parameters.catalog[0]}&objectId=${parameters.objectId[0]}&type=${param.type}&callback=${parameters.callback[0]}&num=${param.num}";
		return;
		
		<s:if test="#parameters.callback!=null">
		var x= (opener!=null?opener:parent);
		x.${parameters.callback[0]}([{id:id,filename:filename,path:path}]);
		window.close();
		</s:if>
		<s:else>
		parent.OnUploadCompleted([{id:id,filename:filename,path:path}]);
		</s:else>
	}
	
	function check(file){
		var exts = ',${parameters.fileext[0]}';
		if(exts.length<2){
			enableButton('sbnt');
			return;
		}
		var ext = file.substring(file.lastIndexOf('.')+1).toLowerCase();
		if(!ext || exts.indexOf(','+ext)<0){
			Frm.alert('不允许上传的文件格式');
			disableButton('sbnt');
			return false;
		}
		enableButton('sbnt');
	}
//-->
</script>

</t:fragment>
<t:tpl name="simple" param="support=form">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-upload">上传</a></li>
			<li><a href="#tabs-list">已上传</a></li>
			<!-- li><a href="#tabs-browse">浏览文件</a></li -->
		</ul>
		<div id="tabs-upload" >
			<s:if test="#session.logined_user!=null">
			<s:form action="dispatchUpload" method="post" namespace="/file" enctype="multipart/form-data">
				<input type="file" name="file" onchange="check(this.value)" multiple="multiple"/>
				<s:hidden name="catalog" value="%{#parameters.catalog}"></s:hidden>
				<s:hidden name="objectId" value="%{#parameters.objectId}"></s:hidden>
				<s:hidden name="type" value="%{#parameters.type}"></s:hidden>
				<s:hidden name="callback" value="%{#parameters.callback}"></s:hidden>
				<s:hidden name="num" value="0%{#parameters.num}"></s:hidden>
				<s:hidden name="singleMaxSize" value="0%{#parameters.singleMaxSize}"></s:hidden>
				<s:submit id="sbnt" value="上传" disabled="true"/>
			</s:form>
			</s:if>
			<s:if test="#session.logined_member!=null">
			<s:form action="dispatchUpload" method="post" namespace="/mfile" enctype="multipart/form-data">
				<input type="file" name="file" onchange="check(this.value)" multiple="multiple"/>
				<s:hidden name="catalog" value="%{#parameters.catalog}"></s:hidden>
				<s:hidden name="objectId" value="%{#parameters.objectId}"></s:hidden>
				<s:hidden name="type" value="%{#parameters.type}"></s:hidden>
				<s:hidden name="callback" value="%{#parameters.callback}"></s:hidden>
				<s:hidden name="num" value="0%{#parameters.num}"></s:hidden>
				<s:hidden name="singleMaxSize" value="0%{#parameters.singleMaxSize}"></s:hidden>
				<s:submit id="sbnt" value="上传" disabled="true"/>
			</s:form>
			</s:if>
			<ul style="line-height: 30px">
				<s:if test="%{#parameters.fileext!=null && #parameters.fileext[0].length>0}">
				<li>允许上传的文件格式为:${parameters.fileext[0]}</li>
				</s:if>
				<li>允许上传的单个文件大小，建议20M左右，文件过大或网速不稳定时,请通过ftp等方式上传</li>
			</ul>
		</div>
		<div id="tabs-list">
			<s:iterator value="%{#request.list}">
				<div class="filediv">
					<div class="fileimg" title="id:'<s:property value="id"/>',filename:'<s:property value="filename"/>',path:'<s:property value="path"/>',mime:'<s:property value="mimetype"/>',title:'<s:property value="title"/>',filesize:'${fn:formatByte(filesize) }',fileext:'<s:property value="fileext"/>'">
					</div>
					<div class="filename"><a onclick="selectFile('${id}','${filename }','/filedown/${path}')" style="cursor: pointer;">${filename }</a></div>
				</div>
			</s:iterator>
			<t:page bindData="#request.list"/> 
		</div>
		<!-- div id="tabs-browse">
			<s:if test="#request.parent!=null&&#request.parent!=''">
				<div class="folder"><a href="load.action?tab=2&dir=${parent }&objectId=${parameters.objectId[0]}&catalog=${parameters.catalog[0]}&type=${parameters.type[0]}&callback=${parameters.callback[0]}i">..</a></div>
			</s:if>
			<s:if test="#request.parent!=null&&#request.parent==''">
				<div class="folder"><a href="load.action?tab=2&objectId=${parameters.objectId[0]}&catalog=${parameters.catalog[0]}&type=${parameters.type[0]}&callback=${parameters.callback[0]}">..</a></div>
			</s:if>
			<s:iterator var="f" value="%{#request.files}">
				<s:if test="%{#f.isFile()}">
				<div><a href="specailFile.action?path=${parameters.dir[0]}/${name}&${query}">${name}</a></div>
				</s:if>
				<s:else>
				<div class="folder"><a href="load.action?tab=2&dir=${parameters.dir[0]}/${name}&${query}">${name}</a></div>
				</s:else>
			</s:iterator>
		</div -->
	</div>
</t:tpl>