<template>
	<div>
		<head-top></head-top>
		<el-row style="margin-top: 20px;">
			<el-col :span="14" :offset="4">
				
				<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
          
          <el-form-item label="角色">
          	<el-select v-model="userForm.department" placeholder="请选择">
          		<el-option v-for="item in departments" :key="item.value" :label="item.label" :value="item.value">
          		</el-option>
          	</el-select>
          </el-form-item>
          
					<el-form-item label="名称" prop="name">
						<el-input v-model="userForm.name"></el-input>
					</el-form-item>
					<el-form-item label="手机号" prop="mobile">
						<el-input v-model="userForm.mobile"></el-input>
					</el-form-item>
					<el-form-item label="邮箱" prop="email">
						<el-input v-model="userForm.email"></el-input>
					</el-form-item>
          
					<el-form-item label="身份证国徽面">
						<el-upload class="avatar-uploader" 
            :name="'image'" 
            :action="baseUrl + '/api/uploadImage'" 
            :show-file-list="false" 
            :on-success="uploadImg1"
						:before-upload="beforeImgUpload"
             >
							<img v-if="userForm.idPositive" :src="userForm.idPositive" class="avatar">
							<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
          <el-form-item label="身份证人像面">
          	<el-upload class="avatar-uploader" 
          	:name="'image'" 
          	:action="baseUrl + '/api/uploadImage'" 
          	:show-file-list="false" 
          	:on-success="uploadImg2"
          	:before-upload="beforeImgUpload"
          	>
          		<img v-if="userForm.idNegative" :src="userForm.idNegative" class="avatar">
          		<i v-else class="el-icon-plus avatar-uploader-icon"></i>
          	</el-upload>
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
	import { addPowerUser } from '@/api/getData'
	import {
		baseUrl,
		baseImgPath
	} from '@/config/env'
	export default {
		data() {
			return {
				baseUrl,
				baseImgPath,
				userForm: {
					name: '',
					mobile: '',
					email: '',
					idPositive: '',
					idNegative: '',
					department: '',
				},
        
        departments: [
          {value: 'originator',label: '发起人'},
          {value: 'manager',label: '资金管理人'},
        ],
				foodrules: {
					name: [{
						required: true,
						message: '请输入名称',
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
				attributes: [{
					value: '新',
					label: '新品'
				}, {
					value: '招牌',
					label: '招牌'
				}, ],
				showAddCategory: false,
				foodSpecs: 'one',
				dialogFormVisible: false,
				specsForm: {
					specs: '',
					packing_fee: 0,
					price: 20,
				},
				specsFormrules: {
					specs: [{
						required: true,
						message: '请输入规格',
						trigger: 'blur'
					}, ],
				}
			}
		},
		components: {
			headTop,
		},
		created() {
			
		},
		computed: {
			selectValue: function() {
				return this.categoryForm.categoryList[this.categoryForm.categorySelect] || {}
			}
		},
		methods: {
			async initData() {
				try {
					const result = await getCategory(this.restaurant_id);
					if (result.status == 1) {
						result.category_list.map((item, index) => {
							item.value = index;
							item.label = item.name;
						})
						this.categoryForm.categoryList = result.category_list;
					} else {
						console.log(result)
					}
				} catch (err) {
					console.log(err)
				}
			},
			addCategoryFun() {
				this.showAddCategory = !this.showAddCategory;
			},
			submitcategoryForm(categoryForm) {
				this.$refs[categoryForm].validate(async (valid) => {
					if (valid) {
						const params = {
							name: this.categoryForm.name,
							description: this.categoryForm.description,
							restaurant_id: this.restaurant_id,
						}
						try {
							const result = await addCategory(params);
							if (result.status == 1) {
								this.initData();
								this.categoryForm.name = '';
								this.categoryForm.description = '';
								this.showAddCategory = false;
								this.$message({
									type: 'success',
									message: '添加成功'
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
			},
			uploadImg1(res, file) {
				if (res) {
					this.userForm.idPositive = res;
				} else {
					this.$message.error('上传图片失败！');
				}
			},
      uploadImg2(res, file) {
      	if (res) {
      		this.userForm.idNegative = res;
      	} else {
      		this.$message.error('上传图片失败！');
      	}
      },
			beforeImgUpload(file) {
				const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isRightType) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isRightType && isLt2M;
			},
			addspecs() {
				this.userForm.specs.push({ ...this.specsForm
				});
				this.specsForm.specs = '';
				this.specsForm.packing_fee = 0;
				this.specsForm.price = 20;
				this.dialogFormVisible = false;
			},
			handleDelete(index) {
				this.userForm.specs.splice(index, 1);
			},
			tableRowClassName(row, index) {
				if (index === 1) {
					return 'info-row';
				} else if (index === 3) {
					return 'positive-row';
				}
				return '';
			},
			confirm(userForm) {
				this.$refs[userForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.userForm
						}
						try {
							const result = await addPowerUser(params);
								
							if (result.statusCode == '0000') {
								this.$message({
									type: 'success',
									message: '添加成功'
								});
								this.userForm = {
									name: '',
									mobile: '',
									email: '',
									idPositive: '',
									idNegative: '',
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
