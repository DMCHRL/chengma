<template>
	<div class="list_section">
		<div class="headlist clearfix myback2">
			<!--<span class="pull-left">ID</span>-->
			<span class="pull-left time">登录名</span>
			<span class="pull-left">名称</span>
			<!--<span class="pull-left">身份</span>-->
			<!--<span class="pull-left">返佣层级</span>-->
			<span class="pull-left ">联系电话</span>
			<span class="pull-left time">邮箱</span>
			<!-- <span class="pull-left">推荐码</span>
			<span class="pull-left">推荐人数</span> -->
			<span class="pull-left">权限</span>
			<span class="pull-left time">注册时间</span>
			<!--<span class="pull-left">上级</span>-->
			<!--<span class="pull-left small pointer" @click="Sort('proxyCount')">下级代理<i class="el-icon-d-caret"></i></span>-->
			<!--<span class="pull-left small pointer" @click="Sort('accountCount')">关联账户<i class="el-icon-d-caret"></i></span>-->
			<span class="pull-right" v-if="user.department == 'admin' || user.department == 'service'">操作</span>
		</div>
		<ul class="bodylist">
			<li class="clearfix" v-for="item in lists">
				<!--<span class="pull-left">DFS3</span>-->
				<span class="pull-left time">{{item.login}}</span>
				<span class="pull-left">{{item.firstName}}</span>

				<!--<span class="pull-left">{{item.lastName}}</span>-->

				<!--<span class="pull-left"> </span>-->
				<span class="pull-left">{{item.mobile}}</span>
				<span class="pull-left time">{{item.email}}</span>
				<!-- <span class="pull-left">{{item.charNo}}</span>
				<span class="pull-left">{{item.recommendationTotal}}</span> -->

				<span class="pull-left">{{item.department | idName}}</span>
				<span class="pull-left time">{{item.createdDate}}</span>
				<!--<span class="pull-left">{{item.parentName}}</span>-->
				<!--<span class="pull-left small">{{item.proxyCount}}</span>-->
				<!--<span class="pull-left small">{{item.accountCount}}</span>-->
				<div class="pull-right" v-if="user.department == 'admin' || user.department == 'service'">
					<!--<button
						v-if="user.department == 'admin' && item.department != 'user' && item.department != 'admin'"
						@click="brokerageSet(item.department,item.id)">佣金设置</button>
					<button @click="Edit(item)">编辑</button>
					<button v-if="user.department == 'admin'" @click="Delete(item.id)" >删除</button>-->

					<el-popover placement="bottom" width="100" trigger="click" popper-class="popover_box">

						<!--<button
							v-if="user.department == 'admin'"
							@click="brokerageSet(item.department,item.id)">
							佣金设置
						</button>-->
						<!--<button
							v-if="user.department == 'admin' || user.department == 'service'"
							@click="CardSet(item.id)">
							银行卡设置
						</button>-->

						<button
							v-if="user.department == 'admin' || user.department == 'service'"
							@click="Edit(item)">
							编辑用户
						</button>
						<!--<button
							v-if="user.department == 'admin' || user.department == 'service'"
							@click="addAccount(item.id)">
							新增账户
						</button>-->
						<button class="delete"
							v-if="user.department == 'admin'" @click="Delete(item.id)">
							删除
						</button>

						<el-button type="primary" icon="el-icon-setting" slot="reference"></el-button>
					</el-popover>

				</div>
			</li>

		</ul>
		<div class="trip_box" v-show="!list.length">
			<img src="../../assets/img/nodata_2.png" />
			<p>暂无数据</p>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["list", "isib"],
		data() {
			return {
				isdesc: true
			}
		},
		filters: {
			idName: function(value) {
				// 身份(service 开户管理员) (operate 运营管理员) (teach 报教管理员)
				switch (value){
					case 'admin':
						return '超级管理员'
						break;
					case 'service':
						return '开户管理员'
						break;
					case 'operate':
						return '运营管理员'
						break;
					case 'teach':
						return '报教管理员'
						break;
          case 'strategy':
            return '策略管理员'
            break;
          case 'mt4':
            return '云端管理员'
            break;
          case 'mobile':
            return 'App用户'
            break;
					default:
						return '未知'
						break;
				}
			}
		},
		computed: {
			lists() {
				if(this.list.length > 0) {
					return this.list;
				}
			},
			user() {
				let userStr = localStorage.getItem("user");
				if(userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return {
						department: 'user'
					}
				}
			}
		},
		components: {

		},
		methods: {
			Sort (para) {//排序
				this.isdesc = !this.isdesc;
				let datas = {
					"orderByColumn": para,
					"sort": this.isdesc ? 'DESC':'ASC' //"DESC"//"ASC"
				};
				this.$emit("Sort",datas)
			},
			addAccount(id) {
				this.$emit("addAccount", id);
			},
			Edit(item) {
				this.$emit("Edit", item);
			},
			CardSet(id) {
				this.$emit("CardSet", id)
			},
			brokerageSet(de, id) { //佣金设置
				let _this = this;
				_this.$until.superGet("/api/tlb-commission/getConfiguration/" + id, function(res) {
					//console.log(res)
					_this.brokeSet(de, id, res.data);
				})
			},
			brokeSet(de, id, data) {
				let _this = this;

				let placeholder = "例如：10";
				let title = '佣金奖励设置';
				let getValue = "";

				if(data) {
					getValue = data.lotAmount;
				}
				_this.$prompt('', title, {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputPlaceholder: placeholder,
					inputValue: getValue,
				}).then(({
					value
				}) => {

					let datas = {};
					datas = {
						"userId": id, //编辑userid
						"lotAmount": value
					}

					_this.$until.superPost("/api/tlb-commission/setConfiguration", datas, function(res) {
						//			        		console.log(res)
						if(res.statusCode == "0000") {
							_this.$message({
								type: 'success',
								message: '设置成功'
							});
						} else {
							_this.$message({
								type: 'warning',
								message: '设置失败'
							});
						}
					})

				}).catch(() => {

				});
			},
			Delete(id) {
				//				console.log(id)
				let _this = this;

				_this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$strike("/api/user/delete/" + id).then((res) => {
						//console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.$emit("close");
						}
					})
				}).catch(() => {});
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.headlist span,
	.bodylist li span {
		width: 140px;
	}
	.headlist span.time,
	.bodylist li span.time {
		width: 200px;
	}

	.headlist span:last-of-type,
	.bodylist li>div {
		width: 200px;
	}


	.bodylist li>div button:last-of-type {
		background-color: transparent;
		color: #409eff;
		border: none
	}

	.popover_box button {
		font-size: 14px;
		color: #6562b6;
		width: 100%;
		height: 30px;
		margin-bottom: 10px;
		background-color: transparent;
		border: 1px solid #6562b6;
		border-radius: 20px;
	}

	.popover_box button.delete {
		color: #f14b3b;
		border: 1px solid #f14b3b;
	}
</style>
