/**
* Description: MelodySelect
* Author: 曹鹏
* Email: cao_peng@dahuatech.com
* version: 1.0.3
* Build time: 2012-10-17
* Example usage:
   var select = new MelodySelect("coding", 140);                 //创建select框控件，控件name=coding 。
   select.add(new Option("001", "请选择", true),0);               //添加第1个下拉选项，并设置该项为默认选项。
   select.add(new Option("002", "你", false),1);                 //添加第2个下拉选项。
   select.add(new Option("003", "我", false),2);                 //添加第3个下拉选项。
   var option = new new Option("004", "他", false);              //生成某个下拉选项对象
   select.add(option,3);                                         //将上面选项对象添加到第4个选项中。
   select.add(option);                                           //将上面选项对象添加到第5个选项中。缺省写第二个参数，则自动增加到最后的选项中。
   select.add(option, 10);                                       //直接添加到第11个选项中。需要注意的是6~10选项为空。不推荐跳曾选项。
   select.remove();												 //删除第一个选项（下标为0的被删除）。还剩10个选项，下标0~9。
   select.remove(8);											 //指定删除下标为8的选项，其后的选项下标一次往前移1。
   alert(select.options[0].text);                                //打印第一个选项的文本内容。
   alert(select.options[0].value);                               //打印第一个选项的值。
   //alert(select.options[selectIndex].text);                       //打印被选中选项的文本内容。未实现。
   //alert(select.options[selectIndex].value);                      //打印被选中选项的值。未实现。
   
   
   var select2 = new MelodySelect("deviceid", 165, true, 120, true, "search");  //创建select框，控件name=deviceid。选中某一个下拉项执行search函数。
   select2.add(new Option('-1', '全部', false), '0');
   select2.add(new Option('0', '存储异常', false), '1');
   select2.add(new Option('1', '数据库连接异常', false), '2');
   select2.add(new Option('2', '没有识别狗', false), '3');
   select2.add(new Option('3', '接收请求异常', false), '4');
   select2.add(new Option('4', '写图片失败', false), '5');
   select2.setSelectPosition(10, 10);                             //设置下拉框相对于选择框的相对位置
   
   
* 
**/

/**
 * 参数说明：
 * _property: 必选项。select框的name。
 * _width: 可选项。select框的指定宽度。
 * _check: 可选项。是否为多选，true为多选。当该项为空时默认false单选。
 * _height: 可选项。select框的指定高度。
 * _load: 可选项。是否重新生成select的内容。true为重新生成，false为不重新生成，默认为true。
 * _callbackName: 可选项。单选时，选中某一个下拉菜单后执行函数。
 */
function MelodySelect(_property, _width, _check, _height, _load, _callbackName) {
	var combo = this;
	this.options = new Array();
	this.selectIndex = 0;
	this.property = "";
	this.text = "";
	this.value = "";
	this.width = 160;
	this.height= 120;
	this.selectOffTop = 0;
	this.selectOffLeft = 0;
	this.load = true;
	this.multiSelection = false;
	this.readonly = true; //只读。暂不开放可写操作，将来有需要可以将其设置为false，表示可写。
	this.callbackName = "";
	
	if(_property == null) {
		return null;
	} else {
		this.property = _property;
	}
	
	if(_width != null) {
		this.width = _width;
	}
	if(_height != null) {
		this.height = _height;
	}
	if(_check != null && (_check == true || _check == "true")) {
		this.multiSelection = true;
	} else {
		this.multiSelection = false;
	}
	
	if(_load == null || _load == true || _load == "true") {
		this.load = true;
	} else {
		this.load = false;
	}
	
	if(_callbackName != null) {
		this.callbackName = _callbackName;
	}
	var size= this.width/10;
	
	document.write("<span class=\"inputbg_l\"></span>");
	document.write("<span class=\"inputbg_m\">");
    document.write("<input type=\"text\" id=\""+this.property+"_selectText\" name=\""+this.property+"_selectText\" value=\""+this.text+"\" class=\"text\" size="+size+" style=\"width:"+this.width+"px\" readonly=\""+this.readonly+"\" />");
	document.write("<input type=\"hidden\" id=\""+this.property+"_selectValue\" name=\""+this.property+"\" value=\""+this.value+"\" />");


	document.write("</span>");
	document.write("<span class=\"inputbg_r\"></span>");
	document.write("<a id=\""+this.property+"_selectA\" class=\"btn_down\" href=\"javascript:void(0);\"></a>");

	/*
	由于可能引起jquery冲突的问题，请打开这段，注释上面的document.write这段。具体原因尚不明确。
	var innerHtml = "<span class=\"inputbg_l\"></span>";
	innerHtml += "<span class=\"inputbg_m\" style=\"width:"+(parseInt(this.width))+"px\">";
	innerHtml += "<input type=\"text\" id=\""+this.property+"_selectText\" name=\""+this.property+"_selectText\" value=\""+this.text+"\" class=\"text\" size="+size+" style=\"width:"+(parseInt(this.width)-20)+"px\" readonly=\""+this.readonly+"\" />";
	innerHtml += "<input type=\"hidden\" id=\""+this.property+"_selectValue\" name=\""+this.property+"\" value=\""+this.value+"\" />";

	
	innerHtml += "</span>";
	innerHtml += "<a id=\""+this.property+"_selectA\" class=\"btn_down\" href=\"javascript:void(0);\"></a>";
	
	$("#"+this.property+"Div").append(innerHtml);	
	*/
	if(this.readonly) {
		document.getElementById(this.property+"_selectText").onclick=function() {
			combo.show();
		}
	}
	document.getElementById(this.property+"_selectA").onclick=function() {
		combo.show();
	}
	
	
}

//add(option [, iIndex])
MelodySelect.prototype.add = function(option, iIndex) {
	if(iIndex != null) {
		this.options[iIndex]=option;
	} else {
		this.options[this.options.length]=option;
	}
	
	if(option.selected) {
		if (this.selectText == null) {
			this.selectText = document.getElementById(this.property+"_selectText");
		}
		if (this.selectValue == null) {
			this.selectValue = document.getElementById(this.property+"_selectValue");
		}
		this.selectText.value = option.text;
		this.selectValue.value = option.value;
	}
}

//remove([iIndex])
MelodySelect.prototype.remove = function(iIndex) {
	if(iIndex == null) {
		iIndex = 0;
	}
	for(var i=iIndex; i<this.options.length; i++) {
		this.options[i] = null;
		this.options[i] = this.options[i+1];
	}
	var obj=this.options.pop();
	obj=null;
}
MelodySelect.prototype.removeAll = function(){
	for(var i=0; i<this.options.length; i++) {
		this.options[i] = null;
	}
	this.options=null;
	this.options=new Array();
}

MelodySelect.prototype.show = function() {
	this.selectFrame = document.getElementById(this.property+"_selectFrame");
	if(this.selectFrame == null) {
		var oIframe = document.createElement("iframe");
		oIframe.setAttribute("id", this.property+"_selectFrame");
		oIframe.setAttribute("frameborder", "0", 0);
		
		if(window.navigator.appName.indexOf("Explorer")>-1) {
			oIframe.style.cssText = "position:absolute;display:none;overflow-x:hidden;overflow-y:auto;border:1px solid #BCC2D6; z-index:9998;";
		} else {
			oIframe.setAttribute("style", "position:absolute;display:none;overflow-x:hidden;overflow-y:auto;border:1px solid #BCC2D6; z-index:9998;");
		}
		
		oIframe.onmouseout = function() {
			oIframe.style.display="none";
		}
		
		document.body.appendChild(oIframe);
		this.selectFrame = oIframe;//document.getElementById(this.property+"_selectFrame");
	} else {
		if(!this.load) {
			this.selectFrame.style.display="block";
			return;
		}
	}
   
	if (this.selectText == null) {
		this.selectText = document.getElementById(this.property+"_selectText");
	}
   
	var oDiv = document.createElement("div");
	oDiv.setAttribute("id", this.property+"_selectDiv");
	if(window.navigator.appName.indexOf("Explorer")>-1){
		oDiv.style.cssText = "position:absolute;left:0;top:0;z-index:9999;width:100%;overflow-x:hidden;";
	} else {
		oDiv.setAttribute("style", "position:absolute;left:0;top:0;z-index:9999;width:100%;overflow-x:hidden;");
	}
   
	var oTable = document.createElement("table");
	oTable.setAttribute("cellspacing", "0");
	oTable.setAttribute("cellpadding", "0");
	if(window.navigator.appName.indexOf("Explorer")>-1){
		oTable.style.cssText = "width:100%;font-family:arial;font-size:12px;color:#41465a;cursor:default;";
	} else {
		oTable.setAttribute("style", "width:100%;font-family:arial;font-size:12px;color:#41465a;cursor:default;");
	}

	var frameHeight=0;
	for (var i=0; i<this.options.length; i++) {
		var oTr = document.createElement("tr");
		oTr.setAttribute("onmouseover", "javascript:parent.mo(this)");
		oTr.setAttribute("onmouseout", "javascript:parent.mu(this)");
		oTr.setAttribute("onclick", "parent.getRadioOptionsValue(\""+this.property+"\","+this.multiSelection+",\""+this.options[i].value+"\",\""+this.options[i].text+"\",\""+this.callbackName+"\")");
	 
		var oTd = document.createElement("td");
		oTd.style.height="25px";
		oTd.style.padding="0 0 0 8px";
		frameHeight += 25;
		if(this.multiSelection) {
			oTd.innerHTML = "<input type=\"checkbox\" name=\"c_"+this.property+"\" value=\""+this.options[i].value+"\" text=\""+this.options[i].text+"\" onclick=\"parent.getMultiOptionsValue('"+this.property+"',this)\"/>"+this.options[i].text;
		} else {
			oTd.innerHTML = this.options[i].text;
		}
		oTr.appendChild(oTd);
		oTable.appendChild(oTr);
	}
   
	oDiv.appendChild(oTable);
	
	if(frameHeight > this.height || frameHeight==0) {
		oDiv.style.width=(this.width-17)+"px";
	} else {
		oDiv.style.width=this.width+"px";
		this.height = frameHeight;
	}
	this.selectFrame.style.width = this.width + "px";
	this.selectFrame.style.height = this.height + "px";

	if(window.navigator.appName.indexOf("Explorer")>-1) {
		document.frames[this.property+"_selectFrame"].document.writeln(oDiv.outerHTML);
		document.frames[this.property+"_selectFrame"].document.close();
		document.frames[this.property+"_selectFrame"].document.body.style.backgroundColor = "#f7f8fb";
		document.frames[this.property+"_selectFrame"].document.body.style.scrollbarArrowColor="#3d476f";
		document.frames[this.property+"_selectFrame"].document.body.style.scrollbarFaceColor="#e8e8e8";
		document.frames[this.property+"_selectFrame"].document.body.style.scrollbarHighlightColor="#e8e8e8";
		document.frames[this.property+"_selectFrame"].document.body.style.scrollbarShadowColor="#e8e8e8";
		document.frames[this.property+"_selectFrame"].document.body.style.scrollbarTrackColor="#e8e8e8";
		oDiv = null;
	} else {
		var oSpan = document.createElement("span");
		oSpan.appendChild(oDiv);
		document.getElementById(this.property+"_selectFrame").contentDocument.write(oSpan.innerHTML);
		document.getElementById(this.property+"_selectFrame").contentDocument.close();
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.backgroundColor = "#f7f8fb";
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.scrollbarArrowColor="#3d476f";
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.scrollbarFaceColor="#e8e8e8";
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.scrollbarHighlightColor="#e8e8e8";
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.scrollbarShadowColor="#e8e8e8";
		document.getElementById(this.property+"_selectFrame").contentDocument.body.style.scrollbarTrackColor="#e8e8e8";
		oSpan = null;
	}

	var ttop = this.selectText.offsetTop;     //TT控件的定位点高
	var thei = this.selectText.clientHeight;  //TT控件本身的高
	var tleft = this.selectText.offsetLeft;    //TT控件的定位点宽
	var ttyp = this.selectText.type;          //TT控件的类型
	
	while (this.selectText = this.selectText.offsetParent){ttop+=this.selectText.offsetTop; tleft+=this.selectText.offsetLeft;}
	var offTop = (ttyp=="image")? ttop+thei-11 : ttop+thei-3;
	var offLeft = tleft;
	
	this.selectFrame.style.top = (offTop + this.selectOffTop) + "px";
	this.selectFrame.style.left = (offLeft + this.selectOffLeft) + "px";
	
	this.selectFrame.style.display="block";
	
}

/**
 * 设置下拉框的位置
 * @param _top 相对父元素顶部的相对位移
 * @param _left 相对父元素左边的相对位移
 */
MelodySelect.prototype.setSelectPosition=function(_top, _left) {
	if(_top != null && _top != "undefined" && "" != _top){
        this.selectOffTop = _top;
    }
    if(_left != null && _left != "undefined" && "" != _left){
        this.selectOffLeft = _left;
    }
}

function Option() {
	this.text="";
	this.value="";
	this.selected=false;
}
function Option(value, text, selected) {
	this.text=text;
	this.value=value;
	this.selected=selected;
}

function mo(obj) {
	obj.style.backgroundColor="#C6CBE4";
	obj.style.color="#000";
}

function mu(obj) {
	obj.style.backgroundColor="";
	obj.style.color="#000";
}

function getRadioOptionsValue (selectE,check,value,text,callbackName) {
	if(!check) {
		document.getElementById(selectE+"_selectText").value = text;
		document.getElementById(selectE+"_selectValue").value = value;
		document.getElementById(selectE+"_selectFrame").style.display="none";
	}
	try{
		//单选框是鼠标点击事件
		if(callbackName != null && callbackName != "" && callbackName != undefined) {
			eval(callbackName+"();");
		}
	}catch(e){}
}

function getMultiOptionsValue (selectE,_sel) {
	var text=",";
	var value=",";
	var sels ;
	if(window.navigator.appName.indexOf("Explorer")>-1){
		sels= document.frames(selectE+"_selectFrame").document.getElementsByName(_sel.name);
	}else{
		sels= document.getElementById(selectE+"_selectFrame").contentDocument.getElementsByName(_sel.name);
	}
	for(var i=0;i<sels.length;i++){
		if(typeof(sels[i].checked)!="undefined"&&sels[i].checked==true){
			value=value+","+sels[i].value;
			text=text+","+$(sels[i]).attr("text");
		}
	}
	text=text.substring(2, text.length);
	value=value.substring(2, value.length);
	document.getElementById(selectE+"_selectText").value=text;
	document.getElementById(selectE+"_selectValue").value=value;
}

document.onmousedown=function(e) {
	var iframes = document.getElementsByTagName("iframe");
	for(var i=0; i<iframes.length; i++) {
		oIframe = iframes[i];
		if (oIframe.style.display=="block") {
			if(window.navigator.appName.indexOf("Explorer")>-1){
				mx=window.event.x + document.body.scrollLeft;
				my=window.event.y + document.body.scrollTop;
			} else {
				mx=e.pageX + document.body.scrollLeft;
				my=e.pageY + document.body.scrollTop;
			}
			x1=oIframe.offsetLeft;
			y1=oIframe.offsetTop;
			x2=oIframe.offsetLeft+oIframe.offsetWidth;
			y2=oIframe.offsetTop+oIframe.offsetHeight;
			if (mx<x1 || my<y1 || x2<mx || y2<my) {
				oIframe.style.display='none';
			}
		}
	}
}

