<template>
	<div class="content_box">
		
		<save-pay-way :show="dialogVisible" :dialogTitle="dialogTitle" :store="store" @close="dialogClose"></save-pay-way>
		
		<div class="titling clearfix">
			<span>国际通道管理</span>
			<button @click="NewRecord">新增国际通道</button>
		</div>
		
		<div class="list_section">
		<div class="headlist clearfix">
			<span class="pull-left">商户名称</span>
			<span class="pull-left">收款人名称</span>
			<span class="pull-left ">账户类型</span>
			<span class="pull-left ">账户号码</span>
			<span class="pull-left ">银行名称</span>
			<span class="pull-left">银行地址</span>
			<span class="pull-left">银行编码</span>
			<span class="pull-left">汇款编码(备注)</span>
			<span class="pull-left">操作</span>
			<span class="pull-right">是否使用</span>
		</div>
		<ul class="bodylist">
			<li class="clearfix" v-for="item in list">
				<span class="pull-left">{{item.company}}</span>
				<span class="pull-left">{{item.userName}}</span>
				<span class="pull-left ">{{item.accountType}}/{{item.accountTypeEn}}</span>
				<span class="pull-left ">{{item.account}}</span>
				<span class="pull-left">{{item.bankName}}</span>
				<span class="pull-left">{{item.address}}/{{item.addressEn}}</span>
				<span class="pull-left">{{item.bankCode}}</span>
				<span class="pull-left">{{item.swiftCode}}</span>
				
				<span class="pull-left">
					  <el-button @click="EditRecord(item)" type="primary" icon="el-icon-edit" circle></el-button>
					  <el-button @click="Delete(item.id)" type="danger" icon="el-icon-delete" circle></el-button>
				</span>
				<span class="pull-right">
					<el-switch
					  v-model="item.flager"
					  active-color="#13ce66"
					  inactive-color="#ff4949"
					  @change="SaveSetting(item.id)">
					</el-switch>
				</span>
			</li>
		</ul>
		<div class="trip_box" v-show="!list.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无数据</p>
		</div>
		
	</div>
		
	</div>
</template>

<script>
	
	import SavePayWay from "../dialog/SavePayWay"
	
	export default {
		data() {
			return {
				dialogVisible: false,
				list: [],
				value2: false,
				dialogTitle: "新增通道",
				store: {}
			}
		},
		computed: {
			user () {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
				    return user;
				}else {
					return {department: 'user'}
				}
			}
		},
		components: {
			SavePayWay
		},
		methods: {
			SaveSetting (val) {
				let _this = this;
				
				_this.$until.superGet("/api/receivables_en/changFlag/"+val,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
							type: 'success',
							message: '设置成功'
						});
						_this.initPage();
					}
				})
			},
			EditRecord (item) {
//				console.log(item)
				this.store = item;
				this.dialogTitle = "编辑通道";
				this.dialogVisible = true;
			},
			NewRecord () {
				this.store = {}
				this.dialogTitle = "新增通道";
				this.dialogVisible = true;
			},
			Delete (id) {
				let _this = this;

				_this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$until.superGet("/api/receivables_en/delete/"+id,function (res) {
						//console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.initPage();
						}
					})
				}).catch(() => {});
				
			},
			dialogClose () {
				this.dialogVisible=false;
				this.initPage();
			},
			
			initPage () {
				let _this = this;
				
				_this.$until.superGet("/api/receivables_en/findAll",function (res) {
					// console.log(res)
					if (res.statusCode == "0000") {
						
						let list = res.data.list;
						if (list) {
							list.forEach(function (item) {
								item.flager = (item.flag == 'N' ? false:true);
							})
						}
						
						_this.list = res.data.list;
						
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
  .bodylist li {
    padding: 10px 0;
  }
	.bodylist li>span{
		height: auto;
		min-height: 30px;
	}
	.headlist>span,
	.bodylist li>span {
		width: 9.5%;
	}
	.headlist>span.time,
	.bodylist li>span.time {
		width: 13%;
	}
	
	.bodylist li>span.up {
		color: #f14b3b;
	}
	
	.bodylist li>span.down {
		color: #17cb8a;
	}
	._button {
		margin-right: 40px;
		color: #FF0000;
		border: 1px dotted #FF0000;
		border-radius: 5px;
		display: inline-block;
		line-height: 20px;
		padding: 5px 20px;
		margin-top: 10px;
	}
	._button:active {
		background-color: #F0F0F0;
	}
</style>