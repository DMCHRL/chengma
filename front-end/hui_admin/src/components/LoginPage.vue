<template>
	<div class="login_section loginback" :style="styleString">
		<div class="form_box">
			<!--<h3>CRM管理系统</h3>-->
			<div class="title_img">
				<img src="../assets/img/logo_01.png"/>
			</div>
			<div class="input_box">
				<img src="../assets/img/l_01.png" alt="" />
				<input type="text" name="" id="1" value="" placeholder="请输入账号" v-model="username" @keyup.enter="tologin" />
			</div>
			<div class="input_box">
				<img src="../assets/img/l_02.png" alt="" />
				<input type="password" name="" id="2" value="" placeholder="请输入密码" v-model="userpass" @keyup.enter="tologin"/>
			</div>
			<div class="ver_box clearfix">
				<input class="pull-left" type="" name="" id="" value="" v-model="VerificaInput" @keyup.enter="tologin"/>
				<div class="img_box pull-right">
					<img @click="changeImg" :src="VerificaImg" alt="验证码图片" />
				</div>
			</div>
			<div class="btn_box">
				<button @click="tologin" ref='login_btn'>登录</button>
				<!--<button @click="toregister">注册</button>-->
			</div>
			<p class="copy_text">Copyright©2018-2019 HRL.All rights reseverd.</p>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				styleString: '',
				username: '',
				userpass: '',
				VerificaImg: '',
				VerificaInput: ''
			}
		},
		components: {

		},
		methods: {
			changeImg () {
				this.VerificaImg = this.$until.host + "/api/generateVerifyCode?rm=" + Math.random(4);
//				console.log(this.VerificaImg)
			},
//			getUserAccount (department) {
//				let _this = this;
//				_this.$until.superGet("/api/user/loadMyAccount",function (res) {
//					////console.log(res)
//					if (res.statusCode == '0000') {
////						////console.log()
////						return
//						if (res.data.length > 0) {
//							localStorage.setItem("account",res.data[0].account);
//							localStorage.setItem("name",res.data[0].accountName);
//							localStorage.setItem("accList",JSON.stringify(res.data));
//						}
//
//						_this.$message({
//				          message: '登录成功',
//				          type: 'success'
//				        });
//						if (department == "admin" ) {
//							_this.$router.push('/')
//						}else if(department == "service"){
//							_this.$router.push('/task')
//						}else if (department == "account") {
//							_this.$router.push('/tasked')
//						}else {
//							_this.$router.push('/manage')
//						}
//					}
//
//				})
//			},
			tologin() {
				let _this = this;
				let btn_el = _this.$refs.login_btn;

				if (_this.username == "") {
					_this.$message({
			          message: '账户不能为空',
			          type: 'warning'
			        });
			        return;
				}
				if (_this.userpass == "") {
					_this.$message({
			          message: '密码不能为空',
			          type: 'warning'
			        });
			        return;
				}
				if (_this.VerificaInput == "") {
					_this.$message({
			          message: '验证码不能为空',
			          type: 'warning'
			        });
			        return;
				}

				btn_el.innerHTML = '登录中...';
				btn_el.setAttribute('disabled','true');

				let datas = {
					'username': _this.username,
					'password': _this.userpass,
					"verifyCode": _this.VerificaInput
				}

				_this.$post("/api/authenticate",datas).then((res) => {
//					console.log(res)

					btn_el.innerHTML = '登录';
				    btn_el.removeAttribute('disabled');

					if (res.statusCode == '0000') {
						_this.$until.tokener.set(res.data.id_token);
						_this.$store.commit("setToken",res.data.id_token);
						localStorage.setItem("user",JSON.stringify(res.data.user));

						_this.$message({
				          message: '登录成功',
				          type: 'success'
				        });
//						if (department == "admin" ) {
							_this.$router.push('/')
//						}else if(department == "service"){
//							_this.$router.push('/task')
//						}else if (department == "account") {
//							_this.$router.push('/tasked')
//						}else {
//							_this.$router.push('/manage')
//						}

//						_this.getUserAccount(res.data.user.department);
					}else if (res.statusCode == "0100") {
						_this.changeImg();
						if (res.msgCode == "login.messages.error.errorVerifyCode") {
							_this.$message({
					          message: '验证码错误',
					          type: 'warning'
					        });
						}else {
							_this.$message({
					          message: '密码错误',
					          type: 'warning'
					        });

						}
					}else {
						_this.$message({
				          message: '登录失败',
				          type: 'warning'
				        });
					}
				})

			},
			toregister() {
				this.$router.push({path: '/regiter'})
//				window.open("http://tlb.txasfx.com/application")
			},
			computedWH () {
				let doc = document.documentElement;
				let body = document.body;
				let ww = doc.clientWidth || body.clientWidth;
				let wh = doc.clientHeight || body.clientHeight;
				this.styleString = 'width:'+ ww+'px;height:'+wh+'px;';
			}
		},
		mounted() {
			let _this = this;

			_this.changeImg();

			_this.computedWH();
			window.onresize = () => _this.computedWH();

		}
	}
</script>

<style scoped>

	input,
	button,
	select,
	textarea {
		outline: none;
		-webkit-appearance: none;
		border-radius: 0;
	}

	::-webkit-input-placeholder {
		/* WebKit browsers */
		color: #fff;
	}

	:-moz-placeholder {
		/* Mozilla Firefox 4 to 18 */
		color: #fff;
	}

	::-moz-placeholder {
		/* Mozilla Firefox 19+ */
		color: #fff;
	}

	:-ms-input-placeholder {
		/* Internet Explorer 10+ */
		color: #fff;
	}

	.login_section {
		position: relative;
		height: 100%;
		width: 100%;
		background-size: auto 100%;
		background-repeat: no-repeat;
		background-position: center;
	}

	.form_box {
		height: 450px;
		width: 400px;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 50%;
		right: 0;
		margin: auto;
		border: 1px solid #fff;
		/*box-shadow: 0px 0px 5px 3px #9292a3 inset;*/
		border-radius: 10px;
		box-sizing: border-box;
		padding: 0 40px;
		padding-top: 30px;
		text-align: center;
		background-color: rgba(18, 18, 19, 0.2)
	}

	.form_box h3 {
		font-size: 28px;
		color: #fff;
		padding-bottom: 25px;
		margin-bottom: 25px;
		border-bottom: 2px solid #fff;
	}

	.form_box .input_box {
		border-radius: 20px;
		border: 1px solid #fff;
		margin-bottom: 20px;
	}

	/*.input_box img {
		position: relative;
		top: 8px;
	}*/

	.input_box input {
		height: 34px;
		width: 70%;
		line-height: 100%;
		padding: 0 10px;
		background-color: transparent;
		border: 0;
		color: #fff;
	}
	.ver_box {
		margin-bottom: 20px;
	}
	.ver_box input {
		width: 60%;
		border-radius: 20px;
		border: 1px solid #fff;
		height: 34px;
		background-color: transparent;
		color: #fff;
		padding: 0 20px;
	}
	.ver_box .img_box {
		overflow: hidden;
		width: 32%;
	}
	.ver_box img {
		height: 34px;
	}
	.btn_box button {
		cursor: pointer;
		font-size: 18px;
		color: #191a41;
		padding: 4px 30px;
		background-color: #FFFFFF;
		border: 1px solid #fff;
		border-radius: 20px;
		margin-top: 20px;
	}

	.btn_box button:last-of-type {
		color: #fff;
		background-color: transparent;
		border: 1px solid #fff;
		margin-left: 0px;
	}

	.copy_text {
		font-size: 12px;
		color: #fff;
		position: absolute;
		bottom: 5px;
		left: 0;
		right: 0;
		text-align: center;
	}
	.title_img {
		text-align: center;
		/*padding: 10px 0;*/
		padding-bottom: 30px;
	}
	.title_img img {
		width: 90px;
	}
</style>
