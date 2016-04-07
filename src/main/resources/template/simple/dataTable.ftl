<table <#rt/>
<#if parameters.cssClass??>
 ${parameters.cssClass?html}<#rt/>
<#else>
 class="table-list"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/css.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" />
>
	<thead>
		<tr class="table-list-head">
			<#if parameters.multiSelect == true>
			<td class="table-select-ctrl" id="sel${parameters.id}" onclick="selAll('.chk${parameters.id}',this)"><div></div></td>
			</#if>
			<#if parameters.showNo == true>
			<td class="table-no">序号</td>
			</#if>
			<@s.iterator value="parameters.headerNames" status="sta">
			<#assign f = stack.findValue('top')/>
			<#assign w = stack.findValue('#sta.index')/>
			<#if parameters.defaultColumns??>
			<#assign dis = parameters.defaultColumns?seq_contains(f)?string("", "style='display:none'")/>
			<#else>
			<#assign dis =""/>
			</#if>
			<td width="${parameters.widths[w]}" ${dis}>${f}</td>
			</@s.iterator>
			<td class="table-setting" onclick="selectTableColumns(this)"><div></div></td>
		</tr>
	</thead>
	<tbody>
		<@s.iterator name="item" value="parameters.value" status="sta">
		<tr class="table-list-data">
			<#if parameters.multiSelect == true>
			<#assign chkvalue = struts.translateVariables(parameters.checkboxValue)/>
			<td><input name="${parameters.checkboxName}" value="${chkvalue}" class="chk chk${parameters.id}" type="checkbox"/></td>
			</#if>
			<#if parameters.showNo == true && parameters.page??>
			<#assign w = stack.findValue('#sta.index')+1 +parameters.offset/>
			<td>${w}</td>
			<#elseif  parameters.showNo == true>
			<#assign w = stack.findValue('#sta.index')+1/>
			<td>${w}</td>
			</#if>
			<@s.iterator value="parameters.fields" status="fta">
			<#assign f = stack.findValue('top')/>
			<#if parameters.defaultColumns??>
			<#assign idx = stack.findValue('#fta.index')/>
			<#assign name =parameters.headerNames[idx]/>
			<#assign dis = parameters.defaultColumns?seq_contains(name)?string("", "style='display:none'")/>
			<#else>
			<#assign dis =""/>
			</#if>
			<td ${dis}><#assign item = struts.translateVariables(f)/>${item}</td>
			</@s.iterator>
			<td></td>
		</tr>
		</@s.iterator>
	</tbody>
</table>
<script>
	if(selAllCheck)
		$('.chk${parameters.id}').bind('click',selAllCheck);
</script>
<#if parameters.page??>
<#include "/${parameters.templateDir}/simple/offsetPage.ftl" />
</#if>