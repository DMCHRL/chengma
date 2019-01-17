<template>
	<div class="content_box">
		<div class="content">
			<div class="row">
				<div class="col-md-4">
					<div class="titling clearfix">
						<span>身份列表</span>
					</div>
					<div class="list_section">
						<div class="headlist clearfix">
							<span class="pull-left">ID</span>
							<span class="pull-left">身份名称</span>
							<span class="pull-right">操作</span>
						</div>
						<ul class="bodylist">
							<li class="clearfix" v-for="item in [1,2,3,4,5,6,7,8]">
								<span class="pull-left">001</span>
								<span class="pull-left">超级管理员</span>
								<div class="pull-right">
									<span>修改</span>
									<span>删除</span>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-md-8">
					<div class="titling clearfix">
						<span>权限设置</span>
					</div>
					<div class="box_top">
						<span>身份名称：</span>
						<input type="text"></input>
						<button>确认添加</button>
					</div>
					<div class="box_bottom">
						<ul class="clearfix">
							<li class="pull-left" v-for="item in [1,1,1,1,1,1,1,1]">
								<div class="_top">
									<el-checkbox>数据</el-checkbox>
								</div>
								<div class="_bottom">
									<el-tree :data="data2" show-checkbox default-expand-all node-key="id" ref="tree" highlight-current :props="defaultProps">
									</el-tree>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				data2: [{
					id: 1,
					label: '新增账户',
					children: [{
						id: 4,
						label: '二级 1-1'
					},
					{
						id: 7,
						label: '二级1-2'
					}]
				}, {
					id: 2,
					label: '交易量',
					children: [{
						id: 5,
						label: '二级 2-1'
					}, {
						id: 6,
						label: '二级 2-2'
					}]
				}],
				defaultProps: {
					children: 'children',
					label: 'label'
				}
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
		width: 30%;
	}
	
	.headlist span:last-of-type {
		width: 40%;
	}
	.bodylist li span {
		width: 30%;
	}
	
	.bodylist li div {
		width: 40%;
	}
	
	.bodylist li div span {
		cursor: pointer;
	}
	
	.bodylist li div span:after {
		content: "   |";
	}
	
	.bodylist li div span:last-of-type:after {
		content: none;
	}
	
	.bodylist li div span:last-of-type {
		color: #f14b3b;
	}
	
	.box_top,
	.box_bottom {
		padding-bottom: 10px;
		padding-top: 10px;
	}
	
	.box_top input {
		border: 1px solid #ccc;
		border-radius: 20px;
		padding: 5px 10px;
		margin: 0 20px;
	}
	
	.box_top button {
		background-color: #f14b3b;
		color: #fff;
		padding: 5px 20px;
		border-radius: 20px;
	}
	.box_bottom li {
		width: 24%;
		border: 1px solid #6562b6;
		margin: 20px 0.5%;
	}
	.box_bottom li ._top{
		background-color: #6562b6;
		padding: 10px;
	}
	.box_bottom li ._bottom {
		background-color: #e9ecff;
		height: 200px;
	}
</style>