<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<%@ include file="include.jsp" %>
<t:fragment name="_title">新建目录</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#save').submit()" class="button-main button-icon-save">保存</a></t:fragment>
<t:fragment>
<script>
	var validate_rules = {
		'catalog.id' : {required:true,rangelength:[1,37],regex:/[a-zA-Z0-9]+/},
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
	<s:form action="save" method="post" title="">
		<table class="table-form" title="${_title}">
			<s:if test="%{#parameters.parent==null}">
   			<tr>
   				<td class="form-feild">编号</td>
   				<td><s:textfield name="catalog.id" title="请输入编号"/></td>
   			</tr>
   			</s:if>
   			<s:else>
   			<tr>
   				<td class="form-feild">上级目录</td>
   				<td>${parent.name}<s:hidden name="parent" value="%{#request.parent.id}"/></td>
   			</tr>
   			</s:else>
   			<tr>
   				<td class="form-feild">名称</td>
   				<td><s:textfield name="catalog.name" title="请输入目录名称"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">别名</td>
   				<td><s:textfield name="catalog.alias" title="目录别名，为空时与名称一致"  cssStyle="height:40px;"/></td>
   			</tr>
   			<tr>
   				<td class="form-feild">类型</td>
   				<td><s:textfield name="catalog.type" value="0" /></td>
   			</tr>
			<tr>
				<td class="form-feild">显示</td>
				<td id="display"><s:radio name="catalog.display" list="#_display" value="1" />
				</td>
			</tr>
			<tr>
				<td class="form-feild"><span class="form-required">序号</span>
				</td>
				<td><s:textfield id="orderNo" name="catalog.treeNode.orderNo"  />
				</td>
			</tr>
			<tr>
   				<td class="form-feild">备注</td>
   				<td><s:textarea name="catalog.description" cols="55" rows="10"/></td>
   			</tr>
			<tr>
   				<td class="form-feild">附件</td>
   				<td><core:attachUploader catalog="catalog" name="token" title="上传" type="add"/></td>
   			</tr>
			<tr><td></td><td><s:submit value="保存"/></td></tr>
		</table> 
		
	</s:form>
	<div id="help-cantainer">
		目录管理广泛用户树形数据的管理，可用户维护栏目、数据字典、目录等数据。
	</div>
</t:tpl>