<template>
		<div class="content_box">
			<div class="titling">
				<span>分组设置</span>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="list_section">
						<div class="headlist clearfix">
							<span class="pull-left">分组名称</span>
							<span class="pull-left">佣金规则</span>
							<span class="pull-right">操作</span>
						</div>
						<ul class="bodylist">
							<li class="clearfix">
								<span class="pull-left">高级销售</span>
								<span class="pull-left">固定比例10%</span>
								<div class="pull-right">
									<button @click="dialogVisible = true">修改</button>
									<button @click="delGrouds">删除</button>
								</div>
							</li>
							<li class="clearfix">
								<span class="pull-left">中级销售</span>
								<span class="pull-left">固定比例10%</span>
								<div class="pull-right">
									<button @click="dialogVisible = true">修改</button>
									<button @click="delGrouds">删除</button>
								</div>
							</li>
							<li class="clearfix">
								<span class="pull-left">初级销售</span>
								<span class="pull-left">固定比例1%</span>
								<div class="pull-right">
									<button @click="dialogVisible = true">修改</button>
									<button @click="delGrouds">删除</button>
								</div>
							</li>
							<li class="clearfix">
								<span class="pull-left">区域代理</span>
								<span class="pull-left">固定比例1%</span>
								<div class="pull-right">
									<button @click="dialogVisible = true">修改</button>
									<button @click="delGrouds">删除</button>
								</div>
							</li>
						</ul>
					</div>
					<!--分页-->
					<el-pagination background layout="prev, pager, next" :total="1000">
					</el-pagination>
				</div>
				<div class="col-md-4">
					<div class="new_ground">
						<h4>添加分组</h4>
						<div class="input_box clearfix">
							<span class="pull-left">上级IB</span>
							<select class="pull-left" name="">
								<option value="">分公司</option>
							</select>
						</div>
						<div class="input_box clearfix">
							<span class="pull-left">分组名称</span>
							<input class="pull-left" type="text" name="" id="" value="" />
						</div>
						<div class="input_box clearfix">
							<span class="pull-left">结算方法</span>
							<select class="pull-left" name="">
								<option value="">固定比例</option>
							</select>
						</div>
						<div class="input_box clearfix">
							<span class="pull-left">佣金规则</span>
							<div class="pull-left input_item">
								<input type="number" name="" id="" value="" />
								<span class="fuhao">%</span>
							</div>
						</div>
						<div class="new_btn">
							<button>保存</button>
							<button>取消</button>
						</div>
					</div>
				</div>
			</div>
			<!--修改弹窗-->
			<el-dialog custom-class="ibs_dialog" :show-close="false" :visible.sync="dialogVisible" width="20%">
				
				<h3 slot="title" class="dialog_title">高级销售</h3>
				<div class="input_box">
					<span>分组名称</span>
					<input type="text" name="" id="" value="" placeholder="" />
				</div>
				<div class="input_box">
					<span>佣金规则</span>
					<input type="number" name="" id="" value="" placeholder="单位%" />
				</div>
				<div slot="footer" class="dialog_footer">
					<button @click="dialogVisible = false">确认</button>
					<button @click="dialogVisible = false">取消</button>
				</div>
			</el-dialog>
		</div>
</template>

<script>
	export default {
		data() {
			return {
				dialogVisible: false
			}
		},
		components: {
		},
		methods: {
			delGrouds () {
				this.$confirm('此操作将永久删除该分组, 是否继续?', '删除分组', {
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
//		          type: 'warning'
		        }).then(() => {
		          this.$message({
		            type: 'success',
		            message: '删除成功!'
		          });
		        }).catch(() => {
//		          this.$message({
//		            type: 'info',
//		            message: '已取消删除'
//		          });          
		        });
			}
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
	
	.new_ground {
		text-align: center;
		background-color: #6562b6;
		border-radius: 20px;
		margin-top: 10px;
	}
	
	.new_ground h4 {
		line-height: 80px;
		color: #fff;
		font-size: 24px;
		border-bottom: 2px solid #fff;
		margin: 0 30px;
		margin-bottom: 70px;
	}
	
	.new_ground .input_box {
		text-align: center;
		margin-bottom: 10px;
		position: relative;
	}
	
	.new_ground .input_box span {
		width: 35%;
		color: #fff;
		font-size: 14px;
		text-align: right;
		line-height: 30px;
		box-sizing: border-box;
		padding-right: 10px;
	}
	
	.new_ground .input_box input,
	.new_ground .input_box select,
	.new_ground .input_box .input_item {
		width: 40%;
		background-color: #fff;
		height: 30px;
		border-radius: 5px;
		box-sizing: border-box;
		padding: 0 5px;
		position: relative;
	}
	
	.new_ground .new_btn {
		margin-top: 140px;
		padding-bottom: 40px;
	}
	
	.new_ground .new_btn button {
		font-size: 18px;
		color: #fff;
		padding: 5px 40px;
		border-radius: 20px;
		background-color: #aba8ff;
	}
	
	.new_ground .new_btn button:last-of-type {
		color: #ABA8FF;
		background-color: #fff;
		margin-left: 20px;
	}
	
	.input_item {
		text-align: left;
	}
	
	.new_ground .input_box .input_item input {
		width: 100%;
	}
	
	.input_item span.fuhao {
		display: block;
		position: absolute;
		top: 0;
		bottom: 0;
		right: 5px;
		width: 30px;
		background-color: #fff;
		color: #666;
	}
	
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	.ibs_dialog .input_box {
		margin-bottom: 10px;
	}
	
	.ibs_dialog .input_box span {
		display: inline-block;
		width: 30%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.ibs_dialog .input_box input,
	.ibs_dialog .input_box select {
		border: 1px solid #ccc;
		width: 50%;
		padding: 0 10px;
		border-radius: 5px;
	}
	
	.ibs_dialog .input_box p {
		cursor: pointer;
		text-align: center;
		font-size: 14px;
		color: #b4b4b4;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 18px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
</style>