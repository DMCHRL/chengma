
var app_v = 6.0;
//var app_key = "67bdbb6f07cbe34451ba50b63320b9df";//安卓
var app_key = "b0352635353479068fe03bb3736cc779";//ios


//var httpHost = 'http://192.168.1.107:8080/crm';
var httpHost = 'http://192.168.1.135:8080/crm';
//var httpHost = 'http://47.52.199.109:8083/crm_dev';//线上测试环境
// var httpHost = 'http://47.52.199.109:8081/crm';

//var socketHost = "ws://192.168.1.139:8080/crm/webSocket";
//var socketHost = 'ws://47.52.199.109:8083/crm_dev/webSocket';//线上测试环境
var socketHost = "ws://47.52.199.109:8081/crm/webSocket";


//获取当前用戶信息
function getUser () {
	var userStr = localStorage.getItem("userStr");
	if (userStr) {
		return JSON.parse(userStr);
	}else {
		return {};
	}
}

//获取当前账号
function getAccount () {
	var accStr = localStorage.getItem("accStr");
	if (accStr) {
		return JSON.parse(accStr);
	}else {
		return {};
	}
}

//获取当前行情
function getSymbol () {
	var symbols = localStorage.getItem("symbol");
	if (!symbols) {
		return 'EURUSD'
	}else {
		return symbols;
	}
}


function GetUrlRelativePath(){//获取页面文件名
	var url = document.location.toString();
	var arrUrl = url.split("//");
	var start = arrUrl[1].indexOf("/");
	var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符
	if(relUrl.indexOf("?") != -1){
		relUrl = relUrl.split("?")[0];
	}
	var page = relUrl.split("/");
	return page[page.length-1];
}

function superPost (url,datas,call) {//线上
	var token = "Bearer "+ tokenGet();
	$.ajax({
	    type: 'POST',
	    url: httpHost+url,
	    headers:"Access-Control-Allow-Origin",
	    beforeSend: function(xhr) {
                    xhr.setRequestHeader("Authorization", token);
                },
	    contentType: 'application/json; charset=utf-8',
	    data: JSON.stringify(datas),
	    success : function(data, textStatus, jqXHR) {
            if ('success' == textStatus) {
                call(data)
            }
        },
	    error : function(XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.status == 401) {
				tokenRemove();
				mui.toast('登錄失效', {
					duration: 'short',
					type: 'div'
				});
				setTimeout(function () {
					if (GetUrlRelativePath() == 'index.html') {
						window.location.href = "page/login.html";
					}else {
						window.location.href = "../page/login.html";
					}
				},500)
			}else {
				console.log("网络出错")
			}
        }
	})
}

function superGet (url,call) {//超级请求
	var token = "Bearer "+ tokenGet();
	$.ajax({
	    type: 'GET',
	    url: httpHost+url,
	    headers:"Access-Control-Allow-Origin",
	    beforeSend: function(xhr) {
                    xhr.setRequestHeader("Authorization", token);
                },
	    contentType: 'application/json; charset=utf-8',
	    success : function(data, textStatus, jqXHR) {
            if ('success' == textStatus) {
                call(data)
            }
        },
	    error : function(XMLHttpRequest, textStatus, errorThrown) {

            if (XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.status == 401) {
				tokenRemove();
				mui.toast('登錄失效', {
					duration: 'short',
					type: 'div'
				});
				setTimeout(function () {
					if (GetUrlRelativePath() == 'index.html') {
						window.location.href = "page/login.html";
					}else {
						window.location.href = "../page/login.html";
					}
				},500)
			}else {
				console.log("网络出错")
			}
        }
	})
}


function checkIsNew (ismine) {//检查新版本
	if(!ismine){ismine= false;}
	
	var url = 'https://www.pgyer.com/apiv2/app/check';
	var data = {
		_api_key: '023dc27d2bcfa09f2a3b583d8e95ee5a',
		appKey: app_key,
	}
	$.post(url,data,function(datas, textStatus, jqXHR) {
//		console.log(datas)
//		return;
		var buildUpdateDescription = datas.data.buildUpdateDescription;
		var version = datas.data.buildVersion;
		var upUrl = datas.data.downloadURL;
		if (app_v < version) {
			mui.confirm(buildUpdateDescription,'升级提示',['确定升级'],function (e) {
				if (e.index == 0) {
//					console.log(upUrl)
					window.open(upUrl);
				}
			},'div')
			$('.mui-popup-button').removeClass('mui-popup-button-bold');
		}else {
			if (ismine) {
				mui.toast('當前已是最新版', {
					duration: 'long',
					type: 'div'
				});
			}
			return;
		}
	},'json')
}



//图片处理
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
//图片处理end

function tokenSet (token) {
	localStorage.setItem("id_token",token);
}
function tokenGet () {
	var token = localStorage.getItem("id_token");
	if (token) {
		return token;
	}else {
		return null;
	}
}
function tokenRemove () {
	localStorage.removeItem("id_token");
}

//			保存賬號對象到本地列表
function setLocalList (accObj) {
	var list = [];
	var accList = localStorage.getItem('accList');
	if (accList) {
		list = JSON.parse(accList);
		
		for (var i=0;i<list.length;i++) {
			if (list[i].account == accObj.account) {
				list.splice(i,1)
			}
		}
// 		list.forEach((item,index) => {
// 			if (item.account == accObj.account) {
// 				list.splice(index,1)
// 			}
// 		})
		
	}
	
	list.push(accObj);
	localStorage.setItem('accList',JSON.stringify(list));
	
}

//退出登錄
function logOut () {
	localStorage.clear();
	window.location.href = './login.html'
}

