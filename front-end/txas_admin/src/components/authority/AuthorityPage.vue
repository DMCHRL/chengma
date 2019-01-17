<template>
	<div class="content_box">
		<div class="content">
			<div class="titling">
				<span>全部</span>
				<button>添加角色</button>
			</div>
			<div class="input_section clearfix">
				<div class="pull-left">
					<el-input placeholder="请输入账户搜索" v-model="searchval">
						<el-button slot="append" icon="el-icon-search"></el-button>
					</el-input>
				</div>
				<el-select v-model="value1" placeholder="名称" class="pull-left btn-group">
					<el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
				<el-select v-model="value2" placeholder="身份" class="pull-left btn-group">
					<el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</div>
			<div class="list_section">
				<div class="headlist clearfix">
					<span class="pull-left">交易者账号</span>
					<span class="pull-left">名称</span>
					<span class="pull-left">身份</span>
					<span class="pull-right">操作</span>
				</div>
				<ul class="bodylist">
					<li class="clearfix" v-for="item in [1,1,1,1,11,1,1,1]">
						<span class="pull-left">15978459632</span>
						<span class="pull-left">王某某</span>
						<span class="pull-left">一级代理</span>
						<div class="pull-right">
							<button>修改身份</button>
							<button>删除</button>
						</div>
					</li>
				</ul>
			</div>
			<!--分页-->
			<el-pagination background layout="prev, pager, next" :total="1000">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				value1: '',
				options1: [{
						value: 'all',
						label: '所有时间'
					},
					{
						value: 'today',
						label: '今天'
					}
				],
				value2: '',
				options2: [{
						value: 'yi',
						label: '已实名'
					},
					{
						value: 'wei',
						label: '未认证'
					}
				],
				searchval: '',
				selectdate: '',
				allchecked: true
			}
		},
		components: {
		},
		methods: {

		},
		mounted() {

		}
	}
</script>

<style scoped>
	.headlist span {
		width: 20%;
	}
	
	.headlist span:last-of-type {
		width: 30%;
	}
	.bodylist li span {
		width: 20%;
	}
	
	.bodylist li div {
		width: 30%;
	}
	
	
</style>