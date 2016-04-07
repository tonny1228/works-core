<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updateUserRole').submit()" class="button-main button-icon-save">保存</a>
	<a class="button-main button-icon-back" onclick="f('editUser.action?id=${param.id}')">返回</a>
</t:fragment>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">用户角色</t:fragment>
<t:fragment>
	<SCRIPT type="text/javascript">
	function addRole(){
		var id="";
		$(".al").each(function(i,a){
			if(a.checked){
				if(document.getElementById("o-"+a.id)){
					$("#"+a.id).remove();
					$("#__checkbox_"+a.id).remove();
					$("#l-"+a.id).remove();
					$("#b-"+a.id).remove();
					return;
				}
				$("#list").append($("#"+a.id));					
				$("#list").append($("#__checkbox_"+a.id));					
				$("#list").append($("#l-"+a.id));					
				$("#list").append($("#b-"+a.id));					
			}
		});
	}
	function removeRole(){
		var id="";
		$(".added").each(function(i,a){
			if(a.checked)
				id+=a.value+",";
		});
		if(!id) return;
		id = id.substring(0,id.length-1);
		$.ajax({
       		url:'removeUserRole.action',
       		type:'post',
       		data:"id=<s:text name='%{#parameters.id}'/>&roleId="+id,
       		dataType:'html',
       		success:function(xml){
       			 alert(xml);
       		}
       	});
	}
	</SCRIPT>
</t:fragment>
<t:tpl menuId="__user" param="support=form">
		<s:form action="updateUserRole">
		<s:hidden name="id" value="%{#parameters.id}"/>
		<table class="table-form">
			<tr>
				<td class="form-feild">已配置角色</td>
				<td>
				<div id="list">
				<s:iterator id="r" value="#request.user.roles" status="role">
					<s:if test="#r.id!='everyone'">
					<div>
						<s:checkbox name="roleId" fieldValue="%{#r.id}" label="%{#r.id}" id="o-r-%{#r.id}" value="true" cssClass="rl"/>
						<s:label for="o-r-%{#r.id}" value="%{#r.name}"/>
					</div>
					</s:if>
				</s:iterator>
				</div>
				</td>
			</tr>
		</table>
		</s:form>
		<br/>
		<table class="table-form">
			<tr>
				<td class="form-feild">可配置角色</td>
				<td>
				<div>
					<s:iterator id="r" value="#request.roles" status="role">
						<s:if test="#r.id!='everyone'">
							<s:checkbox name="roleId" fieldValue="%{#r.id}" label="%{#r.id}" id="r-%{#r.id}" cssClass="al"/>
							<s:label id="l-r-%{#r.id}" for="r-%{#r.id}" value="%{#r.name}"/>
							<br id='b-r-<s:text name="%{#r.id}"/>'/>
						</s:if>
					</s:iterator>
				</div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="center">
				<a onclick="addRole()" class="button">添加</a>
				</td>
			</tr>
		</table>
</t:tpl>
