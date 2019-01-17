<template>
	<div class="content_box">
		<div class="titling">
			<span>佣金报表</span>
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
				<span class="pull-left">合计手数</span>
				<span class="pull-left ">合计处理数</span>
				<span class="pull-left ">返佣</span>
				<span class="pull-right">操作</span>
			</div>
			<ul class="bodylist">
				<!--<li class="clearfix">
					<span class="pull-left">1890677</span>
					<span class="pull-left">李某某</span>
					<span class="pull-left time">套利宝佣金结算</span>
					<span class="pull-left">15.5</span>
					<span class="pull-left ">12</span>
					<span class="pull-left ">100.00</span>
					<span class="pull-right">已结算</span>
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
	
	.headlist span,
	.bodylist li span {
		width: 14%;
	}
	.headlist span.time,
	.bodylist li span.time {
		width: 15%;
	}
	
</style>