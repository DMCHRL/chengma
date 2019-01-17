// export const host = 'http://192.168.1.135:8090/lhfz';
export const host = 'http://47.52.199.109:8083/lhfz';//线上

//android
export const updateLink = "https://www.pgyer.com/apiv2/app/install?appKey=87f469bd097986fb9efabf05f67a3f49&_api_key=023dc27d2bcfa09f2a3b583d8e95ee5a";
export const appKey = "87f469bd097986fb9efabf05f67a3f49";
export const shareUrl = "https://www.pgyer.com/linghang";//分享链接

//ios
// export const updateLink = "itms-services://?action=download-manifest&url=https%3A%2F%2Fwww.pgyer.com%2Fapiv2%2Fapp%2Fplist%3FappKey%3D7f2851b33a1e32dd3594af4e6ba73207%26_api_key%3D023dc27d2bcfa09f2a3b583d8e95ee5a";
// export const appKey = "7f2851b33a1e32dd3594af4e6ba73207";
// export const shareUrl = "https://www.pgyer.com/s7jN"; //分享链接


//===========================================================================

export const _api_key = "023dc27d2bcfa09f2a3b583d8e95ee5a";

//设置cookie
export function setCookie(c_name, value, expire) {
	var date = new Date()
	date.setSeconds(date.getSeconds() + expire)
	document.cookie = c_name + "=" + escape(value) + "; expires=" + date.toGMTString()
	//  console.log(document.cookie)
};
//获取cookie
export function getCookie(c_name) {
	if (document.cookie.length > 0) {
		let c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			let c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1) c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
};
/*删除cookie*/
export function delCookie(c_name) {
	setCookie(c_name, "", -1)
};

//上传图片
export function uploadImage(url, obj) {
	return new Promise((resolve, reject) => {

		var urls = host + url; // 接收上传文件的后台地址 
		var form = new FormData(); // FormData 对象
		form.append("image", obj); // 文件对象 
		let xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
		xhr.open("post", urls, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.onload = function(evt) {
			resolve(evt);
		}; //请求完成
		xhr.onerror = function(err) {
			reject(err)
		}; //请求失败

		xhr.send(form); //开始上传，发送form数据

	})
};

//格式化金额数字
export function formatMoney(s, type) {
	if (/[^0-9\.]/.test(s))
		return "0.00";
	if (s == null || s == "null" || s == "")
		return "0.00";
	s = s.toString().replace(/^(\d*)$/, "$1.");
	s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
	s = s.replace(".", ",");
	var re = /(\d)(\d{3},)/;
	while (re.test(s))
		s = s.replace(re, "$1,$2");
	s = s.replace(/,(\d\d)$/, ".$1");
	if (type == 0) {
		var a = s.split(".");
		if (a[1] == "00") {
			s = a[0];
		}
	}
	return s;
}
//格式化手机号
export function formatMobile(s) {
	if (/[^0-9\.]/.test(s))
		return "";
	if (s == null || s == "null" || s == "")
		return "";
	s = s.slice(0,3)+ '****' + s.slice(7)
	
	return s;
}

//身份识别
export function formatDepartment(name) {
	if (!name) return;
	switch (name){
		case 'admin':
			return '最高管理员';
		case 'manager':
			return '资金管理员';
		case 'originator':
			return '发起人';
		case 'partner':
			return '合伙人';
		case 'user':
			return '普通用户';
		case 'service':
			return '客服人员';
		default:
			break;
	}
}

/**
 * 识别状态
 */
export const whatStatus = name => {
	if (!name) return;
	switch (name){
		case 'REJECT':
			return '已拒绝';
		case 'PASSED':
			return '已通过';
		case 'APPLYING':
			return '审核中';
		default:
			break;
	}
}
/**
 * 识别资金类型
 */
export const whatType = name => {
	if (!name) return;
	switch (name){
		case 'FUNDOUT':
			return '资金转出';
		case 'FUNDIN':
			return '资金转入';
		case 'FUNDJOIN':
			return '资金入伙';
		default:
			break;
	}
}
