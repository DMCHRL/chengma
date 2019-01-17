<template>
	<div class="content_box">
        <my-header :leftOptions="headOption" ></my-header>
		<!-- <div class="close_box myback">
			<x-icon type="ios-close-empty" size="40" @click="closePage"></x-icon>
		</div> -->
        <div class="hui_content">
            <iframe :src="url"></iframe>
        </div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '',backText: '返回',showBack: true},
			}
		},
		computed: {
			url () {
				// console.log(this.$route.query.url)
				return this.$route.query.url;
			}
		},
		components: {
		},
		methods: {
			closePage () {
				this.$router.go(-1)
			}
		},
		mounted () {
		}
	}
</script>

<style scoped>
	
	.close_box {
        width: 100%;
        height: 75px;
		position: fixed;
		top: 0;
		left: 0;
		padding-top: 30px;
	}
	iframe {
		width: 100%;
        height: 100%;
		border: none;
		overflow-y: hidden;
		overflow-x: hidden;
	}
</style>