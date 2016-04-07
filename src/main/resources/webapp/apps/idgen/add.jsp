<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/tpl" prefix="t"%>
<%@include file="include.jsp" %>
<t:fragment name="_title">新建ID生成器</t:fragment>
<t:fragment name="_ctlbnt"><a onclick="$('#save').submit()" class="button-main button-icon-save">保存</a></t:fragment>
<t:fragment>
<script>
	var validate_rules={
		'generator.id' : {required:true,rangelength:[1,30]},
		'generator.name' : {required:true,rangelength:[1,50]}
	};
	
	var idx = 1;
	
	function addString(){
		var html = $("<tr><td class='formfeild'>输入固定值#<span class='idx'>"+(idx++)+"：</td><td><input name='string' type='text' class='exp' onchange='make()'/> <a onclick='del(this)'>-</a></td></tr>");
		$('#tab').append(html);
		make();
	}
	function addField(){
		var html = $("<tr><td class='formfeild'>表单元素表达式#<span class='idx'>"+(idx++)+"：</td><td><input name='field' type='text' class='exp' onchange='make()'/> <input type='text' class='exp' name='w' style='width:50px;' onchange='make()'/>位 <a onclick='del(this)'>-</a></td></tr>");
		$('#tab').append(html);
		make();
	}
	function addDate(){
		var html = $("<tr><td class='formfeild'>日期格式#<span class='idx'>"+(idx++)+"：</td><td><select name='date' class='exp' onchange='make()'><option>yyyyMMdd</option><option>MMdd</option><option>yyyyMM</option><option>HHmm</option><option>HH</option><option>yyyyMMddHH</option></select> <a onclick='del(this)'>-</a></td></tr>");
		$('#tab').append(html);
		make();
	}
	function addSeq(){
		var html = $("<tr><td class='formfeild'>变化周期#<span class='idx'>"+(idx++)+"：</td><td><input name='seq' class='exp' type='text' onchange='make()'/> <input type='text' style='width:50px;' class='exp' onchange='make()' name='w'/>位 <a onclick='del(this)'>-</a></td></tr>");
		$('#tab').append(html);
		make();
	}
	
	function make(){
		var exp="";
		var section = "";
		$('.exp').each(function(i,data){
			if(data.name=='w' && data.value){
				exp=exp.substring(0,exp.length-1)+"{"+data.value+"}"+$('#spliter').val();
				section+=data.value+";";
			}else if(data.name=='string'){
				exp += data.value+$('#spliter').val();
				section+="string:"+data.value+";";
			}else if(data.name=='field'){
				exp += "$"+"{"+data.value+"}"+$('#spliter').val();
				section+="field:"+data.value+":";
			}else if(data.name=='date'){
				exp += "$"+"{#date."+data.value+"}"+$('#spliter').val();
				section+="date:"+data.value+";";
			}else if(data.name=='seq'){
				exp += "$"+"{#seq("+data.value+")}"+$('#spliter').val();
				section+="seq:"+data.value+":";
			}
		});
		if(exp.endsWith($('#spliter').val()))
			$('#exp').val(exp.substring(0,exp.length-1));
		else{
			$('#exp').val(exp);
		}
		$('#section').val(section);
	}
	
	function del(index){
		$(index).parent().parent().remove();
		idx = 1;
		$('.idx').each(function(i,data){
			$(data).text(idx++);
		});
		make();
	}
</script>
<style>
<!--
a{cursor: pointer;}
-->
</style>
</t:fragment>
<t:tpl menuId="__idgenerator" param="support=form">
	<s:form action="save" method="post">
		<s:hidden name="generator.section" id="section"/>
		<table class="table-form" id="tab" width=700>
			<tr>
				<td></td>
				<td class="button-subs" style="text-align:left;">
					<a onclick="addString()" class="button">增加一个固定项</a>
					<a onclick="addField()" class="button">增加一个表单项</a>
					<a onclick="addDate()" class="button">增加一个日期项</a>
					<a onclick="addSeq()" class="button">增加一个流水号</a>
				</td>
			</tr>
			<tr>
   				<td class="form-feild">编号</td>
   				<td><s:textfield name="generator.id" /></td>
   			</tr>
			<tr>
   				<td class="form-feild">名称</td>
   				<td><s:textfield name="generator.name" /></td>
   			</tr>
			<tr>
   				<td class="form-feild">备注</td>
   				<td><s:textarea name="generator.description" /></td>
   			</tr>
			<tr>
   				<td class="form-feild">表达式</td>
   				<td><s:textfield id="exp" name="generator.expression" readonly="true"/></td>
   			</tr>
			<tr>
   				<td class="form-feild">分隔符</td>
   				<td><s:select id="spliter" list="#{'-':'-','_':'_','.':'.','~':'~'}" onchange="make()" name="generator.spliter"/></td>
   			</tr>
		</table> 
	</s:form>
</t:tpl>