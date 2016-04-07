<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/apps-core" prefix="core"%>
<t:menu parentId="catalog.package">
	mainMenu['__catalog']={id:"__catalog",name:"目录管理",link:"/catalog/list.action"};
</t:menu>

<t:menu parentId="form.manage">
	mainMenu['__formm']={id:"__form",name:"${_menu.name }"};
	subMenu['__formm']=[{id:"__element",name:"元数据管理",link:"/element/list.action"},
					{id:"__form",name:"表单管理",link:"/form/list.action"}];
</t:menu>
<t:menu parentId="idgenerator.manage">
	mainMenu['__${core:substringBeforeLast(fn:replace(_menu.id,'.','_'),'_') }']={id:"__${core:substringBeforeLast(fn:replace(_menu.id,'.','_'),'_') }",name:"${_menu.name }",link:"${_menu.url }"};
</t:menu>