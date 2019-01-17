<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		
        <div class="hui_content" id="hui-content">
        
            <my-swiper :list="bannerList"></my-swiper>
            
            <ul class="list_body">
                <li v-for='(item,index) in list'>
                    <div class="item_box clearfix">
                        <div class="img_box pull-left">
                            <img :src="item.img"/>
                        </div>
                        <div class="con_box">
                            <h3>{{item.name}}</h3>
                            <p class="time"><span>{{item.createTime}}</span></p>
                            <p>{{item.text}}</p>
                            
                        </div>
                        <div class="btn_box">
                            <router-link :to="'/webview?url='+ item.url">
                            	<button class="myback" >进入直播间</button>
                            </router-link>
                        </div>
                    </div>
                </li>
                
            </ul>
            
            <div v-show="isloading"  class="trip_box">
            	<spinner :type="'ios'" size='30px'></spinner>
            </div>
        
        </div>
		
	</div>
</template>

<script>
	import MySwiper from "../common/Swiper"
	import GonggeList from "../online/GonggeList"
	export default {
		data() {
			return {
				headOption: {title: '直播',backText: '',showBack: true},
				bannerList: [],
				list: [],
                isloading: false
			}
		},
		components: {
			MySwiper,
			GonggeList
		},
		methods: {
			
			getBannerList () {
				let _this = this;
				_this.$fetch("/api/hpp_advertisement/loadByLive").then((res) => {
					_this.bannerList = res.data.list;
				})
			},
			initPage () {
				let _this = this;
                
                if (_this.isloading) return;
                _this.isloading = true;
                
				_this.$fetch("/api/hpp_live/findAll").then((res) => {
                    _this.isloading = false;
					_this.list = res.data.list;
				})
			}
		},
		mounted () {
			this.getBannerList();
		},
		activated () {
			this.initPage();
		}
	}
</script>

<style scoped>
	.banner_box img {
		width: 100%;
	}
	
	.item_box {
        background-color: #fff;
        margin: 10px;
		padding: 10px;
        border-radius: 10px;
	}
	.item_box .img_box {
		width: 3.1733rem;
		height: 2.2133rem;
		border-radius: 0.1333rem;
        overflow: hidden;
        margin-right: 10px;
        margin-bottom: 10px;
	}
	.item_box p {
		font-size: .35rem;
        color: #666;
        line-height: 21px;
	}
    .item_box p.time {
        text-align: right;
        line-height: 30px;
    }
	.item_box h3 {
		font-size: 0.45rem;
		color: #333;
		font-weight: normal;
        margin-top: 10px;
	}
	.item_box span {
		font-size: 0.2933rem;
		color: #999;
	}
    .btn_box {
        text-align: right;
        margin-top: 10px;
    }
	.item_box button {
		color: #fff;
		font-size: 0.3rem;
		background-color: #fff;
		border-radius: 0.4rem;
		padding: .15rem 0.4rem;
	}
</style>