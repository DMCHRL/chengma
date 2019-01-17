<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">添加CRM用户</h3>
		  
		  <!--<div class="input_box">
		  	<span class="import">登录名</span>
		  	<input type="text" name="" id="5" value="" placeholder="" v-model="user.login"/>
		  </div>-->
		  <div class="input_box ">
		  	<span class="import">类型</span>
		  	<!--<el-radio-group v-model="user.type">
			    <el-radio :label="1">分公司</el-radio>
			    <el-radio :label="2">代理</el-radio>
			</el-radio-group>-->
			<select name="" v-model="user.department">
				<option v-if="user1.department == 'admin'" value="company">分公司</option>
				<option value="proxy">代理</option>
				<option value="service" v-if="user1.department == 'admin'">客服</option>
				<option value="account" v-if="user1.department == 'admin'">财务</option>
				<option value="user">普通用户</option>
			</select>
		  </div>
		  <div class="input_box">
		  	<span class="import">名称</span>
		  	<input type="text" name="" id="1" value="" placeholder="" v-model="user.firstName"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">手机号</span>
		  	<input type="text" name="" id="2" value="" placeholder="" v-model="user.mobile"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">邮箱</span>
		  	<input type="text" name="" id="3" value="" placeholder="" v-model="user.email"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">地区</span>
		  	<input type="text" name="" id="4" value="" placeholder="" v-model="user.eara"/>
		  </div>
		  
		  <div slot="footer" class="dialog_footer">
		  	<button @click="NewUser">确认添加</button>
		  </div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				user: {
					firstName: "",     //名称
					email: "",          //邮箱
					mobile: "", // 电话号码
					eara: "",      // 区域
					department: 'company'
				},
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
		components: {

		},
		methods: {
			NewUser () {
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
				if (_this.user.eara == "") {
					_this.$message({
			          message: '地区不能为空',
			          type: 'warning'
			        });
					return;
				}
				if (_this.user.login == "") {
					_this.$message({
			          message: '登录名不能为空',
			          type: 'warning'
			        });
					return;
				}
				
				let datas = {
					"login": _this.user.email,
					"department": _this.user.department, //部门       
					"firstName": _this.user.firstName,     //名称
					"mobile": _this.user.mobile, // 电话号码/登录号
					"email": _this.user.email,          //邮箱
					"eara": _this.user.eara ,      // 区域
				}
				_this.$until.superPost("/api/user/save",datas,function (res) {
					////console.log(res)
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
				          message: '添加失败',
				          type: 'warning'
				        });
					}
				})
			},
		},
		mounted() {

		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>