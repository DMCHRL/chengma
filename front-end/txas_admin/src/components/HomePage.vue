<template>
	<div class="content">
		<div class="row">

			<div class="col-md-3">
				<div class="item_box">
					<div class="_top clearfix two">
						<div class="pull-left">
							<p>盈利总金额</p>
							<p>$<span>{{userdata.totalGain}}</span></p>

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
							<p>交易总手数</p>
							<p><span>{{userdata.totalLots}}</span>手</p>
						</div>
						<div class="pull-right">
							<p>交易者总数</p>
							<p><span>{{userdata.totalAccount}}</span>人</p>
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
			<div class="clearfix">
				<div class="pull-left bottom_item">
					<h3>EURUSD</h3>
					<span class="sell">{{e_sell | eurusdPrice}} <sup>{{e_sell | eurusdPriceSup}}</sup></span>
          <img src="../assets/img/t_1_02.png" />
					<span class="buy">{{e_buy | eurusdPrice}} <sup>{{e_buy | eurusdPriceSup}}</sup></span>
					<p>实时点差:{{Math.round((e_buy-e_sell)*100000)}}</p>
				</div>
				<div class="pull-left bottom_item">
					<h3>XAUUSD</h3>
					<span class="sell">{{x_sell | xauusdPrice}} <sup>{{x_sell | xauusdPriceSup}}</sup></span>
					<img src="../assets/img/t_1_02.png" />
					<span class="buy">{{x_buy | xauusdPrice}} <sup>{{x_buy | xauusdPriceSup}}</sup></span>
					<p>实时点差:{{Math.round((x_buy-x_sell)*100)}}</p>
				</div>
			</div>
			<!-- <div id="echarts5" style="height: 560px;">

			</div> -->
			<div class="chart_box">
				<div class="clearfix">
					<div class="pull-left">
						<el-select v-model="value" placeholder="请选择" @change="ChangeList">
							<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
							</el-option>
						</el-select>
					</div>
					<div class="pull-left" style="margin-left: 20px;">
						<el-date-picker v-model="value5" type="datetimerange" :picker-options="pickerOptions2" range-separator="至"
						 start-placeholder="开始日期" end-placeholder="结束日期" align="right" @change="dateChange">
						</el-date-picker>
					</div>
				</div>
				<div id="echarts6" style="width: 100%; height: 280px;"></div>
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
	require('echarts/lib/component/datazoom')
	export default {
		name: 'homepage',
		data() {
			return {
				userdata: {},
				websocket: null,
				e_buy: 0,
				e_sell: 0,
				x_buy: 0,
				x_sell: 0,
				e_data: [],
				myChart6: null,
				list: [],
				value: 'EURUSD',
				options: [{
					value: 'EURUSD',
					label: 'EURUSD'
				}, {
					value: 'XAUUSD',
					label: 'XAUUSD'
				}],
				value5: '',
				pickerOptions2: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				start: '',
				end: ''
			}
		},
		computed: {
			user() {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return {
						department: 'user'
					}
				}
			}
		},
    filters: {
    	eurusdPrice (value) {
    		return parseFloat(value).toFixed(5).toString().substr(0,6);
    	},
      eurusdPriceSup (value) {
        return parseFloat(value).toFixed(5).toString().substr(6,1);
      },
      xauusdPrice (value) {
      	return parseFloat(value).toFixed(2).toString().substr(0,6);
      },
      xauusdPriceSup (value) {
      	return parseFloat(value).toFixed(2).toString().substr(6,1);
      },
    },
		methods: {
			formatDateTime(inputTime) {
				var date = new Date(inputTime);
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = m < 10 ? ('0' + m) : m;
				var d = date.getDate();
				d = d < 10 ? ('0' + d) : d;
				var h = date.getHours();
				h = h < 10 ? ('0' + h) : h;
				var minute = date.getMinutes();
				var second = date.getSeconds();
				minute = minute < 10 ? ('0' + minute) : minute;
				second = second < 10 ? ('0' + second) : second;
				return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
			},
			dateChange(date) {
				// console.log(date)
				this.start = this.formatDateTime(date[0]);
				this.end = this.formatDateTime(date[1]);
				this.ChangeList();
			},
			ChangeList(val) {
				let _this = this;
        
        _this.myChart6.showLoading({
          text: '加载中...',
          color: '#26c6da',
          textColor: '#000',
          maskColor: 'rgba(255, 255, 255, 0.8)',
          zlevel: 0
        })
        
				let datas = {
					"type": val,
					"startTime": _this.start,
					"endTime": _this.end
				}
				_this.$until.superPost("/api/tlb-mt-price-history/findList", datas, function(res) {
          _this.myChart6.hideLoading();
          
					// console.log(res)
					_this.randomData(res.data)
				})
			},
			randomData(datas) {
				var data = [];
				let a = {};

				datas.forEach((item) => {
					a = {};
					a.name = item.time;
					a.value = [item.time, item.spread];
					data.push(a)
				})
        
        this.list = data;
				this.myChart6.setOption({
					series: [{
						data: data
					}]
				});

			},
			pushList(item) {
				let _this = this;
				_this.list.push(item);

        _this.myChart6.setOption({
          series: [{
            data: _this.list
          }]
        });
        
			},
			getSocket() {
				let _this = this;
				if ('WebSocket' in window) {
					_this.websocket = new WebSocket("ws://47.52.199.109:8081/crm/webSocket");
				} else {
					alert('Not support websocket');
					return;
				}

				_this.websocket.onmessage = function(event) {

					let list = $.parseJSON(event.data);
					// console.log(list)
          let xau = {};
          let eur = {};
					list.forEach((item) => {
						if (item.symbol == 'XAUUSD') {
							_this.x_buy = item.buy;
							_this.x_sell = item.sell;
              
              xau = {};
              xau.name = item.time;
              xau.value = [item.time, Math.round((item.buy-item.sell)*100)];
              
						} else if (item.symbol == 'EURUSD') {
							_this.e_buy = item.buy;
							_this.e_sell = item.sell;
              
              eur = {};
              eur.name = item.time;
              eur.value = [item.time, Math.round((item.buy-item.sell)*100000)];
						}
					})
          
//           if (_this.value == 'XAUUSD' ) {
//             _this.pushList(xau);
//             
//           }else {
//             _this.pushList(eur);
// 
//           }
          
				}

				window.onbeforeunload = function() { //关闭
					_this.websocket.close();
				}
			},
			initEchart() { //初始化圖標
				let myChart1 = echarts.init(document.getElementById('echarts1'));
				let myChart2 = echarts.init(document.getElementById('echarts2'));
				let myChart3 = echarts.init(document.getElementById('echarts3'));
				let myChart4 = echarts.init(document.getElementById('echarts4'));
				// let myChart5 = echarts.init(document.getElementById('echarts5'));
				this.myChart6 = echarts.init(document.getElementById('echarts6'));
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
						data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00',
							'11:00', '12:00', '00:00'
						],
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
						data: [
              {
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
					series: [
            {
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
					series: [
            {
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
						data: [
              {
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
						data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00',
							'11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00',
							'23:00', '24:00', '00:00'
						],
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
					series: [
            {
						name: '交易量',
						data: [0, 1, 3, 1, 2, 0, 2, 3, 1, 3, 2, 3, 1, 2, 3, 0, 2, 2, 1, 0, 2, 1, 1, 3, 1, 0],
						type: 'line',
						smooth: true,
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
				let option6 = {
					tooltip: {
						trigger: 'axis',
						formatter: function(params) {
							params = params[0];
							return params.name + '<br/>' + params.value[1];
						},
						axisPointer: {
							animation: false
						}
					},
					grid: {
						top: 10,
						right: 30,
						left: 30,
						bottom: 50
					},
					dataZoom: [
            {
						type: 'inside',
						throttle: 50
					}],
					xAxis: {
						type: 'time',
						splitLine: {
							show: false
						}
					},
					yAxis: {
						type: 'value',
						boundaryGap: [0, '100%'],
						splitLine: {
							show: false
						}
					},
					series: [
            {
						name: '模拟数据',
						type: 'line',
						showSymbol: false,
						hoverAnimation: false,
						data: [],
						lineStyle: {
							color: '#26c6da'
						}
					}]
				}
				myChart1.setOption(option1);
				myChart2.setOption(option2);
				myChart3.setOption(option3);
				myChart4.setOption(option4);
				// myChart5.setOption(option5);
				this.myChart6.setOption(option6);
			},

			initPage() {
				let _this = this;
				_this.$until.superGet('/api/tlb-trade/getTlbData', function(res) {
					//					console.log(res)
					if (res.data == null) {
						let department = _this.user.department;

						if (department == "admin") {
							_this.$router.push('/')
						} else if (department == "service") {
							_this.$router.push('/task')
						} else if (department == "account") {
							_this.$router.push('/tasked')
						} else {
							_this.$router.push('/manage')
						}
					} else {
						_this.userdata = res.data;
					}
				})
			}
		},
		mounted() {
      
      let token = localStorage.getItem("id_token");
      if (token == null) {
        this.$router.replace({
            path: '/login',
        })
        return;
      }
      
			this.initPage();
			this.initEchart();
			this.getSocket();
			this.ChangeList(this.value);
		},
		beforeDestroy() {
			if (this.websocket) {
        this.websocket.close()
      }
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

	.bottom_item {
		width: 50%;
		text-align: center;
		padding: 20px 0;
	}

	.bottom_item span {
		font-size: 50px;
		color: #666;
		line-height: 80px;
		margin: 0 30px;
	}

	.bottom_item span.buy {
		color: #3269ff;
	}

	.bottom_item span.sell {
		color: #f45940;
	}
  .bottom_item img {
    width: 30px;
    position: relative;
    top: -13px;
  }
	.bottom_item h3 {
		font-size: 35px;
		color: #666;
		margin-bottom: 10px;
	}

	.bottom_item p {
		font-size: 25px;
		color: #666;
		line-height: 60px;
	}

	.chart_box {
		padding: 0 40px;
	}

	/* .chart_box .el-select {
        padding: 3px 10px;
    } */
</style>
