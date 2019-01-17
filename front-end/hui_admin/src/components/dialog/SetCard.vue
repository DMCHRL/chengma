<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">设置银行卡</h3>
		
		<div class="input_box">
			<span>开户银行</span>
			<input type="text" name="" id="111" value="" v-model="card.bank" />
			<!--<select v-model="card.bank">
				<option v-for="item in banklist" :value="item.value">{{item.label}}</option>
			</select>-->
		</div>
		<div class="input_box">
			<span>银行分行</span>
			<input type="text" name="" id="11" value="" v-model="card.subBank" />
			
		</div>
		<div class="input_box">
			<span>银行卡号</span>
			<input type="number" name="" id="1" value="" v-model="card.bNum" />
		</div>
		
		<div class="input_box">
			<span>银行所在地</span>
			<input type="text" name="" id="2" value="" v-model="card.location" />
		</div>
		<!--<div class="input_box">
			<span>手机号</span>
			<input type="number" name="" id="4" value="" v-model="card.phone" />
		</div>-->

		<div slot="footer" class="dialog_footer">
			<button @click="confirm">确认绑定</button>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["userid"],
		data() {
			return {
				card: {
					bank: '',
					subBank: '',
					bNum: '',
					location: ''
				}
			}
		},
		watch: {
			userid () {
				this.GetBankMess();
			}
		},
		components: {

		},
		methods: {
			confirm () {
				let _this = this;
				
				if (_this.card.bank == "") {
					_this.$message({
						type: 'warning',
						message: '开户银行不能为空'
					});
					return;
				}
				if (_this.card.subBank == "") {
					_this.$message({
						type: 'warning',
						message: '银行分行不能为空'
					});
					return;
				}
				if (_this.card.bNum == "") {
					_this.$message({
						type: 'warning',
						message: '银行卡号不能为空'
					});
					return;
				}
				if (_this.card.location == "") {
					_this.$message({
						type: 'warning',
						message: '银行所在地不能为空'
					});
					return;
				}
				
				let datas = {
					"userId": _this.userid,
				    "bNum": _this.card.bNum,
				    "bank": _this.card.bank,
				    "subBank": _this.card.subBank,
				    "location": _this.card.location
				}
				_this.$until.superPost("/api//bank_info/saveBankInfo",datas,function (res) {
//					console.log(res)
					if (res.statusCode == '0000') {
						_this.$message({
							type: 'success',
							message: '设置成功'
						});
						_this.$emit("close");
					}else {
						_this.$message({
							type: 'warning',
							message: '设置失败'
						});
					}
				})
			},
			GetBankMess () {
				let _this = this;
				
				_this.card = {
						bank: '',
						subBank: '',
						bNum: '',
						location: ''
				};
				
				_this.$until.superGet("/api/bank_info/get/"+_this.userid,function (res) {
//					console.log(res)
					if (res.statusCode == "0000") {
						
						if (res.data) {
							_this.card = res.data;
						}
						
					}else {
						console.log("获取银行卡失败")
					}
				})
			}
		},
		mounted() {
			this.GetBankMess();
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>