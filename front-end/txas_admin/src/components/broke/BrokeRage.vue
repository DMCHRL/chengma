<template>
	<div class="content_box">
		<el-dialog custom-class="sec_dialog" :show-close="false" :visible.sync="dialogVisible" width="20%">
			<h3 slot="title" class="dialog_title">佣金结算</h3>
			<div class="input_box">
				<span>账号</span>
				<select name="zhanhao">
					<option value="1">1891234</option>
					<option value="1">1894567</option>
				</select>
			</div>
			<div class="input_box">
				<span>金额</span>
				<input type="number" name="" id="" value="" />
			</div>
			<div slot="footer" class="dialog_footer">
				<button @click="dialogVisible = false">确认</button>
				<button @click="dialogVisible = false">取消</button>
			</div>
		</el-dialog>
		
		<div class="top_content">
			<div class="row">
				<div class="col-md-4">
					<div class="item_box clearfix">
						<p>今日佣金</p>
						<p><span>0</span>美元</p>
					</div>
				</div>
				<div class="col-md-4">
					<div class="item_box item_box1">
						<p>佣金总收入</p>
						<p><span>0</span>美元</p>
					</div>

				</div>
				<div class="col-md-4">
					<div class="item_box item_box2">
						<p>未结佣金总额</p>
						<p><span>0</span>美元</p>
					</div>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="titling">
				<span>全部</span>
				<button @click="dialogVisible = true">佣金结算</button>
			</div>
			
			<filter-work @Inquire="Inquire" :parent="4"></filter-work>
			
			<div class="list_section">
				<div class="headlist clearfix">
					<span class="pull-left time">登录名</span>
					<span class="pull-left">名称</span>
					<span class="pull-left">身份</span>
					<span class="pull-left time pointer" @click="Sort('createAt')">创建时间<i class="el-icon-d-caret"></i></span>
					<!--<span class="pull-left">交易手数</span>-->
					<span class="pull-left pointer" @click="Sort('lotAmount')">手数单笔提成（元）<i class="el-icon-d-caret"></i></span>
					<span class="pull-left pointer" @click="Sort('lotPercent')">返佣比例<i class="el-icon-d-caret"></i></span>
					<span class="pull-left pointer" @click="Sort('balance')">未提现<i class="el-icon-d-caret"></i></span>
					<span class="pull-left pointer" @click="Sort('withdraw')">已提现<i class="el-icon-d-caret"></i></span>
					<span class="pull-left pointer" @click="Sort('total')">总佣金<i class="el-icon-d-caret"></i></span>
				</div>
				<ul class="bodylist">
					<li class="clearfix" v-for="item in list">
						<span class="pull-left time">{{item.login}}</span>
						<span class="pull-left">{{item.firstName}}</span>
						<span class="pull-left" v-text="item.department == 'company'? '分公司':'代理'"></span>
						<span class="pull-left time">{{item.createAt}}</span>
						<!--<span class="pull-left">{{item.total}}</span>-->
						<span class="pull-left">{{item.lotAmount}}</span>
						<span class="pull-left">{{item.lotPercent}}</span>
						<span class="pull-left">{{item.balance}}</span>
						<span class="pull-left">{{item.withdraw}}</span>
						<span class="pull-left">{{item.total}}</span>
					</li>
				</ul>
			</div>
			<!--分页-->
			<pagination @currentChange="currentChange" :page="page"></pagination>
		</div>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	import FilterWork from "../common/FilterWork.vue"
	export default {
		data() {
			return {
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				list: [],
				dialogVisible: false,
				filter: {
					name: '',
					login: ''
				},
				isdesc: false
			}
		},
		components: {
			Pagination,
			FilterWork
		},
		methods: {
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
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			initPage() {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams":{
						"firstName": _this.filter.name,
						"login": _this.filter.login,
						"orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
					}
				}
				_this.$until.superPost("/api/tlb-commission/pageList",datas,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.list = res.data.list;
						_this.page.total = res.data.total;
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
	.top_content .item_box {
		padding: 28px 0;
		text-align: center;
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_1.png);
		background-size: 200% 100%;
		background-repeat: no-repeat;
	}
	
	.top_content .item_box1 {
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_2.png);
	}
	
	.top_content .item_box2 {
		background-image: url(http://tlb.txasfx.com/crm/img/bro_bg_3.png);
	}
	
	.top_content .item_box p:first-of-type {
		margin-bottom: 20px;
	}
	
	.top_content .item_box .pull-left {
		padding-left: 130px;
	}
	
	.top_content .item_box .pull-right {
		padding-right: 25px;
	}
	
	.top_content .item_box p {
		font-size: 16px;
		color: #fff;
	}
	
	.top_content .item_box span {
		font-size: 30px;
		color: #fff;
	}
	
	.content {
		padding: 20px;
		background-color: #fff;
	}
	.headlist span {
		width: 10%;
	}
	
	.headlist span.time{
		width: 13%;
	}
	.bodylist li span {
		width: 10%;
	}
	
	.bodylist li span.time{
		width: 13%;
	}
	
	/*弹窗*/
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.sec_dialog .input_box {
		margin-bottom: 10px;
		position: relative;
	}
	
	.sec_dialog .input_box span {
		display: inline-block;
		width: 30%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	.sec_dialog .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	.sec_dialog .input_box input,
	.sec_dialog .input_box select {
		border: 1px solid #ccc;
		width: 50%;
		padding: 5px 10px;
		border-radius: 5px;
		height: 30px;
	}
	
	.sec_dialog .input_box p {
		cursor: pointer;
		text-align: center;
		font-size: 14px;
		color: #F14B3B;
	}
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 18px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
		border: 1px solid #F14B3B;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
	/*弹窗end*/
</style>