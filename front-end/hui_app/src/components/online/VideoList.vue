<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content">
            
		<ul class="link_body flex_col">
			
			<li class="link_box" v-for="item in list">
				<router-link :to="'/vdetails?id='+item.id">
				  	<div class="flex_bet">
				  		<div class="img_box">
				  			<img :src="item.img"/>
				  		</div>
				  		<div class="mid_box flex_one">
				  			<h4>{{item.videoName}}</h4>
							<p>播放次数：{{item.playNum}}</p>
				  			<!-- <p>上传时间：{{item.updateAt}}</p> -->
				  		</div>
				  		<div class="right_box flex_col">
				  			<span v-show="item.feeFlag == 2" class="xian">免费</span>
				  			<span v-show="item.feeFlag == 1" class="fu">￥{{item.price}}</span>
				  		</div>
				  	</div>
			  	</router-link>
			</li>
		</ul>
		
		<div v-show="isloading"  class="trip_box">
			<spinner :type="'ios'" size='30px'></spinner>
		</div>
		
		<div class="trip_box" v-show="notrip">
			<img src="../../assets/img/no.png"/>
			<p>暂无视频</p>
		</div>
        
        </div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '选集',backText: '',showBack: true},
				list: [],
				isloading: false,
				notrip: false
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			}
		},
		components: {
			
		},
		methods: {
			initPage () {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				_this.$fetch("/api/hpp_video/findByTypeId/"+this.id).then((res) => {
					// console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = res.data.list;
					if (res.data.list.length) {
						_this.list = res.data.list;
						_this.headOption.title = res.data.list[0].videoTypeName;
					}else {
						_this.notrip = true
					}
				})
			}
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	
	.titling span{
		display: block;
		padding: 0.2666rem 0.4rem;
		font-size: 0.4rem;
		color: #333333;
		background-color: #fff;
		border-bottom: 1px solid #ededed;
	}
	.link_body {
		padding: 0 0.2933rem ;
		background-color: #fff;
	}
	.link_box {
		padding: 0.2933rem 0;
		border-bottom: 1px solid #ccc;
	}
	.link_box img {
		width: 2.6666rem;
		height: 1.8666rem;
		border-radius: 0.1333rem;
	}
	.link_box .mid_box{
		padding: 0 0.2666rem;
		position: relative;
	}
	.link_box .mid_box h4 {
		font-size: 0.3466rem;
		color: #666;
		line-height: 0.6666rem;
	}
	.link_box .mid_box p {
		font-size: 0.2933rem;
		color: #999;
		position: absolute;
		bottom: 0;
		/* left: 0.4666rem; */
	}
	.link_box .right_box span{
		font-size: 0.2933rem;
		color: #999;
		line-height: 0.6666rem;
		text-align: right;
	}
	.link_box .right_box span.xian {
		color: #28c76f;
		font-size: 0.3466rem;
		display: block;
		margin-top: 0.6666rem;
	}
	.link_box .right_box span.fu{
		color: #ff5752;
		font-size: 0.5466rem;
		display: block;
		margin-top: 0.6666rem;
	}
	
</style>