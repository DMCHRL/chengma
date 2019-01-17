<template>
	<div class="item_box box_shadow">
		<h3>{{item.name}}</h3>
        <router-link :to="'/join-details?id='+item.id">
            
        
		<div class="flex_bet">
			<div class="col_box">
				<div>
					<p>预期年化收益率</p>
					<span>{{item.yearProfit1}}%+{{item.yearProfit2}}%</span>
				</div>
				<div>
					<p>剩余可入伙金额</p>
					<span>{{item.canJoin}}元</span>
				</div>
			</div>
			<div class="col_box">
				<div>
					<p>操作周期</p>
					<span>{{item.cycleTime}}天</span>
				</div>
				<div>
					<p>最大风险</p>
					<span>{{item.risk}}%</span>
				</div>
			</div>
		</div>
        </router-link>
		<div class="flex_bet">
			<div class="jin_box flex_one">
				<div class="flex_bet">
					<p>募集进度</p>
					<p>{{item.progress}}%</p>
				</div>
				<div class="jin_progress">
					<div v-show="item.progress" class="jin_active" :style="'width:' + item.progress + '%'"></div>
				</div>
			</div>
			<div class="item_btn">
				<router-link :to="'/confirm-join?id='+item.id">
                    <p>立即入伙</p>
                </router-link>
				<!-- <span>(操作周期46天)</span> -->
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'PartnerItem',
		props: {
			item: {
				// type: Object
			},
		},
		data() {
			return {
				
			}
		},
		computed: {},
		components: {},
		methods: {

		},

	}
</script>

<style scoped>
	.item_box {
		text-align: center;
		margin-bottom: 10px;
		background-color: #fff;
	}

	.item_box h3 {
		font-size: 0.46rem;
		color: #202a65;
		line-height: 40px;
        font-weight: normal;
	}

	.col_box {
		width: 50%;
		padding-bottom: 20px;
	}

	.col_box p {
		font-size: 0.373rem;
		color: #202a65;
		line-height: 40px;
	}

	.col_box span {
		font-size: 0.346rem;
		color: #ca0811;

	}

	.jin_box {
		background-color: #f4f5ff;
		border-right: 2px solid #fff;
		height: 1.066rem;
		padding: 0 10px;
	}

	.jin_box p {
		font-size: 0.373rem;
		color: #202a65;
		margin-top: 5px;
		margin-bottom: 5px;
	}

	.jin_progress {
		position: relative;
		background-color: #2a388c;
		widows: 100%;
		height: 0.133rem;
		border-radius: 0.133rem;
		overflow: hidden;
	}

	.jin_progress .jin_active {
		position: absolute;
		left: 0;
		top: 0;
		bottom: 0;
		background-color: #f9ba4e;
	}

	.jin_progress .jin_active:after {
		content: '';
		display: block;
		position: absolute;
		left: 100%;
		width: 0;
		height: 0;
		border-width: 0 0 0.2rem 0.2rem;
		border-style: solid;
		border-color: transparent transparent transparent #f9ba4e;
	}

	.item_btn {
		width: 3.466rem;
		height: 1.066rem;
		background-color: #e0e4ff;
	}

	.item_btn p {
		font-size: 0.4rem;
		color: #202a65;
		line-height: 1.066rem;
	}

	.item_btn span {
		font-size: 0.293rem;
		color: #888;
	}
</style>
