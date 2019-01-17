<template>
	<div class="fillcontain">
		<head-top></head-top>
		<div class="table_container">
			<el-table :data="tableData" :row-key="row => row.index" style="width: 100%">
				<el-table-column type="expand">
					<template slot-scope="props">
						<el-form label-position="left" inline class="demo-table-expand">
							<el-form-item label="客服QQ" v-if="props.row.type == 'ABOUT_US'">
								<span>{{ props.row.qq }}</span>
							</el-form-item>
							<el-form-item label="客服电话"  v-if="props.row.type == 'ABOUT_US'">
								<span>{{ props.row.phone }}</span>
							</el-form-item>
							<el-form-item label="协议内容">
								<div v-html="props.row.context"></div>
							</el-form-item>
						</el-form>
            
					</template>
				</el-table-column>
				<el-table-column label="名称" prop="typeDesc">
				</el-table-column>
        <el-table-column label="创建时间" prop="createTime">
        </el-table-column>
        <el-table-column label="操作" width="200">
        	<template slot-scope="scope">
        		<el-button
        			size="mini"
        			type="primary"
        			@click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        		<el-button
        			size="mini"
        			type="danger"
        			@click="handleDelete(scope.$index, scope.row)">删除</el-button>
        	</template>
        </el-table-column>
			</el-table>
			<div class="Pagination" style="text-align: left;margin-top: 10px;">
				<el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage"
				:page-size="page.size" layout="total, prev, pager, next" :total="page.count">
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import headTop from '../components/headTop'
	import {
		getAgreeList,
    deleteAgree
	} from '@/api/getData'
	export default {
		data() {
			return {
				tableData: [],
        page: {
        	size: 20,
        	count: 0,
        	currentPage: 1,
        },
        
				currentRow: null,
				offset: 0,
				limit: 20,
				count: 0,
				currentPage: 1,
				restaurant_id: null,
				expendRow: [],
			}
		},
		components: {
			headTop,
		},
		created() {
			// this.restaurant_id = this.$route.query.restaurant_id;
			this.initData();
		},
		mounted() {

		},
		methods: {
			async initData() {
				try{
						const res = await getAgreeList();
						console.log(res)
						if (res.statusCode == '0000') {
								
							this.tableData = res.data.list;
							this.page.count = res.data.total;
							
						}else{
								throw new Error('获取数据失败');
						}
				}catch(err){
						console.log('获取数据失败', err);
				}
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				this.page.currentPage = val;
				this.initData()
			},
      handleEdit (index, row) {
        this.$router.push({
          path: '/addAgreement?type='+row.type
        })
      },
      async handleDelete(index, row) {
      		try{
      				const res = await deleteAgree(row.id);
      				if (res.statusCode == '0000') {
      						this.$message({
      								type: 'success',
      								message: '删除成功'
      						});
      						this.tableData.splice(index, 1);
      				}else{
      						this.$message({
      								type: 'error',
      								message: err.message
      						});
      				}
      		}catch(err){
      				
      				console.log('删除失败',err)
      		}
      },
			
		},
	}
</script>

<style lang="less">
	@import '../style/mixin';

	.table_container {
		padding: 20px;
	}

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
</style>
