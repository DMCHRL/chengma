<template>
	<div>
		<head-top></head-top>
		<el-row style="margin-top: 20px;">
			<el-col :span="14" :offset="4">
				
				<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
          
          <el-form-item label="账户管理人">
          	<el-select v-model="userForm.userId" placeholder="请选择">
          		<el-option v-for="item in users" :key="item.value" :label="item.label" :value="item.value">
          		</el-option>
          	</el-select>
          </el-form-item>
          
					<el-form-item label="账户名称" prop="accountName">
						<el-input v-model="userForm.accountName"></el-input>
					</el-form-item>
					<el-form-item label="交易密码" prop="mt4Password">
						<el-input type="password" v-model="userForm.mt4Password"></el-input>
					</el-form-item>
          <el-form-item label="确认密码" prop="mt4Password2">
          	<el-input type="password" v-model="userForm.mt4Password2"></el-input>
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
	import { getUsers,addAccount } from '@/api/getData'
  import { whatDepart }from '@/config/mUtils'
	export default {
		data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.userForm.mt4Password2 !== '') {
            this.$refs.userForm.validateField('mt4Password2');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.userForm.mt4Password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
			return {
				userForm: {
					accountName: '',
					mt4Password: '',
					mt4Password2: '',
          userId: '',
				},
        
        users: [],
        
				foodrules: {
					accountName: [{
						required: true,
						message: '请输入账户名',
						trigger: 'blur'
					},],
          mt4Password: [{ validator: validatePass, trigger: 'blur' }],
          mt4Password2: [{ validator: validatePass2, trigger: 'blur' }],
				},
				
			}
		},
		components: {
			headTop,
		},
		created() {
			this.getUsers();
		},
		computed: {
			
		},
		methods: {
      
			async getUsers() {
				try{
						const res = await getUsers({
							params: {}
						});
						console.log(res)
						if (res.statusCode == '0000') {
							let list = res.data.list;
              if (list.length) {
                list.forEach(item => {
                  item.label = item.firstName+'  ('+whatDepart(item.department)+')';
                  item.value = item.id;
                })
              }
							this.users = list;
							
						}else{
								throw new Error('获取数据失败');
						}
				}catch(err){
						console.log('获取数据失败', err);
				}
			},
			confirm(userForm) {
				this.$refs[userForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.userForm
						}
						try {
							const result = await addAccount(params);
								
							if (result.statusCode == '0000') {
								this.$message({
									type: 'success',
									message: '添加成功'
								});
								this.userForm = {
									accountName: '',
									mt4Password: '',
									mt4Password2: '',
									userId: '',
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
