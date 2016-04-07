<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">部门管理</t:fragment>
	<t:fragment>
		<script>
		var validate_rules = {
				'department.id' : {required:!false,rangelength:[2,50],regex:/[a-zA-Z0-9]+/}
				,'department.name' : {required:!false,rangelength:[2,100]}
				,'department.description' : {required:!true,rangelength:[0,500]}
				,'department.code' : {required:!true,rangelength:[2,20],regex:/[a-zA-Z0-9]+/}
				,'department.treeNode.orderNo' : {required:true,digits:true}
		};
		
		function userTree(t){
			//window.x = new FrameWindow('选择上级部门','tree.action?callback=selected'+t+'Dept',300,400);
			//x.show();
			if(t=='A')
				UserTree.open({title:'选择上级部门',type:'dob',scope:'o',width:350,height:500,callback:selectedADept});
			if(t=='F')
				UserTree.open({title:'选择上级部门',type:'dob',scope:'o',width:350,height:500,callback:selectedFDept});
		}
		
		function selectedADept(dept){
			$('#ADeptId').attr('value',dept[0].id);
		}
		function selectedFDept(dept){
			$('#FDeptId').attr('value',dept[0].id);
		}
		</script>
	</t:fragment>
<t:tpl param="support=form,userTree">
		<s:form action="saveDepartment">
		<table class="table-form">
			<s:if test="#request.parent!=null">
			<tr>
				<td class="form-feild">上级部门</td>
				<td>${parent.name}</td>
			</tr>
			</s:if>
			<tr>
				<td class="form-feild">编号</td>
				<td><s:textfield name="department.id" onchange="$('#forward').val('editDepartment.action?refresh=%{#request.parent.id}&id='+this.value);"/></td>
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
				<td><s:textarea name="department.description"/></td>
			</tr>
			<tr>
				<td class="form-feild">部门属性</td>
				<td><s:checkboxlist name="department.properties" list="#{'A':'行政组织','F':'财务组织'}" onclick="settiny()"/></td>
			</tr>
			<tr>
				<td class="form-feild">行政属性</td>
				<td><s:textfield name="A_deptId" id="ADeptId" onclick="userTree('A')" readonly="true"/></td>
			</tr>
			<tr>
				<td class="form-feild">财务属性</td>
				<td><s:textfield name="F_deptId" id="FDeptId" onclick="userTree('F')" readonly="true"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<s:submit value="保存"/>
				<s:hidden name="parentId" value="%{#request.parent.id}"></s:hidden>
				<s:hidden name="department.type" value="%{#parameters.type}"></s:hidden>
				<s:hidden name="forward" id='forward' value=""></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>