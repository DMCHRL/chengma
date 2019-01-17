<template>
	<div class="content_box">
		
		<div class="titling">
			<span>跟单/解绑审核</span>
      <span class="dao pull-right" @click="outExe">导出excel</span>

		</div>
		
		<filter-work @Inquire="Inquire" :parent="'task2'"></filter-work>
		
		
		<div class="list_section">
			<div class="headlist clearfix myback2">
				
				<template v-for="(item,index) in headlist">
					<span :key="index"
						:class="['pull-left', item.istime? 'time': '', item.sort?'pointer':'']" 
						@click="Sort(item.sort)" v-if="item.mt4 !== identity">{{item.name}}
						<i v-if="item.sort" class="el-icon-d-caret"></i>
					</span>
				</template>
				
				<span class="pull-right time">操作</span>
			</div>
			
			
			<ul class="bodylist">
				<li v-for="item in list" class="item_box clearfix">
					<div class="pull-left">
						<span>{{item.account}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.password}}</span>
					</div>
					<div class="pull-left">
						<span>{{item.strategyName}}</span>
					</div>
					
					<div class="pull-left">
						<span v-text="item.type == 'IN'?'跟单':'解绑'"></span>
					</div>
					<div class="pull-left"  v-if="identity !== 'mt4'">
						<span>{{item.mobileNum}}</span>
					</div>
					
					<!-- <div class="pull-left">
						<el-tooltip placement="right-start" >
							<div slot="content" class="_content"><p>{{item.password}}</p></div>
								<el-button  type="text" class="el-icon-view"></el-button>
						</el-tooltip>
					</div> -->
					<div class="pull-left time">
						<span>{{item.createAt}}</span>
					</div>
					<div class="pull-left target" :class="item.mt4Status == 'ONLINE'? 'green': (item.mt4Status == 'DOWNLINE'? 'red':'blue')">
						<el-select v-model="item.mt4Status" placeholder="请选择" @change="mt4StatusChange(item)">
							<el-option
								v-for="item in options"
								:key="item.value"
								:label="item.label"
								:value="item.value"
								>
							</el-option>
						</el-select>
						<!-- <span 
						:class="item.mt4Status == 'ONLINE'? 'green': (item.mt4Status == 'DOWNLINE'? 'red':'blue')"
						>
						{{item.mt4Status | mt4StatusLabel}}
						</span> -->
					</div>
					<div class="pull-right time">
						<div class="btn_box" v-if="item.status == 'APPLYING'">
							<span class="pointer" @click="AuditStrategy(item.id,'PASSED',item.strategyName,item.type)"><i>通过</i></span>
							<span class="pointer delete" @click="AuditStrategy(item.id,'REJECT',item.strategyName,item.type)"><i>拒绝</i></span>
						</div>
						<span v-else v-text="item.status=='PASSED'?'已通过':'已拒绝'"></span>
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
					{name: '账号',sort: null,istime: false,mt4:null},
					{name: '密码',sort: null,istime: false,mt4:null},
					{name: '名称',sort: null,istime: false,mt4:null},
					{name: '类型',sort: null,istime: false,mt4:null},
					{name: '手机号',sort: null,istime: false,mt4:'mt4'}, /*云端管理员不可看*/
					{name: '时间',sort: 'createAt',istime: true,mt4:null},
					{name: '云端状态',sort: null,istime: false,mt4:null},
				],
				options: [{//云端状态
          value: 'ONLINE',
          label: '已上线'
        }, {
          value: 'DOWNLINE',
          label: '已下线'
        }, {
          value: 'NOLINE',
          label: '未上线'
        }],
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 13
				},
				filter: {
          orderByColumn: 'createAt',
          sort: 'DESC',
          date:['',''],
        },
				saveShow: false,
        identity:''/*识别当前登录身份*/
			}
		},
		components: {
			SaveStrategy,
			Pagination,
			FilterWork
		},
		filters: {
			mt4StatusLabel: function(value) {
				switch (value){
					case 'ONLINE':
						return '已上线'
						break;
					case 'DOWNLINE':
						return '已下线'
						break;
					case 'NOLINE':
						return '未上线'
						break;
					default:
						break;
				}
			}
		},
		methods: {
			mt4StatusChange (item) {
				let _this = this;
				let datas = {
					id: item.id,//	是	string	id
					mt4Status: item.mt4Status//	是	string	ONLINE DOWNLINE NOLINE
				};
				this.$post("/api/hpp_strategy_order/setMt4Status",datas).then((res) => {
					if (res.statusCode == '0000') {
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
			},
			AuditStrategy (id,status,name,type) {
				let _this = this;
				let datas = {
					strategyOrderId: id,
					status: status
				}
				let text = status == 'REJECT'? '拒绝':'通过';
				let typeText = type == 'IN'? '跟单':'解绑';
				
					_this.$confirm(text+'['+name+']策略'+typeText+', 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.$post("/api/hpp_strategy_order/approve",datas).then((res) => {
							// console.log(res)
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
      outExe() {
        let _this = this;
        this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let datas = {
            page_number: 1, //	是	int	当前页
            page_size: 1000,//	是	int	每页大小
            formParams: {
              strategyName: _this.filter.name,
              mobileNum: _this.filter.phone,
              account: _this.filter.account,
              status: _this.filter.status,
              type: _this.filter.type,
              startTime: _this.filter.date[0],
              endTime: _this.filter.date[1],
              orderByColumn: _this.filter.orderByColumn,
              sort: _this.filter.sort,
            }
          };

          _this.$postForExcel('/api/export/hppStrategyOrder',datas,'跟单/解绑记录').then();

        }).catch(() => {

        });
      },
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire (filter) {
				this.filter = filter;
				this.currentChange(1);
			},
			closeInit () {
				this.saveShow = false;
				this.initPage();
			},
      Sort (param) {//列表排序
				if (!param) return;
				this.filter.orderByColumn = param;
				this.filter.sort =='DESC' ? this.filter.sort ='ASC': this.filter.sort = 'DESC';
				this.initPage();
      },
			initPage () {
				let _this = this;
				let datas = {
					page_number: this.page.num,
					page_size: this.page.size,
					formParams: {
						strategyName: _this.filter.name,
						mobileNum: _this.filter.phone,
						account: _this.filter.account,
            status: _this.filter.status,
            type: _this.filter.type,
            startTime: _this.filter.date[0],
            endTime: _this.filter.date[1],
            orderByColumn: _this.filter.orderByColumn,
            sort: _this.filter.sort,
					}
				};
				this.$post("/api/hpp_strategy_order/pageList",datas).then((res) => {
					if (res.data) {
						_this.list = res.data.list;
						_this.page.total = parseInt(res.data.total);
					}
				})
			}
		},
		mounted () {
			let userStr = localStorage.getItem("user");
			if (userStr) {
				let user = JSON.parse(userStr);
				this.identity = user.department;
			}
			this.initPage();
		}
	}
</script>

<style scoped>
	.headlist span,
	.bodylist li>div {
		width: 160px;
	}

	.headlist span.time,
	.bodylist li>div.time {
		width: 220px;
	}
	.bodylist li>div.target {
		width: 200px;
	}
	.bodylist li>div.target .el-select{
		width: 100px;
	}
	.bodylist li>div.target:before{
		content: '';
		display: inline-block;
		width: 10px;
		height: 10px;
		background-color: #eee;
		margin-right: 10px;
		border-radius: 50%;
		box-shadow: 0px 2px 2px #eee;
	}
	.bodylist li>div.red:before{
		background-color: #FF4A4A;
		box-shadow: 0px 2px 2px #FF4A4A;
	}
	.bodylist li>div.blue:before{
		background-color: #007AFF;
		box-shadow: 0px 2px 2px #007AFF;
	}
	.bodylist li>div.green:before{
		background-color: #00FF00;
		box-shadow: 0px 2px 2px #00FF00;
	}
	
	._content {
		width: 200px;
		/*background-color: #fff;
		color: #666;*/
	}
	
	.btn_box>span i{
		color: #6562b6;
	}
	.btn_box span.delete i {
		color: #FF4A4A;
	}
	
</style>
