var xauText = "<span>XAUUSD</span><span> 黄金</span>";
var eurText = "<span>EURUSD</span><span> 欧元</span>";

$("#btn_sym").click(function() {
	//				 $("#_popover").toggle();
	var sym = getSymbol();
	console.log(sym)
	if(sym == 'EURUSD') {
		localStorage.setItem('symbol', 'XAUUSD');
		$("#sym_box").html(xauText)
	} else {
		localStorage.setItem('symbol', 'EURUSD');
		$("#sym_box").html(eurText)
	}
	updatak(index_time);
})

$("#cell_eurusd").click(function() {
	localStorage.setItem('symbol', 'EURUSD');
	updatak(index_time);
	$("#_popover").hide();
	$("#sym_box").html("<span>欧元兑美元</span><span>EURUSD</span>")
})
$("#cell_xauusd").click(function() {
	localStorage.setItem('symbol', 'XAUUSD');
	updatak(index_time);
	$("#_popover").hide();
	$("#sym_box").html("<span>黄金兑美元</span><span>XAUUSD</span>")
})

$(".item_tab").click(function() {
	var index = $(this).index();
	$(this).addClass('active').siblings().removeClass('active');
	if(index == 0) {
		updatak(30);
	} else if(index == 1) {
		updatak(60);
	} else if(index == 2) {
		updatak(1440);
	}
})

mui.init({
	keyEventBind: {
		backbutton: true //关闭back按键监听
	}
});
// //首页返回键处理
// //处理逻辑：1秒内，连续两次按返回键，则退出应用；
var first = null;
mui.back = function() {
	//首次按键，提示‘再按一次退出应用’
	if(!first) {
		first = new Date().getTime(); //记录第一次按下回退键的时间
		mui.toast('再按一次退出应用'); //给出提示
		history.go(-1) //回退到上一页面
		setTimeout(function() { //1s中后清除
			first = null;
		}, 1000);
	} else {
		if(new Date().getTime() - first < 1000) { //如果两次按下的时间小于1s，
			plus.runtime.quit(); //那么就退出app
		}
	}
};

$(".sec_tab li").click(function() {
	$(this).addClass("active").siblings().removeClass("active");
})

function socketData() { //实时價格

	if('WebSocket' in window) {
		websocket = new WebSocket(socketHost);
	} else {
		alert('Not support websocket');
		return;
	}

	websocket.onmessage = function(event) {
		var list = $.parseJSON(event.data);
		var data = '';
		var sym = getSymbol();
		var floatNum = 5;
		
		list.forEach((item) => {
			if(item.symbol == sym) {
				data = item
			}
		})

		if(sym != 'EURUSD') {
			floatNum = 2;
		}

		$('#buynow').text(parseFloat(data.buy).toFixed(floatNum));

		$('#lowpirce').text(parseFloat(data.low1440).toFixed(floatNum));
		//7月30，最高价和开盘价调换
		$('#openprice').text(parseFloat(data.open1440).toFixed(floatNum));
		$('#highprice').text(parseFloat(data.high1440).toFixed(floatNum));

		updatelast(data);
	}

	window.onbeforeunload = function() { //关闭
		websocket.close();
	}

}

function changeEchart() { //首頁圖表寬高
	var doc = document;
	var main = doc.getElementById('main');
	if(main) {
		var h1 = doc.getElementsByClassName('mui-bar')[0].clientHeight;
		var h2 = doc.getElementsByClassName('sec_1')[0].clientHeight + 4;
		var h3 = doc.getElementsByClassName('sec_tab')[0].clientHeight;
		var h4 = doc.getElementsByClassName('footer')[0].clientHeight;

		var ww = doc.documentElement.clientWidth || doc.body.clientWidth;
		var wh = doc.documentElement.clientHeight || doc.body.clientHeight;
		main.style.width = ww + 'px';
		main.style.height = wh - h1 - h2 - h3 - h4 + 'px';
	}
}
window.BeforeUnloadEvent = changeEchart();
window.resize = changeEchart();


function getCId () {//获取clientId，返回后台
	var info = plus.push.getClientInfo();
    // alert(JSON.stringify(info));
	var datas = {
		cid: info.clientid
	}
	superPost("/api/userCid/setCid",datas,function (res) {})

}

mui.plusReady(function(){
    getCId();
});

$(function() {

	var sym = getSymbol();
	if(sym == 'EURUSD') {
		$("#sym_box").html(eurText)
	} else {
		$("#sym_box").html(xauText)
	}

	socketData(); //实时價格
	checkIsNew();
});