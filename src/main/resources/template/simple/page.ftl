<div class="_page_nav">
	<#if parameters.totalPage&gt;0>
		<span class="_page_desc">共${parameters.total}条${parameters.totalPage}页</span>
		<#if parameters.page&gt;5>
		<#assign from=parameters.page-5>
		<#else>
		<#assign from=1>
		</#if>
		<#if parameters.totalPage&gt;from+9>
		<#assign to=from+9>
		<#else>
		<#assign to=parameters.totalPage>
			<#if to&gt;9>
			<#assign from=to-9>
			<#else>
			<#assign from=1>
			</#if>
		</#if>
		<#if from&gt;1>
		<a  class="_page_page" href="${parameters.queryString?if_exists}page=1">1</a>
		</#if>
		<#if from&gt;2>
		<span class="_page_split">...</span>
		</#if>
		<#list from..to as i>
			<#if i==parameters.page>
			<span class="_page_cur_page">${i?c}</span>
			<#else>
			<a class="_page_page" href="${parameters.queryString?if_exists}page=${i?c}">${i?c}</a>
			</#if>
		</#list>
		<#if to&lt;parameters.totalPage-1>
		<span class="_page_split">...</span>
		</#if>
		<#if to&lt;parameters.totalPage>
		<a class="_page_page" href="${parameters.queryString?if_exists}page=${parameters.totalPage?c}">${parameters.totalPage?c}</a>
		</#if>
		<input id="_topage" type="text" style="width:30px;" onkeydown="return pagego(event,'${parameters.queryString?if_exists}',${parameters.totalPage?c});" onkeyup="pagejudge(this.value,${parameters.totalPage?c})"/>
		<input value="跳转" onclick="gotopage('${parameters.queryString?if_exists}',${parameters.totalPage?c})" type="button">
	<#else>
		<#if parameters.emptyMessage??>
		<span class="_page_desc">${parameters.emptyMessage?html}</span>
		<#else>
		<span class="_page_desc">暂无数据</span>
		</#if>
	</#if>
</div>