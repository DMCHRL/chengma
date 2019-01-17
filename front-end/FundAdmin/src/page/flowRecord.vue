<template>
	<div class="fillcontain">
		<head-top></head-top>
		<div class="table_container">
			<el-table :data="tableData" style="width: 100%">
				<el-table-column type="expand">
					<template slot-scope="props">
						<el-form label-position="left" inline class="demo-table-expand">
							<el-form-item label="申请时间">
								<span>{{ props.row.createAt }}</span>
							</el-form-item>
							<el-form-item label="审核时间">
								<span>{{ props.row.approveAt }}</span>
							</el-form-item>
							<el-form-item label="银行卡号">
								<span>{{ props.row.bankNum }}</span>
							</el-form-item>

						</el-form>
					</template>
				</el-table-column>
				<el-table-column label="类型" prop="Ctype">
				</el-table-column>
				<el-table-column label="金额" prop="money">
				</el-table-column>
				<el-table-column label="申请人" prop="name">
				</el-table-column>
				<el-table-column label="申请手机号" prop="mobile">
				</el-table-column>
				<el-table-column label="状态" prop="Cstatus">
				</el-table-column>
				<el-table-column label="操作" width="200">
					<template slot-scope="scope">

						<el-button size="mini" :type="scope.row.status == 'APPLYING'?'primary':''" @click="handleEdit(scope.$index, scope.row)">{{scope.row.status == 'APPLYING'?'审核':'查看'}}</el-button>

						<!-- <el-button
                      size="mini"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
					</template>
				</el-table-column>
			</el-table>
			<div class="Pagination" style="text-align: left;margin-top: 10px;">
				<el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage" :page-size="page.size" layout="total, prev, pager, next"
				 :total="page.count">
				</el-pagination>
			</div>

			<el-dialog title="出入金审核" v-model="dialogFormVisible" @close="initData">
				<div class="dialog_box">
					<div class="item_box">
						<span>任务事项</span>
						<span>{{selectTable.Ctype}}</span>
					</div>
					<div class="item_box">
						<span>银行卡号</span>
						<span>{{selectTable.bankNum}}</span>
					</div>
					<div class="item_box">
						<span>金额</span>
						<span>{{selectTable.money}}</span>
					</div>
					<div class="item_box">
						<span>申请时间</span>
						<span>{{selectTable.createAt}}</span>
					</div>
					<div class="item_box">
						<span>审核时间</span>
						<span>{{selectTable.approveAt}}</span>
					</div>
					<div class="item_box">
						<span>状态</span>
						<span>{{selectTable.Cstatus}}</span>
					</div>
				</div>
				<div slot="footer" class="dialog-footer" v-if="selectTable.status == 'APPLYING'">
					<el-button type="primary" @click="handleSelect(selectTable.id,'PASSED')">通 过</el-button>
					<el-button type="danger" @click="handleSelect(selectTable.id,'REJECT')">拒 绝</el-button>
				</div>
			</el-dialog>

		</div>
	</div>
</template>

<script>
	import headTop from '../components/headTop'
	import {
		baseUrl,
		baseImgPath
	} from '@/config/env'
  import {
  	whatType,
    whatStatus
  } from '@/config/mUtils'
	import {
		getRecord,
		handleSelect
	} from '@/api/getData'
	export default {
		data() {
			return {
				tableData: [],
				selectTable: {},
				page: {
					size: 20,
					count: 0,
					currentPage: 1,
				},

				baseUrl,
				baseImgPath,
				city: {},
				offset: 0,
				limit: 20,
				count: 0,
				currentPage: 1,
				dialogFormVisible: false,
				categoryOptions: [],
				selectedCategory: [],
				address: {},
			}
		},
		created() {
			this.initData();
		},
		components: {
			headTop,
		},
		methods: {
      
			async initData() {
				try {
					const res = await getRecord({
						page_number: this.page.currentPage, //	是	string	页码
						page_size: this.page.size, //	是	string	每页大小
						formParams: {}
					});
					console.log(res)
					if (res.statusCode == '0000') {
            let list = res.data.list;
            if (list.length) {
              list.forEach(item => {
                item.Ctype = whatType(item.type);
                item.Cstatus = whatStatus(item.status)
              })
            }
						this.tableData = list;
						this.page.count = res.data.total;

					} else {
						throw new Error('获取数据失败');
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
			async handleSelect(id, status) {
				try {
					const res = await handleSelect({
						id: id, //	是	string	处理id
						status: status
					});
					console.log(res)
					if (res.statusCode == '0000') {
						this.$message({
							type: 'success',
							message: '操作成功'
						});
						this.selectTable.status = 'PASSED';
						this.dialogFormVisible = false;
					} else {
						this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
				} catch (e) {
					console.log('操作失败', e);
				}
			},
			async getCategory() {
				try {
					const categories = await foodCategory();
					categories.forEach(item => {
						if (item.sub_categories.length) {
							const addnew = {
								value: item.name,
								label: item.name,
								children: []
							}
							item.sub_categories.forEach((subitem, index) => {
								if (index == 0) {
									return
								}
								addnew.children.push({
									value: subitem.name,
									label: subitem.name,
								})
							})
							this.categoryOptions.push(addnew)
						}
					})
				} catch (err) {
					console.log('获取商铺种类失败', err);
				}
			},
			async getResturants() {
				const {
					latitude,
					longitude
				} = this.city;
				const restaurants = await getResturants({
					latitude,
					longitude,
					offset: this.offset,
					limit: this.limit
				});
				this.tableData = [];
				restaurants.forEach(item => {
					const tableData = {};
					tableData.name = item.name;
					tableData.address = item.address;
					tableData.description = item.description;
					tableData.id = item.id;
					tableData.phone = item.phone;
					tableData.rating = item.rating;
					tableData.recent_order_num = item.recent_order_num;
					tableData.category = item.category;
					tableData.image_path = item.image_path;
					this.tableData.push(tableData);
				})
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				this.page.currentPage = val;
				this.initData()
			},
			handleEdit(index, row) {
				this.selectTable = row;
				this.dialogFormVisible = true;
			},
			addFood(index, row) {
				this.$router.push({
					path: 'addGoods',
					query: {
						restaurant_id: row.id
					}
				})
			},
			async handleDelete(index, row) {
				try {
					const res = await deleteResturant(row.id);
					if (res.status == 1) {
						this.$message({
							type: 'success',
							message: '删除成功'
						});
						this.tableData.splice(index, 1);
					} else {
						throw new Error(res.message)
					}
				} catch (err) {
					this.$message({
						type: 'error',
						message: err.message
					});
					console.log('删除店铺失败')
				}
			},
			async querySearchAsync(queryString, cb) {
				if (queryString) {
					try {
						const cityList = await searchplace(this.city.id, queryString);
						if (cityList instanceof Array) {
							cityList.map(item => {
								item.value = item.address;
								return item;
							})
							cb(cityList)
						}
					} catch (err) {
						console.log(err)
					}
				}
			},
			addressSelect(vale) {
				const {
					address,
					latitude,
					longitude
				} = vale;
				this.address = {
					address,
					latitude,
					longitude
				};
			},
			handleServiceAvatarScucess(res, file) {
				if (res.status == 1) {
					this.selectTable.image_path = res.image_path;
				} else {
					this.$message.error('上传图片失败！');
				}
			},
			beforeAvatarUpload(file) {
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
			async updateShop() {
				this.dialogFormVisible = false;
				try {
					Object.assign(this.selectTable, this.address);
					this.selectTable.category = this.selectedCategory.join('/');
					const res = await updateResturant(this.selectTable)
					if (res.status == 1) {
						this.$message({
							type: 'success',
							message: '更新店铺信息成功'
						});
						this.getResturants();
					} else {
						this.$message({
							type: 'error',
							message: res.message
						});
					}
				} catch (err) {
					console.log('更新餐馆信息失败', err);
				}
			},
		},
	}
</script>

<style lang="less">
	@import '../style/mixin';

	.demo-table-expand {
		font-size: 0;
	}

	.demo-table-expand label {
		width: 90px;
		color: #99a9bf;
	}

	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
	}

	.table_container {
		padding: 20px;
	}

	.Pagination {
		display: flex;
		justify-content: flex-start;
		margin-top: 8px;
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

	.dialog_box {
		border: 1px solid #ddd;
		border-bottom: none;
	}

	.dialog_box .item_box {
		border-bottom: 1px solid #ddd;
	}

	.dialog_box span:first-of-type {
		display: inline-block;
		width: 150px;
		text-align: center;
		line-height: 50px;
		border-right: 1px solid #ddd;
	}
</style>
