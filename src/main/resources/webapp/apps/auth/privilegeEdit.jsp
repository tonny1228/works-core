<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="privilege_include.jsp" %>
<t:fragment name="_title">编辑权限</t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#savePrivilege').submit()" class="button-main button-icon-save" type="button"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
		<SCRIPT type="text/javascript">
		var validate_rules={
			'name' : {required:true,rangelength:[2,30]},
			'resourceId' : {required:true}
		};
		function addResource(){
			var id="";
			$(".rl").each(function(i,a){
				if(a.checked){
					if(document.getElementById("o-"+a.id)){
						$("#"+a.id).remove();
						$("#__checkbox_"+a.id).remove();
						$("#l-"+a.id).remove();
						$("#b-"+a.id).remove();
						return;
					}
					//alert($(a).parent().html());
					$("#list").append($(a).parent());
					$(a).parent().attr('style','');
					//$("#list").append($("#__checkbox_"+a.id));					
					//$("#list").append($("#l-"+a.id));					
					//$("#list").append($("#b-"+a.id));					
				}
			});
			
			return;
		}
		
		</SCRIPT>
</t:fragment>
<t:tpl menuId="__auth" param="support=form">
		<s:form action="savePrivilege">
		<table width="600" class="table-form">
			<tr>
				<td class="form-feild">名称</td>
				<td><s:textfield name="name" value="%{#request.privilege.name}" cssClass="ip"/></td>
			</tr>
			<tr>
				<td class="form-feild">权限</td>
				<td>
					<div id="list">
					<s:iterator id="r" value="#request.privilege.resources" status="res">
						<s:checkbox name="resourceId" fieldValue="%{#r.id}" label="%{#r.id}" id="o-res-%{#r.id}" value="true" cssClass="added"/>
						<s:label for="res-%{#r.id}" value="%{#r.name}"/>
						<br/>
					</s:iterator>
					</div>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td>
				<s:submit value="保存"/>
				<s:hidden name="id" value="%{#request.privilege.id}"></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
		<br/>
		<table width="600" class="table-form">
			<tr>
				<td class="form-feild">可添加权限</td>
				<td>
					<div>
						<s:iterator id="r" value="#request.resource" status="res">
							<s:if test="%{#r.parent== null || #r.parent.id==null || #r.parent.id.length==0}">
								<div style="border-top: 1px solid #aaa;">
								<s:checkbox name="resourceId" fieldValue="%{#r.id}" label="%{#r.id}" id="res-%{#r.id}" cssClass="rl"/>
								<s:label id="l-res-%{#r.id}" for="res-%{#r.id}" value="%{#r.name}" cssStyle="font-weight:bold;"/>
								<br id='b-res-${r.id }' />
								</div>
							</s:if>
							<s:else>
								<div style="padding-left: 20px;">
								<s:checkbox name="resourceId" fieldValue="%{#r.id}" label="%{#r.id}" id="res-%{#r.id}" cssClass="rl"/>
								<s:label id="l-res-%{#r.id}" for="res-%{#r.id}" value="%{#r.name}"/>
								<br id='b-res-${r.id }' />
								</div>
							</s:else>
						</s:iterator>
						
					</div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
				<a onclick="addResource()" class="button">添加</a>
				</td>
			</tr>
		</table>
</t:tpl>

