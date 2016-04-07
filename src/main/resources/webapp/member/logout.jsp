<%@page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	session.invalidate();
	response.sendRedirect("login.jsp?url="+(request.getHeader("referer")==null?"":URLEncoder.encode(request.getHeader("referer"), "utf-8")));
%>