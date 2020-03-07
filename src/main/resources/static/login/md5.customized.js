/**
 * @param userInfo 格式为json， 如
 *      {
 *    		loginName: "sbs",
 *    		loginPass: "test"	
 *      }
 * @return
 */
function encrypt(userInfo) {
	return hex_md5(userInfo.loginName + ":dss:" + userInfo.loginPass);
}

/**
 * 明文密码保存到cookie内
 * @param passInfo
 * @return
 */
function savePassToCookie(passInfo) {
	$.cookie(location.host + "_password", desEncrypt(passInfo), {path: "/", expires: 30});
}

/**
 * 获取明文密码
 * @return
 */
function getPassFromCookie() {
	var pass = $.cookie(location.host + "_password");
	if (pass != null) {
		pass = desDecrypt(pass);
	}
	return pass;
}