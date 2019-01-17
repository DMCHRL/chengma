<template>
	<div class="dialog_box"> 
		<h3 slot="title" class="dialog_title">绑定银行卡</h3>
		
		<div class="input_box">
			<span>银行</span>
			<select v-model="card.bank">
				<option v-for="item in banklist" :value="item.value">{{item.label}}</option>
			</select>
		</div>
		<div class="input_box">
			<span>银行卡号</span>
			<input type="number" name="" id="1" value="" v-model="card.num" />
		</div>
		
		<div class="input_box">
			<span>姓名</span>
			<input type="text" name="" id="2" value="" v-model="card.name" />
		</div>
		<div class="input_box">
			<span>身份证号</span>
			<input type="text" name="" id="3" value="" v-model="card.zheng" />
		</div>
		<div class="input_box">
			<span>手机号</span>
			<input type="number" name="" id="4" value="" v-model="card.phone" />
		</div>

		<div slot="footer" class="dialog_footer">
			<button @click="confirm">确认</button>
		</div>
	</div>
</template>

<script>
	export default {
		props: ["banklist"],
		data() {
			return {
				card: {
					phone: '',
					name: '',
					zheng: '',
					num: '',
					bank: ''
				},
				bindid: ''
			}
		},
		components: {

		},
		methods: {
			checkResult () {
				let _this = this;
				let bindid = _this.bindid;
				if (!bindid) {
					bindid = localStorage.getItem("bindid");
				}
				_this.fetch("/api/bind-bank/get/"+ bindid,function (res) {
//					//console.log(res)
					if (res.data.bindSuccess == "Y") {
						_this.$message({
				          message: "绑卡成功",
				          type: 'success'
				        });
					}else {
						_this.$message({
				          message: "绑卡失败",
				          type: 'warning'
				        });
				        setTimeout(function () {
				        	_this.checkResult();
				        },30000)
					}
				})
			},
			sendCode (val) {
//				//console.log(val)
				let _this = this;
				let datas = {
					"banksn": _this.card.num,
					"smscode": val
				}
				_this.post("/api/xxpay/bind_confirm",datas,function (res) {
//					//console.log(res)
					if (res.data.code == '0000') {
						_this.$message({
				          message: res.data.msg,
				          type: 'success'
				        });
				        setTimeout(function () {
				        	_this.checkResult();
				        },30000)
					}else {
						_this.$message({
				          message: res.data.msg,
				          type: 'warning'
				        });
					}
				})
			},
			confirm () {
				let _this = this;
				let datas = {
					"banksn": _this.card.num,
					"khname": _this.card.name,
					"idtype": "ZR01",
					"idcardsn": _this.card.bank,
					"mobile": _this.card.phone
				}
				_this.post("/api/xxpay/bind",datas,function (res) {
//					//console.log(res)
					if (res.statusCode == '0000') {
						if (res.data.code == "0000") {
							
							_this.bindid = res.data.bindBank.id;
							localStorage.setItem("bindid",res.data.bindBank.id);
							
							_this.$prompt('请输入短信验证码', '', {
					          confirmButtonText: '确定',
					          cancelButtonText: '取消',
					          
					        }).then(({ value }) => {
					        	
//					          this.$message({
//					            type: 'success',
//					            message: '你的邮箱是: ' + value
//					          });
					          
					          _this.sendCode(value);
					          
					        }).catch(() => {
					          //console.log('取消输入')    
					        });
						}else {
							_this.$message({
					          message: res.data.msg,
					          type: 'warning'
					        });
						}
					}
				})
			}
		},
		mounted() {
			
		}
	}
</script>

<style scoped>
	@import url("../../assets/css/dialog.css");
</style>