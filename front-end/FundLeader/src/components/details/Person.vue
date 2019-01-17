<template>
	<div class="sec_box box_shadow">
		<div class="sec_1 flex_bet">
			<div>
				<div class="name flex_bet">
					<span>{{data.name}}</span>
					<div class="id_tab">
						<span>{{data.department | formatDepart}}</span>
					</div>
				</div>
				<div class="item">
					<img src="../../assets/img/f_icon_01.png" alt="">
					<span>已实名认证</span>
				</div>
				<div class="item">
					<img src="../../assets/img/f_icon_02.png" alt="">
					<span>手机已绑定</span>
				</div>
				<div class="item">
					<img src="../../assets/img/f_icon_03.png" alt="">
					<span>邮箱已绑定</span>
				</div>
			</div>
			<div>
				<div class="img_box">
					<img :src="data.headImg" alt="">
				</div>
			</div>
		</div>

		<div class="sec_2">
            
			<div class="list_item">
				<h3 @click="isShow = !isShow">
					<i v-if="!isShow" class="iconfont icon-right-bottom-triangle"></i>
					<i v-if="isShow" class="iconfont icon-sjxdown"></i>
					投资人信息
				</h3>
				<div class="down_box" v-show="isShow">
					<div class="table_box table_box_1">
						<div class="flex_bet">
							<span>发起产品平均年化收益率</span>
							<span class="flex_one">{{data.avgYearRate}}%</span>
						</div>
						<div class="flex_bet">
							<span>历史总收益</span>
							<span class="flex_one">￥{{data.totalProfit | formattNum}}</span>
						</div>
						<div class="flex_bet">
							<span>产品发起成功率</span>
							<span class="flex_one">{{data.successRate}}%</span>
						</div>
						<div class="flex_bet">
							<span>发起的已清盘产品数量</span>
							<span class="flex_one">{{data.finish}}只<i>（其中盈利{{data.profitNum}}只）</i></span>
						</div>
                        <div class="flex_bet">
                        	<span>历史募集总金额</span>
                        	<span class="flex_one">￥{{data.totalPreFund}}</span>
                        </div>
                        <div class="flex_bet">
                        	<span>历史平均发起产品规模</span>
                        	<span class="flex_one">￥{{data.avgPreFund}}</span>
                        </div>
					</div>
				</div>
			</div>
            
		</div>

	</div>
</template>

<script>
    import {formatMoney,formatDepartment} from '@/utils/util'
	export default {
		props: {
			data: {
				type: Object
			}
		},
		data() {
			return {
                isShow: true,
            }
		},
        watch: {
        	
        },
        filters: {
        	formatDepart: function(value) {
        		return formatDepartment(value)
        	},
            formattNum: function(value) {
            	return formatMoney(value,0)
            }
        },
		computed: {},
		components: {},
		methods: {
            
        },
		mounted() {
        }
	}
</script>

<style scoped>
	.sec_box {
		background-color: rgba(255, 255, 255, .9);
	}

	.sec_1 {
		padding: 0.7rem 1rem;
        padding-bottom: 0.1rem;
	}

	.sec_1 .img_box {
		width: 2.026rem;
		height: 2.026rem;
	}

	.sec_1 .name {
		margin-bottom: 0.4rem;
	}

	.sec_1 .name>span {
		font-size: 0.533rem;
		color: #1f2965;
		font-weight: bold;
		margin-right: 0.266rem;
	}

	.sec_1 .item {
		line-height: 0.666rem;
	}

	.sec_1 .item img {
		width: 0.4rem;
		height: 0.4rem;
		margin-right: 0.133rem;
	}

	.sec_1 .item span {
		font-size: 0.32rem;
		color: #28347e;
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
        .table_box span i {
            color: #FF0000;
        }
    	.table_box_1 span {
    		width: 50%;
    	}
</style>
