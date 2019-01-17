<template>
	<div class="content_box">
		<div class="titling clearfix">
			<span>开户审核</span>
			<span class="dao pull-right" @click="outExe">导出excel</span>
		</div>

		<filter-work  @Inquire="Inquire" :parent="'task'"></filter-work>

		<div class="list_section" v-loading="loading" element-loading-text="拼命加载中">
			<div class="headlist clearfix myback2">
				
				<template v-for="(item,index) in headlist">
					<span :key="index"
						:class="['pull-left', item.widen? 'time': '', item.sort?'pointer':'']" 
						@click="Sort(item.sort)">{{item.name}}
						<i v-if="item.sort" class="el-icon-d-caret"></i>
					</span>
				</template>
				
				<span class="pull-right">操作</span>
			</div>
			<ul class="bodylist">
				<li class="clearfix" v-for="(item,index) in list">
					<span class="pull-left">{{item.username}}</span>
					<span class="pull-left">{{item.phone}}</span>
					<span class="pull-left time">{{item.createAt}}</span>
					<span class="pull-left">开户审核</span>
					<span class="pull-left time">{{item.approvedAt}}</span>
					<span class="pull-left">{{item.approvedname}}</span>

					<div class="pull-right">
						
						<el-button type="primary" icon="el-icon-view" circle
						 @click="Review(item.id)"></el-button>
						
						<span v-if="item.status == 'PASSED'">
							已通过
						</span>
						<el-button type="danger" icon="el-icon-delete" circle 
						v-if="user.department == 'admin' && item.status == 'APPLYING'" @click="Delete(item.id,index)"></el-button>
						
					</div>
				</li>
			</ul>
			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>

		<!--审核弹窗-->
		<review-message :dialogFormVisible="dialogFormVisible" :datas="dataReview" @close="dialogClose"></review-message>

		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>

	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import ReviewMessage from "../dialog/ReviewMessage.vue"
	export default {
		data() {
			return {
				headlist: [
					{name: '姓名',sort: null,widen: false},
					{name: '手机号',sort: null,widen: false},
					{name: '申请时间',sort: 'createAt',widen: true},
					{name: '申请事项',sort: null,widen: false},
					{name: '处理时间',sort: 'approvedAt',widen: true},
					{name: '处理人',sort: null,widen: false},
				],
				list: [], //任务列表
				dataReview: '', //审核数据
				dialogFormVisible: false,
				loading: false,
				page: {
					total: 10,
					num: 1,
					size: 10
				},
				filter: {
					date: ['', ''],
					orderByColumn: 'createAt',
					sort: 'DESC'
				}
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
			}
		},
		components: {
			FilterWork,
			ReviewMessage,
			Pagination
		},
		methods: {
			outExe() {
				let _this = this;
				_this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						let datas = {
							page_number: 1, //	是	int	当前页
							page_size: 1000,//	是	int	每页大小
							formParams: {}
						};
						
						_this.$postForExcel('/api/export/hppUserApply',datas,'开户审核记录').then();
							
				}).catch(() => {
		
				});
			},
			dialogClose() {
				this.dialogFormVisible = false;
				this.initPage();
			},
			Inquire(filter) {
				if(!filter.date) {
					filter.date = ['', '']
				}

				this.filter = filter;
				this.initPage();
			},
			Sort(para) { //排序
				if (!para) return;
				this.filter.orderByColumn = para;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
				this.initPage();
			},
			currentChange(p) {
				this.page.num = p;
				this.initPage();
			},
			Review(id) { //查看资料
				let _this = this;
				_this.$fetch('/api/hpp_user/getHppUser/' + id).then((res) => {
					if(res.statusCode == '0000') {
						_this.dataReview = res.data;
						_this.dialogFormVisible = true;
					} else {
						_this.$message({
							type: 'warning',
							message: res.msgCode
						});
					}

				})
			},
			Delete(id, index) {
				let _this = this;
				_this.$confirm('此操作将永久删除该条资料, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$strike('/api/hpp_user/deleteHppUser/' + id).then((res) => {
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.initPage();
						} else {
							_this.$message({
								type: 'warning',
								message: res.msgCode
							});
						}
					})
				}).catch(() => {
					//取消
				});
			},

			initPage() {
				let _this = this;
				//_this.loading = true;
				let datas = {
					"page_number": _this.page.num, //页码
					"page_size": _this.page.size, // 每页条数
					"formParams": {
						"username": _this.filter.name,
						"phone": _this.filter.phone,
						"status": _this.filter.status,
						"type": _this.filter.type,
						"bCreateAt": _this.filter.date[0], //开始申请时间 2018-07-25
						"eCreateAt": _this.filter.date[1],
						"bApprovedAt": "",
						"eApprovedAt": "",
						"approvedname": "",
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
					}
				};
				_this.$post("/api/hpp_user/pageList", datas).then((res) => {
					_this.loading = false;
					if(res.statusCode == "0000") {
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
	.headlist span,
	.bodylist li span {
		width: 160px;
	}
	
	.headlist span.time,
	.bodylist li span.time {
		width: 240px;
	}
	
	.headlist span:last-of-type,
	.bodylist li div {
		width: 240px;
	}
	.bodylist li {
		line-height: 60px;
	}
	
</style>