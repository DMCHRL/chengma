var accObj = getAccount();
var NonCurrentAccount = [];

//切换账户
function changeAccount(account) {

	var list = NonCurrentAccount;
	list.forEach(function(item) {
		if (item.account == account) {
			localStorage.setItem("accStr", JSON.stringify(item))
		}
	})

	mui.toast('切换成功', {
		duration: 'long',
		type: 'div'
	});
	setTimeout(function() {
		window.location.href = "mine.html";
	}, 100)
}

//显示当前账号信息
function setCurrentAccount() {
	var parent = $("#current_box");
	var acc = localStorage.getItem("accStr");
	if (acc) {
		acc = JSON.parse(acc);
		$(".trip1").hide();
	} else {
		return;
	}
	var htmlStr = '';
	if (acc.group == 'demoTX') {
		htmlStr += "<h4>" + acc.accountName + "<span>" + acc.account + "-Txas-Demo</span></h4>"
		htmlStr += "<p>TianxiangIntICompany-demo</p>"
	} else {
		htmlStr += "<h4>" + acc.accountName + "<span>" + acc.account + "-Txas-Live</span></h4>"
		htmlStr += "<p>TianxiangIntICompany-Live</p>"
	}
	parent.html(htmlStr);

}

function setAccountList(arr, num) { //切换账户列表
	//				var arr = NonCurrentAccount;
	var str = ".trip" + num;
	if (arr.length) {
		$(str).hide();
	} else {
		$(str).show();
	}
	var str1 = ".mime_list" + num;
	var parent = $(str1);
	parent.empty();
	for (var i = 0; i < arr.length; i++) {

		var item = arr[i];

		if (item.account == accObj.account) continue;

		var lidom = "";
		var txasType = 'Live';
		
		if (item.group == "demoTX") {
			txasType = 'Demo';
		}else {
			txasType = 'Live';
		}
		
		lidom += "<li class='mui-table-view-cell li" + num + "'>"
		lidom += "<div class='mui-navigate-right'>"
		lidom += "<div class='icon_box pull-left'><img src='../img/account_1.png' /></div>"
		lidom += "<div class='text_box pull-left' onclick='changeAccount(" + item.account + ")' >"
		lidom += "<h4>" + item.accountName + "<span>" + item.account + "-Txas-"+ txasType +"</span></h4>"
		lidom += "<p>TianxiangIntICompany-"+ txasType +"</p>"
		lidom += "</div>"
		lidom += "<div class='jiechu_btn' onclick='delAccount(" + item.account + ")'>删除</div>"
		lidom += "</div>"
		lidom += "</li>";

		parent.append($(lidom))
	}
}


function getUserAccount() {
	superGet("/api/user/loadMyAccount", function(res) {
		//console.log(res)
		if (res.statusCode == "0000") {
			setAccountList(res.data, 2);

			NonCurrentAccount = res.data;
			getBindingAccount();

		} else {
			console.log("获取数据失败")
		}
	})
}

function getBindingAccount() {
	var userObj = getUser();
	superGet("/api/tlb-account-control/loadRelationAccount/" + userObj.id, function(res) {
		//console.log(res)
		if (res.statusCode == "0000") {

			setAccountList(res.data, 3)

			NonCurrentAccount = NonCurrentAccount.concat(res.data);

		} else {
			console.log("获取数据失败")
		}
	})
}

//			显示删除
var bool = true;
$(".jie_btn").click(function() {
	if (bool) {
		$(".mime_list3 .jiechu_btn").show();
		$(".mime_list3 img").hide()
		bool = false;
	} else {
		$(".mime_list3 .jiechu_btn").hide();
		$(".mime_list3 img").show()
		bool = true;
	}

})

//			刪除賬戶
function delAccount(account) {
	var list = NonCurrentAccount;
	list.forEach((item, index) => {
		if (item.account == account) {
			list.splice(index, 1)
		}
	})
	localStorage.setItem('accList', JSON.stringify(list));
	getLocalAccountList();
}

//			解除账户
function untiedAccount(account) {
	//				console.log(account)
	var control = '';
	var list = NonCurrentAccount;
	list.forEach(function(item) {

		if (account == accObj.account) {
			mui.toast('當前賬戶正在操作，不可删除', {
				duration: 'short',
				type: 'div'
			});
			return;
		} else {
			if (item.account == account) {
				control = item.control;
				superGet("/api/tlb-account-control/delete/" + control, function(res) {
					//					console.log(res)
					if (res.statusCode == "0000") {
						mui.toast('删除成功', {
							duration: 'short',
							type: 'div'
						});
					}
					getUserAccount();
				})
				return;
			}
		}
	})
}

//			獲取本地賬戶列表
function getLocalAccountList() {
	var list = [];
	var accList = localStorage.getItem('accList');
	if (accList) {
		list = JSON.parse(accList);

		if (list.length <= 1) {
			$('._other').hide()
		}

		setAccountList(list, 3)
		NonCurrentAccount = list;
	}
}

mui.init({
	gestureConfig:{
	   tap: true, //默认为true
	   doubletap: true, //默认为false
	   longtap: true, //默认为false
	   swipe: true, //默认为true
	   drag: false, //默认为true
	   hold:false,//默认为false，不监听
	   release:false//默认为false，不监听
	},
  
});

mui(".mui-table-view").on('swipeleft','.mui-table-view-cell',function(){
	
		$(this).find(".jiechu_btn").show();
		$(this).find(".icon_box").hide()
	
	
}) 
mui(".mui-table-view").on('swiperight','.mui-table-view-cell',function(){
	
		$(this).find(".jiechu_btn").hide();
		$(this).find(".icon_box").show()
	
}) 

$(function() {

	if (tokenGet()) {
		//getUserAccount();
		setCurrentAccount();
		getLocalAccountList();
	}

})
