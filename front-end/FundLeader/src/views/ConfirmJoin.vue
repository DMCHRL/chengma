<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">
			<div class="con_box">
				<div class="sec_1">
					<div class="flex_bet">
						<span>产品名称</span>
						<span>{{fund.name}}</span>
					</div>
					<div class="flex_bet">
						<span>产品剩余可入伙金额</span>
						<span>{{ fund.canJoin | formattNum}}</span>
					</div>
					<div class="flex_bet">
						<span>账户可用余额</span>
						<span>{{ userMess.balance | formattNum}}</span>
					</div>
				</div>

				<div class="sec_2">
					<div class="input_box flex_bet">
						<span>入伙金额</span>
						<div class="flex_one">
							<input type="number" value="" v-model="money"  />
						</div>
					</div>
					<div class="argee_box">
						<label>
							<input type="checkbox" value="" v-model="isArgee" />
							<span>我已同意并阅读</span>
						</label>

						<router-link to="/agree?type=ABOUT_RISK">
              <i>《领航方舟合伙协议》</i>
            </router-link>
					</div>
				</div>

				<sub-btn :text="subbtn.text" :disabled="subbtn.disabled" @clickme="Submit"></sub-btn>
			</div>
		</div>

	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
	import {
		formatMoney
	} from '@/utils/util'
	export default {

		data() {
			return {
				headOption: {
					title: '确认入伙',
					backText: '',
					showBack: true,
					text: ''
				},
				subbtn: {
					text: '确认入伙',
					disabled: false
				},
				money: '', //	是	double	金额
        fund: {},
        isArgee: true
			}
		},
		filters: {
			formattNum: function(value) {
				return formatMoney(value, 0)
			}
		},
		computed: {
			...mapState(['userInfo', 'userMess']),
			id() {
				return this.$route.query.id
			}
		},
		components: {

		},
		methods: {
      ...mapActions(['getUserMess']),
			async Submit() {
				try {
          if (this.money == '') {
            this.$vux.toast.text('请输入金额', 'middle')
            return;
          }
          if (!this.isArgee) {
          	this.$vux.toast.text('请先同意协议', 'middle')
          	return;
          }
					let params = {
						money: Number(this.money), //	是	double	金额
						fundSignalId: this.id //	是	string	信号id
					}
					const res = await this.fetch("/api/fund_record/createFundRecord", params, "POST");
					console.log(res)
					if (res.statusCode == '0000') {
						this.$vux.toast.text('入伙成功', 'middle')

						this.money = '';
					} else {
						this.$vux.toast.text(res.msgCode, 'middle')
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}
			},
			async initData() {
				try {
					const res = await this.fetch("/api/fund_signal/preFundJoin/" + this.id)
					console.log(res)
					if (res.statusCode == '0000') {
            this.fund = res.data;
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
      this.getUserMess();
		}
	}
</script>

<style scoped>
	.sec_1 {
		background-color: #ecefff;
		padding: 0.533rem;
	}

	.sec_1>div {
		margin-bottom: 0.533rem;
	}

	.sec_1>div:last-of-type {
		margin-bottom: 0;
	}

	.sec_1 span {
		font-size: 0.373rem;
		color: #1d265b;
	}

	.sec_1 span:last-of-type {
		font-weight: bold;
	}

	.sec_2 {
		margin-bottom: 80px;
	}

	.input_box {
		border-top: 1px solid #bdc1da;
		border-bottom: 1px solid #bdc1da;
		padding: 0 0.533rem;
		line-height: 0.9rem;
	}

	.input_box span {
		font-size: 0.373rem;
		color: #1d265b;
	}

	.input_box input {
		width: 100%;
		text-align: right;
		font-size: .37rem;
		color: #666;
		line-height: 0.9rem;
    padding-left: 20px;
    box-sizing: border-box;
	}

	.argee_box {
		padding: 0.533rem;

	}

	.argee_box span {
		font-size: 0.32rem;
		color: #a4a4a4;
	}

	.argee_box i {
		color: #1d265b;
	}
</style>
