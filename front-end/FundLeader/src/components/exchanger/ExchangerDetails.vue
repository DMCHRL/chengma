<template>
	<div class="content_box">

		<my-header v-show="headShow" :leftOptions="headOption"></my-header>
    
    <div class="hui_content" id="hui-content">
      
    
    
		<div class="mine_top backimg5" :style="{backgroundImage: 'url(' + mess.background + ')' }">
			<x-header :left-options="headOption"></x-header>
			<div class="mine_content flex_col">
				<h3>{{mess.sinksName}}</h3>
				<p>{{mess.sinksName2}}</p>
			</div>
		</div>
		<div class="sec_1">
			<div class="sec_1_content">
				<h3>平台介绍</h3>
				<!-- <p>{{mess.explain}}</p> -->
				<div class="flex_bet">
					<div class="item_box">
						<img src="../../assets/img/i_3_01.png" />
						<span>成立时间：{{mess.openTime}}</span>
					</div>
					<div class="item_box">
						<img src="../../assets/img/i_3_02.png" />
						<span>所属国家：{{mess.country}}</span>
					</div>
				</div>
				<div class="flex_bet">
					<div class="item_box">
						<img src="../../assets/img/i_3_03.png" />
						<span>最小手数：{{mess.lots}}</span>
					</div>
					<div class="item_box">
						<img src="../../assets/img/i_3_04.png" />
						<span>最大杠杆：{{mess.lever}}</span>
					</div>
				</div>
				<div class="item_box">
					<img src="../../assets/img/i_3_05.png" />
					<span>点差类型：{{mess.pointType}}</span>
				</div>
				<div class="item_box">
					<img src="../../assets/img/i_3_06.png" />
					<span>主要点差：{{mess.mainPoint}}</span>
				</div>
				<div class="item_box">
					<img src="../../assets/img/i_3_07.png" />
					<span>交易品种：{{mess.tradeType}}</span>
				</div>
			</div>
			<p>{{mess.remark}}</p>
		</div>
		<div class="sec_2">
			<h3>基本信息</h3>
			<div class=" flex_wrap">
				<span>平台类型：</span>
				<span>{{mess.platformType}}</span>
			</div>
			<div class=" flex_wrap">
				<span>开设账户类型：</span>
				<span>{{mess.accountType}}</span>
			</div>
			<div class=" flex_wrap">
				<span>剥头皮：</span>
				<span>{{mess.peel}}</span>
			</div>
			<div class=" flex_wrap">
				<span>对冲：</span>
				<span>{{mess.hedging}}</span>
			</div>
			<div class=" flex_wrap">
				<span>最大交易量：</span>
				<span>{{mess.maxTrade}}</span>
			</div>
			<div class=" flex_wrap">
				<span>出金方式：</span>
				<span>{{mess.fundOut}}</span>
			</div>
			<div class=" flex_wrap">
				<span>入金方式：</span>
				<span>{{mess.fundIn}}</span>
			</div>
			<div class=" flex_wrap">
				<span>出金手续费 ：</span>
				<span>{{mess.fundOutCost}}</span>
			</div>
			<div class=" flex_wrap">
				<span>入金手续费:</span>
				<span>{{mess.fundInCost}}</span>
			</div>
			<div class=" flex_wrap">
				<span>隔夜利息：</span>
				<span>{{mess.rollovers}}</span>
			</div>
			<div class=" flex_wrap">
				<span>爆仓比例：</span>
				<span>{{mess.explosionRatio}}</span>
			</div>
			<div class=" flex_wrap">
				<span>是否支持人民币入金：</span>
				<span>{{mess.rmb}}</span>
			</div>
		</div>
		<div class="sec_3">
			<div class="sec_3_head flex_bet flex_align_center">
				<h3>基本信息</h3>
				<span class="flex_one">{{mess.evaluateScore}}分</span>
				<div class="star_box">
					<img v-for="item in light" src="../../assets/img/star_0.png" />
					<img v-for="item in (5-light)" src="../../assets/img/star_1.png" />
				</div>
			</div>
			<p>{{mess.evaluate}}</p>
		</div>
    
    </div>
    
		<div class="bottom_btn myback">
			<router-link :to="'/open?id='+ mess.id">
				<button>立即开户</button>
			</router-link>
		</div>
    
    
    
	</div>
</template>

<script>
	import {
		XHeader
	} from 'vux'
	export default {
		data() {
			return {
				headOption: {
					title: '汇商详情',
					backText: '',
					showBack: true
				},
				mess: {},
				headShow: false
			}
		},
		computed: {
			id() {
				return this.$route.query.id;
			},
			light() {
				let num = 0;
				if (this.mess.evaluateScore) {
					let score = parseInt(this.mess.evaluateScore);
					num = Math.floor((score / 100) * 5)
				}
				return num;
			}
		},
		components: {
			XHeader
		},
		methods: {
			handleScroll() {
				var scrollTop = document.getElementById('hui-content').pageYOffset || document.getElementById(
					'hui-content').scrollTop || document.getElementById('hui-content').scrollTop
				// console.log(scrollTop)
				if (scrollTop > 100) {
					this.headShow = true;
				} else {
					this.headShow = false;
				}
			},
			initPage() {
				let _this = this;
				_this.$fetch("/api/hpp_sinks/get/" + this.id).then((res) => {
					// console.log(res)
					_this.mess = res.data;
					_this.headOption.title = res.data.sinksName;
				})
			}
		},
		mounted() {
			this.initPage();
			document.getElementById('hui-content').addEventListener('scroll', this.handleScroll)
		}
	}
</script>

<style scoped>
  
  .hui_content {
    padding-top: 0;
  }
  
	.mine_top {
		box-sizing: border-box;
		padding-top: 30px;
		background-size: 100% 100%;
	}

	.mine_content {
		height: 4.1333rem;
		box-sizing: border-box;
		padding: 0 0.5333rem;
		padding-top: 0.8rem;
	}

	.mine_content h3 {
		font-size: 0.68rem;
		color: #fff;
	}

	.mine_content p {
		font-size: 0.4rem;
		color: #fff;
	}

	.sec_1 {
		background-color: #fff;
		padding: 0 0.2666rem;
		padding-top: 0.6666rem;
	}

	.sec_1>p,
  .sec_3>p {
		font-size: 0.4rem;
		color: #494949;
		padding: 0.2666rem 0;
		line-height: 0.666rem;
    text-indent: 2em;
	}

	.sec_1_content {
		border: 1px solid #c6c6c6;
		position: relative;
		padding: 0 0.1333rem;
		padding-top: 0.45rem;
		padding-bottom: 0.2666rem;

	}

	.sec_1_content h3 {
		display: inline-block;
		background-color: #fff;
		font-size: 0.5533rem;
		color: #444;
		position: absolute;
		top: -0.4rem;
		left: 0.1rem;
		padding: 0 0.2666rem;
	}

	.sec_1_content p {
		font-size: 0.3733rem;
		color: #555;
		line-height: 0.7rem;
	}

	.sec_1_content>div>.item_box {
		width: 50%;
	}

	.item_box {
		line-height: 0.7333rem;
		align-items: center;
	}

	.item_box img {
		width: 0.4rem;
		height: 0.3733rem;
		margin: 0 0.2rem;
		position: relative;
		bottom: 0.05rem;
	}

	.item_box span {
		font-size: 0.3733rem;
		color: #555;
	}

	.sec_2,
	.sec_3 {
		background-color: #fff;
		padding: 0.2666rem;
	}

	.sec_2 h3 {
		margin-bottom: 0.2666rem;
	}

	.sec_2 h3,
	.sec_3 h3 {
		font-size: 0.5533rem;
		color: #555;
		padding-left: 0.2666rem;
	}

	.sec_2 span {
		font-size: 0.3733rem;
		color: #555;
		line-height: 0.5866rem;
	}

	.sec_2 span:last-of-type {
		flex: 1;
	}

	.sec_2 span:first-of-type:before {
		content: '';
		display: inline-block;
		width: 0.1333rem;
		height: 0.1333rem;
		background-color: #ffa986;
		border-radius: 50%;
		margin-left: 0.2666rem;
		margin-right: 0.1333rem;
		position: relative;
		bottom: 0.05rem;
	}

	.sec_3_head {
		margin-bottom: 0.2666rem;
	}

	.sec_3_head span {
		font-size: 0.5533rem;
		color: #ffa986;
		font-weight: bold;
		margin-left: 0.2666rem;
	}

	.sec_3_head img {
		width: 0.4rem;
		height: 0.3733rem;
	}

</style>
