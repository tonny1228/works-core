<%@ page language="java" pageEncoding="utf-8"%>
	<s:iterator value="#request._form.formElements">
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
						<a onclick="javascript:o('../file/load.action?objectId=${itemId}&catalog=form&type=replace&callback=Uploaded${id}',300,60)">上传</a></td>
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
	   							buttonImage: '/images/calendar.gif',
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
	   					<s:radio name="ele_%{id}" list="%{element.options.split('\n')}" value="%{element.defaultValue}"/>
	   				</s:if>
	   				<s:if test="element.viewType=='select'">
	   					<s:select name="ele_%{id}" list="%{element.options.split('\n')}"/>
	   				</s:if>
	   				<s:if test="element.viewType=='checkbox'">
	   					<s:checkboxlist name="ele_%{id}" list="%{element.options.split('\n')}"/>
	   				</s:if>
					<s:set name="rules" value="#{'string':'{required:'+(element.required==1)+',minlength:'+element.minLength+',maxlength:'+(element.maxLength>0?element.maxLength:100000)+',regex:/^'+(element.regex!=null && element.regex!=''?element.regex:'.*')+'$/}',
	   					'text':'{required:'+(element.required==1)+',minlength:'+element.minLength+',maxlength:'+(element.maxLength>0?element.maxLength:100000)+',regex:/^'+(element.regex==null?element.regex:'.*')+'$/}',
	   					'date':'{required:'+(element.required==1)+',regex:/^(\\\d{4}\\\-\\\d{2}\\\-\\\d{2}(\\\s\\\d{2}:\\\d{2}:\\\d{2})?)?$/}',
	   					'attachment':'{required:'+(element.required==1)+'}',
	   					'int':'{required:'+(element.required==1)+',digits:true,range:['+element.minLength+','+element.maxLength+']}',
	   					'float':'{required:'+(element.required==1)+',number:true,range:['+element.minLength+','+element.maxLength+']}'}"/>
	   				<script>rule['ele_${id}']=${rules[element.dataType]};</script>
	   				</td>
	   			</tr>
	   			</s:iterator>