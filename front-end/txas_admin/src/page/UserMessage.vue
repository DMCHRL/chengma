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
			<div class="_row clearfix mb200">
				
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
	import SetCard from "@/components/dialog/SetCard"
	import PasswordChange from "@/components/dialog/PasswordChange"
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
			// this.GetAccountList();
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
	}
	
	.item_boxs {
		width: 400px;
		border-bottom: 1px dashed #c1c1c1;
        margin-right: 20px;
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
    .mb200 {
        margin-bottom: 100px;
    }

</style>