<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">权限列表</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="selAll('.chk',this)" class="button-main button-icon-selall">全选</a>
	<a onclick="f('${pageContext.request.contextPath}/catalog/addAuth.action?catalogId=${parameters.catalogId[0] }')" class="button-main button-icon-add">添加</a>
	<a onclick="delSelected('.chk','#deleteAuth')" class="button-main button-icon-del">删除</a>
</t:fragment>
<t:fragment>
<style>
<!--

-->
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.form.js"></script>

<script type="text/javascript">
<!--
function updateCatalogAuth(tr,id,catalogId,roleId,posId){
	if($(tr).children('td:eq(0)').children('input:eq(0)').attr('disabled')){
		askForUpdateParent(tr,id,catalogId,roleId,posId);
		return;
	}
	updateThis(tr,id,catalogId,roleId,posId);
}


function updateThis(idx,id,catalogId,roleId,posId){
	$('#id').attr('value',id);
	$('#extend').attr('value',$('#extend'+idx).get(0).checked);
	$('#edit').attr('value',$('#edit'+idx).get(0).checked);
	$('#read').attr('value',$('#read'+idx).get(0).checked);
	$('#remove').attr('value',$('#remove'+idx).get(0).checked);
	$('#verify').attr('value',$('#verify'+idx).get(0).checked);
	$('#catalogId').attr('value',catalogId);
	$('#roleId').attr('value',roleId);
	$('#positionId').attr('value',posId);
	$('#updateAuth').ajaxForm( function() {showTips("<s:text name='tip_save_success'/>",5000);});
	$('#updateAuth').ajaxSubmit();
}

function askForUpdateParent(tr,id,catalogId,roleId,posId){
	if($("#dialogconfirm").length==0)
	{
		$("body").append('<div id="dialogconfirm"></div>');
		$("#dialogconfirm").dialog({
			autoOpen:false,
			title:'提示',
			modal: true,
			width:300,
			resizable:false,
			buttons:{
				'更新父节点设置':function(){
					updateThis(tr,id,catalogId,roleId,posId);
					$(this).dialog('close');
				},
				'为本节点设置':function(){
					updateThis(tr,'','${parameters["catalogId"][0]}',roleId,posId);
					$(this).dialog('close');
				}
			}
		});
	}
	$("#dialogconfirm").html("此设置继承自父节点，修改父节点设置还是为本节点设置？");
	$("#dialogconfirm").dialog('open');	
}
addOnLoad(function(){
	$('.privilege').buttonset();
});
//-->
</script>
</t:fragment>
<t:tpl menuId="__catalog">
		<s:form name="delForm" action="deleteAuth">
		<table class="table-list">
			<tr class="table-list-head">
				<td width="40">选择</td>
				<td>目录</td>
				<td>对象</td>
				<td>权限</td>
				<td>更新人</td>
				<td width="80">更新</td>
			</tr>
		<s:iterator value="#request.list" status="sta">
			<s:if test="role!=null">
			<s:set var="name" value="'角色：'+role.name"/>
			</s:if>
			<s:if test="position!=null">
			<s:set var="name" value="'岗位：'+position.name"/>
			</s:if>
			<tr class="table-list-data">
				<s:if test="%{catalog.id==#request.parameters['catalogId'][0]}">
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk"/>
				</td>
				</s:if>
				<s:else>
				<td><s:checkbox name="id" fieldValue="%{id}" cssClass="chk" disabled="true"/></td>
				</s:else>
				<td>${catalog.name }</td>
				<td>${name }</td>
				<td class="privilege">
					<s:checkbox name="extend" id="extend%{#sta.index }" disabled="%{catalog.id!=#request.parameters['catalogId'][0]}"/><s:label for="extend%{#sta.index }" value="继承"/> 
   					<s:checkbox name="edit" id="edit%{#sta.index }"/><s:label for="edit%{#sta.index }" value="编辑"/> 
   					<s:checkbox name="read" id="read%{#sta.index }"/><s:label for="read%{#sta.index }" value="读取"/> 
   					<s:checkbox name="remove" id="remove%{#sta.index }"/><s:label for="remove%{#sta.index }" value="删除"/> 
   					<s:checkbox name="verify" id="verify%{#sta.index }"/><s:label for="verify%{#sta.index }" value="特殊权限"/> 
				</td>
				<td><a title="更新时间：${updateTime}">${operator}</a></td>
				<td>
				<a onclick="updateCatalogAuth(${sta.index },'${id }','${catalog.id}','${role.id}','${position.id}')" class="button">更新</a>
				</td>
			</tr>
		</s:iterator>
		</table>
		</s:form>
		<s:form  action="updateAuth">
			<input id="id" type="hidden" name="catalogAuth.id"/>
			<input id="catalogId" type="hidden" name="catalogId"/>
			<input type="hidden" name="roleId" id="roleId" />
			<input type="hidden" name="positionId" id="positionId" />
			<input id="extend" type="hidden" name="catalogAuth.extend"/>
			<input id="edit" type="hidden" name="catalogAuth.edit"/>
			<input id="read" type="hidden" name="catalogAuth.read"/>
			<input id="remove" type="hidden" name="catalogAuth.remove"/>
			<input id="verify" type="hidden" name="catalogAuth.verify"/>
		</s:form>
</t:tpl>

