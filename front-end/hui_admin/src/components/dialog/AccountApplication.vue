<template>
	<!--对话框-->
		<el-dialog :visible="dialogFormVisible" :show-close="false" width="60%" @close="diaclose">
			<div slot="title" class="_dialog-title clearfix">
				<div class="pull-right">
					<span>任务内容：同名开户申请</span>
					<!--<span>申请人：{{dataReview.username}}</span>-->
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
							<span>推荐码</span>
							<input type="text" name="" id="" value="" v-model="dataReview.recommendation" />
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
							<label class="uplond_box">
								<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<img class="icon_img" v-show="!images.bank_p" src="../../assets/img/acc_1.png"/>
								<img class="con_img" v-show="images.card_p" :src="images.card_p"/>
							</label>
						</div>
						<div class="input_box pull-left">
							<span>身份证明反面</span>
							<label class="uplond_box">
								<input @change="onFileChange2" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<img class="icon_img" v-show="!images.bank_p" src="../../assets/img/acc_1.png"/>
								<img class="con_img" v-show="images.card_n" :src="images.card_n"/>
							</label>
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
							<label class="uplond_box">
								<input @change="onFileChange3" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<img class="icon_img" v-show="!images.bank_p" src="../../assets/img/acc_1.png"/>
								<img class="con_img" v-show="images.bank_p" :src="images.bank_p"/>
							</label>
						</div>
					</div>
				</div>
				<!--<div class="sec_dialog_2">
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
							<span>身份证地址</span>
							<textarea type="text" name="" v-model="dataReview.address"></textarea>
						</div>
					</div>
				</div>-->
			</div>
			<div slot="footer" class="_dialog-footer">
				<button @click="Confirm">立即申请</button>
				<button @click="diaclose">关闭</button>
			</div>
		</el-dialog>
</template>

<script>
	export default {
		props: ["dialogFormVisible"],
		data() {
			return {
				dataReview: {
					
				},
				images: {
					card_p: '',
					card_n: '',
					bank_p: ''
				},
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
			
		},
		components: {
			
		},
		methods: {
			onFileChange1(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.card_p = base64Codes;
				});
			},
			onFileChange2(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.card_n = base64Codes;
				});
			},
			onFileChange3(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.bank_p = base64Codes;
				});
			},
			diaclose () {
				this.$emit("close")
			},
			
			Confirm() {
				let _this = this;
				
				console.log(_this.dataReview)
				
				return;
				let datas = {
					"group":"demoTX",
					"address": _this.dataReview.address
				};
				_this.$until.superPost('/api/tlb-user/approved/'+ id, datas ,function(res) {
					console.log(res)
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
	
	.uplond_box {
		position: relative;
		text-align: center;
		width: 50%;
		height: 180px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	
	.uplond_box  span {
		line-height: 90px;
		color: #666;
		font-size: 14px;
	}
	
	.uplond_box  img.con_img {
		width: 100%;
		height: auto;
	}
	.uplond_box  img.icon_img {
		width: 36px;
		height: 36px;
		position: absolute;
		left: 0;
		right: 0;
		bottom: 0;
		top: 0;
		margin: auto;
	}
</style>