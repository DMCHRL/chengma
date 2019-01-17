<template>
	<div class="fillcontain">
		<head-top></head-top>
		<div class="table_container">
			<el-table :data="tableData" style="width: 100%">
				<el-table-column type="expand">
					<template slot-scope="props">
						<el-form label-position="left" inline class="demo-table-expand">
							<el-form-item label="开通时间">
								<span>{{ props.row.createTime }}</span>
							</el-form-item>
							<el-form-item label="更新时间">
								<span>{{ props.row.updateTime }}</span>
							</el-form-item>

						</el-form>
					</template>
				</el-table-column>
				<el-table-column label="授权商户" prop="company">
				</el-table-column>
				<el-table-column label="授权银行卡号" prop="bankNum">
				</el-table-column>
				<el-table-column label="授权收款人" prop="name">
				</el-table-column>
        <el-table-column label="开户银行" prop="bank">
        </el-table-column>
				<el-table-column label="备注码" prop="remark">
				</el-table-column>
				<el-table-column label="状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isflag"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="handleChange(scope.row.isflag,scope.row.id)">
            </el-switch>
          </template>
				</el-table-column>
				<el-table-column label="操作" width="200">
					<template slot-scope="scope">

						<el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              
					</template>
				</el-table-column>
			</el-table>
			<div class="Pagination" style="text-align: left;margin-top: 10px;">
				<el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage" :page-size="page.size" layout="total, prev, pager, next"
				 :total="page.count">
				</el-pagination>
			</div>

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
		getReceiptList,
		handleReceiptSelect,
    deleteReceipt
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
				count: 0,
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
					const res = await getReceiptList();
					console.log(res)
					if (res.statusCode == '0000') {
            let list = res.data.list;
            if (list.length) {
              list.forEach(item => {
                item.isflag = item.flag == 'Y'? true:false;
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
      async handleChange(status, id) {
      	try {
          
      		const res = await handleReceiptSelect(id);
      		console.log(res)
      		if (res.statusCode == '0000') {
      			this.$message({
      				type: 'success',
      				message: '操作成功'
      			});
      			this.initData()
      		} else {
      			this.$message({
      				type: 'error',
      				message: res.msgCode
      			});
            this.initData()
      		}
      	} catch (e) {
      		console.log('操作失败', e);
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
			
			
			handleCurrentChange(val) {
				this.page.currentPage = val;
				this.initData()
			},
			handleEdit(index, row) {
				this.selectTable = row;
				this.dialogFormVisible = true;
			},
			
			async handleDelete(index, row) {
				try {
					const res = await deleteReceipt(row.id);
					if (res.statusCode == '0000') {
						this.$message({
							type: 'success',
							message: '删除成功'
						});
						this.tableData.splice(index, 1);
					} else {
						this.$message({
							type: 'error',
							message: res.msgCode
						});
					}
				} catch (err) {
					console.log('操作失败', e);
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
