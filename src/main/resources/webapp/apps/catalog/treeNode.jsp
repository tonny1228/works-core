<%@ page language="java" pageEncoding="utf-8"%>[
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:if test="#request.list.size()>0">
<s:iterator value="#request.list" status="sta" begin="0" end="0">
	<s:if test="#parameters.depth!=null && #parameters.depth[0]!='' && (''+(layer.length()/4)).compareTo(#parameters.depth[0])>-1">
		<s:set name="isParent" value="false"/>
	</s:if>
	<s:else>
		<s:set name="isParent" value="true"/>
	</s:else>
	{id:'${id}', pId:'${parentId}', name:"${name}",layer:"${layer}",nameLayer:'${nameLayer}',isParent:${isParent},  open:false,selected:${fn:contains(param.selected,id)}}
</s:iterator>
</s:if>
<s:if test="#request.list.size()>1">
<s:iterator value="#request.list" status="sta" begin="1">
	,{id:'${id}', pId:'${parentId}', name:"${name}",layer:"${layer}",nameLayer:'${nameLayer}',isParent:${isParent},  open:false,selected:${fn:contains(param.selected,id)}}
</s:iterator>
</s:if>
]