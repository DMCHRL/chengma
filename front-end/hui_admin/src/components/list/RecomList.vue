<template>
	<!--对话框-->
		<el-dialog :visible="show"  width="60%" @close="diaclose">
			<h3 slot="title" class="title_box">推荐列表</h3>
			<div class="dialog_body">
				<div class="filter_box clearfix">
					
					<div class="pull-left">
						<el-input
						  placeholder="手机号"
						  v-model="filter.phone"
						  clearable
						  @keyup.enter.native="Inquire" >
						</el-input>
					</div>
					<div class="pull-left">
						<el-button type="primary" icon="el-icon-search" @click="Inquire">搜索</el-button>
					</div>
				</div>
				<div class="table_head item_box clearfix">
					<div class="pull-left">
						<span>用户名</span>
					</div>
					<div class="pull-left">
						<span>手机号</span>
					</div>
					<div class="pull-left time">
						<span>注册时间</span>
					</div>
					<div class="pull-left time">
						<span>推荐码</span>
					</div>
				</div>
				<ul class="table_body">
					<li class="item_box clearfix" v-for="(item,index) in list">
						<div class="pull-left">
							<span>{{item.userName}}</span>
						</div>
						<div class="pull-left">
							<span>{{item.phone}}</span>
						</div>
						<div class="pull-left time">
							<span>{{item.updateAt}}</span>
						</div>
						<div class="pull-left time">
							<span>{{item.recommendation}}</span>
						</div>
					</li>
				</ul>
				<!--分页-->
				<pagination @currentChange="currentChange" :page="page"></pagination>
			</div>
		</el-dialog>
</template>

<script>
	import Pagination from "../common/Pagination.vue"
	export default {
		props: ["show","id","url"],
		data() {
			return {
				list: [],
				filter: {
					phone: ''
				},
				page: {
					total: 0,
					num: 1,
					size: 10
				},
				checkAll: false,
		        isIndeterminate: true,
		        selectList: []
			}
		},
		watch: {
			id () {
				this.initPage();
			}
		},
		computed: {
			
		},
		components: {
			Pagination
		},
		methods: {
			handleCheckAllChange(val) {
				let _this = this;
				_this.isIndeterminate = false;
				_this.list.forEach((item) => {
					item.ischeck = val;
				})
		   	},
		    handleCheckedChange(item) {
		      	let _this = this;
		      	let delIndex = null;
		      	if (item.ischeck) {
		      		_this.selectList.push(item)
		      	}else {
		      		let arr = _this.selectList;
		      		for (let i =0;i<arr.length;i++) {
		      			if (arr[i] == item) {
		      				delIndex = i;
		      			}
		      		}
		      		_this.selectList.splice(delIndex,1);
		      	}
		      	
		    },
		    currentChange (p) {//分页点击
				this.page.num = p;
				this.initPage();
			},
			Inquire () {
				this.initPage();
			},
			diaclose () {
				this.$emit("close")
			},
			
			Confirm() {
				let _this = this;
				let datas = {
					'noticeId': _this.noticeId,
					'type': 'MULTIPLE',
					'hppMobileUserDTOList': _this.selectList
				}
				_this.$post('/api/hpp_notice/sendNotice',datas).then((res) => {
					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "发送成功",
				          type: 'success'
				       });
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
					"formParams":{
						recommendation: _this.id,
						mobile: _this.filter.phone
					}
				};
				_this.$post(_this.url,datas).then((res) => {
					// console.log(res)
					_this.list = res.data.list;
					_this.page.total = parseInt(res.data.total);
				})
			}
			
		},
		mounted () {
			this.initPage();
		}
	}
</script>

<style scoped>

	.title_box {
		text-align: center;
	}
	
	.dialog_body {
		margin-top: -30px;
		padding-bottom: 60px;
	}
	
	.dialog_body h4 {
		font-size: 16px;
		border-left: 3px solid #19183E;
		margin: 20px 0;
		padding: 2px 20px;
	}
	
	.dialog_body .input_box {
		width: 50%;
		line-height: 40px;
		margin-bottom: 10px;
	}
	
	.dialog_body .input_box input,
	.dialog_body .input_box textarea {
		height: 35px;
		width: 50%;
		border: 1px solid #dcdfe6;
		padding: 0 10px;
	}
	
	.dialog_body .input_box textarea {
		height: 60px;
		line-height: 20px;
		padding: 5px 10px;
	}
	
	.dialog_body .input_box span {
		float: left;
		width: 26%;
		text-align: right;
		margin-right: 10px;
	}
	
	.dialog_body .img_box {
		float: left;
		width: 50%;
		height: 160px;
		overflow: hidden;
	}
	
	.dialog_body .img_box img {
		width: auto;
		max-width: 100%;
	}
	
	._dialog-footer {
		text-align: center;
		padding: 20px 0;
	}
	
	._dialog-footer button {
		font-size: 16px;
		color: #fff;
		padding: 3px 30px;
		border-radius: 20px;
		box-shadow: 1px 1px 1px #999;
	}
	
	._dialog-footer button:last-of-type {
		color: #191A41;
		margin-left: 30px;
	}
	
	.filter_box {
		padding: 20px 0;
	}
	.filter_box>div {
		margin-right: 20px;
	}
	
	.table_body{
		
	}
	.item_box {
		border-bottom: 1px solid #ededed;
		padding: 5px 20px;
	}
	.item_box>div {
		width: 120px;
		min-height: 1px;
		text-align: center;
	}
	.item_box>div.time {
		width: 200px;
	}
	.item_box img {
		width: 40px;
		max-height: 40px;
		border-radius: 50%;
	}
	
	
</style>
