<template>
	<div class="content_box">
		
		<save-qrcode-pay :show="dialogVisible" :dialogTitle="dialogTitle" :store="store" @close="dialogClose"></save-qrcode-pay>
		
		<div class="titling clearfix">
			<span>充值账户管理</span>
			<button @click="NewRecord">新增充值账户</button>
			<!--<span @click="SaveSetting" class="_button pull-right">保存修改</span>-->
		</div>
		
		<div class="list_section">
		<div class="headlist clearfix">
			<span class="pull-left">授权账户名称</span>
			<span class="pull-left time">授权账户收款码</span>
			<span class="pull-left time">转账备注</span>
			<span class="pull-left">操作</span>
			<span class="pull-right">是否使用</span>
		</div>
		<ul class="bodylist">
			<li class="clearfix" v-for="item in list">
				<span class="pull-left">{{item.shopName}}</span>
				<span class="pull-left time">
					<img :src="item.img"/>
				</span>
				<span class="pull-left time">{{item.text}}</span>
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
	
	import SaveQrcodePay from "../dialog/SaveQrcodePay"
	
	export default {
		data() {
			return {
				dialogVisible: false,
				list: [],
				value2: false,
				dialogTitle: "新增账户充值通道",
				store: {
					shopName: '',
					text: '',
					img: '',
				}
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
			SaveQrcodePay
		},
		methods: {
			SaveSetting (val) {
				let _this = this;
				
				_this.$until.superGet("/api/payment_img/setImgFlag/"+val,function (res) {
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
				this.dialogTitle = "编辑账户充值通道";
				this.dialogVisible = true;
			},
			NewRecord () {
				this.store = {
					shopName: '',
					text: '',
					img: '',
				}
				this.dialogTitle = "新增账户充值通道";
				this.dialogVisible = true;
			},
			Delete (id) {
				let _this = this;

				_this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$until.superGet("/api/payment_img/delete/"+id,function (res) {
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
				
				_this.$until.superGet("/api/payment_img/findAll",function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						
						let list = res.data.list;
						if (list) {
							list.forEach(function (item) {
								item.flager = (item.flag == '0' ? false:true);
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
	.bodylist li>span{
		height: auto;
		min-height: 30px;
		line-height: 100px;
	}
	.bodylist li>span img {
		height: 100px;
	}
	.headlist>span,
	.bodylist li>span {
		width: 15%;
	}
	.headlist>span.time,
	.bodylist li>span.time {
		width: 20%;
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