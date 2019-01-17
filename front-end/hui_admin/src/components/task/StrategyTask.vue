<template>
	<div class="content_box">
		
		<save-strategy :show="saveShow"  :titleText="titleText" :strategyData="strategyData" @close="closeInit"></save-strategy>
		
		<div class="titling">
			<span>策略审核</span>
			<!--<button @click="saveShow = true">申请策略</button>-->
		</div>
		
		<filter-work @Inquire="Inquire" :parent="'task1'"></filter-work>
		
		
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
				<li v-for="item in list" class="item_box clearfix">
					<div class="pull-left">
						<span>{{item.strategyName}}</span>
					</div>
					
					<div class="pull-left">
						<span>{{item.strategyName2}}</span>
					</div>
					<div class="pull-left">
						<!--<p>{{item.strategyText}}</p>-->
						<el-tooltip placement="right-start" >
							<div slot="content" class="_content"><p>{{item.strategyText}}</p></div>
								<el-button  type="text" ><span v-text="(item.strategyText+'.').substr(0, 5)"></span></el-button>
						</el-tooltip>
					</div>
					<div class="pull-left">
						<span>{{item.mobile}}</span>
					</div>
					<div class="pull-left">
					<span>{{item.account}}</span>
					</div>
					<div class="pull-left">
					<span>{{item.tradeType}}</span>
					</div>
					<div class="pull-left time">
						<span>{{item.updateAt}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.approveName}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.status | statusName}}</span>
					</div>
					<div class="pull-right time btn_box">
						
							<el-button type="primary" icon="el-icon-view" circle
							   @click="Edit(item)"></el-button>
							
							<el-button type="danger" icon="el-icon-delete" circle 
							v-if="item.status != 'PASSED'" @click="Delete(item.id)"></el-button>
							
						
						
						<!-- <div v-if="item.status == 'APPLYING'">
							<span class="pointer" @click="AuditStrategy(item.id,'PASSED',item.strategyName)"><i>通过</i></span>
							<span class="pointer delete"  @click="AuditStrategy(item.id,'REJECT',item.strategyName)"><i>拒绝</i></span>
							<span class="pointer"  @click="Edit(item)"><i>编辑</i></span>
						</div>
						<div v-else >
							
							<span class="pointer"  @click="Edit(item)"><i>编辑</i></span>
						</div> -->
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
	import SaveStrategy from '../strategy/SaveStrategy'
	export default {
		data() {
			return {
				headlist: [
					{name: '策略名称',sort: null,istime: false},
					{name: '策略副标题',sort: null,istime: false},
					{name: '策略介绍',sort: null,istime: false},
					{name: '手机号',sort: null,istime: false},
					{name: '账号',sort: null,istime: false},
					{name: '交易方式',sort: null,istime: false},
					{name: '时间',sort: 'createAt',istime: true},
					{name: '处理人',sort: null,istime: false},
					{name: '状态',sort: null,istime: false},
				],
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 15
				},
				filter: {
					orderByColumn: 'createAt',
					sort: 'DESC'
				},
				saveShow: false,
        strategyData: {
          id : '',
          strategyName: '',
          strategyName2: '',
          strategyText : '',
          platform : "",
          server : "",
          account: '',
          password: '',
          tradeType: ''
        },
        titleText: ""
			}
		},
		filters: {
			statusName (val) {
				switch (val){
					case 'APPLYING':
						return '审核中'
						break;
					case 'PASSED':
						return '已通过'
						break;
					case 'REJECT':
						return '已拒绝'
						break;
					default:
						return '未知'
						break;
				}
			}
		},
		components: {
			SaveStrategy,
			Pagination,
			FilterWork
		},
		methods: {
			Delete(id) {
				let _this = this;
				_this.$confirm('此操作将永久删除该条资料, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$strike('/api/hpp_strategy/deleteAll/' + id).then((res) => {
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
      Edit (item) {
        this.titleText = "编辑策略";
        this.strategyData = item;
        this.saveShow = true;
      },
			
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire (filter) {
				this.filter = filter;
				this.currentChange(1);
			},
			Sort (param) {//列表排序
				if (!param) return;
				this.filter.orderByColumn = param;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
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
						strategyName: _this.filter.name,
						account: _this.filter.account,
						mobile: _this.filter.phone,
						status: _this.filter.status,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
					}
				};
				this.$post("/api/hpp_strategy/pageList",datas).then((res) => {
					//console.log(res);
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
	.headlist span,
	.bodylist li>div {
		width: 140px;
	}
	
	.headlist span.time,
	.bodylist li>div.time {
		width: 200px;
	}
	
	._content {
		width: 200px;
		/*background-color: #fff;
		color: #666;*/
	}
	.btn_box span i{
		color: #6562b6;
	}
	.btn_box span.delete i {
		color: #FF4A4A;
	}
</style>
