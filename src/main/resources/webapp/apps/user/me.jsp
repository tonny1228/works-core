<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<t:fragment>
	<style>
	a{cursor: pointer;}
	.attimg{width: 128px;height: 128px;}
	</style>
	<script>
	function uploadHead(){
		var upload = new FileUploadDialog({title:'上传头像',objectId:'${user.id}',catalog:'user.head',type:'replace',fileext:'jpg',num:1,singleMaxSize:100000,callback:function(files){
			$('.attimg').attr('src','${pageContext.request.contextPath}'+files[0].path);
		}});
		upload.show();
	}
	var validate_rules={
			'user.username' : {required:true,rangelength:[4,30]},
			'user.name' : {required:true,rangelength:[1,30]},
			'user.birthday':{date:true},
			'user.telNo':{maxlength:30},
			'user.mobileNo':{maxlength:30},
			'user.email':{email:true},
			'user.zip':{digits:true,maxlength:20},
			'user.address':{maxlength:100}
	};
	</script>
	</t:fragment>
<t:fragment name="_nav">设置</t:fragment><t:fragment name="_title">修改我的信息</t:fragment>
<t:tpl menuId="__setting" param="support=form,upload">
		<s:form action="editMine">
		<table class="table-form column2" title="${_title }">
			<tr>
				<td class="form-feild">用户名</td>
				<td><s:textfield name="user.username" value="%{#request.user.username}" readonly="true"/></td>
				<td rowspan="3" class="form-feild">头像</td>
				<td rowspan="3">
					<div style="border: 1px solid #ccc;padding: 3px;width:128px;">
						<a href="javascript:uploadHead()">
						<core:attach objectId="${user.id}" catalog="user.head" manage="false" ></core:attach>
						<s:if test="#request._attachList.size()==0"><img src="${pageContext.request.contextPath}/images/noface.gif" class="attimg"/></s:if>
						</a>
					</div>
				</td>
			</tr>
			<tr>
				<td class="form-feild">姓名</td>
				<td><s:textfield name="user.name" value="%{#request.user.name}"/></td>
			</tr>
			<tr>
				<td class="form-feild">性别</td>
				<td><s:radio name="user.sex" list="#{'0':'男','1':'女' }" value="%{#request.user.sex}"/></td>
			</tr>
			
			<tr>
				<td class="form-feild" >出生日期</td>
				<td colspan="3"><t:datetime name="user.birthday" id="user_birthday">
					<s:param name="value">
						<s:date name="%{#request.user.birthday}" format="yyyy-MM-dd"/>
					</s:param>
				</t:datetime></td>
			</tr>
			
			<tr>
				<td class="form-feild">电话</td>
				<td><s:textfield name="user.telNo" value="%{#request.user.telNo}"/></td>
				<td class="form-feild">手机</td>
				<td><s:textfield name="user.mobileNo" value="%{#request.user.mobileNo}"/></td>
			</tr>
			<tr>
				<td class="form-feild">邮件</td>
				<td colspan="3"><s:textfield name="user.email" value="%{#request.user.email}"/></td>
			</tr>
			<tr>
				<td class="form-feild">地址</td>
				<td colspan="3"><s:textfield name="user.address" value="%{#request.user.address}"/></td>
			</tr>
			<tr>
				<td class="form-feild">邮编</td>
				<td colspan="3"><s:textfield name="user.zip" value="%{#request.user.zip}"/></td>
			</tr>

			<tr>
				<td class="form-feild">备注</td>
				<td colspan="3"><s:textarea name="user.info" value="%{#request.user.info}" cols="55"/></td>
			</tr>
			<tr>
				<td></td>
				<td align="left">
				<s:submit value="保存"/>
				<s:hidden name="user.id" value="%{#request.user.id}"></s:hidden>
				<s:hidden name="positionId" value="%{#parameters.positionId}"></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
</t:tpl>
