<template>
	<div id="content" :style="'height: '+contentHeight+'px'">
		<head-section></head-section>
		<div class="container-fluid">
			<div class="clearfix">
				<div class="pull-left side_box" :style="'height: '+(contentHeight-55)+'px'">
					<side-bar></side-bar>
				</div>
				<div class="pull-right article_box" :style="'height: '+(contentHeight-75)+'px'">
					<router-view/>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import sideBar from './common/SideBar'
	import HeadSection from './common/HeadSection'
	export default {
		data () {
			return {
				contentHeight: 950
			}
		},
		components: {
			sideBar,
			HeadSection
		},
		methods: {
			setHeight () {
//		      	let ww = document.documentElement.clientWidth || document.body.clientWidth;
				let wh = document.documentElement.clientHeight || document.body.clientHeight;
				this.contentHeight = wh;
		    }
		},
		mounted () {
			let _this = this;

			window.onresize = () => _this.setHeight();

			if (_this.$until.tokener.get()) {
				this.setHeight();
			}else {
				_this.$router.push({path: '/login'})
			}

		}
	}
</script>

<style scoped>
	#content {
		overflow: hidden;
	}
	.side_box {
		width: 12%;
		margin-left: -15px;
		/*padding-right: 30px;*/
		background-color: #2c2b5c;
		/*overflow-y: scroll;*/
	}
	.article_box {
		position: relative;
		width: 88%;
		overflow-y: scroll;
		padding-top: 20px;
	}
</style>

<style>
	.content_box {
		width: 100%;
		height: 100%;
		padding: 20px;
		background-color: #fff;
	}
	.titling {
		border-bottom: 2px solid #6562b6;
		line-height: 50px;
		margin-top: -20px;
	}

	.titling span {
		border-left: 4px solid #6562b6;
		font-size: 18px;
		color: #2c2b5c;
		padding-left: 10px;
	}
	.titling span.dao {
		border: none;
		font-size: 16px;
		color: #0000FF;
		cursor: pointer;
		margin-right: 30px;
	}
	.titling button {
		font-size: 14px;
		color: #fff;
		background-color: #2a578c;
		border-radius: 20px;
		line-height: 30px;
		padding: 0px 20px;
		margin-left: 20px;
	}

	.headlist {
		background-color: #6562b6;
		padding: 8px 0;
	}
	.headlist span {
		font-size: 14px;
		text-align: center;
		width: 8%;
		min-height: 1px;
	}
	.headlist span.time {
		width: 10%;
	}
	/*.bodylist {
		min-height: 300px;
	}*/
	.bodylist {
		background-color: #fff;
	}
	.bodylist li {
		padding: 5px 0;
		border-bottom: 1px dashed #d0d0d0;
		line-height: 30px;
		text-align: center;
	}
	.bodylist li.zongji {
		/*border-top: 2px solid #6562b6;*/
		border-bottom: 2px solid #6562b6;
		background-color: rgba(100,100,180,.2);
	}

	.bodylist li span {
		font-size: 14px;
		color: #3e3e3e;
		width: 8%;
		text-align: center;
		min-height: 2px;
		padding: 0 10px;
		overflow: hidden;
		text-overflow: ellipsis;
		-ms-word-wrap: break-word;
		word-wrap:break-word
	}
	.bodylist li span.time {
		width: 10%;
	}
	.bodylist li span.red {
		color: #FF4A4A;
	}
	.bodylist li span.green {
		color: #00FF00;
	}
	.bodylist li span.blue {
		color: #0000FF;
	}
	.bodylist li>div{
		width: 10%;
		min-height: 1px;
	}

	/*无数据*/
	.trip_box {
		text-align: center;
		padding: 60px 0;
	}
	.trip_box img {
		width: 100px;
		margin-bottom: 20px;
	}
</style>
