<template>

	<div class="content_box">
        <div class="head_bar myback"></div>
        <div class="hui_content">
            <my-swiper :list="bannerList"></my-swiper>
            <home-navigation @share="toShare"></home-navigation>
            <news-section :newsList="newsList"></news-section>
        </div>
	</div>
</template>

<script>
	import {mapState,mapActions	} from 'vuex'
    import { shareUrl,shareImgUrl } from '../../utils/util'
	import MyHeader from "../common/Header"
	import MySwiper from "../common/Swiper"
	import HomeNavigation from "../home/HomeNavigation"
	import NewsSection from "../home/NewsSection"
	export default {
		data() {
			return {
				headOption: {
					title: '汇添溢',
					backText: '',
					showBack: false
				},
				bannerList: [],
				newsList: [],
				shares: null,
				sweixin: null,
				buttons: [
					{
						title: '微信好友',
						extra: {
							scene: 'WXSceneSession'
						}
					},
					{
						title: '微信朋友圈',
						extra: {
							scene: 'WXSceneTimeline'
						}
					},
					//{title:'我的收藏',extra:{scene:'WXSceneFavorite'}}
				],
				onFetching: false,
				scrollerHeight: '-115',
				pushTimer: null,
				isWXSceneTimeline: false,
            friendMsg: {
                type: 'web',
                thumbs: ['../../assets/img/grid_5.png'],
                href: shareUrl,
                title: '“汇添溢”投教联盟APP',
                content: '“汇添溢”投教联盟APP为第三方教育共享软件，是为进入市场的人们通过名师指导、面对面授课，经过系统的学习，准确找到适合自己的获胜战法，从而提升自己的战法水准。所培训内容以人社部审定的“中国金融交易师”能力等级标准为教材，并由教材编撰人胡国政院长主训。'
            },
            CircleOfFriendsMsg: {
                type:'image',
                pictures: [shareImgUrl]
            }
			}
		},
		watch: {
			onFetching(val) {
				console.log(val)
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
		},
		components: {
			MyHeader,
			MySwiper,
			HomeNavigation,
			NewsSection
		},
		methods: {
			...mapActions(['getNoticeNum']),
            //登陆积分
            loginIntegral () {
            	let _this = this;
            	_this.$fetch("/api/hpp_integral/login/"+ _this.userInfo.mobile).then(e=>{
            		// console.log(e)
            	})
            },
			scroll(posi) {
				let _this = this;
				// console.log(posi)
				if (posi.top <= 90) {
					_this.onFetching = false;
					if (!_this.pushTimer) return;
					clearTimeout(_this.pushTimer);
					_this.pushTimer = null;
				}
			},
			onFetchingFalse() {
				let _this = this;
				_this.onFetching = false;
			},
			onScrollBottom() { //滑动至底部触发
				let _this = this;
				_this.onFetching = true;

				if (_this.pushTimer) return;

				_this.pushTimer = setTimeout(function() {
					_this.$router.push({
						path: '/newslist'
					})
				}, 3000)
			},
			//获取新闻
			getNewsList() {
				let _this = this;
				let datas = {
					page_number: 1, //	是	int	当前页
					page_size: 5, //	是	int	每页大小
					formParams: { //	是	json	查询条件
						orderByColumn: 'updateTime',
						sort: 'DESC'
					}
				}
				_this.$post("/api/hpp_news/pageList", datas).then((res) => {
					_this.newsList = res.data.list;
				})
			},
            //获取广告图
			getBannerList() {
				let _this = this;
				_this.$fetch("/api/hpp_advertisement/loadByHome").then((res) => {
					_this.bannerList = res.data.list;
				})
			},

			//微信分享
			toShare() {
				let _this = this;
				_this.sweixin ? plus.nativeUI.actionSheet({
					cancel: '取消',
					buttons: _this.buttons
				}, function(e) {
					(e.index > 0) && _this.share(_this.sweixin, _this.buttons[e.index - 1]);
				}) : plus.nativeUI.alert('当前环境不支持微信分享操作!');

			},
			share(srv, button) {
				let _this = this;
				if (!srv) {
					_this.$vux.toast.text('无效的分享服务！');
					return;
				}

                let msg = {};
				if (button.extra.scene == 'WXSceneTimeline') {
                    //点击分享朋友圈
					_this.isWXSceneTimeline = true;
                    msg = _this.CircleOfFriendsMsg;
				}else {
                    msg = _this.friendMsg;
                }

				button && (msg.extra = button.extra);
				// 发送分享
				if (srv.authenticated) {
					//alert('---已授权---');
					_this.doShare(srv, msg);
				} else {
					//alert('---未授权---');
					srv.authorize(function() {
						_this.doShare(srv, msg);
					}, function(e) {
						_this.$vux.toast.text('认证授权失败：' + JSON.stringify(e));
					});
				}
			},
			//分享朋友圈加积分
			shareToCommunity () {
				let _this = this;
				_this.$fetch("/api/hpp_integral/shareToCommunity/"+_this.userInfo.mobile).then()
			},
			//分享好友加积分
			shareToFriend () {
				let _this = this;
				_this.$fetch("/api/hpp_integral/shareToFriend/"+_this.userInfo.mobile).then()
			},
			// 发送分享
			doShare(srv, msg) {
				let _this = this;
                //后台奖励积分
				if (_this.isWXSceneTimeline) {
					_this.shareToCommunity();
				}else {
					_this.shareToFriend();
				}
				srv.send(msg, function() {
					_this.$vux.toast.text('分享成功！');
				}, function(e) {
					// alert(JSON.stringify(e))
					_this.$vux.toast.text('分享失败！');
				});
			},
			//获取本地分享模块
			updateSerivces() {
				let _this = this;
				plus.share.getServices(function(s) {
					_this.shares = {};
					for (var i in s) {
						var t = s[i];
						_this.shares[t.id] = t;
					}
					_this.sweixin = _this.shares['weixin'];
				}, function(e) {
					_this.$vux.toast.text('获取分享服务列表失败：' + e.message);
				});
			},
			getCId() { //获取clientId，返回后台
				let _this = this;
				let info = plus.push.getClientInfo();
				//				_this.$vux.toast.text(JSON.stringify(info));
				let datas = {
					mobile: _this.userInfo.mobile, //	是	string	电话号码
					cid: info.clientid
				}
				_this.$post('/api/hpp_mobile_user/setCid', datas).then((res) => {

				})
			},
			// 获取本地进vuex
			pushVuex() {
				let _this = this;
				var user = localStorage.getItem('user')
				var mess = localStorage.getItem('userMess')
				_this.$store.commit("setUserInfo", JSON.parse(user));
				_this.$store.commit("setUserMess", JSON.parse(mess));
			},
			plusReady() {
				this.updateSerivces();
				this.getCId();
			}
		},
		activated() {
			let _this = this;
			_this.pushVuex(); //开发环境

			let token = localStorage.getItem('id_token');
			if (token) {
				_this.getBannerList();
				_this.getNewsList();
                _this.loginIntegral();
                _this.getNoticeNum();
			} else {
				_this.$router.replace({
					path: '/login'
				})
			}
			document.addEventListener('plusready', _this.plusReady, false);
		}
	}
</script>

<style scoped>

    .hui_content {
        padding-top: 30px;
    }
    .head_bar {
        height: 30px;
        width: 100%;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        z-index: 9;
    }

	.call_box {
		width: 1.0666rem;
		height: 1.0933rem;
		position: fixed;
		bottom: 1.8rem;
		right: 0.6rem;
	}

	.call_box img {
		width: 1.0666rem;
		height: 1.0933rem;
	}

	.more_data {
		text-align: center;
		padding: 15px 0;
	}

	.more_data p {
		color: #999;
		font-size: .2rem;
		padding-top: 10px;
	}
</style>
