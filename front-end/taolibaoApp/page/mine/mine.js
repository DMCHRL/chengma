var accObj = getAccount();


document.getElementById("mySwitch").addEventListener("toggle", function(event) {
	if (event.detail.isActive) {
		console.log("你启动了开关");
	} else {
		console.log("你关闭了开关");
		mui.toast('不可关闭');
		mui("#mySwitch").switch().toggle();
	}
})


$('#close').click(function() {
	mui('.mui-popover1').popover('toggle', document.getElementById("close"));
});

$('#close2').click(function() {
	mui('.mui-popover2').popover('toggle', document.getElementById("close2"));
});

$('#close3').click(function() {
	mui('.mui-popover2').popover('toggle', document.getElementById("close3"));
});

$('.xiao_box').click(function() {
	mui('#sheet1').popover('toggle');
});

$('.logout-v2').click(function() { //退出登录
	logOut();
});

$('.sec_1_btn').click(function() { //账户切换
	window.location.href = 'AccountList.html';
})

//修改密碼
function commitmima(el) {
	var jiu = $('#jiumima').val();
	var xin = $('#xinmima').val();
	var xin2 = $('#xinmima2').val();
	if (jiu == '') {
		mui.toast('舊密碼為空！', {
			duration: 'short',
			type: 'div'
		});
		return;
	}
	if (xin == '') {
		mui.toast('新密碼為空！', {
			duration: 'short',
			type: 'div'
		});
		return;
	}
	if (xin2 == '') {
		mui.toast('兩次密碼不一樣！', {
			duration: 'short',
			type: 'div'
		});
		return;
	}
	if (xin2 != xin) {
		mui.toast('兩次密碼不一樣！', {
			duration: 'short',
			type: 'div'
		});
		return;
	}
	el.attr('disabled', 'true')

	var datas = {
		login: accObj.account,
		oldPassword: jiu,
		newPassword: xin
	}
	superPost('/api/tlb-account/editPassword', datas, function(res) {
		console.log(res)

		if (res.statusCode == '0000') {
			mui.toast('修改成功,請重新登錄', {
				duration: 'long',
				type: 'div'
			});

			setTimeout(function() {
				logOut();
			}, 1000)

		} else {
			mui.toast(res.msgCode, {
				duration: 'long',
				type: 'div'
			});
		}
	})

};

$(function() {
	//				isLogin ();

	if (tokenGet()) {
		$('#nickname').text(accObj.accountName);
		if (accObj.group == "demoTX") {
			$('.account_num').text(accObj.account + "-Txas-Demo");
		} else {
			$('.account_num').text(accObj.account + "-Txas-Live");
		}
	}
});
