<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">

			<div class="con_box">

				<div class="sec_1 clearfix">
          <div class="item_box flex_bet flex_align_center">
          	<span>充值金额（人民币）</span>
          	<span class="price tag-read" :data-clipboard-text="money" @click="copy">{{money | formattNum}}</span>
          </div>
					<div class="item_box flex_bet flex_align_center">
            <span>授权收款商户</span>
            <span class="tag-read" :data-clipboard-text="datas.company" @click="copy">{{datas.company}}</span>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<span>授权收款银行卡号</span>
          	<span class="tag-read" :data-clipboard-text="datas.bankNum" @click="copy">{{datas.bankNum}}</span>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<span>授权收款人</span>
          	<span class="tag-read" :data-clipboard-text="datas.name" @click="copy">{{datas.name}}</span>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<span>开户银行</span>
          	<span class="tag-read" :data-clipboard-text="datas.bank" @click="copy">{{datas.bank}}</span>
          </div>
          <div class="item_box flex_bet flex_align_center">
          	<span>备注码</span>
          	<span class="tag-read" :data-clipboard-text="datas.remark" @click="copy">{{datas.remark}}</span>
          </div>
          <p>提示：点击阴影复制信息</p>
				</div>

        
				<my-card :title="'选择充值银行卡：'" @initCard="initCard"></my-card>


				<sub-btn :text="btnText" @clickme="Submit" :disabled="Disabled"></sub-btn>
        
			</div>


		</div>

	</div>
</template>

<script>
	import {
		mapState,
		mapActions
	} from 'vuex'
  import {formatMoney} from '@/utils/util'
	import MoneyInput from '@/components/common/MoneyInput'
	import MyCard from '@/components/common/MyCard'
  import Clipboard from 'clipboard'
	export default {
		directives: {},
		data() {
			return {
				headOption: {
					title: '确认充值',
					backText: '',
					showBack: true,
					text: '重要消息，不容错过'
				},
				datas: {},
				btnText: '确认充值',
				Disabled: false,
        initMoney: 0,
				money: '',
        bankNum: null,
        
			}
		},
		filters: {
      formattNum: function(value) {
      	return formatMoney(value,2)
      }
		},
		computed: {
			...mapState(['userInfo']),
      moneyNum () {
        return this.$route.query.money;
      }
		},
    watch: {
    },
		components: {
			MoneyInput,
			MyCard
		},
		methods: {
			// ...mapActions(['getNoticeNum']),
      async initData() {
        try {
         
          const res = await this.fetch("/api/receivables/findNew")
          console.log(res)
          if (res.statusCode == '0000') {
            this.datas = res.data;
          }else {
            console.log('获取数据失败', res);
          }
        } catch (err) {
          console.log('获取数据失败', err);
        }

      },
			inputChange(value) {
        this.initmoney = Number(value);
				this.money = Number(value);
			},
      initCard (obj) {
        // console.log(obj)
        this.bankNum = obj.bankNum;
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
            this.$router.go(-1)
					}else {
						this.$vux.toast.text(res.msgCode,'middle')
					}
				} catch (err) {
					console.log('获取数据失败', err);
				}

			},
      copy() {
      	let _this = this;
      	var clipboard = new Clipboard('.tag-read')
      	clipboard.on('success', e => {
      		//console.log('复制成功')
      		this.$vux.toast.text('复制成功','middle')
      		// 释放内存
      		clipboard.destroy()
      	})
      	clipboard.on('error', e => {
      		// 不支持复制
          this.$vux.toast.text('该浏览器不支持自动复制','middle')
      		// 释放内存
      		clipboard.destroy()
      	})
      },
			
		},
		activated() {
      this.initData();
      this.money = this.moneyNum;
      console.log(this.moneyNum)
		}
	}
</script>

<style scoped>
	.con_box {
		background-color: #f4f5ff;
	}

	.sec_1 {
		width: 100%;
		padding: 0.4rem .2rem;
		background-color: #fff;
	}
  .sec_1 .item_box {
    height: .8rem;
  }
  .sec_1 .item_box span {
    color: #333;
    font-size: .32rem;
  }
  .sec_1 .item_box span:last-of-type{
    color: #666;
    background-color: rgba(60,60,130,.1);
    padding: 0 10px;
    border-radius: 10px;
  }
  .sec_1 .item_box span.price {
    color: #F20D0D;
  }
  .sec_1>P {
    text-align: center;
    font-size: .3rem;
    color: #FF6159;
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
  .sec_3 {
    padding: 0.266rem;
  }
  .sec_3 p {
    color: #666;
    font-size: .3rem;
    line-height: 0.666rem;
  }
</style>
