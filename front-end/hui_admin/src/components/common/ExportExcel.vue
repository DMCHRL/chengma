<template>
	<div>
		<button type="primary" @click="outExe">导出Excel</button>
	</div>
</template>

<script>
	export default {
		data () {
			return {
				excelData: []
			}
		},
		methods: {
			outExe() {
				this.$confirm('此操作将导出excel文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.excelData = this.list //你要导出的数据list。
					this.export2Excel()
				}).catch(() => {

				});
			},
			export2Excel() {
				var that = this;
				require.ensure([], () => {
					const {
						export_json_to_excel
					} = require('../../excel/Export2Excel'); //这里必须使用绝对路径
					const filterVal = ['username', 'phone', 'email', 'createAt', 'trainName', 'status']; // 导出的表头名
					const tHeader = ['姓名', '手机', '邮箱', '报名时间', '报名课程', '支付状态']; // 导出的表头字段名
					const list = that.excelData;
					const data = that.formatJson(filterVal, list);
					
					export_json_to_excel(tHeader, data, `[${that.today}]课程报名excel`); // 导出的表格名称，根据需要自己命名
				})
			},
			formatJson(filterVal, jsonData) {
				return jsonData.map(v => filterVal.map(j => v[j]))
			}
		}
	}
</script>

<style scoped>
	
</style>