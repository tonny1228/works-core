<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">权限配置</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#updateUserPrivilege').submit()" class="button-main button-icon-save">保存</a>
	<s:set name="menu" value="'__role'"/>
	<s:if test="#request.user">
	<a class="button-main button-icon-back" onclick="f('editUser.action?id=${param.id}')">返回</a>
	<s:set name="menu" value="'__user'"/>
	</s:if>
</t:fragment>
<t:fragment>
		<SCRIPT type="text/javascript">
		function addPrivilege(){
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
					$("#list").append($(a).parent());					
					//$("#list").append($("#__checkbox_"+a.id));					
					//$("#list").append($("#l-"+a.id));					
					//$("#list").append($("#b-"+a.id));					
				}
			});
			
			return;
		}
		</SCRIPT>
</t:fragment>
<t:tpl menuId="${menu }" param="support=form">
		<s:form action="updateUserPrivilege">
		<table class="table-form" style="width:400px">
			<tr>
				<s:if test="#request.role">
				<td class="form-feild">角色</td>
				<td>
				${role.name}
				</td>
				</s:if>
				<s:if test="#request.user">
				<td class="form-feild">用户</td>
				<td>
				${user.name}
				</td>
				</s:if>
			</tr>
			<tr>
				<td class="form-feild">已有权限</td>
				<td>
				<div id="list">
				<s:iterator id="p" value="#request.list" status="pri">
					<div>
					<s:checkbox name="privilegeId" fieldValue="%{#p.id}" value="true" label="%{#p.id}" id="o-p-%{#p.id}" cssClass="rl"/>
					<s:label for="o-%{#p.id}" value="%{#p.name}"/>
					<br/>
					</div>
				</s:iterator>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<s:hidden name="id" value="%{#parameters.id}"/>
				<s:hidden name="type" value="%{#parameters.type}"/>
				</td>
			</tr>
		</table>
		</s:form>
		<br/>
		<table class="table-form" style="width:400px">
			<tr>
				<td class="form-feild">所有权限</td>
				<td>
		<div>
			<s:iterator id="p" value="#request.all" status="pri">
				<div>
				<s:checkbox name="privilegeId" fieldValue="%{#p.id}" label="%{#p.id}" id="p-%{#p.id}" cssClass="al"/>
				<s:label id="l-p-%{#p.id}"  for="p-%{#p.id}" value="%{#p.name}"/>
				<br id='b-p-${pri.index}'/>
				</div>
			</s:iterator>
			
		</div>
				</td>
			</tr>
			<tr>
				<td></td><td><a onclick="addPrivilege()" class="button">添加</a></td>
			</tr>
		</table>
</t:tpl>
