<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content">
            
        
		<my-swiper :list="bannerList"></my-swiper>
		<div class="list_box">
			<template v-for="item in list">
				
				<div class="item_box flex_around flex_align_center">
					<div class="left flex_one">
						<h3>{{item.sinksName}}</h3>
						<span class="short_line"></span>
						<span>{{item.sinksName2}}</span>
						<p>{{item.explain}}</p>
						<router-link :to="'/ech-details?id='+ item.id"><button class="myback">点击查看</button></router-link>
					</div>
					<div class="right">
						<router-link :to="'/ech-details?id='+ item.id"><img :src="item.listImg"/></router-link>
					</div>
				</div>
				
			</template>
			
			<!-- <spinner-loading v-show="isloading" >
					<p slot='text'>努力加载中...</p>
			</spinner-loading > -->
			
			<div v-show="isloading"  class="trip_box">
				<spinner :type="'ios'" size='30px'></spinner>
			</div>
			
			<div v-show="notrip" class="trip_box">
				<img src="../../assets/img/h_13.png"/>
				<p>暂无汇商数据</p>
			</div>
			
			
		</div>
        
        </div>
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import SpinnerLoading from "../common/SpinnerLoading"
	import MySwiper from "../common/Swiper"
	export default {
		data() {
			return {
				headOption: {title: '汇商',showBack: false},
				bannerList: [],
				list: [],
				notrip: false,
				isloading: false
			}
		},
		components: {
			MySwiper,
			SpinnerLoading
		},
		methods: {
			...mapActions(['getNoticeNum']),
			getBannerList () {
				let _this = this;
				_this.$fetch("/api/hpp_advertisement/loadBySinks").then((res) => {
//					console.log(res)
					_this.bannerList = res.data.list;
				})
			},
			initPage () {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				_this.$fetch("/api/hpp_sinks/findList").then((res) => {
//					console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = res.data.list;
					
					if (res.data.list.length) {
						_this.notrip = false;
					}else {
						_this.notrip = true;
					}
				})
			}
		},
		mounted () {
			this.getBannerList();
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	
	.list_box {
		background-color: #f5f5f5;
		padding: 0 0.2933rem;
		padding-top: 0.2666rem;
/*		padding-bottom: 1.3333rem;*/
	}
	.item_box {
		background-color: #fff;
		border-radius: 0.2rem;
		padding: 0.48rem;
		margin-bottom: 0.2666rem;
	}
	.left h3 {
		font-size: 0.46rem;
		color: #333;
		line-height: 0.8666rem;
		font-weight: normal;
	}
	.left span.short_line {
		display: block;
		border-top: 2px solid #666;
		width: 1.3333rem;
	}
	.left span {
		font-size: 0.32rem;
		color: #999;
		display: block;
		line-height: 0.6666rem;
	}
	.left p {
		font-size: 0.3rem;
		color: #666;
	}
	.left button {
		color: #fff;
		font-size: 0.3rem;
		padding: 0.1333rem 0.7rem;
		border-radius: 0.4rem;
		margin-top: 0.2666rem;
	}
	
	.right {
		width: 3.2rem;
	}
	
	.right img {
		width: 3.2rem;
	}
</style>