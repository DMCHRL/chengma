<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		<div class="text_box" v-html="context">
			
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '积分说明',backText: '',showBack: true},
				type: 'POINT_COMMON',
				context: ''
			}
		},
		components: {
		},
		methods: {
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_protocol/getType/"+_this.type).then((res) => {
//					console.log(res)
					_this.context = res.data.context;
				})
			}
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.text_box {
		padding: 0.2666rem;
	}
</style>