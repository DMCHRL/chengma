<template>
	<header class="myback">
		<div class="container-fluid">
			<div class="clearfix">
				<div class="pull-left headname">
					<router-link to="/">
						<img src="../../assets/img/logo_01.png"/>
						<span>外汇跟单系统管理员后台</span>
					</router-link>
				</div>
				<div class="pull-right headbutton">
					<!--<router-link to="/into" v-if="user.department != 'service'">
						<button>充值</button>
					</router-link>
					<router-link to="/out" v-if="user.department != 'service'">
						<button>提现</button>
					</router-link>-->
					<!--<button @click="rateChange">汇率调整</button>-->
					<button @click="tologout" class="logout"><img src="../../assets/img/logout_1.png"/>退出</button>
				</div>
			</div>
		</div>
		<!--提现弹窗-->
		<el-dialog custom-class="ibs_dialog" :show-close="false" :visible.sync="dialogVisible" width="20%">
			<!--添加银行卡弹窗-->
			<!--<add-bank-card :innerVisible="innerVisible" @cancelDialog="cancelDialog"></add-bank-card>-->

			<h3 slot="title" class="dialog_title">提现</h3>
			<div class="input_box">
				<span>提现账号</span>
				<select name="zhanhao">
					<option value="1">1890670</option>
				</select>
			</div>
			<div class="input_box">
				<span>提现金额</span>
				<input type="text" name="" id="" value="" placeholder="可用保证金$2500" />
			</div>
			<div class="input_box">
				<span>选择银行卡</span>
				<select name="card">
					<option value="1">6217003210011234</option>
				</select>
				<p><i @click="innerVisible = true">+添加新银行卡</i></p>
			</div>
			<div slot="footer" class="dialog_footer">
				<button @click="dialogVisible = false">确认</button>
				<button @click="dialogVisible = false">取消</button>
			</div>
		</el-dialog>
		<!--汇率-->
		<el-dialog custom-class="ibs_dialog" :show-close="false" :visible.sync="dialogVisible2" width="20%">
			<h3 slot="title" class="dialog_title">汇率调整</h3>
			<div class="input_box">
				<span>充值汇率</span>
				<input type="number" name="" id="" value="" placeholder="" v-model="chongRate" />
			</div>
			<div class="input_box">
				<span>提现汇率</span>
				<input type="number" name="" id="" value="" placeholder="" v-model="tiRate" />
			</div>
			<div slot="footer" class="dialog_footer">
				<button @click="rateComit">确认</button>
				<button @click="dialogVisible2 = false">取消</button>
			</div>
		</el-dialog>
	</header>
</template>

<script>
//	import AddBankCard from "../dialog/AddBankCard"
	export default {
		props: {
			tablist: {
				type: Array,
				default: function() {
					return []
				}
			}
		},
		data() {
			return {
				chongRate: 6.7,
				tiRate: 6.5,
				dialogVisible: false,
				innerVisible: false,
				dialogVisible2: false
			}
		},
		components: {
//			AddBankCard

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
		methods: {
			tologout() {
				let _this = this;
				_this.$until.tokener.remove();
				localStorage.removeItem("account");
				localStorage.removeItem("accList");
				localStorage.removeItem("name");
				localStorage.removeItem("user");
				localStorage.removeItem("bindid");

				_this.$router.push({path: '/login'});
			},
			cancelDialog () {
				this.innerVisible = false;
			},
			goInto () {
				window.open("http://tlb.txasfx.com/cash")
//				this.$router.push({path: '/into'})
			},
			rateChange () {
				let _this = this;

				_this.$until.superPost('/api/tlb-rate/getAll',{},function (res) {
					//console.log(res)
					if (res.statusCode == '0000') {
						let datas = res.data[0];
//						////console.log(datas)
						_this.tiRate = datas.rate;
						_this.chongRate = datas.recharge;
				        _this.dialogVisible2 = true;
					}
				})


			},
			rateComit () {
				let _this = this;

				let datas = {
					"id": "1",
					"rate": _this.tiRate,
					"recharge": _this.chongRate
				}
				_this.$until.superPost('/api/tlb-rate/updateTlbRate',datas,function (res) {
//					////console.log(res)
					if (res.statusCode == '0000') {
						_this.$message({
				          message: '修改成功',
				          type: 'success'
				        });
				        _this.dialogVisible2 = false;
					}
				})
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	/*公共头部*/

	header {
		background-color: #19183e;
		line-height: 55px;
		height: 55px;
		overflow: hidden;
	}

	.headname {
		width: 13%;
		text-align: center;
		overflow: hidden;
	}

	.headname span {
		font-size: 16px;
		color: #fff;
		white-space: nowrap;
		line-height: 55px;
	}
	.headname img {
		width: 50px;
		/*margin-right: 20px;*/
	}

	.headtab {
		margin-left: -15px;
	}

	.headtab a {
		display: inline-block;
		font-size: 16px;
		color: #fff;
		border-top: 4px solid #19183e;
		padding: 20px 30px;
	}

	.headtab a.router-link-exact-active {
		border-top: 4px solid #b0aeff;
		background-color: #2c2b5c;
	}

	.headtab a:hover {
		border-top: 4px solid #b0aeff;
		background-color: #2c2b5c;
	}

	.headtab .badge {
		position: relative;
		top: -10px;
		left: 10px;
		background-color: #f14b3b;
	}

	.headbutton {
		width: 87%;
		text-align: right;
	}

	.headbutton button {
		background-color: #f14b3b;
		font-size: 14px;
		color: #fff;
		border-radius: 30px;
		margin-right: 15px;
		line-height: 30px;
		padding: 0 30px;
	}

	.headbutton button.logout {
		color: #fff;
		background-color: transparent;
		border: 0;
	}
	.headbutton button img {
		width: 20px;
		margin-right: 10px;
	}

	.uplond_box {
		text-align: center;
	}
	.uplond_box label{
		display: inline-block;
		width: 140px;
		height: 90px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	.uplond_box label span {
		line-height: 90px;
		color: #666;
		font-size: 14px;
	}
	.uplond_box label img {
		width: 100%;
		height: auto;
	}
</style>
