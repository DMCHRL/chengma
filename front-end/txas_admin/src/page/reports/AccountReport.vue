<template>
	<div class="content_box">
		<div class="titling">
			<span>账户报表</span>
			<span class="pull-right">导出Excel</span>
		</div>
		<div class="input_section clearfix">
			<div class="pull-left">
				<el-input placeholder="搜索姓名" v-model="searchval">
					<el-button slot="append" icon="el-icon-search"></el-button>
				</el-input>
			</div>
			<el-select v-model="value1" placeholder="时间" class="pull-left btn-group">
				<el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<div class="block">
				<el-date-picker v-model="selectdate" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
				</el-date-picker>
			</div>
		</div>
		<div class="list_section">
			<div class="headlist clearfix">
				<span class="pull-left">账户</span>
				<span class="pull-left">姓名</span>
				<span class="pull-left time">归属</span>
				<span class="pull-left">入金</span>
				<span class="pull-left">出金</span>
				<span class="pull-left">手续费</span>
				<span class="pull-left">利息</span>
				<span class="pull-left">盈亏</span>
				<span class="pull-left">订单合计</span>
				<span class="pull-left">开仓手数</span>
				<span class="pull-left">平仓手数</span>
				<span class="pull-left time">创建时间</span>
				<span class="pull-left time">首次入金时间</span>
				<span class="pull-left ">初期资金</span>
				<span class="pull-right">末期资金</span>
			</div>
			<ul class="bodylist">
				<!--<li class="clearfix">
					<span class="pull-left">1890677</span>
					<span class="pull-left">李某某</span>
					<span class="pull-left time">套利宝佣金结算</span>
					<span class="pull-left">0.00</span>
					<span class="pull-left">0.00</span>
					<span class="pull-left">-1900.00</span>
					<span class="pull-left">1000.00</span>
					<span class="pull-left">100.00</span>
					<span class="pull-left">15</span>
					<span class="pull-left">10.1</span>
					<span class="pull-left">9.1</span>
					<span class="pull-left time">2018-06-06 05:12:12</span>
					<span class="pull-left time">2018-06-06 05:12:12</span>
					<span class="pull-left">0.00</span>
					<span class="pull-right">0.00</span>
				</li>-->
			</ul>
		</div>
		<!--分页-->
		<el-pagination background layout="prev, pager, next" :total="5">
		</el-pagination>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				searchval: '', //搜索
				value1: '1',
				options1: [{
						value: '1',
						label: '注册时间'
					},
					{
						value: '2',
						label: '登录时间'
					}
				],
				selectdate: '',
				pickerOptions2: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				allchecked: true
			}
		},
		components: {},
		methods: {

		},
		mounted() {

		}
	}
</script>

<style scoped>
	.headlist span {
		width: 5.5%;
	}
	.headlist span.time {
		width: 11%;
	}
	.bodylist li span {
		width: 5.5%;
	}
	.bodylist li span.time {
		width: 11%;
	}
</style>