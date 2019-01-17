<template>

	<div class="page_box">
		<left-text-header :leftOptions="headOption"></left-text-header>

		<div class="body_box" id="my-box">
			<my-swiper :list="bannerList"></my-swiper>

			<div class="sec_list">
				<div class="item_box flex_bet box_shadow">
					<div class="text_box">
						<h3>中荣资本-连续并购与投资的共富之道</h3>
						<p>大鳄潜行 豪掷537亿并购，虽说腾讯是上市公司，但其对外投资并
							购的全貌却并不为外界所知。因为在多数情况下，其并购行为，并
							没有触及港交所强制披露的“上限”。</p>
					</div>
					<div class="img_box">
						<img src="http://tlb.txasfx.com/linghang/img/l2.png" alt="">
					</div>
				</div>
				<div class="item_box flex_bet box_shadow">
					<div class="text_box">
						<h3>明星父母-打造国内 TOP1-天然母婴用品品牌</h3>
						<p>万国城创办至今，不忘初心，致力于打造首个
							世界冠军夫妇创始上市企业。立足全球，牵动
							国内外行业潮流。
							万国城杜芬希望通过不懈努力、力求创新、持
							续探索的核心精神达成上市的最终目标，继而
							扩大自主品牌力量，为全球更多孕婴童消费者
							提供高品质、范围广、品类多样的杜芬产品以
							及服务体验。</p>
					</div>
					<div class="img_box">
						<img src="http://tlb.txasfx.com/linghang/img/l1.png" alt="">
					</div>
				</div>
			</div>

		</div>




	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import MyHeader from "../components/common/Header.vue"
	import MySwiper from "../components/common/Swiper.vue"
	import LeftTextHeader from '../components/common/leftTextHeader.vue'
	export default {
		data() {
			return {
				headOption: {
					title: '一级市场融投服务'
				},
				bannerList: [
					{
						img: 'http://tlb.txasfx.com/linghang/img/z2.png'
					},
					{
						img: 'http://tlb.txasfx.com/linghang/img/d1.png'
					},
					{
						img: 'http://tlb.txasfx.com/linghang/img/d2.png'
					},
				],
				
			}
		},
		watch: {
			
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
		},
		components: {
			MyHeader,
			MySwiper,
			LeftTextHeader
		},
		methods: {
			...mapActions(['getNoticeNum','getUserMess']),
			
			//获取广告图
			async getBannerList() {
                try {
                    this.bannerList = [];
                    const res = await this.fetch("/api/advertisement/loadByType/1")
                    // console.log(res)
                    if (res.statusCode == '0000') {
                        let list = res.data.list;
                        list.sort(function(a,b){
                        	return a.sortNum - b.sortNum;
                        })
                        this.bannerList = list;

                    } else {
                        console.log('获取数据失败', res)
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            async sendCid() { //获取clientId，返回后台
                try {
                    let info = plus.push.getClientInfo();
                    const res = await this.fetch("/api/hpp_mobile_user/setCid",{
                        cid: info.clientid//	否	string	个推识别码
                    },'POST')
                    // console.log(res)
                    if (res.statusCode == '0000') {
                       
                    } else {
                        console.log('获取数据失败', res)
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            	
            },
            plusReady () {
                this.sendCid();
            }

		},
		activated() {
            let _this = this;

            let token = localStorage.getItem('id_token');
            if (token) {
				_this.getBannerList();
                _this.getUserMess();
                
                document.addEventListener('plusready', _this.plusReady, false);
                
            } else {
                _this.$router.replace({
                    path: '/login'
                })
            }

		}
	}
</script>

<style scoped>
	.call_box {
		width: 1.0666rem;
		height: 1.0933rem;
		position: fixed;
		bottom: 1.8rem;
		right: 0.6rem;
	}

	.call_box img {
		width: 1.0666rem;
		height: 1.0933rem;
	}

	.more_data {
		text-align: center;
		padding: 15px 0;
	}

	.more_data p {
		color: #999;
		font-size: .2rem;
		padding-top: 10px;
	}

	.sec_list {
		margin: 10px;
	}

	.item_box {
		background-color: #fff;
		border-radius: 5px;
		padding: 10px;
		margin-bottom: 20px;
	}

	.item_box h3 {
		font-size: 0.35rem;
		color: #666;
		margin-bottom: 10px;
	}

	.item_box p {
		font-size: 0.3rem;
		color: #999;
	}

	.text_box {
		width: 70%;
		padding-right: 10px;
	}

	.img_box {
		width: 30%;

	}

	.img_box img {
		border-radius: 5px;
	}
</style>
