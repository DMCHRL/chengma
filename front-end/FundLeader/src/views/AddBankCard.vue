<template>
	<div class="page_box">
		<my-header :leftOptions="headOption"></my-header>
		<div class="body_box box_pt150 box_pb0" id="my-box">
      
      <div class="con_box">
        
        <div class="sec_1">
          
          <div class="item_box flex_bet">
            <span>银行卡号</span>
            <div class="flex_one">
              <input type="number" value="" v-model="datas.bankNum" />
            </div>
          </div>
          <div class="item_box flex_bet">
          	<span>开户银行</span>
          	<div class="flex_one">
          		<input type="text" value="" v-model="datas.bank"/>
          	</div>
          </div>
          <div class="item_box flex_bet">
          	<span>银行分行</span>
          	<div class="flex_one">
          		<input type="text" value="" v-model="datas.subBank"/>
          	</div>
          </div>
          <div class="item_box flex_bet">
          	<span>银行所在地</span>
          	<div class="flex_one">
          		<input type="text" value="" v-model="datas.location"/>
          	</div>
          </div>
          
        </div>
        
        
        
        <sub-btn :text="subbtn.text" @clickme="Submit" :disabled="subbtn.disabled"></sub-btn>
        
      </div>
      
      
		</div>

	</div>
</template>

<script>
	import {		mapState,		mapActions	} from 'vuex'
  import MoneyInput from '@/components/common/MoneyInput'
  import MyCard from '@/components/common/MyCard'
	export default {
		directives: {
		},
		data() {
			return {
				headOption: {
					title: '添加银行卡',
					backText: '',
					showBack: true,
				},
        subbtn: {
        	text: '确认',
        	disabled: false
        },
				datas: {
          bankNum: '',
          bank: '',
          subBank: '',
          location: ''
        }
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
			
			async Submit () {
        	try {
        		const res = await this.fetch("/api/bank_info/createBankInfo", this.datas, "POST");
        		console.log(res)
        		if (res.statusCode == '0000') {
        			this.$vux.toast.text('添加成功','middle')
        			this.datas = {
                bankNum: '',
                bank: '',
                subBank: '',
                location: ''
              }
              this.$router.go(-1)
        		}else {
        			this.$vux.toast.text(res.msgCode,'middle')
        		}
        	} catch (err) {
        		console.log('获取数据失败', err);
        	}
      },
			
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
    padding: 0.4rem .5rem;
    margin-bottom: 1.5rem;
  }
  .sec_1 .item_box {
    margin-bottom: 0.133rem;
    line-height: 50px;
  }
  .sec_1 .item_box span {
    font-size: 0.35rem;
    color: #28347e;;
    width: 2rem;
  }
  .sec_1 .item_box input {
    border-bottom: 1px solid #999;
    width: 100%;
    font-size: 0.37rem;
    color: #666;
    box-sizing: border-box;
    padding: 2px 10px;
    background-color: transparent;
  }
</style>
