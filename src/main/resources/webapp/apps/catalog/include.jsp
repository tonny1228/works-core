<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="_display" value="#{1:'显示',0:'不显示'}"/>
<s:set name="_status" value="#{1:'停用',0:'正常'}"/>
<t:fragment name="_nav"><a href="list.action">目录管理</a></t:fragment>