<div class="datetimepicker-container">
<input type="text"<#rt/> name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/css.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" />
/><#rt/>

</div>
<script>
addOnLoad(function() {
	<#if parameters.type=="datetime">
	 	$("#${parameters.id?html}").datetimepicker({<#rt/>
	<#elseif parameters.type=="time">
	 	$("#${parameters.id?html}").timepicker({<#rt/>
	<#else>
		$("#${parameters.id?html}").datepicker({<#rt/>
	</#if>
	<#if parameters.dateFormat??>
	 	dateFormat: '${parameters.dateFormat?html}',<#rt/>
	</#if>
	<#if parameters.minDate??>
	 	minDate: '${parameters.minDate?html}',<#rt/>
	</#if>
	<#if parameters.maxDate??>
	 	maxDate: '${parameters.maxDate?html}',<#rt/>
	</#if>
	<#if parameters.yearRange??>
	 	yearRange: '${parameters.yearRange?html}',<#rt/>
	</#if>
	<#if parameters.defaultDate??>
	 	defaultDate: '${parameters.defaultDate?html}',<#rt/>
	</#if>
	<#if parameters.buttonImage??>
	 	buttonImage: '${parameters.buttonImage?html}',<#rt/>
	 	buttonImageOnly: true,
	</#if>
	<#if parameters.type!="date">
	 	<#if parameters.timeFormat??>
		 timeFormat:'${parameters.timeFormat?html}',<#rt/>
		</#if>
	 	showSecond: true,
		hourText:'时',
		timeText:'时间',
		minuteText:'分',
		secondText:'秒',
	</#if>
	<#if parameters.type!="time">
	    monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
	    dayNames:['周日','周一','周二','周三','周四','周五','周六'],
	    dayNamesMin:['日','一','二','三','四','五','六'],
	</#if>
		<#if (parameters.dynamicAttributes?? && parameters.dynamicAttributes?size > 0)><#rt/>
		<#assign aKeys = parameters.dynamicAttributes.keySet()><#rt/>
		<#list aKeys as aKey><#rt/>
		  <#assign keyValue = parameters.dynamicAttributes[aKey]/>
		  <#if keyValue?is_string>
		      <#assign value = struts.translateVariables(keyValue)!keyValue/>
		  <#else>
		      <#assign value = keyValue?string/>
		  </#if>
		 ${aKey}:${value?html},
		</#list><#rt/>
		</#if><#rt/>
		changeMonth:true,
		changeYear:true,
		showMonthAfterYear: true,
		showOn: 'both',
		clearText:'清除',
		closeText:'关闭',
	    prevText:'上月',
	    nextText:'下月',
		currentText:'当前',
		buttonText:'',
		buttonImageOnly:true
		});
});
</script>
