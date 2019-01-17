<template>
	<div class="list_section">
		<div class="headlist clearfix">
			<span class="pull-left">账户</span>
			<span class="pull-left time">名称</span>
			<span class="pull-left">归属</span>
			<span class="pull-left time pointer" @click="Sort ('createAt')">创建时间<i class="el-icon-d-caret"></i></span>
			<span class="pull-left time">账户组</span>
			<span class="pull-left pointer" @click="Sort ('balance')">账户余额<i class="el-icon-d-caret"></i></span>
			<span class="pull-left pointer" @click="Sort ('profit')">盈利<i class="el-icon-d-caret"></i></span>
			<span class="pull-left small pointer" @click="Sort ('totalCount')">交易量<i class="el-icon-d-caret"></i></span>
			<span class="pull-left small pointer" @click="Sort ('totalLots')">交易手数<i class="el-icon-d-caret"></i></span>
			<span class="pull-left pointer" @click="Sort ('margin')">已用保证金<i class="el-icon-d-caret"></i></span>
			<span class="pull-left pointer" @click="Sort ('freeMargin')">可用保证金<i class="el-icon-d-caret"></i></span>
			<span class="pull-left pointer" v-if="user.department == 'admin'" @click="Sort ('successRate')">胜率<i class="el-icon-d-caret"></i></span>
			<span class="pull-left small">交易状态</span>
			<span class="pull-left small" v-if="user.department == 'admin'">是否反向</span>
			<span class="pull-right" v-if="user.department == 'admin' || user.department == 'service'">操作</span>
		</div>
		<ul class="bodylist">
			<li class="clearfix" v-for="item in list">
				<span class="pull-left">{{item.account}}</span>
				<span class="pull-left time">{{item.accountName}}</span>
				<span class="pull-left">{{item.parentName}}</span>
				<span class="pull-left time">{{item.createAt}}</span>
				<span class="pull-left time" >
					<i v-text="item.group == 'demoTX' ? '模拟账户' : (item.group == 'TXA2'?'体验账户':'真实账户')"></i>
					<i>{{item.eaGroupName}}</i>
				</span>
					
				<span class="pull-left">{{item.balance}}</span>
				<span class="pull-left">{{item.profit}}</span>
				<span class="pull-left small">{{item.totalCount}}</span>
				<span class="pull-left small">{{item.totalLots}}</span>
				<span class="pull-left">{{item.margin}}</span>
				<span class="pull-left">{{item.freeMargin}}</span>
				<!--胜率-->
				<span class="pull-left" v-if="user.department == 'admin'">{{item.successRate}}%</span>
				
				<span class="pull-left small" v-text="item.enableTrade == 'Y' ? '正常':'禁止'"></span>
				<!--方向-->
				<span class="pull-left small" v-if="user.department == 'admin'" v-text="item.comment == 'Y' ? '是':'否'"></span>
				<div class="pull-right" v-if="user.department == 'admin' || user.department == 'service'">
					
					<!--<button @click="Edit(item)">编辑</button>
					<button @click="Delete(item.id)" v-if="user.department == 'admin'">删除</button>-->
					
					<el-popover
					  placement="bottom"
					  width="100"
					  trigger="click"
					  popper-class="popover_box">
					  
					  	<button 
					  		@click="Edit(item)" 
					  		v-if="user.department == 'admin' || user.department == 'service'" >
					  		编辑账号
					  	</button>
						<button class="delete" 
							@click="Delete(item.id)" 
							v-if="user.department == 'admin'">
							删除
						</button>
					  
					  <el-button  type="primary" icon="el-icon-setting" slot="reference"></el-button>
					</el-popover>
					
				</div>
			</li>
			
			<li class="clearfix zongji">
				<span class="pull-left">总计</span>
				<span class="pull-left time">--</span>
				<span class="pull-left">--</span>
				<span class="pull-left time">--</span>
				<span class="pull-left time">--</span>
					
				<span class="pull-left">{{totalbalance}}</span>
				<span class="pull-left">{{totalprofit}}</span>
				<span class="pull-left small">{{totaltotalCount}}</span>
				<span class="pull-left small">{{totaltotalLots}}</span>
				<span class="pull-left">{{totalmargin}}</span>
				<span class="pull-left">{{totalfreeMargin}}</span>
				<!--胜率-->
				<span class="pull-left" v-if="user.department == 'admin'">--</span>
				
				<span class="pull-left small">--</span>
				<!--方向-->
				<span class="pull-left small" v-if="user.department == 'admin'">--</span>
				<div class="pull-right" v-if="user.department == 'admin' || user.department == 'service'">
					--
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
		props: ["list"],
		data() {
			return {
				isdesc: true
			}
		},
		computed: {
			user () {
				let user = JSON.parse(localStorage.getItem("user"));
				return user;
			},
			totalbalance () {
				return this.getTotal ('balance');
			},
			totalprofit () {
				return this.getTotal ('profit');
			},
			totaltotalCount () {
				return this.getTotal ('totalCount');
			},
			totaltotalLots () {
				return this.getTotal ('totalLots');
			},
			totalmargin () {
				return this.getTotal ('margin');
			},
			totalfreeMargin () {
				return this.getTotal ('freeMargin');
			}
		},
		components: {
			
		},
		methods: {
			getTotal (keys) {
				let list = this.list;
				if (list.length) {
					let sum = 0;
					list.forEach(function (item,index,input) {
					    sum += Number(item[keys]);
					})
					return sum.toFixed(2);
				}else {
					return '--'
				}
			},
			Sort (para) {
				this.isdesc = !this.isdesc;
				let datas = {
					"orderByColumn": para,
					"sort": this.isdesc ? 'DESC':'ASC' //"DESC"//"ASC"
				};
				this.$emit("Sort",datas)
			},
			Edit (item) {
				this.$emit("Edit",item)
			},
			Delete (id) {
//				console.log(id)
				let _this = this;
				
				_this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.$until.superDelete("/api/tlb-account/deleteTlbAccount/"+id,function (res) {
						//console.log(res)
						if(res.statusCode == '0000') {
							_this.$message({
								type: 'success',
								message: '删除成功!'
							});
							_this.$emit("close");
						}
					})
				}).catch(() => {
//		          _this.$message({
//		            type: 'info',
//		            message: '已取消删除'
//		          });
				});
			}
		},
		mounted () {
		}
	}
</script>

<style scoped>
	.headlist span,
	.bodylist li span {
		width: 7%;
	}
	
	.headlist span.time,
	.bodylist li span.time {
		width: 8.5%;
	}
	.headlist span.small,
	.bodylist li span.small {
		width: 5%;
	}
	.headlist span:last-of-type,
	.bodylist li>div {
		width: 5%;
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