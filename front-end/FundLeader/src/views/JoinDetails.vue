<template>
	<div class="page_box">

		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box" id="my-box">

			<div class="sec_tab flex_around">
				<template v-for="(item,index) in tabList">
					<div class="item_box" :key="index" @click="swiper_index = index" :class="swiper_index == index?'active':''" :style="{backgroundImage: swiper_index == index ? tabBack: '' }">
						<span>{{item}}</span>
					</div>

				</template>

			</div>

				<swiper v-model="swiper_index" :height="swiperHeight" :show-dots="false">
					<swiper-item>
                        <div class="con_box">
                            <product :datas="datas"></product>
                        </div>
					</swiper-item>
					<swiper-item>
                        <div class="con_box">
						<person :data="personData" ></person>
                        </div>
					</swiper-item>
					<swiper-item>
                        <div class="con_box">
						<person :data="personData"></person>
                        </div>
					</swiper-item>
					<swiper-item>
                        <div class="con_box">
						<join-record :data="record"></join-record>
                        </div>
					</swiper-item>
				</swiper>
			<sub-btn :text="subbtn.text" :disabled="subbtn.disabled" @clickme="Commit"></sub-btn>
		</div>
	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import {
		Swiper,
		SwiperItem,
	} from 'vux'
	import Product from '@/components/details/Product'
	import JoinRecord from '@/components/details/JoinRecord'
	import Person from '@/components/details/Person'
	import PartnerItem from '@/components/PartnerItem'
	export default {
		data() {
			return {
				headOption: {
					title: '产品详情',
					showBack: true,
					backText: '',
				},
				subbtn: {
					text: '立即入伙',
					disabled: false
				},
				datas: {},
				personData: {},
				Identity: '',
				dapartId: '',

				tabList: ['产品信息', '发起人', '资金管理人', '入伙记录'],
				swiper_index: 0,
				tabBack: "url(" + require('../assets/img/f_b_01.png') + ")",

				record: {},
                swiperHeight: '500px'
			}
		},
		watch: {
			swiper_index(newValue, oldValue) {
				if (newValue == 1) {
					this.Identity = 'originator';
					this.dapartId = this.datas.originatorId;
					this.initPerson()
				} else if (newValue == 2) {
					this.Identity = 'manager';
					this.dapartId = this.datas.managerId;
					this.initPerson()
				} else if (newValue == 3) {
					this.initRecord();
				}
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
			id() {
				return this.$route.query.id;
			}
		},

		components: {
			PartnerItem,
			Product,
			Person,
			JoinRecord,
			Swiper,
			SwiperItem
		},
		methods: {
			// ...mapActions(['getNoticeNum']),

			Commit() {
				this.$router.push({
					path: '/confirm-join?id='+this.id
				})
			},
			async initRecord() {
				try {
					const res = await this.fetch("/api/fund_record/findByFundSignalId/" + this.datas.id);
					console.log(res)
					if (res.statusCode == '0000') {
						this.record = res.data;
					} else {
						this.record = {};
						this.record.list = [];
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
			async initPerson() {
				try {
					const res = await this.fetch("/api/user/loadPowerInfo", {
						department: this.Identity, //	是	string	身份(originator 发起人) (manager 资金管理人)
						departmentId: this.dapartId
					}, "POST");
					console.log(res)
					if (res.statusCode == '0000') {
						this.personData = res.data;
					} else {
						this.personData = {};
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},

			initPage() {
				let _this = this;

				_this.$fetch("/api/fund_signal/findOneDetail/" + this.id).then((res) => {
					console.log(res)
					this.datas = res.data;
					// _this.list = res.data.list;

				})
			}

		},
        created () {
            var wh = document.documentElement.clientHeight || document.body.clientHeight;
            console.log(wh-82-36-95)
            this.swiperHeight = (wh-82-36-95)+'px';
        },
		activated() {
            
			this.initPage();
			this.swiper_index = 0;
			// this.getBannerList();
			// this.getMyAccount();
		}
	}
</script>

<style scoped>
	.body_box {
		padding-top: 95px;
        padding-bottom: 0;
	}

	.sec_tab {
		margin: 0 20px;
        line-height: 30px;
	}

	.sec_tab .item_box {
		padding: 0 0.31rem;

	}

	.sec_tab .item_box span {
		color: #fff;
		font-size: 0.373rem;
	}

	.sec_tab .item_box.active {
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}

	.sec_tab .item_box.active span {
		color: #28347e;
	}

	.con_box {
		margin: 0 0.333rem;
        height: 100%;
        overflow-y: scroll;
	}
</style>
