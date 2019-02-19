import $axios from 'axios'
import { Loading, Message } from 'element-ui'

/*const host = 'http://192.168.1.108:8089/hpp';*/
//const host = 'http://192.168.1.139:8083/hpp';
const host = 'http://47.105.110.81:8080/hpp/';//线上

const localer = { //本地存储操作
	set(key, value) {
		localStorage.setItem(key, value);
	},
	get(key) {
		var result = localStorage.getItem(key);
		return result;
	},
	remove(key) {
		localStorage.removeItem(key);
	}
}
const tokener = {
	set(value) {
		localStorage.setItem("id_token", value);
	},
	get() {
		var result = localStorage.getItem("id_token");
		return result;
	},
	remove() {
		localStorage.removeItem("id_token");
	}
}

function isNull(data) {
	if(data == null || data == undefined || data == [] || data == '') {
		return true;
	}
	return false;
}

function photoCompress(file, w, objDiv) {
	var ready = new FileReader();
	/*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
	ready.readAsDataURL(file);
	ready.onload = function() {
		var re = this.result;
		canvasDataURL(re, w, objDiv)
	}
}

function canvasDataURL(path, obj, callback) {
	var img = new Image();
	img.src = path;
	img.onload = function() {
		var that = this;
		// 默认按比例压缩
		var w = that.width,
			h = that.height,
			scale = w / h;
		w = obj.width || w;
		h = obj.height || (w / scale);
		var quality = 0.7; // 默认图片质量为0.7
		//生成canvas
		var canvas = document.createElement('canvas');
		var ctx = canvas.getContext('2d');
		// 创建属性节点
		var anw = document.createAttribute("width");
		anw.nodeValue = w;
		var anh = document.createAttribute("height");
		anh.nodeValue = h;
		canvas.setAttributeNode(anw);
		canvas.setAttributeNode(anh);
		ctx.drawImage(that, 0, 0, w, h);
		// 图像质量
		if(obj.quality && obj.quality <= 1 && obj.quality > 0) {
			quality = obj.quality;
		}
		// quality值越小，所绘制出的图像越模糊
		var base64 = canvas.toDataURL('image/jpeg', quality);
		// 回调函数返回base64的值
		callback(base64);
	}
}

function convertBase64UrlToBlob(urlData) {
	var arr = urlData.split(','),
		mime = arr[0].match(/:(.*?);/)[1],
		bstr = atob(arr[1]),
		n = bstr.length,
		u8arr = new Uint8Array(n);
	while(n--) {
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {
		type: mime
	});
}

//上传图片
function uploadImage(url, obj) {
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

export default {
	host,
	localer,
	tokener,
	isNull,
	photoCompress,
	canvasDataURL,
	convertBase64UrlToBlob,
	uploadImage
};
