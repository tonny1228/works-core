<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@ include file="user_include.jsp" %>
<t:fragment name="_title">组织管理</t:fragment>
<t:fragment name="_ctlbnt">
		<a onclick="addBusinessUnit()" class="button-main button-icon-add">管理单元</a>
		<a id="refreshNode" href="#" onclick="return false;" class="button-main button-icon-refresh">重新加载</a>
</t:fragment>
<t:fragment name="_header" param="tree">
	<script>
		var item;
		var zTree;
		var demoIframe;
	 
		var setting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				expandSpeed: "fast"
			},
			async: {
				enable: true,
				url:"department.action",
				autoParam:["id","type"],
				otherParam:{},
				dataFilter: filter
			},
			callback: {
				beforeClick: function(treeId, treeNode) {
					item = treeNode;
					if(treeNode.type=='Position')
						iwindow.location='editPosition.action?id='+treeNode.id;
					else
						iwindow.location='editDepartment.action?id='+treeNode.id;
				}
			}
		};
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				if(childNodes[i].name)
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("tree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				Frm.alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], "refresh", true);
			}
		}
		
		function refreshParentNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("tree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				Frm.alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].getParentNode())
					zTree.reAsyncChildNodes(nodes[i].getParentNode(), "refresh", true);
				zTree.selectNode(nodes[i]);
			}
		}
		
		function refreshDelay(w){
			w.setTimeout(w.refreshNode,5000);
		}
		function refreshParentDelay(w){
			w.setTimeout(w.refreshParentNode,5000);
		}
		
		function deleteNode(e){
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var node = zTree.getNodeByParam("id",e,null);
			zTree.reAsyncChildNodes(node.getParentNode(), "refresh", true);
			zTree.selectNode(node.getParentNode());
		}
		
		function refreshNodeQuietly(id){
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var node = zTree.getNodeByParam("id",id,null);
			zTree.reAsyncChildNodes(node, "refresh", true);
		}
		
		var zNodes =[];
 		<s:iterator value="#request.list" status="sta">
			zNodes[zNodes.length]={id:'${id}',  name:"${name}", open:false};
		</s:iterator>
 		<s:iterator value="#request.companies" status="sta">
			zNodes[zNodes.length]={id:'${id}', name:"${name}", open:false};
		</s:iterator>
		$(document).ready(function(){
			var t = $("#tree");
			t = $.fn.zTree.init(t, setting);
			zTree = $.fn.zTree.getZTreeObj("tree");
			$('#mainview').css('width',(pageWidth() - 210 -300)+'px');
			$('#mainview').css('height',(pageHeight() - 100)+'px');
			$('#treeview').css('height',(pageHeight() - 100)+'px');
			$("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
			
			var iframe = $('#iwindow');
			iframe.bind("load", resize);
			//$(iwindow).bind("resize", resize);
			
			function resize(){
				var bodyH = iframe.contents().find("body").get(0).scrollHeight;
				var htmlH = iframe.contents().find("html").get(0).scrollHeight;
				var maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH);
				var h = iframe.height() >= maxH ? minH:maxH ;
				//if (h < 700) h = 700;
				var pTar = document.getElementById('iwindow');
				pTar.height=pTar.contentDocument.body.offsetHeight +0;
				iframe.height(Math.max(h,pageHeight()-100));
				$('#mainview').height(Math.max(h,pageHeight()-100));
				$('#treeview').height(Math.max(h,pageHeight()-100));
			}
		});
		
		
		function addBusinessUnit(){
			var id="";
			if(zTree.getSelectedNodes().length>0){
				id = zTree.getSelectedNodes()[0].id;
			}
			iwindow.location='addDepartment.action?type=BusinessUnit&id='+id;
		}
		
	</script>

</t:fragment>

<t:tpl menuId="__org" param="support=tree">
	<div id="treeview" style="border: 1px solid #eee;width: 250px;float: left;">
		<div>
		<ul id="tree" class="ztree" style="width:220px; overflow:auto;"></ul>
		</div>
	</div>
	<div id="mainview" style="border: 1px solid #eee;float: left;border-left: 0px;">
		<iframe id="iwindow" name="iwindow" frameborder="0" width="100%" height="100%"></iframe>
	</div>
</t:tpl>

