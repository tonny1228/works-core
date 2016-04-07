<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">编辑目录</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#update').submit()" class="button-main button-icon-save">保存</a></t:fragment>
<t:fragment>
<script>
var validate_rules= {
		'catalog.id' : {required:true,rangelength:[1,37]},
		'catalog.name' : {required:true,rangelength:[1,20]},
		'catalog.orderNo' : {required:true,digits:true},
		'catalog.type' : {digits:true}
	};
	
addOnLoad(function(){
	$('#display').buttonset();
});
</script>
</t:fragment>
<t:tpl menuId="__catalog" param="support=form">
	<s:form action="update" method="post">
		<table class="table-form" title="编辑目录信息">
   			<tr>
   				<td class="form-feild">名称</td>
   				<td><s:textfield name="catalog.name"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">别名</td>
   				<td><s:textfield name="catalog.alias"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">类型</td>
   				<td><s:textfield name="catalog.type"/></td>
   			</tr>
			<tr>
				<td class="form-feild">显示</td>
				<td id="display"><s:radio name="catalog.display" list="#_display"/>
				</td>
			</tr>
			<tr>
				<td class="form-feild">序号</td>
				<td><s:textfield name="catalog.treeNode.orderNo"   id="orderNo"/>
				</td>
			</tr>
			<tr>
   				<td class="form-feild">备注</td>
   				<td><s:textarea name="catalog.description" cols="55" rows="10"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">附件</td>
   				<td>
   				<core:attach objectId="${catalog.id }" manage="true"/>
   				<core:attachUploader catalog="catalog" name="token" title="上传" type="add" objectId="${catalog.id }"/>
   				</td>
   			</tr>
			<tr><td></td><td><s:submit value="保存"/></td></tr>
		</table> 
		<s:hidden name="catalog.id" value="%{#request.catalog.id}"/>
	</s:form>
</t:tpl>