<<<<<<< .mine
import $axios from 'axios'
import { Loading, Message } from 'element-ui'

//const host = 'http://192.168.1.107:8080/crm';
const host = 'http://192.168.1.135:8080/crm';
//const host = 'http://47.52.199.109:8083/crm_dev';
/*const host = 'http://47.52.199.109:8081/crm';*/


//const shareHost = 'http://192.168.1.116:8001';
const shareHost = 'http://www.txasfx.com';

const localer = {//本地存储操作
	set (key,value) {
		localStorage.setItem(key, value);
	},
	get (key) {
		var result=localStorage.getItem(key);
    	return result;
	},
	remove (key) {
		localStorage.removeItem(key);
	}
}
const tokener = {
	set (value) {
		localStorage.setItem("id_token", value);
	},
	get () {
		var result=localStorage.getItem("id_token");
    	return result;
	},
	remove () {
		localStorage.removeItem("id_token");
	}
}


function superPost (url,data,call) {//post请求
	$axios({
	    method: 'POST',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    data: data,
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}

function superGet (url,call) {//get请求
	$axios({
	    method: 'GET',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}
function superDelete (url,call) {//delete请求
	$axios({
	    method: 'DELETE',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8'
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		console.log(err)
	})
}

function isNull (data) {
	if (data == null || data == undefined || data == [] || data == '') {
		return true;
	}
	return false;
}

function photoCompress (file,w,objDiv) {
  var ready=new FileReader();
  /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
  ready.readAsDataURL(file);
  ready.onload=function(){
      var re=this.result;
      canvasDataURL(re,w,objDiv)
  }
}
function canvasDataURL(path, obj, callback){
    var img = new Image();
    img.src = path;
    img.onload = function(){
        var that = this;
        // 默认按比例压缩
        var w = that.width,
            h = that.height,
            scale = w / h;
        w = obj.width || w;
        h = obj.height || (w / scale);
        var quality = 0.7;  // 默认图片质量为0.7
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
        if(obj.quality && obj.quality <= 1 && obj.quality > 0){
            quality = obj.quality;
        }
        // quality值越小，所绘制出的图像越模糊
        var base64 = canvas.toDataURL('image/jpeg', quality);
        // 回调函数返回base64的值
        callback(base64);
    }
}
function convertBase64UrlToBlob(urlData){
    var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

export default {
  host,
  shareHost,
  localer,
  tokener,
  
  superPost,
  superGet,
  superDelete,
  
  isNull,
  photoCompress,
  canvasDataURL,
  convertBase64UrlToBlob
};
||||||| .r316
import $axios from 'axios'
import { Loading, Message } from 'element-ui'

//const host = 'http://192.168.1.107:8080/crm';
// const host = 'http://192.168.1.135:8080/crm';
//const host = 'http://47.52.199.109:8083/crm_dev';
const host = 'http://47.52.199.109:8081/crm';


//const shareHost = 'http://192.168.1.116:8001';
const shareHost = 'http://www.txasfx.com';

const localer = {//本地存储操作
	set (key,value) {
		localStorage.setItem(key, value);
	},
	get (key) {
		var result=localStorage.getItem(key);
    	return result;
	},
	remove (key) {
		localStorage.removeItem(key);
	}
}
const tokener = {
	set (value) {
		localStorage.setItem("id_token", value);
	},
	get () {
		var result=localStorage.getItem("id_token");
    	return result;
	},
	remove () {
		localStorage.removeItem("id_token");
	}
}


function superPost (url,data,call) {//post请求
	$axios({
	    method: 'POST',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    data: data,
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}

function superGet (url,call) {//get请求
	$axios({
	    method: 'GET',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}
function superDelete (url,call) {//delete请求
	$axios({
	    method: 'DELETE',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8'
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		console.log(err)
	})
}

function isNull (data) {
	if (data == null || data == undefined || data == [] || data == '') {
		return true;
	}
	return false;
}

function photoCompress (file,w,objDiv) {
  var ready=new FileReader();
  /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
  ready.readAsDataURL(file);
  ready.onload=function(){
      var re=this.result;
      canvasDataURL(re,w,objDiv)
  }
}
function canvasDataURL(path, obj, callback){
    var img = new Image();
    img.src = path;
    img.onload = function(){
        var that = this;
        // 默认按比例压缩
        var w = that.width,
            h = that.height,
            scale = w / h;
        w = obj.width || w;
        h = obj.height || (w / scale);
        var quality = 0.7;  // 默认图片质量为0.7
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
        if(obj.quality && obj.quality <= 1 && obj.quality > 0){
            quality = obj.quality;
        }
        // quality值越小，所绘制出的图像越模糊
        var base64 = canvas.toDataURL('image/jpeg', quality);
        // 回调函数返回base64的值
        callback(base64);
    }
}
function convertBase64UrlToBlob(urlData){
    var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

export default {
  host,
  shareHost,
  localer,
  tokener,
  
  superPost,
  superGet,
  superDelete,
  
  isNull,
  photoCompress,
  canvasDataURL,
  convertBase64UrlToBlob
};=======
import $axios from 'axios'
import { Loading, Message } from 'element-ui'

//const host = 'http://192.168.1.107:8080/crm';
//const host = 'http://192.168.1.135:8080/crm';
const host = 'http://47.52.199.109:8081/crm';


//const shareHost = 'http://192.168.1.116:8001';
const shareHost = 'http://www.txasfx.com';

const localer = {//本地存储操作
	set (key,value) {
		localStorage.setItem(key, value);
	},
	get (key) {
		var result=localStorage.getItem(key);
    	return result;
	},
	remove (key) {
		localStorage.removeItem(key);
	}
}
const tokener = {
	set (value) {
		localStorage.setItem("id_token", value);
	},
	get () {
		var result=localStorage.getItem("id_token");
    	return result;
	},
	remove () {
		localStorage.removeItem("id_token");
	}
}


function superPost (url,data,call) {//post请求
	$axios({
	    method: 'POST',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    data: data,
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}

function superGet (url,call) {//get请求
	$axios({
	    method: 'GET',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8',
	    async:false,
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		
		return;
	})
}
function superDelete (url,call) {//delete请求
	$axios({
	    method: 'DELETE',
	    url: host+url,
	    contentType: 'application/json; charset=utf-8'
	}).then(function (res) {
		call(res)
	}).catch(function (err) {
		console.log(err)
	})
}

function isNull (data) {
	if (data == null || data == undefined || data == [] || data == '') {
		return true;
	}
	return false;
}

function photoCompress (file,w,objDiv) {
  var ready=new FileReader();
  /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
  ready.readAsDataURL(file);
  ready.onload=function(){
      var re=this.result;
      canvasDataURL(re,w,objDiv)
  }
}
function canvasDataURL(path, obj, callback){
    var img = new Image();
    img.src = path;
    img.onload = function(){
        var that = this;
        // 默认按比例压缩
        var w = that.width,
            h = that.height,
            scale = w / h;
        w = obj.width || w;
        h = obj.height || (w / scale);
        var quality = 0.7;  // 默认图片质量为0.7
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
        if(obj.quality && obj.quality <= 1 && obj.quality > 0){
            quality = obj.quality;
        }
        // quality值越小，所绘制出的图像越模糊
        var base64 = canvas.toDataURL('image/jpeg', quality);
        // 回调函数返回base64的值
        callback(base64);
    }
}
function convertBase64UrlToBlob(urlData){
    var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

export default {
  host,
  shareHost,
  localer,
  tokener,
  
  superPost,
  superGet,
  superDelete,
  
  isNull,
  photoCompress,
  canvasDataURL,
  convertBase64UrlToBlob
};>>>>>>> .r329
