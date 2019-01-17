<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		<div v-html="context"></div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '跟单说明书',backText: '',showBack: true},
				type: 'ABOUT_STRATEGY',
				context: ''
			}
		},
		components: {},
		methods: {
			getNews () {
				let _this = this;
				_this.$fetch("/api/hpp_protocol/getType/"+_this.type).then((res) => {
//					console.log(res)
					if (res.data) {
						_this.context = res.data.context;
					}
				})
			},
		},
		activated() {
			this.getNews();
		}
	}
</script>

<style scoped>
	.content_box {
		padding: 0.2666rem;
	}
	
	.content_box p {
		text-indent: 2em;
		font-size: 0.2933rem !important;
		color: #666;
		line-height: 0.6666rem;
	}
</style>