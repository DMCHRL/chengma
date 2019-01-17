<template>
    <div class="player-container">
      <video-player 
      class="vjs-custom-skin" 
      :options="playerOptions"
      @play="onPlayerPlay($event)"
       @pause="onPlayerPause($event)"
       @ended="onPlayerEnded($event)"
       @loadeddata="onPlayerLoadeddata($event)"
       @waiting="onPlayerWaiting($event)"
       @playing="onPlayerPlaying($event)"
       @timeupdate="onPlayerTimeupdate($event)"
       @canplay="onPlayerCanplay($event)"
       @canplaythrough="onPlayerCanplaythrough($event)"
       @ready="playerReadied"
       @statechanged="playerStateChanged($event)"
      ></video-player>
    </div>
</template>

<script>
//引入video样式
import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'

//引入hls.js
import 'videojs-contrib-hls.js/src/videojs.hlsjs'

export default {
  props: ['sources'],
  data () {
    return {
		playerOptions: {
	        playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
	        autoplay: false, //如果true,浏览器准备好时开始回放。
	        controls: true, //控制条
	        preload: 'auto', //视频预加载
	        muted: false, //默认情况下将会消除任何音频。
	        loop: false, //导致视频一结束就重新开始。
	        language: 'zh-CN',
	        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
	        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
	        sources: [{
          			type: "video/mp4",
          			src: ''
          		}],
	        // poster: "http://static.smartisanos.cn/pr/img/video/video_03_cc87ce5bdb.jpg", //你的封面地址
	        width: document.documentElement.clientWidth || document.body.clientWidth,
	        notSupportedMessage: '此视频暂无法播放，请稍后再试' //允许覆盖Video.js无法播放媒体源时显示的默认信息。
	      }
	    }
  },
  watch: {
  	sources(newValue, oldValue) {
      console.log(newValue)
  		this.playerOptions.sources = newValue
  	}
  },
  methods: {
    // listen event
      onPlayerPlay(player) {
        // console.log('player play!', player)
      },
      onPlayerPause(player) {
        // console.log('player pause!', player)
      },
      onPlayerEnded(player) {
        // console.log('player ended!', player)
      },
      onPlayerLoadeddata(player) {
        // console.log('player Loadeddata!', player)
      },
      onPlayerWaiting(player) {
        // console.log('player Waiting!', player)
      },
      onPlayerPlaying(player) {
        // console.log('player Playing!', player)
      },
      onPlayerTimeupdate(player) {
        // console.log('player Timeupdate!', player.currentTime())
      },
      onPlayerCanplay(player) {
        // console.log('player Canplay!', player)
      },
      onPlayerCanplaythrough(player) {
        // console.log('player Canplaythrough!', player)
      },
      // or listen state event
      playerStateChanged(playerCurrentState) {
        // console.log('player current update state', playerCurrentState)
      },
      // player is ready
      playerReadied(player) {
        // seek to 10s
        console.log('example player 1 readied', player)
        player.currentTime(1)
        // console.log('example 01: the player is readied', player)
      }
  },
  computed: {
    
  },
  mounted () {
    
  }
}
</script>

<style lang="scss">
  @import "@/assets/scss/player.scss";
  .video-js .vjs-big-play-button{
    /*
    播放按钮换成圆形
    */
   height: 2em;
   width: 2em;
   line-height: 2em;
   border-radius: 1em;
   }
</style>