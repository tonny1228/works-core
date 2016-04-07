<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<t:fragment name="_nav"><a href="../user/listPrivilege.action">权限管理</a> &gt; <a href="listSystemResource.action">资源管理</a></t:fragment>
<s:set name="types" value="#{'0':'接口','1':'主菜单','2':'子菜单','3':'链接'}"/>
<s:set name="apis" value="#{'list':'列表和查询','create':'创建','update':'修改','delete':'删除','read':'读取','special':'特殊权限'}"/>