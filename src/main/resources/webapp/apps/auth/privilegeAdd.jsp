<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="privilege_include.jsp" %>
<t:fragment name="_title">权限定义</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#savePrivilege').submit()" class="button-main button-icon-save" type="button"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<script>
var validate_rules={
		'name' : {required:true,rangelength:[2,30]},
		'resourceId' : {required:true}
	};
	</script>
</t:fragment>
<t:tpl menuId="__auth" param="support=form">
		<s:form action="savePrivilege">
		<table class="table-form">
			<tr>
				<td class="form-feild form-required">名称</td>
				<td><s:textfield name="name" value="%{#request.resource.name}" cssClass="ip"/></td>
			</tr>
			<tr>
				<td class="form-feild">权限</td>
				<td>
				<s:iterator id="r" value="#request.resource" status="p">
					<s:if test="%{#r.parent== null || #r.parent.id==null || #r.parent.id.length==0}">
						<div style="border-top: 1px solid #aaa;">
							<s:checkbox name="resourceId" fieldValue="%{#r.id}" label="%{#r.id}" id="role-%{#p.index}" cssClass="added"/>
							<s:label for="role-%{#role.index}" value="%{#r.name}" cssStyle="font-weight:bold;"/>
						</div>
					</s:if>
					<s:else>
						<div style="padding-left: 20px;">
							<s:checkbox name="resourceId" fieldValue="%{#r.id}" label="%{#r.id}" id="role-%{#p.index}" cssClass="added"/>
							<s:label for="role-%{#role.index}" value="%{#r.name}"/>
						</div>
					</s:else>
				</s:iterator>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<s:submit value="保存"/>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>