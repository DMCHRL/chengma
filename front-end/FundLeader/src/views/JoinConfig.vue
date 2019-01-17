<template>
	<div class="content_box">
		<fund-header :leftOptions="headOption"></fund-header>

		<div class="hui_content hui_content_pt150" id="hui-content">
      
      <div class="back_fff">
        <div class="sec_tab flex_row box_shadow">
        		<div class="item_box flex_align_center flex_center">
        				<span class="active">募集中</span>
        				<i class="iconfont icon-sjxdown"></i>
        		</div>
        		<div class="item_box flex_align_center flex_center">
        			<span>操作中</span>
        			<!-- <i class="iconfont icon-sjxup"></i> -->
        		</div>
        		<div class="item_box flex_align_center flex_center">
        			<span>已满盘</span>
        			<!-- <i class="iconfont icon-sjxdown"></i> -->
        		</div>
        </div>
        
        <div class="sec_list">
          
          <template v-for="(item,index) in [20,50,80]">
            <partner-item :key="index" :item="item"></partner-item>
          </template>
          
        </div>
        
        
      </div>
      
      
		</div>

	</div>
</template>

<script>
	import {		mapState,		mapActions	} from 'vuex'
  import PartnerItem from '@/components/PartnerItem'
	export default {
		directives: {
		},
		data() {
			return {
				headOption: {
					title: '我的合伙配置',
					backText: '',
					showBack: true,
          text: '重要消息，不容错过'
				},
				list: [],
				
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
			
			initPage() {
				let _this = this;
				
				_this.$fetch("/api/hpp_notice_sign/read/" + _this.userInfo.mobile).then((res) => {
					// console.log(res)
					
				})
			}
		},
		activated() {
      
		}
	}
</script>

<style scoped>
  
  .back_fff {
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
  .sec_tab .item_box span.active {
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
