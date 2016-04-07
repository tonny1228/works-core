<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">目录列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('add.action')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.chk','#delete')" class="button-main button-icon-del">删除</a>
	<a onclick="moveCatalog()" class="button-main button-icon-move">移动</a>
</t:fragment>

<t:fragment>
<script type="text/javascript">
<!--
function moveCatalog(){
	var c = getSelOne('chk');
	if(c)
		CatalogTree.open({title:'移动到',width:300,height:400,callback:function(id, name,layer,nameLayer){
			Frm.confirm('您将移动此目录到'+(name?name:'根目录')+'?','',function(cfm){
				if(cfm){
					f('move.action?id='+$(c).val()+'&parentId='+id);
				}
			});
		}});
}
//-->
</script>
</t:fragment>
<t:tpl menuId="__catalog" param="support=catalogTree">
		<s:form name="delForm" action="delete">
		<t:dataTable multiSelect="true" showNo="true" fields="<a href='view.action?id=%{id}'>%{name}</a>|%{alias}|%{type}|%{#_status[status]}|%{#_display[display]}" bundle="#request.list"
		 headerNames="名称|别名|类型|状态|显示" widths="|100|50|50|50" checkboxName="id" checkboxValue="%{id}" defaultColumns="名称|状态|显示"/>
		 
		</s:form>
</t:tpl>

