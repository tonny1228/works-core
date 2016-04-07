<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_title">权限列表</t:fragment>
<t:fragment name="_nav">权限管理 数据权限 </t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="dialog.dialog('open');" class="button-main button-icon-add">添加</a>
</t:fragment>
<t:fragment>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.form.js"></script>
<script type="text/javascript">
<!--
var validate_rules={
		'config.module' : {required:true,rangelength:[1,20]},
		'config.entity' : {required:true,rangelength:[1,50],regex:/[a-zA-Z0-9]+/}
};
var dialog;
addOnLoad(function(){
	$('#save').ajaxForm(function (){showTips("<s:text name='tip_save_success'/>",5000);dialog.dialog( "close" );$('#doid').val('');});
	dialog = $('#form').dialog({
		title:'添加数据权限设置',
		autoOpen: false,
		height:400,
		width:670,
		show: {
			effect: "blind",
			duration: 500
		},
		hide: {
			effect: "explode",
			duration: 500
		},
		buttons: {
			 "保存": function(){
				 if($('#doid').val())
				 	$('#save').attr('action','update.action');
				 else
					 $('#save').attr('action','save.action');
				 $('#save').submit();
			 },
			 "取消": function() {
			 	dialog.dialog( "close" );
			 	$('#doid').val('');
			 }
		}
	});
});
var g;
function delConfig(a){
	g=a;
	Frm.confirm('确定要删除吗？','提示',function (r){
		if(r){
			$.ajax({url:"delete.action?",type:'post',data:'id='+g,success:function(t){
				showTips("<s:text name='tip_delete_success'/>",5000);
				$('#tr'+g).remove();
			}});
		}
	});
}

function edit(id,m,e,d,ov,oe,pv,pe,cv,br,or){
	dialog.dialog('open');
	$('#doid').val(id);
	$('#cmodule').val(m);
	$('#centity').val(e);
	$('#cdefaultOwner').val(d);
	$('#cownerView').prop('checked',ov);
	$('#cownerEdit').prop('checked',oe);
	$('#cparentView').prop('checked',pv);
	$('#cparentEdit').prop('checked',pe);
	$('#cchildView').prop('checked',cv);
	$('#cbusinessUnitRoleId').val(br);
	$('#corganizationRoleId').val(or);
}
//-->
</script>

</t:fragment>
<t:tpl menuId="__dataOwnerAuth" param="support=form">
	<s:set name="bol" value="#{true:'是',false:'否' }"/>
	<table class="table-list">
		<tr class="table-list-head">
			<td>模块</td>
			<td>类名</td>
			<td>拥有者</td>
			<td>拥有者读</td>
			<td>拥有者写</td>
			<td>上级读</td>
			<td>上级写</td>
			<td>下级读</td>
			<td>单位管理员角色</td>
			<td>机构管理员角色</td>
			<td>操作</td>
		</tr>
	<s:iterator value="#request.list">
		<tr id="tr${id }" class="table-list-data">
			<td>${module}</td>
			<td>${entity}</td>
			<td>${defaultOwner}</td>
			<td><s:property value="#bol[ownerView]" /> </td>
			<td><s:property value="#bol[ownerEdit]" /> </td>
			<td><s:property value="#bol[parentView]" /> </td>
			<td><s:property value="#bol[parentEdit]" /> </td>
			<td><s:property value="#bol[childView]" /> </td>
			<td>${businessUnitRoleId}</td>
			<td>${organizationRoleId}</td>
			<td><a href="javascript:edit('${id }','${module}','${entity}','${defaultOwner}',${ownerView },${ownerEdit },${parentView },${parentEdit },${childView },'${businessUnitRoleId }','${organizationRoleId }')">编辑</a> <a href="javascript:delConfig('${id }')">删除</a></td>
		</tr>
	</s:iterator>
	</table>
	
	<div id="form">
		<s:form action="save" validate="true">
			<input type="hidden" name="config.id" id="doid"/>
			<table class="table-form">
				<tr>
					<td class="form-feild">模块</td>
					<td><s:textfield id="cmodule" name="config.module"/></td>
				</tr>
				<tr>
					<td class="form-feild">类名</td>
					<td><s:textfield id="centity" name="config.entity"/></td>
				</tr>
				<tr>
					<td class="form-feild">拥有者</td>
					<td><s:select id="cdefaultOwner" name="config.defaultOwner" list="{'当前用户','当前岗位','当前部门','当前单位'}"/> </td>
				</tr>
				<tr>
					<td class="form-feild">单位管理员角色</td>
					<td><s:textfield id="cbusinessUnitRoleId" name="config.businessUnitRoleId"/> </td>
				</tr>
				<tr>
					<td class="form-feild">机构管理员角色</td>
					<td><s:textfield id="corganizationRoleId" name="config.organizationRoleId"/></td>
				</tr>
				<tr>
					<td class="form-feild">权限</td>
					<td>
						<s:checkbox id="cownerView" name="config.ownerView" fieldValue="true" value="true"/><s:label for="cownerView" value="拥有者可读"/>
						<s:checkbox id="cownerEdit" name="config.ownerEdit" fieldValue="true" value="true"/><s:label for="cownerEdit" value="拥有者可写"/>
						<br/>
						<s:checkbox id="cparentView" name="config.parentView" fieldValue="true" value="true"/><s:label for="cparentView" value="上级可读"/>
						<s:checkbox id="cparentEdit" name="config.parentEdit" fieldValue="true" value="true"/><s:label for="cparentEdit" value="上级可写"/>
						<br/>
						<s:checkbox id="cchildView" name="config.childView" fieldValue="true" value="true"/><s:label for="cchildView" value="下级可读"/>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</t:tpl>

