<template>
	<div class="content_box">
		<div class="titling">
			<span>IB申请审核</span>
			<button>添加IB申请</button>
		</div>
		<div class="input_section clearfix">
			<div class="pull-left">
				<el-input placeholder="请输入关键字" v-model="searchval">
					<el-button slot="append" icon="el-icon-search"></el-button>
				</el-input>
			</div>
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
				<span class="pull-left"><el-checkbox v-model="allchecked"></el-checkbox></span>
				<span class="pull-left">ID</span>
				<span class="pull-left">名称</span>
				<span class="pull-left">交易者账号</span>
				<span class="pull-left">实名状态</span>
				<span class="pull-left">申请时间</span>
				<span class="pull-left">分组</span>
				<span class="pull-left">佣金</span>
				<span class="pull-right">操作</span>
			</div>
			<ul class="bodylist">
				<li class="clearfix" v-for="item in [1,2,3,4,5,6,7,8]">
					<span class="pull-left"><el-checkbox v-model="allchecked"></el-checkbox></span>
					<span class="pull-left">12356</span>
					<span class="pull-left">王某某</span>
					<span class="pull-left">15978459632</span>
					<span class="pull-left">已实名</span>
					<span class="pull-left">2018-06-24  18:22:15</span>
					<span class="pull-left">
											<select name="默认分组3">
												<option>默认分组1</option>
												<option>默认分组2</option>
												<option>默认分组3</option>
												<option>默认分组4</option>
											</select>
										</span>
					<span class="pull-left">2500</span>
					<span class="pull-right">详情</span>
				</li>
			</ul>
			<div class="list_btn">
				<button>批量分组</button>
			</div>
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
				value1: 'alltime',
				options1: [{
						value: 'alltime',
						label: '所有时间'
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
				searchval: '',
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
	.content {
		margin-top: 20px;
		padding: 20px;
		background-color: #fff;
	}
	
	.titling {
		border-bottom: 2px solid #2c2b5c;
		padding-bottom: 10px;
	}
	
	.titling span {
		border-left: 4px solid #2c2b5c;
		font-size: 18px;
		color: #2c2b5c;
		padding-left: 10px;
	}
	
	.titling button,
	.list_btn button {
		font-size: 14px;
		color: #fff;
		background-color: #f14b3b;
		border-radius: 20px;
		padding: 5px 20px;
		margin-left: 20px;
	}
	
	.input_section {
		padding: 10px 0;
	}
	
	.input_section .search_input {
		border: 1px solid #ccc;
	}
	
	.input_section .form-control {
		border: 0;
		border-radius: 0;
		display: inline-block;
	}
	
	.input_section .search_input input {
		width: 80%;
		float: left;
	}
	
	.input_section .search_input span {
		float: right;
		color: #ccc;
		padding: 6px;
		height: 34px;
		background-color: transparent;
	}
	
	.input_section .btn-group .btn {
		height: 36px;
		color: #ccc;
		border-radius: 0;
		margin-left: 10px;
	}
	
	.input_section .caret {
		border-top: 4px solid #ccc;
	}
	
	.input_section .pull-left {
		margin-right: 10px;
	}
	
	.list_section {
		position: relative;
	}
	
	.headlist {
		background-color: #6562b6;
		padding: 12px 0;
	}
	
	.headlist span {
		font-size: 14px;
		width: 10%;
		text-align: center;
	}
	
	.headlist span:nth-child(6) {
		width: 15%;
	}
	
	.headlist span:nth-child(1) {
		width: 5%;
	}
	
	.headlist span:last-of-type {
		width: 17%;
	}
	
	.bodylist li {
		padding: 10px 0;
		border-bottom: 1px dashed #d0d0d0;
		text-align: center;
	}
	
	.bodylist li:last-of-type {
		border-bottom: 2px solid #6562b6;
	}
	
	.bodylist li span {
		font-size: 14px;
		color: #3e3e3e;
		width: 10%;
		text-align: center;
		line-height: 31px;
	}
	
	.bodylist li span:nth-child(6) {
		width: 15%;
	}
	
	.bodylist li span:nth-child(1) {
		width: 5%;
	}
	
	.bodylist li div {
		width: 17%;
	}
	
	.bodylist li button {
		background-color: #f14b3b;
		font-size: 14px;
		color: #fff;
		background-color: #f14b3b;
		border-radius: 20px;
		padding: 5px 30px;
	}
	
	.bodylist li button:nth-child(2) {
		border: 1px solid #F14B3B;
		background-color: #fff;
		color: #F14B3B;
		padding: 4px 30px;
	}
	
	.list_btn {
		position: absolute;
		top: 102%;
		left: 0px;
	}
</style>