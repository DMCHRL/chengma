<template>
	<div class="content">
		<div class="row">
			
			<div class="col-md-3">
				<div class="item_box">
					<div class="_top clearfix two">
						<div class="pull-left">
							<p>盈利总金额</p>
							<p><span>${{userdata.totalGain}}</span></p>
							
						</div>
						<div class="pull-right">
							<p>佣金总金额</p>
							<p><span>{{userdata.totalCommission}}</span></p>
						</div>
					</div>
					<div class="_bottom">
						<div id="echarts2" style="height: 120px;">

						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="item_box">
					<div class="_top clearfix third">
						<div class="pull-left">
							<p>入金总额</p>
							<p>$<span>{{userdata.totalIn}}</span></p>
						</div>
						<div class="pull-right">
							<p>出金总额</p>
							<p>$<span>{{userdata.totalOut}}</span></p>
						</div>
					</div>
					<div class="_bottom">
						<div id="echarts3" style="height: 120px;">

						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="item_box">
					<div class="_top clearfix four">
						<div class="pull-left">
							<p>交易者总数</p>
							<p><span>{{userdata.totalAccount}}</span>人</p>
						</div>
						<div class="pull-right">
							<p>交易总手数</p>
							<p><span>{{userdata.totalLots}}</span>手</p>
						</div>
					</div>
					<div class="_bottom">
						<div id="echarts4" style="height: 120px;">

						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="item_box">
					<div class="_top clearfix one">
						<div class="pull-left">
							<p>总胜率</p>
							<p><span>{{userdata.totalSuccessRate}}</span>%</p>
						</div>
						<div class="pull-right">
							<p>人均交易单数</p>
							<p><span>{{userdata.avgTradeCount}}</span>单</p>
						</div>
					</div>
					<div class="_bottom">
						<div id="echarts1" style="height: 120px;">

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="bottom_echart">
			<div id="echarts5" style="height: 560px;">

			</div>
		</div>
	</div>
</template>

<script>
	// 引入基本模板
	let echarts = require('echarts/lib/echarts')
	// 引入折线组件
	require('echarts/lib/chart/line')
	// 引入提示框和title组件
	require('echarts/lib/component/tooltip')
	require('echarts/lib/component/title')
	export default {
		name: 'homepage',
		data() {
			return {
				userdata: {}
			}
		},
		methods: {
			initEchart() { //初始化圖標
				let myChart1 = echarts.init(document.getElementById('echarts1'));
				let myChart2 = echarts.init(document.getElementById('echarts2'));
				let myChart3 = echarts.init(document.getElementById('echarts3'));
				let myChart4 = echarts.init(document.getElementById('echarts4'));
				let myChart5 = echarts.init(document.getElementById('echarts5'));
				let option1 = {
					title: {
						text: '新增交易者',
						textStyle: {
							color: '#19183e',
							fontSize: 14
						}
					},
					grid: {
						top: '40px',
						bottom: '30px',
						left: '30px',
						right: '30px',
						borderColor: '#fff'
					},
					xAxis: {
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
						nameTextStyle: {
							color: "#111",
							fontSize: '15px'
						},
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
					},
					yAxis: {
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
						axisLabel: {
							show: false
						},
						splitLine: {
							show: false
						}
					},
					series: [{
						data: [5, 2, 1, 4, 8, 2, 6],
						type: 'line',
						showSymbol: false,
						lineStyle: {
							color: '#26c6da'
						}
					}],
					animation: true
				};
				let option2 = {
					title: {
						text: '交易量',
						textStyle: {
							color: '#19183e',
							fontSize: 14
						}
					},
					grid: {
						top: '40px',
						bottom: '30px',
						left: '30px',
						right: '30px',
						borderColor: '#fff'
					},
					xAxis: {
						type: 'category',
						data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '00:00'],
						nameTextStyle: {
							color: "#111",
							fontSize: '15px'
						},
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						}
					},
					yAxis: {
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
						axisLabel: {
							show: false
						},
						splitLine: {
							show: false
						}
					},
					series: [{
						data: [5, 2, 1, 4, 8, 2, 5, 2, 1, 4, 8, 2, 6],
						type: 'line',
						showSymbol: false,
						lineStyle: {
							color: '#f14b3b'
						}
					}],
					animation: true
				};
				let option3 = {
					title: {
						text: '出入金',
						textStyle: {
							color: '#19183e',
							fontSize: 14
						}
					},
					grid: {
						top: '40px',
						bottom: '30px',
						left: '30px',
						right: '30px',
						borderColor: '#fff'
					},
					xAxis: {
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
						nameTextStyle: {
							color: "#111",
							fontSize: '15px'
						},
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						}
					},
					yAxis: {
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
						axisLabel: {
							show: false
						},
						splitLine: {
							show: false
						}
					},
					color: ['#37a0f4', '#fd7c4a'],
					legend: {
						top: '5px',
						right: '10px',
						itemWidth: 8,
						itemHeight: 8,
						data: [{
								name: '出金',
								icon: 'circle',
								textStyle: {
									fontSize: 12
								}
							},
							{
								name: '入金',
								icon: 'circle',
								textStyle: {
									fontSize: 12
								}
							}
						],
					},
					series: [{
							name: '出金',
							data: [5, 2, 1, 4, 8, 2, 6],
							type: 'line',
							showSymbol: false,
							lineStyle: {
								color: '#37a0f4'
							}
						},
						{
							name: '入金',
							data: [3, 4, 5, 7, 3, 8, 12, 17],
							type: 'line',
							showSymbol: false,
							lineStyle: {
								color: '#fd7c4a'
							}
						}
					],
					animation: true
				};
				let option4 = {
					title: {
						text: '佣金',
						textStyle: {
							color: '#19183e',
							fontSize: 14
						}
					},
					grid: {
						top: '40px',
						bottom: '30px',
						left: '30px',
						right: '30px',
						borderColor: '#fff'
					},
					xAxis: {
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
						nameTextStyle: {
							color: "#111",
							fontSize: '15px'
						},
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						}
					},
					yAxis: {
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
						axisLabel: {
							show: false
						},
						splitLine: {
							show: false
						}
					},
					series: [{
						data: [5, 2, 1, 4, 8, 2, 6],
						type: 'line',
						showSymbol: false,
						lineStyle: {
							color: '#fd713b'
						}
					}],
					animation: true
				};
				let option5 = {
					title: {
						text: '昨日交易量',
						textStyle: {
							color: '#19183e',
							fontSize: 18
						},
						top: '20px',
						left: '10px'
					},
					color: ['#7a77c2'],
					legend: {
						top: '20px',
						right: '30px',
						data: [{
							name: '交易量',
							icon: 'roundRect',
							textStyle: {
								fontSize: 14
							}
						}],
					},
					grid: {
						top: '65px',
						bottom: '50px',
						left: '80px',
						right: '80px',
						borderColor: '#fff'
					},
					xAxis: {
						type: 'category',
						data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00', '24:00', '00:00'],
						nameTextStyle: {
							color: "#111",
							fontSize: '15px'
						},
						boundaryGap: false,
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						}
					},
					yAxis: {
						type: 'value',
						axisLine: {
							lineStyle: {
								color: '#ccc'
							}
						},
						axisTick: { //坐标刻度
							show: false
						},
						//				        axisLabel: {
						//				        	show: false
						//				        },
						splitLine: {
							show: false
						}
					},
					series: [{
						name: '交易量',
						data: [0,1,3,1,2,0,2,3,1,3,2,3,1,2,3,0,2,2,1,0,2,1,1,3,1,0],
						type: 'line',
						smooth:true,
						showSymbol: false,
						lineStyle: {
							color: '#26c6da'
						},
						areaStyle: {
							color: '#7a77c2'
						}
					}],
					animation: true
				};
				myChart1.setOption(option1);
				myChart2.setOption(option2);
				myChart3.setOption(option3);
				myChart4.setOption(option4);
				myChart5.setOption(option5);
			},
			initPage () {
				let _this = this;
				_this.$until.superGet('/api/tlb-trade/getTlbData',function (res) {
//					console.log(res)
					if (res.data == null) {
						_this.$router.go(-1)
					}
					_this.userdata = res.data;
				})
			}
		},
		mounted() {
//			this.initPage();
//			this.initEchart();
			
		}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	.content {
		padding-left: 5px;
		padding-right: 5px;
	}
	
	.content .row {
		margin-left: -5px;
		margin-right: -5px;
	}
	
	.content .col-md-3 {
		padding-left: 5px;
		padding-right: 5px;
	}
	
	.item_box {
		background-color: #fff;
		box-shadow: 1px 1px 2px 2px #c0c0c6;
	}
	
	._top {
		background-image: url(http://tlb.txasfx.com/crm/img/h_bg_11.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
		text-align: center;
		padding: 27px 0;
	}
	
	._top.two {
		background-image: url(http://tlb.txasfx.com/crm/img/h_bg_12.png);
	}
	
	._top.third {
		background-image: url(http://tlb.txasfx.com/crm/img/h_bg_13.png);
	}
	
	._top.four {
		background-image: url(http://tlb.txasfx.com/crm/img/h_bg_14.png);
	}
	
	._top .pull-left {
		width: 60%;
	}
	
	._top .pull-right {
		width: 40%;
	}
	
	._top p:first-of-type {
		margin-bottom: 35px;
	}
	
	._top p {
		font-size: 16px;
		color: #fff;
	}
	
	._top p span {
		font-size: 30px;
		color: #fff;
	}
	
	.bottom_echart {
		margin-top: 20px;
		background-color: #fff;
		box-shadow: 1px 1px 2px 2px #c0c0c6;
	}
</style>