<template>
	<div class="content_box">

		<div class="titling">
			<span>消息推送</span>
			<button @click="dialogVisible = true">新增消息</button>
		</div>

		<el-dialog :visible.sync="dialogVisible" width="30%">
		  <add-push @close="dialogClose" :mess="mess"></add-push>
		</el-dialog>

		<mobile-list :dialogFormVisible="dialogVisible2" :noticeId="noticeId" :noticeName="noticeName" @close="dialogClose"></mobile-list>

		<!--<filter-work @Inquire="Inquire" :parent="3"></filter-work>-->


		<div class="list_body">
			<div class="clearfix table_head myback2">
				<div class="pull-left">
					<span>类型</span>
				</div>
				<div class="pull-left">
					<span>内容</span>
				</div>
				<div class="pull-left">
					<span>路径</span>
				</div>
				<div class="pull-left">
					<span class="pointer" @click="Sort('createTime')">时间<i  class="el-icon-d-caret"></i></span>
				</div>
				<div class="pull-right">
					<span>操作</span>
				</div>
			</div>
			<div v-for="item in list" class="item_box clearfix">

				<div class="pull-left">
					<span>{{item.type}}</span>
				</div>
				<div class="pull-left">
					<!--<span>{{item.context}}</span>-->
					<el-tooltip placement="right-start" >
						<div slot="content" class="_content"><p>{{item.context}}</p></div>
					  	<el-button  type="text" ><span v-text="(item.context+'.').substr(0,15)"></span></el-button>
					</el-tooltip>
				</div>
				<div class="pull-left">
					<span>{{item.url}}</span>
				</div>
				<div class="pull-left">
					<span>{{item.createTime}}</span>
				</div>
				<div class="pull-right ">

					<el-button v-show="item.sendFlag == 'N'" type="text" @click="allSend(item.id,item.type)">全部发送</el-button>
					<el-button v-show="item.sendFlag == 'N'" type="text" @click="selectSend(item.id,item.type)">指定发送</el-button>
					<el-button v-show="item.sendFlag == 'N'" type="text" @click="editMess(item)">编辑</el-button>
					<i class="only_text"><el-button v-show="item.sendFlag == 'Y'" type="text">已发送</el-button></i>
					<i class="del_button"><el-button type="text" @click="Delete(item.id)">删除</el-button></i>
				</div>
			</div>

			<div class="trip_box" v-show="!list.length">
				<img src="../../assets/img/nodata_2.png" />
				<p>暂无数据</p>
			</div>
		</div>

		<!--分页-->
		<pagination @currentChange="currentChange" :page="page"></pagination>
	</div>
</template>

<script>
	import Pagination from "../common/Pagination"
	import FilterWork from "../common/FilterWork"
	import AddPush from './AddPush'
	import MobileList from '../list/MobileList'
	export default {
		data() {
			return {
				list: [],
				page: {
					total: 0,
					num: 1,
					size: 13
				},
        filter: {
          orderByColumn: 'createTime',
          sort: 'DESC'
        },
				saveShow: false,
				dialogVisible: false,
				dialogVisible2: false,
				noticeId: null,
				noticeName: '',
				mess: {
					id: '',
					url: '',
					type: '',
					context: ''
				}
			}
		},
		components: {
			AddPush,
			Pagination,
			FilterWork,
			MobileList
		},
		methods: {
			editMess (val) {
				this.mess = val;
				this.dialogVisible = true;
			},
			Delete (id) {
				let _this = this;

				_this.$confirm('确认删除?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.$fetch("/api/hpp_notice/delete/"+id).then(res => {
//							console.log(res)
							if(res.statusCode == '0000') {
								_this.$message({
									type: 'success',
									message: '删除成功!'
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
			dialogClose () {
				this.dialogVisible=false;
				this.dialogVisible2 = false;
				this.initPage();
			},
			allSend (id,name) {
				let _this = this;
				let datas = {
					noticeId: id,//	是	string	消息Id
					type: 'WHOLE'
				}
				_this.$confirm('向全体用户发送'+name+', 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						_this.$post("/api/hpp_notice/sendNotice",datas).then((res) => {
//							console.log(res)
							if(res.statusCode == '0000') {
								_this.$message({
									type: 'success',
									message: '发送成功!'
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
			selectSend (id,name) {
				let _this = this;
				_this.noticeId = id;
				_this.noticeName = name;
				_this.dialogVisible2 = true;
			},
			currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire (filter) {
				this.filter = filter;
				this.initPage();
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
            "orderByColumn": _this.filter.orderByColumn,
            "sort": _this.filter.sort,
					}
				}
				this.$post("/api/hpp_notice/pageList",datas).then((res) => {
//					console.log(res)
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
	.table_head {
		line-height: 40px;
	}
	.item_box {
		height: 50px;
		padding: 10px 0;
		border-bottom: 1px solid #999;
	}
	.table_head>div,
	.item_box>div{
		width: 300px;
		min-height: 40px;
		text-align: center;
	}
	.table_head>div span,
	.item_box>div span {
		color: #fff;
		font-size: 14px;
	}
	.item_box>div span {
		display: block;
		color: #666;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.item_box>div p {
		font-size: 14px;
		line-height: 20px;
	}
	.el-button--text {
		color: #007AFF;
	}
	.only_text button {
		color: #999;
	}
	.del_button button {
		color: #FF0000;
	}
	._content {
		width: 200px;
		overflow: hidden;
	}
</style>
