<template>
	<div class="content_box">
		
		<left-text-header :leftOptions="headOption"></left-text-header>
		
        <div class="hui_content" id="hui-content">
            <my-swiper :list="[1,1,1,1]"></my-swiper>
		
		
            <div class="sec_2 flex_row box_shadow">
                <div class="item_box flex_align_center flex_center">
                    <span>募集进度</span>
                    <x-icon type="ios-arrow-down" size="17"></x-icon>
                </div>
                <div class="item_box flex_align_center flex_center">
                	<span>预期年化</span>
                	<x-icon type="ios-arrow-down" size="17"></x-icon>
                </div>
                <div class="item_box flex_align_center flex_center">
                	<span>最大风险</span>
                	<x-icon type="ios-arrow-down" size="17"></x-icon>
                </div>
                <div class="item_box flex_align_center flex_center xl">
                	<span>募集剩余时间</span>
                	<x-icon type="ios-arrow-down" size="17"></x-icon>
                </div>
            </div>
            
            <div class="sec_3">
                
                <template v-for="item in [50,30,80]">
                <div class="item_box box_shadow">
                    <h3>方舟一号</h3>
                    <div class="flex_bet">
                        <div class="col_box">
                            <div>
                                <p>预期年化收益率</p>
                                <span>9.53%+1.00%</span>
                            </div>
                            <div>
                            	<p>剩余可入伙金额</p>
                            	<span>5191973元</span>
                            </div>
                        </div>
                        <div class="col_box">
                        	<div>
                        		<p>七日年化收益率</p>
                        		<span>9.53%+1.00%</span>
                        	</div>
                        	<div>
                        		<p>最大风险</p>
                        		<span>0%</span>
                        	</div>
                        </div>
                    </div>
                    <div class="flex_bet">
                        <div class="jin_box flex_one">
                            <div class="flex_bet">
                                <p>募集进度</p>
                                <p>32.15%</p>
                            </div>
                            <div class="jin_progress">
                                <div v-show="progressValue" class="jin_active" :style="'width:' + item + '%'"></div>
                            </div>
                        </div>
                        <div class="item_btn">
                            <p>立即入伙</p>
                            <span>(操作周期46天)</span>
                        </div>
                    </div>
                </div>
                </template>
            </div>
        	
        </div>
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
	import LeftTextHeader from '@/components/common/leftTextHeader'
    import MySwiper from "@/components/common/Swiper"
	export default {
		data() {
			return {
				headOption: {title: '华人对冲基金合伙',showBack: false},
				list: [],
				notrip: false,
				isloading: false,
				swiper_index: 0,
				accList: [],
                bannerList: [],
                progressValue: 33,
                // headShow: false
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
		},
		components: {
			LeftTextHeader,
			MySwiper
		},
		methods: {
			...mapActions(['getNoticeNum']),
//             handleScroll() {
//             	var scrollTop = document.getElementById('hui-content').pageYOffset || document.getElementById(
//             		'hui-content').scrollTop || document.getElementById('hui-content').scrollTop
//             	// console.log(scrollTop)
//             	if (scrollTop > 50) {
//             		this.headShow = true;
//             	} else {
//             		this.headShow = false;
//             	}
//             },
            //获取广告图
            getBannerList() {
            	let _this = this;
            	_this.$fetch("/api/hpp_advertisement/loadByHome").then((res) => {
            		_this.bannerList = res.data.list;
            	})
            },
			getMyAccount () {
				let _this = this;
				_this.$fetch("/api/tlb-account/findByMobile").then((res) => {
					// console.log(res)
					_this.accList = res.data.list;
				})
			},
			initPage() {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				_this.$post("/api/hpp_strategy/findAll",{}).then((res) => {
					//console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = res.data.list;
					if (res.data.list.length) {
						_this.notrip = false;
					}else {
						_this.notrip = true;
					}
				})
			}
			
		},
        
		activated () {
            // document.getElementById('hui-content').addEventListener('scroll', this.handleScroll)
//             this.getBannerList();
// 			this.initPage();
// 			this.getMyAccount();
		}
	}
</script>

<style scoped>
	
	.sec_2 {
        margin: 10px;
		background-color: #fff;
	}
    .sec_2 .item_box {
        line-height: 0.933rem;
        width: 24%;
        text-align: center;
    }
    .sec_2 .item_box.xl {
    	width: 27%;
    }
    .sec_2 .item_box span {
        color: #1e275e;
        font-size: 0.346rem;
    }
    .vux-x-icon {
      fill: #1e275e;
    }
    
    .sec_3 {
        padding: 0 10px;
    }
    .sec_3 .item_box {
        text-align: center;
        margin-bottom: 10px;
        background-color: #fff;
    }
    .sec_3 .item_box h3 {
        font-size: 0.48rem;
        color: #202a65;
        line-height: 40px;
    }
    .col_box {
        width: 50%;
        padding-bottom: 20px;
    }
    .col_box p {
        font-size: 0.373rem;
        color: #202a65;
        line-height: 40px;
    }
    .col_box span {
        font-size: 0.346rem;
        color: #ca0811;
        
    }
    .jin_box {
        background-color: #f4f5ff;
        border-right: 2px solid #fff;
        height: 1.066rem;
        padding: 0 10px;
    }
    .jin_box p {
        font-size: 0.373rem;
        color: #202a65;
        margin-top: 5px;
        margin-bottom: 5px;
    }
    .jin_progress {
    	position: relative;
    	background-color: #2a388c;
    	widows: 100%;
    	height: 0.133rem;
    	border-radius: 0.133rem;
        overflow: hidden;
    }
    .jin_progress .jin_active {
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        background-color: #f9ba4e;
    }
    .jin_progress .jin_active:after {
        content: '';
        display: block;
        position: absolute;
        left: 100%;
        width:0;
        height:0;
        border-width:0 0 0.2rem 0.2rem;
        border-style:solid;
        border-color:transparent transparent transparent #f9ba4e;
    }
    
    .item_btn {
        width: 3.466rem;
        height: 1.066rem;
        background-color: #e0e4ff;
    }
    .item_btn p{
        font-size: 0.4rem;
        color: #202a65;
        line-height: 0.6rem;
    }
    .item_btn span {
        font-size: 0.293rem;
        color: #888;
    }
    
	
</style>