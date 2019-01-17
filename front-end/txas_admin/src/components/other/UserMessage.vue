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
		
		<!--修改银行卡-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible4" width="25%">
			<set-card @close="dialogClose" :userid="userid"></set-card>
		</el-dialog>
		
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
				    <span v-text="user.department == 'company'? '分公司':(user.department == 'proxy'?'代理':'普通用户')"></span>
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
			<div class="_row clearfix">
				<div class="item_boxs pull-left">
					<span>开户银行</span>
				    <span>{{bank.bank}}</span>
						<el-button 
							class="pull-right _button"  
							type="primary" 
							icon="el-icon-edit" 
							slot="reference" 
							@click="showSetCard">
						</el-button>
				</div>
				<div class="item_boxs pull-left">
					<span>银行卡号</span>
					<span>{{bank.bNum}}</span>
					
				</div>
			</div>
			<div class="_row clearfix">
				<div class="item_boxs pull-left">
					<span>银行分行</span>
						<span>{{bank.subBank}}</span>
				</div>
				<div class="item_boxs pull-left">
					<span>银行所在地</span>
					<span>{{bank.location}}</span>
				</div>
			</div>
			
		</div>
	</div>
</template>

<script>
	import SetCard from "../dialog/SetCard"
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
				url: "/api/user/editPassword",
				userid: null,
				dialogVisible4: false
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
		components: {
			SetCard,
			PasswordChange
		},
		methods: {
			showSetCard () {
				this.userid = this.user.id;
				this.dialogVisible4=true;
			},
//			cancelDialog () {
//				this.innerVisible = false;
//			},
			dialogClose () {
				this.dialogVisible=false;
//				this.dialogVisible2=false;
//				this.dialogVisible3=false;
				this.dialogVisible4=false;
//				this.dialogVisible5 = false;
				this.GetBankMess();
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
				_this.$until.superGet("/api/bank_info/get/"+id,function (res) {
					// console.log(res)
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
			this.GetAccountList();
			this.GetBankMess();
		}
	}
</script>

<style scoped>
	
	.message_box {
		margin: 20px;
	}
	._row {
		width: 1000px;
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
		background-color: #eae9ff;
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