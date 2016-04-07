var cnmsg = {  
    required: "请输入信息",   
    remote: "请修正该字段",   
    email: "请输入正确格式的电子邮件",   
    url: "请输入合法的网址",  
    date: "请输入合法的日期",   
    dateISO: "请输入合法的日期 (ISO).",  
    number: "请输入合法的数字",   
    digits: "只能输入整数",   
    creditcard: "请输入合法的信用卡号",   
    equalTo: "请再次输入相同的值",   
    accept: "请输入拥有合法后缀名的字符串",   
    maxlength: jQuery.format("最多是 {0} 个字符"),   
    minlength: jQuery.format("最少是 {0} 个字符"),   
    rangelength: jQuery.format("长度应介于 {0}和 {1}之间"),   
    range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),   
    max: jQuery.format("请输入一个最大为 {0} 的值"),  
    min: jQuery.format("请输入一个最小为 {0} 的值"),
    regex:jQuery.format("数据格式不合法:{0}")
};
jQuery.extend(jQuery.validator.messages, cnmsg); 
jQuery.validator.addMethod("regex", function(value, element,param) {
	return param.test(value);       
});

function jQueryErrorPlacement(error, element) {  
	var div = document.getElementById("errorDiv_"+element.attr("name"));
	if(!div){
		div=document.createElement('div');
		div.id="errorDiv_"+element.attr("name");	
		div.style.visibility="hidden";
		div.style.position="absolute";
		if($(element).parent().css('position')=='relative'){
			div.style.left = (element.position().left)+'px';
			div.style.top = (element.position().top+10+element.height())+'px';
		}else{
			div.style.left = (element.offset().left)+'px';
			div.style.top = (element.offset().top+10+element.height())+'px';
		}
		$(div).appendTo($(element).parent());
		//document.body.appendChild(div);
		div.className="form-tip-error";
		
	}	
	element.mouseover(function(){
		if($(element).parent().css('position')=='relative'){
			div.style.left = (element.position().left)+'px';
			div.style.top = (element.position().top+10+element.height())+'px';
		}else{
			div.style.left = (element.offset().left)+'px';
			div.style.top = (element.offset().top+10+element.height())+'px';
		}
		div.style.visibility="visible";
		div.style.zIndex="10000";
	});
	element.mouseout(function(){
		div.style.visibility="hidden";
	});
	div.innerHTML="";
	error.appendTo(div);    	
	div.style.backgroundPosition= "-"+(485-$(div).width())+"px -"+Math.abs(($(div).height()/2-7))+"px";
}	


function jQuerySuccess(label) {    
	if(label.parent().parent().attr('class') && label.parent().parent().attr('class').indexOf('datetimepicker-container')>=0){
		label.parent().parent().removeClass('error');
	}else{
		label.addClass("valid");
	}
	var div = document.getElementById(label.parent("div").attr("id"));
	$(div).remove()
}    


function jQueryHighlight(element, errorClass) {  
	if($(element).parent().attr('class') && $(element).parent().attr('class').indexOf('datetimepicker-container')>=0){
		$(element).parent().addClass(errorClass);
	}else{
		$(element).addClass(errorClass);
	}
}

function initFormLabel(){
	$(".form-required").each(function(){
		$(this).html($(this).html()+"<span class='form-required-syb'>*</span>");
	})
}

var validate_msg;
var validate_rules;

addOnLoad(function(){
	$('form').validate({   
		messages:validate_msg,
		errorPlacement:jQueryErrorPlacement,
		success:jQuerySuccess,
		highlight:jQueryHighlight
	});
	
	try{
		if(validate_rules==null)
		return;
	}catch(e){
		return;
	}
	
	$('form input[type=text],form input[type=password],form select,form textarea').each(function(i,ele){
		if(validate_rules[ele.name]){
			$(ele).rules('add',validate_rules[ele.name]);
		}
		if(validate_rules[ele.name] && validate_rules[ele.name].required && $(ele).parent().attr('class')=='datetimepicker-container'){
			$(ele).parent().parent().append('<span class="form-required-syb">*</span>');
		}else if(validate_rules[ele.name] && validate_rules[ele.name].required && ele.type!='hidden'){
			$(ele).parent().append('<span class="form-required-syb">*</span>');
		}
	});
});
