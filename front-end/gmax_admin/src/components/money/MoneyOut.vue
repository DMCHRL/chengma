<template>
	<div class="content_box">
		<div class="titling clearfix">
			<span>账户提现</span>
		</div>
		<div class="message_box">

			
					<div class="input_box clearfix">
						<span>提现账户</span>
						<!--<input type="text" placeholder="请输入账户号" />-->
						<el-autocomplete class="inline-input" v-model="account" :fetch-suggestions="querySearch" placeholder="请输入账户" @select="handleSelect" clearable>
							<template slot-scope="{ item }">
								<div class="name">{{ item.account }} &nbsp;&nbsp;&nbsp;<span class="addr">{{ item.accountName }}</span></div>
							</template>
						</el-autocomplete>
					</div>
					<div class="input_box clearfix">
						<span>账户姓名</span>
						<el-input type="text" :disabled="true" id="1" placeholder="" v-model="accName"></el-input>
					</div>
					<div class="input_box clearfix">
						<span>提现金额</span>
						<el-input type="text" value="" placeholder="请输入美金" v-model="usd" @change="usdChange" @focus="usd_trip = true"></el-input>
                        <p v-show="usd_trip" class="trip">注意：单笔提现最大金额7000美金，可多笔申请</p>
					</div>
					<div class="input_box clearfix" v-if="false">
						<span>到账金额</span>
						<el-input type="text" :disabled="true" value="" placeholder="自动换算人民币" v-model="money"></el-input>
					</div>
					<div class="input_box clearfix">
						<span>到账银行</span>
						<el-input type="text" :disabled="true" value="" placeholder="" v-model="bank.bank"></el-input>
					</div>
					<div class="input_box clearfix">
						<span>银行卡号</span>
						<el-input type="text" :disabled="true" value="" placeholder="" v-model="bank.bNum"></el-input>
					</div>
					<div class="input_box clearfix">
						<span>备注</span>
						<el-input 
							type="textarea"
  							:rows="2"
							placeholder="请输入内容" 
							v-model="beizhu"></el-input>
					</div>

			<div class="btn_box">
				<button @click="Confirm">确认</button>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				usd: '',
				account: '',
				acclist: [],
				accName: '',
				beizhu: '',
				bank: {
					bank: '',
					bNum: ''
				},
                usd_trip: false
			}
		},
		computed: {
			user() {
				let userStr = localStorage.getItem("user");
				if(userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return {
						department: 'user'
					}
				}
			},
			money() {
				if(this.usd == '') {
					return ""
				}
				return this.usd * 6.7;
			}
		},
		components: {

		},
		methods: {
            usdChange (value) {
                if (value > 7000) {
                    this.usd = 7000;
                }
            },
			querySearch(queryString, cb) {
				var acclist = this.acclist;
				var results = queryString ? acclist.filter(this.createFilter(queryString)) : acclist;
				// 调用 callback 返回建议列表的数据
				//console.log(results)
				cb(results);
			},
			createFilter(queryString) {
				return(restaurant) => {
					return(restaurant.account.indexOf(queryString) === 0);
				};
			},
			handleSelect(item) {
				//console.log(item);
				this.account = item.account;
				this.accName = item.accountName;
			},
			Confirm() {
				let _this = this;
				if (_this.account == '') {
					_this.$message({
						message: "请输入账户号",
						type: 'warning'
					});
					return;
				}
				if (_this.usd == '') {
					_this.$message({
						message: "请输入提现金额",
						type: 'warning'
					});
					return;
				}
                if (_this.usd > 7000) {
                	_this.$message({
                		message: "单笔提现最大金额7000美金，可多笔申请",
                		type: 'warning'
                	});
                	return;
                }
				
				if (_this.bank.bNum == '') {
					_this.$message({
						message: "抱歉银行卡信息缺失，无法提现，请联系客服",
						type: 'warning'
					});
					return;
				}
				
				let datas = {
					"account": _this.account,
					"amount": _this.usd,
					"body": _this.beizhu
				};
				
				_this.$until.superPost("/api/tlb-fund-apply/withdrawals", datas, function(res) {
					////console.log(res)

					_this.usd = '';
					if(res.statusCode == "0000") {
						_this.$message({
							message: "提现申请成功",
							type: 'success'
						});
					} else {
						_this.$message({
							message: res.msgCode,
							type: 'warning'
						});
					}
				})
			},
			pushAccount() {
				let _this = this;
				let initlist = [];
				let acclist = [];

				if(_this.user.department == "admin" || _this.user.department == "service") {
					let datas = {
						"page_number": 1, //页码
						"page_size": 10000, // 每页条数
						"formParams": {}

					};

					let url = "/api/tlb-account/pageList";

					_this.$until.superPost(url, datas, function(res) {
						//						console.log(res)
						if(res.statusCode == "0000") {

							initlist = res.data.list;
							initlist.forEach(function(item) {
								acclist.push({
									"account": item.account,
									"accountName": item.accountName
								})
							})
							_this.acclist = acclist;
						}
					})

				} else {
					let list = localStorage.getItem("accList");
					if(!list) return;
					list = JSON.parse(localStorage.getItem("accList"));
					list.forEach(function(item) {
						acclist.push({
							"account": item.account,
							"accountName": item.accountName
						})
					})
					_this.acclist = acclist;

				}
				//console.log(acclist)
			},
			GetBankMess() {
				let _this = this;
				let id = this.user.id;
				_this.$until.superGet("/api/bank_info/get/" + _this.user.id, function(res) {
//					console.log(res)
					if(res.statusCode == "0000") {
						if (res.data) {
							_this.bank = res.data;
							
						}

					} else {
						console.log("获取银行卡失败")
					}
				})
			}
		},
		mounted() {
			this.pushAccount();
			this.GetBankMess();
		}
	}
</script>

<style scoped>
	.message_box {
		padding: 20px 0;
	}
	
	.input_box  {
		margin-bottom: 25px;
		position: relative;
	}
	
    .input_box  span {
		float: left;
		line-height: 35px;
		width: 12%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.input_box  span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.input_box  p {
		margin-left: 6%;
		font-size: 14px;
		color: #999999;
	}
	
	.input_box  input,
	.input_box  select {
		border: 1px solid #ccc;
		width: 20%;
		padding: 5px 10px;
		border-radius: 5px;
	}
	
	.input_box  img {
		width: 200px;
		margin: 10px auto;
		margin-left: 164px;
	}
    .input_box .trip {
        font-size: 14px;
        color: #FF4A4A;
        position: absolute;
        left: 25%;
        top: 10px;
    }
	
		.btn_box {
			padding: 20px 0;
		}
	
		.btn_box button {
			background-color: #F14B3B;
			border-radius: 20px;
			padding: 8px 40px;
			color: #fff;
			font-size: 14px;
			margin-left: 10%;
		}
	
	.input_box  .el-radio-button__orig-radio:checked+.el-radio-button__inner {
		background-image: url(../../assets/img/btn_1.png);
		background-position: right bottom;
		background-repeat: no-repeat;
	}
	
	.input_box  button {
		background-color: #fff;
		margin-left: 200px;
		color: #FF3C3C;
	}
	.el-autocomplete {
		width: 250px;
	}
	.el-input {
		width: 250px;
	}
	.input_box  .el-textarea {
		float: left;
		width: 250px;
	}
</style>