<template>
	<div class="content_box">
		<div class="titling">
			<span>客服任务</span>
		</div>
		
		<filter-work  @Inquire="Inquire" :parent="6"></filter-work>
		
		<div class="list_section" v-loading="loading" element-loading-text="拼命加载中">
			<div class="headlist clearfix">
				<span class="pull-left">姓名</span>
				<span class="pull-left">手机号</span>
				<span class="pull-left">审核事项</span>
				<span class="pull-left time pointer" @click="Sort('createAt')">申请时间<i class="el-icon-d-caret"></i></span>
				<span class="pull-left">处理人</span>
				<span class="pull-left time pointer" @click="Sort('approvedAt')">处理时间<i class="el-icon-d-caret"></i></span>
				<span class="pull-right">操作</span>
			</div>
			<ul class="bodylist">
				<li class="clearfix" v-for="(item,index) in list">
					<span class="pull-left">{{item.username}}</span>
					<span class="pull-left">{{item.phone}}</span>
					<span class="pull-left" v-text="item.type == 'APPLYACCOUNT'?'开户申请':'其他'"></span>
					<span class="pull-left time">{{item.createAt}}</span>
					<span class="pull-left">{{item.approvedname}}</span>
					<span class="pull-left time">{{item.approvedAt}}</span>
					
					<div class="pull-right">
						<span v-if="item.status == 'APPLYING'">
							<button 
								@click="Review(item.id)">
								审核
							</button>
							<button class="delete" 
								v-if="user.department == 'admin'"
								@click="Delete(item.id,index)">
								删除
							</button>
						</span>
						<span v-else>
							<i 
								v-if="item.status == 'PASSED'">
								已通过
							</i>
							<i class="red"
								@click="Review(item.id)">
								查看
							</i>
							<i class="delete" 
								v-if="user.department == 'admin'"
								@click="Delete(item.id,index)">
								删除
							</i>
						</span>
						
						
						
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
				list: [], //任务列表
				dataReview: '', //审核数据
				dialogFormVisible: false,
				loading: false,
				page: {
					total: 10,
					num: 1,
					size: 15
				},
				filter: {
					date: ['',''],
					orderByColumn: 'createAt',
					sort: 'DESC'
				},
				isdesc: true//排序切换
			}
		},
		computed: {
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return {department: 'user'}
				}
			}
		},
		components: {
			FilterWork,
			ReviewMessage,
			Pagination
		},
		methods: {
			dialogClose () {
				this.dialogFormVisible=false;
				this.initPage();
			},
			Inquire (filter) {
//				console.log(filter);
//				return;
				
				if (!filter.date) {
					filter.date = ['','']
				}
					
				this.filter = filter;
				this.initPage();
			},
			Sort (para) {//排序
				this.isdesc = !this.isdesc;
				this.filter.orderByColumn = para;
				this.filter.sort = this.isdesc ? 'DESC':'ASC';
				this.initPage();
			},
			currentChange (p) {
//				console.log(p)
				this.page.num = p;
				this.initPage();
			},
			Review(id) { //查看资料
				let _this = this;
				_this.$until.superGet('/api//tlb-user/getTlbUser/' + id, function(res) {
					////console.log(res)
					if(res.statusCode == '0000') {
						_this.dataReview = res.data;
					    _this.dialogFormVisible = true;
					}else {
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
					_this.$until.superDelete('/api//tlb-user/deleteTlbUser/' + id, function(res) {
//						console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.initPage();
						}else {
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
//				_this.loading = true;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
				        "username": _this.filter.name,
				        "phone": _this.filter.phone,
				        "status": _this.filter.status,
						"type":_this.filter.type,
						"bCreateAt": _this.filter.date[0], //开始申请时间 2018-07-25
				        "eCreateAt": _this.filter.date[1],       
						"bApprovedAt":"",
						"eApprovedAt":"",
						"approvedname":"",
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
				    }
				};
				_this.$until.superPost('/api/tlb-user/pageList', datas, function(res) {
//					console.log(res)
					
					_this.loading = false;
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
					}
					

				})
			}
		},
		mounted() {
			let _this = this;
			_this.initPage();
		}
	}
</script>

<style scoped>
	.headlist span,
	.bodylist li span {
		width: 10%;
	}
	.headlist span.time,
	.bodylist li span.time {
		width: 15%;
	}
	
	.headlist span:last-of-type,
	.bodylist li div {
		width: 16%;
	}
	i.red{
		color: #0000FF;
		cursor: pointer;
	}
	i.delete {
		color: #FF0000;
		cursor: pointer;
	}
</style>