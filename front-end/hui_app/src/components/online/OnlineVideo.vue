<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>

        <div class="hui_content">

		<my-swiper :list="bannerList"></my-swiper>
		<!--每日推荐-->
		<gongge-list :list="FindDayList"></gongge-list>
		<div class="sec_box sec_2">
			<div class="titling">
				<span>精选专辑</span>
			</div>
			<ul class="link_body flex_col">

				<li class="link_box" v-for="item in list">
					<router-link :to="'/vlist?id='+item.id">
					  	<div class="flex_bet">
					  		<div class="img_box">
					  			<img :src="item.img"/>
					  		</div>
					  		<div class="mid_box flex_one">
					  			<h4>{{item.videoTypeName}}</h4>
					  			<p v-html="item.text"></p>
					  		</div>
					  		<div class="right_box flex_col">
					  			<span>共{{item.phase}}期</span>
					  			<!-- <span class="xian" v-if="item.dayPush == 'NO'">免费</span>
					  			<span class="fu" v-else>付费</span> -->
					  		</div>
					  	</div>
				  	</router-link>
				</li>

			</ul>
		</div>

        </div>
	</div>
</template>

<script>
	import MySwiper from "../common/Swiper"
	import GonggeList from "../online/GonggeList"
	export default {
		data() {
			return {
				headOption: {title: '交易学习视频',backText: '',showBack: true},
				list: [],
				bannerList: [],
				FindDayList: [],
			}
		},
		components: {
			MySwiper,
			GonggeList
		},
		methods: {
			getFindDayPush () {
				let _this = this;
				_this.$fetch("/api/hpp_video_type/findDayPush").then((res) => {
					_this.FindDayList = res.data;
				})
			},
			getBannerList () {
				let _this = this;
				_this.$fetch("/api/hpp_advertisement/loadByVideo").then((res) => {
					_this.bannerList = res.data.list;
				})
			},
			initPage () {
				let _this = this;
				this.$post('/api/hpp_video_type/findAll',{}).then((res) => {
					// console.log(res)
					_this.list = res.data.list;
				})
			}
		},
		mounted () {
			this.getBannerList();
		},
		activated () {
			this.initPage();
			this.getFindDayPush();
		}
	}
</script>

<style scoped>
	.sec_box {
		background-color: #fff;
		margin-bottom: 0.2666rem;
	}
	.titling {
		padding: 0.2666rem 0.4rem;
	}
	.titling span{
		font-size: 0.4rem;
		color: #333333;
	}

	.link_body {
		padding: 0 0.2933rem ;
	}
	.link_box {
		padding: 0.2933rem 0;
		border-top: 1px solid #ccc;
	}
	.link_box .img_box {
		width: 2.6666rem;
		height: 1.8666rem;
		border-radius: 0.1333rem;
        overflow: hidden;
	}
	.link_box .mid_box{
		padding: 0 0.2666rem;
	}
	.link_box .mid_box h4 {
		font-size: 0.3733rem;
		color: #333;
		line-height: 0.6666rem;
	}
	.link_box .mid_box p {
		font-size: 0.3rem;
		color: #999;
	}
	.link_box .right_box span{
		font-size: 0.3rem;
		color: #999;
		line-height: 0.6666rem;
		text-align: right;
	}
	.link_box .right_box span.xian {
		color: #28c76f;
		font-size: 0.3466rem;
		display: block;
		margin-top: 0.3666rem;
	}
	.link_box .right_box span.fu{
		color: #ff5752;
		font-size: 0.3466rem;
		display: block;
		margin-top: 0.3666rem;
	}

</style>
