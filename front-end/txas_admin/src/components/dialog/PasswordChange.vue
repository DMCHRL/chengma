<template>
	<!--修改密码弹窗-->
		<el-dialog
			custom-class="box_dialog"
		   :visible="dialogVisible"
		   width="20%"
           @close="close">
		  <h3 slot="title" class="dialog_title">{{title}}</h3>
		  <div class="input_box">
		  	<span class="import">原密码</span>
		  	<input type="password" name="" value="" placeholder="" v-model="password.old"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">新密码</span>
		  	<input type="password" name="" value="" placeholder="" v-model="password.new1"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">确认密码</span>
		  	<input type="password" name="" value="" placeholder="" v-model="password.new2"/>
		  </div>
		  
		  <div slot="footer" class="dialog_footer">
		  	<button @click="ChangePass">确认修改</button>
		  </div>
		</el-dialog>
</template>

<script>
	export default {
		props: ['title','dialogVisible',"url",'loginAccount'],
		data() {
			return {
				password: {
					old: '',
					new1: '',
					new2: ''
				}
			}
		},
		computed: {
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return {department: 'user'}
				}
			}
		},
		watch: {
			dialogVisible () {
				this.password = {old: '',new1: '',new2: ''}
			}
		},
		components: {
			
		},
		methods: {
			ChangePass () {
				let _this = this;
				let password = _this.password;
				
				if (password.old == "") {
					_this.$message({
			          message: '原密码不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (password.new1 == "") {
					_this.$message({
			          message: '新密码不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (password.new1 != password.new2) {
					_this.$message({
			          message: '密码不一致',
			          type: 'warning'
			        });
					return;
				}
				
				let datas = {
					"login": _this.loginAccount,
					"oldPassword": password.old,
					"newPassword": password.new1
				}
				_this.$until.superPost(_this.url,datas,function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '修改成功',
				          type: 'success'
				       });
				       
						_this.close();
						
					}else if (res.statusCode == "0100") {
						_this.$message({
				          message: '原密码错误',
				          type: 'warning'
				       });
				       
					}else {
						_this.$message({
				          message: '修改失败',
				          type: 'warning'
				        });
					}
				})
			},
			close () {
				this.$emit('close')
			}
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.box_dialog .input_box {
		margin-bottom: 10px;
	}
	
	.box_dialog .input_box span {
		display: inline-block;
		width: 40%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.box_dialog .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.box_dialog .input_box input,
	.box_dialog .input_box select {
		border: 1px solid #ccc;
		width: 40%;
		padding: 0 10px;
		border-radius: 5px;
	}
	
	.box_dialog .input_box p {
		cursor: pointer;
		text-align: center;
		font-size: 14px;
		color: #b4b4b4;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 14px;
		background-color: #6562b6;
		border-radius: 20px;
		padding: 5px 30px;
		color: #fff;
	}

</style>