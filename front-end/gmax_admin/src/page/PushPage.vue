<template>
	<div class="content_box">
			<div class="titling">
				<span>消息推送</span>
				<button @click="NewPush">新增消息</button>
			</div>
			
			<save-push :show="dialogFormVisible" :message="pushData" @close="dialogClose"></save-push>
			
			<filter-work  @Inquire="Inquire" :parent="9"></filter-work>
			
			<div class="list_section">
				<div class="headlist clearfix">
					<span class="pull-left">消息类型</span>
					<span class="pull-left time">消息内容</span>
					<span class="pull-left time pointer" @click="Sort('createTime')">更新时间<i class="el-icon-d-caret"></i></span>
					<span class="pull-right">状态</span>
				</div>
				<ul class="bodylist">
					<li class="clearfix" v-for = "item in list">
						<span class="pull-left">{{item.type}}</span>
						<span class="pull-left time">{{item.context}}</span>
						<span class="pull-left time">{{item.updateTime}}</span>
						
						<div class="pull-right">
							<button @click="sendMessage(item.id)" v-if="item.sendFlag == 'N'">
								发送
							</button>
							<span v-show="item.sendFlag == 'Y'">
								<i >已发送</i>
							</span>
							<span>
								<i class="red"
									@click="Review(item)">
									查看
								</i>
							</span>
							<span>
								<i class="delete"
									@click="Delete(item.id)">
									删除
								</i>
							</span>
						</div>
					</li>
				</ul>
				<div class="trip_box" v-show="!list.length">
					<img src="@/assets/img/nodata_2.png" />
					<p>暂无数据</p>
				</div>
			</div>
			
			<!--分页-->
			<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import SavePush from "@/components/dialog/SavePush"
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 15
				},
				filter: {
					date: ['',''],
					orderByColumn: 'createTime',
					sort: 'DESC'
				},
				isdesc: true,//排序切换
				dialogFormVisible: false,
				pushData: {
					type: '',
					context: ''
				}
			}
		},
		components: {
			SavePush
		},
		methods: {
			Delete(id) {
				let _this = this;

				_this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.fetch("/api/notice/delete/" + id, function(res) {
						//console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.initPage();
						}
					})
				}).catch(() => {});
			},
			sendMessage (id) {
				let _this = this;
				let datas = {
					noticeId: id,//	是	string	消息Id
					type: 'WHOLE',
				}
				_this.post("/api/notice/sendNotice",datas,function (res) {
					// console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
							message: '发送成功',
							type: 'success'
						});
						_this.initPage();
					}else {
						_this.$message({
							message: res.msgCode,
							type: 'warning'
						});
					}
				})
			},
			dialogClose () {
				this.dialogFormVisible=false;
				this.initPage();
			},
			NewPush () {
				this.pushData = {
					type: '',
					context: ''
				}
				this.dialogFormVisible=true;
			},
			Review(item) { //查看
				this.pushData = item;
				this.dialogFormVisible=true;
			},
			Inquire (filter) {
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
				this.page.num = p;
				this.initPage();
			},
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
				        "orderByColumn": _this.filter.orderByColumn,
						    "sort": _this.filter.sort
				    }
				};
				_this.post("/api/notice/pageList",datas,function (res) {
					// console.log(res)
					if (res.statusCode == "0000") {
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
		width: 11%;
	}
	
	.headlist span.time,
	.bodylist li span.time {
		width: 14%;
	}
	
	.headlist span:last-of-type,
	.bodylist li span.last,
	.bodylist li div {
		width: 16%;
	}
	i.red {
		color: #0000FF;
		cursor: pointer;
	}
	i.delete {
		color: #FF0000;
		cursor: pointer;
	}
	
</style>
