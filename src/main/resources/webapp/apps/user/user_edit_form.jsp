<%@ page language="java" pageEncoding="utf-8"%>
<t:authCheck auth="UserEntityService.update" errorMessage="<div>您没有权限</div>">
    <div class="button-subs" style="width:600px;">
        <s:if test="#request.user.id!=#request.user.username">
            <a onclick="f('resetPassword.action?id=${parameters.id[0]}')" >重置密码</a>
            <a onclick="f('editUserinfo.action?id=${parameters.id[0]}')" class="ui-button-info">配置用户组织</a>
            <a onclick="f('editUserRole.action?id=${parameters.id[0]}')" >配置用户角色</a>
            <a onclick="f('userPrivilege.action?id=${parameters.id[0]}&type=user')" class="ui-button-success">配置用户权限</a>
            <s:if test="#request.user.status.toString()=='ACTIVE'">
                <a onclick="f('lockUser.action?id=${parameters.id[0]}')" class="lock bnt">锁定</a>
            </s:if>
            <s:elseif test="#request.user.status.toString()=='LOCKED'">
                <a onclick="f('unlockUser.action?id=${parameters.id[0]}')" class="unlock bnt">解锁</a>
            </s:elseif>
            <s:elseif test="#request.user.status.toString()=='INACTIVE'">
                <a onclick="f('unlockUser.action?id=${parameters.id[0]}')" >激活</a>
            </s:elseif>
            <a onclick="del('deleteUser.action?forward=userList.action%3F&id=${parameters.id[0]}')" class="ui-button-danger">删除</a>
        </s:if>
        <s:else>
            <a onclick="f('editUserRole.action?id=${parameters.id[0]}')" >更改用户角色</a>
            <a onclick="f('userPrivilege.action?id=${parameters.id[0]}&type=user')" >配置用户权限</a>
            <s:if test="#request.user.status.toString()=='ACTIVE'">
                <a onclick="f('lockUser.action?id=${parameters.id[0]}')">锁定</a>
            </s:if>
            <s:elseif test="#request.user.status.toString()=='LOCKED'">
                <a onclick="f('unlockUser.action?id=${parameters.id[0]}')">解锁</a>
            </s:elseif>
            <s:elseif test="#request.user.status.toString()=='INACTIVE'">
                <a onclick="f('unlockUser.action?id=${parameters.id[0]}')" >激活</a>
            </s:elseif>
        </s:else>
    </div>
    <s:form action="saveUser">
        <table class="table-form column2" title="编辑用户信息">
            <tr>
                <td class="form-feild">状态</td>
                <td><s:property value="#{'INACTIVE':'未激活','LOCKED':'锁定','ACTIVE':'激活'}[#request.user.status]"/></td>
            </tr>
            <s:set name="rdonly" value="%{#request.user==null?false:true}"/>
            <tr>
                <td class="form-feild">用户名</td>
                <td><s:textfield name="user.username" value="%{#request.user.username}" readonly="%{#rdonly }"/></td>
                <td class="form-feild required">姓名</td>
                <td><s:textfield name="user.name" value="%{#request.user.name}"/></td>
            </tr>
            <tr>
                <td class="form-feild">性别</td>
                <td><s:radio name="user.sex" list="#{'0':'男','1':'女' }" value="%{#request.user.sex}"/></td>
                <td class="form-feild">出生日期</td>
                <td>
                    <t:datetime name="user.birthday" yearRange="1900:%{new java.util.Date().getYear()+1900}">
                        <s:param name="value">
                            <s:date name="%{#request.user.birthday}" format="yyyy-MM-dd"/>
                        </s:param>
                    </t:datetime>
                </td>
            </tr>
            <tr>
                <td class="form-feild">电话</td>
                <td><s:textfield name="user.telNo" value="%{#request.user.telNo}"/></td>
                <td class="form-feild">手机</td>
                <td><s:textfield name="user.mobileNo" value="%{#request.user.mobileNo}"/></td>
            </tr>
            <tr>
                <td class="form-feild">电子邮箱</td>
                <td><s:textfield name="user.email" value="%{#request.user.email}"/></td>
                <td class="form-feild">邮编</td>
                <td><s:textfield name="user.zip" value="%{#request.user.zip}"/></td>
            </tr>
            <tr>
                <td class="form-feild">地址</td>
                <td colspan="3"><s:textfield name="user.address" value="%{#request.user.address}"/></td>
            </tr>
            <tr>
                <td class="form-feild">注册日期</td>
                <td><t:datetime name="user.regTime"  id="regtime">
                    <s:param name="value">
                        <s:date name="%{#request.user.regTime}" format="yyyy-MM-dd"/>
                    </s:param>
                </t:datetime>
                </td>
            </tr>
            <tr>
                <td class="form-feild">备注</td>
                <td colspan="3"><s:textarea name="user.info" value="%{#request.user.info}" rows="4" cols="55"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <s:hidden name="user.id" value="%{#request.user.id}"></s:hidden>
                </td>
            </tr>
        </table>
    </s:form>
</t:authCheck>