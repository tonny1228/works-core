<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">目录信息</t:fragment>
<t:fragment name="_ctlbnt">
	<s:if test="#request.catalog.treeNode.parentId!=null">
	<a onclick="f('view.action?id=${catalog.treeNode.parentId}')" class="button-main button-icon-back">上级</a>
	</s:if>
	<a onclick="f('list.action')"  class="button-main button-icon-list">列表</a>
	<a onclick="f('auth.action?catalogId=${catalog.id}')" class="button-main button-icon-auth">配置权限</a>
	<a onclick="f('edit.action?id=${catalog.id}')" class="button-main button-icon-edit">修改</a>
	<a onclick="del()" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:fragment>
<script>
	function del(){
		Frm.confirm('确定要删除吗？','提示',function(cfm){
			if(cfm){
				f('delete.action?id=${catalog.id}');
			}
		});
	}
	
	function moveCatalog(){
		var c = getSelOne('chk');
		if(c)
			CatalogTree.open({title:'移动到',width:300,height:400,callback:function(id, name,layer,nameLayer){
				f('move.action?id='+$(c).val()+'&parentId='+id);
			}});
	}
</script>
</t:fragment>
<t:tpl menuId="__catalog" param="support=catalogTree">
		<div class="title-sub">目录信息</div>
		<table class="table-view">
   			<tr>
   				<td class="form-feild">编号</td>
   				<td>${catalog.id}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">名称</td>
   				<td>${catalog.name}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">别名</td>
   				<td>${catalog.alias}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">类型</td>
   				<td>${catalog.type}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">状态</td>
   				<td>${_status[catalog.status]}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">显示</td>
   				<td><s:property value="#_display[catalog.display]"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">备注</td>
   				<td>${catalog.description}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">更新时间</td>
   				<td><s:date name="catalog.updateTime" format="yyyy-MM-dd HH:mm"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">更新人</td>
   				<td>${catalog.admin}</td>
   			</tr>
   			<tr>
   				<td class="form-feild">附件</td>
   				<td><core:attach objectId="${catalog.id }"/> </td>
   			</tr>
		</table> 
		<br/>
		<div class="title-sub">子目录</div>
		<div class="button-subs">	
			<a onclick="selAll('.chk',this)"><s:text name="button.selall"/></a>
			<a onclick="f('add.action?parent=${catalog.id}')" class="ui-button-success">添加子目录</a>
			<a onclick="delSelected('.chk','#delete')" class="ui-button-danger" ><s:text name="button.delete"/></a>
			<a onclick="moveCatalog()" class="ui-button-info" >移动</a>
		</div>
	<s:form name="delForm" action="delete" namespace="/catalog">
		<s:hidden name="catalogId" value="%{catalog.id}"></s:hidden>
		<t:dataTable multiSelect="true" showNo="true" fields="<a href='view.action?id=%{id}'>%{name}</a>|%{alias}|%{type}|%{#_status[status]}|%{#_display[display]}" bundle="#request.list"
		 headerNames="名称|别名|类型|状态|显示" widths="|100|50|50|50" checkboxName="id" checkboxValue="%{id}" defaultColumns="名称|状态|显示"/>
		</s:form>
</t:tpl>