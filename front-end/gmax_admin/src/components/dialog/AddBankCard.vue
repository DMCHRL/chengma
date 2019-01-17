<template>
	<!--添加银行卡弹窗-->
	<el-dialog custom-class="ibs_dialog" width="30%" :show-close="false" :visible.sync="innerVisible" append-to-body>
		<h3 slot="title" class="dialog_title">添加银行卡</h3>
		<div class="input_box">
			<span>币种</span>
			<select name="bizhong" v-model="currency">
				<option value="人民币">人民币</option>
				<option value="英镑">英镑</option>
				<option value="美元">美元</option>
			</select>
		</div>
		<div class="input_box">
			<span>卡号</span>
			<input type="text" placeholder="请输入卡号" v-model="cardnum" />
			<p class="trip_text" v-show="tripnum == 1">卡号为空</p>
			<p class="trip_text" v-show="tripnum == 2">卡号格式错误</p>
		</div>
		<div class="input_box">
			<span>银行名称</span>
			<input type="text" placeholder="请输入银行名称" v-model="bank" />
			<p class="trip_text" v-show="tripnum == 3">银行名称为空</p>
		</div>
		<div class="input_box">
			<span>姓名</span>
			<input type="text" placeholder="请输入姓名" v-model="name" />
			<p class="trip_text" v-show="tripnum == 4">姓名为空</p>
		</div>
		<div class="input_box">
			<span>城市</span>
			<input type="text" placeholder="请输入城市" v-model="city" />
			<p class="trip_text" v-show="tripnum == 5">城市为空</p>
		</div>
		<div class="input_box">
			<span>开户行</span>
			<input type="text" placeholder="请输入开户行" v-model="bankname" />
			<p class="trip_text" v-show="tripnum == 6">开户行为空</p>
		</div>
		<div class="input_box">
			<span>预留电话</span>
			<input type="text" placeholder="请输入预留电话" v-model="phone" />
			<p class="trip_text" v-show="tripnum == 7">预留电话为空</p>
		</div>
		<div class="uplond_box">
			<label>
				<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
				<span v-show="!images.zheng_image">上传银行卡正面</span>
				<img v-show="images.zheng_image" :src="images.zheng_image"/>
			</label>
			<label>
				<input @change="onFileChange2" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
				<span v-show="!images.fan_image">上传银行卡反面</span>
				<img v-show="images.fan_image" :src="images.fan_image"/>
			</label>
			<p class="trip_text" v-show="tripnum == 8">银行卡照片不齐全</p>
		</div>
		<div slot="footer" class="dialog_footer">
			<button @click="cardCommit">确认</button>
			<button @click="cancelDialog">取消</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['innerVisible'],
		data() {
			return {
				images: {
					zheng_image: '',
					fan_image: ''
				},
				cardnum: '',
				bank: '',
				name: '',
				city: '',
				bankname: '',
				phone: '',
				currency: '美元',
				tripnum: 0
			}
		},
		components: {

		},
		methods: {
			onFileChange1(e) {
				////console.log(e)
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.zheng_image = base64Codes;
				});
			},
			onFileChange2(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.fan_image = base64Codes;
				});
			},
			cardCommit() {
				let _this = this;
				let cardata = {
					images: _this.images,
					cardnum: _this.cardnum.trim(),
					bank: _this.bank.trim(),
					name: _this.name.trim(),
					city: _this.city.trim(),
					bankname: _this.bankname.trim(),
					phone: _this.phone.trim(),
					currency: _this.currency.trim()
				}
				if(cardata.cardnum == '') {
					_this.tripnum = 1;
					return;
				}
				if(!/^([1-9]{1})(\d{14}|\d{18})$/.test(cardata.cardnum)) {
					_this.tripnum = 2;
					return;
				}
				if(cardata.bank == '') {
					_this.tripnum = 3;
					return;
				}
				if(cardata.name == '') {
					_this.tripnum = 4;
					return;
				}
				if(cardata.city == '') {
					_this.tripnum = 5;
					return;
				}
				if(cardata.bankname == '') {
					_this.tripnum = 6;
					return;
				}
				if(cardata.phone == '') {
					_this.tripnum = 7;
					return;
				}
				if(cardata.images.zheng_image == '' || cardata.images.fan_image == '') {
					_this.tripnum = 8;
					return;
				}
				_this.tripnum = 0;

				_this.cardnum = ''; //清空卡号，

				//				_this.$emit('addcard',cardata);//提交
				_this.$emit('cancelDialog') //关闭弹出
			},
			cancelDialog() {
				this.$emit('cancelDialog')
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.ibs_dialog .input_box {
		margin-bottom: 25px;
		position: relative;
	}
	
	.ibs_dialog .input_box span {
		display: inline-block;
		width: 35%;
		text-align: right;
		padding-right: 10px;
		color: #000;
	}
	
	.ibs_dialog .input_box span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.ibs_dialog .input_box input,
	.ibs_dialog .input_box select {
		border: 1px solid #ccc;
		width: 40%;
		padding: 5px 10px;
		border-radius: 5px;
	}
	
	.ibs_dialog .trip_text {
		position: absolute;
		top: 100%;
		left: 0;
		padding-left: 40%;
		font-size: 12px;
		color: #F14B3B;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 18px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
	
	.uplond_box {
		position: relative;
		text-align: center;
	}
	
	.uplond_box label {
		display: inline-block;
		width: 140px;
		height: 90px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	
	.uplond_box label span {
		line-height: 90px;
		color: #666;
		font-size: 14px;
	}
	
	.uplond_box label img {
		width: 100%;
		height: auto;
	}
</style>