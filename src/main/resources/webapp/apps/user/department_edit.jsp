<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">部门管理</t:fragment>
<t:fragment name="_ctlbnt">
	<s:if test="#request.department">
		<s:if test="#request.department.type.toString()=='BusinessUnit'">
		<a onclick="f('addDepartment.action?id=${department.id}&type=BusinessUnit')" class="button-main button-icon-add">子管理单元</a>
		</s:if>
		<a onclick="f('addDepartment.action?id=${department.id}&type=Department')" class="button-main button-icon-add">子部门</a>
		<a onclick="f('addPosition.action?deptId=${department.id}')" class="button-main button-icon-add">岗位</a>
		<a class="button-main button-icon-del" onclick="del('deleteDepartment.action?id=${department.id}&forward=editDepartment.action%3frefresh%3d${department.parentDept.id}%26id%3d${department.parentDept.id}')">删除</a>
		<a onclick="moveDepartment()" class="button-main button-icon-move">移动</a>
	</s:if>
</t:fragment>
<t:fragment>
	<script>
	
	var validate_rules = {
		'department.id' : {required:!false,rangelength:[2,50]},
		'department.name' : {required:!false,rangelength:[2,100]}
		,'department.description' : {required:!true,rangelength:[0,500]}
		,'department.code' : {required:!true,rangelength:[2,20]}
		,'department.treeNode.orderNo' : {required:true,digits:true}
	};
	
	addOnLoad(function (){
		if('${param.refresh}')
			parent.refreshNodeQuietly('${param.refresh}');
		else
			parent.refreshNodeQuietly('${department.parentDept.id}');
	});

	function userTree(t){
		//window.x = new FrameWindow('选择上级部门','tree.action?callback=selected'+t+'Dept',300,400);
		//x.show();
		if(t=='A')
			UserTree.open({title:'选择上级部门',type:'dob',scope:'o',width:350,height:500,callback:selectedADept});
		if(t=='F')
			UserTree.open({title:'选择上级部门',type:'dob',scope:'o',width:350,height:500,callback:selectedFDept});
	}
	
	function selectedADept(d){
		$('#ADeptId').attr('value',d[0].id);
	}
	function selectedFDept(d){
		$('#FDeptId').attr('value',d[0].id);
	}
	
	function moveDepartment(){
		var type;
		if('${department.type}'=='BusinessUnit'){
			type ='bo';
		}else if('${department.type}'=='Department'){
			type = 'bdo';
		}else{
			type = 'o';
		}
		UserTree.open({title:'移动到',type:type,scope:'o',width:350,height:500,callback:function(t){
			Frm.confirm('您将移动此部门到'+(t[0].name)+'?','',function(cfm){
				if(cfm){
					f('moveDepartment.action?id=${department.id}&forward=editDepartment.action%3fid%3d${department.id}&parentId='+t[0].id);
				}
			});
		}});
	}
	</script>
</t:fragment>
<t:tpl param="support=form,userTree">
		<s:form action="saveDepartment">
		<s:hidden name="forward" id='forward' value="editDepartment.action?id=%{#request.department.id}&refresh=%{#request.department.parentDept.id}"></s:hidden>
		<table class="table-form">
			<tr>
				<td class="form-feild">编号</td>
				<td><s:textfield name="department.id" readonly="true"/></td>
			</tr>
			<tr>
				<td class="form-feild">上级部门</td>
				<td>${department.parentDept.name}</td>
			</tr>
			<tr>
				<td class="form-feild">部门负责人</td>
				<td>${department.commander.name }</td>
			</tr>
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="department.name"/></td>
			</tr>
			<tr>
				<td class="form-feild">助记码</td>
				<td><s:textfield name="department.code"/></td>
			</tr>
			<tr>
				<td class="form-feild">排序号</td>
				<td><s:textfield name="department.treeNode.orderNo"/></td>
			</tr>
			<tr>
				<td class="form-feild">备注</td>
				<td><s:textarea name="department.description" /></td>
			</tr>
			<tr>
				<td class="form-feild">部门属性 </td>
				<td><s:checkboxlist name="department.properties" list='#{"A":"行政组织","F":"财务组织"}' value="%{department.properties==null?'': department.properties.split(', ')}" onclick="settiny()"/></td>
			</tr>
			<tr>
				<td class="form-feild">行政属性</td>
				<td><s:textfield name="A_deptId" id="ADeptId" onclick="userTree('A')" value="%{department.bu['A'].parent.id}" readonly="true"/></td>
			</tr>
			<tr>
				<td class="form-feild">财务属性</td>
				<td><s:textfield name="F_deptId" id="FDeptId" onclick="userTree('F')" value="%{department.bu['F'].parent.id}"  readonly="true"/></td>
			</tr>
			<tr>
				<td></td>
				<td><s:submit value="保存"/></td>
			</tr>
		</table>
		</s:form>
</t:tpl>