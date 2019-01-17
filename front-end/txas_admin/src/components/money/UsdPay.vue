<template>
	<div class="content_box">
		<div class="text_box">
			<div class="sec_1">
        <img src="../../assets/img/unionpay_01.jpg" alt="">
				<span>国际汇款</span>
			</div>
			
			<div class="sec_table">
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>商户名称：</p>
        		<p>StoreName：</p>
        	</div>
        	<div class="pull-left item_con">
        		<p>
              {{item.company}}
              <button class="tag-read" :data-clipboard-text="item.company" @click="copy">复制</button>
            </p>
        	</div>
        </div>
        
				<div class="clearfix item_row">
					<div class="pull-left item_tab">
						<p>收款人名称：</p>
						<p>Name：</p>
					</div>
					<div class="pull-left item_con">
            <p>{{item.userName}}<button class="tag-read" :data-clipboard-text="item.userName" @click="copy">复制</button></p>
					</div>
				</div>
        
				<div class="clearfix item_row">
					<div class="pull-left item_tab">
						<p>账户号码：</p>
						<p>Account Number：</p>
					</div>
					<div class="pull-left clearfix">
            <div class="pull-left item_con right_border">
              <p>{{item.accountType}}</p>
              <p>{{item.accountTypeEn}}</p>
            </div>
            <div class="pull-left item_con">
              <p>{{item.account}}<button class="tag-read" :data-clipboard-text="item.account" @click="copy">复制</button></p>
            </div>
          </div>
				</div>
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>银行名称：</p>
        		<p>Bank Name：</p>
        	</div>
        	<div class="pull-left item_con">
        			<p>{{item.bankName}}<button class="tag-read" :data-clipboard-text="item.bankName" @click="copy">复制</button></p>
        			<p>{{item.bankNameEn}}<button class="tag-read" :data-clipboard-text="item.bankNameEn" @click="copy">复制</button></p>
        	</div>
        </div>
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>银行地址：</p>
        		<p>Address：</p>
        	</div>
        	<div class="pull-left item_con">
        			<p>{{item.address}}<button class="tag-read" :data-clipboard-text="item.address" @click="copy">复制</button></p>
        			<p>{{item.addressEn}}<button class="tag-read" :data-clipboard-text="item.addressEn" @click="copy">复制</button></p>
        	</div>
        </div>
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>银行编码：</p>
        		<p>Bank Code：</p>
        	</div>
        	<div class="pull-left item_con">
        			<p>{{item.bankCode}}<button class="tag-read" :data-clipboard-text="item.bankCode" @click="copy">复制</button></p>
        	</div>
        </div>
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>汇款编码(汇款备注)：</p>
        		<p>SWIFT Code：</p>
        	</div>
        	<div class="pull-left item_con">
        			<p>{{item.swiftCode}}<button class="tag-read" :data-clipboard-text="item.swiftCode" @click="copy">复制</button></p>
        	</div>
        </div>
        
        <div class="clearfix item_row">
        	<div class="pull-left item_tab">
        		<p>汇款金额（美金）：</p>
        		<p>Amount：</p>
        	</div>
        	<div class="pull-left item_con">
        			<p>{{usd}}<button class="tag-read" :data-clipboard-text="usd" @click="copy">复制</button></p>
        	</div>
        </div>
        
			</div>


			<!-- <div class="sec_box sec_3">
				<h4>购汇指导</h4>
				<p>1.登陆您的 网上银行 账号。</p>
				<p class="red">2.确定转账金额并 复制【转账备注】信息 到银行支付页面的 “备注或附言”。</p>
				<p>3.完成转账。</p>
				<p>4.转账必须在30分钟内完成，否则购买将被视为无效。</p>
				<p class="red">请注意：会员必须在转账时填写正确【转账备注】信息，否则影响到账时间。</p>

			</div> -->

			<div class="sec_box sec_4">
				<div class="item_box clearfix">
					<span class="pull-left">剩余完成充值时间</span>
					<div class="pull-right">
						<span class="down_time">{{minute}}:{{second}}</span>
					</div>
				</div>
			</div>

			<div class="sec_btn">
				<button @click="Commit">确认完成 <p>Confirm</p></button>
			</div>

		</div>

		<!--<span class="sharebtn sharebtn1">
					<button class="tag-read" :data-clipboard-text="qrcodeUrl" @click="copy">复制</button>
				</span>-->

	</div>
</template>

<script>
	import Clipboard from 'clipboard'
	export default {
		data() {
			return {
				item: {},
				minutes: 30,
				seconds: 0
			}
		},
		computed: {
			account() {
				return this.$route.query.account;
			},
			cny() {
				return this.$route.query.cny;
			},
			usd() {
				return this.$route.query.usd;
			},
			second: function() {
				return this.num(this.seconds)
			},
			minute: function() {
				return this.num(this.minutes)
			}
		},
		components: {},
		watch: {
			second: {
				handler(newVal) {
					this.num(newVal)
				}
			},
			minute: {
				handler(newVal) {
					this.num(newVal)
				}
			}
		},
		methods: {
			Commit() {
				let _this = this;
				let datas = {
					"account": _this.account,
					"amount": _this.cny,
					"usd": _this.usd,
					"bankNum": _this.item.account, //	是	string	银行卡号
					"remarkNum": _this.item.swiftCode
				};
				_this.$until.superPost("/api/tlb-fund-apply/recharge", datas, function(res) {
					if (res.statusCode == "0000") {
						_this.$message({
							message: "提交成功",
							type: 'success'
						});
						_this.$router.go(-1);
					} else {
						_this.$message({
							message: res.msgCode,
							type: 'warning'
						});
					}
				})
			},
			num(n) {
				return n < 10 ? '0' + n : '' + n
			},
			add() {
				var _this = this
				var time = window.setInterval(function() {
					if (_this.seconds === 0 && _this.minutes !== 0) {
						_this.seconds = 59
						_this.minutes -= 1
					} else if (_this.minutes === 0 && _this.seconds === 0) {
						_this.seconds = 0
						window.clearInterval(time)
						_this.$router.go(-1)

					} else {
						_this.seconds -= 1
					}
				}, 1000)
			},
			makeQrcode() {
				let user = JSON.parse(localStorage.getItem("user"));
				let id = user.id;
				if (id) {
					this.qrcodeUrl = this.$until.shareHost + "/#/regiter?id=" + id;
				}
			},
			copy() {
				let _this = this;
				var clipboard = new Clipboard('.tag-read')
				clipboard.on('success', e => {
					//console.log('复制成功')
					_this.$message({
						message: '复制成功',
						type: 'success'
					});
					// 释放内存
					clipboard.destroy()
				})
				clipboard.on('error', e => {
					// 不支持复制
					//console.log('该浏览器不支持自动复制')
					_this.$message({
						message: '该浏览器不支持自动复制',
						type: 'warning'
					});
					// 释放内存
					clipboard.destroy()
				})
			},
			initPage() {
				let _this = this;
				_this.$until.superGet("/api/receivables_en/findNew", function(res) {
          console.log(res)
					if (res.statusCode == "0000") {
						if (res.data) {
							_this.item = res.data;
						}
					}
				})
			}
		},
		mounted() {
			this.initPage();
			this.add()
		}
	}
</script>

<style scoped>
	.content_box {
		padding: 0;
	}

	.text_box {
		background-color: #fff;
		width: 700px;
		height: 750px;
		position: fixed;
		left: 0;
		right: 0;
		top: 20px;
		bottom: 0;
		margin: 0 auto;
		padding: 0 15px;
	}

	.sec_1 {
		text-align: center;
		border-bottom: 1px solid #ededed;
	}
  .sec_1 img {
    width: 140px;
  }
	.sec_1 span {
		/* font-family: "palace script mt"; */
		font-size: 20px;
		color: #333;
		line-height: 100px;
		margin: 0 10px;
	}


	.sec_table {
		border: 1px solid #666;
	}

	.sec_table .item_row {
		border-bottom: 1px solid #666;
	}
  .sec_table .item_row:last-of-type {
    border: none;
  }
  .item_tab {
    border-right: 1px solid #666;
    width: 160px;
    text-align: center;
    height: 60px;
  }
  .item_tab p {
    font-size: 14px;
    color: #333;
    font-weight: bold;
    line-height: 30px;
  }
  .right_border {
    border-right: 1px solid #666;
  }
  .item_con {
    height: 60px;
  }
  .item_con p {
    font-size: 14px;
    line-height: 30px;
    padding: 0 10px;
    color: #666;
  }
  .item_con button {
    padding: 0 10px;
    background-color: #999;
    color: #fff;
    border-radius: 5px;
    font-size: 12px;
    line-height: 25px;
    margin-left: 10px;
  }


	.sec_box {
		padding: 15px 0;
		border-bottom: 1px solid #ededed;
	}

	.sec_2 .item_box {
		padding-bottom: 5px;
	}

	.sec_2 .item_box img {
		width: 20px;
	}

	.sec_2 .item_box button {
		background-color: #fff;
	}

	.sec_3 h4 {
		margin-bottom: 20px;
		font-size: 16px;
		color: #333;
	}

	.sec_3 p {
		font-size: 14px;
		line-height: 24px;
		color: #666;
	}

	.sec_3 p.red {
		color: #FF0000;
	}

	.down_time {
		display: block;
		width: 80px;
		line-height: 26px;
		text-align: center;
		background-color: #FF743D;
		color: #fff;
		border-radius: 20px;
		font-size: 16px;

	}

	.sec_btn {
		text-align: center;
		padding-top: 20px;
	}

	.sec_btn button {
		font-size: 14px;
		color: #fff;
		background-color: #000;
		border-radius: 5px;
		padding: 4px 30px;
	}
</style>
