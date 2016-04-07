

var onloadFunction = new Array();

function addOnLoad(func){
	onloadFunction[onloadFunction.length]=func;
}

function SetCookie(name,value,expire) {   
    var exp  = new Date();   
    exp.setTime(exp.getTime() + expire);   
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();   
}   
  
function getCookie(name) {   
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));   
    if(arr != null) return unescape(arr[2]); return null;   
}   

function delCookie(name){   
    var exp = new Date();   
    exp.setTime(exp.getTime() - 1);   
    var cval=getCookie(name);   
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();   
}


function f(url,win){
	if(!win)
		document.location=url;
	else
		win.location=url;
}

function r(){
	window.location.reload();
}


function getKeyCode( e ){
	var e = e || window.event;
	return e.keyCode?e.keyCode:e.charCode;
}

function op(url,w,h){
	window.open(url);
}

function del(url,msg){
	Frm.confirm(msg?msg:'确定要删除吗？','提示',function(r){
		if(r){
			f(url);
		}
	});
}

function isRootWindow(){
	return !(parent && parent.location!=window.location);
}

var Frm = function(){
	
}


function pageWidth(){ 
	if(/msie/.test(navigator.userAgent.toLowerCase())){ 
	return document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : 
	document.body.clientWidth; 
	}else{ 
	return self.innerWidth; 
	} 
} 
function pageHeight(){
	if(/msie/.test(navigator.userAgent.toLowerCase())){
		return document.compatMode == "CSS1Compat"? document.documentElement.clientHeight :
			document.body.clientHeight;
	}else{
		return self.innerHeight;
	}
}

var    _intValue   = '0123456789.';
var    _upperValue = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
var    _lowerValue = 'abcdefghijklmnopqrstuvwxyz';
var    _etcValue   = ' ~`!@#$%%^&*()-_=+\|[{]};:\'\",<.>/?';


function replaceString(str,s,r){
	var reg=new RegExp(s,"g"); //创建正则RegExp对象   
	return str.replace(reg,r);    
}

/**判断是否大写
 * 
 */
function IsUpper(value) {    
    var   i;
    for(i=0;i<_upperValue.length;i++)
        if(value == _upperValue.charAt(i)) {
            return true;
        }
    return false;
}
//是否小写
function IsLower(value) {    
    var   i;
    for(i=0;i<_lowerValue.length;i++)
        if(value == _lowerValue.charAt(i)) {
            return true;
        }
    return false;
}
// 是否整数
function IsInt(value) {    
	var   re =/^\d+$/;
	return re.test(''+value);

}
// 是否特殊符号
function IsEtc(value) {    
    var   j;
    for(j=0;j<_etcValue.length;j++)
        if(value == _etcValue.charAt(j)) {
            return true;
        }
    return false;
}

// 是否整数
function IsIntStr(no) {    
    var    i;
    var    str = null;

    str = new String(no);

    if(str==null || str.length == 0)
        return false;

    for(i=0;i<str.length;i++)
        if(!IsInt(str.charAt(i)))
            return false;
    return true;
}

// left trim
function ltrim(para)
{
    while(para.substring(0,1) == ' ')
        para = para.substring(1, para.length);
    return para;
}

// right trim
function rtrim(para)
{
    while(para.substring(para.length-1,1) == ' ')
        para = para.substring(0, para.length-1);
    return para;
}
// trim all
function trim(para)
{
    return rtrim(ltrim(para));
}

//
function GetByte(str) {    
    var    i, ch, bytes;
    var    app, isNe=0;

    if(str=='')
        return 0;
    app = navigator.appName;
    if(app == 'Netscape')
        isNe = 1;
    
    for(i=0, bytes=0;i<str.length;i++) {
        ch = str.charAt(i);
 
        if(IsInt(ch))
            bytes++;
        else if(IsLower(ch))
            bytes++;
        else if(IsUpper(ch))
            bytes++;
        else if(IsEtc(ch))
            bytes++;
        else {
            bytes += 2;
            if(isNe)
                i++;
        }
    }
    return bytes;
}

// ��Ʈ�� -> �޸���Ʈ�� 
function Comma(input) {
    var inputString = new String;
    var outputString = new String;
    var counter = 0;
    var decimalPoint = 0;
    var end =0;

    inputString=input.toString();
    outputString='';
    decimalPoint = inputString.indexOf('.', 1);
    if(decimalPoint == -1) {
        end = inputString.length;
        for(counter=1;counter<=inputString.length; counter++) {
            outputString = (counter%3==0  && counter<end ? ',':'')+
                           inputString.charAt(inputString.length-counter)+
                           outputString;
        }
    }
    else {
        end = decimalPoint - (inputString.charAt(0)=='-' ? 1:0);
        for(counter=1;counter<=decimalPoint;counter++) {
            outputString=(counter%3==0&&counter<end ? ',':'')+
                         inputString.charAt(decimalPoint-counter)+
                         outputString;
        }
        for(counter=decimalPoint; counter < decimalPoint+3; counter++) {
            outputString += inputString.charAt(counter);
        }
    }
    return (outputString);
}
// �޸���Ʈ�� -> ��Ʈ�� 
function UnComma(input) {
    var    inputString  = new String;
    var    outputString = new String;
    var    outputNumber = new Number;
    var    counter = 0;

    inputString  = input;
    outputString = '';

    for(counter=0;counter<inputString.length;counter++) {
        outputString += (inputString.charAt(counter) != ',' ?
                         inputString.charAt(counter) : '');
    }
    outputNumber = parseFloat(outputString);
    return (outputNumber);
}

/******** ��¥ ��� �ڹٽ�ũ��Ʈ **********/

// ����üũ 
function IsLeapYear(year) {     
    if(year % 4 == 0) {
        if(year % 100 == 0) {
            if(year % 400 == 0)
                return true;
            return false; 
        }
        else
            return true;
    }
    return false;
}

// ��¥ ���� 
function isDD( yyyy, mm,dd)
{
	var result = false;
	var monthDD = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	if ( mm.length != 2 || !IsIntStr(mm)  ) return false;
	if ( mm > 12 || mm < 1) return false;

	var im = eval(mm) - 1;
	if ( dd.length != 2 || !IsIntStr(dd) ) return false;
	if ( IsLeapYear(yyyy))	monthDD[1] = 29;
	//var dd = eval(value);
	if ( (0 < dd) && (dd <= monthDD[im]) ) result = true;
	return result;
}

// Month ���� 
function isMM( value )
{
	return( (value.length > 0) && (IsIntStr(value) ) && (0 < eval(value)) && (eval(value) < 13) );
}
// YEAR ���� 
function isYYYY(value)
{
	return( (value.length == 4) && (IsIntStr(value)) && (value != "0000") );
}
// DATE ���� 
function isDate( value )
{
	if (GetByte(value)!=8 || !IsIntStr(value)) return false;
	
	var    year  = value.substring( 0,4 );
	var    month = value.substring( 4,6 );
	var    day   = value.substring( 6,8 );
	return( isYYYY(year) && isMM(month) && isDD(year,month,day) );
}    


/******** �� ��� �ڹٽ�ũ��Ʈ **********/

// ���̵� üũ 
function CheckId(id)
{ 
  var alphaCnt, bytes; 
  var i; 
  alphaCnt = 0; 
  
  var alphaCnt, bytes; 
  var i; 
  alphaCnt = 0; 
  if (GetByte(id) > 20 || GetByte(id) < 5) return false ; 
  if(id.length == 0) 
  { 
     alphaCnt = 0 ; 
     bytes = 0 ; 
  } 
  else 
  { 
    for (i = 0, bytes=0; i < id.length; i++) 
    { 
      if(IsUpper(id.charAt(i)) || IsLower(id.charAt(i)) || IsInt(id.charAt(i))) 
         alphaCnt++; 
      if(IsEtc(id.charAt(i))) 
         bytes++; 
    } 
  } 
  if (alphaCnt == 0) return false; 
  if (bytes > 0) return false; 
  return true; 
} 

// �н����� üũ 
function CheckPwd(pwd) 
{
    var alphaCnt;
    var intCnt;
    var i;

    intCnt = 0;
    alphaCnt = 0;

    if (GetByte(pwd) < 5 || GetByte(pwd) > 10) return false;

    for (i = 0; i < pwd.length; i++) 
    {
        if(IsUpper(pwd.charAt(i)) || IsLower(pwd.charAt(i)))       
            alphaCnt++;
        if(IsInt(pwd.charAt(i)))       
            intCnt++;
    }

    if (intCnt == 0 || alphaCnt == 0) return false;
    return true;
}

// �ֹε�Ϲ�ȣ ��ȿüũ	    
function  ResidentNO_Check(para) {

	var IDAdd = "234567892345";
    var i=0 ; 
    var iDtot=0 ;
    var IDTemp ="";     
            
    if (para.length!= 13 || !IsIntStr(para))  return(false);
       
    for (i=0 ; i<12 ; i ++) 
		iDtot = iDtot + para.substr( i, 1) * IDAdd.substr( i, 1);
        
    iDtot = 11 - (iDtot % 11);
    
    if(iDtot == 10) {
        iDtot = 0;
    } else if(iDtot == 11) {
        iDtot = 1;
    }

    if (para.substr(12, 1)== iDtot) {
    	return(true);
    }  else {
    	return(false);
    }
}

// E-mail �ּ� üũ �Լ� 
function CheckEmail(email_addr) {
	if (email_addr == "") return false;
	
	var t = email_addr;
	
	var Alpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	var Digit = '1234567890';
	var Symbol='_-';
	var check = '@.' + Alpha + Digit + Symbol;
	
	for (i=0; i < t.length; i++)
	   if(check.indexOf(t.substring(i,i+1)) < 0) 	{
		return false;
		}
	var check = '@';
	var a = 0;
	for (i=0; i < t.length; i++)
	  if(check.indexOf(t.substring(i,i+1)) >= 0) 	a = i;
	
	var check = '.';
	var b = 0;
	
	for (i=a+1; i < t.length; i++)
		if(check.indexOf(t.substring(i,i+1)) >= 0) 	b = i;

	if (a != 0 && b != 0 && b!=t.length-1 ) {
		return true;
	} else {
		return false;
	}
}

// ��ȭ��ȣ�� ������ ���� ���� üũ 
function IsTelChar(value) {  
    var   j;
    for(j=0;j<_intValue.length;j++)
        if(value == _intValue.charAt(j) || value == '-') 
            return true;
    return false;
}
// ��ȭ��ȣ üũ 
function IsTel(tel) {    
    var    i;
    for(i=0;i<tel.length;i++)
        if(!IsTelChar(tel.charAt(i)))
            return false;
    return true;
}

//����� ��Ϲ�ȣ üũ
function Check_Co_No(strNo) {
	
	if(IsIntStr(strNo)==false) return false;

	IDtot = 0;
	IDAdd = "137137135";
	
	for(i=0; i<9 ; i++) 
		IDtot = IDtot + strNo.charAt(i) * IDAdd.charAt(i);

	IDtot = IDtot + Math.floor(( strNo.charAt(8) * 5) / 10);
	IDtot = 10 - (IDtot%10);
		
	if (eval(strNo.charAt(9))== IDtot) 
		return true;
	else
		return false;

}
