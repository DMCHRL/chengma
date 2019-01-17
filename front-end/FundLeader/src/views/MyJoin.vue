<template>
	<div class="page_box">
		<fund-header :leftOptions="headOption"></fund-header>

		<div class="body_box box_pt150" id="my-box">

			<div class="con_box">
				<div class="sec_tab flex_row box_shadow">
          
					<template v-for="(item,index) in tabList">
						<div class="item_box flex_align_center flex_center" 
						:class="status == item.value? 'active':''"
						:key = "index"
						@click="sortChange(item)"
						>
							<span>{{item.name}}</span>
							<div v-if="status == item.value">
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
        <p class="trip_p" v-if="!list.length">暂无数据！</p>

			</div>


		</div>

	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import PartnerItem from '@/components/PartnerItem'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '我的合伙',
					backText: '',
					showBack: true,
					text: '重要消息，不容错过'
				},
				list: [],
        status: 'OPERATION',
        sort:'DESC',
        tabList: [
        	{name: '操作中', value: 'OPERATION',sort: 'DESC'},
        	{name: '募集中', value: 'APPLYING',sort: 'DESC'},
        	{name: '已满盘', value: 'FINISH',sort: 'ASC'},
        ],
			}
		},
		filters: {

		},
		computed: {
			...mapState(['userInfo']),
		},
		components: {
			PartnerItem
		},
		methods: {
			// ...mapActions(['getNoticeNum']),
      sortChange (obj) {
      	this.status = obj.value;
      	this.initData()
      },
			async initData() {
				try {
					let params = {
            status: this.status//	是	string	状态(APPLYING 募集中，OPERATION 操作中 FINISH 已完成)
          }
					const res = await this.fetch("/api/fund_signal/loadMyFund",params,'POST')
					console.log(res)
					if (res.statusCode == '0000') {
						this.list = res.data.list;
					} else {
						console.log('获取数据失败', res)
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}

			}
		},
		activated() {
      this.initData();
		}
	}
</script>

<style scoped>
	.con_box {
		margin-top: 20px;
	}

	.sec_tab {
		margin: 10px;
		background-color: #fff;
		position: relative;
		top: -20px;
	}

	.sec_tab .item_box {
		line-height: 40px;
		width: 33.33%;
		text-align: center;
	}

	.sec_tab .item_box.xl {
		width: 27%;
	}

	.sec_tab .item_box span {
		color: #9a9a9a;
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
