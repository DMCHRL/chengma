<template>
	<div class="content_box">
		
		<left-text-header :leftOptions="headOption"></left-text-header>
		
        <div class="hui_content" id="hui-content">
            <my-swiper :list="[1,1,1,1]"></my-swiper>
		
            <div class="sec_tab flex_row box_shadow">
                <div class="item_box flex_align_center flex_center">
                    <span>募集进度</span>
                    <i class="iconfont icon-sjxdown"></i>
                </div>
                <div class="item_box flex_align_center flex_center">
                	<span>预期年化</span>
                	<i class="iconfont icon-sjxup"></i>
                </div>
                <div class="item_box flex_align_center flex_center">
                	<span>最大风险</span>
                	<i class="iconfont icon-sjxdown"></i>
                </div>
                <div class="item_box flex_align_center flex_center xl">
                	<span>募集剩余时间</span>
                	<i class="iconfont icon-sjxdown"></i>
                </div>
            </div>
            
            <div class="sec_list">
                <template v-for="item in [50,30,80]">
                  <partner-item :key="item.id" :item="item"></partner-item>
                </template>
            </div>
        	
        </div>
	</div>
</template>

<script>
	import {mapState,mapActions} from 'vuex'
  import MySwiper from "@/components/common/Swiper"
	import LeftTextHeader from '@/components/common/leftTextHeader'
	import PartnerItem from '@/components/PartnerItem'
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
			MySwiper,
			LeftTextHeader,
      PartnerItem
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
        // this.getBannerList();
        // this.initPage();
        // this.getMyAccount();
		}
	}
</script>

<style scoped>
	
    .sec_tab {
      margin: 10px;
      background-color: #fff;
    }
    .sec_tab .item_box {
        line-height: 0.933rem;
        width: 24%;
        text-align: center;
    }
    .sec_tab .item_box.xl {
    	width: 27%;
    }
    .sec_tab .item_box span {
        color: #1e275e;
        font-size: 0.346rem;
    }
    .sec_tab .item_box i.iconfont {
      color: #1e275e;
      font-size: 0.3rem;
      margin-left: 0.1rem;
    }
    .sec_list {
        padding: 0 10px;
    }
    
	
</style>