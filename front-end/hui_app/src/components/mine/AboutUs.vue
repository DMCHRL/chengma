<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		<div class="sec_1">
			<div class="img_box">
				<img src="../../assets/img/logo.png"/>
			</div>
			<div id="" v-html="context">
				
			</div>
		</div>
		<!--<div class="sec_box">
			<h3>联系客服</h3>
			<p>客服QQ：3259502569</p>
			<p>客服电话：400-8939-522</p>
		</div>-->
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '关于我们',backText: '',showBack: true},
				context: '',
				type: 'ABOUT_US'
			}
		},
		components: {
		},
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
		activated () {
			this.getNews();
		}
	}
</script>

<style scoped>
	
	.sec_1 {
		margin: 0.2666rem;
		border-radius: 0.2666rem;
		background-color: #fff;
		padding: 0.2666rem;
		text-align: center;
	}
	.sec_1 img{
		border-radius: 50%;
		width: 2rem;
		height: 2rem;
	}
	.sec_1 p {
		font-size: 0.32rem;
		color: #666;
		line-height: 0.5333rem;
		text-indent: 2em;
	}
	.sec_box {
		padding: 0.5333rem;
	}
</style>