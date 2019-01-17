var accObj = getAccount();
var ajaxdata = [];

mui.init({
	pullRefresh: {
		container: "#refreshContainer", //下拉刷新容器标识，querySelector能定位的css选择器均可，比如：id、.class等
		down: {
			style: 'circle', //必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
			color: '#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
			height: '50px', //可选,默认50px.下拉刷新控件的高度,
			range: '100px', //可选 默认100px,控件可下拉拖拽的范围
			offset: '0px', //可选 默认0px,下拉刷新控件的起始位置
			auto: true, //可选,默认false.首次加载自动上拉刷新一次
			callback: getTradeRecord //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
		}
	}
});

new Mdate("startbtn", {
	acceptId: "dateSelectorOne",
	format: "-",
	beginYear: "2017"
});
new Mdate("endbtn", {
	acceptId: "dateSelectorTwo",
	format: "-",
	beginYear: "2017"
});

function showDown(el) {
	$(el).find(".down_box").toggle();
	$(el).siblings().find(".down_box").hide();
}

function appendRecord(data) {
	$("#loading1").hide();
	//				console.log(data)

	var parentDom = $('.listbody');
	parentDom.empty();
	for(var i = 0; i < data.length; i++) {
		var item = data[i];
		var html = '';

		var iconSrc = '';
		var className = '';

		if(item.lots) {
			if(item.orderType == 'BUY') {
				iconSrc = '../img/c_c2.png';
				className = 'buy';
			} else {
				iconSrc = '../img/c_c1.png';
			}

			html += "<li onclick='showDown(this)'>"
			html += "<div class='_top_box clearfix " + className + "'>"
			html += "<img class='pull-left' src='" + iconSrc + "'/>"
			html += "<span class='pull-left symbol'>" + item.symbol + "</span>"
			html += "<span class='pull-left ziti'>lots:" + item.lots + "</span>"
			html += "<span class='pull-right jiesuan'><span>結算：</span><span class='ziti jie'>" + item.gainAmount + "</span></span>"
			html += "</div>"
			html += "<div class='down_box'>"
			html += "<p class='clearfix'>"
			html += "<span class='pull-left'>ID：<span>" + item.orderNo + "</span></span>"
			html += "<span class='pull-right'>税      金：<span>" + item.overnightFee + "</span></span>"
			html += "</p>"
			html += "<p class='clearfix'>"
			html += "<span class='pull-left'>開倉時間：<span>" + item.openTime + "</span></span>"
			html += "<span class='pull-right'>開倉價格：<span>" + (parseFloat(item.openPrice)).toFixed(5) + "</span></span>"
			html += "</p>"
			html += "<p class='clearfix'>"
			html += "<span class='pull-left'>平倉時間：<span>" + item.closeTime + "</span></span>"
			html += "<span class='pull-right'>平倉價格：<span>" + (parseFloat(item.closePrice)).toFixed(5) + "</span></span>"
			html += "</p>"
			html += "</div>"
			html += "</li>"

		} else {
			var _name = '';
			if(item.fundType == 'IN') {
				iconSrc = '../img/c_c4.png';
				className = 'buy';
				_name = 'deposit';
			} else if (item.fundType == 'OUT') {
				iconSrc = '../img/c_c3.png';
				_name = 'withdrawal';
			}else {
				iconSrc = '../img/c_c4.png';
				className = 'buy';
				_name = 'inner';
			}

			html += "<li onclick='showDown(this)'>"
			html += "<div class='_top_box clearfix " + className + "'>"
			html += "<img class='pull-left' src='" + iconSrc + "'/>"
			html += "<span class='pull-left symbol'>balace</span>"
			html += "<span class='pull-left ziti'>" + _name + "</span>"
			html += "<span class='pull-right jiesuan'><span></span><span class='ziti'>" + item.amount + "</span></span>"
			html += "</div>"
			html += "<div class='down_box'>"
			html += "<p class='clearfix'>"
			html += "<span class='pull-left'>ID：<span>" + (item.id).substr((item.id).length - 8, 8) + "</span></span>"
			html += "<span class='pull-right'>税      金：<span>0</span></span>"
			html += "</p>"
			html += "<p class='clearfix'>"
			html += "<span class='pull-left'>時間：<span>" + item.approvedAt + "</span></span>"
			html += "<span class='pull-right'>狀      態：<span>SUCCESS</span></span>"
			html += "</p>"
			html += "</div>"
			html += "</li>"
		}
		parentDom.append($(html));
	}
}

function getRujinRecord() { //出入金記錄
	superGet("/api/tlb-fund-apply/history/" + accObj.account, function(res) {
		//					console.log(res)
		if(res.statusCode == "0000") {

			ajaxdata = ajaxdata.concat(res.data);
			getDateArray(0);

		} else {
			console.log("出入金記錄失败")
		}
	})
};

function getTradeRecord() { //历史交易記錄
	$("#loading1").show();
	superGet("/api/tlb-trade/closeTlbTrades/" + accObj.account, function(res) {
		//					console.log(res)
		if(res.statusCode == "0000") {
			ajaxdata = res.data;
			getRujinRecord();
		} else {
			console.log("历史交易记录失败")
		}
	})

}

//自定义显示隐藏
$("#ziding").click(function() {
	$(".zidingyi").slideToggle();
})
//获取区间日期
Date.prototype.format = function() {
	var month = this.getMonth() + 1;
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	var strDate = this.getDate();
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var s = '';
	s += this.getFullYear() + '-'; // 获取年份。
	s += month + "-"; // 获取月份。
	s += strDate; // 获取日。
	return(s); // 返回日期。
};

function getAll(begin, end) { //传入"2018-01-01"
	var arr = [];
	var ab = begin.split('-');
	var ae = end.split('-');
	var db = new Date();
	db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);
	var de = new Date();
	de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);
	var unixDb = db.getTime();
	var unixDe = de.getTime();
	for(var k = unixDb; k <= unixDe;) {
		arr.push((new Date(parseInt(k))).format());
		k = k + 24 * 60 * 60 * 1000;
	}
	return arr;
}

//获取当前日期
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	return currentdate;
}

//获取前几天时间
function getBeforeDate(n) {
	var n = n;
	var d = new Date();
	var year = d.getFullYear();
	var mon = d.getMonth() + 1;
	var day = d.getDate();
	if(day <= n) {
		if(mon > 1) {
			mon = mon - 1;
		} else {
			year = year - 1;
			mon = 12;
		}
	}
	d.setDate(d.getDate() - n);
	year = d.getFullYear();
	mon = d.getMonth() + 1;
	day = d.getDate();
	s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
	return s;
}

function getDateArray(n) { //日期数组
	var val1 = getBeforeDate(n);
	var val2 = getNowFormatDate();
	var dayArr = getAll(val1, val2);
	dataFilter(dayArr);
}

function dataFilter(dayArr) { //数据筛选
	var newarr = [];
	var arr = ajaxdata;
	for(var i = 0; i < dayArr.length; i++) {
		for(var j = 0; j < arr.length; j++) {
			//closeTime -> createAt
			if(!arr[j].closeTime) {
				arr[j].closeTime = arr[j].approvedAt;
			}
			if((arr[j].closeTime).substring(0, 10) == dayArr[i]) {
				newarr.push(arr[j]);
			}
		}
	}
	//				console.log(newarr)
	if(newarr.length > 0) {
		$('.trip').hide();
	} else {
		$('.trip').show();
	}
	arrSort(newarr)
}

function arrSort(arr) { //排序
	//				arr.sort((a, b) => new Date(b.closetime).getTime() - new Date(a.closetime).getTime())
	arr1 = arr.sort(function(a, b) {
		//					console.log(new Date(a.closetime).getTime() - new Date(b.closetime).getTime())
		//					return a < b ? 1 : -1;  
		return new Date(a.createAt).getTime() < new Date(b.createAt).getTime() ? 1 : -1;
	})
	appendRecord(arr1)
	//				pageVm.data1 = arr1;
	//				pageVm.data2 = [];
}

//点击日周月
$('.rili span').click(function() {
	$(".zidingyi").slideUp();
	$(this).addClass('active').siblings().removeClass('active');

	if($(this).index() == 0) {
		getDateArray(0);
	} else if($(this).index() == 1) {
		getDateArray(7)
	} else {
		getDateArray(30)
	}
});

//确定自定义
function commitDate() {
	var val1 = $("#dateSelectorOne").val();
	var val2 = $("#dateSelectorTwo").val();

	if(new Date(val1).getTime() > new Date(val2).getTime()) {
		mui.toast('日期区间错误', {
			duration: 'short',
			type: 'div'
		});
		return;
	}

	var dayArr = getAll(val1, val2);
	dataFilter(dayArr)

	$(".rili span").removeClass('active');
}

$(function() {
	if(tokenGet()) {
		getTradeRecord();
	}
});