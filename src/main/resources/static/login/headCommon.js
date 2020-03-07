/*************************/
/*该JS主要处理公用引用JS/CSS*/
/*     2012-12-28       */
/*      By HanLulu       */
/** ********************* */

/**
 * 输出引用文件
 * 
 * @param jsFiles
 * @param cssFiles
 * @return
 */
var isItmsPage = false;
var authOpen = 1;
var windowScreen = screen.width;
var lang ="zh";
var cssFolder = "newBlue";//当前css文件夹名称  可在 config.xml（<css><folderName>）中配置
var productId = 1;

try {
	var srcEm = document.getElementsByTagName('script')[1].src;
	var type = getEmlementByUrl(srcEm, "type");
    lang = getEmlementByUrl(srcEm, "lang");
    cssFolder = getEmlementByUrl(srcEm, "cssFolder");
    productId = getEmlementByUrl(srcEm, "productId");
    if(lang==''||typeof(lang)=='undefined'){
    	 lang ="zh";
    }
    if(cssFolder==''||typeof(cssFolder)=='undefined'||cssFolder=='null'||cssFolder==null){
    	cssFolder = "newBlue"
    }
    if(productId==''||typeof(productId)=='undefined'){
    	productId = 1;
    }
} catch (e) {
	alert("公用JS页面引用写入不正常");
}

var jsFiles;
var cssFiles
if (type == 'index') {
	includesIndexJsAndCssFiles();
} else if (type == 'home') {
	includesHomeJsAndCssFiles();
}else if(type=='newHome'){
	includesNewHomeJsAndCssFiles();
}else if(type=='dcpHome'){
	includesDcpHomeJsAndCssFiles();
}else if (type == 'tree') {
	includesTreeJsAndCssFiles();
}else if (type == 'jwdctree'){
	includesJwdcTreeJsAndCssFiles();
}else if (type == 'jsptTree'){
	includesJsptTreeJsAndCssFiles();
}else if(type == 'tree3_1'){
	includesTreeJsAndCssFiles3_1();
}else if(type=='secondLevelFrame'){
	includesSecondLevelFrameJsAndCssFiles();
}else if(type=='secondLevelDcpFrame'){
	includesSecondLevelDcpFrameJsAndCssFiles();
}else if(type=='login'){
	includesLoginJsAndCssFiles();
}else if(type=='newLogin'){
	includesNewLoginJsAndCssFiles();
}else if(type=='dcpLogin'){
	includesDcpLoginJsAndCssFiles();
}else if(type=='jwdc'){
	includesJwdcJsAndCssFiles();
}else if(type=='nbsj'){
	includesNbsjJsAndCssFiles();
}else if(type=='videoMgtLogin'){
	includesVideoMgtLoginJsAndCssFiles();
}else if(type=='videoMgtMain'){
	includesVideoMgtMainJsAndCssFiles();
}else {
	includesMainJsAndCssFiles();
}
customizedIncludesByProductId();
writeCssAndJs(jsFiles, cssFiles, appPath);

var imageCommonPath =  appPath + 'include/styles/'+cssFolder+'/images/';
function includesSecondLevelFrameJsAndCssFiles() {
	jsFiles = [ "include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
	    			"include/script/jquery.form.js",
	    			"include/script/json2.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/emapFun.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/piczoom_new.js",
	    			"include/script/dahuaDefined/uploadAtt.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/exportFun.js",
	    			"include/script/dahuaDefined/cssFun.js",
	    			"include/script/dahuaDefined/jquery.pageGrid.js",
	    			"include/script/dahuaDefined/jquery.pageList.js",
	    			"include/script/dahuaDefined/jquery.pageOperBar.js",
	    			"include/script/dahuaDefined/jquery.pageLeftInfo.js",
	    			"include/script/dahuaDefined/jquery.keyEvent.js",
	    			"include/script/dahuaDefined/jquery.select.js",
	    			"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
	    			"include/script/dahuaDefined/windowEnhance.js",
	    			"include/script/formValidator/formValidatorRegex.js",
	    			"include/script/formValidator/formValidator.js",
	    			"include/script/dahuaDefined/treeFun.js"
	    	];
	cssFiles = ["include/styles/"+cssFolder+"/css/mainCss.css","include/styles/"+cssFolder+"/css/reset.css","include/script/formValidator/css/validator.css"];
}
function includesSecondLevelDcpFrameJsAndCssFiles() {
	jsFiles = [
					"dcp/script/common/jquery/jquery-1.8.js",
	    			//"include/script/jquery.form.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/exportFun.js",
	    			"include/script/dahuaDefined/windowEnhance.js",
	    			"include/script/formValidator/formValidatorRegex.js",
	    			"include/script/formValidator/formValidator.js",
//	    			"include/script/dahuaDefined/treeFun.js",
	    			
	    			"dcp/script/util/dcpCssFun.js",
					"dcp/script/common/jquery/jquery.cookie.js",
					"dcp/script/common/jquery/jquery.form.js" ,
					"dcp/script/common/JQuery_zTree_v3.1/js/jquery.ztree.core-3.1.js",
					"dcp/script/common/JQuery_zTree_v3.1/js/jquery.ztree.excheck-3.1.js",
					"dcp/script/common/JQuery_zTree_v3.1/js/jquery.ztree.exedit-3.1.js",
					"dcp/script/common/My97DatePicker/WdatePicker.js",
					"dcp/script/common/json2.js",
					"dcp/script/common/bootstrap.validation/jquery.validate.js" ,
	    			"dcp/script/util/ajax.js",
	    			"dcp/script/util/common.js",
	    			"dcp/script/util/page.js",
	    			"dcp/script/util/config.js",
	    			"dcp/script/util/MessageResource_zh-cn.js",
	    			"dcp/script/util/table.event.js",
	    			"dcp/script/util/org.tree.js",
	    			"dcp/script/util/select.js",
	    			"dcp/script/util/select.search.js",
//	    			"dcp/script/util/ranking.list.js",
	    			"dcp/script/util/importExport.js",
	    			"dcp/script/util/map.js",
	    			"dcp/script/util/validation.js",
	    			"dcp/script/util/MessageResource_zh-cn.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js",
	    			"dcp/script/util/org.tree.js"
	    	];
	    	cssFiles = [
	    	                cssFolder+"/styles/dcp.reset.css",
	    	                //"include/script/formValidator/css/validator.css",
	    	                cssFolder+"/styles/dcp.main.css",
	    	                cssFolder+"/styles/dcp.common.css",
	    	                cssFolder+"/styles/dcp.list.css",
	    	                cssFolder+"/styles/common/select.css",
	    	                cssFolder+"/styles/common/zTreeStyle/zTreeStyle.css"
	    	                ];
}

/**
 * 树相关引用
 * 
 * @return
 */
function includesTreeJsAndCssFiles() {
	jsFiles = [
			"include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
			"include/script/JQuery_zTree_v3.0/js/jquery.ztree.core-3.5.min.js",
			"include/script/JQuery_zTree_v3.0/js/jquery.ztree.excheck-3.5.min.js",
			"include/script/JQuery_zTree_v3.0/js/jquery.ztree.exedit-3.5.min.js",
			"include/script/dahuaDefined/jquery.ztree.device-3.0.js",
			"include/script/dahuaDefined/validateFun.js",
			"common/lang.jsp",
			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
			"include/script/dahuaDefined/commonFun.js",
			"include/script/json2.js",
			"include/script/dahuaDefined/windowEnhance.js"
	];
	cssFiles = ["include/styles/"+cssFolder+"/css/mainCss.css","include/styles/"+cssFolder+"/css/reset.css","include/script/JQuery_zTree_v3.0/css/zTreeStyle/zTreeStyle.css" ];
	var zTree = null;
}

function includesJwdcTreeJsAndCssFiles(){
		jsFiles = [
	           "include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
				"include/script/JQuery_zTree_v3.0/js/jquery.ztree.core-3.5.min.js",
				"include/script/JQuery_zTree_v3.0/js/jquery.ztree.excheck-3.5.min.js",
				"include/script/JQuery_zTree_v3.0/js/jquery.ztree.exedit-3.5.min.js",
				"jwdc/include/ztree/jquery.ztree.device-3.0.js",
				"include/script/dahuaDefined/validateFun.js",
				"common/lang.jsp",
				"include/script/lhgdialog/lhgdialog.js?self=true&skin=newBlue",
				"include/script/dahuaDefined/commonFun.js",
				"include/script/json2.js",
				"include/script/dahuaDefined/windowEnhance.js"
		];
		cssFiles = ["include/styles/"+cssFolder+"/css/mainCss.css","include/styles/"+cssFolder+"/css/reset.css","include/script/JQuery_zTree_v3.0/css/zTreeStyle/zTreeStyle.css" ];
		var zTree = null;
}

function includesJsptTreeJsAndCssFiles(){
	jsFiles = [
		        "include/script/dahuaDefined/pemCodeFun.js",
				"include/script/ztree3.1/js/jquery-1.4.4.min.js",
				"include/script/ztree3.1/js/jquery.ztree.core-3.1.min.js",
				"include/script/ztree3.1/js/jquery.ztree.excheck-3.1.min.js",
				"include/script/ztree3.1/js/jquery.ztree.exedit-3.1.min.js",
				"jspt/include/ztree/jquery.ztree.device-3.0.js",
				"include/script/dahuaDefined/validateFun.js",
				"common/lang.jsp",
				"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
				"include/script/dahuaDefined/commonFun.js",
				"include/script/json2.js",
				"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
				"include/script/dahuaDefined/windowEnhance.js",
				"include/script/formValidator/formValidatorRegex.js",
				"include/script/formValidator/formValidator.js",
				"include/script/dahuaDefined/ocxFun.js"
		];
		cssFiles = ["include/script/ztree3.1/css/zTreeStyle/zTreeStyle.css" ];
		var zTree = null;
}

/**
 * 树相关引用
 * 
 * @return
 */
function includesTreeJsAndCssFiles3_1() {
	jsFiles = [
			"include/script/ztree3.1/js/jquery-1.4.4.min.js",
			"include/script/ztree3.1/js/jquery.ztree.core-3.1.min.js",
			"include/script/ztree3.1/js/jquery.ztree.excheck-3.1.min.js",
			"include/script/ztree3.1/js/jquery.ztree.exedit-3.1.min.js",
			"include/script/dahuaDefined/jquery.ztree.device-3.0.js",
			"include/script/dahuaDefined/validateFun.js",
			"common/lang.jsp",
			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
			"include/script/dahuaDefined/commonFun.js",
			"include/script/json2.js",
			"include/script/dahuaDefined/windowEnhance.js",
			"include/script/formValidator/formValidatorRegex.js",
			"include/script/formValidator/formValidator.js"
	];
	cssFiles = ["include/script/ztree3.1/css/zTreeStyle/zTreeStyle.css" ];
	var zTree = null;
}


function includesIndexJsAndCssFiles() {
	jsFiles = [ "include/script/jquery-1.7.2.min.js",
	    			"include/script/jquery.cookie.js",
	    			"include/script/jquery.form.js",
	    			"include/script/dahuaDefined/validateFun.js", 
	    			"include/script/dahuaDefined/MelodySelect.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/loginFun.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js",
	    			"include/script/util/base64Strong.min.js"
	    			];
	cssFiles = ["include/styles/"+cssFolder+"/css/indexCss.css",
	            "include/styles/"+cssFolder+"/css/reset.css"];   	
	if(lang!="zh"){
		cssFiles =["include/styles/"+cssFolder+"/css/indexCss.css","include/styles/"+cssFolder+"/css/reset.css","include/styles/"+cssFolder+"/css/i18n/index_"+lang+".css"];
	}
}

function includesNbsjJsAndCssFiles() {
	jsFiles = [ "include/script/jquery-1.7.2.min.js",
	    			"include/script/jquery.cookie.js",
	    			"include/script/jquery.form.js",
	    			"include/script/dahuaDefined/validateFun.js", 
	    			"include/script/dahuaDefined/MelodySelect.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin=purple",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/loginFun.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js"
	    			];
	cssFiles = ["include/styles/nbsj/css/reset.css",
	                "include/styles/nbsj/css/login.css"];   	
	if(lang!="zh"){
		cssFiles =["include/styles/nbsj/css/login.css","include/styles/nbsj/css/reset.css","include/styles/nbsj/css/i18n/index_"+lang+".css"];
	}
}

function includesVideoMgtLoginJsAndCssFiles(){
	jsFiles = [ "include/script/jquery-1.7.2.min.js",
	            	"include/script/jquery.cookie.js",
	                "include/script/dahuaDefined/MelodySelect.js",
	    			"include/script/jquery.form.js",
	    			"include/script/json2.js",
	    			"include/script/Highcharts-3.0.5/highcharts.src.js",
	    			"include/script/Highcharts-3.0.5/modules/exporting.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/commonCharts.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin=purple",
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/loginFun.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js"
	    			];
	cssFiles = ["include/styles/nbsj/css/reset.css","include/styles/nbsj/css/login.css","include/styles/nbsj/css/videomgt.css"];   	
}
function includesVideoMgtMainJsAndCssFiles(){
	 jsFiles = [ "include/script/jquery-1.7.2.min.js",
	            	"include/script/jquery.cookie.js",
	                "include/script/dahuaDefined/MelodySelect.js",
	    			"include/script/jquery.form.js",
	    			"include/script/json2.js",
	    			"include/script/Highcharts-3.0.5/highcharts.src.js",
	    			"include/script/Highcharts-3.0.5/modules/exporting.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/commonCharts.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin=white",
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/loginFun.js"
	    			];
	 cssFiles = ["include/styles/nbsj/css/videomgt.css"];   	
}
function includesLoginJsAndCssFiles(){
	jsFiles = [ "include/script/jquery-1.7.2.min.js",
	            	"include/script/jquery.cookie.js",
	                "include/script/dahuaDefined/MelodySelect.js",
	    			"include/script/jquery.form.js",
	    			"include/script/json2.js",
	    			"include/script/Highcharts-3.0.5/highcharts.src.js",
	    			"include/script/Highcharts-3.0.5/modules/exporting.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/commonCharts.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/loginFun.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js"
	    			];
	cssFiles = ["include/styles/"+cssFolder+"/css/portalCss.css","include/styles/"+cssFolder+"/css/reset.css"];   	
}


function includesHomeJsAndCssFiles() {
	jsFiles = [ "include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
	                "include/script/jquery-ui-1.8.16/js/jquery-ui-1.8.16.custom.min.js",
	    			"include/script/jquery.form.js",
	               // "itms/openjs/jquery/jquery-1.8.3.js",
			       // "itms/openjs/jqueryui/jquery-ui-1.10.3.js",
					"include/script/dahuaDefined/validateFun.js",
					"include/script/dahuaDefined/cssFun.js",
					"include/script/jquery.bgiframe.min.js",
					"include/script/pushlet/ajax-pushlet-client.js",
					"include/script/dahuaDefined/pemCodeFun.js",
					"common/lang.jsp",
					"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
					"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
					"include/script/dahuaDefined/commonFun.js",
					"include/script/ligerUI/lib/ligerUI/js/plugins/ligerMenu.js",
					"include/script/dahuaDefined/jquery.ligerTab.js",
					"include/script/dahuaDefined/ocxFun.js",
					"include/script/json2.js",
					"include/script/dahuaDefined/homeFun.js",
					"include/script/dahuaDefined/windowEnhance.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/flowIcons.js",
	    			"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js",
	    			"include/script/jquery.cookie.js"
					];
	
	cssFiles = [ "include/styles/"+cssFolder+"/css/homeCss.css","include/styles/"+cssFolder+"/css/reset.css"];
	if(lang!="zh"){
		cssFiles =["include/styles/"+cssFolder+"/css/homeCss.css","include/styles/"+cssFolder+"/css/reset.css","include/styles/"+cssFolder+"/css/i18n/home_"+lang+".css"];
	}
}
function includesJwdcJsAndCssFiles(){
	jsFiles = [ "include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
	            "include/script/jquery-1.8.js",
    			"include/script/jquery.form.js",
    			"include/script/json2.js",
    			"include/script/jquery.qtip-1.0.0-rc3.min.js",
    			"include/script/Highcharts-3.0.5/highcharts.src.js",
    			"include/script/Highcharts-3.0.5/modules/exporting.js",
    			"include/script/dahuaDefined/validateFun.js",
    			"include/script/dahuaDefined/commonCharts.js",
    			"include/script/dahuaDefined/emapFun.js",
    			"include/script/dahuaDefined/jquery.piczoom.js",
    			"include/script/dahuaDefined/uploadAtt.js",
    			"include/script/dahuaDefined/ocxFun.js",
    			"include/script/dahuaDefined/pemCodeFun.js",
    			"common/lang.jsp",
    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
    			"include/script/dahuaDefined/commonFun.js",
    			"include/script/dahuaDefined/exportFun.js",
    			"include/script/dahuaDefined/cssFun.js",
    			"include/script/dahuaDefined/jquery.pageGrid.js",
    			"include/script/dahuaDefined/jquery.pageList.js",
    			"include/script/dahuaDefined/jquery.pageOperBar.js",
    			"include/script/dahuaDefined/jquery.pageLeftInfo.js",
    			"include/script/dahuaDefined/jquery.pagePartMore.js",
    			"include/script/dahuaDefined/jquery.pageImages.js",
    			"include/script/dahuaDefined/jquery.keyEvent.js",
    			"include/script/dahuaDefined/jquery.select.js",
    			"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
    			"include/script/dahuaDefined/windowEnhance.js",
    			"include/script/formValidator/formValidatorRegex.js",
    			"include/script/formValidator/formValidator.js",
    			"include/script/dahuaDefined/treeFun.js",
    			//"include/script/formValidator/jquery.validate.js",
    			"jwdc/script/util/common.js",
    			"jwdc/script/util/ajax.js"
    	];
    	cssFiles = ["jwdc/styles/patrol/mainCss.css","jwdc/styles/patrol/reset.css"];
}
function includesMainJsAndCssFiles() {
	jsFiles = [ "include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
	    			"include/script/jquery.form.js",
	    			"include/script/json2.js",
	    			"include/script/jquery.qtip-1.0.0-rc3.min.js",
	    			//"include/script/Highcharts-3.0.5/highcharts.js",
	    			"include/script/Highcharts-3.0.5/highcharts.src.js",
	    			"include/script/Highcharts-3.0.5/modules/exporting.js",
	    			//"include/script/jquery.fancybox-1.3.4/fancybox/jquery.fancybox-1.3.4.pack.js",
	    			//"include/script/jquery.fancybox-1.3.4/fancybox/jquery.mousewheel-3.0.4.pack.js",
	    			"include/script/dahuaDefined/validateFun.js",
	    			"include/script/dahuaDefined/commonCharts.js",
	    			"include/script/dahuaDefined/emapFun.js",
	    			"include/script/dahuaDefined/jquery.piczoom.js",
	    			"include/script/dahuaDefined/uploadAtt.js",
	    			"include/script/dahuaDefined/ocxFun.js",
	    			"include/script/dahuaDefined/pemCodeFun.js",
	    			"common/lang.jsp",
	    			"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
	    			"include/script/dahuaDefined/commonFun.js",
	    			"include/script/dahuaDefined/exportFun.js",
	    			"include/script/dahuaDefined/cssFun.js",
	    			"include/script/dahuaDefined/jquery.pageGrid.js",
	    			"include/script/dahuaDefined/jquery.pageList.js",
	    			"include/script/dahuaDefined/jquery.pageOperBar.js",
	    			"include/script/dahuaDefined/jquery.pageLeftInfo.js",
	    			"include/script/dahuaDefined/jquery.pagePartMore.js",
	    			"include/script/dahuaDefined/jquery.pageImages.js",
	    			"include/script/dahuaDefined/jquery.keyEvent.js",
	    			"include/script/dahuaDefined/jquery.select.js",
	    			"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
	    			"include/script/dahuaDefined/windowEnhance.js",
	    			"include/script/formValidator/formValidatorRegex.js",
	    			"include/script/formValidator/formValidator.js",
	    			"include/script/showTreeFun.js",
	    			"include/script/dahuaDefined/treeFun.js",
	    			"include/script/util/base64.js"
	    	];
			var prodPrefix= "";
			if(productId == '6'){
				prodPrefix = "_jwdc";
			}
	    	cssFiles = ["include/styles/"+cssFolder+"/css/mainCss"+prodPrefix+".css","include/styles/"+cssFolder+"/css/reset.css"];
	    	if(lang!="zh"){
	    		cssFiles =["include/styles/"+cssFolder+"/css/mainCss"+prodPrefix+".css","include/styles/"+cssFolder+"/css/reset.css","include/styles/"+cssFolder+"/css/i18n/main_"+lang+".css"];
	    	}
}

function includesNewLoginJsAndCssFiles(){
    jsFiles = [ "include/script/jquery-1.7.2.min.js",
                    "include/script/jquery.cookie.js",
                    "include/script/dahuaDefined/validateFun.js", 
                    "common/lang.jsp",
                    "include/script/lhgdialog/lhgdialog.js?self=true&&skin=windows8",
                    "include/script/dahuaDefined/ocxFun.js",
                    "include/script/encryption/md5.customized.js",
                    "include/script/dahuaDefined/loginFun.js",
                    "include/script/encryption/des.js"
                    ];
    cssFiles = ["include/styles/newlogin/css/reset.css","include/styles/newlogin/css/login.css"];
}

function includesDcpLoginJsAndCssFiles(){
	jsFiles = [ "include/script/jquery-1.7.2.min.js",
    			"include/script/jquery.cookie.js",
    			"include/script/dahuaDefined/validateFun.js", 
    			"include/script/dahuaDefined/MelodySelect.js",
    			"common/lang.jsp",
    			"include/script/encryption/md5-min.js",
    			"include/script/encryption/md5.customized.js",
    			"include/script/encryption/des.js",
    			"include/script/dahuaDefined/ocxFun.js",
    			"include/script/dahuaDefined/loginFun.js"
    			];
	cssFiles = ["include/styles/"+cssFolder+"/css/indexCss.css","include/styles/"+cssFolder+"/css/reset.css"];   	
	if(lang!="zh"){
		cssFiles =["include/styles/"+cssFolder+"/css/indexCss.css","include/styles/"+cssFolder+"/css/reset.css","include/styles/"+cssFolder+"/css/i18n/index_"+lang+".css"];
	}
}

function includesNewHomeJsAndCssFiles(){
    jsFiles = [ "include/script/jquery-1.7.2.min.js",
                    "include/script/dahuaDefined/validateFun.js",
                    "common/lang.jsp",
                    "include/script/lhgdialog/lhgdialog.js?self=true&&skin=windows8",
                    "include/script/Highcharts-3.0.5/highcharts.src.js",
                    "include/script/dahuaDefined/cssFun.js",
                    "include/script/dahuaDefined/ocxFun.js",
                    "include/script/My97DatePicker/WdatePicker.js?skin=blue",
                    "include/script/jquery.form.js",
                    "include/script/dahuaDefined/newHomeFun.js"];
    cssFiles = ["include/styles/newlogin/css/reset.css","include/styles/newlogin/css/home.css"];
}

function includesDcpHomeJsAndCssFiles(){
    jsFiles = [
					"include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js",
					"include/script/jquery-ui-1.8.16/js/jquery-ui-1.8.16.custom.min.js",
					"include/script/jquery.form.js",
					"include/script/dahuaDefined/validateFun.js",
					"dcp/script/util/dcpCssFun.js",
					"include/script/jquery.bgiframe.min.js",
					"include/script/dahuaDefined/pemCodeFun.js",
					"common/lang.jsp",
					"include/script/lhgdialog/lhgdialog.js?self=true&skin="+cssFolder,
					"include/script/My97DatePicker/WdatePicker.js?skin="+cssFolder,
					"include/script/dahuaDefined/commonFun.js",
					"include/script/ligerUI/lib/ligerUI/js/plugins/ligerMenu.js",
					"include/script/dahuaDefined/jquery.ligerTab.js",
					"include/script/json2.js",
					"dcp/script/util/dcpHomeFun.js",
					"include/script/dahuaDefined/windowEnhance.js",
					"include/script/encryption/md5.customized.js",
					"include/script/encryption/md5-min.js",
	    			"include/script/encryption/md5.customized.js",
	    			"include/script/encryption/des.js",
					"include/script/jquery.cookie.js",
					"include/script/dahuaDefined/ocxFun.js"
                  ];
    cssFiles = [cssFolder+"/styles/dcp.reset.css",cssFolder+"/styles/dcp.home.css",cssFolder+"/styles/dcp.common.css"];
}

function customizedIncludesByProductId(){
	if(productId == 4 || productId == 9){	// 司法审讯
		
		// 通用
		jsFiles.push("dip/script/util/common.js");
		jsFiles.push("dip/script/util/datestartendinit.js");
		jsFiles.push("dcp/script/common/bootstrap.validation/jquery.validate.js");
		jsFiles.push("dip/script/util/validation.js");
		for(i = 0; i < jsFiles.length; i++){
			if(jsFiles[i].indexOf("cssFun.js") != -1){
				jsFiles[i] = "dip/script/util/dipCssFun.js";
			} else if(jsFiles[i].indexOf("homeFun.js") != -1){
				jsFiles[i] = "dip/script/util/dipHomeFun.js";
			}
		}
		
		// 登录页
		if(type == 'index'){
			for(i = 0; i < jsFiles.length; i++){// 替换定制过的
				if(jsFiles[i].indexOf("indexCss.css") != -1){
					jsFiles[i] = "dip/script/util/indexCss.css";
				}
			}
		// 非登录页
		} else {
			cssFiles.push("dip/styles/dip.reset.css");
			cssFiles.push("dip/styles/dip.common.css");
			cssFiles.push("dip/styles/common/select.css");
			cssFiles.push("dip/styles/common/zTreeStyle/zTreeStyle.css");
			cssFiles.push("dip/styles/dip.list.css");
			// 框架页HOME
			if(type == 'home'){
				cssFiles.push("dip/styles/dip.home.css");
			// 非框架页
			} else {
				cssFiles.push("dip/styles/dip.main.css");
			}
		}
		
	}
}

function writeCssAndJs(jsFiles, cssFiles, appPath) {
	for ( var i = 0; i < jsFiles.length; i++) {
		var jsFileImport = "<script type='text/javascript' src='" + appPath
				+ jsFiles[i] + "'></script>";
		document.writeln(jsFileImport);
	}

	for ( var i = 0; i < cssFiles.length; i++) {
		var cssFileImport = "<link rel='stylesheet' type='text/css' "
				+ "href='" + appPath + cssFiles[i] + "'></link>";
		document.writeln(cssFileImport);
	}
}



/**
 * 获取JS路径参数值
 * 
 * @param srcEm
 * @param emlement
 * @return
 */
function getEmlementByUrl(srcEm, emlement) {
	var temp = srcEm.split(emlement + "=")[1];
	if (typeof (temp) != "undefined" && temp != '') {
		temp = temp.split("&")[0];
	}
	return temp;
}

/**
 * 小分辨率替换样式
 * 
 * @param path
 * @return
 */
function changeStylePath(path) {
	if ( windowScreen <= 1024) {
		path = path.replace("default", "defaultMin");
	}
	return path;
}


/**
 * IE6需要引用JS/CSS
 * 
 * @return
 */
function ie6IncludesJsAndCssFiles() {
	var jsFileImport = "<script type='text/javascript' src='" + appPath
			+ "include/script/DD_belatedPNG.js'></script>";
	if (!window.XMLHttpRequest) {
		document.writeln(jsFileImport);
	}
}
/**
 * IE6对图片进行处理
 * 
 * @param type
 * @return
 */
function ie6Pic(type) {
	if (checkBrowser() == "IE6") {
		if (typeof (DD_belatedPNG) == "object") { // 如果有加载DD_belatedPHG.js,则对IE6不支持png图做绑定处理
			if (type == 'index') {
				DD_belatedPNG.fix('a.btn,#info a');
			} else if (type == 'home') {
				DD_belatedPNG
						.fix('.alarm_no,a:hover ul li.corner_l,a:hover ul li.corner_r');
			} else {
				DD_belatedPNG
						.fix('ul.btn_num li a,.cz_cancel,.ico_export,.ico_import,.ico_lock,.ico_delete,.ico_add,a.cz_search,a:hover.cz_search,.cz_export,a.cz_edit,a:hover.cz_edit,a.cz_lock,a:hover.cz_lock,a.cz_unlock,a:hover.cz_unlock,a.cz_add,a:hover.cz_add,a.cz_delete,a:hover.cz_delete,a.cz_video,a:hover.cz_video,a.cz_download,a:hover.cz_download,.ico_ok,.ico_warn,.dot1,.dot2,.dot3,#alarm_no,#alarm_yes,#previewbg_lt,#previewbg_rt,#previewbg_lb,#previewbg_rb,#gridbg_lt,#gridbg_rt,#gridbg_lb,#gridbg_rb,#tranbg_lt,#tranbg_rt,#tranbg_lb,#tranbg_rb,#tree_lt,#tree_rt,#tree_lb,#tree_rb,#tips,.ico_docu');
			}
		}
	}

}