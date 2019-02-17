<template>
	<div class="content_box">

		<save-strategy :show="saveShow" :titleText="titleText" :strategyData="strategyData" @close="closeInit"></save-strategy>

		<follow-list :show="followShow" :url="'/api/hpp_strategy_order/findList'" :id="strategyId" @close="closeInit"></follow-list>

		<el-dialog :visible.sync="pushDialogVisible" width="30%" @close="closeInit">
			<div class="dialog_box">
				<h3 slot="title" class="dialog_title">编辑推送消息</h3>

				<div class="input_box">
					<span class="import">消息类型（标题）</span>
					<input type="text" name="" id="1" value="" maxlength="15" placeholder="15字以内" v-model="push.type" />
				</div>
				<div class="input_box ">
					<span class="import">消息内容（中断原因）</span>
					<textarea name="" rows="6" cols="" placeholder="" v-model="push.context"></textarea>
				</div>

				<div slot="footer" class="dialog_footer">
					<button @click="outCommit">确认</button>
				</div>
			</div>
		</el-dialog>

		<div class="titling">
			<span>线上策略</span>
			<!--<button @click="saveShow = true">申请策略</button>-->
		</div>

		<!--<filter-work @Inquire="Inquire" :parent="'strategy'"></filter-work>-->

		<div class="list_section">
			<div class="headlist clearfix myback2">

				<template v-for="(item,index) in headlist">
					<span :key="index"
						:class="['pull-left', item.istime? 'time': '', item.sort?'pointer':'']"
						@click="Sort(item.sort)">{{item.name}}
						<i v-if="item.sort" class="el-icon-d-caret"></i>
					</span>
				</template>

				<span class="pull-right time">操作</span>
			</div>

			<ul class="bodylist">
				<li v-for="(item,index) in list" class="item_box clearfix">
					<div class="pull-left" >
						<span>{{item.strategyName}}</span>
					</div>
					<div class="pull-left time">
						<span>{{item.strategyName2}}</span>
					</div>
					<div class="pull-left">
						<!--<p>{{item.strategyText}}</p>-->
						<el-tooltip placement="right-start">
							<div slot="content" class="_content">
								<p>{{item.strategyText}}</p>
							</div>
							<el-button type="text"><span v-text="(item.strategyText+'.').substr(0,8)+'...'"></span></el-button>
						</el-tooltip>
					</div>
					<div class="pull-left time">
						<span>{{item.account}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.mobile}}</span>
					</div>
					<div class="pull-left time">
						<span>{{item.updateAt}}</span>
					</div>
					<div class="pull-left">
						<span class="pointer" @click="showFollow(item.id)">{{item.followNum}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.incomeRate}}</span>
					</div>
					<div class="pull-left">
						<el-switch v-model="item.line" active-color="#13ce66" inactive-color="#ff4949" @change="downOrUpLine(item.id)">
						</el-switch>
					</div>
					<div class="pull-left">
						<el-switch v-model="item.activity" active-color="#13ce66" inactive-color="#ff4949" @change="outStrategy(item)">
						</el-switch>
					</div>
					<div class="pull-right time">
						<el-button @click="Edit(item)" type="primary" icon="el-icon-edit" circle></el-button>
						<el-button @click="Delete(item.strategyName,item.id)" type="danger" icon="el-icon-delete" circle></el-button>
					</div>
				</li>
			</ul>
		</div>
		<div class="trip_box" v-show="!list.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无数据</p>
		</div>


		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	import SaveStrategy from './SaveStrategy'
	import FollowList from '../list/FollowList'
	export default {
		data() {
			return {
				headlist: [
					{name: '策略名称',sort: null,istime: false},
					{name: '策略类型',sort: null,istime: true},
					{name: '策略介绍',sort: null,istime: false},
					{name: '账号',sort: null,istime: true},
					{name: '手机号',sort: null,istime: false},
					{name: '时间',sort: 'updateAt',istime: true},
					{name: '跟单人数',sort: null,istime: false},
					{name: '累计收益率%',sort: null,istime: false},
					{name: '控制上下线',sort: null,istime: false},
					{name: '信号中断',sort: null,istime: false},
				],
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				filter: {
					orderByColumn: 'updateAt',
					sort: 'ASC'
				},
				saveShow: false,
				followShow: false,
				strategyId: null,
				strategyData: {
					id: '',
					strategyName: '',
					strategyName2: '',
					strategyText: '',
					platform: "",
					server: "",
					account: '',
					password: ''
				},
				titleText: "",

				pushDialogVisible: false,
				push: {
					type: '',
					context: ''
				}
			}
		},
		components: {
			SaveStrategy,
			Pagination,
			FilterWork,
			FollowList
		},
		methods: {
			showFollow(id) {
				this.strategyId = id;
				this.followShow = true;
			},
			downOrUpLine(id) {
				let _this = this;
				//				console.log(id);
				_this.$fetch("/api/hpp_strategy/downOrUpLine/" + id).then((res) => {
					//console.log(res)
					if (res.statusCode == '0000') {
						_this.$message({
							type: 'success',
							message: '操作成功!'
						});
					} else {
						_this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
					_this.initPage();
				});
			},
			Edit(item) {
				this.titleText = "编辑策略";
				this.strategyData = item;
				this.saveShow = true;
			},
			Delete(name, id) {
				let _this = this;

				_this.$confirm('删除[' + name + ']策略, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$fetch("/api/hpp_strategy/delete/" + id).then((res) => {
						//						console.log(res)
						if (res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '操作成功!'
							});
							_this.initPage();
						} else {
							_this.$message({
								type: 'error',
								message: res.msgCode
							});
						}
					})

				}).catch(() => {});

			},
			//策略中断
			outCommit() {
				let _this = this;
				let datas = {
					id: _this.strategyId, //	是	string	id
					activityTitle: _this.push.type, //	是	string	消息标题
					activityText: _this.push.context
				}
				_this.$post("/api/hpp_strategy/outStrategy/", datas).then((res) => {
					//						console.log(res)
					if (res.statusCode == '0000') {
						_this.$message({
							type: 'success',
							message: '操作成功!'
						});
						_this.closeInit();
					} else {
						_this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
				})
			},
			outStrategy(item) {
				let _this = this;
				_this.strategyId = item.id;

				if (item.activity) {
					_this.outCommit();
				} else {
					_this.$confirm('[' + item.strategyName + ']信号中断并发送信息到跟单账号, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.pushDialogVisible = true;

					}).catch(() => {
						item.activity = true;
					});
				}

			},
			currentChange(p) { //分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire(filter) {
				this.filter = filter;
				this.initPage();
			},
			closeInit() {
				this.pushDialogVisible = false;
				this.saveShow = false;
				this.followShow = false;
				this.initPage();
			},
			Sort (data) {//列表排序
				if (!data) return;
				this.filter.orderByColumn = data;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
				this.initPage();
			},
			initPage() {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				};
				this.$post("/api/hpp_strategy/findPage", datas).then((res) => {
					if (res.data) {
						let list = res.data.list;

						if (list.length) {
							list.forEach(item => {
								item.activityFlag == 'Y' ? item.activity = true : item.activity = false;
								item.lineFlag == 'Y' ? item.line = true : item.line = false;
							});
							_this.list = list;
							//console.log(list)
						}
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted() {
			this.initPage();
		}
	};
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");

	.headlist span,
	.bodylist li>div {
		width: 120px;
	}

	.headlist span.time,
	.bodylist li>div.time {
		width: 165px;
	}
	.bodylist li {
		line-height: 60px;
	}
	.bodylist li span.pointer {
		color: #0000FF;
	}
	._content {
		width: 200px;
		overflow: hidden;
	}
</style>
