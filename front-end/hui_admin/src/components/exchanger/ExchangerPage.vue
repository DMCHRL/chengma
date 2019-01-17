<template>
	<div class="content_box">
		
		<save-exchanger :show="saveShow" :exid="exId" @close="closeInit"></save-exchanger>
		
		<div class="titling">
			<span>汇商管理</span>
			<button @click="AddEx">新增汇商</button>
		</div>
		
		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->
		
		
		<div class="list_body">
			<div class="clearfix table_head myback2">
				<div class="pull-left">
					<span>名称</span>
				</div>
				<div class="pull-left">
					<span>英文名</span>
				</div>
				<div class="pull-left">
					<span>时间</span>
				</div>
				<div class="pull-left">
					<span>状态</span>
				</div>
				<div class="pull-right">
					<span>操作</span>
				</div>
			</div>
			<div v-for="item in list" class="item_box clearfix">
				<div class="pull-left">
					<span>{{item.sinksName}}</span>
				</div>
				
				<div class="pull-left">
					<span>{{item.sinksName2}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.createTime}}</span>
				</div>
				<div class="pull-left">
					<span>已通过</span>
				</div>
				<div class="pull-right">
					<el-button @click="Edit(item.id)" type="primary" icon="el-icon-edit" circle></el-button>
					<el-button @click="Delete(item.sinksName,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
					
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
	import Pagination from "../common/Pagination"
	import FilterWork from "../common/FilterWork"
	import SaveExchanger from '../dialog/SaveExchanger'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				filter: {},
				saveShow: false,
				exId: null
			}
		},
		components: {
			SaveExchanger,
			Pagination,
			FilterWork
		},
		methods: {
			Edit (id) {
				this.exId = id;
				this.saveShow = true;
			},
			AddEx() {
				this.exId = null;
				this.saveShow = true;
			},
			Delete (name,id) {
				let _this = this;
				
				_this.$confirm('删除['+name+']汇商, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_sinks/delete/"+id).then((res) => {
//						console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '操作成功!'
							});
							_this.initPage();
						}else {
							_this.$message({
								type: 'error',
								message: res.msgCode
							});
						}
					})
					
				}).catch(() => {});
				
			},
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire (filter) {
				this.filter = filter;
				this.initPage();
			},
			closeInit () {
				this.saveShow = false;
				this.initPage();
			},
			initPage () {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						userId: '',
						videoTypeName: ''
					}
				}
				this.$post("/api/hpp_sinks/pageList",datas).then((res) => {
//					console.log(res)
					if (res.data) {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.table_head {
		line-height: 40px;
	}
	.item_box {
		line-height: 60px;
		border-bottom: 1px solid #999;
	} 
	.table_head>div,
	.item_box>div{
		width: 200px;
		min-height: 1px;
		text-align: center;
	}
	.table_head>div span,
	.item_box>div span {
		color: #fff;
		font-size: 14px;
	}
	.item_box>div span {
		color: #666;
	}
	.item_box>div p {
		font-size: 14px;
		line-height: 20px;
	}
	
	.el-button--text {
		color: #007AFF;
	}
	
	._content {
		width: 200px;
		/*background-color: #fff;
		color: #666;*/
	}
</style>