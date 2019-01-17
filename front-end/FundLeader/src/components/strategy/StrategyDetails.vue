<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content" id="hui-content">
            
        
        
		<!-- <div class="head_section" v-show="!leftOptions.ishide">
			<div class="head_sec myback">
				<x-header :left-options="leftOptions">{{leftOptions.title}}</x-header>
			</div>
		</div> -->
		
		<div class="sec_box sec_1 backimg0">
			<p>{{strategy.strategyText}}</p>
		</div>
		
		<data-summary :strategy="strategy" :my="false"></data-summary>
		
		
		<chart-section></chart-section>
		
		
		<trade-history :hisData="tradeHistory"></trade-history>
		
		
		<div v-transfer-dom class="weui-mask" v-show="followShow">
		  	<div class="weui-dialog">
				<div class="dialog_icon_box">
					<img src="../../assets/img/can_01.png"/>
				</div>
				<div class="dialog_cancel_box" @click="followShow = false">
					<icon type="cancel" ></icon>
				</div>
				<div class="dialog_text">
					<p>当前交易账号</p>
					<h3>1254863</h3>
					<p>风险参数设置</p>
					<h3 class="red">稳健</h3>
				</div>
				<div class="dialog_btn_box flex_bet">
					<button>确定跟单</button>
					<button @click="ToBinding">修改信息</button>
				</div>
		  	</div>
		</div>
		
		<risk-notice :show="show12" :context="context" @ToBinding="ToBinding" @cancle="show12 = false"></risk-notice>
        
		</div>
        
        <div class="bottom_btn myback" @click="getRiskNotice">
        	<button>立即跟单</button>
        </div>
        
	</div>
</template>

<script>
	import { Popup,XHeader, Icon, TransferDomDirective as TransferDom } from 'vux'

	import DataSummary from "./DataSummary"
	import ChartSection from "./ChartSection"
	import TradeHistory from "./TradeHistory"
	import RiskNotice from "../other/RiskNotice"
	export default {
		directives: {
	    	TransferDom
	  	},
		data() {
			return {
				headOption: {ishide: false,title: '策略详情',backText: '',showBack: true},
				followShow: false,
				strategy: {},
				show12: false,
				tradeHistory: [],
				context:　''
			}
		},
		computed: {
			account () {
				return this.$route.query.account;
			}
		},
		components: {
			XHeader,
			Icon,
			DataSummary,
			ChartSection,
			TradeHistory,
			Popup,
			RiskNotice
		},
		methods: {
			readAndAgreen() {
//				localStorage.setItem('isAgreen',true);
				this.$router.push({path: '/bind?id='+this.strategy.strategyId})
			},
			ToBinding () {
				this.show12 = false;
				this.$router.push({path: '/bind?id='+this.strategy.strategyId})
			},
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
			showFollow () {
//				this.followShow = true;
//				let isAgreen = localStorage.getItem('isAgreen');
//				if (isAgreen) {
//					this.$router.push({path: '/bind?id='+this.strategy.strategyId})
//				}else {
									
//				}
			},
			getTradeHistory () {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_trade/findTradeByAccountLimit/"+_this.account).then((res) => {
//					console.log(res)
					if (res.data) {
						_this.tradeHistory = res.data;
					}
					
				})
			},
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_data/findByAccount/"+_this.account).then((res) => {
					// console.log(res)
					_this.strategy = res.data;
                    _this.headOption.title = res.data.strategyName;
				})
			}
		},
		mounted () {
			this.initPage();
			this.getTradeHistory();
		}
	}
</script>

<style scoped>
	
	.sec_box {
		background-color: #fff;
		box-shadow: 0px 3px 4px #ededed;
		border-radius: 0.15rem;
		overflow: hidden;
	}
	.sec_1 {
		padding: 0.2666rem;
		margin: 0.2666rem;
		background-size: 10%;
	}
	.sec_1 h3 {
		font-size: 0.48rem;
		color: #333;
		text-align: center;
		line-height: 0.8rem;
	}
	.sec_1 p {
		font-size: 0.35rem;
		color: #333;
		text-indent: 2em;
		line-height: 0.6666rem;
		font-weight: bold;
	}
	.sec_chart,
	.sec_history{
		margin: 0.2666rem;
	}
	
	.weui-dialog {
		overflow: auto;
		padding-top: 0.8666rem;
	}
	.dialog_icon_box {
		position: absolute;
		top: -0.8666rem;
		width: 1.7333rem;
		height: 1.7333rem;
		line-height: 1.7333rem;
		background-color: #ff5752;
		border-radius: 50%;
		left: 0;
		right: 0;
		margin: 0 auto;
	}
	.dialog_icon_box img {
		width: 0.8533rem;
	}
	.dialog_cancel_box {
		position: absolute;
		top: 0.2rem;
		right: 0.15rem;
		z-index: 50001;
	}
	.weui-icon-cancel {
		color: #666;
	}
	.dialog_text {
		padding: 0.2666rem 0;
	}
	.dialog_text p {
		color: #666666;
		font-size: 0.3466rem;
		line-height: 0.6rem;
	}
	.dialog_text h3 {
		font-size: 0.6666rem;
		color: #333;
		font-weight: normal;
	}
	.dialog_text h3.red {
		color: #ff5752;
	}
	.dialog_btn_box{
		overflow: hidden;
		border-radius: 0 0 0.0666rem 0.0666rem;	
	}
	.dialog_btn_box button {
		background-color: #ff5752;
		width: 49.8%;
		line-height: 1.0666rem;
		color: #fff;
	}
	
	
</style>