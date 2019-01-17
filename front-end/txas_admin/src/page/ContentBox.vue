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
	import sideBar from '@/components/common/SideBar'
	import HeadSection from '@/components/common/HeadSection'
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
            
            let token = localStorage.getItem("id_token");
            if (token == null) {
            	_this.$router.replace({
            			path: '/login',
            	})
            	return;
            }
            
			_this.setHeight();
			window.onresize = () => _this.setHeight();
            
		}
	}
</script>

<style scoped>
	#content {
		overflow: hidden;
	}
	.side_box {
		width: 14%;
		margin-left: -15px;
	}
	.article_box {
		width: 86%;
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
	.titling>span {
		font-size: 16px;
		color: #666;
		cursor: pointer;
        padding-right: 10px;
	}
	.titling>span:first-of-type {
		cursor: text;
		border-left: 4px solid #6562b6;
		font-size: 18px;
		color: #2c2b5c;
		padding-left: 10px;
	}
	.titling button {
		font-size: 14px;
		color: #fff;
		background-color: #f14b3b;
		border-radius: 20px;
		line-height: 30px;
		padding: 0px 20px;
		margin-left: 20px;
	}
	
	.headlist {
		background-color: #6562b6;
		padding: 8px 0;
	}
	.headlist>span {
		font-size: 14px;
		text-align: center;
		width: 8%;
	}
	.headlist>span.time {
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
		text-align: center;
	}
	.bodylist li.zongji {
		/*border-top: 2px solid #6562b6;*/
		border-bottom: 2px solid #6562b6;
		background-color: rgba(100,100,180,.2);
	}
	
	.bodylist li>span {
		font-size: 14px;
		color: #3e3e3e;
		width: 8%;
		text-align: center;
		height: 30px;
		line-height: 30px;
		padding: 0 10px;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.bodylist li>span.time {
		width: 10%;
	}
	.bodylist li>div{
		width: 10%;
	}
	.bodylist li>div button {
		font-size: 14px;
		color: #6562b6;
		padding: 3px 20px;
		background-color: transparent;
		border: 1px solid #6562b6;
		border-radius: 20px;
	}
	.bodylist li>div button.delete {
		background-color: #f14b3b;
		color: #fff;
		border: #fff;
		border: 1px solid #f14b3b;
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