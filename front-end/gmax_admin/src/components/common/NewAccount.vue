<template>
	<el-form :model="ruleForm" :inline="true" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" size="mini">
		<div class="tab1" v-show="istab">
			<div class="dialog_line">
				<span>联系方式</span>
			</div>
			<div>
				<el-form-item label="手机" prop="phone">
					<el-input v-model="ruleForm.phone"></el-input>
				</el-form-item>
				<el-form-item label="备用电话" prop="phone1">
					<el-input v-model="ruleForm.phone1"></el-input>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="ruleForm.email"></el-input>
				</el-form-item>
			</div>
			<div class="dialog_line">
				<span>基本资料</span>
			</div>
			<div>
				<el-form-item label="姓名" prop="name">
					<el-input v-model="ruleForm.name"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-select v-model="ruleForm.sex">
						<el-option label="男" value="1"></el-option>
						<el-option label="女" value="2"></el-option>
					</el-select>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="出生年月">
					<el-date-picker v-model="ruleForm.birthday" type="date" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="国家或地区">
					<el-select v-model="ruleForm.country">
						<el-option label="中国内地" value="1"></el-option>
						<el-option label="中国香港" value="2"></el-option>
						<el-option label="中国澳门" value="3"></el-option>
					</el-select>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="现住地">
					<el-input v-model="ruleForm.nowaddr"></el-input>
				</el-form-item>
			</div>
			<div class="dialog_line">
				<span>财务信息</span>
			</div>
			<div>
				<el-form-item label="开户姓名">
					<el-input v-model="ruleForm.yinname"></el-input>
				</el-form-item>
				<el-form-item label="银行卡账户">
					<el-input v-model="ruleForm.yinaccount"></el-input>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="开户银行">
					<el-input v-model="ruleForm.bank"></el-input>
				</el-form-item>
				<el-form-item label="开户支行">
					<el-input v-model="ruleForm.bank1"></el-input>
				</el-form-item>
			</div>
			<div class="_button_box">
				<el-form-item>
					<el-button @click="istab = false">下一步</el-button>
					<el-button @click="resetForm('ruleForm')">重置</el-button>
				</el-form-item>
			</div>
		</div>
		<div class="tab2" v-show="!istab">

			<div class="dialog_line">
				<span>身份证明</span>
			</div>
			<div>
				<el-form-item label="证件类型">
					<el-select>
						<el-option label="身份证" value="1"></el-option>
						<el-option label="驾驶证" value="2"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="证件号码">
					<el-input></el-input>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="证件正面">
					<label class="uplond_box">
								<input @change="onFileChange1" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<span v-show="!images.zz">点击上传</span>
								<img v-show="images.zz" :src="images.zz"/>
							</label>
				</el-form-item>
				<el-form-item label="证件反面">
					<label class="uplond_box">
								<input @change="onFileChange2" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<span v-show="!images.zf">点击上传</span>
								<img v-show="images.zf" :src="images.zf"/>
							</label>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="银行卡正面">
					<label class="uplond_box">
								<input @change="onFileChange3" type="file" ref="file" style="display:none;" accept="image/jpg" name="file" >
								<span v-show="!images.yz">点击上传</span>
								<img v-show="images.yz" :src="images.yz"/>
							</label>
				</el-form-item>
			</div>
			<div>
				<el-form-item label="备注">
					<el-input type="textarea" v-model="ruleForm.bei"></el-input>
				</el-form-item>
			</div>
			<div class="_button_box">
				<el-form-item>
					<el-button @click="istab = true">上一步</el-button>
					<el-button type="primary" @click="submitForm('ruleForm')">立即提交</el-button>
					<el-button @click="resetForm('ruleForm')">重置</el-button>
				</el-form-item>
			</div>
		</div>
	</el-form>
</template>

<script>
	export default {
		data() {
			return {
				istab: true,
				ruleForm: {
					phone: '',
					phone1: '',
					email: '',
					name: '',
					sex: '1',
					birthday: '',
					country: '',
					nowaddr: '',
					yinname: '',
					yinaccount: '',
					bank: '',
					bank1: '',
					bei: ''
				},
				rules: {
					phone: [
						{
							required: true,
							message: '请输入手机号',
							trigger: 'blur'
						}
					],
					phone1: [
						{
							required: true,
							message: '请输入备用手机号',
							trigger: 'blur'
						}
					],
					email: [
						{
							required: true,
							message: '请输入备用手机号',
							trigger: 'blur'
						}
					],
					name: [{
							required: true,
							message: '请输入姓名',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 5,
							message: '长度在 3 到 5 个字符',
							trigger: 'blur'
						}
					],

				},
				images: {
					zz: '',
					zf: '',
					yz: ''
				}
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
					//					////console.log(base64Codes)
					_this.images.zz = base64Codes;
				});
			},
			onFileChange2(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.zf = base64Codes;
				});
			},
			onFileChange3(e) {
				let _this = this;

				let file = e.target.files[0] || e.dataTransfer.files[0];

				_this.$until.photoCompress(file, {
					quality: 0.5
				}, function(base64Codes) {
					//					////console.log(base64Codes)
					_this.images.yz = base64Codes;
				});
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if(valid) {
						alert('submit!');
					} else {
						////console.log('error submit!!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.dialog_line {
		line-height: 30px;
		border-bottom: 1px solid #999;
		margin-bottom: 20px;
	}
	
	.dialog_line span {
		border-left: 3px solid #434197;
		padding-left: 10px;
	}
	
	._button_box {
		text-align: center;
	}
	
	.demo-ruleForm {
		margin-top: -20px;
	}
	
	.uplond_box {
		width: 140px;
		height: 90px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	
	.uplond_box span {
		line-height: 90px;
		color: #666;
		font-size: 14px;
	}
	
	.uplond_box img {
		width: 100%;
		height: auto;
	}
</style>