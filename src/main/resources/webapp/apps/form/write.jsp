<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<t:fragment name="_nav"> 表单管理</t:fragment><t:fragment name="_title"><s:text name="Form.bnt.write"/></t:fragment>
<t:fragment name="_ctlbnt">
	<a onclick="$('#save').submit()" class="button-main button-icon-save"><s:text name="button.save"/></a>
</t:fragment>
<t:fragment>

<script>
	var rule = new Array();
	
	addOnLoad(function(){
		
			$('#saveFormValues').validate({   
				rules:rule,
				errorPlacement:jQueryErrorPlacement,
				success:jQuerySuccess
			});
	});
	function o(u,w,h){
		window.x = new FrameWindow('附件上传',u,w,h);
		window.x.show();
	}
</script>
</t:fragment>
<t:tpl>
	<s:set name="required" value="{'','class=\"required\"' }"></s:set>
	<s:form action="saveFormValues" namespace="/form">
		<s:hidden name="id" value="%{form.id}"></s:hidden>
   		<s:if test="form.formValues==null">
			<s:token name="itemId"></s:token>
			<table class="table-form">
	   			<tr>
	   				<td class="form-feild"><span><s:text name="Form.name"/></span>
	   				</td>
	   				<td>${form.name}</td>
	   			</tr>
	   			<s:iterator value="form.formElements">
	   			<tr>
	   				<td class="form-feild"><span ${required[element.required]}>${name}</span>
	   				</td>
	   				<td>
	   				<s:if test="element.viewType=='text'">
	   					<s:textfield name="ele_%{id}" />
	   				</s:if>
	   				<s:if test="element.viewType=='attachment'">
	   					<a id="div${id}">
						</a>
						<s:hidden name="ele_%{id}" id="ele_%{id}"/>
						<a onclick="javascript:o('../admin/core/attach/upload.jsp?objectId=${itemId}&catalog=form&type=replace&callback=Uploaded${id}',300,60)">上传</a></td>
						<script>
						function Uploaded${id}(id,name,url){
							var a=document.getElementById('div${id}');
							a.href=url;
							a.alt=name;
							$(a).text(name);
							window.x.hide();
							document.getElementById('ele_${id}').value=id;
						}
						</script>
	   				</s:if>
	   				<s:if test="element.viewType=='date'">
	   					<s:textfield name="ele_%{id}" id="ele_%{id}"/>
	   					<script>
	   					addOnLoad(function() {
	   					    $("#ele_${id}").datepicker({dateFormat: 'yy-mm-dd',
	   							buttonImage: '/admin/images/icon/calendar.gif',
	   							buttonImageOnly: true,
	   							showOn: 'both',
	   							clearText:'清除',
	   							closeText:'关闭',
	   						    prevText:'上月',
	   						    nextText:'下月',
	   						    currentText:' ',
	   						    monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
	   						    dayNames:['日','一','二','三','四','五','六'],
	   						    dayNamesMin:['日','一','二','三','四','五','六']
	   							});
	   					});
	   					</script>
	   				</s:if>
	   				<s:if test="element.viewType=='datetime'">
	   					<s:textfield name="ele_%{id}" id="ele_%{id}"/>
	   					<script>
	   					addOnLoad(function() {
	   					    $("#ele_${id}").datetimepicker({dateFormat: 'yy-mm-dd',timeFormat:'hh:mm:ss',
	   							buttonImage: '/admin/images/icon/calendar.gif',
	   							buttonImageOnly: true,
	   							showSecond: true,
	   							showOn: 'both',
	   							clearText:'清除',
	   							closeText:'关闭',
	   						    prevText:'上月',
	   						    nextText:'下月',
		   						hourText:'时',
		   						timeText:'时间',
		   						minuteText:'分',
		   						currentText:'当前',
		   						secondText:'秒',
	   						    currentText:' ',
	   						    monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
	   						    dayNames:['日','一','二','三','四','五','六'],
	   						    dayNamesMin:['日','一','二','三','四','五','六']
	   							});
	   					});
	   					</script>
	   				</s:if>
	   				<s:if test="element.viewType=='textarea'">
	   					<s:textarea name="ele_%{id}" />
	   				</s:if>
	   				<s:if test="element.viewType=='radio'">
	   					<s:radio name="ele_%{id}" list="%{element.options.split('\n')}"/>
	   				</s:if>
	   				<s:if test="element.viewType=='select'">
	   					<s:select name="ele_%{id}" list="%{element.options.split('\n')}"/>
	   				</s:if>
	   				<s:if test="element.viewType=='checkbox'">
	   					<s:checkboxlist name="ele_%{id}" list="%{element.options.split('\n')}"/>
	   				</s:if>
					<s:set name="rules" value="#{'string':'{required:'+(element.required==1)+',minlength:'+element.minLength+',maxlength:'+(element.maxLength>0?element.maxLength:100000)+',regex:/^'+(element.regex!=''?element.regex:'.*')+'$/}',
	   					'text':'{required:'+(element.required==1)+',minlength:'+element.minLength+',maxlength:'+(element.maxLength>0?element.maxLength:100000)+',regex:/^'+(element.regex==null?element.regex:'.*')+'$/}',
	   					'date':'{required:'+(element.required==1)+',regex:/^(\\\d{4}\\\-\\\d{2}\\\-\\\d{2}(\\\s\\\d{2}:\\\d{2}:\\\d{2})?)?$/}',
	   					'attachment':'{required:'+(element.required==1)+'}',
	   					'int':'{required:'+(element.required==1)+',digits:true,range:['+element.minLength+','+element.maxLength+']}',
	   					'float':'{required:'+(element.required==1)+',number:true,range:['+element.minLength+','+element.maxLength+']}'}"/>
	   				<script>rule['ele_${id}']=${rules[element.dataType]};</script>
	   				</td>
	   			</tr>
	   			</s:iterator>
		   		<tr><td colspan="2"><s:submit value="%{getText('bnt.save')}"/></td></tr>
			</table> 
   		</s:if>
   		<s:else>
			<s:hidden name="itemId" value="%{#parameters.itemId[0]}"></s:hidden>
			<table class="table-form">
	   			<tr>
	   				<td class="form-feild"><span><s:text name="Form.name"/></span>
	   				</td>
	   				<td>${form.name}</td>
	   			</tr>
	   			<s:iterator value="form.formValues">
	   			<tr>
	   				<td class="form-feild"><span ${required[formElement.element.required]}>${formElement.name}</span>
	   				</td>
	   				<td>
	   				<s:if test="formElement.element.viewType=='text'">
	   					<s:textfield name="ele_%{formElement.id}" value="%{value}"/>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='attachment'">
	   					<core:attach id="${value}"><a id="div${formElement.id}" href="${_attach.attachment.path}">${_attach.attachment.filename}</a></core:attach>
						<s:hidden name="ele_%{formElement.id}" id="ele_%{formElement.id}"  value="%{value}"/>
						<a onclick="javascript:o('../admin/core/attach/upload.jsp?objectId=${itemId}&catalog=form&type=replace&callback=Uploaded${formElement.id}',300,60)">上传</a></td>
						<script>
						function Uploaded${formElement.id}(id,name,url){
							var a=document.getElementById('div${formElement.id}');
							a.href=url;
							a.alt=name;
							$(a).text(name);
							window.x.hide();
							document.getElementById('ele_${formElement.id}').value=id;
						}
						</script>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='date'">
	   					<s:textfield name="ele_%{formElement.id}" id="ele_%{formElement.id}" value="%{value}"/>
	   					<script>
	   					addOnLoad(function() {
	   					    $("#ele_${formElement.id}").datepicker({dateFormat: 'yy-mm-dd',
	   							buttonImage: '/admin/images/icon/calendar.gif',
	   							buttonImageOnly: true,
	   							showOn: 'both',
	   							clearText:'清除',
	   							closeText:'关闭',
	   						    prevText:'上月',
	   						    nextText:'下月',
	   						    currentText:' ',
	   						    monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
	   						    dayNames:['日','一','二','三','四','五','六'],
	   						    dayNamesMin:['日','一','二','三','四','五','六']
	   							});
	   					});
	   					</script>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='datetime'">
	   					<s:textfield name="ele_%{formElement.id}" id="ele_%{formElement.id}" value="%{value}"/>
	   					<script>
	   					addOnLoad(function() {
	   					    $("#ele_${formElement.id}").datetimepicker({dateFormat: 'yy-mm-dd',timeFormat:'hh:mm:ss',
	   							buttonImage: '/admin/images/icon/calendar.gif',
	   							buttonImageOnly: true,
	   							showSecond: true,
	   							showOn: 'both',
	   							clearText:'清除',
	   							closeText:'关闭',
	   						    prevText:'上月',
	   						    nextText:'下月',
		   						hourText:'时',
		   						timeText:'时间',
		   						minuteText:'分',
		   						currentText:'当前',
		   						secondText:'秒',
	   						    currentText:' ',
	   						    monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
	   						    dayNames:['日','一','二','三','四','五','六'],
	   						    dayNamesMin:['日','一','二','三','四','五','六']
	   							});
	   					});
	   					</script>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='textarea'">
	   					<s:textarea name="ele_%{formElement.id}"  value="%{value}"/>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='radio'">
	   					<s:radio name="ele_%{formElement.id}" list="%{formElement.element.options.split('\n')}" value="%{value}"/>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='select'">
	   					<s:select name="ele_%{formElement.id}" list="%{formElement.element.options.split('\n')}" value="%{value}"/>
	   				</s:if>
	   				<s:if test="formElement.element.viewType=='checkbox'">
	   					<s:checkboxlist name="ele_%{formElement.id}" list="%{formElement.element.options.split('\n')}" value="%{value.split(', ')}"/>
	   				</s:if>
					<s:set name="rules" value="#{'string':'{required:'+(formElement.element.required==1)+',minlength:'+formElement.element.minLength+',maxlength:'+(formElement.element.maxLength>0?formElement.element.maxLength:100000)+',regex:/^'+(formElement.element.regex!=''?formElement.element.regex:'.*')+'$/}',
	   					'text':'{required:'+(formElement.element.required==1)+',minlength:'+formElement.element.minLength+',maxlength:'+(formElement.element.maxLength>0?formElement.element.maxLength:100000)+',regex:/^'+(formElement.element.regex==null?formElement.element.regex:'.*')+'$/}',
	   					'date':'{required:'+(formElement.element.required==1)+',regex:/^(\\\d{4}\\\-\\\d{2}\\\-\\\d{2}(\\\s\\\d{2}:\\\d{2}:\\\d{2})?)?$/}',
	   					'attachment':'{required:'+(formElement.element.required==1)+'}',
	   					'int':'{required:'+(formElement.element.required==1)+',digits:true,range:['+formElement.element.minLength+','+formElement.element.maxLength+']}',
	   					'float':'{required:'+(formElement.element.required==1)+',number:true,range:['+formElement.element.minLength+','+formElement.element.maxLength+']}'}"/>
	   				<script>rule['ele_${formElement.id}']=${rules[formElement.element.dataType]};</script>
	   				</td>
	   			</tr>
	   			</s:iterator>
		   			<tr><td colspan="2"><s:submit value="%{getText('bnt.save')}"/></td></tr>
			</table> 
   		</s:else>
	</s:form>
</t:tpl>