<template>
	<div class="content_box">
			<div class="titling">
				<span>出入金明细</span>
			</div>
			
			<filter-work  @Inquire="Inquire" :parent="7"></filter-work>
			
			<div class="list_section">
				<div class="headlist clearfix">
					<span class="pull-left">账号</span>
					<span class="pull-left">名称</span>
					<span class="pull-left">审核事项</span>
					<span class="pull-left pointer" @click="Sort('amount')">金额<i class="el-icon-d-caret"></i></span>
					<span class="pull-left time pointer" @click="Sort('createAt')">申请时间<i class="el-icon-d-caret"></i></span>
					
					<span class="pull-right">状态</span>
				</div>
				<ul class="bodylist">
					<li class="clearfix" v-for = "item in list">
						<span class="pull-left">{{item.account}}</span>
						<span class="pull-left">{{item.username}}</span>
						<span class="pull-left" v-text="item.fundType == 'IN' ? '充值' : (item.fundType == 'OUT' ? '提现' : '佣金内转')"></span>
						<span class="pull-left">{{item.amount}}</span>
						<span class="pull-left time">{{item.createAt}}</span>
					
						<div class="pull-right">
							
							<span>
								<i v-show="item.status == 'PASSED'">已通过</i>
								<i v-show="item.status == 'REJECT'">已拒绝</i>
								<i v-show="item.status == 'APPLYING'">审核中</i>
								
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
					orderByColumn: 'createAt',
					sort: 'DESC'
				},
				isdesc: true,//排序切换
				dialogFormVisible: false,
				dataReview: ''
			}
		},
		components: {
		},
		methods: {
			dialogClose () {
				this.dialogFormVisible=false;
				this.initPage();
			},
			Inquire (filter) {
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
				        "account": _this.filter.account,     //申请号
				        "fundType": _this.filter.type,  //任务事项
				        "status": _this.filter.status,       //状态
				        "username": _this.filter.name, //申请人(firstName)
				        "approvedname":"", //处理人(firstName)123
				        "bCreateAt": _this.filter.date[0], //开始申请时间 2018-07-25
				        "eCreateAt": _this.filter.date[1],                 //结束申请时间
				        "bApprovedAt":"",             //开始处理时间
				        "eApprovedAt":"",            //结束处理时间
				        "orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
				    }
				};
				_this.post("/api/tlb-fund-apply/pageList",datas,function (res) {
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
		width: 15%;
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
	
</style>
