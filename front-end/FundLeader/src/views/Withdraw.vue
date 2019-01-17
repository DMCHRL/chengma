<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>

		<div class="body_box box_pb0" id="my-box">
      
      <div class="con_box">
        
        <div class="sec_1 clearfix">
          <div class="btn pull-left">提现金额</div>
          <div class="input_box pull-right">
            <money-input @change="inputChange" :money="initMoney"></money-input>
            <div class="tui_btn">
              <span>当前账户可提现余额<i>{{userMess.balance | formattNum}}</i>元</span>
            </div>
          </div>
        </div>
        
        
        <my-card :title="'选择到账路径：'" @initCard="initCard"></my-card>
        
        
        <sub-btn :text="btnText" @clickme="Submit" :disabled="Disabled"></sub-btn>
        
      </div>
      
      
		</div>

	</div>
</template>

<script>
  import {formatMoney} from '@/utils/util'
	import {		mapState,		mapActions	} from 'vuex'
  import MoneyInput from '@/components/common/MoneyInput'
  import MyCard from '@/components/common/MyCard'
	export default {
		directives: {
		},
		data() {
			return {
				headOption: {
					title: '我要提现',
					backText: '',
					showBack: true,
          text: '重要消息，不容错过'
				},
				list: [],
				btnText: '确认提现',
        Disabled: false,
        
        initMoney: 0,
        money: '',
        bankNum: null,
			}
		},
		filters: {
			formattNum: function(value) {
				return formatMoney(value,0)
			}
		},
		computed: {
			...mapState(['userMess']),
		},
		components: {
      MoneyInput,
      MyCard
		},
		methods: {
			inputChange(value) {
				this.initMoney = Number(value);
				this.money = Number(value);
			},
			initCard (obj) {
				console.log(obj)
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
            type: 'FUNDOUT',//	是	string	类型(FUNDIN 入金 FUNDOUT 出金)
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
  .tui_btn span{
    color: #28347e;
    font-size: 0.373rem;
    
  }
  .tui_btn span i {
    color: #ce0217;
  }
  
</style>
