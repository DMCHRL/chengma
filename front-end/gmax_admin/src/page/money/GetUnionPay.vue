<template>
	<div class="content_box">
		<div class="text_box">
			<div class="sec_1">
				<img src="../../assets/img/timg.jpg" />
				<span>中国银联充值</span>
			</div>
			<div class="sec_box sec_2">
				<div class="item_box clearfix">
					<span class="pull-left">授权商户名称：</span>
					<div class="pull-right">
						<span>{{bankAcc.company}}</span>
						<button class="tag-read" :data-clipboard-text="bankAcc.company" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
				<div class="item_box clearfix">
					<span class="pull-left">银行名称：</span>
					<div class="pull-right">
						<span>{{bankAcc.bank}}</span>
						<button class="tag-read" :data-clipboard-text="bankAcc.bank" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
				
				<div class="item_box clearfix">
					<span class="pull-left">授权账户姓名：</span>
					<div class="pull-right">
						<span>{{bankAcc.name}}</span>
						<button class="tag-read" :data-clipboard-text="bankAcc.name" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
				<div class="item_box clearfix">
					<span class="pull-left">授权银行账号：</span>
					<div class="pull-right">
						<span>{{bankAcc.bankNum}}</span>
						<button class="tag-read" :data-clipboard-text="bankAcc.bankNum" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
				<div class="item_box clearfix">
					<span class="pull-left">金额：</span>
					<div class="pull-right">
						<span>{{cny}}</span>
						<button class="tag-read" :data-clipboard-text="cny" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
				<div class="item_box clearfix">
					<span class="pull-left">转账备注：</span>
					<div class="pull-right">
						<span>{{bankAcc.remark}}</span>
						<button class="tag-read" :data-clipboard-text="bankAcc.remark" @click="copy">
							<img src="../../assets/img/h_7.png" alt="" />
						</button>
					</div>
				</div>
			</div>

			<div class="sec_box sec_3">
				<h4>购汇指导</h4>
				<p>1.登陆您的 网上银行 账号。</p>
				<p class="red">2.确定转账金额并 复制【转账备注】信息 到银行支付页面的 “备注或附言”。</p>
				<p>3.完成转账。</p>
				<p>4.转账必须在30分钟内完成，否则购买将被视为无效。</p>
				<p class="red">请注意：会员必须在转账时填写正确【转账备注】信息，否则影响到账时间。</p>

			</div>

			<div class="sec_box sec_4">
				<div class="item_box clearfix">
					<span class="pull-left">剩余完成充值时间</span>
					<div class="pull-right">
						<span class="down_time">{{minute}}:{{second}}</span>
					</div>
				</div>
			</div>

			<div class="sec_5">
				<button @click="Commit">完成</button>
			</div>

		</div>

		<!--<span class="sharebtn sharebtn1">
					<button class="tag-read" :data-clipboard-text="qrcodeUrl" @click="copy">复制</button>
				</span>-->

	</div>
</template>

<script>
	import QRCode from '@xkeshi/vue-qrcode'
	import Clipboard from 'clipboard'
	export default {
		data() {
			return {
				bankAcc: {},
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
		components: {
			qrcode: QRCode
		},
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
			Commit () {
				let _this = this;
				let datas = {
					"account":_this.account,
					"amount": _this.cny,
					"usd": _this.usd,
					"bankNum": _this.bankAcc.bankNum, //	是	string	银行卡号
					"remarkNum": _this.bankAcc.remark
				};
				_this.post("/api/tlb-fund-apply/recharge",datas,function (res) {
//					console.log(res)
					
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "提交成功",
				          type: 'success'
				        });
				        _this.$router.go(-1);
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
			},
			num (n) {
				return n < 10 ? '0' + n : '' + n
			},
			add () {
				var _this = this
				var time = window.setInterval(function() {
					if(_this.seconds === 0 && _this.minutes !== 0) {
						_this.seconds = 59
						_this.minutes -= 1
					} else if(_this.minutes === 0 && _this.seconds === 0) {
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
				if(id) {
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
				_this.fetch("/api/receivables/findNew", function(res) {
					if(res.statusCode == "0000") {
						if(res.data) {
							_this.bankAcc = res.data;
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
		border-radius: 10px;
		width: 650px;
		height: 630px;
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
		position: relative;
		top: -7px;
	}
	
	.sec_1 span {
		font-size: 20px;
		color: #333;
		line-height: 100px;
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
		display:block;
		width: 80px;
		line-height: 26px;
		text-align: center;
		background-color: #FF2222;
		color: #fff;
		border-radius: 20px;
		font-size: 16px;
		
	}
	.sec_5 {
		text-align: center;
		padding-top: 20px;
	}
	
	.sec_5 button {
		font-size: 14px;
		color: #fff;
		background-color: #0000FF;
		border-radius: 20px;
		padding: 4px 40px;
	}
</style>