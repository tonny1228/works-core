<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:fragment name="_header">
		<script>
			function OK(){
				var selected = zTree.getSelectedNodes();
				var ids="",names="",layers="",nameLayer="";
				for(i=0;i<selected.length;i++){
					ids+=selected[i].id;
					names+=selected[i].name;
					layers+=selected[i].idLayer;
					nameLayer+=selected[i].nameLayer;
					if(i<selected.length-1){
						ids+=",";
						names+=",";
						layers+=",";
						nameLayer+=",";
					}
				}
				var x = opener?opener:parent;
				x.<%=request.getParameter("callback")%>(ids,names,layers,nameLayer);
			}
			
			var zTree;
			var demoIframe;
		 
			var setting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: ${'multi'==param.multi},
					expandSpeed: "fast"
				},
				<s:if test="#parameters.async[0]=='true'">
				async: {
					enable: true,
					url:"tree.action",
					autoParam:["id"],
					otherParam:["node","true","selected","${param.selected}","depth","${param.depth}"]
				},
				</s:if>
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: ""
					}
				},
				callback: {
					onClick: function(treeId, treeNode) {
						<s:if test='%{#parameters.event[0].equals("click")}'>
						OK();
						</s:if>
					}
				}
			};
			var zNodes =[];
			<t:cache seconds="1800">
 		<s:iterator var="c" value="#request.list" status="sta">
 			<s:if test="#parameters.async==null">
	 			<s:if test="#request.list.total<30">
	 			zNodes[${sta.index}]={id:'${id}', pId:'${utils.entityLazyProxy.refresh(treeNode).parentId}',layer:'${utils.entityLazyProxy.refresh(treeNode).idLayer}', name:"${name}",open:true,selected:${fn:contains(param.selected,id)}};
	 			</s:if>
	 			<s:else>
	 				<s:if test="layer.length()<5">
	 				zNodes[${sta.index}]={id:'${id}', pId:'${utils.entityLazyProxy.refresh(treeNode).parentId}',layer:'${utils.entityLazyProxy.refresh(treeNode).idLayer}', name:"${name}", open:true,selected:${fn:contains(param.selected,id)}};
	 				</s:if>
	 				<s:else>
	 				zNodes[${sta.index}]={id:'${id}', pId:'${utils.entityLazyProxy.refresh(treeNode).parentId}',layer:'${utils.entityLazyProxy.refresh(treeNode).idLayer}', name:"${name}", open:false,selected:${fn:contains(param.selected,id)}};
	 				</s:else>
	 			</s:else>
	 		</s:if>
	 		<s:else>
	 			<s:if test="layer.length()<5">
	 			zNodes[${sta.index}]={id:'${id}', pId:'${utils.entityLazyProxy.refresh(treeNode).parentId}',layer:'${utils.entityLazyProxy.refresh(treeNode).idLayer}', name:"${name}",isParent:true, open:true,selected:${fn:contains(param.selected,id)}};
	 			</s:if>
 				<s:else>
	 			zNodes[${sta.index}]={id:'${id}', pId:'${utils.entityLazyProxy.refresh(treeNode).parentId}',layer:'${utils.entityLazyProxy.refresh(treeNode).idLayer}', name:"${name}",isParent:true, open:false,selected:${fn:contains(param.selected,id)}};
	 			</s:else>
	 		</s:else>
		</s:iterator>
			$(document).ready(function(){
				var t = $("#tree");
				t = $.fn.zTree.init(t, setting, zNodes);
				zTree = $.fn.zTree.getZTreeObj("tree");
				var ids = "${param.selected}";
				var nodes = zTree.getNodesByFilter(function(node){
					return node.selected;
				});
				for(i=0;i<nodes.length;i++){
					zTree.selectNode(nodes[i],true);
				}
			});
			</t:cache>
		</script>
	
	</t:fragment>
<t:tpl name="simple" param="support=tree">
	
		<ul id="tree" class="ztree" style="width:90%; overflow:auto;"></ul>
		
</t:tpl>

