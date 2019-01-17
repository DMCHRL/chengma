<template>
	<div>
		<head-top></head-top>
		<el-row style="margin-top: 20px;">
			<el-col :span="14" :offset="4">
				
				<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
          
          <el-form-item label="授权商户" prop="company">
          	<el-input v-model="userForm.company"></el-input>
          </el-form-item>
					<el-form-item label="授权银行卡号" prop="bankNum">
						<el-input v-model="userForm.bankNum"></el-input>
					</el-form-item>
					<el-form-item label="授权收款人" prop="name">
						<el-input v-model="userForm.name"></el-input>
					</el-form-item>
          <el-form-item label="开户银行" prop="bank">
          	<el-input v-model="userForm.bank"></el-input>
          </el-form-item>
					<el-form-item label="备注码" prop="remark">
						<el-input v-model="userForm.remark"></el-input>
					</el-form-item>
          
					<el-form-item>
						<el-button type="primary" @click="confirm('userForm')">确认添加</el-button>
					</el-form-item>
				</el-form>
				
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import headTop from '@/components/headTop'
	import { addReceipt } from '@/api/getData'
	export default {
		data() {
			return {
				userForm: {
					bank: '',//	是	string	银行名称
          name: '',//	是	string	收款人姓名
          bankNum: '',//	否	string	银行卡号
          remark: '',//	否	string	转账备注
          company: '',//	否	string	公司
				},
        
        departments: [
          {value: 'service',label: '客服'},
        ],
				foodrules: {
					login: [{
						required: true,
						message: '请输入登录名',
						trigger: 'blur'
					},],
          mobile: [{
          	required: true,
          	message: '请输入手机号',
          	trigger: 'blur'
          },],
          email: [{
          	required: true,
          	message: '请输入邮箱',
          	trigger: 'blur'
          },],
				},
				
			}
		},
		components: {
			headTop,
		},
		created() {
			
		},
		computed: {
			
		},
		methods: {
			
			confirm(userForm) {
				this.$refs[userForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.userForm
						}
						try {
							const result = await addReceipt(params);
								
							if (result.statusCode == '0000') {
								this.$message({
									type: 'success',
									message: '添加成功'
								});
								this.userForm = {
									bank: '',//	是	string	银行名称
									name: '',//	是	string	收款人姓名
									bankNum: '',//	否	string	银行卡号
									remark: '',//	否	string	转账备注
									company: '',//	否	string	公司
								}
							} else {
								this.$message({
									type: 'error',
									message: result.message
								});
							}
						} catch (err) {
							console.log(err)
						}
					} else {
						this.$notify.error({
							title: '错误',
							message: '请检查输入是否正确',
							offset: 100
						});
						return false;
					}
				});
			}
		}
	}
</script>

<style lang="less">
	@import '../style/mixin';

	.form {
		min-width: 400px;
		margin-bottom: 30px;

		&:hover {
			box-shadow: 0 0 8px 0 rgba(232, 237, 250, .6), 0 2px 4px 0 rgba(232, 237, 250, .5);
			border-radius: 6px;
			transition: all 400ms;
		}
	}

	.food_form {
		border: 1px solid #eaeefb;
		padding: 10px 10px 0;
	}

	.form_header {
		text-align: center;
		margin-bottom: 10px;
	}

	.category_select {
		border: 1px solid #eaeefb;
		padding: 10px 30px 10px 10px;
		border-top-right-radius: 6px;
		border-top-left-radius: 6px;
	}

	.add_category_row {
		height: 0;
		overflow: hidden;
		transition: all 400ms;
		background: #f9fafc;
	}

	.showEdit {
		height: 185px;
	}

	.add_category {
		background: #f9fafc;
		padding: 10px 30px 0px 10px;
		border: 1px solid #eaeefb;
		border-top: none;
	}

	.add_category_button {
		text-align: center;
		line-height: 40px;
		border-bottom-right-radius: 6px;
		border-bottom-left-radius: 6px;
		border: 1px solid #eaeefb;
		border-top: none;
		transition: all 400ms;

		&:hover {
			background: #f9fafc;

			span,
			.edit_icon {
				color: #20a0ff;
			}
		}

		span {
			.sc(14px, #999);
			transition: all 400ms;
		}

		.edit_icon {
			color: #ccc;
			transition: all 400ms;
		}
	}

	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #20a0ff;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 120px;
		height: 120px;
		line-height: 120px;
		text-align: center;
	}

	.avatar {
		width: 120px;
		height: 120px;
		display: block;
	}

	.cell {
		text-align: center;
	}
</style>
