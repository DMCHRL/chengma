<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		
        <div class="hui_content" id="hui-content">
            
        
		<div class="list_body">

			<template v-for="(item,index) in list">
				<trade-order-item :key="index" :item="item"></trade-order-item>
                
			</template>
			
			<!-- <spinner-loading v-show="isloading" >
					<p slot='text'>努力加载中...</p>
			</spinner-loading > -->
			
			<div v-show="isloading"  class="trip_box">
				<spinner :type="'ios'" size='30px'></spinner>
			</div>
			
			<div class="trip_box" v-show="notrip">
				<img src="../../assets/img/no_1.png"/>
				<p>暂无数据</p>
			</div>
		</div>
        
        </div>

	</div>
</template>

<script>
    import TradeOrderItem from "./TradeOrderItem"
	import SpinnerLoading from "../common/SpinnerLoading"
	import {XHeader,} from 'vux'
	export default {
		data() {
			return {
				headOption: {title: '交易记录',backText: '',showBack: true},
				list: [],
				strategy: {},
				notrip: false,
				isloading: false,
			}
		},
		computed: {
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
			XHeader,
			SpinnerLoading,
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
			},
			getStrategyMess () {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_data/findByAccount/"+_this.account).then((res) => {
					//console.log(res)
					_this.strategy = res.data;
					if (res.data.strategyName) {
						_this.headOption.title = '交易记录-'+res.data.strategyName;
					}else {
						_this.headOption.title = '交易记录-'+_this.account;
					}
					
				})
			},
			initPage () {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				_this.$fetch("/api/hpp_strategy_trade/findTradeByAccount/"+_this.account).then((res) => {
					// console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = [];
					if (res.data.list.length) {
						let list = res.data.list;
						if (list.length) {
							list.forEach((item) => {
								item.showdown = false
							})
						}
						_this.list = list;
						_this.notrip = false;
					}else {
						_this.notrip = true;
					}
					
				})
			}
		},
		activated() {
			this.getStrategyMess();
			this.initPage();
		}
	}
</script>

<style scoped>
	
	.list_body {
		margin: 0.2666rem;
		background-color: #fff;
		box-shadow: 0px 3px 4px #ededed;
		border-radius: 0.15rem;
		overflow: hidden;
		padding-bottom: 0.5333rem;
	}
	
	
	.list_body li {
		border-bottom: 1px solid #e6e6e6;
	}
	
	._time_box {
		line-height: 1.0666rem;
		border-bottom: 1px solid #e6e6e6;
	}
	
	._time_box span {
		font-size: 0.3466rem;
		color: #666;
		padding: 0 0.2rem;
	}
	
</style>