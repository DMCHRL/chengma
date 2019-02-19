<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>

        <div class="hui_content" id="hui-content">

		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/8_01.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="number" name="" id="" value="" placeholder="输入交易账户" v-model="account" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box icon_box_2">
				<img src="../../assets/img/login_02.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="password" name="" id="" value="" placeholder="输入交易密码" v-model="passWord"/>
			</div>
		</div>
		<div class="trips_box">
			<p>请在解绑完成后登录您的交易帐户查询是否还有持单，请及时按照自己的计划及时处理，以确保帐户安全。</p>
		</div>

		<div class="btn_box">
			<button class="myback" :disabled="isDisable" @click="Commit">确认解除</button>
		</div>

        </div>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '策略解除',backText: '',showBack: true},
				typeNum: 'STEADY',
				isDisable: false,
				account: '',
				passWord: '',
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			},
			accountNum () {
				this.account = this.$route.query.account;
				return this.$route.query.account;
			},
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return null;
				}
			}
		},
		watch: {
			accountNum(newValue, oldValue) {
				this.account = newValue;
			}
		},
		components: {
		},
		methods: {
			changType (num) {
				this.typeNum = num;
			},
			Commit () {
				let _this = this;

				if (_this.user.account == '') {
					_this.$vux.toast.text('请输入交易账号','middle')
					return;
				}
				if (_this.user.passWord == '') {
					_this.$vux.toast.text('请输入交易密码','middle')
					return;
				}
				let datas = {
					account: _this.account,//	是	string	交易账号
					password: _this.passWord,//	是	string	密码
					mail: _this.user.login,//	是	string	手机号码
					strategyId: _this.id//
				}
				_this.isDisable = true;
				_this.$post("/api/hpp_strategy_order/relieve",datas).then((res) => {
					_this.isDisable = false;
					if (res.statusCode === '0000') {
						_this.$vux.toast.text('提交解除成功','middle');
						_this.$router.go(-1)
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
					}
				})
			}
		},
		activated () {
			// console.log(0)
		}
	}
</script>

<style scoped>
	.item_box {
		background-color: #fff;
	}
	.icon_box {
		width: 1.4666rem;
		text-align: center;
	}
	.icon_box {
		line-height: 1rem;
	}
	.icon_box img {
		width: 0.64rem;
		height: 0.64rem;
	}
	.icon_box_2 img {
		width: 0.5rem;
		height: 0.5rem;
	}

	.text_box {
		height: inherit;
		border-bottom: 1px solid #ededed;
	}
	.text_box input {
		width: 100%;
		line-height: 1.2rem;
		color: #666;
        font-size: .35rem;
	}
	.btn_box {
		padding: 1.3333rem 0;
		text-align: center;
	}
	.btn_box button {
		color: #fff;
		font-size: 0.3466rem;
		padding: 0 1.3333rem;
		line-height: 1.0666rem;
		border-radius: 0.8rem;
	}

	.text_box p {
		line-height: 1rem;
		font-size: 0.3733rem;
		color: #666;
	}
	._radio_box span{
		font-size: 0.3466rem;
		color: #333;
		line-height: 1rem;
	}
	._radio_box span:before {
		content: "";
		display: inline-block;
		width: 5px;
		height: 5px;
		border: 1px solid #666;
		border-radius: 50%;
		margin-right: 10px;
		position: relative;
		bottom: 2px;
	}
	._radio_box span.active:before {
		border-color: #FF0000;
	}
	.trips_box {
		background-color: #fff;
		padding: 0.2666rem;
	}
	.trips_box p {
		text-indent: 2em;
		font-size: 0.3rem;
		color: #FF4C39;
	}
</style>
