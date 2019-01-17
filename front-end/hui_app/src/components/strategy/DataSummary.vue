<template>
	<div class="sec_2 flex_bet">
			<div class="left_box flex_one">
				<div class="sec_box left_content">
					<div class="item_box flex_bet flex_align_center" v-if="!my">
						<span>帐户属性</span>
						<span>实盘帐户</span>
					</div>
					<div class="item_box flex_bet flex_align_center" v-if="!my">
						<span>交易方式</span>
						<span>{{strategy.tradeType}}</span>
					</div>
					<div class="item_box flex_bet flex_align_center">
						<span>最大回撤</span>
						<span>{{strategy.fundBack | numFilter}}%</span>
					</div>
					
					<div class="item_box flex_bet flex_align_center">
						<span>交易手数</span>
						<span>{{strategy.lots | numFilter}}手</span>
					</div>
					<div class="item_box flex_bet flex_align_center">
						<span>累计收益率</span>
						<span>{{strategy.monthProfit | numFilter}}%</span>
					</div>
				</div>
			</div>
			<div class="sec_box right_box">
				<div class="item_box flex_bet flex_align_center" v-if="!my">
					<div class="">
						<img src="../../assets/img/9_01.png"/>
						<span>入金总额</span>
					</div>
					<span>${{strategy.fundIn | numFilter}}</span>
				</div>
				<div class="item_box flex_bet flex_align_center" v-if="!my">
					<div class="">
						<img src="../../assets/img/9_02.png"/>
						<span>出金总额</span>
					</div>
					<span>${{strategy.fundOut | numFilter}}</span>
				</div>
				<div class="item_box flex_bet flex_align_center">
					<div class="">
						<img src="../../assets/img/9_03.png"/>
						<span>账户余额</span>
					</div>
					<span>${{strategy.balance | numFilter}}</span>
				</div>
				<div class="item_box flex_bet flex_align_center">
					<div class="">
						<img src="../../assets/img/9_04.png"/>
						<span>账户净值</span>
					</div>
					<span>${{strategy.netWorth | numFilter}}</span>
				</div>
				<div class="item_box flex_bet flex_align_center">
					<div class="">
						<img src="../../assets/img/kai_09.png"/>
						<span>累计收益</span>
					</div>
					<span class="red">${{strategy.totalProfit | numFilter}}</span>
				</div>
			</div>
		</div>
</template>

<script>
	export default {
		props: ['strategy','my'],
		data() {
			return {
				
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
		components: {
		},
		methods: {
			
		},
		mounted () {
			
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
	.sec_2 {
		padding: 0 0.2666rem;
	}
	.left_box {
		padding-right: 0.2666rem;
	}
	.left_content{
	}
	.right_box {
		width: 56%;
	}
	.item_box {
		height: 1.0133rem;
		padding: 0 0.2666rem;
	}
	.item_box img {
		width: 0.5333rem;
		height: 0.5333rem;
		margin-right: 0.2666rem;
		position: relative;
		top: -2px;
	}
	.item_box span {
		font-size: 0.35rem;
		color: #333;
	}
	.item_box span.red {
		color: #FF3C3C;
	}
	
</style>