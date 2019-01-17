<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content" id="hui-content">
            
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/8_01.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="number" name="" id="1" value="" placeholder="输入交易账户" v-model="account" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box icon_box_2">
				<img src="../../assets/img/login_02.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="password" name="" id="2" value="" placeholder="输入交易密码" v-model="passWord"/>
			</div>
		</div>
		
		<div class="item_box flex_bet">
			<div class="icon_box">
				<img src="../../assets/img/4_05.png"/>
			</div>
			<div class="text_box flex_one">
				<p>
					<span>风险参数设置</span>
					<span @click="getContent" ><x-icon type="ios-help-outline" size="17"></x-icon></span>
				</p>
				<ul class="_radio_box">
					<!--<li @click="changType(1)">
						<span :class="typeNum == 1 ? 'active':''">保守</span>
					</li>-->
					<li @click="changType('STEADY')">
						<span :class="typeNum == 'STEADY' ? 'active':''">稳健</span>
					</li>
					<!-- <li @click="changType('RADICAL')">
						<span :class="typeNum == 'RADICAL' ? 'active':''">激进</span>
					</li> -->
				</ul>
			</div>
		</div>
		
		<div class="btn_box">
			<button class="myback" :disabled="isDisable" @click="Commit">确认跟单</button>
			<div>
				<router-link to="/exchanger">
					<button class="kai" >无交易账户，去开户</button>
				</router-link>
			</div>
		</div>
		
		<risk-notice :show="show12" :context='context' @ToBinding="show12 = false" @cancle="show12 = false">
			<div class="bottom_btn myback" slot="bottom_btn">
				<button class="" @click="show12 = false">已阅读并同意</button>
			</div>
		</risk-notice>
        
        </div>
        
	</div>
</template>

<script>
	import RiskNotice from "../other/RiskNotice"
	export default {
		data() {
			return {
				headOption: {title: '策略跟单',backText: '',showBack: true},
				typeNum: 'STEADY',
				isDisable: false,
				account: '',
				passWord: '',
				show12: false,
				context: ''
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			},
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return null;
				}
			}
		},
		components: {
			RiskNotice
		},
		methods: {
			getContent () {
				let _this = this;
				if (_this.context) {
					_this.show12 = true;
				}else {
					_this.$fetch("/api/hpp_protocol/getType/RISK_PARAM_SETTING").then((res) => {
						// console.log(res)
						if (res.data) {
							_this.context = res.data.context;
							_this.show12 = true;	
						}
					})
				}
			},
			ToBinding () {
				this.show12 = false;
			},
			changType (num) {
				this.typeNum = num;
			},
			Commit () {
				let _this = this;
				if (_this.account == '') {
					_this.$vux.toast.text('请输入交易账号','middle')
					return;
				}
				if (_this.passWord == '') {
					_this.$vux.toast.text('请输入交易密码','middle')
					return;
				}
				let datas = {
					account: _this.account,//	是	string	交易账号
					password: _this.passWord,//	是	string	密码
					mobileNum: _this.user.mobile,//	是	string	手机号码
					risk: _this.typeNum,//	是	string	风险设置 STEADY(稳健) RADICAL(激进)
					strategyId: _this.id//
				}
				_this.isDisable = true;
				_this.$post("/api/hpp_strategy_order/follow",datas).then((res) => {
					_this.isDisable = false;
					if (res.statusCode == '0000') {
						_this.$vux.toast.text('提交跟单成功,耐心等待审核通过','middle')
						_this.$router.push({
							path: '/strategy'
						})
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
					}
				})
			}
		},
		activated () {
			this.account =  '';
			this.passWord =  '';
		}
	}
</script>

<style scoped>
	.item_box {
		background-color: #fff;
	}
	.icon_box {
		width: 1.4666rem;
		text-align: center;
	}
	.icon_box {
		line-height: 1.2rem;
	}
	.icon_box img {
		width: 0.64rem;
		height: 0.64rem;
	}
	.icon_box_2 img {
		width: 0.5rem;
		height: 0.5rem;
	}
	
	.text_box {
		height: inherit;
		border-bottom: 1px solid #ededed;
	}
	.text_box input {
		width: 100%;
		line-height: 1.2rem;
		color: #666;
        font-size: .35rem;
	}
	.btn_box {
		padding-top: 3rem;
		padding-bottom: 1.3333rem;
		text-align: center;
	}
	.btn_box button {
		color: #fff;
		font-size: 0.3466rem;
		width: 60%;
		text-align: center;
		line-height: 1.0666rem;
		border-radius: 0.8rem;
		margin-bottom: .7rem;
	}
	.btn_box button.kai {
		line-height: .8rem;
		background-color: #FF8080;
	}
	.btn_box span {
		display: block;
		color: #005CBF;
	}
	
	.text_box p {
		line-height: 1.2rem;
		font-size: 0.3733rem;
		color: #666;
	}
	.text_box svg {
		position: relative;
		top: 3px;
		margin-left: 0.1333rem;
	}
	._radio_box span{
		font-size: 0.3466rem;
		color: #333;
		line-height: 1rem;
	}
	._radio_box span:before {
		content: "";
		display: inline-block;
		width: 5px;
		height: 5px;
		border: 1px solid #666;
		border-radius: 50%;
		margin-right: 10px;
		position: relative;
		bottom: 2px;
	}
	._radio_box span.active:before {
		border-color: #FF0000;
		background-color: #FF0000;
	} 
	/* ._btn_box {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
	}
	._btn_box button {
		padding: 0 0.2666rem;
		background-color: #f3917f;
		border: 1px solid #f3917f;
		color: #fff;
		font-size: 0.4rem;
		border-radius: 0.4rem;
		line-height: 50px;
	} */
</style>