<template>
	<div class="page_box">
		<el-pagination background layout="prev, pager, next" :page-size="page.size" @current-change="currentChange" :total="page.total" :current-page="page.num">
		</el-pagination>
	</div>
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
	.page_box {
		position: absolute;
		bottom: 20px;
		right: 50px;
		margin: 0 20px;
	}
</style>