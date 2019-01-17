<template>
	<div class="content_box">
		<div class="titling">
			<span>用户反馈</span>
		</div>
		
		<filter-work @Inquire="Inquire" :parent="8"></filter-work>
		
		<div class="list_section">
			<div class="headlist clearfix">
				<span class="pull-left">账户号</span>
				<span class="pull-left">姓名</span>
				<span class="pull-left">手机号</span>
				<span class="pull-left">问题类型</span>
				<span class="pull-left time pointer" @click="Sort('createAt')">时间<i class="el-icon-d-caret"></i></span>
				<span class="pull-right">问题详情</span>
			</div>
			<ul class="bodylist" v-loading="loading" element-loading-text="拼命加载中...">
				<li class="clearfix" v-for="item in list">
					<span class="pull-left ">{{item.tlbAccount}}</span>
					<span class="pull-left">{{item.userName}}</span>
					<span class="pull-left">{{item.phone}}</span>
					<span class="pull-left ">{{item.feedbackType}}</span>
					<span class="pull-left time">{{item.createAt}}</span>
					<span class="pull-right">{{item.feedbackContext}}</span>
				</li>
			</ul>
			<div class="trip_box" v-show="!list.length">
				<img src="@/assets/img/nodata_2.png"/>
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
				list: '',
				loading: false,
				page: {
					total: 10,
					num: 1,
					size: 15
				},
				filter: {
					orderByColumn: 'createAt',
					sort: 'DESC'
				},
				isdesc: true//排序切换
			}
		},
		components: {
		},
		methods: {
			Inquire (filter) {
//				console.log(filter);
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
			initPage () {
				let _this = this;
//				_this.loading = true;
				
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						"userName": _this.filter.name,
				        "tlbAccount": _this.filter.login,
				        "feedbackType": _this.filter.type,
				        "orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
					}
				}
				
				_this.post('/api//tlb-user-feedback/pageList',datas,function (res) {
//					console.log(res)
					_this.list = res.data.list;
					_this.page.total = parseInt(res.data.total);
					_this.loading = false;
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
		width: 9%;
	}
	.headlist span.time,
	.bodylist li span.time {
		width: 12%;
	}
	.headlist span:last-of-type,
	.bodylist li span:last-of-type {
		width: 50%;
	}

</style>