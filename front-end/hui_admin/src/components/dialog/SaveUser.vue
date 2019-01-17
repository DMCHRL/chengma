<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">添加/编辑用户</h3>
		  
		  <div class="input_box">
		  	<span class="import">名称</span>
		  	<input type="text" name="" id="1" value="" placeholder="" v-model="user.firstName"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">手机号</span>
		  	<input type="text" name="" id="2" value="" placeholder="" v-model="user.mobile"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">邮箱（登陆）</span>
		  	<input type="text" name="" id="3" value="" placeholder="" v-model="user.email"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">权限</span>
		  	<!-- <input type="text" name="" id="4" value="" placeholder="" v-model="user.department"/> -->
				<!-- 身份(service 开户管理员) (operate 运营管理员) (teach 报教管理员) -->
				
				<el-radio-group v-model="user.department">
					<el-radio :label="'service'">开户管理员</el-radio><br />
					<el-radio :label="'operate'">运营管理员</el-radio><br />
					<el-radio :label="'teach'">投教管理员</el-radio><br />
          <el-radio :label="'strategy'">策略管理员</el-radio><br />
          <el-radio :label="'mt4'">云端管理员</el-radio><br />
				</el-radio-group>
		  </div>
		  
		  <div slot="footer" class="dialog_footer_a">
				<el-button type="primary" round @click="Commit" :disabled="disabled">确认</el-button>
		  </div>
	</div>
</template>

<script>
	export default {
		props: {
			mess: {}
		},
		data() {
			return {
				user: {
					firstName: "",     //名称
					email: "",          //邮箱
					mobile: "", // 电话号码
					eara: "",      // 区域
					department: 'service',
					id: ''
				},
				disabled: false
			}
		},
		computed: {
			user1 () {
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
			mess () {
				this.user = JSON.parse(JSON.stringify(this.mess));
			},
		},
		components: {

		},
		methods: {
			Commit () {
				let _this = this;
				
				if (_this.user.firstName == "") {
					_this.$message({
			          message: '用户名不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (_this.user.mobile == "") {
					_this.$message({
			          message: '手机号不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (_this.user.email == "") {
					_this.$message({
			          message: '邮箱不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (!/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(_this.user.email)) {
					_this.$message({
			          message: '邮箱格式错误',
			          type: 'warning'
			        });
					return;
				}
				_this.disabled = true;
				let datas = {
					"login": _this.user.email,
					"department": _this.user.department, //部门       
					"firstName": _this.user.firstName,     //名称
					"mobile": _this.user.mobile, // 电话号码/登录号
					"email": _this.user.email,          //邮箱
					'id': _this.id
				}
				_this.$post("/api/user/save",datas).then((res) => {
					_this.disabled = false;
					if (res.statusCode == "0000") {
						_this.$message({
								message: '添加成功',
								type: 'success'
							});
							
							_this.user =  {
								firstName: "",     //名称
								email: "",          //邮箱
								mobile: "", // 电话号码
								eara: "",      // 区域
								department: 'company'
							};
						_this.$emit("close");
					}else{
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
			},
		},
		mounted() {
      this.user = JSON.parse(JSON.stringify(this.mess));
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
	.el-radio {
		line-height: 25px;
	}
	.dialog_footer_a {
		padding-top: 30px;
		text-align: center;
	}
	.dialog_footer_a button {
		padding: 10px 24px;
	}
</style>
