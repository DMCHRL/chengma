<template>
	<div class="content_box">
		<div class="titling">
			<span>结算请单</span>
		</div>
		<div class="input_section clearfix">
			<!--<div class="pull-left">
				<el-input placeholder="请输入关键字" v-model="searchval">
					<el-button slot="append" icon="el-icon-search"></el-button>
				</el-input>
			</div>-->
			<el-select v-model="value1" placeholder="所有时间" class="pull-left btn-group">
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
				
				<span class="pull-left">流水号</span>
				<span class="pull-left">申请时间</span>
				<span class="pull-left">结算时间</span>
				<span class="pull-left">金额</span>
				<span class="pull-left">结算方式</span>
				<span class="pull-right">状态</span>
			</div>
			<ul class="bodylist">
				<!--<li class="clearfix" v-for="item in [1,2,3,4,5,6,7,8]">
					
					<span class="pull-left">15978459632</span>
					<span class="pull-left">2018-06-24  18:22:15</span>
					<span class="pull-left">2018-06-24  18:22:15</span>
					<span class="pull-left">2500</span>
					<span class="pull-left">交易账户</span>
					<span class="pull-right">完成</span>
				</li>-->
			</ul>
		</div>
		<!--分页-->
		<el-pagination background layout="prev, pager, next" :total="1000">
		</el-pagination>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				value1: 'time1',
				options1: [{
						value: 'time1',
						label: '时间'
					},
					{
						value: 'today',
						label: '今天'
					}
				],
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
//				searchval: '',
				selectdate: '',
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
		width: 14%;
	}
	
	.headlist span:nth-child(2),
	.headlist span:nth-child(3) {
		width: 21%;
	}
	.bodylist li span {
		width: 14%;
	}
	
	.bodylist li span:nth-child(2),
	.bodylist li span:nth-child(3) {
		width: 21%;
	}
	
</style>