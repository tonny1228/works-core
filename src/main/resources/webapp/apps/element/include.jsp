<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<t:fragment name="_nav"> <a href="../form/list.action">表单管理</a>&gt; <a href="list.action">元数据管理</a></t:fragment>
<s:set name="type" value="#{'string':#{'text':'文本框','textarea':'文本域','radio':'单选','checkbox':'多选','select':'下拉列表'}
   					,'text':#{'textarea':'大文本','htmleditor':'html编辑器'}
   					,'int':#{'text':'文本框','radio':'单选','select':'下拉列表'}
   					,'float':#{'text':'文本框','radio':'单选','select':'下拉列表'}
   					,'date':#{'date':'日期','datetime':'日期时间'}
   					,'attachment':#{'attachment':'附件组件'}}" />  
<s:set name="bool" value="{'否','是'}"/>
<s:set name="_required" value="#{0:'可为空',1:'不为空' }"/>
