var accObj = getAccount();

var tradeType = '';
var floatNum = 5;

new Mdate("dateShowBtn", {
	acceptId: "dateSelectorTwo",
	beginYear: "2017",
	beginMonth: "1",
	beginDay: "1",
	format: "-"
})

function changeMonth(y, m) {
	console.log(y, m)
	if(y == null) {
		getLineData();
	} else if(m == null) {
		getLineData();
	} else {
		getLineData(y + '-' + m);
	}
}

//			轮播指示点
var sliderIndex = 1;
document.querySelector('.mui-slider').addEventListener('slide', function(event) {
	sliderIndex = event.detail.slideNumber;
	$('.slider_dot span').eq(sliderIndex).addClass('active').siblings().removeClass('active');
	//				console.log(sliderIndex)
	if(sliderIndex == 0) {
		getLineData();
	} else if(sliderIndex == 2) {
		getPieData();
	}
});

$(".show_popover").click(function() {
	$("#_popover").toggle();
})

$("#cell_eurusd").click(function() {
	localStorage.setItem('symbol', 'EURUSD');
	$("#_popover").hide();
	$("#btn_sym").text("歐元兌美元")
})
$("#cell_xauusd").click(function() {
	localStorage.setItem('symbol', 'XAUUSD');
	$("#_popover").hide();
	$("#btn_sym").text("黃金兌美元")
})

//常用手数
function setLots(val) {
	$("#lot_input").val(val)
}

//			取消下单弹窗

$("#cancel_btn").click(function() {
	$("#trade_box").hide();
})

$("#sell_btn").click(function() {
	$("#trade_box").show();
	tradeType = 'SELL';
	var sym = getSymbol();
	if(sym == 'XAUUSD') {
		$("#sym_text").text("黃金兌美元")

	} else {
		$("#sym_text").text("歐元兌美元")
	}
	//				goToOrder('SELL');
});
$("#buy_btn").click(function() {
	$("#trade_box").show();
	tradeType = 'BUY';
	var sym = getSymbol();
	if(sym == 'XAUUSD') {
		$("#sym_text").text("黃金兌美元")

	} else {
		$("#sym_text").text("歐元兌美元")
	}
	//				goToOrder('BUY');
});

//下單
$("#commit_btn").click(function() {
	var btn = $(this);
	$("#trade_box").hide();
	if(!tokenGet()) {
		mui.toast('資金不足', {
			duration: 'long',
			type: 'div'
		});
		return;
	}
	if(accObj.enableTrade != "Y") {
		mui.toast('賬戶暫不可交易', {
			duration: 'long',
			type: 'div'
		});
		return;
	}
	btn.attr('disabled', 'true');
	var lots = $("#lot_input").val();
	var sym = getSymbol();
	var datas = {
		"account": accObj.account, //账号
		"lots": lots, //手数
		"orderType": tradeType, // BUY(买涨) / SELL(买跌)
		"symbol": sym
	}

	superPost("/api/tlb-trade/createTlbTrade", datas, function(res) {
		//							console.log(res)
		btn.removeAttr('disabled');
		if(res.statusCode == "0000") {
			mui.toast('下單成功', {
				duration: 'short',
				type: 'div'
			});

			getUserAccount(); //更新保證金
			$("#loading1").show();
			setTimeout(function() {

				getUserAccount(); //更新保證金

				getCangData(); //更新持倉

			}, 2000)

		} else {
			mui.toast(res.msgCode, {
				duration: 'short',
				type: 'div'
			});
			return;
		}
	})
})

function showDown(el) {
	$(el).find('.down_box').toggle();
	$(el).siblings().find('.down_box').hide();
}

function appendCang(data) {
	//				console.log(data)

	var parentDom = $('.listbody');
	parentDom.empty();
	for(var i = 0; i < data.length; i++) {
		var item = data[i];
		var html = '';
		var iconSrc = '';
		var className = '';
		if(item.orderType == 'BUY') {
			iconSrc = '../img/c_c2.png';
			className = 'buy';
		} else {
			iconSrc = '../img/c_c1.png';
		}

		var floatNum = 5;
		if(item.symbol == 'XAUUSD') {
			floatNum = 2;
		}

		html += "<li onclick='showDown(this)'>";
		html += "<div class='_top_box clearfix " + className + "'>";
		html += "<img class='pull-left' src='" + iconSrc + "' alt=''/>";
		html += "<span class='pull-left symbol'>" + item.symbol + "</span>";
		html += "<span class='pull-left ziti'>lots:" + item.lots + "</span>";
		html += "<span class='pull-right ziti'>" + item.openPrice.toFixed(floatNum) + "</span>";
		html += "</div>";
		html += "<div class='down_box'>";
		html += "<p class='clearfix'>";
		html += "<span class='pull-left'>ID：<span>" + item.orderNo + "</span></span>";
		html += "<span class='pull-right'>行權成功：<span>" + (parseFloat(item.tp)).toFixed(floatNum) + "</span></span>";
		html += "</p>";
		html += "<p class='clearfix'>";
		html += "<span class='pull-left'>創建時間：<span>" + item.openTime + "</span></span>";
		html += "<span class='pull-right'>行權失敗：<span>" + (parseFloat(item.sl)).toFixed(floatNum) + "</span></span>";
		html += "</p>";
		html += "</div>";
		html += "</li>";

		parentDom.append($(html));
	}

}

function getCangData() { //持仓信息
	$("#loading1").show();
	superGet("/api/tlb-trade/openTlbTrades/" + accObj.account, function(res) {
		//					console.log(res)
		if(res.statusCode == "0000") {
			$("#loading1").hide(); //关闭加载动画

			if(res.data.length > 0) {
				$('.sec_3 .trip').hide(); //关闭无数据
			} else {
				$('.sec_3 .trip').show();
			}

			appendCang(res.data)

		} else {
			console.log("持仓数据获取失败")
		}
	})
};

function socketPrice() {

	//实时数据
	if('WebSocket' in window) {
		websocket1 = new WebSocket(socketHost);
	} else {
		alert('Not support websocket');
		return;
	}

	websocket1.onmessage = function(event) {
		var list = $.parseJSON(event.data);
		var data = '';
		var sym = getSymbol();

		list.forEach((item) => {
			if(item.symbol == sym) {
				data = item
			}
		})
		var floatNums = 5;
		if(sym == 'XAUUSD') {
			floatNums = 2
		}

		//var data = $.parseJSON(event.data);
		//console.log(data)
		$('#buyprice').html((((parseFloat(data.buy)).toFixed(floatNums)).toString()).substr(0, 6) + "<sup>" + (((parseFloat(data.buy)).toFixed(floatNum)).toString()).substr(6, 1) + "</sup>");
		$('#sellprice').html((((parseFloat(data.sell)).toFixed(floatNums)).toString()).substr(0, 6) + "<sup>" + (((parseFloat(data.sell)).toFixed(floatNum)).toString()).substr(6, 1) + "</sup>");

	}

	window.onbeforeunload = function() { //关闭
		websocket1.close();
	}
}

function getUserAccount() {
	superGet("/api/tlb-account/getTlbAccountByNo/" + accObj.account, function(res) {
		//					console.log(res)
		if(res.statusCode == "0000") {
			var accData = res.data;
			if(accData) {
				$('#useing_canuse').text(Number(accData.margin).toFixed(2)); //已用保证金
				$('#currentmoney').text(Number(accData.balance).toFixed(2)); //余额
				$('#canuse').text(Number(accData.freeMargin).toFixed(2)); //可用保证金
				$('#shouyi').text(Number(accData.profit).toFixed(2)); //收益

			}
		}
	})
}

//檢查賬戶有效性
function checkAccount () {
	superPost('/api/tlb-account/confirmAccount',accObj,function (res) {
		console.log(res)
		if (res.statusCode != '0000') {
			mui.toast(res.msgCode, {
				duration: 'short',
				type: 'div'
			});
			
			setTimeout(function () {
	  			logOut();
	  		},1000)
		}
	})
}

$(function() {
	
	checkAccount();
	
	var sym = getSymbol();
	if(sym == 'XAUUSD') {
		$("#btn_sym").text("黃金兌美元")

	} else {
		$("#btn_sym").text("歐元兌美元")
	}

	if(tokenGet()) {
		getCangData();
		getUserAccount();
		var myTimer = setInterval(function() {
			getCangData();
			getUserAccount();
		}, 30000)

		setTimeout(function() {
			clearInterval(myTimer);
		}, 180000)
	}

	socketPrice();
	
});