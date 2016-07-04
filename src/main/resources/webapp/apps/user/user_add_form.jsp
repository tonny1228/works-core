<%@ page language="java" pageEncoding="utf-8"%>
<t:authCheck auth="UserEntityService.create" errorMessage="<div>您没有权限</div>">
    <s:form action="saveUser">
        <s:hidden name="positionId" value="%{#parameters.positionId}"/>
        <table class="table-form column2" title="添加用户">
            <tr>
                <td class="form-feild">用户名</td>
                <td><s:textfield name="user.username"/></td>
                <td class="form-feild">姓名</td>
                <td><s:textfield name="user.name"/></td>
            </tr>
            <tr>
                <td class="form-feild">性别</td>
                <td><s:radio name="user.sex" list="#{'0':'男','1':'女' }" value="0"/></td>
                <td class="form-feild">状态</td>
                <td ><s:radio name="user.status" list="#{'ACTIVE':'激活','INACTIVE':'未激活','LOCKED':'锁定'}" value="'ACTIVE'"/></td>
            </tr>
            <tr>
                <td class="form-feild">密码</td>
                <td><s:password name="user.password"  onchange="$('#saveUser').validate().element($('#saveUser_confirm').get(0))"/></td>
                <td class="form-feild">确认密码</td>
                <td><s:password name="confirm"  onchange="$('#saveUser').validate().element($('#saveUser_user_password').get(0))"/></td>
            </tr>

            <tr>
                <td class="form-feild">出生日期</td>
                <td colspan="3">
                    <t:datetime name="user.birthday" id="birthday" yearRange="1900:%{new java.util.Date().getYear()+1900}" />
                </td>
            </tr>
            <tr>
                <td class="form-feild">电话</td>
                <td><s:textfield name="user.telNo" /></td>
                <td class="form-feild">手机</td>
                <td><s:textfield name="user.mobileNo" /></td>
            </tr>
            <tr>
                <td class="form-feild">电子邮箱</td>
                <td colspan="3"><s:textfield name="user.email"/></td>
            </tr>
            <tr>
                <td class="form-feild">地址</td>
                <td colspan="3"><s:textfield name="user.address"/></td>
            </tr>
            <tr>
                <td class="form-feild">邮编</td>
                <td><s:textfield name="user.zip"/></td>
                <td class="form-feild">注册日期</td>
                <td>
                    <t:datetime name="user.regTime"  id="regtime">
                        <s:param name="value">
                            <s:date name="%{new java.util.Date()}" format="yyyy-MM-dd"/>
                        </s:param>
                    </t:datetime>

                </td>
            </tr>
            <tr>
                <td class="form-feild">备注</td>
                <td colspan="3"><s:textarea name="user.info" value="%{#request.user.info}" rows="4" cols="55"/></td>
            </tr>
        </table>
    </s:form>
</t:authCheck>
