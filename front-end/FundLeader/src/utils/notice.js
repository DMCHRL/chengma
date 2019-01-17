import router from '../router/index.js';

mui.init({
	keyEventBind: {
		backbutton: true //关闭back按键监听
	}
});

var first = null;
mui.back = function() {
	var thisHash = location.hash;
	if (thisHash == '#/' || thisHash == '#/login') {
		//首次按键，提示‘再按一次退出应用’
		if (!first) {
			first = new Date().getTime(); //记录第一次按下回退键的时间
			mui.toast('再按一次退出应用'); //给出提示
			setTimeout(function() { //1s中后清除
				first = null;
			}, 1000);
		} else {
			if (new Date().getTime() - first < 1000) { //如果两次按下的时间小于1s，
				plus.runtime.quit(); //那么就退出app
			}
		}
	} else {
		history.go(-1) //回退到上一页面
	}

};

	document.addEventListener('plusready', function() {
	// console.log("所有plus api都应该在此事件发生后调用，否则会出现plus is undefined。")
	//消息透传
	// 监听离线点击消息事件
	plus.push.addEventListener("click", function(msg) {
		var payload = (plus.os.name == 'iOS') ? msg.payload : JSON.parse(msg.payload);
		pushGetRun(payload);
	}, false);
	// 监听在线消息事件
	plus.push.addEventListener("receive", function(msg) {
		// alert("receive")
		if (msg.payload) {

			if (typeof(msg.payload) == "string") {
				// alert( "payload(String): "+msg.payload );
				var payload = JSON.parse(msg.payload);
				var id = payload.id;
				
				// var url = location.host + '/#/news-details?id=' + id;
				
// 				plus.nativeUI.confirm("收到一条新消息，是否立即查看", function(e) {
// 					if (e.index == 0) {
// 						
						router.push({
							path: '/news-details?id='+id
						})
						
					// }
				// }, "新消息通知", ["查看", "忽略"]);
			}
		} else {
			// alert( "payload: undefined" );
		}
	}, false);

	function pushGetRun(payload) {
		var id = payload.id;
		router.push({
			path: '/news-details?id='+id
		})
// 		var url = location.host + '/#/news-details?id=' + id;
// 		location.href = url;
	}


});
