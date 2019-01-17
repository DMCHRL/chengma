<template>
	<div class="fillcontain">
		<head-top></head-top>
		<div class="table_container">
			<el-table :data="list" style="width: 100%">
				<el-table-column prop="login" label="登陆账户" width="180">
				</el-table-column>
				<el-table-column prop="firstName" label="名称" width="220">
				</el-table-column>
				<el-table-column prop="mobile" label="手机号" width="180">
				</el-table-column>
				<el-table-column prop="email" label="邮箱">
				</el-table-column>
			</el-table>
			<div class="Pagination" style="text-align: left;margin-top: 10px;">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.currentPage"
				 :page-size="page.size" layout="total, prev, pager, next" :total="page.count">
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import headTop from '../components/headTop'
// 	import {
// 		adminList,
// 		adminCount
// 	} from '@/api/getData'
	export default {
		data() {
			return {
				list: [],
				currentRow: null,
				page: {
					size: 20,
					count: 0,
					currentPage: 1,
				}
			}
		},
		components: {
			headTop,
		},
		created() {
			// this.getAdmin();
		},
		methods: {
			async initData() {
				try {
					const countData = await adminCount();
					if (countData.status == 1) {
						this.count = countData.count;
					} else {
						throw new Error('获取数据失败');
					}
					this.getAdmin();
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.offset = (val - 1) * this.limit;
				this.getAdmin()
			},
			async getAdmin() {
				try {
					const res = await adminList({
						page_number: this.page.currentPage, //	是	string	页码
						page_size: this.page.size, //	是	string	每页大小
						formParams: {}
					});

					console.log(res)
          
					if (res.statusCode == '0000') {
						this.list = res.data.list;
            this.page.count = res.data.total_count
					} else {
						throw new Error(res.message)
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			}
		},
	}
</script>

<style lang="less">
	@import '../style/mixin';

	.table_container {
		padding: 20px;
	}
</style>
