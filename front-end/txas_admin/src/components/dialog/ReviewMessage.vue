<template>
	<!--对话框-->
		<el-dialog :visible="dialogFormVisible" :show-close="false" width="60%" @close="diaclose">
			<div slot="title" class="_dialog-title clearfix">
				<div class="pull-right">
					<span>任务内容：同名开户申请</span>
					<span>申请人：{{dataReview.username}}</span>
				</div>
			</div>
			<div class="dialog_body">
				<div class="sec_dialog_1">
					<h4>个人基本资料</h4>
					<div class="clearfix">
						<div class="input_box pull-left">
							<span>姓名</span>
							<input type="text" name="" id="" value="" v-model="dataReview.username" />
						</div>
						<div class="input_box pull-left">
							<span>手机</span>
							<input type="text" name="" id="" value="" v-model="dataReview.phone" />
						</div>
						<div class="input_box pull-left">
							<span>邮箱</span>
							<input type="text" name="" id="" value="" v-model="dataReview.email" />
						</div>
						<div class="input_box pull-left">
							<span>地址</span>
							<textarea type="text" name="" v-model="dataReview.address"></textarea>
						</div>
						
					</div>
				</div>
				<div class="sec_dialog_3">
					<h4>身份证明</h4>
					<div class="clearfix">
						<div class="input_box pull-left">
							<span>身份证明类型</span>
							<input type="text" name="" id="" value="" v-model="dataReview.idType" />
						</div>
						<div class="input_box pull-left">
							<span>身份证明号码</span>
							<input type="text" name="" id="" value="" v-model="dataReview.idNumber" />
						</div>
						<div class="input_box pull-left">
							<span>身份证明正面</span>
							<div class="img_box">
								<img :src="dataReview.idPositive" />
							</div>
						</div>
						<div class="input_box pull-left">
							<span>身份证明反面</span>
							<div class="img_box">
								<img :src="dataReview.idOther" />
							</div>
						</div>

					</div>
				</div>
				<div class="sec_dialog_2">
					<h4>财务信息</h4>
					<div class="clearfix">
						<div class="input_box pull-left">
							<span>银行卡号</span>
							<input type="text" name="" id="" value="" v-model="dataReview.cardNumber" />
						</div>
						<div class="input_box pull-left">
							<span>开户银行</span>
							<input type="text" name="" id="" value="" v-model="dataReview.openingBank" />
						</div>
						<div class="input_box pull-left">
							<span>银行支行</span>
							<input type="text" name="" id="" value="" v-model="dataReview.branch" />
						</div>
						<div class="input_box pull-left">
							<span>银行卡正面</span>
							<div class="img_box">
								<img :src="dataReview.cardPositive" />
							</div>
						</div>
					</div>
				</div>
				<div class="sec_dialog_2">
					<h4>客服添加</h4>
					<div class="clearfix">
						<div class="input_box pull-left">
							<span>账户类型</span>
							<el-select v-model="account_type" placeholder="任务事项" class="btn-group">
								<el-option v-for="item in options_type" :key="item.value" :label="item.label" :value="item.value">
								</el-option>
							</el-select>
						</div>
						<div class="input_box pull-left">
							<span>推荐人</span>
							<input type="text" name="" disabled="disabled" id="" value="" v-model="dataReview.recommendationName" />
						</div>
					</div>
				</div>
			</div>
			<div slot="footer" class="_dialog-footer">
				<button v-if="dataReview.status == 'APPLYING'" @click="Update(dataReview.id)">保存</button>
				<button v-if="dataReview.status == 'APPLYING'" @click="UpdateAndPass(dataReview.id)">保存并通过</button>
				<button @click="diaclose">关闭</button>
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
				_this.$until.superPost('/api/tlb-user/updateTlbUser', datas ,function(res) {
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
			Passed(id) { //处理 审核通过
				let _this = this;
				let datas = {
					"group": _this.account_type,
					"address": _this.dataReview.address
				};
				_this.$until.superPost('/api/tlb-user/approved/'+ id, datas ,function(res) {
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
			UpdateAndPass (id) {
				let _this = this;
				let datas = _this.dataReview;
				_this.$until.superPost('/api/tlb-user/updateTlbUser', datas ,function(res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						_this.Passed(id);
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'warning'
				        });
					}
				})
			}
			
		},
		mounted () {
			
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
	
	.dialog_body .input_box {
		width: 50%;
		line-height: 40px;
		margin-bottom: 10px;
	}
	
	.dialog_body .input_box input,
	.dialog_body .input_box textarea {
		height: 35px;
		width: 60%;
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
		width: 60%;
		height: 160px;
		overflow: hidden;
	}
	
	.dialog_body .img_box img {
		width: auto;
		max-width: 100%;
		max-height: 100%;
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
	
	._dialog-footer button:last-of-type {
		background-color: transparent;
		border: 1px solid #191A41;
		color: #191A41;
		padding: 4px 20px;
		margin-left: 30px;
	}
</style>