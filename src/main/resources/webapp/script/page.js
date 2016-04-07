function gotopage(queryString,total){
	var p = document.getElementById('_topage').value;
	if(!p){
		Frm.alert('请输入要跳转的页码');
		return;
	}
	if(!IsInt(p)){
		Frm.alert('页码超出有效范围');
		return;
	}
	var n = parseInt(p,10);
	if(n<1 || n>total){
		Frm.alert('页码超出有效范围');
		return;
	}
	f(queryString+'page='+p);
	return false;
}
function gotoOffsetpage(queryString,total,limit){
	var p = document.getElementById('_topage').value;
	if(!p){
		Frm.alert('请输入要跳转的页码');
		return;
	}
	if(!IsInt(p)){
		Frm.alert('页码超出有效范围');
		return;
	}
	var n = parseInt(p,10);
	if(n<1 || n>total){
		Frm.alert('页码超出有效范围');
		return;
	}
	f(queryString+'page='+p);
	f(queryString+'offset='+((p-1)*limit));
	return false;
}
function pagego(e,queryString,total){
	var keynum;
	if(window.event) // IE
	{
	  keynum = e.keyCode
	}
	else if(e.which) // Netscape/Firefox/Opera
	{
	  keynum = e.which
	}
	if(keynum ==13){
		gotopage(queryString,total);
		return false;
	}
}

function offsetPagego(e,queryString,total,limit){
	var keynum;
	if(window.event) // IE
	{
	  keynum = e.keyCode
	}
	else if(e.which) // Netscape/Firefox/Opera
	{
	  keynum = e.which
	}
	if(keynum ==13){
		gotoOffsetpage(queryString,total,limit);
		return false;
	}
}

function pagejudge(p,total){
	if(!IsInt(p)){
		Frm.alert('页码超出有效范围');
		return;
	}
	var n = parseInt(p,10);
	if(n<1 || n>total){
		Frm.alert('页码超出有效范围');
		return;
	}
}