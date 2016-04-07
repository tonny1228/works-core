<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title"><s:text name="Element.add.title"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#save').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>
	<script>
		addOnLoad(function(){
			$('#save_element_viewType').change(function(){
				showOptions($(this).children('option:selected').val()); 
		    });
			showOptions($('#save_element_viewType').children('option:selected').val());
			$('#save').validate({   
				rules:{
					'element.name' : {required:true,rangelength:[2,37]},
					'element.dataType' : {required:true,rangelength:[1,20]}
				},
				errorPlacement:jQueryErrorPlacement,
				success:jQuerySuccess
			});
		});
		function showOptions(viewType){
			if(viewType=='radio'||viewType=='checkbox'||viewType=='select'){
				$('#options').css('display','');
			}else{
				$('#options').css('display','none');
			}
		}
		
		var ct;
		function selectCatalog(id,type){
			if(!ct){
				ct = new CatalogTree('选择分类',300,400,id);
				ct.callback=callback;
				ct.event='click';
			}
			ct.selected=$('#catalogId').attr('value');
			ct.show();
		}
		function callback(id,name){
			$('#catalogId').attr('value',id);
			$('#catalogName').attr('value',name);
		}
		
		var validate_rules={
				'element.name' : {required:true,rangelength:[2,30]},
				'Element.dataType' : {required:true}
			};
	</script>
</t:fragment>
<t:tpl menuId="__element" param="support=catalog,form">
	<s:form action="save">
		<table class="table-form">
   			<tr>
   				<td class="form-feild"><s:text name="Element.name"/>
   				</td>
   				<td><s:textfield name="element.name" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.dataType"/>
   				</td>
   				<td>
   					<s:doubleselect onchange="showOptions();" name="element.dataType" list="{'string','text','int','float','date','attachment'}" doubleListKey="key" doubleListValue="value" doubleName="element.viewType" doubleList="#type[top]" doubleOnchange="showOptions(this)"/>
   				</td>
   			</tr>
   			<tr id="options" style="display:none">
   				<td class="form-feild"><s:text name="Element.options"/>
   				</td>
   				<td><s:textarea name="element.options" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.defaultValue"/>
   				</td>
   				<td><s:textfield name="element.defaultValue" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.required"/>
   				</td>
   				<td><s:radio name="element.required" list="%{#_required}" value="0"/> </td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.minLength"/>
   				</td>
   				<td><s:textfield name="element.minLength" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.maxLength"/>
   				</td>
   				<td><s:textfield name="element.maxLength" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.regex"/>
   				</td>
   				<td><s:textfield name="element.regex" /></td>
   			</tr>
   			<tr>
   				<td class="form-feild"><s:text name="Element.catelog"/>
   				</td>
   				<td><s:textfield id="catalogName" name="catalogName" readonly="true" value="%{#request.catalog.name}" onclick="selectCatalog('%{#request.catalog.id}',0)"/>
   					<s:hidden name="catalogId" id="catalogId" value="%{#request.catalog.id}" /></td>
   			</tr>
			<tr><td></td><td><s:submit value="%{getText('button.save')}"/></td></tr>
		</table> 
	</s:form>
</t:tpl>