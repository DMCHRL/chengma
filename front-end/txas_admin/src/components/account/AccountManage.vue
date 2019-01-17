<template>
	<div class="content_box">
		
		<!--新增套利宝账户-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible3" width="20%">
			<add-tlb @close="dialogClose" :userid="user.id"></add-tlb>
		</el-dialog>
		
		<!--资金内转-->
		<el-dialog custom-class=""  :visible.sync="dialogVisible2" width="20%">
			<money-transfer @close="dialogClose" :userid="user.id"></money-transfer>
		</el-dialog>
		
		<!--修改密码弹窗-->
		<password-change 
			:title="dialogTitle" 
			:dialogVisible="dialogVisible" 
			:url='url'
			:loginAccount= 'accountNum'
			@close="dialogClose">
		</password-change>
		
		<div class="top_content">
			<div class="row">
				<div class="col-md-3 col-xs-3">
					<div class="item_box item_box0">
						<p>真实账号总余额</p>
						<p><span>${{TotalBalance}}</span></p>
					</div>
				</div>
				<div class="col-md-3 col-xs-3">
					<div class="item_box item_box1">
						<p>真实账户数量</p>
						<p><span>{{TotalRevenue}}</span>个</p>
					</div>

				</div>
				<div class="col-md-3 col-xs-3">
					<div class="item_box item_box2">
						<p>总可用保证金</p>
						<p><span>${{TotalAvailableMargin}}</span></p>
					</div>
				</div>
				<div class="col-md-3 col-xs-3">
					<div class="item_box item_box3" v-if="user.department != 'user'">
						<p>总佣金</p>
						<p><span>${{TotalCashWithdrawal}}</span></p>
					</div>
				</div>
			</div>
		</div>
		<div class="down_content">
			<div class="titling">
				<span>我的账户</span>
				<button @click="dialogVisible3 = true">新增账户</button>
			</div>
			<div class="content">
				<div class="list_section">
					<ul class="clearfix">
						<li class="pull-left account_card ecn_account" v-if="user.department == 'company' || user.department == 'proxy' ">
							<h4>佣金账户</h4>
							<div class="yue_box clearfix">
								<span class="pull-left">总佣金</span>
								<span class="pull-right">$<span>{{brokeData.total}}</span></span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">可提佣金</span>
								<span class="pull-right">{{brokeData.balance}}</span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">佣金奖励</span>
								<span class="pull-right" 
									v-text="'$'+brokeData.lotAmount+'.00/标准手'"></span>
							</div>
							<div class="mess_box link_box clearfix">
								<b @click="dialogVisible2 = true">资金内转</b>
							</div>
							<!--<div class="mess_box clearfix">
								<span class="pull-left">佣金奖励（代理）</span>
								<span class="pull-right">{{brokeData.lotPercent *100}}%</span>
							</div>-->
						</li>
						<li class="pull-left account_card" v-for="item in acclist">
							<h4>{{item.accountName}}</h4>
							<div class="yue_box clearfix">
								<span class="pull-left">余额</span>
								<span class="pull-right">$<span v-text="item.balance ? item.balance : '0.00'"></span></span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">交易账户</span>
								<span class="pull-right">{{item.account}}</span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">交易密码</span>
								<span class="pull-right clearfix">
									<span class="pull-left">******</span>
									<el-button 
										class="pull-right _button"  
										type="primary" 
										icon="el-icon-edit" 
										slot="reference" 
										@click="changeAccountPassword(item.account)">
									</el-button>
								</span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">观摩密码</span>
								<span class="pull-right clearfix">
									<span class="pull-left">******</span>
									<el-button 
										class="pull-right _button"  
										type="primary" 
										icon="el-icon-edit" 
										slot="reference" 
										@click="changeWatchAccountPassword(item.account)">
									</el-button>
								</span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">账户类型</span>
								<span class="pull-right" 
									v-text="item.group == 'demoTX' ? '模拟账户' : (item.grounp == 'TXA2'? '体验账户':'真实账户')">{{item.group}}</span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">可用保证金</span>
								<span class="pull-right" v-text="item.freeMargin ? item.freeMargin : '0.00'"></span>
							</div>
							<div class="mess_box clearfix">
								<span class="pull-left">杠杆</span>
								<span class="pull-right">1 : 10</span>
							</div>
							<div class="mess_box link_box clearfix">
								<router-link :to="'/record?account='+item.account"><b>交易详情</b></router-link>
							</div>
							<!--<div class="btn_box">
								<button @click="dialogVisible2 = false">修改信息</button>
							</div>-->
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		
		<div class="trip_box" v-show="!acclist.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无账号</p>
		</div>
		
	</div>
</template>

<script>
	import AddTlb from "../dialog/AddTlb"
	import MoneyTransfer from "../dialog/MoneyTransfer"
	import PasswordChange from "../dialog/PasswordChange"
	export default {
		data() {
			return {
//				tab1: 1,
//				tab2: 1,
				dialogVisible: false,//密码修改
				dialogVisible2: false,
				dialogVisible3: false,
				acclist: [],
				brokeData: {
					total: 0,
					balance: 0,
					lotAmount: 0,
					lotPercent: 0
				},
				
				dialogTitle: "修改账户密码",
				url: "/api/tlb-account/editPassword",
				accountNum: '',
//				TotalBalance: 0,//总余额
//				TotalRevenue: 0,//总收益
//				TotalAvailableMargin: 0,//总可以保证金
//				TotalCashWithdrawal: 0,//总可提现金额
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
			},
			TotalBalance () {
				let acclist = this.acclist;
				let ban = 0;
				acclist.forEach(function (item) {
					if (item.group == 'TXA3') {
						ban += item.balance;
					}
				})
				return ban.toFixed(2);
			},
			TotalRevenue () {
				let acclist = this.acclist;
				let ban = 0;
				acclist.forEach(function (item) {
					if (item.group == 'TXA3') {
						ban ++;
					}
				})
				return ban;
			},
			TotalAvailableMargin () {
				let acclist = this.acclist;
				let ban = 0;
				acclist.forEach(function (item) {
					if (item.group == 'TXA3') {
						ban += item.freeMargin;
					}
				})
				return ban.toFixed(2);
			},
			TotalCashWithdrawal () {
				return this.brokeData.total;
			}
		},
		components: {
			AddTlb,
			MoneyTransfer,
			PasswordChange
		},
		methods: {
			changeAccountPassword (account) {
//				console.log(account)
				this.dialogTitle = "修改账户密码";
				this.url = "/api/tlb-account/editPassword";
				this.accountNum = account;
				this.dialogVisible=true;
			},
			changeWatchAccountPassword (account) {
//				console.log(account)
				this.dialogTitle = "修改观摩密码";
				this.url = "/api/tlb-account/editSeePassword";
				this.accountNum = account;
				this.dialogVisible=true;
			},
			dialogClose () {
				this.dialogVisible=false;
				this.dialogVisible2=false;
				this.dialogVisible3=false;
				this.initPage();
			},
			
			getUserAccount () {
				let _this = this;
				_this.$until.superGet("/api/user/loadMyAccount",function (res) {
//					console.log(res)
					if (res.statusCode == '0000') {

						if (res.data.length > 0) {
							localStorage.setItem("accList",JSON.stringify(res.data));
							_this.acclist = res.data;
						}
						
					}
					
				})
			},
			getBroke () {
				let _this = this;
				_this.$until.superGet("/api/tlb-commission/getConfiguration/"+_this.user.id,function (res) {
//						console.log(res)
					if (res.statusCode == '0000') {
						_this.brokeData = res.data;
					}
				})
			},
			initPage () {
//				let acclist = JSON.parse(localStorage.getItem("accList"));
//				this.acclist = acclist;
				this.getUserAccount();
			},
		},
		mounted() {
			this.initPage();
			this.getBroke();
		}
	}
</script>

<style scoped>
	
	.top_content .item_box {
		padding: 28px 0;
		text-align: center;
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_1.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
	}
	
	.top_content .item_box0 {
		background-size: 160% 100%;
	}
	
	.top_content .item_box1 {
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_2.png);
	}
	
	.top_content .item_box2 {
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_3.png);
	}
	
	.top_content .item_box3 {
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_4.png);
	}
	
	.top_content .item_box p:first-of-type {
		margin-bottom: 20px;
	}
	
	.top_content .item_box .pull-left {
		width: 70%;
	}
	
	.top_content .item_box .pull-right {
		width: 30%;
	}
	
	.top_content .item_box span {
		font-size: 30px;
	}
	
	
	.titling {
	    margin-top: 20px;
	}
	
	.content {
		background-color: #fff;
		padding-top: 20px;
	}
	
	
	.account_card {
		border: 1px solid #6562b6;
		width: 23%;
		text-align: center;
		margin-bottom: 20px;
		margin-right: 15px;
	}
	.account_card h4 {
		background-color: #6562B6;
		color: #fff;
		padding: 10px 0;
	}
	.account_card  span{
		font-size: 14px;
		color: #3e3e3e;
	}
	.account_card .yue_box span{
		line-height: 27px;
	}
	.account_card .yue_box span span{
		font-size: 27px;
	}
	.account_card .yue_box {
		padding: 15px;
		border-bottom: 1px dotted #ccc;
		margin-bottom: 10px;
	}
	.account_card .mess_box{
		padding-bottom: 10px ;
	}
	.account_card .mess_box>span:nth-child(1) {
		width: 45%;
		text-align: right;
	}
	.account_card .mess_box span:nth-child(2) {
		width: 50%;
		text-align: left;
		padding-right: 40px;
	}
	.account_card .btn_box button {
		margin: 15px 0;
		background-color: #f14b3b;
		border-radius: 20px;
		padding: 5px 30px;
		color: #fff;
	}
	.ecn_account {
		border: 1px solid #ff743d;
	}
	.ecn_account h4 {
		background-color: #ff743d;
	}
	.ecn_account .link_box b {
		cursor: pointer;
		color: #ff743d;
		border: 1px solid #ff743d;
	}
	
	/*交易详情*/
	.link_box b {
		color: #6562B6;
		font-size: 12px;
		float: right;
		margin: 0px 20px;
		border: 1px solid #6562B6;
		border-radius: 10px;
		padding: 2px 10px;
	}
	._button {
		padding: 3px 20px;
	}
</style>