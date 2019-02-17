<template>
	<div class="content_box">
		<div class="mine_top backimg1">
			<x-header :left-options="headOption">个人中心</x-header>
			<div class="m_text">
				<router-link to="/set">
					<div>
						<div class="img_box">
							<img :src="userMess.headImg" />
						</div>
						<p v-text="userMess.userName">--</p>
					</div>
					<div class="xicon_box">
						<x-icon type="ios-arrow-right" size="30"></x-icon>
					</div>
				</router-link>
			</div>
		</div>

    <div class="hui_content" id="hui-content">

		<div class="mine_list">
			<!--<router-link to="/order">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_09.png" />
					</div>
					<div class="min_box flex_one">
						<span>我的订单</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>-->
			<!-- <router-link to="/notice">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_01.png"/>
					</div>
					<div class="min_box flex_one">
						<span :class="noticeNum > 0 ? 'active':''">消息通知</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link> -->
			<router-link to="/account">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_05.png" />
					</div>
					<div class="min_box flex_one">
						<span>账号管理</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>
      <router-link to="/agreement?type=ABOUT_STRATEGY&name=跟单说明书">
      	<div class="item_link flex_bet flex_align_center">
      		<div class="icon_box">
      			<img src="../../assets/img/4_10.png" />
      		</div>
      		<div class="min_box flex_one">
      			<span>跟单说明书</span>
      		</div>
      		<div class="arrow_right"></div>
      	</div>
      </router-link>
			<router-link to="/agreement?type=USER_PROTOCOL&name=用户协议">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_02.png" />
					</div>
					<div class="min_box flex_one">
						<span>用户协议</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>
			<!--<router-link to="/point">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_03.png" />
					</div>
					<div class="min_box flex_one">
						<span>积分钱包</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>-->

			<!--<div class="item_link flex_bet flex_align_center" @click="show11 = true">
				<div class="icon_box">
					<img src="../../assets/img/4_04.png" />
				</div>
				<div class="min_box flex_one">
					<span>联系客服</span>
				</div>
				<div class="arrow_right"></div>
			</div>-->
			<!--<router-link to="/set">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_05.png"/>
					</div>
					<div class="min_box flex_one">
						<span>个人设置</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>-->
			<router-link to="/agreement?type=ABOUT_US&name=关于我们">
				<div class="item_link flex_bet flex_align_center">
					<div class="icon_box">
						<img src="../../assets/img/4_06.png" />
					</div>
					<div class="min_box flex_one">
						<span>关于我们</span>
					</div>
					<div class="arrow_right"></div>
				</div>
			</router-link>

			<div class="item_link flex_bet flex_align_center" @click="downLoadMt4">
				<div class="icon_box">
					<img src="../../assets/img/4_07.png" />
				</div>
				<div class="min_box flex_one">
					<span>下载Meta Trader 4</span>
				</div>
				<div class="arrow_right"></div>
			</div>



		</div>

		<contact-box :show='show11' @close="closeContactBox"></contact-box>

     </div>
	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import ContactBox from '../common/ContactBox'
	import {
		XHeader
	} from 'vux'
	export default {
		data() {
			return {
				mess: {},
				headOption: {
					showBack: false,
					title: '我的'
				},
				show11: false
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess', 'noticeNum']),
		},
		components: {
			XHeader,
			ContactBox
		},
		methods: {
			// ...mapActions(['getNoticeNum']),
			closeContactBox() {
				this.show11 = false;
			},
			downLoadMt4() {
				this.$router.push({
					path: '/webview?url=https://sj.qq.com/myapp/detail.htm?apkName=com.ifuture.mt4'
				})
				//				location.open = "http://a.app.qq.com/o/simple.jsp?pkgname=net.metaquotes.metatrader4";
			},
		},
		activated() {

		}
	}
</script>

<style scoped>

  .hui_content {
    padding-top: 200px;
    padding-bottom: 60px;
  }
	.mine_top {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 200px;
    z-index: 99;
		box-sizing: border-box;
		padding-top: 30px;
		background-size: 100% 100%;
	}

	.m_text {
		position: relative;
    padding-top: 10px;
	}

	.m_text .img_box {
		width: 70px;
		height: 70px;
		border: 2px solid #fcc8c8;
		border-radius: 50%;
		overflow: hidden;
		margin: 0 auto;
	}

	.m_text p {
		text-align: center;
		color: #fff;
		font-size: 16px;
		line-height: 40px;
	}

	.m_text .xicon_box {
		position: absolute;
		top: 27%;
		right: 10%;
	}

	.vux-x-icon {
		fill: #fff;
	}

	.mine_list {
		margin-top: 0.2666rem;
		background-color: #fff;
	}

	.item_link {
		height: 1.4rem;
		border-bottom: 1px solid #ededed;
		padding: 0 0.2666rem;
	}

	.item_link:active {
		background-color: #ededed;
	}

	.item_link>span {
		color: #ccc;
		font-size: 0.4rem;
		font-family: "宋体";
	}

	.item_link .min_box span {
		font-size: 0.4rem;
		color: #666;
		margin-left: 0.2666rem;
	}

	.item_link .min_box span.active:after {
		content: '';
		display: inline-block;
		width: 0.2133rem;
		height: 0.2133rem;
		border-radius: 50%;
		background-color: #ff4c39;
		position: relative;
		bottom: 0.2rem;
		left: 0.1333rem;
	}

	.icon_box img {
		width: 0.6666rem;
		height: 0.6666rem;
	}
</style>
