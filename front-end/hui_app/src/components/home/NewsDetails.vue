<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        <div class="hui_content">
		<div class="art_head">
			<h3>{{news.title}}</h3>
			<div class="flex_bet">
				<span>已有{{news.read}}人阅读</span>
				<div class="">
					<span class="type">{{news.type}}</span>
					<span v-text="(news.updateTime+'.').substring(5,19)"></span>
				</div>
			</div>
		</div>
		<div class="art_body" v-html="news.context">
			
		</div>
        </div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '新闻详情',backText: '',showBack: true},
				news: {}
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			}
		},
		components: {
			
		},
		methods: {
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_news/read/"+_this.id,).then((res) => {
					// console.log(res)
					_this.news = res.data;
				})
			}
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.content_box {
		background-color: #fff;
	}
	.art_head {
		padding: 0.2666rem;
	}
	.art_head h3 {
		font-size: 0.4853rem;
		color: #333;
		font-weight: normal;
		margin-bottom: 0.2666rem;
	}
	.art_head span {
		font-size: 0.32rem;
		color: #999;
	}
	.art_head span.type {
		color: #e51534;
		margin-right: 0.2666rem;
	}
	.art_body {
		padding: 0.2666rem;
	}
	.art_body img {
		width: 100%;
	}
	
</style>