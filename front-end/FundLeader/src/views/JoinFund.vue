<template>
	<div class="page_box">

		<left-text-header :leftOptions="headOption"></left-text-header>

		<div class="body_box" id="my-box">
			<my-swiper :list="bannerList"></my-swiper>

			<div class="sec_tab flex_around box_shadow">
        
        <template v-for="(item,index) in tabList">
          <div class="item_box flex_align_center flex_center" 
          :class="orderByColumn == item.value? 'active':''"
          :key = "index"
          @click="sortChange(item)"
          >
            <span>{{item.name}}</span>
            <div v-if="orderByColumn == item.value">
              <i class="iconfont icon-sjxdown" v-if="sort == 'DESC'"></i>
              <i class="iconfont icon-sjxup" v-else></i>
            </div>
          </div>
        </template>
				
			</div>

			<div class="sec_list">
				<template v-for="(item,index) in list">
					<partner-item :key="index" :item="item"></partner-item>
				</template>
			</div>

		</div>
	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import MySwiper from "@/components/common/Swiper"
	import LeftTextHeader from '@/components/common/leftTextHeader'
	import PartnerItem from '@/components/PartnerItem'
	export default {
		data() {
			return {
				headOption: {
					title: '华人对冲基金合伙',
					showBack: false
				},
        tabList: [
          {name: '募集进度', value: 'progress',sort: 'DESC'},
          {name: '预期年化', value: 'yearProfit1',sort: 'DESC'},
          {name: '最大风险', value: 'risk',sort: 'ASC'},
          {name: '募集剩余时间', value: 'remainingTime',sort: 'DESC'},
        ],
				list: [],
				notrip: false,
				isloading: false,
				swiper_index: 0,
				accList: [],
				bannerList: [{
						img: 'http://tlb.txasfx.com/linghang/img/1.jpg'
					},
					{
						img: 'http://tlb.txasfx.com/linghang/img/2.jpg'
					},
				],
				progressValue: 33,

				orderByColumn: 'progress', //募集进度(progress) 预期年化(yearProfit1) 最大风险(risk) 募集剩余时间(remainingTime)
				sort: 'DESC', //DESC,ASC
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

			//获取广告图
      async getBannerList() {
          try {
              this.bannerList = [];
              const res = await this.fetch("/api/advertisement/loadByType/2")
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
			getMyAccount() {
				let _this = this;
				_this.$fetch("/api/tlb-account/findByMobile").then((res) => {
					// console.log(res)
					_this.accList = res.data.list;
				})
			},
      sortChange (obj) {
        this.orderByColumn = obj.value;
        this.sort = obj.sort;
        this.initPage()
      },
			initPage() {
				let _this = this;

				let timer = setTimeout(function() {
					_this.isloading = true;
				}, 1000)
				let params = {
					orderByColumn: _this.orderByColumn,
					sort: _this.sort
				};
				_this.$post("/api/fund_signal/findList", params).then((res) => {
					//console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = res.data.list;
					if (res.data.list.length) {
						_this.notrip = false;
					} else {
						_this.notrip = true;
					}
				})
			}

		},
		activated() {
			this.getBannerList();
			this.initPage();
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
		height: 0.933rem;
	}


	.sec_tab .item_box span {
		color: #999;
		font-size: 0.346rem;
	}
  .sec_tab .item_box.active span {
  	color: #1e275e;
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
