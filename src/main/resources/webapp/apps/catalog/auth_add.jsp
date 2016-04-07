<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">添加权限</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#updateAuth').submit()" class="button-main button-icon-save">保存</a></t:fragment>
<t:fragment>
<script>
	var validate_rules = {
		'roleId' : {required:true}
	};
	
	addOnLoad(function(){
		$('#privilege').buttonset();
	});
</script>
</t:fragment>
<t:tpl menuId="__catalog" param="support=form">
	<s:form action="updateAuth" method="post">
		<table class="table-form">
   			<tr>
   				<td class="form-feild">目录</td>
   				<td>${catalog.name }<s:hidden name="catalogId" value="%{#request.catalog.id}"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">角色</td>
   				<td><s:select name="roleId" list="%{#request.roles}" listKey="id" listValue="name"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">权限</td>
   				<td id="privilege">
   					<s:checkbox name="catalogAuth.extend" id="extend"/><s:label for="extend" value="继承"/> 
   					<s:checkbox name="catalogAuth.edit" id="edit"/><s:label for="edit" value="编辑"/> 
   					<s:checkbox name="catalogAuth.read" id="read" value="true"/><s:label for="read" value="读取"/> 
   					<s:checkbox name="catalogAuth.remove" id="remove"/><s:label for="remove" value="删除"/> 
   					<s:checkbox name="catalogAuth.verify" id="verify"/><s:label for="verify" value="特殊权限"/> 
   				</td>
   			</tr>
			<tr><td></td><td><s:submit value="保存"/></td></tr>
		</table> 
		
	</s:form>
</t:tpl>

