<template>
		<el-pagination background layout="prev, pager, next" :page-size="page.size" @current-change="currentChange" :total="page.total">
		</el-pagination>
</template>

<script>
	export default {
		props: {
			page: {
				total: 0,
				num: 1,
				size: 15
			}
		},
		data() {
			return {
				
			}
		},
		components: {
			
		},
		methods: {
			currentChange (p) {
				this.$emit("currentChange",p)
			},
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	
</style>