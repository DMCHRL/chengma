<template>
	<el-dialog custom-class="" width="30%" :show-close="false" :visible="show" append-to-body>
		<h3 slot="title" class="dialog_title">视频详情</h3>
		
		<div class="video_box">
			<video controls="controls" autoplay="false">
				<source :src="VideoDetails.videoUrl" type="video/mp4"></source>
				<object width="" height="" type="application/x-shockwave-flash" data="myvideo.swf">
					<param name="movie" value="myvideo.swf" />
					<param name="flashvars" value="autostart=true&amp;file=myvideo.swf" />
				</object> 当前浏览器不支持 video直接播放，点击这里下载视频：
				<a href="https://mp.weixin.qq.com/s/Vv2yCyttDJqF4PuduDaU4Q">下载视频</a>
			</video>
		</div>
		
		
		
		
		<div slot="footer" class="dialog_footer">
			<!--<button @click="cardCommit">确认</button>-->
			<button @click="cancelDialog">关闭</button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		props: ['show','VideoMess'],
		data() {
			return {
				VideoDetails: {}
			}
		},
		computed: {
			
		},
		watch: {
			VideoMess () {
				this.VideoDetails = JSON.parse(JSON.stringify(this.VideoMess))
				console.log(this.VideoDetails.videoUrl)
			}
		},
		components: {

		},
		methods: {
			
			cardCommit() {
				let _this = this;
				let datas = {
					videoTypeId: _this.typeid,
					videoUrl: _this.url,
					videoText: _this.text,
					videoName: _this.title,
					feeFlag: _this.radioValue,
					price: _this.price,
					integralPrice: _this.integralPrice,
					img: _this.images.zheng_image,
					id: _this.id
				}
				
				this.$post("/api/hpp_video/saveHppVideo",datas).then((res) => {
					console.log(res)
					if (res.statusCode == "0000") {
						_this.$message({
				          message: "新增成功",
				          type: 'success'
				        });
				        _this.$emit('close') //关闭弹出
					}
				})

			},
			cancelDialog() {
				this.$emit('close')
			}
		},
		mounted() {
		}
	}
</script>

<style scoped>
	.dialog_title {
		text-align: center;
		font-size: 25px;
		color: #6562b6;
	}
	
	.video_box video {
		width: 100%;
	}
	
	.input_box {
		margin-bottom: 25px;
		position: relative;
	}
	.left_box {
		text-align: right;
		width: 40%;
	}
	.left_box span {
		padding-right: 20px;
		color: #666;
		font-size: 14px;
		line-height: 36px;
	}
	
	span.import:before {
		content: '*  ';
		color: #F14B3B;
	}
	
	.right_box input,
	.right_box select,
	.right_box textarea {
		border: 1px solid #ccc;
		padding: 5px 10px;
		border-radius: 5px;
		font-size: 14px;
		color: #666;
		width: 200px;
	}
	
	.trip_text {
		position: absolute;
		top: 100%;
		left: 0;
		padding-left: 40%;
		font-size: 12px;
		color: #F14B3B;
	}
	
	.dialog_footer {
		text-align: center;
	}
	
	.dialog_footer button {
		font-size: 18px;
		background-color: #F14B3B;
		border-radius: 20px;
		padding: 3px 30px;
		color: #fff;
	}
	
	.dialog_footer button:last-of-type {
		background-color: #fff;
		color: #F14B3B;
		border: 1px solid #F14B3B;
		padding: 1.5px 28px;
		margin-left: 20px;
	}
	
	.uplond_box {
		position: relative;
		text-align: center;
	}
	
	.uplond_box label {
		width: 200px;
		min-height: 100px;
		border: 1px solid #ccc;
		border-radius: 10px;
		margin-right: 10px;
		text-align: center;
		overflow: hidden;
	}
	.icon_box{
		line-height: 100px;
	}
	.icon_box img {
		width: 30px;
		height: 30px;
	}
	
	.img_box img {
		width: 100%;
		height: auto;
	}
</style>