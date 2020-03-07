/**
 * Author :  HanLulu
 * Date   :  2013-03-14
 */
/**
 * 设置用户登录IP
 * 
 * @return
 */
function readySetUserLoginIp() {
	var urls = location.host.split(":");
	$('#userLoginIp').val(urls[0]);
}
/**
 * 页面值初始化
 * 
 * @return
 */
function readyInitLogin() {
	var type = $.cookie('DSS_USERTYPE');
	initLoginForm(type);
}
/**
 * 更多下载
 * 
 * @return
 */
function readyMoreDownLoad() {
	$("#all_download").mouseenter(function() {
		if ($("#all_download_layer").is(":animated")) {
			$("#all_download_layer").stop().show()
		} else {
			$("#all_download_layer").stop().show()
		}
	}).mouseleave(function() {
		$("#all_download_layer").hide();
	});
	$("#all_download_layer").mouseenter(function() {
		if ($(this).is(":animated")) {
			$(this).stop().show();
		} else {
			$(this).stop().show();
		}
	}).mouseleave(function() {
		$(this).hide();
	});

	$("#show-suport").toggle(function() {
		$(this).removeClass("down");
		$(".help-area").hide();
		$(".person-area").show();
	}, function() {
		$(this).addClass("down");
		$(".person-area").hide();
		$(".help-area").show();

	});
}

/**
 * 记住密码切换效果
 */
function changeCheckBox() {
	if (!$(".checkbox").hasClass("checked")) {
		$("#isSave").val(true);
		$(".checkbox").addClass("checked");
	} else {
		$("#isSave").val(false);
		$(".checkbox").removeClass("checked");
	}
}

function changeCheckBoxCss() {
	if ($("#isSave").val() == 'true') {
		$(".checkbox").addClass("checked");
	} else {
		$(".checkbox").removeClass("checked");
	}
}

/**
 * COOKIE set值
 */
function setCookieModule() {
	var userType = $("#userType_selectValue").val();
	var loginName = $("#loginName").val();
	var password = $("#loginPass").val();
	savePassToCookie(password);
	$.cookie('DSS_USERTYPE', userType, {
		expires : 30
	});
	if ($("#isSave").val() == 'true') {
		if (userType == 'nbsj') {
			$.cookie('VIDEO_MGT_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('VIDEO_MGT_ISSAVE', "true", {
				expires : 30
			});
			$.cookie('VIDEO_MGT_PASSWROD', desEncrypt(password), {
				expires : 30
			});
		} else if (userType == '1') {//操作员
			$.cookie('DSS_USERNAME', loginName, {
				expires : 30
			});
			if(password == '......'){
				$.cookie('DSS_PASSWROD', $("#encryptP").val(), {
	                expires: 30 
	            });
			}else{
				$.cookie('DSS_PASSWROD', desEncrypt(password), {
	                expires: 30 
	            });
			}
			$.cookie('DSS_ISSAVE', "true", {
				expires : 30
			});
		} else if (userType =='0'){//管理员
			$.cookie('ADMIN_USERNAME', loginName, {
				expires : 30
			});
			if(password == '......'){
				$.cookie('ADMIN_PASSWROD', $("#encryptP").val(), {
	                expires: 30 
				});
			}else{
				$.cookie('ADMIN_PASSWROD', desEncrypt(password), {
	                expires: 30 
	         });
			}
			$.cookie('ADMIN_ISSAVE', "true", {
				expires : 30
			});
		} else if (userType =='3') {//协同办案
			$.cookie('XIETONG_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('XIETONG_PASSWROD', desEncrypt(password), {
	                expires: 30 
	         });
			$.cookie('XIETONG_ISSAVE', "true", {
				expires : 30
			});
		} else {//扁平化指挥
			$.cookie('POLICE_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('POLICE_PASSWROD', desEncrypt(password), {
	                expires: 30 
	         });
			$.cookie('POLICE_ISSAVE', "true", {
				expires : 30
			});
		}
	} else {
		if (userType == 'nbsj') {
			$.cookie('VIDEO_MGT_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('VIDEO_MGT_PASSWROD', '', {
				expires : 30
			});
			$.cookie('VIDEO_MGT_ISSAVE', false, {
				expires : 30
			});
		} else if (userType == '1') {//操作员
			$.cookie('DSS_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('DSS_PASSWROD', '', {
				expires : 30
			});
			$.cookie('DSS_ISSAVE', false, {
				expires : 30
			});
		} else if (userType =='0'){//管理员
			$.cookie('ADMIN_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('ADMIN_PASSWROD', '', {
				expires : 30
			});
			$.cookie('ADMIN_ISSAVE', false, {
				expires : 30
			});
		} else if (userType =='3'){//协同办案
			$.cookie('XIETONG_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('XIETONG_PASSWROD', '', {
				expires : 30
			});
			$.cookie('XIETONG_ISSAVE', false, {
				expires : 30
			});
		} else {//扁平化指挥
			$.cookie('POLICE_USERNAME', loginName, {
				expires : 30
			});
			$.cookie('POLICE_PASSWROD', '', {
	                expires: 30 
	         });
			$.cookie('POLICE_ISSAVE', false, {
				expires : 30
			});
		}
	}
}
/**
 * 登陆 包含验证
 */
function login() {
	if ($("#loginName").val() == '') {
		$("#loginNameTip").html($.jQi18Login.loginNameEmpty);// 用户名不能为空，请修改
		$("#loginPassTip").html("");
	} else if ($("#loginPass").val() == '') {
		$("#loginPassTip").html($.jQi18Login.loginPassEmpty);// 密码不能为空，请修改
		$("#loginNameTip").html("");
	} else if (validate($("#loginName").val(), 1) && validate($("#loginPass").val(), 2)) {
		loginAllAjax();
	}
}
function validate(testobj, type) {// 用户密码验证与管理员端保存一致s
	var userNameP1 = /^[\u4E00-\u9FA5a-zA-Z0-9\w\-\(\).（）@;#]*$|^([\u4E00-\u9FA5a-zA-Z0-9\w\-\(\).（）;#])+([\u4E00-\u9FA5a-zA-Z0-9\w\-\(\).（）;# ])+([\u4E00-\u9FA5a-zA-Z0-9\w\-\(\).（）;#])+$/;
	var passWordP2 = /[&\+;]/g;
	if (type == 1) {
		if (!userNameP1.test(testobj)) {
			$("#loginNameTip").html($.jQi18Login.loginUserVaild);// 用户名仅允许 汉字字母数字-_.;#()（）请修改
			$("#loginPassTip").html("");
			return false;
		}
	} else if (type == 2) {
		if (passWordP2.test(testobj)) {
			if($("#loginNameTip").html()==""){
				$("#loginPassTip").html("密码不允许&+; 请修改");
				$("#loginNameTip").html("");
				return false;
			}
		}
	}
	return true;
}

/**
登录验证用户正确
**/
function loginAllAjax(){

	var userInfo = {
			"loginName": $("#loginName").val(),
			"loginPass": $("#loginPass").val()
		};
		var encryptedPass = $("#loginPass").val();
		if(encryptedPass == '......' && $("#encryptP").val()!=''){
			encryptedPass = desDecrypt($("#encryptP").val());
		}
		//加密不加密
		if(isEncrypted == "1"){
			encryptedPass = encrypt(userInfo);
		}
		//对密码进行
		//var params = {
		//		"userBean.loginName"       : encodeURI($("#loginName").val()),
		//	"userBean.loginPass"       : encryptedPass
		//};
		var paramStr = "userBean.loginName="+encodeURI($("#loginName").val())+"&userBean.loginPass="+encryptedPass;
		paramStr = strongEncode(paramStr);
		var params = {};
		params.param = paramStr;
		
	$.ajax({
		url:appPath+"/login_checkUser.action",
		data: params,
		type:"POST",
		cache:false,
		dataType:"text",
		success : function(data) {
			if(data=='0'){
				var tempParams = {};
				tempParams.param = strongEncode("userBean.loginName="+encodeURI($("#loginName").val()));
				$.ajax( {
					url : appPath+"/login_getPasswordErrorNum.action",
					data : tempParams,
					cache : false,
					dataType : "text",
					success : function(result) {
					$("#loginNameTip").html("");
					if(result=='0'){
						$("#loginPassTip").html("&nbsp;&nbsp;今天已输错5次密码,<br>请明天尝试或者联系管理员");
					} else if (result == "") {
						$("#loginPassTip").html("用户不存在,请检查用户名");
					} else{
						$("#loginPassTip").html("用户名或密码错误,请修改<br>&nbsp;今天还有"+result+"次重新登录机会");
					}
						return false;
					}
				});
			}else if(data=='1'){
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginUserLock);// 该用户已冻结，请联系管理员
				return false;
			}else if(data=='3'){
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginNoMore);// 该用户已在线，且不支持复用，请联系管理员
				return false;
			}else if(data=='7'){
				$("#loginNameTip").html("");
				$("#loginPassTip").html("&nbsp;&nbsp;今天已输错5次密码,<br>请明天尝试或者联系管理员");
				return false;
			}else if(data=='8'){
				$("#loginNameTip").html("");
				$("#loginPassTip").html("&nbsp;&nbsp;用户在该电脑上无法登录");
			}else if(data=='9'){
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginUserPassError);
			}else if(data=='10'){
				$("#loginPassTip").html("用户不存在,请检查用户名");
			}else if(data=='11'){
				$("#loginPassTip").html("用户有效期已过,请联系管理员");
			}else{
				if (forceUpdatePasswd()) return;
				
				if(updateOriginalPasswd()) return;//强制修改原始密码123456
				
				if(productId == 3 || productId == 4 || productId == 9){	// 如果是数字法庭 3, 公安审讯平台4, 需要登录统一鉴权.
					loginDahuaCA();
				}
				$("#loginNameTip").html("");
				$("#loginPassTip").html("");
				setCookieModule();
				var userType = $("#userType_selectValue").val();
				if (userType == 'nbsj') {// 视频管理门户登录
					$("#videoMgtName").val( $("#loginName").val() );
					$("#videoMgtPass").val( $("#loginPass").val() );
					$("#videoMgtLoginForm").attr("action", "videoMgt_loginSuccess.action");
					window.document.forms['videoMgtLoginForm'].submit();
				} else if(userType==0){//管理员
					var formId = "";
					if($("#loginAdminEncrypt").val()=='1'){
						//登录admin加密
						$("#loginInfoStr").val(params.param);
						$("#userTypeIdEncrypt").val(userType);
						formId = "loginFromEncrypt";
					}else{
						//不加密
						$("#adminName").attr("value",$("#loginName").val());
						$("#adminPass").attr("value",encryptedPass);
						formId = "adminLoginForm";
					}
					if(typeof(adminUrl) == "undefined"){
						adminUrl = "";
					}
					$("#"+formId).attr("action",adminUrl + "/admin/login_login.action");
					window.document.forms[formId].submit();
				}else{//操作员
					//表单增加ssid参数
					$(":hidden[name='ssid']").val(hex_md5($.cookie("JSESSIONID")));
					$("#loginInfoStr").val(params.param);
					$("#userTypeIdEncrypt").val(userType);
					var formId = "loginFromEncrypt";
					
					$("#"+formId).attr("action",appPath+"/login_loginAbleUser.action");
					window.document.forms[formId].submit();
					
				}
			}
		},
		error : function() {
		}
	});
}

function forceUpdatePasswd() {
	var result = false;
	$.ajax({ url: "login_isForceUpdatePasswd.action", async: false, success: function(data){
	    if (data == 'true') {
	    	result = true;
	    } 
	}});
	
	
	if (result) {
		if (isPassWeak($("#loginPass").val())) {
			$.ajax({ url: "pages/login/passwdUpdate.jsp", async: false, success: function(data){
				var dialog = $.dialog({
					title:'系统检测到您的密码过于简单，请修改。',
					width: '500px',
					height: '220px',
					content: data,
					max: false,
				    min: false,
				    ok: function(){
						return validation();
				    }
				});
			}});
		} else {
			result = false;
		}
		
	}
	
	return result;
}

function updateOriginalPasswd(){
	var result = false;
	//var loginName = $("#loginName").val();
	var loginPass = $("#loginPass").val();
	if("123456" == loginPass) {
		result = true;
    	var artParam = {
			title:$.jQi18Home.passwdModify,//"修改密码"
			width:"450px",
			height:"200px",
			resize:false,
			max:false,
			min:false,
			lock:true,
			content:"url:"+appPath+"/pages/home/firstChangePassword.jsp"
		}
	    $.dialog(artParam);
	}
	return result;
}

function isPassWeak(pass) {
	if (pass.length < 8) return true;
	
	var res = /[a-z]/.test(pass);//必须有小写字母
	if (!res) return true;
	
	res = /[A-Z]/.test(pass);//必须有大写字母
	if (!res) return true;
	
	res = /[0-9]/.test(pass);//必须有数字
	if (!res) return true;
	
	return false;
}
/**
 * 登录验证用户名密码
 */
function checkloginAble() {
	$.ajax( {
		url : appPath + "/login_isAbledLogin.action",
		data : "userBean.loginName=" + encodeURI($("#loginName").val())
				+ "&userBean.loginPass=" + encodeURI($("#loginPass").val())
				+ "&userBean.loginServerName=" + $("#userLoginIp").val()
				+ "&userBean.loginType=" + $("#userType_selectValue").val(),
		cache : false,
		dataType : "text",
		success : function(data) {
			if (data == '0') {
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginPassError);// 密码错误，请修改
				return false;
			} else if (data == '1') {
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginUserLock);// 该用户已冻结，请联系管理员
				return false;
			} else if (data == '3') {
				$("#loginNameTip").html("");
				$("#loginPassTip").html($.jQi18Login.loginNoMore);// 该用户已在线，且不支持复用，请联系管理员
				return false;
			} else {
				$("#loginNameTip").html("");
				$("#loginPassTip").html("");
			}
		}
	});
}

/**
 * 登录统一鉴权
 */
function loginDahuaCA() {
	var loginName = $("#loginName").val();
	var loginPass = $("#loginPass").val();
	var user = {loginName:loginName,loginPass:loginPass};
	$.ajax({
        type: "post",
        data: user,
        datatype: "text",
        url: "/dahuaCA/checkuser.do",
        async: false,
        success : function(msg) {
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function addErrorMsg(data) {
	if (data == '4') {
		$("#loginNameTip").html($.jQi18Login.loginNameEmpty);// 用户名不能为空，请修改
		$("#loginPassTip").html("");
	} else if (data == '5') {
		$("#loginPassTip").html($.jQi18Login.loginPassEmpty);// 密码不能为空，请修改
		$("#loginNameTip").html("");
	} else if (data == '0') {
		$("#loginPassTip").html($.jQi18Login.loginUserPassError);// 用户或密码错误，请修改
		$("#loginNameTip").html("");
	} else if (data == '1') {
		$("#loginPassTip").html($.jQi18Login.loginUserLock);// 该用户已冻结，请联系管理员
		$("#loginNameTip").html("");
	} else if (data == '3') {
		$("#loginPassTip").html($.jQi18Login.loginNoMore);// 该用户已在线，且不支持复用，请联系管理员
		$("#loginNameTip").html("");
	}
}
/**
 * 一键IE配置
 */
function speedyInstallIETool(ips) {
	ips = $('#userLoginIp').val()+" "+ips;
	var system = optSystem; 
	var regedit;
    var regKey = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\DSSOcx\\InstallPath";//客户端安装时会将安装目录写到注册表项键名
    var regValue;//键值
    try {
	    var wsh = new ActiveXObject("WScript.Shell");    //wscript.shell
	    regedit = new RegEdit(wsh); 
		regValue = regedit.regRead(regKey);//取得导出路径   
		wsh.run('"'+regValue+'\\IEConfig.exe" '+ips);
	} catch (e) {
		$.dialog.alert($.jQi18Home.ocxNoInstallTip);// 请先下载安装OCX控件
	}
}
/**
* 注册表编辑器，封装对注册表的操作
*/
function RegEdit(wsh){
	this.shell = wsh;
	this.regRead = regRead; 
}

/** 返回名为 strName 的注册键或值。
* @param strName 要读取的键或值。如果 strName 以反斜线 (\) 结束，本方法将返回键，而不是值
* @return 名为 strName 的注册键或值
*/
function regRead(strName){
	var val = null;
	try {
	   val = this.shell.regRead(strName);
	} catch (e) {
	   //alert(e.message);
	}
	return val;
}


//下载ie配置工具
function downIETool(obj) {
	var system = optSystem;
	var ieToolName="IETool.zip";
	if(obj==1){
		ieToolName ="IEToolUnion.zip";
	}
	if (system == "Linux") {
		window.location.href = "http://" + location.host
				+ "/upload/itc/"+ieToolName;
	} else {
		window.location.href = "http://" + location.host + appPath
				+ "/"+ieToolName;
	}
}


// 协议码文档下载
function downText() {
	var textPath = appPath + "/errorCodes.chm";
	window.location.href = appPath+"/attachment_downloadByUrlAtt.action?type=1&filePath="
			+ encodeURI(encodeURI(textPath));

}


// 证书登陆
function keyLogin() {
	var cmsip = $('#userLoginIp').val();
	var userType = $("#userType_selectValue").val();
	if (cmsip == "") {
		alert($.jQi18Login.loginCmsEmpty);// CMS服务器IP不能为空
		return false;
	}
	//document.getElementById("downloadocx").innerHTML = "<object id='testOCX'classid='CLSID:D51A9C05-BF1E-49DA-B067-1ED1F5E0264D' width='0' height='0'></object>";

	//goToVerify(cmsip, userType);
	$.dialog.alert($.jQi18Login.loginCmsEmpty);// 暂不支持证书登录
	// self.location="http://10.33.7.73:8099/getCard.asp?cmsip="+cmsip+"&userType="+userType;
	//self.location="/pki/login.jsp?url=http:\//"+cmsip+"/itc/login_loginByKey.action?userBean.loginServerName="+cmsip;
	//self.location="https://10.62.207.164:9443/itc/pkilogin.jsp"
}


//PKI登陆验证
function goToVerify(cmsip, userType) {
	var version ="";
	try {
		version = dsscocx.GetVersion();
	} catch (e) {
		$.dialog.alert($.jQi18Home.ocxNoInstallTip,function(){//控件未安装，请先下载控件，再将服务器地址设置为信任站点并重启浏览器，谢谢！
			
		});
		return false;
	}
	//var loginInfo = "0$张三$330721123456789874";
	dsscocx.SetDogLoginFlag(true);
	var loginInfo = dsscocx.GetLoginInfoFromRockeyDog();
	var loginArray = loginInfo.split("$");
	if(loginArray.length > 0){
		if(loginArray[0] == "0"){
			if(loginArray.length == 3){
				$.ajax({
					type:"POST",
					url:"/itc/login_loginByDog.action?userBean.loginServerName="+cmsip+"&userBean.userName="+encodeURI(loginArray[1])+"&userBean.userCode="+loginArray[2],
					success:function(data){
						data = eval("(" + data + ")");
						if (data.isAbleLogin == 0) {
							$.dialog.alert("用户不存在，登录失败");//用户名身份证UUID不匹配，登录失败
						} else {
							self.location = "/itc/login_toHomePage.action?userType="+userType;
						}
					}
				});
			}else{
				$.dialog.alert("用户信息不正确");//用户信息不正确
			}
		}else{
			$.dialog.alert("读取加密狗失败");//读取加密狗失败
		}
	}
}

//跳转到统一鉴权登陆页面
function redirectToDahuaCA() {
	window.open("/dahuaCA");
}

/**
 * 
 * 下拉改变值
 */
function onchangeForSelect() {
	initLoginForm($("#userType_selectValue").val());
}
/**
 * 初始化取COOKIE里的值
 */
function initLoginForm(type) {
    var isSave;
    if (type == 'nbsj') {//管理员
		var isSave = $.cookie('VIDEO_MGT_ISSAVE');
		if ((typeof (isSave) != "undefined") && isSave == "true") {
			$("#loginName").val($.cookie('VIDEO_MGT_USERNAME'));
			try {
				var password = desDecrypt($.cookie('VIDEO_MGT_PASSWROD'));
		        $("#loginPass").val(password);	
			} catch (e) {
				$("#loginPass").val("");	
			}						
		} else {			
			$("#loginName").val("");
			$("#loginPass").val("");
		}
		$('#isSave').val($.cookie('VIDEO_MGT_ISSAVE'));
		document.getElementById("userType_selectValue").value = 'nbsj';
	} else if (type == '0') {//管理员
		var isSave = $.cookie('ADMIN_ISSAVE');
		if ((typeof (isSave) != "undefined") && isSave == "true") {
			$("#loginName").val($.cookie('ADMIN_USERNAME'));
			try {
				//var password = desDecrypt($.cookie('ADMIN_PASSWROD'));
		        $("#encryptP").val($.cookie('ADMIN_PASSWROD'));
				$("#loginPass").val("......");	
			} catch (e) {
				$("#loginPass").val("");	
			}						
		} else {			
			$("#loginName").val("");
			$("#loginPass").val("");
		}
		$('#isSave').val($.cookie('ADMIN_ISSAVE'));
		document.getElementById("userType_selectValue").value = 0;
		document.getElementById("userType_selectText").value = $.jQi18Login.loginAdmin;// 管理员
	} else if(type == '1'){//操作员
		isSave = $.cookie('DSS_ISSAVE');
		if ((typeof (isSave) != "undefined") && isSave == "true") {
			$("#loginName").val($.cookie('DSS_USERNAME'));
			try {
			   //var password = desDecrypt($.cookie('DSS_PASSWROD'));
			   $("#encryptP").val($.cookie('DSS_PASSWROD'));
	           $("#loginPass").val("......");
			} catch (e) {
				$("#loginPass").val("");
			}
		} else {
			$("#loginName").val("");
			$("#loginPass").val("");
		}
		$("#isSave").val($.cookie('DSS_ISSAVE'));
	} else if(type == '2'){//扁平化指挥
		isSave = $.cookie('POLICE_ISSAVE');
		if ((typeof (isSave) != "undefined") && isSave == "true") {
			$("#loginName").val($.cookie('POLICE_USERNAME'));
			try {
			   var password = desDecrypt($.cookie('POLICE_PASSWROD'));
	           $("#loginPass").val(password);
			} catch (e) {
				$("#loginPass").val("");
			}
		} else {
			$("#loginName").val("");
			$("#loginPass").val("");
		}
		$("#isSave").val($.cookie('POLICE_ISSAVE'));
	} else if(type == '3'){//协同办案
		isSave = $.cookie('XIETONG_ISSAVE');
		if ((typeof (isSave) != "undefined") && isSave == "true") {
			$("#loginName").val($.cookie('XIETONG_USERNAME'));
			try {
			   var password = desDecrypt($.cookie('XIETONG_PASSWROD'));
	           $("#loginPass").val(password);
			} catch (e) {
				$("#loginPass").val("");
			}
		} else {
			$("#loginName").val("");
			$("#loginPass").val("");
		}
		$("#isSave").val($.cookie('XIETONG_ISSAVE'));
	}
    changeCheckBoxCss();
}
/**
 * 密码，用户名label选中后对密码框和用户名框聚焦
 * @param id
 * @return
 */
function setTextFocus(id){
	if(id=="namelabel"){
		$("#loginName").focus();
	}else if(id=="pwdlabel"){
		$("#loginPass").focus();
	}
}



//加密狗登陆
function dogLogin() {
	var cmsip = $('#userLoginIp').val();
	var userType = $("#userType_selectValue").val();
	if (cmsip == "") {
		alert($.jQi18Login.loginCmsEmpty);// CMS服务器IP不能为空
		return false;
	}
	//document.getElementById("downloadocx").innerHTML = "<object id='testOCX'classid='CLSID:D51A9C05-BF1E-49DA-B067-1ED1F5E0264D' width='0' height='0'></object>";
	
	goToVerify(cmsip, userType);
	/*var version ="";
	try {
		version = dsscocx.GetVersion();
	} catch (e) {
		$.dialog.alert($.jQi18Home.ocxNoInstallTip,function(){//控件未安装，请先下载控件，再将服务器地址设置为信任站点并重启浏览器，谢谢！
		});
		return false;
	}
	var loginInfo = dsscocx.GetLoginInfoFromRockeyDog();
	var loginArray = loginInfo.split("$");
	if(loginArray.length > 0){
		alert("12");
		alert(loginInfo);
		if(loginArray[0] == "0"){
			if(loginArray.length == 3){
				$("#loginName").val(loginArray[1]);
				$("#loginPass").val(loginArray[2]);
				$("#isSave").val(false);
				login();
			}else{
				$.dialog.alert("用户信息不正确");//用户信息不正确
			}
		}else{
			$.dialog.alert("读取加密狗失败");//读取加密狗失败
		}
	}*/
}
