<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1 clearfix">
					<div class="btn pull-left">充值金额</div>
					<div class="input_box pull-right">
						<money-input @change="inputChange" :money="initMoney"></money-input>
						<div class="tui_btn">
							<button @click="initMoney = 50000">5万</button>
							<button @click="initMoney = 100000">10万</button>
						</div>
					</div>
				</div>

        <div class="sec_2">
        	<p>请选择充值方式</p>
        	<div class="radio_groud">
            
        		<div class="radio_item flex_bet flex_align_center" @click="changeType('online')">
        			<div class="icon_box">
                <img src="../assets/img/pay_01.png" alt="">
              </div>
        			<span class="flex_one">银联在线充值</span>
        			<icon type="success-no-circle" v-if="payType == 'online'"></icon>
        		</div>
            
            <div class="radio_item flex_bet flex_align_center" @click="changeType('offline')">
            	<div class="icon_box">
            		<img src="../assets/img/pay_02.png" alt="">
            	</div>
            	<span class="flex_one">线下转账汇款</span>
            	<icon type="success-no-circle" v-if="payType == 'offline'"></icon>
            </div>
            
        	</div>
        </div>
				<!-- <my-card :title="'选择支付方式：'" @initCard="initCard"></my-card> -->


				<sub-btn :text="btnText" @clickme="toConfirm" :disabled="Disabled"></sub-btn>
        
        <div class="sec_3">
          <p>充值说明：</p>
          <p>1，领航方舟平台原则上不允许非本人充值情况发生，一经发现，资金将退回充值的银行卡；</p>
          <p>2，银联在线充值单笔最高20000元，如果银行卡限额低于此限额，以银行卡设置为准；</p>
          <p>3，严禁利用充值功能进行信用卡套现、转账、洗钱等行为，一经发现，资金将退回原卡并封停账号30天以上；</p>
          <p>4，在线充值可能存在延迟现象，如有疑问，请联系客服。</p>
          
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
	import MoneyInput from '@/components/common/MoneyInput'
	import MyCard from '@/components/common/MyCard'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '我要充值',
					backText: '',
					showBack: true,
					text: '重要消息，不容错过'
				},
				list: [],
				btnText: '立即充值',
				Disabled: false,
        initMoney: 0,
				money: '',
        bankNum: null,
        payType: 'online'
			}
		},
		filters: {

		},
		computed: {
			...mapState(['userInfo']),
		},
		components: {
			MoneyInput,
			MyCard
		},
		methods: {
			// ...mapActions(['getNoticeNum']),
      changeType (value) {
        this.payType = value;
      },
			inputChange(value) {
        this.initmoney = Number(value);
				this.money = Number(value);
			},
      initCard (obj) {
        console.log(obj)
        this.bankNum = obj.bankNum;
      },
      toConfirm () {
        if (this.money <= 0) {
        	this.$vux.toast.text('请填写金额','middle')
        	return;
        }
        this.$router.push({
          path: "/ConfirmRecharge?money="+this.money
        })
      },
			async Submit() {
				try {
          if (this.money <= 0) {
            this.$vux.toast.text('请填写金额','middle')
            return;
          }
          let params = {
            money: this.money,//	是	double	金额
            type: 'FUNDIN',//	是	string	类型(FUNDIN 入金 FUNDOUT 出金)
            bankNum: this.bankNum
          }
          
					const res = await this.fetch("/api/asset_detail/createAssetDetail",params,'POST')
					console.log(res)
					if (res.statusCode == '0000') {
						this.$vux.toast.text('提交成功','middle')
						this.initMoney = 0;
						this.money = '';
					}else {
						this.$vux.toast.text(res.msgCode,'middle')
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}

			}
			
		},
		activated() {

		}
	}
</script>

<style scoped>
	.con_box {
		background-color: #f4f5ff;
	}

	.sec_1 {
		width: 100%;
		padding: 0.4rem 0;
		background-color: #fff;
	}

	.sec_1 .btn {
		width: 20%;
		background-color: #ffb226;
		font-size: 0.353rem;
		color: #fff;
		border-radius: 5px;
		line-height: 0.8rem;
		text-align: center;
		margin: 0 0.32rem;
	}

	.sec_1 .input_box {
		width: 72%;
	}

	.tui_btn button {
		color: #28347e;
		font-size: 0.366rem;
		border: 1px solid #28347e;
		background-color: #fff;
		border-radius: 0.4rem;
		padding: 0 0.266rem;
		margin-top: 0.266rem;
		margin-right: 0.266rem;
	}
  
  .sec_2 {
    margin-top: 0.3rem;
    background-color: #fff;
    padding: 0 0.2rem;
  }
  .sec_2>p {
    font-size: .3rem;
    color: #666;
    line-height: 0.7rem;
  }
  .radio_item {
    height: 1.2rem;
    border-bottom: 1px solid #ddd;
  }
  .radio_item .icon_box {
    width: 1rem;
    margin-right: 0.4rem;
  }
  .radio_item .icon_box img {
    width: 100%;
  }
  .radio_item .weui-icon-success-no-circle {
    font-size: 18px;
  }
  .sec_3 {
    padding: 0.266rem;
  }
  .sec_3 p {
    color: #666;
    font-size: .3rem;
    line-height: 0.666rem;
  }
</style>
