
var host = 'http://47.52.199.109:8080';//毕烈
//var host = 'http://192.168.1.139:8082';//毕烈


var userAccount = getUrlParam('account');//账户号
var userName = getUrlParam('name');


function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
} 


function superPost (url,datas,call) {//线上
	$.ajax({
	    type: 'POST',
	    url: host+url,
	    headers:"Access-Control-Allow-Origin",
	    beforeSend: function(xhr) {
                    xhr.setRequestHeader("Authorization", "");
                },
	    contentType: 'application/json; charset=utf-8',
	    data: JSON.stringify(datas),
	    success: function(data, status, xhr){
	       call(data)
	    }
	})
}

function superGet (url,call) {//超级请求
	$.get(host+url,function (data,status,xhr) {
		call(data)
	},'jsonp')
}
