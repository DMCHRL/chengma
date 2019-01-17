<template>
	<flexbox :gutter="0" wrap="wrap">
		<flexbox-item :span="1/3">
			<router-link to="/online">
				<div class="flex-box pad_top" @click="countFlag('video')">
					<img src="../../assets/img/grid_1.png" />
					<p>交易战法视频</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3">
			<router-link to="/training">
				<div class="flex-box pad_top" @click="countFlag('course')">
					<img src="../../assets/img/grid_2.png" />
					<p>交易面对面</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3">
			<router-link to="/exam">
				<div class="flex-box pad_top" @click="countFlag('exam')">
					<img src="../../assets/img/grid_3.png" />
					<p>交易师考证</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3">
			<div class="flex-box" @click="toLive">
				<img src="../../assets/img/grid_4.png" />
				<p>视频直播</p>
			</div>
		</flexbox-item>

		<flexbox-item :span="1/3">
			<router-link to="/application">
				<div class="flex-box" @click="countFlag('strategy')">
					<img src="../../assets/img/grid_5.png" />
					<p>策略入驻</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3" >
			<div class="flex-box" @click="share">
				<img src="../../assets/img/grid_10.png" />
				<p>分享APP</p>
			</div>
		</flexbox-item>

		<flexbox-item :span="1/3">
			<router-link to="/point">
				<div class="flex-box" @click="countFlag('integral')">
					<img src="../../assets/img/grid_7.png" />
					<p>积分钱包</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3">
			<router-link to="/notice">
				<div class="flex-box" @click="countFlag('notice')">
					<div v-if="noticeNum > 0" class="badge_box"><badge :text="noticeNum"></badge></div>
					<img src="../../assets/img/grid_8.png" />
					<p>消息通知</p>
				</div>
			</router-link>
		</flexbox-item>
		<flexbox-item :span="1/3">
			<div class="flex-box" @click="toContact">
				<img src="../../assets/img/grid_9.png" />
				<p>联系客服</p>
			</div>
			
			<contact-box :show='show11' @close="closeContactBox"></contact-box>
      	
		</flexbox-item>

	</flexbox>
</template>

<script>
	import {mapState} from 'vuex'
	import ContactBox from '../common/ContactBox'
	import { Flexbox, FlexboxItem,Badge } from 'vux'
	export default {
		data() {
			return {
				show11: false,
				// unreadNum: null
			}
		},
		computed: {
			...mapState(['noticeNum']),
		},
		components: {
			Flexbox,
			FlexboxItem,
			ContactBox,
			Badge
		},
		methods: {
			closeContactBox () {
				this.show11 = false;
			},
			toContact () {
				this.show11 = true;
				this.countFlag('server');
			},
			toLive () {
				this.countFlag ('live');
				this.$router.push({
					path: '/live'
				})
//				this.$router.push({
//					path: '/webview?url=https://www.6.cn/?mks=cpcbaidu&mkk=%E6%89%8B%E6%9C%BA%E7%9B%B4%E6%92%AD%E5%B9%B3%E5%8F%B0&mkc=a0447&audience=301932'
//				})
			},
			countFlag (val) {
				this.$fetch('/api/hpp_navigation/countFlag/'+val).then((res) => {
//					console.log(res)
				})
			},
			actionClick(e) {
				if (e == 'menu1') {
					location.href = "mqqwpa://im/chat?chat_type=wpa&uin=1685211398&version=1&src_type=web&web_src=oicqzone.com"
				}else {
					location.href = "tel:13816374697"
				}
			},
			share () {
				this.countFlag('share')
				this.$emit('share')
			},
// 			getNotice () {
// 				let _this = this;
// 				_this.$fetch("/api/hpp_notice_sign/unreadNum/"+_this.user.mobile).then((res) => {
// //					console.log(res)
// 					_this.unreadNum = res.data.unreadNum;
// 				})
// 			}
		},
		activated () {
			// this.getNotice();
		}
	}
</script>

<style scoped>
	
	.flex-box {
		position: relative;
		text-align: center;
		padding-bottom: 0.2666rem;
		background-color: #fff;
	}
	.pad_top {
		padding-top: 0.2666rem;
	}
	
	.flex-box img {
		width: 1.5733rem;
		height: 1.5733rem;
		margin-bottom: 0.2666rem;
	}
	
	.flex-box p {
		font-size: 0.2933rem;
		color: #666666;
	}
	.badge_box {
		position: absolute;
		top: 0;
		right: 23px;
	}
</style>