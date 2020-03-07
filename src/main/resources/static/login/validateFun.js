
//document.oncontextmenu = rightclick;
/*******************************************************************************
 * Function: rightclick Description: 网页禁用右键 Input: 无 Output: 无 Return: bool:
 * true false
 ******************************************************************************/
function rightclick()
{
	return false;
}
// window.onerror = killerrors;
/*******************************************************************************
 * Function: killerrors Description: 屏蔽网页报错 Input: 无 Output: 无 Return: bool:
 * true false
 ******************************************************************************/
function killerrors()
{ 
	return true; 
} 
/*******************************************************************************
 * Function: CheckKeyDown Description: 输入时按下空格时，不允许输入 Input: 无 Output: 无 return:
 * 无
 ******************************************************************************/
function CheckKeyDown()
{  
	if(event.keyCode == 32)   
  {
  	event.returnValue = false;   
		return;
   }
}
/*******************************************************************************
 * Function: JudgeTextLength Description: 计算字符串的长度 Input: szString ：元素 Output: 无
 * return: 无
 ******************************************************************************/
function JudgeTextLength(szString)
{
	var  iLength = 0;   
 	for(var i=0; i<szString.length; i++)   
 	{   
		if(szString.charCodeAt(i)>255)   
	  	{   
			iLength+=2;   
	  	}   
	  	else   
	  	{   
	    	iLength+=1;   
	  	}        
 	}   
 	return  iLength;    
}


/*******************************************************************************
 * Function: CheckString Description: 检查字符是否合法 Input: strInfo:传入的参数 Id:输入框ID
 * Output: 无 Return: bool:true false
 ******************************************************************************/
function CheckString(strInfo,strTips,iNull)
{
	
	if(iNull == 0 || iNull == 2)
	{
		if(strInfo == "")
		{
			alert(strTips+"不能为空");
			return false;			
		}
	}
	
	var forbidChar = new Array("'",":",";","*","?","<",">","|", "/", "%","\\",'"', "\""); // 包含特殊字符时提示
	for(var i = 0;i < forbidChar.length ; i++)
	{ 
		if(strInfo.indexOf(forbidChar[i]) >= 0)
		{ 
			alert(strTips+"不能含有字符 / \\ : ; * ? ' \" < > | % ");
    		return false;
	  	} 
	}   
	if(iNull == 2)
	{
		return true;
	}
	return true;
}
/*******************************************************************************
 * Function: CheckNum Description: 检查是否全部是数字 Input: _str：输入的字符串 Output: 无
 * Return: 是否是数字 0表示是 -1表示空 -2表示不是
 ******************************************************************************/
function CheckNum(_str)
{
if(_str =="")
{
	  return -1;
}
var iret = /^[0-9]*$/.test(_str);
if(iret == false)
{
    return -2;
}
return 0
}
/*******************************************************************************
 * Function: document.onkeydown Description: 按回车键登录 Input: 无 Output: 无 Return:
 * bool true - 成功 false - 失败
 ******************************************************************************/
function  Shielding()   
{   
	 // 屏蔽ESC
	if((event.keyCode == 27))
	{   
		event.keyCode = 0;   
      event.returnValue = false;   
	} 
}
/*******************************************************************************
 * Function: in_array Description: 为 Javascript 数组添加一个 inArray 方法 Input: 无
 * Output: 无 Return: bool true - 成功 false - 失败
 ******************************************************************************/
function in_array(needle, haystack) 
{
	// 得到needle的类型
	var type = typeof needle;
	if(type == 'string' || type =='number') 
	{
      for(var i in haystack) 
		{
          if(haystack[i] == needle) 
     		{
          	return true;
			}
		}
	}
	return false;
}
/*******************************************************************************
 * Function: CheckArrayRepeat Description: 判断数组里面是否有重复元素 Input: szArr: 数组
 * Output: 无 Return: 无
 ******************************************************************************/
function CheckArrayRepeat(szArr)   
{ 
	var hash = {}; 
	for(var i in szArr) { 
		if(hash[szArr[i]]) 
			return true; 
		hash[szArr[i]] = true; 
	} 
	return false;
} 
/*******************************************************************************
 * Function: ArraycheckItem Description: 判断数组里面是否有重复元素 Input: receiveArray: 数组
 * checkItem: 元素 Output: 无 Return: 无
 ******************************************************************************/
function ArraycheckItem(receiveArray, checkItem) {
    var index = -1;// 函数返回值用于布尔判断
    for (var i = 0; i < receiveArray.length; i++) {
        if (receiveArray[i] == checkItem) {
            index = i;
            break;
        }
    }
    return index;
}
/*******************************************************************************
 * Function: f_check_number Description: 判断是否为数字， Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/
function f_check_number(str)    
{           
  if (/^\d+$/.test(str)){    
     return true;    
  }     
  else     
  {    
     f_alert(str,"请输入数字");    
     return true;    
  }    
}    

/*******************************************************************************
 * Function: f_check_naturalnumber Description: 判断是否为自然数， Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/
function f_check_naturalnumber(str)    
{           
  var s = str;    
  if (/^[0-9]+$/.test( s ) && (s > 0))    
  {    
     return true;    
  }     
  else     
  {    
      f_alert(str,"请输入自然数");    
      return false;    
  }    
}  


/*******************************************************************************
 * Function: f_check_integer Description: 判断是否为整数 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/

function f_check_integer(str)    
{           
  if (/^(\+|-)?\d+$/.test( str ))     
  {    
     return true;    
  }     
  else     
  {    
      f_alert(str,"请输入整数");    
      return false;    
  }    
}    

/*******************************************************************************
 * Function: f_check_float Description: 判断是否为实数 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/
function f_check_float(str)    
{           
  if (/^(\+|-)?\d+($|\.\d+$)/.test( str ))     
  {    
     return true;    
  }     
  else     
  {    
      f_alert(str,"请输入实数");    
     return false;    
  }    
}    
/*******************************************************************************
 * Function: f_check_zh Description: 检查输入字符串是否只由汉字组成 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_zh(str){    
  if (/^[\u4e00-\u9fa5]+$/.test(str)) {    
    return true;    
  }    
  f_alert(str,"请输入汉字");    
  return false;    
}    

/*******************************************************************************
 * Function: f_check_lowercase Description: 判断是否为小写英文字母 Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_lowercase(str)    
{           
  if (/^[a-z]+$/.test( str ))     
  {    
     return true;    
  }     
  f_alert(str,"请输入小写英文字母");    
  return false;    
}    

/*******************************************************************************
 * Function: f_check_uppercase Description: 判断是否为大写英文字母 Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_uppercase(str)    
{           
  if (/^[A-Z]+$/.test( str ))     
  {    
     return true;    
  }     
  f_alert(str,"请输入大写英文字母");    
  return false;    
}    

/*******************************************************************************
 * Function: f_check_letter Description: 判断是否为英文字母 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_letter(str)    
{           
  if (/^[A-Za-z]+$/.test( str ))     
  {    
     return true;    
  }     
  f_alert(str,"请输入英文字母");    
  return false;    
}    



/*******************************************************************************
 * Function: f_check_ZhOrNumOrLett Description: 检查输入字符串是否只由汉字、字母、数字组成 Input:
 * str：输入的字符串 Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_ZhOrNumOrLett(str){    // 判断是否是汉字、字母、数字组成
  var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";       
  var re = new RegExp(regu);    
  if (re.test( str )) {    
    return true;    
  }    
  f_alert(str,"请输入汉字、字母或数字");    
  return false;    
}    

/*******************************************************************************
 * Function: f_check_IP Description: 校验ip地址的格式 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_IP(str)     
{     
  var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/; // 匹配IP地址的正则表达式
  if(re.test( str ))    
  {    
      if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;    
  }    
  f_alert(str,"请输入合法的计算机IP地址");    
  return false;     
}    

/*******************************************************************************
 * Function: f_check_port Description: 检查输入对象的值是否符合端口号格式 Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/  
function f_check_port(str)    
{    
  if(!f_check_number(str))    
      return false;    
  if(str < 65536)    
      return true;    
  f_alert(str,"请输入合法的计算机IP地址端口号");    
  return false;     
}    


/*******************************************************************************
 * Function: f_check_URL Description: 检查输入对象的值是否符合网址格式 Input: str：输入的字符串 Output:
 * 无 Return: 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_URL(str){    
  var myReg = /^((http:[/][/])?\w+([.]\w+|[/]\w*)*)?$/;     
  if(myReg.test(str)) return true;     
    alert(str);
	f_alert(str,"请输入合法的网页地址");    
  return false;     
}    

/*******************************************************************************
 * Function: f_check_email Description: 检查输入对象的值是否符合E-Mail格式 Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/   

function f_check_email(str){     
  var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;      
  if(myReg.test(str)){
		return true;
	}    
  f_alert(str,"请输入合法的电子邮件地址");    
  return false;     
}    

/*******************************************************************************
 * Function: f_check_mobile Description:
 * 检查输入手机号码是否正确(一、移动电话号码为11或12位，如果为12位,那么第一位为0 二、11位移动电话号码的第一位和第二位为"13"
 * 三、12位移动电话号码的第二位和第三位为"13") Input: str：输入的字符串 Output: 无 Return:
 * 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_mobile(str){       
  var regu =/(^[1][0-9][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;    
  var re = new RegExp(regu);    
  if (re.test( str )) {    
    return true;    
  }    
  f_alert(str,"请输入正确的手机号码");    
  return false;       
}    
/*******************************************************************************
 * Function: f_check_phone Description: 检查输入的电话号码格式是否正确 (一、电话号码由数字、"("、")"和"-"构成
 * 二、电话号码为3到8位 三、如果电话号码中包含有区号，那么区号为三位或四位 四、区号用"("、")"或"-"和其他部分隔开 ) Input:
 * str：输入的字符串 Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/   


function f_check_phone(str)     
{    
  var regu =/(^([0][1-9]{2,3}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0][1-9]{2,3}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/;     
  var re = new RegExp(regu);    
  if (re.test( str )) {    
    return true;    
  }    
  f_alert(str,"请输入正确的电话号码");    
  return false;    
}    

/*******************************************************************************
 * Function: f_check_zipcode Description: 判断是否为邮政编码 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/   

function f_check_zipcode(str)    
{    
  if(!f_check_number(str))    
      return false;    
  if(str.length!=6)    
  {    
      f_alert(str,"邮政编码长度必须是6位");    
      return false;    
  }    
  return true;    
}    

/*******************************************************************************
 * Function: f_check_userID Description: 用户ID，可以为数字、字母、下划线的组合，
 * 第一个字符不能为数字,且总长度不能超过20。 Input: str：输入的字符串 Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/   


function f_check_userID(str)    
{    
  var userID = str;    
  if(userID.length > 20)    
  {    
      f_alert(str,"ID长度不能大于20");    
      return false;    
  }    
 
  if(!isNaN(userID.charAt(0)))    
  {    
      f_alert(str,"ID第一个字符不能为数字");    
      return false;    
  }    
  if(!/^\w{1,20}$/.test(userID))     
  {    
      f_alert(str,"ID只能由数字、字母、下划线组合而成");    
      return false;    
  }    
  return true;    
}    



/*******************************************************************************
 * Function: f_check_IDno Description: 验证身份证号码是否有效 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_IDno(str)    
{     
  var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};    
   
  var iSum = 0;    
  var info = "";    
  var strIDno = str;    
  var idCardLength = strIDno.length;      
  if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno))     
  {    
      f_alert(str,"非法身份证号");    
      return false;    
  }    
   
  // 在后面的运算中x相当于数字10,所以转换成a
  strIDno = strIDno.replace(/x$/i,"a");    
 
  if(aCity[parseInt(strIDno.substr(0,2))]==null)    
  {    
      f_alert(str,"非法地区");    
      return false;    
  }    
      
  if (idCardLength==18)    
  {    
      sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));    
      var d = new Date(sBirthday.replace(/-/g,"/"))    
      if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))    
      {           
          f_alert(str,"非法生日");    
          return false;    
      }    
 
      for(var i = 17;i>=0;i --)    
          iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);    
 
      if(iSum%11!=1)    
      {    
          f_alert(str,"非法身份证号");    
          return false;    
      }    
  }    
  else if (idCardLength==15)    
  {    
      sBirthday = "19" + strIDno.substr(6,2) + "-" + Number(strIDno.substr(8,2)) + "-" + Number(strIDno.substr(10,2));    
      var d = new Date(sBirthday.replace(/-/g,"/"))    
      var dd = d.getFullYear().toString() + "-" + (d.getMonth()+1) + "-" + d.getDate();       
      if(sBirthday != dd)    
      {    
          f_alert(str,"非法生日");    
          return false;    
      }    
  }    
  return true;     
}    
/*******************************************************************************
 * Function: isIdCardNo Description: 身份证号码验证-支持新的带x身份证 Input: str：输入的字符串 Output:
 * 无 Return: 是则返回true,否则返回false
 ******************************************************************************/     
function isIdCardNo(num) {
   var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
   var error;
   var varArray = new Array();
   var intValue;
   var lngProduct = 0;
   var intCheckDigit;
   var intStrLen = num.length;
   var idNumber = num;    
   if ((intStrLen != 15) && (intStrLen != 18)) {
      return false;
   }    
   for(i=0;i<intStrLen;i++) {
       varArray[i] = idNumber.charAt(i);
       if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
           return false;
       } else if (i < 17) {
      	 varArray[i] = varArray[i]*factorArr[i];
       }
   }
   if (intStrLen == 18) {
       // check date
       var date8 = idNumber.substring(6,14);
       if (checkDate(idNumber,true) == false) {
          return false;
       }       
       for(i=0;i<17;i++) {
           lngProduct = lngProduct + varArray[i];
       }        
      intCheckDigit = 12 - lngProduct % 11;
       switch (intCheckDigit) {
           case 10:
               intCheckDigit = 'X';
               break;
           case 11:
               intCheckDigit = 0;
               break;
           case 12:
               intCheckDigit = 1;
               break;
       }        
   }else{        // length is 15
       var date6 = idNumber.substring(6,12);
       date6 = '19'+date6;
       if (checkDate(idNumber,false) == false) {
           return false;
       }
   }
   return true;
}
function checkDate(idcard,is18){
	 var ereg;
	 if(is18){
		 if (parseInt(idcard.substring(6,10)) % 4 == 0 || (parseInt(idcard.substring(6,10)) % 100 == 0 && parseInt(idcard.substring(6,10))%4 == 0 )){     
			 ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
		  }else {    
			  ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
		  } 
	 }else{
		    if ( (parseInt(idcard.substr(6,8))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,8))+1900) % 100 == 0 && (parseInt(idcard.substr(6,8))+1900) % 4 == 0 )){    
			 ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			 } else {    
			 ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			 }   
	 }
	 if(ereg.test(idcard)){
		 return true;
	 }else{
		 return false; 
	 }
}
function checkIdcard(idcard){ 
	var Errors=new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!" ); 
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}  
	var idcard,Y,JYM; 
	var S,M; 
	var idcard_array = new Array(); 
	idcard_array = idcard.split(""); 
	// 地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
	// 身份号码位数及格式检验
	switch(idcard.length){ 
		case 15: 
			if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			} else { 
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			} 
			if(ereg.test(idcard)) return Errors[0]; 
			else return Errors[2]; 
		break; 
		case 18: 
			// 18位身份号码检测
			// 出生日期的合法性检查
			// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
			} else { 
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
			} 
			if(ereg.test(idcard)){// 测试出生日期的合法性
				// 计算校验位
// S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
// + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
// + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
// + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
// + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
// + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
// + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
// + parseInt(idcard_array[7]) * 1
// + parseInt(idcard_array[8]) * 6
// + parseInt(idcard_array[9]) * 3 ;
// Y = S % 11;
// M = "F";
// JYM = "10X98765432";
// M = JYM.substr(Y,1);//判断校验位
// if(M == idcard_array[17]) return Errors[0]; //检测ID的校验位
// else return Errors[3];
				return Errors[0];
			}else return Errors[2]; 
			break; 
		default:return Errors[1]; 
		break; 
	} 
} 
/*******************************************************************************
 * Function: f_check_date Description:
 * 判断是否为日期(格式:yyyy年MM月dd日,yyyy-MM-dd,yyyy/MM/dd,yyyyMMdd) Input: str：输入的字符串
 * Output: 无 Return: 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_date(str)    
{    
  var date = Trim(str);    
  var dtype = str.eos_datatype;    
  var format = dtype.substring(dtype.indexOf("(")+1,dtype.indexOf(")")); // 日期格式
  var year,month,day,datePat,matchArray;    
 
  if(/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format))    
      datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/;    
  else if(/^(y{4})(年)(M{1,2})(月)(d{1,2})(日)$/.test(format))    
      datePat = /^(\d{4})年(\d{1,2})月(\d{1,2})日$/;    
  else if(format=="yyyyMMdd")    
      datePat = /^(\d{4})(\d{2})(\d{2})$/;    
  else   
  {    
      f_alert(str,"日期格式不对");    
      return false;    
  }    
  matchArray = date.match(datePat);    
  if(matchArray == null)     
  {    
      f_alert(str,"日期长度不对,或日期中有非数字符号");    
      return false;    
  }    
  if(/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format))    
  {    
      year = matchArray[1];    
      month = matchArray[3];    
      day = matchArray[4];    
  } else   
  {    
      year = matchArray[1];    
      month = matchArray[2];    
      day = matchArray[3];    
  }    
  if (month < 1 || month > 12)    
  {                 
      f_alert(str,"月份应该为1到12的整数");    
      return false;    
  }    
  if (day < 1 || day > 31)    
  {    
      f_alert(str,"每个月的天数应该为1到31的整数");    
      return false;    
  }         
  if ((month==4 || month==6 || month==9 || month==11) && day==31)    
  {    
      f_alert(str,"该月不存在31号");    
      return false;    
  }         
  if (month==2)    
  {    
      var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));    
      if (day>29)    
      {                   
          f_alert(str,"2月最多有29天");    
          return false;    
      }    
      if ((day==29) && (!isleap))    
      {                   
          f_alert(str,"闰年2月才有29天");    
          return false;    
      }    
  }    
  return true;    
}    

/*******************************************************************************
 * Function: f_check_time Description: 校验的格式为yyyy年MM月dd日HH时mm分ss秒,yyyy-MM-dd
 * HH:mm:ss,yyyy/MM/dd HH:mm:ss,yyyyMMddHHmmss Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/   
function f_check_time(str)    
{    
  var time = Trim(str);    
  var dtype = str.eos_datatype;    
  var format = dtype.substring(dtype.indexOf("(")+1,dtype.indexOf(")")); // 日期格式
  var datePat,matchArray,year,month,day,hour,minute,second;    
 
  if(/^(y{4})(-|\/)(M{1,2})\2(d{1,2}) (HH:mm:ss)$/.test(format))    
      datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;    
  else if(/^(y{4})(年)(M{1,2})(月)(d{1,2})(日)(HH时mm分ss秒)$/.test(format))    
      datePat = /^(\d{4})年(\d{1,2})月(\d{1,2})日(\d{1,2})时(\d{1,2})分(\d{1,2})秒$/;    
  else if(format == "yyyyMMddHHmmss")    
      datePat = /^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/;    
  else   
  {    
      f_alert(str,"日期格式不对");    
      return false;    
  }    
  matchArray = time.match(datePat);    
  if(matchArray == null)     
  {    
      f_alert(str,"日期长度不对,或日期中有非数字符号");    
      return false;    
  }    
  if(/^(y{4})(-|\/)(M{1,2})\2(d{1,2}) (HH:mm:ss)$/.test(format))    
  {    
      year = matchArray[1];    
      month = matchArray[3];    
      day = matchArray[4];    
      hour = matchArray[5];    
      minute = matchArray[6];    
      second = matchArray[7];    
  } else   
  {    
      year = matchArray[1];    
      month = matchArray[2];    
      day = matchArray[3];    
      hour = matchArray[4];    
      minute = matchArray[5];    
      second = matchArray[6];    
  }    
  if (month < 1 || month > 12)    
  {                 
      f_alert(str,"月份应该为1到12的整数");    
      return false;    
  }    
  if (day < 1 || day > 31)    
  {               
      f_alert(str,"每个月的天数应该为1到31的整数");    
      return false;    
  }         
  if ((month==4 || month==6 || month==9 || month==11) && day==31)    
  {             
      f_alert(str,"该月不存在31号");    
      return false;    
  }         
  if (month==2)    
  {    
      var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));    
      if (day>29)    
      {                   
          f_alert(str,"2月最多有29天");    
          return false;    
      }    
      if ((day==29) && (!isleap))    
      {                   
          f_alert(str,"闰年2月才有29天");    
          return false;    
      }    
  }    
  if(hour<0 || hour>23)    
  {    
      f_alert(str,"小时应该是0到23的整数");    
      return false;    
  }    
  if(minute<0 || minute>59)    
  {    
      f_alert(str,"分应该是0到59的整数");    
      return false;    
  }    
  if(second<0 || second>59)    
  {    
      f_alert(str,"秒应该是0到59的整数");    
      return false;    
  }    
  return true;    
}    
/*******************************************************************************
 * Function: isVisible Description: 判断当前对象是否可见 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/   
function isVisible(str){    
  var visAtt,disAtt;    
  try{    
      disAtt=str.style.display;    
      visAtt=str.style.visibility;    
  }catch(e){}    
  if(disAtt=="none" || visAtt=="hidden")    
      return false;    
  return true;    
}    

/*******************************************************************************
 * Function: checkPrVis Description: 判断当前对象及其父对象是否可见 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/
function checkPrVis(str){    
  var pr=str.parentNode;    
  do{    
      if(pr == undefined || pr == "undefined") return true;    
      else{    
          if(!isVisible(pr)) return false;    
      }    
  }while(pr=pr.parentNode);    
  return true;    
}    

/*******************************************************************************
 * Function: f_alert Description: 弹出警告对话框 Input: str：输入的字符串 Output: 无 Return:
 ******************************************************************************/

function f_alert(str,alertInfo){    
  alert(str + "：" + alertInfo + "！");     
 
}    

/*******************************************************************************
 * Function: isNullOrEmpty Description: 检测字符串是否为空 Input: str：输入的字符串 Output: 无
 * Return: 是则返回true,否则返回false
 ******************************************************************************/

function isNullOrEmpty(str)    
{    
  str = Trim(str);    
  if(str.length == 0) {   
      return true;    
  } 
  else { 
      return false 
  } 
}  

/*******************************************************************************
 * Function: LTrim Description: 去左空格 Input: str：输入的字符串 Output: 无 Return:
 ******************************************************************************/
function LTrim(str){ 
	return str.replace(/^\s*/g,""); 
} 
/*******************************************************************************
 * Function: CheckArrayRepeat Description: 去右空格 Input: str：输入的字符串 Output: 无
 * Return:
 ******************************************************************************/
function RTrim(str){ 
	return str.replace(/\s*$/g,""); 
}
/*******************************************************************************
 * Function: Trim Description: 去首尾空格 Input: str：输入的字符串 Output: 无 Return:
 ******************************************************************************/
function Trim(str){ 
	return str.replace(/^\s*|\s*$/g,""); 
}

// get parameter value from 'get' url, strurl: window.parent.location.href or
// window.location.href, para: keyName
function getPara(para, strurl){ 
if (strurl.indexOf("?") == -1) { 
 // Do nothing.
 return null; 
} 
else { 
 // get the string after '?'
 var urlQuery = strurl.split("?"); 
 if(urlQuery[1].indexOf("&")==-1){// only one parameter
  if (urlQuery[1].indexOf("=") == -1) { 
   // no parameter value
   // Do nothing
   return null; 
  } 
  else{ 
   var keyValue = urlQuery[1].split("="); 
   var key = keyValue[0]; 
   var value = keyValue[1]; 
   if(key==para){ 
    return value; 
   } 
  } 
 } 
 else{ 
  // analyse parameters
  var urlTerms = urlQuery[1].split("&"); 
  for (var i = 0; i < urlTerms.length; i++){ 
   var keyValue = urlTerms[i].split("="); 
   var key = keyValue[0]; 
   var value = keyValue[1]; 
   if(key==para){ 
    return value; 
   } 
  } 
 } 
} 
return null; 
}


/*******************************************************************************
 * Function: Trim Description: 1)去除字符串前后所有空格 2)去除字符串中所有空格(包括中间空格,需要设置第2个参数为:g)
 * Input: str：输入的字符串 Output: 无 Return:
 ******************************************************************************/
function Trim(str,is_global) { 
	var result; 
	result = str.replace(/(^\s+)|(\s+$)/g,""); 
	if(is_global.toLowerCase()=="g") 
	result = result.replace(/\s/g,""); 
	return result; 
}
/*******************************************************************************
 * Function: setCooki Description: 设置cookie Input: cookie_id：cookie 的id cookie的值
 * 有效时间 ：单位;小时 Output: 无 Return:
 ******************************************************************************/
function setCookie(cookie_id,cookie_value,times){
	$.cookies.set( cookie_id, cookie_value , { hoursToLive: times });
}
/*******************************************************************************
 * Function: checkCookie Description: 检测浏览器是否支持Cookie Input: str：输入的字符串 Output:
 * 无 Return: 是则返回true,否则返回false
 ******************************************************************************/
function checkCookie(){
 	if( $.cookies.test() ){ 
		alert('恭喜你，你的浏览器支持Cookies!');
		 return true; 
	} else {
		alert('很抱歉，你的浏览器不支持Cookies!');
		return false; 
	}
	return false; 
}

/*******************************************************************************
 * Function: checkCookie Description: 删除某个cookie Input: cookie_id：cookie 的id
 * Output: 无 Return:
 ******************************************************************************/
function delCookie(cookie_id){
	$.cookies.del(cookie_id );   
}
/*******************************************************************************
 * Function: checkCookie Description: 获取某个cookie Input: cookie_id：cookie 的id
 * Output: 无 Return: 返回cookie的值 不存在返回null
 ******************************************************************************/
function getCookie(cookie_id){
	var sessid = $.cookies.get(cookie_id ); 
	alert(sessid); 
	return sessid;
}

/*******************************************************************************
 * Function: DBC2SBC Description: 全角转半角 Input: str：字符串 Output: 半角 Return:
 * 返回cookie的值 不存在返回null
 ******************************************************************************/

function DBC2SBC(str)
{
	var result = '';
	for (i=0 ; i<str.length; i++)
	{
		code = str.charCodeAt(i);// 获取当前字符的unicode编码
		if (code >= 65281 && code <= 65373)// 在这个unicode编码范围中的是所有的英文字母已经各种字符
		{
		   result += String.fromCharCode(str.charCodeAt(i) - 65248);// 把全角字符的unicode编码转换为对应半角字符的unicode码
		}else if (code == 12288)// 空格
		{
		   result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32);
		}else
		{
		   result += str.charAt(i);
		}
	}
	return result;
}
/**
 * @author HanLulu 时间比较新增函数
 * @param start
 *            开始日期
 * @param end
 *            结束日期
 * @param time
 *            时间 如开始时间的判断 则放结束时间
 * @param type
 *            类型 如开始时间的判断为1，否则为2
 * @return 返回 例子引用
 *         maxDate:'#F{timeAllByCompare(\'startdate_a\',\'enddate_a\',\'endtime_a\',1)}'
 *         minDate:'#F{timeAllByCompare(\'startdate_a\',\'enddate_a\',\'starttime_a\',2)}'
 */
function timeAllByCompare(start,end,time,type){
	var rs="";
	if(type==1){
		rs="23:59:59";
	}else{
		rs="00:00:00";
		}
	var startDate = $("#"+start).val();
	var endDate = $("#"+end).val();
	if($.trim(startDate)==$.trim(endDate)){// 开始日期等于结束日期 则需要比较时间
		rs = $("#"+time).val();
	}
	return rs;
}
/**
 * 日期比较函数
 * 
 * @author HanLulu
 * @param start
 * @param end
 * @param time
 * @param type
 *            例子引用
 *            maxDate:'#F{dateByCompare(\'startdate_a\',\'enddate_a\',\'starttime_a\',\'endtime_a\',1)}'
 *            minDate:'#F{dateByCompare(\'startdate_a\',\'enddate_a\',\'starttime_a\',\'endtime_a\',2)}'
 * @return
 */
function dateByCompare(start,end,startTime,endTime,type){
	var rs ="";
	var startDate = $("#"+start).val();
	var endDate = $("#"+end).val();
	var startTime = $("#"+startTime).val();
	var endTime=$("#"+endTime).val();
	if(type==1){
		rs = endDate;
		var allStart = endDate+" "+startTime;
		var allEnd = endDate+" "+endTime;
		if(compareTime(allStart,allEnd,"yyyy-MM-dd HH:mm:ss")==1){// 开始时间大于结束时间
			var endDateTemp = new Date(endDate.replace(/-/g,"\/"))
			rs = getYestoday(endDateTemp);
		}
	}else{
		rs = startDate;
		var allStart = startDate+" "+startTime;
		var allEnd = startDate+" "+endTime;
		if(compareTime(allStart,allEnd,"yyyy-MM-dd HH:mm:ss")==1){// 开始时间大于结束时间
			var startDateTemp = new Date(startDate.replace(/-/g,"\/"))
			rs = getTomorrow(startDateTemp);
		}
	}
	
	return rs;
}
/**
 * 获取明天的日期
 * 
 * @author HanLulu
 * @param date
 * @return
 */
function getTomorrow(date){       
    var tomorrow_milliseconds=date.getTime()+1000*60*60*24;        
    var tomorrow = new Date();        
    tomorrow.setTime(tomorrow_milliseconds);        
         
    var strYear = tomorrow.getFullYear();     
    var strDay = tomorrow.getDate();     
    var strMonth = tomorrow.getMonth()+1;   
    if(strMonth<10)     
    {     
        strMonth="0"+strMonth;     
    }     
    datastr = strYear+"-"+strMonth+"-"+strDay;   
    return datastr;   
  } 
/**
 * 获取昨天的日期
 * 
 * @author HanLulu
 * @param date
 * @return
 */
function getYestoday(date){       
    var yesterday_milliseconds=date.getTime()-1000*60*60*24;        
    var yesterday = new Date();        
        yesterday.setTime(yesterday_milliseconds);        
         
    var strYear = yesterday.getFullYear();     
    var strDay = yesterday.getDate();     
    var strMonth = yesterday.getMonth()+1;   
    if(strMonth<10)     
    {     
        strMonth="0"+strMonth;     
    }     
    datastr = strYear+"-"+strMonth+"-"+strDay;   
    return datastr;   
  }  
/**
 * 比较日期大小
 * 
 * @author HanLulu
 * @param time1
 * @param time2
 * @param dateFmt
 * @return
 */
function compareTime( time1, time2, dateFmt ){

    var a = 'yyyy-MM-dd HH:mm:ss'.match(/\w+/g);

    for (var i = 0; i < a.length; i++) {

            var t = dateFmt.indexOf(a[i]);

            if (t != -1) {
                    t = time1.substr(t, a[i].length) - time2.substr(t, a[i].length);

                    if (t) return t < 0 ? -1 : 1;
            }

    }

    return 0;

}
/**
 * 日期JS
 * 
 * @param startdate
 * @param endDate
 * @param startTime
 * @param endTime
 * @param type
 * @return
 */
function dateShow(startdate,endDate,startTime,endTime,type){
	if(type==1){
		WdatePicker({
			maxDate:"#F{dateByCompare('"+startdate+"','"+endDate+"','"+startTime+"','"+endTime+"',"+type+")}", 
			isShowClear:false,
			dateFmt:'yyyy-MM-dd',
			readOnly:true})
	}else{
		WdatePicker({
			minDate:"#F{dateByCompare('"+startdate+"','"+endDate+"','"+startTime+"','"+endTime+"',"+type+")}", 
			isShowClear:false,
			dateFmt:'yyyy-MM-dd',
			readOnly:true})
	}
}
/**
 * 时间
 * 
 * @param startdate
 * @param endDate
 * @param obj
 * @param type
 * @return
 */
function timeShow(startdate,endDate,obj,type){
	if(type==1){
		WdatePicker({
			maxDate:"#F{timeAllByCompare('"+startdate+"','"+endDate+"','"+obj+"',"+type+")}",
			isShowClear:false,
			readOnly:true,
			dateFmt:'HH:mm:ss'})
	}else{
		WdatePicker({
			minDate:"#F{timeAllByCompare('"+startdate+"','"+endDate+"','"+obj+"',"+type+")}",
			isShowClear:false,
			readOnly:true,
			dateFmt:'HH:mm:ss'})
		
	}
}

/**
 * 判断浏览器各个类型
 * 
 * @return IE6 IE7 IE8 IE9 Safari Mozilla Opera
 * 
 */
function checkBrowser(){
	var type="";
	if($.browser.msie) {
		if(($.browser.version == "6.0")&&!$.support.style){
			type = "IE6";
		}else if($.browser.version == "7.0"){
			type = "IE7";
		}
	}else if($.browser.safari)  {  
		type = "Safari";
	}  else if($.browser.mozilla) {  
		type = "Mozilla";
	}  else if($.browser.opera) {  
		type = "Opera";
	}
	return type;

}

/**
 * 是否为ie内核
 * @return
 */
function isIE(){
    var brows=$.browser; 
    if(brows.msie){
        return true;
    }
    return false;           
}


