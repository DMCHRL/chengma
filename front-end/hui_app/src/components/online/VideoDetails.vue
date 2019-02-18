<template>
	<div class="content_box">
		<my-header :leftOptions="headOption"></my-header>

    <div class="hui_content">

		<div class="video_box">

			<video v-if="video.videoUrl" controls="controls" autoplay="false" controlslist="nodownload" :src="video.videoUrl">
				<source :src="video.videoUrl" type="video/mp4">
				</source>
				<object width="" height="" type="application/x-shockwave-flash" data="myvideo.swf">
					<param name="movie" value="myvideo.swf" />
					<param name="flashvars" value="autostart=true&amp;file=myvideo.swf" />
				</object> 当前浏览器不支持 video直接播放，点击这里下载视频：
			</video>

      <!-- <video-player :sources="sources"></video-player> -->

      <!--<div v-else class="video_trip">
      	<p>该视频为付费视频，暂不可观看</p>
      	<span>￥{{video.price}} / 积分{{video.price}}</span>
      	<router-link :to="'/settle?type=video&id='+ video.id">
      		<button class="myback" type="primary">立即购买 </button>
      	</router-link>
      </div>-->

		</div>
		<div class="sec_box sec_1 flex_bet">
			<div class="left_box flex_col">
				<h4>{{video.videoName}}</h4>
				<p>{{video.videoText}}</p>
			</div>
			<div class="right_box flex_col">
				<p>{{video.playNum}}次播放</p>
				<!-- <button>
					<a :href="video.videoUrl">
						<img src="../../assets/img/i_01.png" />
						保存到手机
					</a>
				</button> -->
			</div>
		</div>
		<div class="sec_box sec_2 ">
			<div class="titling flex_bet">
				<span>选集</span>
				<div class="arrow_right"></div>
			</div>
			<selection-slider :videoTypeId="video.videoTypeId" :id="video.id"></selection-slider>
		</div>
		<div class="sec_box sec_3 ">
			<!--<div class="titling flex_bet">
				<span>评论</span>
				<span class="right_span">共{{commentList.length}}条</span>
			</div>
			<comment-list :commentList="commentList"></comment-list>-->
		</div>
		<!--<div class="sec_box sec_bottom flex_bet myback">
			<div class="input_box flex_one">
				<input type="text" name="" id="" value="" v-model="commentText" placeholder="请输入评论内容(字数40字以内)" />
			</div>
			<button @click="Commit" :disabled="isDisabled">发表评论</button>
		</div>-->

    </div>

	</div>
</template>

<script>
  import VideoPlayer from "../common/myPlayer.vue"
	import SelectionSlider from "./SelectionSlider"
	import CommentList from "./CommentList"
	export default {
		data() {
			return {
				headOption: {
					title: '视频播放',
					backText: '',
					showBack: true
				},
				video: {},
				commentText: '',
				isDisabled: false,
				commentList: [],
				pays: [
					{id: 'integral',description: '积分'}
				], //支付方式
				w: null, //请求中

        sources: null,
			}
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
			id (val) {
				this.initPage();
			}
		},
		components: {
			SelectionSlider,
			CommentList,
      VideoPlayer
		},
		methods: {

      plusReady () {
        let _this = this;
        // 创建视频播放控件
        if(!_this.player){
          _this.player = plus.video.createVideoPlayer('videoplayer', {
            src: _this.video.videoUrl,
            top:'100px',
            left:'0px',
            width: '100%',
            height: '200px',
            position: 'static'
          });
          plus.webview.currentWebview().append(_this.player);
        }
      },
			Commit() {
				let _this = this;
				let datas = {
					context: _this.commentText,
					videoId: _this.id,
					mobile: _this.user.mobile
				}
				_this.isDisable = true;
				_this.$post("/api/hpp_comment/saveHppComment", datas).then((res) => {
					_this.isDisable = false;
					if (res.statusCode == '0000') {
						_this.$vux.toast.text('评论成功', 'middle');
						_this.commentText = '';
						_this.getCommentList();
					} else {
						_this.$vux.toast.text(res.msgCode, 'middle')
					}
				})
			},
			getCommentList() {
				let _this = this;
				_this.$fetch("/api/hpp_comment/findForApp/" + this.id).then((res) => {
					_this.commentList = res.data.list;
				})
			},
			initPage() {
				let _this = this;
				_this.$fetch("/api/hpp_video/showOnApp/" + this.id).then((res) => {
					// console.log(res)
					_this.video = res.data;
					_this.headOption.title = res.data.videoName;

          _this.sources = [{
          			type: "video/mp4",
          			src: res.data.videoUrl
          		}]

				})
			},

		},
		mounted() {
			let _this = this;
			this.initPage();
			this.getCommentList();
//
//       let w = document.documentElement.clientWidth || document.body.clientWidth;
//       this.playerOptions.width = w;

// 			document.addEventListener('plusready', _this.plusReady, false);
// 			_this.plusReady();
		}
	}
</script>

<style scoped>

    /* @import "../../assets/scss/player.scss"; */


  .video_box {
    min-height: 200px;
		position: relative;
	}
  .video_box video{
  		width: 100%;
  }

	.sec_box {
		background-color: #fff;
	}

	.sec_1 {
		padding: 0.3333rem;
		padding-top: 0.6rem;
		margin-bottom: 0.2666rem;
	}

  .sec_2 {
    padding: 0.3333rem;
    padding-top: 0.6rem;
    height: 8rem;
    margin-bottom: 0.2666rem;
  }

	.sec_1 .right_box {
		text-align: right;
	}

	.sec_1 h4 {
		font-size: 0.4rem;
		color: #333;
		margin-bottom: 0.4rem;
	}

	.sec_1 p {
		font-size: 0.2933rem;
		color: #999;
	}

	.sec_1 button {
		font-size: 0.32rem;
		color: #333;
		width: 2.4rem;
		background-color: #fff;
		margin-top: 0.9333rem;
	}

	.sec_1 button img {
		width: 0.48rem;
		margin-right: 0.1333rem;
		position: relative;
		bottom: 0.04rem;
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

	.video_trip {
    position: absolute;
    top: 0;
    left: 0;
		width: 100%;
		height: 100%;
		background-color: #333;
		padding-top: 50px;
		text-align: center;
    z-index: 9999999;
	}

	.video_trip p {
		font-size: .45rem;
		color: #fff;
		line-height: 40px;
	}
	.video_trip span {
		font-size: .35rem;
		color: #FF4C39;

	}
	.video_trip button {
		font-size: 14px;
		color: #fff;
		/* background-color: transparent; */
		border-radius: 0.4rem;
		/* border: 1px solid #00FF00; */
		padding: 2px 20px;
		display: block;
		margin: 0.6rem auto;
	}
	.video_trip button.integral {
		color: #f3917f;
		border-color: #f3917f ;
	}

    .video-player-box {
        width: 100%;
    }
</style>
