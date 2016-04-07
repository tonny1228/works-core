<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment>
    <script>
        var validate_rules ={
            'member.username' : {required:true,rangelength:[4,40],remote: {type: "post",
                url: "/member/check.action",
                data: {
                    username: function() {
                        return $("#save_member_username").val();
                    }
                },
                dataType: "html",
                dataFilter: function(data, type) {
                    if (data == "true")
                        return true;
                    else
                        return false;
                }
            }},
            'member.password' : {required:true,rangelength:[1,20],equalTo:'#con'},
            'member.name' : {required:true,rangelength:[1,20]},
            'member.sex' : {required:true,rangelength:[1,20]},
            'member.birthday' : {required:true,rangelength:[10,10]},
            'year' : {required:true,rangelength:[1,20]},
            'month' : {required:true,rangelength:[1,20]},
            'day' : {required:true,rangelength:[1,20]},
            'member.telNo' : {required:true,rangelength:[1,13]},
            'member.mobileNo' : {required:false,digits:true,rangelength:[1,13]},
            'member.address' : {required:true,rangelength:[1,50]},
            'member.zipCode' : {required:true,rangelength:[1,6]},
            'member.answer' : {required:true,rangelength:[5,10]}
        } ;
        validate_msg = {
            'member.username': {
                remote: "用户名已被注册,请换一个用户名再试"
            }
        }
        /*
         addOnLoad(function(){
         alert('1');
         $('#save').validate({
         rules:rule,
         errorPlacement:jQueryErrorPlacement,
         success:jQuerySuccess,
         messages: {
         'member.username': {
         remote: "用户名已被注册,请换一个用户名再试"
         }
         }
         });
         });
         */
    </script>
    <style>
        <!--
        input[type='text'] {
            width: 200px;
        }
        span.required{font-weight: bold;}
        -->
    </style>
</t:fragment>
<t:fragment name="_nav">会员中心</t:fragment><t:fragment name="_title"><s:text name="Member.add.title"/></t:fragment>
<t:tpl name="blank" param="support=form">
    <div class="contentbox">
        <div id="leftpage">
            <div class="pagemodule">
                <h2 class="moduletitle"><a href='#' class="menu1 title3">用户注册</a></h2>
            </div>
        </div>
        <div class="lmain">
            <div id="contentpage2">
                <h1 class="pagetitle">会员注册</h1>
                <s:form action="save" namespace="/member">
                    <s:hidden name="member.userId"/>
                    <table class="table-form">
                        <tr>
                            <td class="form-feild" valign="top"><span class="required">登录名</span><div>(Email)</div></td>
                            <td>
                                <s:textfield name="member.username"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">密码</span></td>
                            <td>
                                <s:password name="member.password"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">确认密码</span></td>
                            <td>
                                <s:password name="confirm" id="con"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">姓名</span></td>
                            <td>
                                <s:textfield name="member.name"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">性别</span></td>
                            <td>
                                <s:select name="member.sex" list="{'男','女'}"></s:select>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">生日</span></td>
                            <td>
                                <select name="year" id="year" onchange="$('#birthday').attr('value',$('#year').attr('value')+'-'+$('#month').attr('value')+'-'+$('#day').attr('value'))">
                                    <option></option>
                                    <%
                                        for(int i=1950;i<2000;i++){
                                            out.println("<option value='"+i+"'>"+i+"</option>");
                                        }
                                    %>
                                </select>年
                                <select name="month" id="month" onchange="$('#birthday').attr('value',$('#year').attr('value')+'-'+$('#month').attr('value')+'-'+$('#day').attr('value'))">
                                    <option></option>
                                    <option value="01">1</option>
                                    <option value="02">2</option>
                                    <option value="03">3</option>
                                    <option value="04">4</option>
                                    <option value="05">5</option>
                                    <option value="06">6</option>
                                    <option value="07">7</option>
                                    <option value="08">8</option>
                                    <option value="09">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                </select>月
                                <select name="day" id="day" onchange="$('#birthday').attr('value',$('#year').attr('value')+'-'+$('#month').attr('value')+'-'+$('#day').attr('value'))">
                                    <option></option>
                                    <option value="01">1</option>
                                    <option value="02">2</option>
                                    <option value="03">3</option>
                                    <option value="04">4</option>
                                    <option value="05">5</option>
                                    <option value="06">6</option>
                                    <option value="07">7</option>
                                    <option value="08">8</option>
                                    <option value="09">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                    <option value="13">13</option>
                                    <option value="14">14</option>
                                    <option value="15">15</option>
                                    <option value="16">16</option>
                                    <option value="17">17</option>
                                    <option value="18">18</option>
                                    <option value="19">19</option>
                                    <option value="20">20</option>
                                    <option value="21">21</option>
                                    <option value="22">22</option>
                                    <option value="23">23</option>
                                    <option value="24">24</option>
                                    <option value="25">25</option>
                                    <option value="26">26</option>
                                    <option value="27">27</option>
                                    <option value="28">28</option>
                                    <option value="29">29</option>
                                    <option value="30">30</option>
                                    <option value="31">31</option>
                                </select>
                                <s:hidden id="birthday" name="member.birthday" value="0000-00-00"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">通讯地址</span></td>
                            <td>
                                <s:textfield name="member.address"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">邮编</span></td>
                            <td>
                                <s:textfield name="member.zip" cssStyle="width:100px"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">办公电话</span></td>
                            <td>
                                <s:textfield name="member.telNo"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span>手机</span></td>
                            <td>
                                <s:textfield name="member.mobileNo"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">密码找回问题</span></td>
                            <td>
                                <s:select name="member.question" list="{'您最敬佩的人是?','您的大学是?','您最喜欢的运动是?'}"></s:select>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-feild"><span class="required">密码找回答案</span></td>
                            <td>
                                <s:textfield name="member.answer"/>
                            </td>
                        </tr>
                        <tr><td colspan="2"><s:submit value="注册"/></td></tr>
                    </table>
                </s:form>
            </div>
        </div>
    </div>
</t:tpl>