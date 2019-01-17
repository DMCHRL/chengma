<template>
	<div>
		<head-top></head-top>
		<el-row style="margin-top: 20px;">
			<el-col :span="14" :offset="4">
				
				<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
          
          <el-form-item label="身份">
          	<el-select v-model="userForm.department" placeholder="请选择">
          		<el-option v-for="item in departments" :key="item.value" :label="item.label" :value="item.value">
          		</el-option>
          	</el-select>
          </el-form-item>
          
					<el-form-item label="登录名" prop="login">
						<el-input v-model="userForm.login"></el-input>
					</el-form-item>
					<el-form-item label="昵称" prop="firstName">
						<el-input v-model="userForm.firstName"></el-input>
					</el-form-item>
          <el-form-item label="手机号" prop="mobile">
          	<el-input v-model="userForm.mobile"></el-input>
          </el-form-item>
					<el-form-item label="邮箱" prop="email">
						<el-input v-model="userForm.email"></el-input>
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
	import { addAdminUser } from '@/api/getData'
	export default {
		data() {
			return {
				userForm: {
					login: '',
					mobile: '',
					email: '',
          firstName: '',
					department: '',
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
							const result = await addAdminUser(params);
								
							if (result.statusCode == '0000') {
								this.$message({
									type: 'success',
									message: '添加成功'
								});
								this.userForm = {
									login: '',
									mobile: '',
									email: '',
									firstName: '',
									department: '',
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
