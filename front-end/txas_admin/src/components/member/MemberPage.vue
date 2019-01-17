<template>
	<div class="content_box">
		
		<el-dialog
		  title="客户笔记"
		  :visible.sync="dialogVisible"
		  width="30%">
		    <el-input
			  type="textarea"
			  :rows="4"
			  placeholder="请输入内容"
			  v-model="area_content">
			</el-input>
		  <span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisible = false">取 消</el-button>
		    <el-button type="primary" @click="ConfirmEdit">确 定</el-button>
		  </span>
		</el-dialog>
		
		<div class="titling">
			<span>潜在客户</span>
			<!--<button>添加会员</button>
			<span class="pull-right">导出execl</span>-->
		</div>
		
		<filter-work  @Inquire="Inquire" :parent="5"></filter-work>
		
		<div class="list_section">
			<div class="headlist clearfix">
				<!--<span class="pull-left">会员编号</span>-->
				<!--<span class="pull-left">名称</span>-->
				<span class="pull-left  pointer" @click="Sort('createAt')">登录时间<i class="el-icon-d-caret"></i></span>
				<span class="pull-left ">登录手机</span>
				<span class="pull-left time">笔记</span>
				<!--<span class="pull-left">归属</span>
				<span class="pull-left time">邮箱</span>
				<span class="pull-left">推荐人</span>
				<span class="pull-left time">推荐码</span>-->
				<!--<span class="pull-left"></span>-->
				<span class="pull-right">随记</span>
			</div>
			<ul class="bodylist">
				<li class="clearfix" v-for="item in list">
					<!--<span class="pull-left">DFS3SD</span>
					<span class="pull-left">李某某</span>-->
					<span class="pull-left ">{{item.createAt}}</span>
					<span class="pull-left ">{{item.mobileNum}}</span>
					<span class="pull-left time">{{item.text}}</span>
					<!--<span class="pull-left">套利宝结算</span>
					<span class="pull-left time">2456225**@qq.com</span>
					<span class="pull-left">王某某</span>
					<span class="pull-left time">15812341234</span>
					<span class="pull-left">未开户</span>-->
					<span class="pull-right"><el-button icon="el-icon-edit" @click="openDialog(item)"></el-button></span>
				</li>
			</ul>
		</div>
		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
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
					size: 15
				},
				list: [],
				filter: {},
				dialogVisible: false,
				mobileNum: '',
				area_content: '',
				isdesc: false//排序切换
			}
		},
		components: {
			FilterWork,
			Pagination
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
//				//console.log(p)
				this.page.num = p;
				this.initPage();
			},
			openDialog (item) {
				let _this = this;
				_this.mobileNum = item.mobileNum;
				_this.area_content = item.text;
				this.dialogVisible = true;
			},
			ConfirmEdit() {
				let _this = this;
		        let datas = {
					"mobileNum": _this.mobileNum,
    				"text": _this.area_content
				};
				_this.$until.superPost("/api/potential-user/editUser",datas,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
				        _this.dialogVisible = false;
				        _this.initPage();
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
		    },
			initPage () {
				let _this = this;
				let datas = {
					"page_number": _this.page.num,  //页码
					"page_size": _this.page.size,   // 每页条数
					"formParams": {
				        "mobile": _this.filter.phone,
				        "orderByColumn": _this.filter.orderByColumn,
						"sort": _this.filter.sort
				    }
				};
				_this.$until.superPost("/api/potential-user/pageList",datas,function (res) {
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
	.headlist span {
		width: 10%;
	}
	.headlist span.time {
		width: 60%;
	}
	.bodylist li span {
		width: 10%;
	}
	.bodylist li span.time {
		width: 60%;
	}
	.el-button {
		border: none;
	}
</style>