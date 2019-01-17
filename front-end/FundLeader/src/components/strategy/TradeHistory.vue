<template>
	<div class="con_box">
		<div class="title_box flex_bet">
			<div class="">
				<span>交易记录</span>
			</div>
			<!-- <router-link :to="'/history?account='+ account">
				<span>更多>></span>
			</router-link> -->
		</div>
		<div>

			<template v-for="(item,index) in list">
				
                <trade-order-item :key="index" :item="item"></trade-order-item>
				
			</template>
            
			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/no_1.png"/>
				<p>暂无数据</p>
			</div>
            
            <router-link :to="'/history?account='+ account">
                <p class="trip_p">查看更多>></p>
            </router-link>
            
		</div>
	</div>
</template>

<script>
    import TradeOrderItem from "./TradeOrderItem"
	export default {
		props: ['hisData'],
		data() {
			return {
			}
		},
		computed: {
			list () {
				let _this = this;
				let list = _this.hisData.list;
				if (list) {
					list.forEach((item) => {
						item.showdown = false
					})
					return list;
				}else {
					return []
				}
			},
			account () {
				return this.$route.query.account;
			},
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
            TradeOrderItem
        },
		methods: {
			showDownBox (item) {
				this.$forceUpdate();
				item.showdown = !item.showdown;
				let list = this.list;
				if (list) {
					list.forEach((items) => {
						if (item != items) {
							items.showdown = false
						}
					})
				}
			}
		},
		mounted() {
			
		}
	}
</script>

<style scoped>
	.title_box {
		height: 45px;
		padding: 0 0.2rem;
		border-bottom: 1px solid #e6e6e6;
	}
	.title_box span {
		line-height: 45px;
		color: #999;
		font-size: 0.3rem;
	}
	.title_box div:first-of-type span{
		border-left: 0.08rem solid #e40b2f;
		padding-left: 0.2666rem;
		font-size: 0.3466rem;
		color: #333;
		
	}
	
	.con_box {
		margin: 0.2666rem;
		background-color: #fff;
		box-shadow: 0px 3px 4px #ededed;
		border-radius: 0.15rem;
		overflow: hidden;
	}
	
</style>