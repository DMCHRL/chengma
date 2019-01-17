<template>
	<div class="content_box">
		
		<!--修改密码弹窗-->
		
		<password-change 
			:title="dialogTitle" 
			:dialogVisible="dialogVisible" 
			:url='url'
			:loginAccount="user.login"
			@close="dialogClose">
		</password-change>
		
		
		<div class="titling ">
			<span>基本信息</span>
		</div>
		<div class="message_box">
			
			<div class="_row clearfix">
				
				<div class="item_boxs pull-left">
					<span>姓名</span>
					<span>{{user.firstName}}</span>
				</div>
				<div class="item_boxs pull-left">
					<span>身份</span>
				    <span>{{user.department | idName}}</span>
				</div>
			</div>
			<div class="_row clearfix">
				<div class="item_boxs pull-left">
					<span>登录号</span>
					<span>{{user.login}}</span>
				</div>
				<div class="item_boxs pull-left">
					<span>登录密码</span>
					<span>*********</span>
					<el-button 
						class="pull-right _button"  
						type="primary" 
						icon="el-icon-edit" 
						slot="reference" 
						@click="dialogVisible = true">
					</el-button>
				</div>
			</div>
			<div class="_row clearfix">
				<div class="item_boxs pull-left">
					<span>创建日期</span>
				    <span>{{user.createdDate}}</span>
				</div>
				<div class="item_boxs pull-left">
					<span>邮箱</span>
				    <span v-show="showinput != 5">{{user.email}}</span>
				</div>
			</div>
			<div class="_row clearfix">
				<div class="item_boxs border_none pull-left">
					<span>交易账户</span>
					<!--<template v-for="item in accList">
						<span>{{item.account}}</span>
					</template>-->
					<el-select name="" v-model="acc">
						<!--<template v-for="item in accList">-->
							<!--<option>{{item.account}} &nbsp;&nbsp;&nbsp;&nbsp; {{item.accountName}}</option>-->
							<el-option
						      v-for="item in accList"
						      :key="item.value"
						      :label="item.account +'  '+ item.accountName"
						      :value="item.account">
						    </el-option>
						<!--</template>-->
					</el-select>
				</div>
				<div class="item_boxs pull-left">
					<span>电话</span>
				    <span v-show="showinput != 4">{{user.mobile}}</span>
				</div>
			</div>
			
			
		</div>
		<!-- <h1>欢迎登陆汇添溢管理后台！</h1> -->
	</div>
</template>

<script>
	import PasswordChange from "../dialog/PasswordChange"
	export default {
		data() {
			return {
				acc: '',
				accList: [],
				dialogVisible: false,
				innerVisible: false,
				zheng_image: '',
				fan_image: '',
				showinput: '0',
				
				oldPassword: '',
				newPassword: '',
				newPassword2: '',
				bank: {
					num: '',
					bankname: ''
				},
				dialogTitle: "修改用户密码",
				url: "/api/user/editPassword"
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
		filters: {
			idName: function(value) {
				// 身份(service 开户管理员) (operate 运营管理员) (teach 报教管理员)
				switch (value){
					case 'admin':
						return '超级管理员'
						break;
					case 'service':
						return '开户管理员'
						break;
					case 'operate':
						return '运营管理员'
						break;
					case 'teach':
						return '报教管理员'
						break;
          case 'strategy':
            return '策略管理员'
            break;
          case 'mt4':
            return '云端管理员'
            break;
					default:
						return '未知'
						break;
				}
			}
		},
		components: {
			PasswordChange
		},
		methods: {
			dialogClose () {
				this.dialogVisible=false;
			},
			
			GetAccountList () {
				
				let acclist = JSON.parse(localStorage.getItem("accList"));
				if (acclist) {
					this.accList = acclist;
					this.acc = acclist[0].account;
				}
			},
			GetBankMess () {
				let _this = this;
				let id = this.user.id;
//				console.log(id)
				_this.$until.superGet("/api/bank_info/get/"+id,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						
						if (res.data) {
							_this.bank = res.data;
						}
						
					}else {
						console.log("获取银行卡失败")
					}
				})
			}
		},
		mounted() {
//			this.GetAccountList();
//			this.GetBankMess();
		}
	}
</script>

<style scoped>
	h1 {
		font-size: 50px;
		padding: 100px 0;
		padding-left: 120px;
	}
	
	.message_box {
	margin: 20px;
	}
	._row {
		width: 820px;
		border-bottom: 1px dashed #c1c1c1;
	}
	._row:first-of-type{
		border-top: 1px solid #c1c1c1 ;
	}
	._row:last-of-type {
		border-bottom: 1px solid #c1c1c1 ;
	}
	.item_boxs {
		width: 50%;
	}
	.item_boxs span{
		display: inline-block;
		line-height: 40px;
		padding: 0 15px;
	}
	.item_boxs span:first-of-type{
		background-color: #f2825b;
		color: #fff;
		width: 160px;
		text-align: center;
	}

	._button {
		margin: 3px 5px;
	}
	.border_none .el-input__inner {
		border: none;
	}



	
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.ibs_dialog .input_box {
		margin-bottom: 10px;
	}
	
	.ibs_dialog .input_box span {
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
	
	.ibs_dialog .input_box input,
	.ibs_dialog .input_box select {
		border: 1px solid #ccc;
		width: 40%;
		padding: 0 10px;
		border-radius: 5px;
	}
	
	.ibs_dialog .input_box p {
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
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
</style>
