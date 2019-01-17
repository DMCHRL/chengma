<template>
	<div>
		<head-top></head-top>
		<el-row style="margin-top: 20px;">
			<el-col :span="14" :offset="4">
				
				<el-form :model="userForm" :rules="foodrules" ref="userForm" label-width="110px" class="form food_form">
          
          <el-form-item label="位置">
          	<el-select v-model="userForm.type" placeholder="请选择">
          		<el-option v-for="item in departments" :key="item.value" :label="item.label" :value="item.value">
          		</el-option>
          	</el-select>
          </el-form-item>
          
					<el-form-item label="排序" prop="sortNum">
						<el-input type="number" v-model="userForm.sortNum"></el-input>
					</el-form-item>
					<el-form-item label="备注" prop="text">
						<el-input v-model="userForm.text"></el-input>
					</el-form-item>
          
					<el-form-item label="图片">
						<el-upload class="avatar-uploader" 
            :name="'image'" 
            :action="baseUrl + '/api/uploadImage'" 
            :show-file-list="false" 
            :on-success="uploadImg1"
						:before-upload="beforeImgUpload"
             >
							<img v-if="userForm.img" :src="userForm.img" class="avatar">
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
	import { addBanner } from '@/api/getData'
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
					img: '',//	是	string	图片
          type: '1',//	是	string	1(融投页) 2(入伙页)
          text: '',//	否	string	备注
          sortNum: '',//	否	int	排序
				},
        
        departments: [
          {value: '1',label: '融投页'},
          {value: '2',label: '入伙页'},
        ],
				foodrules: {
					text: [{
						required: true,
						message: '请输入备注',
						trigger: 'blur'
					},],
          sortNum: [{
          	required: true,
          	message: '请输入序号',
          	trigger: 'blur'
          },],
          img: [{
          	required: true,
          	message: '请选择图片',
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
			selectValue: function() {
				return this.categoryForm.categoryList[this.categoryForm.categorySelect] || {}
			}
		},
		methods: {
			
			uploadImg1(res, file) {
				if (res) {
					this.userForm.img = res;
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

			confirm(userForm) {
				this.$refs[userForm].validate(async (valid) => {
					if (valid) {
						const params = {
							...this.userForm
						}
						try {
							const result = await addBanner(params);
								
							if (result.statusCode == '0000') {
								this.$message({
									type: 'success',
									message: '添加成功'
								});
								this.userForm = {
									img: '',//	是	string	图片
									type: '',//	是	string	1(融投页) 2(入伙页)
									text: '',//	否	string	备注
									sortNum: '',//	否	int	排序
								}
							} else {
								this.$message({
									type: 'error',
									message: result.msgCode
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
