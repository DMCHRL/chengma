<template>
	<div class="content_box">
		<div class="titling clearfix">
			<span>平仓记录</span>
			<!--<span class="pull-right">导出</span>-->
		</div>
		<div class="content">
			
			<filter-work @Inquire="Inquire" :parent="3"></filter-work>
			
			<details-list @Sort="Sort" :list="list"></details-list>
			<!--分页-->
		    <pagination @currentChange="currentChange" :page="page"></pagination>
		</div>
	</div>
</template>

<script>
	import FilterWork from "../common/FilterWork.vue"
	import DetailsList from "../list/DetailsList.vue"
	import Pagination from "../common/Pagination.vue"
	export default {
		data() {
			return {
				page: {
					total: 0,
					num: 1,
					size: 15
				},
				tab1: 2,
				list: [],
				filter: {
					account: '',
					date: ['',''],
					orderByColumn: 'closeTime',
					sort: 'DESC'
				}
			}
		},
		computed: {
			urlaccount () {
				return this.$route.query.account;
			}
		},
//		watch: {
//			urlaccount (val) {
//				console.log(val)
//				return
//				
//				this.filter.account = val;
//				this.initPage();
//			}
//		},
		components: {
			FilterWork,
			DetailsList,
			Pagination
		},
		methods: {
			Inquire (filter) {
//				console.log(filter);
//				return;
				this.filter = filter;
				this.initPage();
			},
			Sort (data) {//列表排序
//				console.log(data)
				this.filter.orderByColumn = data.orderByColumn;
				this.filter.sort = data.sort;
				this.initPage();
			},
			currentChange (p) {//分页点击
//				//console.log(p)
				this.page.num = p;
				this.initPage();
			},
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
				        "account": _this.filter.account, // 套利宝帐号
				        "startCloseDate": _this.filter.date[0],
				        "endCloseDate": _this.filter.date[1],
				        "orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort,
						"orderNo": _this.filter.login
				    }
				};
				_this.$until.superPost("/api/tlb-trade/pageList",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.page.total = res.data.total;
					}
				})
			}
		},
		mounted() {
			this.initPage();
			let acc = this.urlaccount;
			if (acc) {
				this.filter.account = acc;
				this.initPage();
			}
		}
	}
</script>

<style scoped>
	
</style>