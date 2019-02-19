<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>

		<div class="hui_content" id="hui-content">

		<div class="sec_1 myback ">
			<div class="flex_bet flex_align_center">
				<div class="left_box">
					<div class="img_box">
						<img :src="userInfo.imageUrl" />
					</div>
				</div>
				<div class="mid_box flex_one">
					<!-- <p>{{account}}</p> -->
					<div class="flex_row">
						<span>{{incomeRate}}</span>
						<div class="small_box">
							<p>累计收益率</p>
							<p>%</p>
						</div>
					</div>
					<p v-if="strategy.strategyName">跟单策略：{{strategy.strategyName}} |
						<router-link :to="'/strategy-details?account='+ strategy.strategyAccount">
							<img src="../../assets/img/2_02.png" />
						</router-link>
					</p>
					<p v-else>跟单策略：暂无跟单策略</p>
				</div>
			</div>
			<div class="flex_bet">
				<div class="item_bottom flex_col">
					<span>入金</span>
					<p><span>$</span>{{strategy.fundIn | numFilter}}</p>
				</div>
				<div class="item_bottom flex_col">
					<span>出金</span>
					<p><span>$</span>{{strategy.fundOut | numFilter}}</p>
				</div>
			</div>

		</div>

		<data-summary :strategy="strategy" :my="true"></data-summary>

		<chart-section></chart-section>

		<trade-history v-if="!tradeHistory.length" :hisData="tradeHistory"></trade-history>


		<!-- <risk-notice :show="show12" @ToBinding="stopFollow" @cancle="show12 = false"></risk-notice> -->
		<risk-notice :show="show12" :context="context" @ToBinding="stopFollow" @cancle="show12 = false"></risk-notice>

		</div>

    <div class="bottom_btn myback" >
    	<router-link v-if="strategy.strategyId == null" to="/strategy">
    		<button>此账号暂无跟单，去跟单</button>
    	</router-link>
    	<button v-else :disabled="isDisable" @click="getRiskNotice">解除跟单</button>
    </div>

	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import DataSummary from "./DataSummary"
	import ChartSection from "./ChartSection"
	import TradeHistory from "./TradeHistory"
	import RiskNotice from "../other/RiskNotice"
	export default {
		data() {
			return {
				headOption: {title: '我的账号',backText: '',showBack: true},
				strategy:{},
				isDisable: false,
				tradeHistory: [],
				show12: false,
				context: ''
			}
		},
		filters: {

		  numFilter(value) {

		  // 截取当前数据到小数点后两位

		    let realVal = Number(value).toFixed(2)

		    // num.toFixed(2)获取的是字符串

		    return Number(realVal)

		  }

		},
		computed: {
			...mapState(['userInfo', 'userMess']),
// 			userMess () {
// 				let userMess = localStorage.getItem('userMess');
// 				if (userMess) {
// 					return JSON.parse(userMess)
// 				}else {
// 					return {}
// 				}
// 			},
			account () {
				return this.$route.query.account;
			},
			incomeRate () {
				return this.strategy.monthProfit ? this.strategy.monthProfit: '0.00';
			}
		},
		components: {
			DataSummary,
			ChartSection,
			TradeHistory,
			RiskNotice
		},
		methods: {
			getRiskNotice () {
				let _this = this;
				if (_this.context) {
					_this.show12 = true;
				}else {
					_this.$fetch("/api/hpp_protocol/getType/RISK_PROTOCOL").then((res) => {
						// console.log(res)
						if (res.data) {
							_this.context = res.data.context;
							_this.show12 = true;
						}
					})
				}
			},
			stopFollow () {
				this.show12 = false;
				this.$router.push({
					path: '/release?id='+this.strategy.strategyId+'&account='+this.account
				})
			},
			getTradeHistory () {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_trade/findTradeByAccountLimit/"+_this.account).then((res) => {
					// console.log(res)
					if (res.data) {
						_this.tradeHistory = res.data;
					}

				})
			},
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_data/loadByAccount/"+_this.account).then((res) => {
					//console.log(res)
					_this.strategy = res.data;
				})
			}
		},
		mounted () {
			this.initPage();
			this.getTradeHistory();
			this.headOption.title = this.account;
		}
	}
</script>

<style scoped>
	.sec_1 {
		margin-bottom: 0.2rem;
	}
	.left_box {
		padding: 0.6666rem;
	}
	.left_box .img_box {
		width: 1.8666rem;
		height: 1.8666rem;
		border: 0.1066rem solid #fcc8c8;
		border-radius: 50%;
		overflow: hidden;
	}
	.left_box img {
		width: 100%;
	}

	.mid_box p {
		color: #fff;
		font-size: 0.3733rem;
	}

	.mid_box p:last-of-type {
		font-size: 0.2933rem;
	}

	.small_box p {
		font-size: 0.2933rem;
		line-height: 0.5rem;
		margin-top: 0.1rem;
	}

	.mid_box span {
		color: #fff;
		font-size: 0.96rem;
		margin: 0 0.1333rem;
	}

	.mid_box img {
		width: 0.28rem;
		height: 0.28rem;
	}

	.right_box {
		height: 3.1333rem;
	}

	.right_box span {
		color: #eb0021;
		font-size: 0.2933rem;
		background-color: #fff;
		display: inline-block;
		padding: 0.1333rem 0.6666rem;
		border-radius: 0.3666rem 0 0 0.3666rem;
	}

	.item_bottom {
		width: 50%;
		background-color: rgba(255,255,255,.3);
		text-align: center;
		padding: 0.1333rem 0;
	}
	.item_bottom p {
		font-size: 0.5333rem;
		color: #fff;
	}
	.item_bottom span {
		font-size: 0.3466rem;
		color: #fff;
		line-height: 0.6666rem;
	}


	.titling {
		line-height: 1.0666rem;
		padding: 0 0.2rem;
		border-bottom: 1px solid #e6e6e6;
	}
	.titling span {
		border-left: 0.08rem solid #e40b2f;
		padding-left: 0.2666rem;
		font-size: 0.3466rem;
		color: #333;
	}

	.sec_box {
		background-color: #fff;
		box-shadow: 0px 3px 4px #ededed;
		border-radius: 0.15rem;
		overflow: hidden;
	}
	.sec_chart,
	.sec_history{
		margin: 0.2666rem;
	}
</style>
