	<div class="table-toolbar-container">
		<#if parameters.showSelectButton==true>
		<a class="js-button button-icon-select button-icon-dropdown" id="${parameters.tableId}-selectAll" select="#${parameters.tableId} .chk"></a>
		</#if>
		<span class="button-group">
		<#if parameters.showOrderButton==true>
		<a class="js-button button-icon-dropdown" id="${parameters.tableId}-order">排序</a>
		</#if>
		<#if parameters.showQueryButton==true>
		<a class="js-button button-icon-search" onclick="showQueryForm('${parameters.tableId}-form')">查询</a>
		</#if>
		<#if parameters.showColumnButton==true>
		<a class="js-button button-icon-dropdown" id="${parameters.tableId}-column">筛选列</a>
		</#if>
		</span>
		<#if parameters.buttons??>
		<span class="button-group">
			<@s.iterator value="parameters.buttons" status="sta">
			<#assign btn = stack.findValue('top')/>
			<a class="js-button ${btn['css']}" <#rt/>
			<#if btn['onclick']??>
			onclick="${btn['onclick']}" <#rt/>
			</#if>
			<#if btn['id']??>
			id="${btn['id']}" <#rt/>
			</#if>
			<#if btn['href']??>
			href="${btn['href']}" <#rt/>
			</#if>
			<#if btn['title']??>
			title="${btn['title']}" <#rt/>
			</#if>
			<#if btn['style']??>
			style="${btn['style']}" <#rt/>
			</#if>
			>${btn['name']}</a>
			</@s.iterator>
		</span>
		</#if>
	</div>
	<div>
		<ul id="menu-${parameters.tableId}-selectAll" class="menu">
			<li><a onclick="menuSelectAll('all','#${parameters.tableId} .chk')">全选</a></li>
			<li><a onclick="menuSelectAll('none','#${parameters.tableId} .chk')">不选</a></li>
			<li><a onclick="menuSelectAll('reverse','#${parameters.tableId} .chk')">反选</a></li>
		</ul>
		<#if parameters.showOrderButton==true>
		<ul id="menu-${parameters.tableId}-order" class="menu">
			<@s.iterator value="parameters.orderDesc">
				<#assign f = stack.findValue('top')/>
				<li><a onclick="queryOrder('${parameters.tableId}','${f.method.name }','${f.type}')">${f.name}<#if parameters.ordervalue?? && parameters.orderparam?? && parameters.ordervalue==f.method.name && f.type==parameters.orderparam>√</#if></a>
				
				</li>
			</@s.iterator>
		</ul>
		</#if>
		<#if parameters.showColumnButton==true>
		<ul id="menu-${parameters.tableId}-column" class="menu">
			<@s.iterator value="parameters.headerNames" status="sta">
			<#assign f = stack.findValue('top')/>
			<#assign w = stack.findValue('#sta.index')/>
			<#if parameters.defaultColumns??>
			<#assign dis = parameters.defaultColumns?seq_contains(f)?string("checked='checked'", "")/>
			<#else>
			<#assign dis ="checked='checked'"/>
			</#if>
			<li style="display:block"><input id="menu-${parameters.tableId}-column-${w}" type="checkbox" ${dis} onclick="_showColumn('#${parameters.tableId}',this.checked,${w})"/><label for="menu-${parameters.tableId}-column-${w}">${f}</label></li>
			</@s.iterator>
		</ul>
		</#if>
		<div id="${parameters.tableId}-form" class="dialog">
			<form id="${parameters.tableId}-form-form">
			<input type="hidden" id="${parameters.tableId}_order" name="_order" value="${parameters.ordervalue!""}"/>
			<input type="hidden" id="${parameters.tableId}_orderparam" name="_orderparam"  value="${parameters.orderparam!""}"/>
			<#if parameters.showQueryButton==true>
			<table class="table-form">
			<@s.iterator value="parameters.queryDesc">
				<#assign f = stack.findValue('top')/>
				<#assign values = f.values/>
				<tr><td class="form-field">${f.name}</td><td>
					<#if f.type=='text'>
					<input name="${f.method.name}" type="text" value="${f.value}"/>
					</#if>
					<#if f.type=='date'>
					<div class="datetimepicker-container">
					<input name="${f.method.name}" type="text" class="input-datepicker" value="${f.value}" readonly="readonly"/>
					</div>
					</#if>
					<#if f.type=='datetime'>
					<div class="datetimepicker-container">
					<input name="${f.method.name}" type="text" class="input-datetimepicker" value="${f.value}" readonly="readonly"/>
					</div>
					</#if>
					<#if f.type=='select'>
					<select name="${f.method.name}">
						<@s.iterator value="values" status="sta">
						<#assign opt = stack.findValue('top')/>
						<option value="${opt}" <#rt/>
						<#if f.value==opt>
						selected="selected"
						</#if>
						>${valueNames[sta.index]}</option>
						</@s.iterator>
					</select>
					</#if>
					<#if f.type=='checkbox'>
					<@s.iterator value="values" status="sta">
					<#assign val = stack.findValue('top')/>
					<input name="${f.method.name}" type="checkbox" value="${val}" id="${f.method.name}${sta.index}" <#rt/>
					<#if f.value==val>
						checked="checked"
						</#if>
					/><label for="${f.method.name}${sta.index}">${val}</label>
					</@s.iterator>
					</#if>
					<#if f.type=='boolean'>
					<input name="${f.method.name}" type="checkbox" value="1" <#rt/> 
					<#if f.value=='1'>
						checked="checked"
					</#if>
					/>
					</#if>
				</td></tr>
			</@s.iterator>
				<tr><td></td><td><a  class="js-button" onclick="complexQuery('${parameters.tableId}-form')">查询</a></td></tr>
			</table>
			</#if>
			</form>
		</div>
		
	</div>
<#if parameters.formAction??>
<form action="${parameters.formAction?html}" type="post" name="${parameters.tableId}-data-form" id="${parameters.tableId}-data-form">
</#if>
<table <#rt/>
<#if parameters.cssClass??>
 ${parameters.cssClass?html}<#rt/>
<#else>
 class="table-list"<#rt/>
</#if>
 id="${parameters.tableId}"<#rt/>
<#include "/${parameters.templateDir}/simple/css.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" />
>
	<thead>
		<tr class="table-list-head">
			<#if parameters.multiSelect == true>
			<td class="table-select"></td>
			</#if>
			<#if parameters.showRowNo == true>
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
		</tr>
	</thead>
	<tbody>
		<@s.iterator name="item" value="parameters.value" status="sta">
		<tr class="table-list-data">
			<#if parameters.multiSelect == true>
			<#assign chkvalue = struts.translateVariables(parameters.checkboxValue)/>
			<td><input name="${parameters.checkboxName}" value="${chkvalue}" class="chk" type="checkbox"></td>
			</#if>
			<#if parameters.showRowNo == true && parameters.offset??>
			<#assign no = stack.findValue('#sta.index')+1 +parameters.value.offset/>
			<td>${no}</td>
			<#elseif  parameters.showRowNo == true>
			<#assign no = stack.findValue('#sta.index')+1/>
			<td>${no}</td>
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
		</tr>
		</@s.iterator>
	</tbody>
</table>
<#if parameters.formAction??>
</form>
</#if>
<#if parameters.page??>
<#include "/${parameters.templateDir}/simple/offsetPage.ftl" />
</#if>