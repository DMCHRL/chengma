<template>
	<div class="content_box">
		<div class="logo_box">
			<div class="img_box">
				<img src="../../assets/img/logo_01.png"/>
			</div>
		</div>
		<div class="sec_2">
			<div class="input_box">
				<p>账号</p>
				<input type="text" name="" id="" value="" placeholder="请输入手机号" v-model="phoneNum" />
			</div>
            <div class="input_box">
            	<p>验证码</p>
            	<input type="text" name="" id="" value="" placeholder="请输入验证码" v-model="messCode"/>
            </div>
            
		</div>
        <div class="sec_3">
            <div class="get_btn">
            	<button @click="getMessCode" :class="codeBtnDisablued?'active':''" :disabled="codeBtnDisablued">{{btnName}}</button>
            </div>
            
            <div class="sub_btn">
            	<button @click="toLogin" :disabled="loginDisablued">{{loginText}}</button>
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
                    _this.loginText = '登录';
                    
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
		overflow: hidden;
	}
	
    .logo_box {
        margin-top: 141px;
    }
    .logo_box .img_box {
        width: 40%;
        margin: 0 auto;
    }
    
	.sec_2 {
		text-align: center;
		width: 65%;
        margin: 0 auto;
		margin-top: 50px;
	}
    .input_box {
        margin-bottom: 0.533rem;
    }
    .input_box p {
        font-size: 0.48rem;
        color: #22295d;
        font-weight: bold;
    }
	.input_box input {
        text-align: center;
		border-bottom: 1px solid #22295d;
        font-size: .4rem;
		color: #666;
		width: 100%;
        line-height: 50px;
	}
    .sec_3 {
        text-align: center;
    }
	.get_btn button {
		color: #fff;
		font-size: 0.373rem;
		background-color: #fca60e;
        border-radius: 40px;
        padding: 0 15px;
        line-height: 0.8rem;
	}
    .get_btn button.active {
        background-color: #b2b2b2;
    }
	.sub_btn button {
		width: 90%;
		color: #fff;
		font-size: 0.426rem;
		background-color: #fca60e;
		border-radius: 6px;
		line-height: 1.173rem;
        margin-top: 0.6rem;
	}
</style>