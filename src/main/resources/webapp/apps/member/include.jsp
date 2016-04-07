<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="_status" value="#{'ACTIVE':'正常','INACTIVE':'未激活','LOCKED':'锁定'}"/>
<t:fragment name="_nav"><a href="list.action">会员管理</a></t:fragment>