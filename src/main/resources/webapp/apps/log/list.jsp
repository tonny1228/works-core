<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_title">日志列表</t:fragment>
<t:fragment name="_ctlbnt">

</t:fragment>

<t:fragment>

</t:fragment>
<t:tpl menuId="__log">
    <s:form name="delForm" action="delete">

        <t:complexDataTable  limit="${limit }" offset="${offset }" showSelectButton="true" showRowNo="true" showColumnButton="true" showOrderButton="true" formAction="delete.action"
                             showQueryButton="true" id="articleList" multiSelect="true" checkboxName="id" checkboxValue="%{id}" headerNames="时间|用户|分类|动作|信息" defaultColumns="时间|用户|分类|动作|信息"
                             fields="%{utils.formatDate(logTime,'yyyy-MM-dd')}|%{admin}|%{catalog}|%{action}|%{info}" bundle="#request.query" widths="80|100|80|100|0"/>

    </s:form>
</t:tpl>

