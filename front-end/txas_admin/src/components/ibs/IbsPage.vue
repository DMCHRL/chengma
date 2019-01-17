<template>
	<div class="content_box">
		
		<!--添加用户弹窗-->
		<el-dialog
			custom-class="ibs_dialog"
		   :show-close="false"
		   :visible.sync="dialogVisible"
		   width="30%">
		  <h3 slot="title" class="dialog_title">添加下级代理</h3>
		  
		  <div class="input_box">
		  	<span class="import">用户名</span>
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
		  <div class="input_box ">
		  	<span class="import">类型</span>
		  	<el-radio-group v-model="user.type">
			    <!--<el-radio :label="1">分公司</el-radio>-->
			    <el-radio :label="2">代理</el-radio>
			</el-radio-group>
		  </div>
		  <div class="input_box">
		  	<span class="import">登录名</span>
		  	<input type="text" name="" id="5" value="" placeholder="" v-model="user.login"/>
		  </div>
		  <!--<div class="input_box ">
		  	<span class="import">登录密码</span>
		  	<input type="password" name="" id="6" value="" placeholder="" v-model="user.password"/>
		  </div>
		  <div class="input_box ">
		  	<span class="import">确认密码</span>
		  	<input type="password" name="" id="7" value="" placeholder="" v-model="user.password2"/>
		  </div>-->
		  
		  
		  <div slot="footer" class="dialog_footer">
		  	<button @click="NewUser">确认添加</button>
		  	<button @click="dialogVisible = false">取消</button>
		  </div>
		</el-dialog>
		
		<div class="titling">
			<span>下级代理</span>
			<button @click="dialogVisible = true">添加</button>
		</div>
		
		<filter-work></filter-work>
		
		<user-list :list="list"></user-list>
		
		<!--分页-->
		<el-pagination background layout="prev, pager, next" :total="total_count">
		</el-pagination>
	</div>
</template>

<script>
	import UserList from "../list/UserList.vue"
	import FilterWork from "../common/FilterWork.vue"
	export default {
		data() {
			return {
				pageNum: 1,
				pageSize: 20,
				list: [],
				total_count: 0,
				dialogVisible: false,//添加用户弹窗
				user: {
					login: "", //登录名
					password: "",         // 密码
					password2: "",         // 密码
					firstName: "",     //名称
					email: "",          //邮箱
					mobile: "", // 电话号码
					eara: "",       // 区域
					type: 2 //分公司 1，代理2 
				}
			}
		},
		components: {
			FilterWork,
			UserList
		},
		methods: {
			NewUser () {
				let _this = this;
				let isCompany = _this.user.type == 1 ? 'Y':'N';
				
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
//				if (_this.user.password != _this.user.password2) {
//					_this.$message({
//			          message: '密码不一致',
//			          type: 'warning'
//			        });
//					return;
//				}
				
				let datas = {
					"login": _this.user.login, //登录名
					"password": _this.user.password,         // 密码
					"firstName": _this.user.firstName,     //名称
					"email": _this.user.email,          //邮箱
					"mobile": _this.user.mobile, // 电话号码
					"isCompany": isCompany,         // 分公司  Y, N
					"isProxy": "Y",          // 代理   Y, N
					"eara": _this.user.eara       // 区域
				}
				_this.$until.superPost("/api/user/save",datas,function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: '添加成功',
				          type: 'success'
				        });
				        _this.user =  {
							login: "", //登录名
							password: "",         // 密码
							password2: "",         // 密码
							firstName: "",     //名称
							email: "",          //邮箱
							mobile: "", // 电话号码
							eara: "",       // 区域
							type: 1 //分公司 1，代理2 
						};
						_this.dialogVisible = false;
						_this.initPage();
					}else{
						_this.$message({
				          message: '添加失败',
				          type: 'warning'
				        });
					}
				})
			},
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.pageNum,
					"page_size": _this.pageSize,
					"formParams":{}
				};
				_this.$until.superPost("/api/user/list",datas,function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.total_count = parseInt(res.data.total_count);
					}
				})
			}
		},
		mounted() {
			this.initPage();
		}
	}
</script>

<style scoped>

	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	.ibs_dialog .input_box {
		margin-bottom: 10px;
	}
	.ibs_dialog .input_box span{
		display: inline-block;
		width: 40%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	.ibs_dialog .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	.ibs_dialog .input_box input{
		border: 1px solid #ccc;
		width: 40%;
		padding: 2px 10px;
		border-radius: 5px;
	}
	.dialog_footer {
		text-align: center;
	}
	.dialog_footer button{
		font-size: 18px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 20px;
		color: #fff;
	}
	.dialog_footer button:last-of-type{
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 0px 28px;
		margin-left: 20px;
	}
	
</style>