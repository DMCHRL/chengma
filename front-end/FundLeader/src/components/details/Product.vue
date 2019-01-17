<template>
	<div class="sec_box box_shadow">
		<div class="sec_1 ">
			<h3>{{datas.name}}</h3>
			<div class="flex_bet">
				<div class="slide_box">
					<div>
						<p>{{datas.yearProfit1}}%+{{datas.yearProfit2}}%</p>
						<p>预期收益率</p>
					</div>
					<div>
						<span>{{datas.risk}}%</span>
						<p>合伙人最大风险</p>
					</div>
				</div>
				<div class="mid_box flex_one">

					<circular-progress :percent="datas.progress"></circular-progress>

				</div>
				<div class="slide_box">
					<div>
						<p>￥{{datas.targetFund | formatt}}</p>
						<p>目标募集金额</p>
					</div>
					<div>
						<span>￥{{datas.canJoin | formatt}}</span>
						<p>还可入伙</p>
					</div>
				</div>
			</div>
			<p class="bottom">已入伙：{{datas.num}}人</p>
		</div>

		<div class="sec_2">

			<div class="list_item">
				<h3 @click="isShow = !isShow">
					<i v-if="!isShow" class="iconfont icon-right-bottom-triangle"></i>
					<i v-if="isShow" class="iconfont icon-sjxdown"></i>
					风险及预期收益
				</h3>
				<div class="down_box" v-show="isShow">
					<p>目标募集金额￥{{datas.targetFund}}元，产品最小募集合伙金额￥{{datas.minFund}}，
						产品最大风险{{datas.risk}}%，产品操作期预期收益{{datas.yearProfit1+datas.yearProfit2}}%</p>
					<div class="table_box">
              <div class="flex_bet">
                <span>角色</span>
                <span>承担风险</span>
                <span class="flex_one">预期总收益(元)</span>
              </div>
            <template v-for="item in datas.expectRiskProfitDTOList">
              
              <div class="flex_bet">
              	<span>{{item.role}}</span>
              	<span>{{item.risk}}%</span>
              	<span class="flex_one">{{item.preProfit}}</span>
              </div>
              
            </template>
						<!-- 
						<div class="flex_bet">
							<span>发起人</span>
							<span>5%</span>
							<span class="flex_one">268,100.00</span>
						</div>
						<div class="flex_bet">
							<span>合伙人</span>
							<span>5%</span>
							<span class="flex_one">268,100.00</span>
						</div> -->
					</div>
				</div>
			</div>

			<div class="list_item">
				<h3 @click="isShow1 = !isShow1">
					<i v-if="!isShow1" class="iconfont icon-right-bottom-triangle"></i>
					<i v-if="isShow1" class="iconfont icon-sjxdown"></i>
					收益分配规则
				</h3>
				<div class="down_box" v-show="isShow1">

					<div class="table_box">
						<div class="flex_bet">
							<span>角色</span>
							<span class="flex_one">收益分配</span>
						</div>
            <template v-for="item in datas.allotRuleDTOList">
              
              <div class="flex_bet">
                <span>{{item.role}}</span>
                <span class="flex_one">{{item.remark}}</span>
              </div>
              
            </template>
						<!-- <div class="flex_bet">
							<span>发起人</span>
							<span class="flex_one">产品收益中提取（发起提成+风险金收益）</span>
						</div>
						<div class="flex_bet">
							<span>合伙人</span>
							<span class="flex_one">产品中收益提取（操盘提成+风险金收益）</span>
						</div> -->
					</div>
				</div>
			</div>

			<div class="list_item">
				<h3 @click="isShow2 = !isShow2">
					<i v-if="!isShow2" class="iconfont icon-right-bottom-triangle"></i>
					<i v-if="isShow2" class="iconfont icon-sjxdown"></i>
					产品信息
				</h3>
				<div class="down_box" v-show="isShow2">

					<div class="table_box table_box_1">
						<div class="flex_bet">
							<span>目标募集总金额</span>
							<span class="flex_one">￥{{datas.targetFund}}</span>
						</div>
						<div class="flex_bet">
							<span>产品最小募集合伙金额</span>
							<span class="flex_one">￥{{datas.minFund}}</span>
						</div>
						<div class="flex_bet">
							<span>可入伙的合伙人最低段位</span>
							<span class="flex_one">{{datas.minLevel}}</span>
						</div>
						<div class="flex_bet">
							<span>募集开始时间</span>
							<span class="flex_one">{{datas.startAt}}</span>
						</div>
						<div class="flex_bet">
							<span>计划操作开始时间</span>
							<span class="flex_one">{{datas.operationAt}}</span>
						</div>
						<div class="flex_bet">
							<span>计划操作结束时间</span>
							<span class="flex_one">{{datas.endAt}}</span>
						</div>
						<div class="flex_bet">
							<span>募集成功可提前进入操作期</span>
							<span class="flex_one">{{datas.successFlag}}</span>
						</div>
						<div class="flex_bet">
							<span>合伙人的收益达预期可提前清盘</span>
							<span class="flex_one">{{datas.partnerFlag}}</span>
						</div>
						<div class="flex_bet">
							<span>产品描述</span>
							<span class="flex_one">{{datas.remark}}</span>
						</div>
					</div>
				</div>
			</div>

			<div class="list_item">
				<h3 @click="isShow3 = !isShow3">
					<i v-if="!isShow3" class="iconfont icon-right-bottom-triangle"></i>
					<i v-if="isShow3" class="iconfont icon-sjxdown"></i>
					投资风格
				</h3>
				<div class="down_box" v-show="isShow3">

					<div class="table_box table_box_white">
						
						<div class="flex_bet">
							<span>交易平台</span>
							<span class="flex_one">{{datas.platform}}</span>
						</div>
						<div class="flex_bet">
							<span>投资策略</span>
							<span class="flex_one">{{datas.strategy}}</span>
						</div>
						<div class="flex_bet">
							<span>买卖方向</span>
							<span class="flex_one">{{datas.direction}}</span>
						</div>
            <div class="flex_bet">
            	<span>投资范围</span>
            	<span class="flex_one">{{datas.range}}</span>
            </div>
					</div>
				</div>
			</div>
		</div>


	</div>
</template>

<script>
  import {formatMoney} from '@/utils/util'
	import CircularProgress from './CircularProgress'
	export default {
		name: 'Product',
		props: {
			datas: {
				type: Object,
			},
		},
    filters: {
    	formatt: function(value) {
    		return formatMoney(value,0)
    	}
    },
		data() {
			return {
				percent: 20,
				isShow: true,
				isShow1: false,
				isShow2: false,
				isShow3: false,
			}
		},
		computed: {},
		components: {
			CircularProgress
		},
		methods: {},
		mounted() {}
	}
</script>

<style scoped>
	.sec_box {
		background-color: rgba(255, 255, 255, .9);
	}

	.sec_1 {
		border-bottom: 1px solid #ccc;
	}

	.sec_1 h3 {
		text-align: center;
		font-size: 0.4rem;
		color: #202a67;
		line-height: 1.2rem;
    height: 1.2rem;
	}

	.sec_1 p {
		color: #202a67;
		font-size: 0.346rem;
	}

	.sec_1 p.bottom {
		color: #ca0811;
		line-height: 1.066rem;
		text-align: center;
		font-weight: bold;
	}

	.sec_1 span {
		font-size: .4rem;
		color: #ca0811;
	}

	.slide_box {
		width: 30%;
		text-align: center;
	}

	.slide_box div {
		margin: 23px 0;
	}

	.list_item {
		padding: 10px;
	}

	.list_item h3 {
		font-size: 0.373rem;
		color: #28347e;
	}

	.list_item h3 i {
		font-size: 14px;
		color: #fbaa14;
		margin-right: 4px;
	}

	.list_item h3 i.icon-right-bottom-triangle {
		font-size: 12px;
		margin-right: 6px;
	}

	.list_item p {
		font-size: 0.293rem;
		color: #ccc;
		padding: 5px 0;
	}

	.table_box {
		padding: 10px 0;
	}

	.table_box span {
		background-color: #f5f6ff;
		font-size: 0.293rem;
		color: #28347e;
		border: 1px solid #fff;
		text-align: center;
		width: 1.866rem;
		line-height: 0.533rem;
	}

	.table_box_1 span {
		width: 50%;
	}
  .table_box_white span {
    background-color: #fff;
    color: #999;
  }
  .table_box_white span:last-of-type {
    color: #666;
  }
</style>
