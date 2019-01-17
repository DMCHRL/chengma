<template>
	<div class="login_section" :style="styleString">
		<div class="form_box">
			<h3>开户申请</h3>
			<el-row :gutter="20">
				<el-col :span="12">
					<div class="input_box">
						<span>真实姓名</span>
						<el-input v-model="userMess.name" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 1">姓名不能为空</span>
					</div>
					<div class="input_box">
						<span>手机号</span>
						<el-input v-model="userMess.phone" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 3">手机号不能为空</span>
					</div>
				</el-col>
				<el-col :span="12">
					<div class="input_box">
						<span>邮箱</span>
						<el-input v-model="userMess.email" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 2">邮箱不能为空</span>
					</div>
					<div class="input_box">
						<span>地址</span>
						<el-input v-model="userMess.address" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 4">地址不能为空</span>
					</div>
				</el-col>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="12">
					<div class="input_box">
						<span>证件类型</span>
						<el-select v-model="card_type" placeholder="请选择">
							<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
							</el-option>
						</el-select>
					</div>
					<div class="input_box">
						<span>证件正面</span>
						<label class="uplond_box" @click="hideTrip">
							<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
							<img class="icon_img" v-show="!images.card_p" src="../assets/img/acc_1.png"/>
							<img class="con_img" v-show="images.card_p" :src="images.card_p"/>
						</label>
						<span class="trip_text" v-if="tripNum == 5">证件正面不能为空</span>
					</div>
				</el-col>
				<el-col :span="12">

					<div class="input_box">
						<span>证件编号</span>
						<el-input v-model="userMess.cardNum" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 6">证件编号不能为空</span>
					</div>
					
					<div class="input_box">
						<span>证件反面</span>
						<label class="uplond_box" @click="hideTrip">
							<input @change="onFileChange2" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
							<img class="icon_img" v-show="!images.card_n" src="../assets/img/acc_1.png"/>
							<img class="con_img" v-show="images.card_n" :src="images.card_n"/>
						</label>
						<span class="trip_text" v-if="tripNum == 7">证件反面不能为空</span>
					</div>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="12">
					<div class="input_box">
						<span>银行卡号</span>
						<el-input v-model="userMess.bankNum" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 8">银行卡号不能为空</span>
					</div>
					<div class="input_box">
						<span>开户银行</span>
						<el-input v-model="userMess.bank" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 9">开户银行不能为空</span>
					</div>
					<div class="input_box">
						<span>银行分行</span>
						<el-input v-model="userMess.bank2" placeholder="" @focus="hideTrip"></el-input>
						<span class="trip_text" v-if="tripNum == 10">银行分行不能为空</span>
					</div>
				</el-col>
				<el-col :span="12">
					<div class="input_box">
						<span>银行卡正面</span>
						<label class="uplond_box" @click="hideTrip">
							<input @change="onFileChange3" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
							<img class="icon_img" v-show="!images.bank_p" src="../assets/img/acc_1.png"/>
							<img class="con_img" v-show="images.bank_p" :src="images.bank_p"/>
						</label>
						<span class="trip_text" v-if="tripNum == 11">银行卡正面不能为空</span>
					</div>
				</el-col>
			</el-row>

			<div class="btn_box">
				<span @click="back" class="el-icon-back">返回</span>
				<el-button @click="commit" :disabled="dis">立即申请</el-button>
				<router-link to="/login">登录</router-link>
				
			</div>
			<p class="copy_text">Copyright©2017-2018 TxasFx.All rights reseverd.</p>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				styleString: '',
				options: [{
					value: '1',
					label: '身份证'
				}, {
					value: '2',
					label: '驾驶证'
				}],
				card_type: '1',
				images: {
					card_p: '',
					card_n: '',
					bank_p: ''
				},
				userMess: {
					name: '',
					phone: '',
					email: '',
					address: '',
					cardNum: '',
					bankNum: '',
					bank: '',
					bank2: '' 
				},
				tripNum: 0,
				dis: false
			}
		},
		computed: {
			tuiId () {
				return this.$route.query.id;
			}
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
					_this.images.card_p = base64Codes;
				});
			},
			onFileChange2(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					_this.images.card_n = base64Codes;
				});
			},
			onFileChange3(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					_this.images.bank_p = base64Codes;
				});
			},
			back () {
				 this.$router.go(-1);//返回上一层
			},
			hideTrip () {
				this.tripNum = 0;
			},
			computedWH() {
				let ww = document.documentElement.clientWidth || document.body.clientWidth;
				let wh = document.documentElement.clientHeight || document.body.clientHeight;
				this.styleString = 'width:' + ww + 'px;height:' + wh + 'px;';
			},
			commit() {
				let _this = this;
				
				if (_this.userMess.name == '') {
					_this.tripNum = 1;
					return;
				}
				if (_this.userMess.email == '') {
					_this.tripNum = 2;
					return;
				}
				if (_this.userMess.phone == '') {
					_this.tripNum = 3;
					return;
				}
				if (_this.userMess.address == '') {
					_this.tripNum = 4;
					return;
				}
				if (_this.userMess.cardNum == '') {
					_this.tripNum = 6;
					return;
				}
				if (_this.images.card_p == '') {
					_this.tripNum = 5;
					return;
				}
				if (_this.images.card_n == '') {
					_this.tripNum = 7;
					return;
				}
				if (_this.userMess.bankNum == '') {
					_this.tripNum = 8;
					return;
				}
				if (_this.userMess.bank == '') {
					_this.tripNum = 9;
					return;
				}
				if (_this.userMess.bank2 == '') {
					_this.tripNum = 10;
					return;
				}
				if (_this.images.bank_p == '') {
					_this.tripNum = 11;
					return;
				}
				_this.dis = true;
				let datas = {
					"username": _this.userMess.name,
		            "phone": _this.userMess.phone,
		            "email": _this.userMess.email,
		            "address": _this.userMess.address,
		            "recommendation": _this.tuiId,//推荐码
		            
		            "idType": _this.card_type,
		            "idNumber": _this.userMess.cardNum,
		            "idPositive": _this.images.card_p,
		            "idOther": _this.images.card_n,
		            
		            "cardNumber": _this.userMess.bankNum,
		            "openingBank": _this.userMess.bank,
		            "branch": _this.userMess.bank2,
		            "cardPositive": _this.images.bank_p,
		            
		            "cardOther": null,
		            "state": null,
		            "spare1": null,
		            "spare2": null,
		            "type": 'APPLYACCOUNT'
				}
				_this.$until.superPost("/api/tlb-user/createTlbUser",datas, function (res) {
//					console.log(res)
					_this.dis = false;
					if (res.statusCode == '0000') {
						_this.$message({
				          message: "申请成功，等待审核",
				          type: 'success'
				        });
						_this.$router.push({
							path: "/login"
						});
					}else {
						_this.$message({
				          message: res.msgCode,
				          type: 'error'
				        });
					}
				})
			}
		},
		mounted() {
			let _this = this;
			_this.computedWH();
			window.onresize = () => _this.computedWH();
		}
	}
</script>

<style scoped>
	.el-select {
		width: 260px;
	}
	
	.el-input {
		width: 260px;
	}
	
	::-webkit-input-placeholder {
		/* WebKit browsers */
		color: #fff;
	}
	
	:-moz-placeholder {
		/* Mozilla Firefox 4 to 18 */
		color: #fff;
	}
	
	::-moz-placeholder {
		/* Mozilla Firefox 19+ */
		color: #fff;
	}
	
	:-ms-input-placeholder {
		/* Internet Explorer 10+ */
		color: #fff;
	}
	
	.login_section {
		position: relative;
		height: 100%;
		width: 100%;
		background-image: url("http://tlb.txasfx.com/crm/img/login_1.jpg");
		background-size: 100% 100%;
	}
	
	.form_box {
		/*height: 780px;*/
		height: 80%;
		width: 70%;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
		border: 2px solid #9292a3;
		box-shadow: 0px 0px 5px 3px #9292a3 inset;
		border-radius: 10px;
		box-sizing: border-box;
		padding: 0 40px;
		padding-top: 30px;
		text-align: center;
		background-color: rgba(1, 1, 1, .4);
	}
	
	.form_box h3 {
		font-size: 20px;
		color: #fff;
		padding-bottom: 25px;
		margin-bottom: 25px;
		border-bottom: 2px solid #fff;
	}
	
	.input_box {
		position: relative;
		margin-bottom: 25px;
	}
	
	.input_box span {
		float: left;
		line-height: 35px;
		text-align: right;
		width: 120px;
		color: #fff;
		font-size: 14px;
		position: relative;
		left: 14%;
	}
	.input_box span.trip_text {
		position: absolute;
		left: 80%;
		top: 0;
		color: #F14B3B;
		font-size: 12px;
	}
	
	.trips {
		position: absolute;
		top: 105%;
		left: 0;
		width: 100%;
		color: #FF4A4A;
		font-size: 12px;
		text-align: left;
		padding-left: 20px;
		text-align: right;
	}
	
	.btn_box .el-button {
		font-size: 18px;
		color: #191a41;
		padding: 5px 30px;
		background-color: #FFFFFF;
		border: 1px solid #fff;
		border-radius: 20px;
		margin-top: 20px;
		margin-left: 50px;
		margin-right: 50px;
	}
	.btn_box span,
	.btn_box a {
		font-size: 18px;
		cursor: pointer;
		color: #fff;
		background-color: transparent;
		border: 1px solid #fff;
		padding: 2px 20px;
		border-radius: 20px;
	}
	.btn_box span{
		padding: 4px 20px;
	}
	
	
	.uplond_box {
		position: relative;
		text-align: center;
		width: 260px;
		height: 160px;
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
		width: auto;
		height: auto;
		max-height: 100%;
		max-width: 100%;
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
	
	
	.copy_text {
		text-align: center;
		font-size: 12px;
		color: #fff;
		position: absolute;
		bottom: 5px;
		left: 0;
		right: 0;
	}
</style>