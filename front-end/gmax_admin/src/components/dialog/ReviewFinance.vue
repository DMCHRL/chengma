<template>
	<!--对话框-->
		<el-dialog :visible="dialogFormVisible" :show-close="false" width="60%" @close="diaclose">
			<div slot="title" class="_dialog-title clearfix">
				<div class="pull-right">
					<span>任务内容：<i v-text="dataReview.fundType == 'IN' ? '充值' : (dataReview.fundType == 'OUT' ? '提现' : '佣金内转')"></i></span>
					<span>申请账户：{{dataReview.account}}</span>
				</div>
			</div>
			<div class="dialog_body">
				<div class="sec_dialog_1">
					<h4>任务详情</h4>
					<div class="item_box clearfix">
						<span class="pull-left">账号：</span>
						<span class="pull-right">{{dataReview.account}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">任务内容：</span>
						<span class="pull-right">
							<i v-text="dataReview.fundType == 'IN' ? '充值' : (dataReview.fundType == 'OUT' ? '提现' : '佣金内转')"></i>
						</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">美元金额：</span>
						<span class="pull-right">{{dataReview.amount}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">人民币金额：</span>
						<span class="pull-right">{{dataReview.cnyAmount}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">申请时间：</span>
						<span class="pull-right">{{dataReview.createAt}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">银行收款账号：</span>
						<span class="pull-right">{{dataReview.bankNum}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">转账备注：</span>
						<span class="pull-right">{{dataReview.remarkNum}}</span>
					</div>
					<div class="item_box clearfix">
						<span class="pull-left">状态：</span>
						<span class="pull-right">
							<i v-text="dataReview.status == 'APPLYING'? '审核中': dataReview.status == 'PASSED'? '已通过':'已拒绝'"></i>
						</span>
					</div>
				</div>
				
			</div>
			<div slot="footer" class="_dialog-footer">
				<button v-if="dataReview.status == 'APPLYING'" @click="PassAppli(dataReview.id)">通过</button>
				<button v-if="dataReview.status == 'APPLYING'" class="red" @click="RefuseAppli(dataReview.id)">拒绝</button>
				<button class="can" @click="diaclose">关闭</button>
			</div>
		</el-dialog>
</template>

<script>
	export default {
		props: ["dialogFormVisible","datas"],
		data() {
			return {
				account_type: 'TXA3',
				options_type: [
					{
						label: '真实账户',
						value: 'TXA3'
					},
					{
						label: '体验账户',
						value: 'TXA2'
					},
					{
						label: '模拟账户',
						value: 'demoTX'
					},
				],
			}
		},
		computed: {
			dataReview () {
				return JSON.parse(JSON.stringify(this.datas));
			}
		},
		components: {
			
		},
		methods: {
			diaclose () {
				this.$emit("close")
			},
			Update(id) {//处理 更改用户信息
				let _this = this;
				let datas = _this.dataReview;
				_this.post('/api/tlb-user/updateTlbUser', datas ,function(res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
						 _this.$emit("close")
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
			},
			PassAppli (id) {
				let _this = this;
				_this.post("/api/tlb-fund-apply/pass/"+id,{},function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
				        _this.$emit("close")
					}
				})
			},
			RefuseAppli (id) {
				let _this = this;
				_this.post("/api/tlb-fund-apply/reject/"+id,{},function (res) {
					////console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "操作成功",
				          type: 'success'
				        });
				        _this.$emit("close")
					}
				})
			}
		},
		mounted () {
			// console.log(this.dataReview)
		}
	}
</script>

<style scoped>
	/*审核弹窗*/
	
	._dialog-title {
		background-color: #19183e;
		margin: -20px -20px -10px;
		padding: 0 20px;
		border-radius: 2px 2px 0px 0px;
	}
	
	._dialog-title span {
		color: #fff;
		line-height: 40px;
		margin-right: 20px;
	}
	
	.dialog_body {
		margin-top: -30px;
	}
	
	.dialog_body h4 {
		font-size: 16px;
		border-left: 3px solid #19183E;
		margin: 20px 0;
		padding: 2px 20px;
	}
	
	.item_box {
		width: 400px;
		margin: 0 auto;
		padding-bottom: 5px;
	}
	
	._dialog-footer {
		text-align: center;
		padding: 20px 0;
	}
	
	._dialog-footer button {
		font-size: 16px;
		color: #fff;
		padding: 5px 30px;
		background-color: #191A41;
		border-radius: 20px;
	}
	._dialog-footer button.red {
		background-color: #FF3C3C;
	}
	
	._dialog-footer button.can {
		background-color: transparent;
		border: 1px solid #191A41;
		color: #191A41;
		padding: 4px 20px;
	}
</style>