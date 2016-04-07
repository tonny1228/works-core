<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<t:fragment name="_nav">会员中心</t:fragment><t:fragment name="_title"><s:text name="Member.edit.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#update').submit()" class="button-main button-icon-save" type="button"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
<style>
a{cursor: pointer;}
.attimg{width: 128px;height: 128px;}
</style>
<script>
	addOnLoad(function(){
		
			$('#update').validate({   
				rules:{
					'member.id' : {required:true,rangelength:[1,32]}
					,'member.username' : {required:!false,rangelength:[0,20]}
					,'member.name' : {required:!true,rangelength:[0,30]}
					,'member.idNo' : {required:!true,rangelength:[0,50]}
					,'member.telNo' : {required:!true,rangelength:[0,20]}
					,'member.mobileNo' : {required:!true,rangelength:[0,25]}
					,'member.email' : {required:!true,rangelength:[0,60]}
					,'member.question' : {required:!true,rangelength:[0,300]}
					,'member.answer' : {required:!true,rangelength:[0,300]}
					,'member.sex' : {required:!true,rangelength:[0,1]}
					,'member.birthday' : {required:!true,regex:/^(\d{4}\-\d{2}\-\d{2})?$/}
				},
				errorPlacement:jQueryErrorPlacement,
				success:jQuerySuccess
			});
	});
	function uploadHead(){
		var upload = new FileUploadDialog({title:'上传头像',objectId:'${user.id}',catalog:'user.head',type:'replace',fileext:'jpg',num:1,singleMaxSize:100000,callback:function(files){
			$('.attimg').attr('src','${pageContext.request.contextPath}'+files[0].path);
		}});
		upload.show();
	}
</script>
</t:fragment>
<t:tpl theme="member" menuId="__user" param="support=form">
	<s:form action="update" namespace="/member">
		<s:hidden name="member.id"/>
		<s:hidden name="member.username"/>
		<table class="table-form" title="${_title }">
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.name"/></td>
	   				<td>
					<s:textfield name="member.name"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.idNo"/></td>
	   				<td>
					<s:textfield name="member.idNo"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.tel"/></td>
	   				<td>
					<s:textfield name="member.telNo"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.mobile"/></td>
	   				<td>
					<s:textfield name="member.mobileNo"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.email"/></td>
	   				<td>
					<s:textfield name="member.email"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.address"/></td>
	   				<td>
					<s:textfield name="member.address"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.zip"/></td>
	   				<td>
					<s:textfield name="member.zipCode"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.question"/></td>
	   				<td>
					<s:textfield name="member.question"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.answer"/></td>
	   				<td>
					<s:textfield name="member.answer"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.gender"/></td>
	   				<td>
	   					<s:select name="member.sex" list="{'男','女'}"/>
   					</td>
   			</tr>
	   		<tr>
	   				<td class="form-feild"><s:text name="Member.birth"/></td>
	   				<td>
						<t:datetime name="member.birthday" id="member_birth">
							<s:param name="value"><s:date name="member.birthday" format="yyyy-MM-dd"/></s:param>
						</t:datetime>
   					</td>
   			</tr>
			<tr>
				<td class="form-feild">头像</td>
				<td>
					<div style="border: 1px solid #ccc;padding: 3px;width:128px;">
						<a href="javascript:uploadHead()">
						<core:attach objectId="${member.id}" catalog="member.head" manage="false" />
						<s:if test="#request._attachList.size()==0"><img src="${pageContext.request.contextPath}/images/noface.gif" class="attimg"/></s:if>
						</a>
					</div>
				</td>
			</tr>
			<tr><td colspan="2"><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>