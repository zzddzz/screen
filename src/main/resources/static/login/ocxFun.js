/*************************************/
/*******该JS主要处理OCX相关 函数 *****/
/*********    2012-09-28 ******  ****/

/**
 * 加载cab包的classid
 * 
 * @param cmsIp
 *            cms服务ip
 * 
 * @param cmsPort
 *            cms服务端口
 * 
 * @param loginName
 *            用户名
 * 
 * @param loginPass
 *            密码
 * 
 * @return
 */
function initDsscOcx() {
	getVersionByOcx();
	
}

/**
 * 常州武进实时流量监测
 * strChnnlId:通道Id
 */
function queryDev4GFlowInfo(strChnnlId){
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.QueryDev4GFlowInfo(strChnnlId); 
	} catch (e) {
		// TODO: handle exception
	}
}
/**
 * 获取版本号
 * 
 * @return
 */
function getVersionByOcx() {
	var version ="";
	try {
		version = dsscocx.GetVersion();
		checkVersion(version)
	} catch (e) {
		 recoveryBodyArea();
		$.dialog.alert($.jQi18Home.ocxNoInstallTip,function(){//控件未安装，请先下载控件，再将服务器地址设置为信任站点并重启浏览器，谢谢！
			  var loginJsp = appPath + "pages/index.jsp";
				window.location=loginJsp;
			  }
		  ); 
	}
	

}
/**
 * 比较版本号
 * 
 * @param version
 * @return
 */

function checkVersion(version) {
	$.ajax( {
		url : appPath + "/home_checkOcxVersion.action?ocxVersion=" + version,
		cache : false,
		success : function(data) {
		if(data!='true'){
			   	(function(config){ 				   
					    config['okVal'] = $.jQi18Common.immediateUpdate; 
					    config['cancelVal'] = $.jQi18Common.updateLater;  
				})($.dialog.setting);		   	    
		   	    $.dialog.confirm($.jQi18Common.updateTipOne.replace("{0}",data)+"</br>"+$.jQi18Common.updateTipTwo,function(){
					        location.href=appPath+"DSSOcx30.exe";
				      },
				      function () {			    	 			    	 
					  disableBodyArea();
					  try {
							if (typeof (userTypeId) != "undefined") {
								document.getElementById("dsscocx").SetLoadingPlatform(userTypeId);
							}
					  } catch(e){										
					  }
					  try {
							if (typeof (isRtcUse) != "undefined") {
								document.getElementById("dsscocx").SetRTCSeverBeUse(isRtcUse);
							}
					  } catch(e){										
					  }					  
					  setTimeout(function() {
							loginDsscOcx(cmsIp, cmsPort, loginName, loginPass);
						}, 2000); // 延时时间为2秒					
					}
				);
			   	(function(config){ 				   
				    config['okVal'] = $.jQi18lhgdialog.dialogYes; 
				    config['cancelVal'] = $.jQi18lhgdialog.dialogCancel;  
			    })($.dialog.setting);
			 } else {
				disableBodyArea();
				try {
						if (typeof (userTypeId) != "undefined") {
							document.getElementById("dsscocx").SetLoadingPlatform(userTypeId);
						}
				 } catch(e){										
				 }
				 try {
						if (typeof (isRtcUse) != "undefined") {
							document.getElementById("dsscocx").SetRTCSeverBeUse(isRtcUse);
						}
				  } catch(e){										
				  }	
				setTimeout(function() {
					loginDsscOcx(cmsIp, cmsPort, loginName, loginPass);
				}, 2000); // 延时时间为2秒
		  }
		}
	});
}
/**
 * 关闭报警声音
 * @return
 */
function closeAlarm(){
	try{
		dsscocx.CloseAlarmSound(); 
		$("#alarmSound").removeClass("open");
	}catch(e){
		queryDev4GFlowInfo
		
	}

	
}

/**
 * 加载ocx
 * 
 * @return
 */
function loginDsscOcx(cmsIp, cmsPort, loginName, loginPass) {
	try{// 数字法庭/公安审讯  客户端定制接口.
		document.getElementById("dsscocx").SetExtendProperty(customizeInfo);
	} catch(e){}
	try {
		if(isDogLogin == "1"){//如果是加密狗登录，则先通知客户端
			document.getElementById("dsscocx").SetDogLoginFlag(true);
		}
		if(typeof(userisencrypted)!='undefined' && userisencrypted=='1'){
			document.getElementById("dsscocx").Login(cmsIp, cmsPort, loginName,
					getPassFromCookie(), "1");
		}else{
			document.getElementById("dsscocx").Login(cmsIp, cmsPort, loginName,
					loginPass, "1");
		}
	} catch (e) {
		recoveryBodyArea();
		$.dialog.alert($.jQi18Home.ocxLoadError, function() {//ocxLoadError控件未加载或者加载出错，请重新下载安装并重启IE浏览器！
			if(productId == 4){// P700融合控件下载页面
				window.location=appPath+"downLoadUnionExe.jsp";
			} else {
				window.location=appPath+"downLoadExe.jsp";
			}
		});
	}
	try{
		document.getElementById("dsscocx").SetNetFlag(netFlg);
		document.getElementById("dsscocx").SetEmapOpenType(openEmapMode);
		document.getElementById("dsscocx").SetVideoWaitTime(videoWaitTime);
		document.getElementById("dsscocx").SetEmapFtpNaPaWord(strFtpName,strFtpPaword);
		document.getElementById("dsscocx").SetDevManagerServerInfo(nmsUrlJson);
	}catch(e){
		
	}
	try{// 数字法庭/公安审讯  客户端定制接口.
		document.getElementById("dsscocx").SetExtendProperty(customizeInfo);
	} catch(e){}
	// 注册下载优化控件
	//document.getElementById("downloadocx").innerHTML = "<object id='testOCX' classid='CLSID:D51A9C05-BF1E-49DA-B067-1ED1F5E0264D' width='0' height='0'></object>";
}

/**
 * 加载功能
 * 
 * @param type
 * @param param1
 * @return
 */
function UIMainCall(type, param1) {

	try {
		if (param1) {
			dsscocx.UiMainCall(type, param1);
		} else {
			dsscocx.UiMainCall(type, 'd');
		}
	} catch (e) {
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"//标准化改为错误提示框
	}
}
/**
 * 首页即时通讯
 * @return
 */
function telCall(){
	UIMainCall(28, "");
}

function talkFun(){
	var dssocxObject = top.window.document.getElementById("dsscocx");
	dssocxObject.OpenMonitorSoundTalk();
}
/**
 * 应急指挥系统客户端配置
 * @return
 */
function clientConfig() {
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.ShowUrgentCommandConfigCtrl();		
	} catch (e) {				
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	 //标准化改为错误提示框
	}
}

/**
 * 首页报警
 * 
 * @return
 */
function showAlarm() {
	UIMainCall(14, "");
	$("#alarmSound").removeClass("open");
}

/**
 * ocx播放
 * @param data
 * @return
 */
function ocxPlay(data){
	window.parent.dsscocx.TriggerPlay(data);
}

/**
 * 录像回放 调用OCX回放接口
 * 
 * @param channelNum
 * @param carImgUrl
 * @param carImg1Url
 * @param carImg1Url
 * @param carImg3Url
 * @return
 */
function playBack(channelNum, time, carImgUrl, carImg1Url, carImg2Url,domainId) {
	if(typeof(domainId) == "undefined"){
		domainId="";
	}
	// 录像关联参数格式--- 卡口通道id,年-月-日-时-分-秒,关联图片URL1,URL2,URL3]
	$.ajax({
		  url : appPath + "carQuery/commonQuery_getVideoChannel.action?channelCode="+channelNum+"&domainId="+domainId,
		  cache : false,
		  success: function(data){
		 	var playbackStr = data + "," + time + "," + carImgUrl + ","+ carImg1Url + "," + carImg2Url;
			try {
				var dssocxObject = top.window.document.getElementById("dsscocx");
				if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
					ocxMainForCSClient("BayLinkRecord",playbackStr);
				} else {
					dssocxObject.BayLinkRecord(playbackStr);
				}
				
			} catch (e) {				
				errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"				
			}
		  }
     });
}

/**
 * 此方法供mac查询的关联录像使用
 * @param channelNum
 * @param time
 * @param carImgUrl
 * @param carImg1Url
 * @param carImg2Url
 * @return
 */
function playBackForPersonFlow(channelNum, time, carImgUrl, carImg1Url, carImg2Url){
	var playbackStr = channelNum + "," + time + "," + carImgUrl + ","+ carImg1Url + "," + carImg2Url;
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			ocxMainForCSClient("BayLinkRecord",playbackStr);
		} else {
			dssocxObject.BayLinkRecord(playbackStr);
		}
		
	} catch (e) {				
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"				
	}
}

function shipPlayBack(devChnid,time,img1Url,img2Url,img3Url){
	var playbackStr = devChnid + "," + time + "," + img1Url + ","+ img2Url + "," + img3Url;
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			ocxMainForCSClient("BayLinkRecord",playbackStr);
		} else {
			dssocxObject.BayLinkRecord(playbackStr);
		}
		
	} catch (e) {				
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"				
	}
}
/**
 * 获取IVSPC人数统计
 * @param channel
 * @param startDate
 * @param endDate
 * @return
 */
function queryIVSPC(channel,startDate,endDate){
	//alert("参数:channel="+channel+";startDate="+startDate+";endDate="+endDate);
	try {
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			ocxMainForCSClient("QueryIVSPCData",channel+","+startDate+","+endDate);
		} else {
			window.parent.dsscocx.QueryIVSPCData(channel,startDate,endDate);
		}		
	}catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
		recoveryBodyArea();		
	}
	
}


function uploadFacePic(){
	try {
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			 window.parent.ocxMainForCSClient("GetRegFaceData",null);
		} else {
			var dssocxObject = top.window.document.getElementById("dsscocx");	
			return dssocxObject.GetRegFaceData();
		}	
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
}

function notifyFaceChannelAlarmSimilar(chnId,minLinkage) {	
	if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
		if(headCommonCssFolder=="gray"){
			ocxMainForCSClient("SetFaceChannelAlarmSimilar",chnId+","+minLinkage);
		}else{
			parent.ocxMainForCSClient("SetFaceChannelAlarmSimilar",chnId+","+minLinkage);
		}
	} else {
		var dssocxObject = top.window.document.getElementById("dsscocx");		
		dssocxObject.SetFaceChannelAlarmSimilar(chnId,minLinkage);	
	}
}

function urgentDisplayOrHide(flag){
	
	try {
		if (flag == "1") {
			$("#showVideo").addClass("hiddenClass");
			$("#hideVideo").removeClass("hiddenClass");
		} else {
			$("#hideVideo").addClass("hiddenClass");
			$("#showVideo").removeClass("hiddenClass");
		}
		var dssocxObject = top.window.document.getElementById("dsscocx");
		return dssocxObject.SetUrgentDisplayOrHide(flag);
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
	
	
}


/**
 * 应急指挥电子地图全屏/退出全屏
 * @param nFull =1全屏,=0退出全屏
 * @return
 */
function emapFullScreen(nFull){
	
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		return dssocxObject.SetEmapFullScreen(nFull);
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
	
	
	
	
}
/**
 * 应急指挥系统，取消上墙
 * @return
 */
function clearUrgentTVWall(){
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.OnClearUrgentTVWall();
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
	
}
/**
 * 应急指挥系统，二三维地图模式切换 1：二维 2：三维
 * @return
 */
function SwitchEmapModelType(type){
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.SwitchEmapModelType(type);
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
	
}
/**
 * 应急指挥系统，手动同步
 * @return
 */
function OnSynByHand(){
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.OnSynByHand();
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	
	}
}
/**
 * 应急指挥系统，即时与否
 * @return
 */
function ChangeUrgentSynState(checkFlag){
	try {
		var dssocxObject = top.window.document.getElementById("dsscocx");
		dssocxObject.ChangeUrgentSynState(checkFlag);
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}
}

/**
 * 协同办案web页面变更通知客户端
 * @param groupId
 * @param moduleType
 * @return
 */
function notifyOcxWebChange(groupId,moduleType,dutyUserId) {
	try {
		if (moduleType == "1") {
			qTaskWidget.OnReceiveWebPageUpdate(groupId,moduleType,dutyUserId);
		} else {
			qPopOutNoticeWidget.OnReceiveWebPageUpdate(groupId,moduleType);
		}
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}

}

/**
 * 协同办案web页面操作类型通知客户端
 * @param messageType
 * @param noticeID
 * @return
 */
function groupNoticeOperFun(messageType, noticeID) {
	try {
		qNotifyWidget.OnPopOutNoticeWebPage(messageType,noticeID);
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}

}

/**
 * 协同办案通知客户端关闭弹框
 * @return
 */
function groupNoticeCloseFun(){
	try {
		qPopOutNoticeWidget.OnCloseNoticeWebPage();
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}
}

/**
 * 协同办案--管理页面--删除或修改，通知客户端
 * @return
 */
function groupNoticeManageChangeFun(){
	try {
		qPopOutNoticeWidget.OnChangeNotifyTask();
	} catch(e){		
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}
}
/**
 * 山东省厅 点播接口
 * @return
 */
function openVideoOnDemand(json){
	try {
		//alert('emap调用成功:参数：'+json);
		var dssocxObject = top.window.document.getElementById("dsscocx");
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			//alert('调用控件方法1');
			ocxMainForCSClient("OpenVideoOnDemand",json);
		} else {
			//alert('调用控件方法2');
			dssocxObject.OpenVideoOnDemand(json);
		}		
		//alert('success!');
	} catch (e) {				
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"	 //标准化改为错误提示框
	}
}
/**
 * OCX下载EXCEL
 * 
 * @param url
 *            ：导出路径
 */
window.exportByOcx = function(url, type) {
	recoveryBodyArea();
	var downLoadOcx = top.window.document.getElementById("testOCX");
	if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
		ocxMainForCSClient("Download",''+ url + ","+type);
	} else {
		downLoadOcx.Download('' + url, type);
	}
}
/**
 * OCX下载EXCEL
 * 
 * @param url
 *            ：导出路径
 */
window.exportAllByOcx = function(json,type) {
	recoveryBodyArea();

	var downLoadOcx = top.window.document.getElementById("testOCX");
	if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
		ocxMainForCSClient("DownloadEx",''+ JSON.stringify(json) + ","+type);
	} else {
		eval("var json = '"+JSON.stringify(json)+"';");
		downLoadOcx.DownloadEx(json,type);
	}
	
}
/**
 * 图片去雾
 */
window.clearFogFun = function(url){
	try{ 	
		if (typeof(isClientUser)!='undefined' && isClientUser == "true") {
			ocxMainForCSClient("PictureClearFog",url);
		} else {
			var dssocxObject = top.window.document.getElementById("dsscocx");
			dssocxObject.PictureClearFog(url);
		}
	}catch(e){
		alert($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
	}
}


/**
 * cs客户端调用ocx方法的方式
 * @param method
 * @param ocxParam
 * @return
 */
function ocxMainForCSClient(method,ocxParam){
	try{ 		
		if(isIE()){   
			 window.external.MainCall("initComplete","");//客户端有个固定判断，必须要调用了这个方法才能执行下去
	         if(ocxParam == null){        	
	             window.external.MainCall(method,"");	          
	         }else{
	             window.external.MainCall(method, ocxParam);
	         }
		} else {          
            if(ocxParam == null){
                servers.MainCall(method,"");           
            }else{
                servers.MainCall(method, ocxParam);
            }
		}		
	}catch(e){
		errorDialog($.jQi18Home.ocxLoadErrorTip2);//"加载dsscocx控件出错"
		recoveryBodyArea();
	}		
}

/******************数字法庭start*******************/
var OcxSystem = {
	/**
	 * 初始化ocx
	 */
	initDsscOcx: function(){
		this.loginDsscOcx({
			cmsIp: ocxConfig.cmsIp,
			cmsPort: ocxConfig.cmsPort,
			loginName: ocxConfig.loginName,
			loginPass: ocxConfig.loginPass
		});
		
	},
	
	/**登录OCX*/
	loginDsscOcx:function(params){
			try{
				setTimeout(function(){
					document.getElementById("dsscocx").Login(params.cmsIp, params.cmsPort,params.loginName, params.loginPass,"1");
					document.getElementById("dsscocx").SetNetFlag(1);
				},5000);
			}catch(e){
				//alert("控件未加载或者加载出错，请重新下载安装并重启IE浏览器！");
				//跳转到下载页面
			}
	},
	
	/**
	 * 登出ocx
	 */
	exitOcx:function(){		
		try
		{
			dsscocx.Logout(0);
		}
		catch(e){}
	}
};
/**
 * 下载录像
 * @param videoInfo
 * @return
 */
function downloadVideos(videoInfo) {
	dsscocx.SetBlockCarRecordInfo(videoInfo);
}

/**
 * 业务层ocx调用
 */
var OcxManager = {
	
	dsscocx: top.window.document.getElementById("dsscocx"),
	
	/**
	 * cs客户端调用ocx方法的方式
	 * @param method
	 * @param ocxParam
	 * @return
	 */
	ocxMainForCSClient:function(method,ocxParam){
		try{
			if(isIE()){   
				 window.external.MainCall("initComplete","");//客户端有个固定判断，必须要调用了这个方法才能执行下去
		         if(ocxParam == null){        	
		             window.external.MainCall(method,"");	          
		         }else{
		             window.external.MainCall(method, ocxParam);
		         }
			} else {
	            if(ocxParam == null){
	                servers.MainCall(method,"");           
	            }else{
	                servers.MainCall(method, ocxParam);
	            }
			}		
		}catch(e){
			alert('加载dsscocx控件出错');
		}
	},
	
	/**
	 * 模块显示(iframe里面调用此方法)
	 * 
	 * @param videoType =  立案： 2  信访：3  运维：1  法庭：4
	 * 
	 * @param funModule =  实时： 0  录像：1 
	 * 
	 * */
	showModule:function(videoType, funModule){
		try
		{
			OcxManager.dsscocx.UiMainCall(videoType, funModule);
			
		}catch(e){}
	},
	
	/**
	 * 模块显示（顶层调用此方法）
	 * 
	 * @param videoType =  立案： 2  信访：3  运维：1  法庭：4
	 * 
	 * @param funModule =  实时： 0  录像：1 
	 * 
	 * */
	topShowModule: function(videoType, funModule){
		try
		{
			document.getElementById("dsscocx").UiMainCall(videoType, funModule);
			
		}catch(e){}
	},

	/**
	 * 调用ocx接口
	 * 
	 * @param videoType =  立案： 2  信访：3  运维：1  法庭：4
	 * 
	 * @param funModule =  实时： 0  录像：1 
	 * @return
	 */
	playOcx: function(videoType, funModule){
		
		OcxManager.showModule(videoType, funModule);
		
	},
	/**
	 * 直播OCX
	 */
	currentVideo: function(config){
		var _config = {
			left: 230,
			top: 200,
			videoStr : "{\"devCode\":\"1000007\",\"courtUserName\":\"hi\",\"courtTrialIp\":\"172.6.5.80\",\"caseNum\":\"案号\",\"chiefJudge\":\"审批长\"}"
		}
		//{"id":83387,"caseMark":"121800000043960","num":1,"courtNum":"A70","courtName":"江苏省连云港市中级人民法院","courtCount":1,"courtDate":"20140824","holdCourtTime":"09:00","endTime":"16:00","courtPlace":"本院审判庭","holdCourt":"320700123","videoPath":"http://10.32.1.100:8080/service/vodCaseAction.action?ttrialplan.trialplanid\u003d506C1CD3_8817_B585_45E1_DE101F5CEE0D","picPath":"img/video-have-source.bmp","recStat":0,"msgCount":1,"deanRecommend":1,"chiefJudge":"乙斌","caseNum":"(2012)连民终字第1128号","caseType":"2211","supportDept":"32070006","caseCauseDec":"提供劳务者受害责任纠纷","involvePerson":"上诉人:曙光控股集团有限公司连云港分公司;被上诉人:卫忠年;原审被告:曙光控股集团有限公司,郑国财","supportor":"320700yib","reporterName":"","caseDate":"20121213","holdCourtState":1,"rtspUrl":"126:192.168.129.25:4000:16:4|613:192.168.129.25:4000:18:4|614:192.168.129.25:4000:20:4|615:192.168.129.25:4000:22:4|","notesUrl":"/ftp/caseManage/121800000043960/1/CourtNote2014-08-24.doc","videoSwitch":1,"devCode":"1000016","courtUserName":"zql","courtTrialIp":"192.200.13.39"}
		var videoStr = config.videoStr;//"{\"devCode\":\"1000007\",\"courtUserName\":\"hi\",\"courtTrialIp\":\"172.6.5.80\",\"caseNum\":\"案号\",\"chiefJudge\":\"审批长\"}";
		OcxManager.showModule(31, videoStr);
		$('#ocxDiv').css({'position':'absolute',left: 230, top: 10, display: 'block', height: 620, width: 1020});
	},
	/**
	 * 一键开庭
	 */
	OneKeyOpenCourt: function(){
		OcxManager.dsscocx.OneKeyOpenCourt();
	},
	
	/**
	 * 开庭审理
	 */
	openCourt: function(){
		OcxManager.dsscocx.OpenCourt();
	},
	
	/**
	 * 闭庭
	 */
	closeCourt: function(){
		OcxManager.dsscocx.CloseCourt();
	},
	
	/**
	 * 休庭
	 */
	pauseCourt: function(){
		OcxManager.dsscocx.PauseCourt();
	},
	/**
	 * 复庭
	 */
	resumeCourt: function(){
		OcxManager.dsscocx.ResumeCourt();
	},
	
	callDownLoadWindows: function(strCourtSearchInfo){
		OcxManager.dsscocx.CallDownLoadWindows(strCourtSearchInfo);
	},
	/**
	 * 头部下载刻录
	 */
	onShowDownLoadDlg: function(){
		document.getElementById("dsscocx").OnShowDownLoadDlg();
	}
}
/******************数字法庭end*******************/