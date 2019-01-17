<template>
	<div class="list_section">
		<div class="headlist clearfix">
			<span class="pull-left">交易账户</span>
			<span class="pull-left">订单号</span>
			<span class="pull-left">交易品种</span>
			<span class="pull-left time pointer" @click="Sort('openTime')">建仓时间<i class="el-icon-d-caret"></i></span>
			<span class="pull-left time pointer" @click="Sort('closeTime')">平仓时间<i class="el-icon-d-caret"></i></span>
			<span class="pull-left">建仓价格</span>
			<span class="pull-left">平仓价格</span>
			<span class="pull-left pointer" @click="Sort('lots')">手数<i class="el-icon-d-caret"></i></span>
			<span class="pull-left" v-if="user.department == 'admin'">佣金(公司~代理)</span>
			<span class="pull-left">方向</span>
			<span class="pull-right pointer" @click="Sort('gainAmount')">结算<i class="el-icon-d-caret"></i></span>
		</div>
		<ul class="bodylist">
			<li class="clearfix" v-for="item in list.list">
				<span class="pull-left">{{item.account}}</span>
				<span class="pull-left">{{item.orderNo}}</span>
				<span class="pull-left">{{item.symbol}}</span>
				<span class="pull-left time">{{item.openTime}}</span>
				<span class="pull-left time">{{item.closeTime}}</span>
				<span class="pull-left">{{item.openPrice}}</span>
				<span class="pull-left">{{item.closePrice}}</span>
				<span class="pull-left">{{item.lots}}</span>
				<span class="pull-left" v-if="user.department == 'admin'">{{item.companyAmount}}~{{item.proxyAmount}}</span>
				<span class="pull-left" :class="item.orderType == 'BUY'? 'up':'down'" >{{item.orderType}}</span>
				<span class="pull-right" :class="item.orderType == 'BUY'? 'up':'down'" >{{item.gainAmount}}</span>
			</li>
			
			<li class="clearfix zongji">
				<span class="pull-left">总计</span>
				<span class="pull-left">--</span>
				<span class="pull-left">--</span>
				<span class="pull-left time">--</span>
				<span class="pull-left time">--</span>
				<span class="pull-left">--</span>
				<span class="pull-left">--</span>
				<span class="pull-left">{{list.sumLots}}</span>
				<span class="pull-left" v-if="user.department == 'admin'">--</span>
				<span class="pull-left" >--</span>
				<span class="pull-right" >{{list.sumAmount}}</span>
			</li>
		</ul>
		<div class="trip_box" v-show="!list.list.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无数据</p>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["list"],
		data() {
			return {
				isdesc: true
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
			totalLots () {
				return this.getTotal ('lots');
			},
			totalGainAmount () {
				return this.getTotal ('gainAmount');
			}
		},
		components: {
			
		},
		methods: {
			getTotal (keys) {
				let list = this.list;
				if (list.length) {
					let sum = 0;
					list.forEach(function (item,index,input) {
					    sum += Number(item[keys]);
					})
					return sum.toFixed(2);
				}else {
					return '--'
				}
			},
			Sort (para) {//排序
				this.isdesc = !this.isdesc;
				let datas = {
					"orderByColumn": para,
					"sort": this.isdesc ? 'DESC':'ASC' //"DESC"//"ASC"
				};
				this.$emit("Sort",datas)
			},
		},
		mounted () {
//			console.log(this.list)
		}
	}
</script>

<style scoped>
	
	.headlist span {
		width: 8%;
	}
	.headlist span.time {
		width: 14%;
	}
	.bodylist li span {
		width: 8%;
	}
	.bodylist li span.time {
		width: 14%;
	}
	
	.bodylist li span.up {
		color: #f14b3b;
	}
	
	.bodylist li span.down {
		color: #17cb8a;
	}
</style>