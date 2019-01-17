<template>
	<div >
		
                <template v-if="item.orderType == 'BUY' || item.orderType == 'SELL'">
                    
                
				<div class="item_box flex_bet flex_align_center" @click="showDownBox(item)">
					<div class="left_box flex_row">
						<div class="icon_box" v-if="item.orderType == 'BUY'">买入</div>
						<div class="icon_box sell" v-else>卖出</div>
						<span>{{item.lots}}手</span>
					</div>
					<div class="mid_box flex_col flex_one">
						<span>{{item.symbol}}</span>
						<div>
							<span>{{item.openPrice}}</span>——<span>{{item.closePrice}}</span>
						</div>
					</div>
					<span>{{item.profitAmount}}</span>
				</div>
				<div class="item_down_box flex_bet" v-show="item.showdown">
					<div>
						<div class="flex_bet">
							<span>ID：</span>
							<span>{{item.orderNo}}</span>
						</div>
						<div class="flex_bet">
							<span>手续费：</span>
							<span>{{item.changeAmount | notNull}}</span>
						</div>
						<div class="flex_bet">
							<span>税金：</span>
							<span>0</span>
						</div>
					</div>
					<div class="">
						<div class="flex_bet">
							<span>库存费：</span>
							<span>{{item.stockAmount | notNull}}</span>
						</div>
						<div class="flex_bet">
							<span>开仓时间：</span>
							<span>{{item.openTime}}</span>
						</div>
						<div class="flex_bet">
							<span>平仓时间：</span>
							<span>{{item.closeTime}}</span>
						</div>
					</div>
				</div>
                
                </template>
                
                <template v-else>
                    <div class="item_box flex_bet flex_align_center" @click="showDownBox(item)">
                    	<div class="left_box flex_row">
                    		<div class="icon_box" v-if="item.orderType == 'IN'">入金</div>
                    		<div class="icon_box sell" v-else>出金</div>
                    		
                    	</div>
                    	<div class="mid_box flex_col flex_one">
                    		<span>{{item.comment}}</span>
                    		<!-- <div>
                    			<span>{{item.comment}}</span>
                    		</div> -->
                    	</div>
                    	<span>{{item.profitAmount}}</span>
                    </div>
                    <div class="item_down_box flex_bet" v-show="item.showdown">
                    	<div>
                    		<div class="flex_bet">
                    			<span>ID：</span>
                    			<span>{{item.orderNo}}</span>
                    		</div>
                    		<div class="flex_bet">
                    			<span>手续费：</span>
                    			<span>{{item.changeAmount | notNull}}</span>
                    		</div>
                    	</div>
                    	<div class="">
                    		
                    		<div class="flex_bet">
                    			<span>申请时间：</span>
                    			<span>{{item.openTime}}</span>
                    		</div>
                    		<div class="flex_bet">
                    			<span>成功时间：</span>
                    			<span>{{item.closeTime}}</span>
                    		</div>
                    	</div>
                    </div>
                </template>
		
	</div>
</template>

<script>
	export default {
        props: {
        	item: {
        		type: Object
        	},
        },
		data() {
			return {
				
			}
		},
		computed: {
			
		},
		filters: {
			notNull: function(value) {
				if (value == null) {
					return 0
				}else {
					return value
				}
			}
		},
		watch: {},
		components: {
		},
		methods: {
			showDownBox (item) {
				this.$forceUpdate();
				item.showdown = !item.showdown;
// 				let list = this.list;
// 				if (list) {
// 					list.forEach((items) => {
// 						if (item != items) {
// 							items.showdown = false
// 						}
// 					})
// 				}
			}
		},
		activated() {
		}
	}
</script>

<style scoped>
	
	.item_box {
		margin-left: 0.5rem;
		height: 1.0666rem;
		border-bottom: 1px solid #e6e6e6;
	}
	
	/*.item_box:last-of-type {
		border: none;
	}*/
	
	.item_box span {
		font-size: 0.3rem;
		color: #666;
	}
	
	.item_box>span {
		font-size: 0.3466rem;
		color: #333;
		font-weight: bold;
		margin-right: 0.6666rem;
	}
	
	.icon_box {
		padding: 0 .1rem;
		line-height: 0.5rem;
		color: #fff;
		font-size: 0.3rem;
		border-radius: 0.0666rem;
		background-color: #ff5752;
		margin-right: 0.08rem;
	}
	
	.icon_box.sell {
		background-color: #4b84d5;
	}
	.left_box {
		width: 25%;
	}
	
	.mid_box {
		padding-left: 0.5333rem;
	}
	
	.mid_box>span {
		font-size: 0.3466rem;
		color: #333;
	}
	.item_down_box {
		background-color: #ededed;
		padding: 0.2rem 0.5rem;
	}
	.item_down_box span {
		color: #666;
		font-size: 0.3rem;
		line-height: 0.5rem;
	}
</style>