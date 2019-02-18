<template>
	<div class="content_box">

		<my-header :leftOptions="headOption" ></my-header>

        <div class="hui_content">

		<swiper
		v-model="swiper_index"
		height="140px"
		:show-dots="true"
		:dots-position="'center'"
		>
			<template v-for="(item,index) in accList" >
				<swiper-item :key="index">
					<div class="swiper_box myback flex_bet flex_align_center">
						<div class="left_box">
							<div class="img_box">
								<img :src="userInfo.imageUrl" />
							</div>
						</div>
						<div class="mid_box flex_one" >
							<p>{{item.account}}</p>
							<div class="flex_row">
								<span v-text="item.monthProfit ? item.monthProfit: '0.00'"></span>
								<div class="small_box">
									<p>累计收益率</p>
									<p>%</p>
								</div>
							</div>
							<p v-if="item.strategyName">跟单策略：{{item.strategyName}} |
								<router-link :to="'/strategy-details?account='+ item.strategyAccount">
									<img src="../../assets/img/2_02.png" />
								</router-link>
							</p>
							<p v-else>跟单策略：暂无跟单策略</p>
						</div>
						<div class="right_box">
							<router-link :to="'/mystrategy?account='+ item.account">
								<span>我的账号</span>
							</router-link>
						</div>
					</div>
				</swiper-item>
			</template>

			<swiper-item v-if="!accList.length">
				<div class="swiper_box myback flex_bet flex_align_center">
					<div class="left_box">
						<div class="img_box">
							<img :src="userInfo.imageUrl" />
						</div>
					</div>
					<div class="mid_box flex_one" >
						<h3>昵称：{{userInfo.firstName}}</h3>
						<p>跟单账户：暂无</p>
						<p>跟单策略：暂无</p>
					</div>
				</div>
			</swiper-item>

		</swiper>



		<div class="sec_2">
			<div class="title_box flex_center flex_align_center">
				<img src="../../assets/img/2_04.png" />
				<span>策略信号</span>
				<img src="../../assets/img/2_04.png" />
			</div>
			<div class="list_box">

				<template v-for="item in list">
					<div class="item_box flex_around flex_align_center backimg0">
						<div class="left">
							<h3>{{item.strategyName}}</h3>
							<p>{{item.strategyName2}}</p>
						</div>
						<div class="mid">
							<span v-text="item.incomeRate? item.incomeRate: '0.00'"></span><i>%</i>
							<p>累计收益率</p>
						</div>
						<div class="right">
							<router-link :to="'/strategy-details?account='+ item.account">
								<button>策略详情</button>
							</router-link>
						</div>
					</div>
				</template>

				<!-- <spinner-loading v-show="isloading" >
					<p slot='text'>努力加载中...</p>
				</spinner-loading > -->

				<div class="trip_box" v-show="isloading" >
					<spinner :type="'ios'" size='30px'></spinner>
				</div>

				<div class="trip_box" v-show="notrip">
					<img src="../../assets/img/h_12.png"/>
					<p>暂无策略数据</p>
				</div>

			</div>
		</div>

        </div>
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import { Swiper,SwiperItem } from 'vux'
	import SpinnerLoading from "../common/SpinnerLoading"
	export default {
		data() {
			return {
				headOption: {title: '策略',showBack: false},
				list: [],
				notrip: false,
				isloading: false,
				swiper_index: 0,
				accList: [],
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
		},
		components: {
			SpinnerLoading,
			Swiper,
			SwiperItem
		},
		methods: {
			...mapActions(['getNoticeNum']),
			getMyAccount () {
				let _this = this;
				_this.$fetch("/api/tlb-account/findByMobile").then((res) => {
					// console.log(res)
					_this.accList = res.data.list;
				})
			},
			initPage() {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				_this.$post("/api/hpp_strategy/findAll",{}).then((res) => {
					//console.log(res)
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
		activated () {
			this.initPage();
			this.getMyAccount();
		}
	}
</script>

<style scoped>

	.swiper_box {
		position: relative;
		height: 100%;
	}
	.left_box {
		padding: 0.6666rem;
	}
	.left_box .img_box {
		width: 1.8666rem;
		height: 1.8666rem;
		border: 0.1066rem solid #fcc8c8;
		border-radius: 50%;
		overflow: hidden;
	}
	.mid_box h3 {
		font-size: .4rem;
		color: #fff;
		margin-bottom: .05rem;
	}
	.mid_box p {
		color: #fff;
		font-size: 0.3733rem;
	}

	.mid_box p:last-of-type {
		font-size: 0.3rem;
	}

	.small_box p {
		font-size: 0.3rem;
		line-height: 0.5rem;
		margin-top: 0.1rem;
	}

	.mid_box span {
		color: #fff;
		font-size: 0.96rem;
		margin: 0 0.1333rem;
	}

	.mid_box img {
		position: relative;
		top: -1px;
		width: 0.28rem;
		height: 0.28rem;
	}

	.right_box {
		position: absolute;
		right: 0;
		top: 0;
	}

	.right_box span {
		color: #eb0021;
		font-size: 0.2933rem;
		background-color: #fff;
		display: inline-block;
		padding: 0.1333rem 0.6666rem;
		border-radius: 0.3666rem 0 0 0.3666rem;
	}

	.sec_2 {
		background-color: #f5f5f5;
	}

	.title_box {
		padding: 0.3333rem 0;
	}

	.title_box img {
		width: 0.64rem;
		height: 0.2133rem;
        margin-top: 3px;
	}

	.title_box span {
		color: #eb0021;
		font-size: 0.3733rem;
		padding: 0 0.1333rem;
	}

	.list_box {
		padding: 0 0.2933rem;
		padding-bottom: 1.3333rem;
	}

	.item_box {
		background-color: #fff;
		border-radius: 0.2rem;
		padding: 0.48rem 0;
		margin-bottom: 0.2666rem;
	}
	.left {
		width: 32%;
	}
	.left h3 {
		font-size: 0.4rem;
		color: #333;
		text-align: center;
		line-height: 0.8666rem;
	}

	.item_box p {
		font-size: 0.3rem;
		color: #aaaaaa;
		text-align: center;
	}

	.mid {
		border-right: 0.0133rem solid #cccccc;
		padding-right: 0.5rem;
		text-align: center;
	}

	.mid span {
		font-size: 0.6933rem;
		font-weight: bold;
		color: #ea4853;
	}

	.mid i {
		color: #ea4853;
		font-size: 0.4rem;
		font-weight: bold;
	}

	.right button {
		border: 1px solid #ea4853;
		color: #ea4853;
		font-size: 0.2666rem;
		border-radius: 0.3666rem;
		background-color: transparent;
		padding: 0.1333rem 0.4333rem;
	}
</style>
