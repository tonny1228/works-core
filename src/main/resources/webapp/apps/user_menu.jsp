<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<t:menu parentId="auth.package">
	mainMenu['__privlege']={id:"__privlege",name:"${_menu.name }"};
	subMenu['__privlege']=[];
	<s:iterator value="#request._submenu">
	subMenu['__privlege'][subMenu['__privlege'].length]={id:"__${core:substringBeforeLast(fn:replace(id,'.','_'),'_') }",name:"${name}",link:"${url}"};
	</s:iterator>
	subMenu['__privlege'][subMenu['__privlege'].length]={id:"__dataOwnerAuth",name:"数据权限",link:"/dataOwner/list.action"};
	
</t:menu>
<t:menu parentId="user.manage">
	mainMenu['__userm']={id:"__userm",name:"${_menu.name }"};
	subMenu['__userm']=[];
	<s:iterator value="#request._submenu">
	subMenu['__userm'][subMenu['__userm'].length]={id:"__${core:substringBeforeLast(fn:replace(id,'.','_'),'_') }",name:"${name}",link:"${url}"};
	</s:iterator>
    <t:authCheck auth="user.list">
	subMenu['__userm'][subMenu['__userm'].length]={id:"__sysuser",name:"系统用户",link:"/user/sysUsers.action"};
    </t:authCheck>
    subMenu['__userm'][subMenu['__userm'].length]={id:"__subuser",name:"子用户管理",link:"/user/sub/userList.action"};
    subMenu['__userm'][subMenu['__userm'].length]={id:"__buuser",name:"单位用户管理",link:"/user/bu/userList.action"};
</t:menu>
