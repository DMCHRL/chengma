<template>
	<div class="content_box">

		<div class="titling">
			<span>订单管理</span>
			<!--<button @click="saveShow = true">新增考证</button>-->
			<span class="dao pull-right" @click="outExe">导出excel</span>
		</div>

		<filter-work @Inquire="Inquire" :parent="'orderList'"></filter-work>

		<div class="list_body">
			<div class="clearfix table_head myback2">
				<div class="pull-left">
					<span>订单号</span>
				</div>
				<div class="pull-left">
					<span>商品类别</span>
				</div>
				<div class="pull-left detail">
					<span>商品名称</span>
				</div>
				<div class="pull-left detail">
					<span>商品详情</span>
				</div>
				<div class="pull-left">
					<span>应付价格(￥)</span>
				</div>
				<div class="pull-left">
					<span>积分抵扣</span>
				</div>
				<div class="pull-left">
					<span>实付(￥)</span>
				</div>
				<div class="pull-left time">
					<span>下单时间</span>
				</div>
				<div class="pull-left">
					<span>联系方式</span>
				</div>
				<div class="pull-left">
					<span>支付方式</span>
				</div>
				<div class="pull-left ">
					<span>支付状态</span>
				</div>
			</div>
			<div v-for="item in list" class="item_box clearfix">
				<div class="pull-left">
					<span>{{item.outTradeNo}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.body | formatBody}}</span>
				</div>
				<div class="pull-left detail">
					<span>{{item.bodyName}}</span>
				</div>
				<div class="pull-left detail">
					<span>{{item.bodyDetail}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.pay}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.integral}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.totalFee}}</span>
				</div>
				<div class="pull-left time">
					<span>{{item.timeStart}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.object}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.payType | formatPayType}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.status|formatStatus}}</span>
				</div>
			</div>

			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>

		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveVideoType from '../dialog/SaveVideoType'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 13
				},
				filter: {
					date: ''
				},
				saveShow: false
			}
		},
		components: {
			SaveVideoType,
			Pagination,
			FilterWork
		},
		computed: {
			today() {
				var myDate = new Date();
				var time = myDate.getFullYear() + '-' + (myDate.getMonth() + 1) + '-' + myDate.getDate();
				return time;
			}
		},
		filters: {
			formatStatus: function(val) {
				return val === "Y" ? "已支付" : "未支付";
			},
			formatPayType: function(val) {
				switch (val) {
					case "integral":
						return "积分兑换";
					case "weChat":
						return "微信支付";
					case "mix":
						return "积分抵扣";
					default:
						return "未知";
				}
			},
			formatBody: function(val) {
				if (val === "video") {
					return "交易战法视频"
				} else if (val === "course") {
					return "交易面对面"
				} else if (val === "exam") {
					return "交易师考证"
				}
			}
		},
		methods: {
			outExe() {
				let _this = this;
				this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let datas = {
						page_number: 1, //	是	int	当前页
						page_size: 1000, //	是	int	每页大小
						formParams: {
							object: _this.filter.phone,
							outTradeNo: _this.filter.outTradeNo,
							status: _this.filter.status,
							body: _this.filter.body,
							payType: _this.filter.payType,
							startTime: _this.filter.date[0],
							endTime: _this.filter.date[1]
						}
					};

					_this.$postForExcel('/api/export/wxOrder', datas, '订单记录').then();

				}).catch(() => {

				});
			},
			currentChange(p) { //分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire(filter) {
				this.filter = filter;
				this.currentChange(1);
			},
			closeInit() {
				this.saveShow = false;
				this.initPage();
			},
			initPage() {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						object: _this.filter.phone,
						outTradeNo: _this.filter.outTradeNo,
						status: _this.filter.status,
						body: _this.filter.body,
						payType: _this.filter.payType,
						startTime: _this.filter.date[0],
						endTime: _this.filter.date[1]
					}
				};
				this.$post("/api/wx_order/pageList", datas).then((res) => {
					if (res.data) {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted() {
			this.initPage();
		}
	}
</script>

<style scoped>
	.table_head {
		line-height: 40px;
	}

	.item_box {
		line-height: 40px;
		border-bottom: 1px solid #999;
	}

	.table_head>div,
	.item_box>div {
		width: 130px;
		text-align: center;
		min-height: 1px;
	}


	.item_box>div.detail,
	.table_head>div.detail {
		width: 200px;
	}

	.table_head>div.time,
	.item_box>div.time {
		width: 160px;
	}

	.table_head>div span,
	.item_box>div span {
		color: #fff;
		font-size: 13px;
	}

	.item_box>div span {
		color: #666;
	}
</style>
