<template>
	<div class="page_box">
    
    <left-text-header :leftOptions="headOption">
      <div slot="right-box" class="notice_box">
        <router-link to="/notice">
          <i class="iconfont icon-xiaoxigonggao"></i>
        </router-link>
      </div>
    </left-text-header>
    
    <div class="body_box" id="my-box">
      
      
    <router-link to="/userinfo">
      <div class="sec_1 flex_row flex_align_center">
        <div class="img_box">
          <img :src="userMess.headImg" />
        </div>
        <span>{{userMess.name}}</span>
        <div class="id_tab">
          <span>{{userMess.department | formatDepart}}</span>
        </div>
      </div>
    </router-link>  
      <div class="sec_2">
        <div class="flex_bet">
        	<span>账户资产总额：</span>
        	<span>{{userMess.total | formattNum}}</span>
        </div>
        <div class="flex_bet">
          <span>账户可用余额：</span>
          <span>{{userMess.balance | formattNum}}</span>
        </div>
        <div class="flex_bet">
        	<span>当前投资金额：</span>
        	<span>{{userMess.used | formattNum}}</span>
        </div>
        <div class="flex_bet">
        	<span>历史累计净收益：</span>
        	<span class="red">{{userMess.netProfit | formattNum}}</span>
        </div>
        <div class="flex_bet">
        	<span>当前入伙盈亏：</span>
        	<span class="green">{{userMess.fundProfit | formattNum}}</span>
        </div>
        
        
      </div>
      
      <chart></chart>
      
      <mine-menu></mine-menu>
      
     </div>
	</div>
</template>

<script>
  import {formatMoney,formatDepartment} from '@/utils/util'
  import LeftTextHeader from '@/components/common/leftTextHeader'
  import Chart from '@/components/mine/Chart'
  import MineMenu from '@/components/mine/Menu'
  
	import {		mapState	} from 'vuex'
	import {		XHeader	} from 'vux'
	export default {
		data() {
			return {
				mess: {},
				headOption: {
					showBack: false,
					title: '我的资产'
				},
				show11: false,
			}
		},
    filters: {
    	formatDepart: function(value) {
    		return formatDepartment(value)
    	},
    	formattNum: function(value) {
    		return formatMoney(value,0)
    	}
    },
		computed: {
			...mapState(['userInfo', 'userMess', 'noticeNum']),
		},
		components: {
			XHeader,
      LeftTextHeader,
      Chart,
      MineMenu
		},
		methods: {
			
		},
		activated() {
		}
	}
</script>

<style scoped>
  
  .body_box {
    padding-top: 120px;
  }
  
  .notice_box i{
    font-size: 0.6rem;
  }
  
  .sec_1 {
    background-color: rgba(211,211,211,.8);
    padding: 0.16rem 0.56rem;
  }
  .sec_1 .img_box {
    width: 1.773rem;
    height: 1.773rem;
    border: 3px solid #fff;
    border-radius: 50%;
    overflow: hidden;
  }
  .sec_1 .img_box img {
    width: 100%;
  }
  .sec_1>span {
    font-size: 0.44rem;
    color: #202966;
    margin: 0 0.266rem;
  }
  
  
  .sec_2 {
    background-color: #f4f5ff;
    padding: 0 0.533rem;
  }
  .sec_2 span {
    font-size: 0.373rem;
    color: #202966;
    line-height: 0.8rem;
  }
  .sec_2 span.red {
    color: red;
  }
  .sec_2 span.green {
    color: green;
  }
  
  
  
  
</style>
