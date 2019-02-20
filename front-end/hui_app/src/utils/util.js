
/* const host = 'http://192.168.1.100:8089/hpp';*/
//const host = 'http://192.168.1.139:8083/hpp';
const host = 'http://47.105.110.81:8080/hpp/';//线上


//android
const updateLink = "https://www.pgyer.com/apiv2/app/install?appKey=5a0eb38e3789c50c31504d1bbd537c08&_api_key=85587e6996fba94613ed944d1d88bc93";
const appKey = "5a0eb38e3789c50c31504d1bbd537c08";
const shareUrl = "https://www.pgyer.com/hui88";//分享链接
const shareImgUrl = 'http://www.hui1688.com.cn/img/share-android.png';

//ios
// const updateLink = "itms-services://?action=download-manifest&url=https%3A%2F%2Fwww.pgyer.com%2Fapiv2%2Fapp%2Fplist%3FappKey%3Dc842036799cf8d3eca87f90d6c89f8a7%26_api_key%3D85587e6996fba94613ed944d1d88bc93";
// const appKey = "c842036799cf8d3eca87f90d6c89f8a7";
// const shareUrl = "https://www.pgyer.com/evUY";//分享链接
// const shareImgUrl = 'http://www.hui1688.com.cn/img/share-ios.png';


//===========================================================================

const _api_key = "85587e6996fba94613ed944d1d88bc93";

//设置cookie
export function setCookie (c_name, value, expire) {
    var date = new Date()
    date.setSeconds(date.getSeconds() + expire)
    document.cookie = c_name + "=" + escape(value) + "; expires=" + date.toGMTString()
//  console.log(document.cookie)
};
//获取cookie
export function getCookie(c_name){
    if (document.cookie.length>0){
        let c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1){
            c_start=c_start + c_name.length+1
            let c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
    return ""
};
/*删除cookie*/
export function delCookie(c_name) {
    setCookie(c_name, "", -1)
};

//上传图片
export function uploadImage (url,obj) {
	return new Promise((resolve, reject) => {

		var urls = host+url; // 接收上传文件的后台地址
		var form = new FormData(); // FormData 对象
		form.append("image", obj); // 文件对象
		let xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
		xhr.open("post", urls, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.onload = function (evt) {
			resolve(evt);
		}; //请求完成
		xhr.onerror = function (err) {
			reject(err)
		}; //请求失败

		xhr.send(form); //开始上传，发送form数据

	})
};

export {
	host,
	updateLink,
	_api_key,
	appKey,
    shareUrl,
    shareImgUrl
}
