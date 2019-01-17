<template>
	<div class="content_box">
		<div class="sec_1">
			<div class="con_box">
				<img src="../../assets/img/logo_01.png"/>
			</div>
		</div>
		<div class="sec_2">
			<h3>登录汇添溢</h3>
			<div class="input_box flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/login_01.png"/>
				</div>
				<div class="flex_one">
					<input type="text" name="" id="" value="" placeholder="请输入手机号" v-model="phoneNum" />
				</div>
			</div>
			<div class="input_box flex_bet flex_align_center">
				<div class="icon_box">
					<img src="../../assets/img/login_02.png"/>
				</div>
				<div class="flex_one">
					<input type="text" name="" id="" value="" placeholder="请输入验证码" v-model="messCode"/>
				</div>
				<div class="get_btn">
					<button @click="getMessCode" :disabled="codeBtnDisablued">{{btnName}}</button>
				</div>
			</div>
			
			<div class="btn_box">
				<button class="myback" @click="toLogin" :disabled="loginDisablued">{{loginText}}</button>
			</div>
		</div>
		
	</div>
</template>

<script>
	import { mapActions }from'vuex'
	import {setCookie} from '../../utils/util' 
	export default {
		data() {
			return {
				phoneNum: '',
				messCode: '',
				btnName: '获取验证码',
				codeBtnDisablued: false,
				loginDisablued: false,
				num: 60,
				loginText: '确认登录'
			}
		},
		components: {
		},
		methods: {
			...mapActions(['getUserMess']),
			codeBtnCountdown() {
				let _this = this;
				_this.btnName = _this.num+'s后重新获取';
				if (_this.num <= 0) {
					_this.codeBtnDisablued = false;
					_this.btnName = '获取验证码';
					_this.num = 60;
					return;
				}
				_this.num--;
				setTimeout(function () {
					_this.codeBtnCountdown();
				},1000)
			},
			getMessCode () {
				let _this = this;
				if (_this.phoneNum == '') {
					_this.$vux.toast.text('手机号不能为空','middle')
					return;
				}
				_this.codeBtnDisablued = true;
				_this.codeBtnCountdown();
				_this.$post('/api/mobileValidate/sendCode',{"mobileNum": _this.phoneNum}).then((res) => {
			        if (res.statusCode == '0000') {
			        	// _this.$vux.toast.text('验证码发送成功','middle')
			        }else {
						// _this.$vux.toast.text('验证码发送','middle')
					}
			    })
			},
			
			toLogin () {
				let _this = this;
				
				if (_this.phoneNum == '') {
					_this.$vux.toast.text('手机号不能为空','middle')
					return;
				}
				if (_this.messCode == '') {
					_this.$vux.toast.text('验证码不能为空','middle')
					return;
				}
				
				let datas = {
					"mobileNum": this.phoneNum,
					"validateCode": this.messCode
				}
				_this.loginDisablued = true;
				_this.loginText = '登录中...';
				_this.$post('/api/mobileValidate/verification',datas).then((res) => {
					_this.loginDisablued = false;
					if (res.data) {
						localStorage.setItem("id_token",res.data.id_token);
						localStorage.setItem("user",JSON.stringify(res.data.user));
						
						_this.$store.commit("setToken",res.data.id_token);
						_this.$store.commit("setUserInfo",res.data.user);
						
						_this.getUserMess();
						_this.loginText = '登录成功';
						_this.$router.push({path: '/'})
					}else {
						_this.$vux.toast.text('验证码错误','middle')
					}
				})
			}
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	.content_box {
		background-color: #fff;
		overflow: hidden;
	}
	.sec_1,
	.sec_1 .con_box:after{
		background: -webkit-linear-gradient(left, #f07871, #e6263e);
		/* Safari 5.1 - 6.0 */
		background: -o-linear-gradient(right, #f07871, #e6263e);
		/* Opera 11.1 - 12.0 */
		background: -moz-linear-gradient(right, #f07871, #e6263e);
		/* Firefox 3.6 - 15 */
		background: linear-gradient(to right, #f07871, #e6263e);
		/* 标准的语法（必须放在最后） */
	}
	.sec_1 {
		position: relative;
		height: 196px;
		box-sizing: border-box;
		text-align: center;
	}
	.sec_1 .con_box{
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		padding-top: 2rem;
		z-index: 1;
	}
	.sec_1 .con_box:after {
		width: 120%;
		height: 186px;
		position: absolute;
		left: -10%;
		top: 50%;
		z-index: -1;
		content: '';
		border-radius: 0 0 50% 50%;
	}
	.sec_1 img {
		width: 4.7733rem;
	}
	
	.sec_2 {
		margin-top: 128px;
		text-align: center;
		padding: 0 1.3333rem;
	}
	.sec_2 h3 {
		font-size: 0.56rem;
		color: #e6233d;
		margin-bottom: 0.9333rem;
	}
	.icon_box img {
		width: 0.5333rem;
		height: 0.5333rem;
	}
	.input_box {
		height: 0.9333rem;
		border-bottom: 1px solid #e6233d;
		margin-bottom: 0.6666rem;
	}
	.input_box input {
		text-align: left;
		color: #666;
		width: 100%;
        font-size: .35rem;
		padding: 0 0.5333rem;
	}
	.get_btn button {
		color: #E6233D;
		font-size: 0.35rem;
		padding-left: 0.2rem;
		border-left: 1px solid #E6233D;
		background-color: transparent;
	}
	.btn_box button {
		width: 100%;
		line-height: 45px;
		font-size: 0.3733rem;
		color: #fff;
		border-radius: 0.6666rem;
		margin-top: 0.6666rem;
	}
</style>