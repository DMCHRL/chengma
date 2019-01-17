<template>
	<div class="content_box no_footer">
		<my-header :leftOptions="headOption" ></my-header>
		<div class="video_box">
			<video controls="controls" autoplay="false" :src="video.videoUrl">
				<source src="http://tlb.txasfx.com/video/tlb_1.mp4" type="video/mp4">
				</source>
				<object width="" height="" type="application/x-shockwave-flash" data="myvideo.swf">
					<param name="movie" value="myvideo.swf" />
					<param name="flashvars" value="autostart=true&amp;file=myvideo.swf" />
				</object> 当前浏览器不支持 video直接播放，点击这里下载视频：
				<!--<a href="https://mp.weixin.qq.com/s/Vv2yCyttDJqF4PuduDaU4Q">下载视频</a>-->
			</video>
		</div>
		<div class="sec_box sec_1 flex_bet">
			<div class="" @click="swiper_index = 0">
				<span :class="swiper_index==0? 'active': ''">讲师</span>
			</div>
			<div class="" @click="swiper_index = 1">
				<span :class="swiper_index==1? 'active': ''">聊天</span>
			</div>
			<div class="" @click="swiper_index = 2">
				<span :class="swiper_index==2? 'active': ''">分享APP</span>
			</div>

			<div class="">
				<button @click="Attention" class="att_button" :class="isAtten?'active':''">
					<img v-show="!isAtten" src="../../assets/img/attention_0.png" />
					<i v-text="isAtten?'已关注':'关注'"></i>
				</button>
			</div>
		</div>
		<div class="sec_box sec_2 ">
			<swiper v-model="swiper_index" :height="swiperHeight" class="text-scroll" :show-dots="false" @on-get-height="changeHeight">
				<swiper-item>
					<div class="swiper-item swiper-item-1">
						<div class="person_box flex_row">
							<div class="img_box">
								<img src="../../assets/img/2_01.png" />
							</div>
							<div class="right_box flex_col">
								<h4>maggie</h4>
								<div>
									<span>房间号：<i>3242355</i></span>
									<span>粉丝数：<i>4227</i></span>
								</div>
								<span><i>#股票实战派</i></span>
							</div>
						</div>
						<div class="text_box">
							<h4>直播公告</h4>
							<p>寻找确定性的机会</p>
						</div>
						<div class="text_box">
							<h4>讲师简介</h4>
							<p>具备敏锐的嗅觉和听觉，通过各种细微的变化，判断大势所趋和风险所在，
								在极端环境下仍能全身而退。多年媒体从业经验，曾任四川卫视、山东卫视及多家媒体特邀嘉宾。
								股海沉浮13载，形成了自己独特的分析体系。擅长通过，精准把握大资金运作脉络，以凶悍又不乏稳健的框架闻名
								。在2017年初更是提前嗅出市场风格的转变，提前挖掘贵州茅台、海康威视、赣锋锂业等年度牛股。</p>
						</div>
					</div>
				</swiper-item>
				<swiper-item>
					<div class="swiper-item swiper-item-0">
						<div class="sec_box sec_3 ">
							<comment-list :commentList="commentList"></comment-list>
						</div>
						<div class="sec_box sec_bottom flex_bet myback">
							<div class="input_box flex_one">
								<input type="text" name="" id="" value="" v-model="commentText" placeholder="请输入评论内容(字数40字以内)" />
							</div>
							<button @click="Commit" :disabled="isDisabled">发表评论</button>
						</div>
					</div>

				</swiper-item>
				<swiper-item>
					<div class="swiper-item swiper-item-2">

					</div>
				</swiper-item>
			</swiper>
		</div>

	</div>
</template>

<script>
	import {
		Swiper,
		SwiperItem
	} from "vux";
	import SelectionSlider from "../online/SelectionSlider";
	import CommentList from "../online/CommentList";
	export default {
		data() {
			return {
				headOption: {title: '直播间',backText: '',showBack: true},
				video: {},
				commentText: "",
				isDisabled: false,
				commentList: [],
				swiperHeight: "380px",
				swiper_index: 0,
				isAtten: false,
				shares: null,
				sweixin: null,
				buttons: [{
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
					//				  {title:'我的收藏',extra:{scene:'WXSceneFavorite'}}
				]
			};
		},
		computed: {
			id() {
				return this.$route.query.id;
			},
			user() {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return null;
				}
			}
		},
		watch: {
			id(val) {
				this.initPage();
				this.getCommentList();
			},
			swiper_index(val) {
				if (val == 1) {
					location.href = "mqqwpa://im/chat?chat_type=wpa&uin=1685211398&version=1&src_type=web&web_src=oicqzone.com"
				} else if (val == 2) {
					this.toShare();
				}
			}
		},
		components: {
			SelectionSlider,
			CommentList,
			Swiper,
			SwiperItem
		},
		methods: {
			Attention() {
				this.isAtten = !this.isAtten;
			},
			changeHeight(h) {
				// console.log(h);
				this.swiperHeight = "500px";
			},
			Commit() {
				let _this = this;
				let datas = {
					context: _this.commentText,
					videoId: _this.id,
					mobile: _this.user.mobile
				};
				_this.isDisable = true;
				_this.$post("/api/hpp_comment/saveHppComment", datas).then(res => {
					console.log(res);
					_this.isDisable = false;
					if (res.statusCode == "0000") {
						_this.$vux.toast.text("评论成功", "middle");
						_this.commentText = "";
						_this.getCommentList();
					} else {
						_this.$vux.toast.text(res.msgCode, "middle");
					}
				});
			},
			getCommentList() {
				let _this = this;
				_this.$fetch("/api/hpp_comment/findForApp/" + this.id).then(res => {
					console.log(res);
					_this.commentList = res.data.list;
				});
			},
			initPage() {
				let _this = this;
				_this.$fetch("/api/hpp_video/showOnApp/" + this.id).then(res => {
					console.log(res);
					_this.video = res.data;
				});
			},
			//			分享
			toShare() {
				let _this = this;
				var msg = {
					type: 'web',
					thumbs: ['../assets/img/grid_5.png'],
					href: 'https://www.pgyer.com/hui88',
					title: '“汇添溢”投教联盟APP',
					content: '“汇添溢”投教联盟APP为第三方投资者教育及交易共享软件，是为进入投资市场的人们通过名师指导、面对面授课，准确找到适合自己的交易战法，减少在交易市场中的惨痛经历，从而提升自己的投资能力和交易水准。同时“汇添溢”投教联盟APP汇集了多个优质交易策略信号，为投资人实现同平台、跨平台的实时复制、跟随交易，让投资人更省时、省心、省力。'
				};

				_this.sweixin ? plus.nativeUI.actionSheet({
					cancel: '取消',
					buttons: _this.buttons
				}, function(e) {
					(e.index > 0) && _this.share(_this.sweixin, msg, _this.buttons[e.index - 1]);
				}) : plus.nativeUI.alert('当前环境不支持微信分享操作!');

			},
			share(srv, msg, button) {
				let _this = this;

				//				_this.$vux.toast.text(JSON.stringify(srv));
				if (!srv) {
					_this.$vux.toast.text('无效的分享服务！');
					return;
				}
				button && (msg.extra = button.extra);
				// 发送分享
				if (srv.authenticated) {
					//					_this.$vux.toast.text('---已授权---');
					_this.doShare(srv, msg);
				} else {
					//					_this.$vux.toast.text('---未授权---');
					srv.authorize(function() {
						_this.doShare(srv, msg);
					}, function(e) {
						_this.$vux.toast.text('认证授权失败：' + JSON.stringify(e));
					});
				}
			},
			// 发送分享
			doShare(srv, msg) {
				let _this = this;
				//				_this.$vux.toast.text(JSON.stringify(msg));
				srv.send(msg, function() {
					_this.$vux.toast.text('分享成功！');
				}, function(e) {
					//					+JSON.stringify(e)
					_this.$vux.toast.text('分享失败！');
				});
			},
			updateSerivces() {
				let _this = this;

				plus.share.getServices(function(s) {
					//					_this.$vux.toast.text(JSON.stringify(s))
					_this.shares = {};
					for (var i in s) {
						var t = s[i];
						_this.shares[t.id] = t;
					}
					//					_this.$vux.toast.text(JSON.stringify(_this.shares['weixin']));

					_this.sweixin = _this.shares['weixin'];
				}, function(e) {
					_this.$vux.toast.text('获取分享服务列表失败：' + e.message);
				});


			}
		},
		activated () {
			let _this = this;
			//			this.initPage();
			//			this.getCommentList();
			setTimeout(function() {
				_this.updateSerivces();
			}, 1000)
			setTimeout(function() {
				_this.updateSerivces();
			}, 3000)
		}
	};
</script>

<style scoped>
	.video_box video {
		width: 100%;
	}

	.sec_box {
		background-color: #fff;
	}

	.sec_1 {
		padding: 0 0.3rem;
		margin-bottom: 0.1rem;
	}

	.sec_1>div {
		line-height: 1rem;
	}

	.sec_1 span {
		display: block;
		line-height: 1rem;
		border-bottom: 0.0533rem solid #fff;
		font-size: 0.4rem;
	}

	.sec_1 span.active {
		color: #ff3c3c;
		border-bottom: 0.0533rem solid #ff3c3c;
	}

	.titling {
		padding: 0.1333rem 0.2666rem;
	}

	.titling span {
		font-size: 0.3466rem;
		color: #333;
		line-height: 30px;
	}

	.titling span.right_span {
		color: #999;
	}

	.sec_bottom {
		height: 50px;
		width: 100%;
		position: fixed;
		bottom: 0;
		box-sizing: border-box;
		padding: 0.1333rem 0;
	}

	.sec_bottom button {
		color: #fff;
		font-size: 0.4rem;
		background-color: transparent;
		padding-right: 0.2666rem;
	}

	.sec_bottom .input_box {
		padding: 0 0.2666rem;
	}

	.sec_bottom input {
		box-sizing: border-box;
		width: 100%;
		height: 100%;
		padding: 0 0.2666rem;
		border-radius: 0.5333rem;
		color: #666;
	}

	.swiper-item {
		height: 100%;
		overflow-y: scroll;
	}

	.person_box,
	.text_box {
		padding: 0.2666rem;
		border-bottom: 0.2rem solid #f4f4f4;
	}

	.person_box .img_box {
		width: 2.2rem;
		height: 2.2rem;
		overflow: hidden;
		border-radius: 50%;
		margin-right: 0.4rem;
	}

	.img_box img {
		width: 100%;
	}

	.person_box h4 {
		font-size: 0.45rem;
		color: #666;
		line-height: 0.8rem;
	}

	.person_box span {
		font-size: 0.3733rem;
		color: #999;
		line-height: 0.7rem;
		margin-right: 0.2666rem;
	}

	.person_box span i {
		color: #ff2222;
	}

	.text_box h4 {
		font-size: 0.4rem;
		color: #333;
		line-height: 0.8rem;
		border-bottom: 1px solid #f4f4f4;
	}

	.text_box p {
		font-size: 0.3333rem;
		color: #666;
		line-height: 0.5333rem;
		text-indent: 2em;
	}

	.att_button {
		width: 2rem;
		line-height: 0.6rem;
		text-align: center;
		background-color: #ff2222;
		border-radius: 0.4rem;
	}

	.att_button.active {
		background-color: #008200;
	}

	.att_button img {
		width: 0.5rem;
	}

	.att_button {
		color: #fff;
		font-size: 0.4rem;
		padding: 0.08rem 0.2666rem;
	}
</style>
