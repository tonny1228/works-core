<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib uri="/apps-core" prefix="core"%>
var _${param.id }=[];
var _${param.id }Map=[];
<core:catalog parentId="${param.id }" name="sheng" type="0" depth="${param.depth }">
var j = _${param.id }.length;

var item ={name:'${sheng.name }',id:'${sheng.id }',parent:'${sheng.treeNode.parentId}',subs:[]};
if(_${param.id }Map['${sheng.treeNode.parentId}']){
	_${param.id }Map['${sheng.treeNode.parentId}'].subs[_${param.id }Map['${sheng.treeNode.parentId}'].subs.length]=item;
}else{
	_${param.id }[j]=item;
}
_${param.id }Map['${sheng.id }']=item;
</core:catalog>

initCatalog();