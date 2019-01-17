<template>
		<div class="news_box">
			<div class="news_header flex_bet">
				<div>
					<div>财经资讯</div>
					<div class="shutline myback"></div>
				</div>
				<!-- <router-link to="/newslist">
					<span>更多>></span>
				</router-link> -->
			</div>
			<div class="news_list">
				<template v-for="item in newsList">
					<router-link :to="'/news-details?id='+item.id">
						<div class="item_box flex_bet">
								<div class="news_text flex_one">
									<span class="title">{{item.title}}</span>
									<div class="foot flex_bet">
										<span class="one">{{item.type}}</span>
										<span class="two">已有{{item.read}}人阅读</span>
										<span class="three" v-text="(item.updateTime+'.').substring(5,19)"></span>
									</div>
								</div>
								<div class="news_img">
									<img :src="item.listImg"></img>
								</div>
						</div>
					</router-link>
					
				</template>
                <router-link to="/newslist">
                    <p class="trip_p">获取更多资讯>></p>
                </router-link>
				<load-more v-show="!newsList.length"></load-more>
				<div class="trip_box" v-show="notrip">
					<img src="../../assets/img/no_1.png"/>
					<p>暂无新闻</p>
				</div>
			</div>
		</div>
</template>

<script>
	import { LoadMore } from 'vux'
	export default {
		props: ['newsList'],
		data() {
			return {
				notrip: false
			}
		},
		filters: {
			strCut (val) {
				return val.substring(5,19);
			}
		},
		watch: {
			newsList (val) {
				if (val.length) {
					this.notrip = false;
				}else {
					this.notrip = true;
				}
			}
		},
		components: {
			LoadMore
		},
		methods: {
			
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	.news_box {
		background-color: #fff;
		border-top: 0.1333rem solid #f4f4f4;
		padding-bottom: 0.1rem;
	}
	.news_header {
		line-height: 0.9333rem;
		border-bottom: 0.0133rem solid #f4f4f4;
		margin-bottom: 0.0666rem;
		box-sizing: border-box;
		padding: 0 0.2666rem;
	}
	.news_header div {
		font-size: 0.4rem;
		color: #333;
	}
	.news_header div.shutline {
		height: 0.08rem;
		width: 1.55rem;
	}
	
	.news_list .item_box {
		padding: 0.3333rem;
		border-bottom: 2px solid #fff;
	}
	.news_list  a {
		display: block;
		background-color: #fff;
	}
	.news_list  a:visited{
		background-color: #f8f8f8;
	}
	.news_list .item_box {
		padding: 0.3333rem;
		border-bottom: 0.0266rem solid #f4f4f4;
	}
	.news_text {
		height: 1.6rem;
		position: relative;
		padding-right: 0.2666rem;
	}
	.news_text span.title {
		font-size: 0.33rem;
		color: #666;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 3;
	}
	.news_text .foot {
		position: absolute;
		bottom: 0;
		width: 100%;
		box-sizing: border-box;
		padding-right: 0.4666rem;
	}
	.news_text .foot span {
		font-size: 0.2933rem;
	}
	.news_text span.three {
		color: #999;
	}
	.news_text span.one {
		color: #e51534;
	} 
	.news_text span.two {
		color: #ccc;
	} 
	.news_img {
		width: 2.4266rem;
		height: 1.6rem;
		border-radius: 0.1rem;
		overflow: hidden;
	}
	.news_img img {
		width: 100%;
		min-height: 100%;
	}
</style>