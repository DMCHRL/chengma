<template>
  	<div class="login_page fillcontain">
	  	<transition name="form-fade" mode="in-out">
	  		<section class="form_contianer" v-show="showLogin">
		  		<div class="manage_tip">
		  			<p>领航方舟</p>
		  		</div>
		    	<el-form :model="loginForm" :rules="rules" ref="loginForm">
					<el-form-item prop="username">
						<el-input v-model="loginForm.username" placeholder="用户名"></el-input>
					</el-form-item>
					<el-form-item prop="password">
						<el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
					</el-form-item>
                    <el-form-item prop="verifyCode">
                    	<div class="flex_bet ver_box">
                            <el-input  placeholder="验证码" v-model="loginForm.verifyCode"></el-input>
                            <img :src="verifyCodeImg" alt="">
                        </div>
                    </el-form-item>
					<el-form-item>
				    	<el-button type="primary" @click="submitForm('loginForm')" class="submit_btn">登陆</el-button>
				  	</el-form-item>
				</el-form>
	  		</section>
	  	</transition>
  	</div>
</template>

<script>
    import {setStore} from '@/config/mUtils'
	import {login} from '@/api/getData'
	import {mapActions, mapState} from 'vuex'
    import { baseUrl } from '../config/env'
	export default {
	    data(){
			return {
                verifyCodeImg: '',
				loginForm: {
					username: '',
					password: '',
                    verifyCode: ''
				},
				rules: {
					username: [
			            { required: true, message: '请输入用户名', trigger: 'blur' },
			        ],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
                    verifyCode: [
                    	{ required: true, message: '请输入验证码', trigger: 'blur' }
                    ],
				},
				showLogin: false,
			}
		},
		mounted(){
            
			this.showLogin = true;
			if (!this.adminInfo.id) {
                this.changeImg();
    			this.getAdminData()
    		}
		},
		computed: {
			...mapState(['adminInfo']),
		},
		methods: {
			...mapActions(['getAdminData']),
            changeImg () {
            	this.verifyCodeImg = baseUrl + "/api/generateVerifyCode?rm=" + Math.random(4);
            },
			async submitForm(formName) {
				this.$refs[formName].validate(async (valid) => {
					if (valid) {
                        
						const res = await login({
                            username: this.loginForm.username,//	是	string	用户名
                            password: this.loginForm.password,//	是	string	密码
                            verifyCode: this.loginForm.verifyCode
                            })
						if (res.statusCode == '0000') {
                            
                            setStore('f_token',res.data.id_token);
                            setStore('f_admin_info',res.data.user);
                            this.$store.commit('saveToken', res.data.id_token);
                            this.$store.commit('saveAdminInfo', res.data.user);
                            
							this.$message({
		                        type: 'success',
		                        message: '登录成功'
		                    });
							this.$router.push('manage')
						}else{
							this.$message({
		                        type: 'error',
		                        message: res.message
		                    });
						}
					} else {
						this.$notify.error({
							title: '错误',
							message: '请输入正确的信息',
							offset: 100
						});
						return false;
					}
				});
			},
		},
		watch: {
			adminInfo: function (newValue){
				if (newValue.id) {
					this.$message({
                        type: 'success',
                        message: '检测到您之前登录过，将自动登录'
                    });
					this.$router.push('manage')
				}
			}
		}
	}
</script>

<style lang="less" scoped>
	@import '../style/mixin';
	.login_page{
		background-color: #324057;
	}
	.manage_tip{
		position: absolute;
		width: 100%;
		top: -100px;
		left: 0;
		p{
			font-size: 34px;
			color: #fff;
		}
	}
	.form_contianer{
		.wh(320px, 250px);
		.ctp(320px, 210px);
		padding: 25px;
		border-radius: 5px;
		text-align: center;
		background-color: #fff;
		.submit_btn{
			width: 100%;
			font-size: 16px;
		}
	}
	.tip{
		font-size: 12px;
		color: red;
	}
	.form-fade-enter-active, .form-fade-leave-active {
	  	transition: all 1s;
	}
	.form-fade-enter, .form-fade-leave-active {
	  	transform: translate3d(0, -50px, 0);
	  	opacity: 0;
	}
    .ver_box img{
        height: 36px;
        margin-left: 2px;
        border-radius: 5px;
    }
</style>
