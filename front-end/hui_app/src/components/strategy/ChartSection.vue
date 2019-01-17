<template>
	<div class="chart_sec">

		<swiper v-model="swiper_index" height="265px" :show-dots="false">
			<swiper-item>
				<div class="title_box">
					
						<span>累计收益率</span>
            <i>%</i>
					<!-- <button>2018</button> -->
				</div>
				<div class="trip_box" v-show="!data.length">
					<img src="../../assets/img/no_1.png"/>
					<p>暂无数据</p>
				</div>
       <div>
          <v-chart :data="data" :height="220" :width="lineWidth" v-show="data.length" ref="chart1">
            
            <!-- <v-tooltip show-x-value/>
            <v-area series-field="type" shape="smooth"/>
            <v-legend disabled />
            <v-line series-field="type" shape="smooth"/>
            <v-scale x type="timeCat" mask="YYYY/MM/DD" :tick-count="3" /> -->
            
              <!-- <v-scale x type="timeCat" mask="YYYY/MM/DD" :tick-count="3" />
              <v-scale y :tick-count="5"/>
              <v-tooltip show-crosshairs show-value-in-legend  />
              
              <v-area />
              <v-line series-field="inAmount"/>
              <v-point 
                :style="{
                  stroke: '#fff',
                  lineWidth: 1
                }" 
                shape="smooth" /> -->
                
            <v-scale x type="timeCat" mask="YYYY/MM/DD" :tick-count="3"/>
            <v-scale y :min="0"/>
            <v-area :colors="gradient" shape="smooth" />
            <v-line :colors="gradient" />
            <v-tooltip show-x-value/>
          </v-chart>
        </div>
			</swiper-item>

			<swiper-item>
				<div class="title_box">
					<span>交易品种</span>
					<i>百分比</i>
				</div>
				<div class="trip_box" v-show="!data2.length">
					<img src="../../assets/img/no_1.png"/>
					<p>暂无数据</p>
				</div>
				<v-chart :data="data2" :height="220" :width="330" v-show="data2.length" ref="chart2">
					<v-scale y :options="yOptions" />
					<v-tooltip disabled />
					<v-pie :radius="0.85" series-field="name" />
					<v-legend :options="legendOptions" />
				</v-chart>
			</swiper-item>
      
      <swiper-item>
      	<div class="title_box">
      		<span>资金量</span>
      		<i>$</i>
      	</div>
      	<div class="trip_box" v-show="!data3.length">
      		<img src="../../assets/img/no_1.png"/>
      		<p>暂无数据</p>
      	</div>
      	<v-chart :data="data3" :height="220" :width="lineWidth" v-show="data3.length" ref="chart3">
          
      		<!-- <v-scale x field="year" type="timeCat" mask="YY/MM/DD" :tick-count="5" />
          <v-scale y field="percent" :min="0" :max="0.5" :formatter="formatter" />
          <v-bar series-field="country" adjust="stack" />
          <v-tooltip show-value-in-legend /> -->
          
          <v-scale x field="year" type="timeCat" mask="YYYY/MM/DD" :tick-count="3"/>
          <v-scale y field="value" />
          <v-bar series-field="country" adjust="stack" />
          <v-tooltip show-x-value />
          
      	</v-chart>
      </swiper-item>

		</swiper>
		
		<div class="dot_box">
			<span :class="{active: swiper_index == 0}" @click="swiper_index = 0"></span>
			<span :class="{active: swiper_index == 1}" @click="swiper_index = 1"></span>
			<span :class="{active: swiper_index == 2}" @click="swiper_index = 2"></span>
		</div>
		
	</div>
</template>

<script>
	import { Swiper, SwiperItem, VChart, VLine, VArea, VTooltip, VScale, VAxis, VPoint, VPie, VLegend,VBar } from 'vux'
	let map = {
		'--': '40%',
		'--': '20%',
		'--': '18%',
		'--': '15%',
		'--': '5%',
		'--': '2%'
	}
	export default {
		components: {
			VChart,
			VLine,
			VArea,
			VTooltip,
			VScale,
			VAxis,
			VPoint,
			Swiper,
			SwiperItem,
			VPie,
			VLegend,
      VBar
		},
		data() {
			return {
				swiper_index: 0,
				// data: [{"date":"2010-01-10","type":"余额","value":99.9},{"date":"2010-01-10","type":"出金","value":96.6},{"date":"2010-01-10","type":"入金","value":96.6}],
				data: [{ time: '2016-08-08 00:00:00', tem: 10 }],
        gradient: [
          [0, '#FE6384'],
          [1, '#FE6384']
        ],
				legendOptions: {
					position: 'right',
					itemFormatter(val) {
						// console.log(val)
						return val
					}
				},
				yOptions: {
					formatter(val) {
						return val * 100 + '%'
					}
				},
				map,
				data2: [
          {name: '--',percent: 0.4,a: '1'},
					{name: '--',percent: 0.2,a: '1'},
					{name: '--',percent: 0.18,a: '1'},
					{name: '--',percent: 0.15,a: '1'},
					{name: '--',percent: 0.05,a: '1'},
					{name: '--',percent: 0.02,a: '1'}
				],
        lineWidth: 400,
        formatter: function (val) {
          return (val * 100).toFixed(0) + '%'
        },
        data3: [{ country: 'Europe', year: '1750', value: 163, percent: 0.24511278195488723 },
        { country: 'Asia', year: '1750', value: 502, percent: 0.7548872180451128 },
        { country: 'Europe', year: '1800', value: 203, percent: 0.24224343675417662 },
        { country: 'Asia', year: '1800', value: 635, percent: 0.7577565632458234 },
        { country: 'Europe', year: '1850', value: 276, percent: 0.2543778801843318 },
        { country: 'Asia', year: '1850', value: 809, percent: 0.7456221198156682 },
        { country: 'Europe', year: '1900', value: 408, percent: 0.3011070110701107 }],
        gradient3: [
        	[0, '#FE6384'],
        	[1, '#ffdd57']
        ]
      }
		},
		computed: {
			account() {
				return this.$route.query.account;
			},
		},
		watch: {
// 			account () {
// 				this.getGrowthRate();
// 				this.getVarietyRatio();
// 			},
			swiper_index(val) {
        if (val == 0) {
        	this.$refs.chart1.rerender()
        }
        if (val == 1) {
        	this.$refs.chart2.rerender()
        }
				if (val == 2) {
          this.$refs.chart3.rerender()
        }
			}
		},
		methods: {
			computedRatio(list) {
				let maps = {};
				let datas = [];
				list.forEach((item) => {
					maps[item.symbol] = item.ratio + '%';
					datas.push({
						name: item.symbol,
						percent: item.ratio / 100,
						a: '1'
					})
				})
				this.map = maps;
				this.data2 = datas;
			},
			getVarietyRatio() {
				let _this = this;
				_this.$fetch("/api/hpp_strategy_trade/findTradeByRatio/" + _this.account).then((res) => {
					if(res.data.list) {
						let list = res.data.list;
							_this.computedRatio(list);
					}

				})
			},
			getGrowthRate() {
				let _this = this;
				let datas = {
					account: _this.account
				}
				_this.$post("/api/hpp_strategy_bal_history/findGainRate", datas).then((res) => {
          // console.log(res)
					if(res.data) {
						let list = res.data.list;
						let eList = [];
						list.forEach((item) => {
							if (item.gainRate) {
                eList.push({
                	time: item.days,
                	tem: item.gainRate
                });
              }
              
						})
            
						// _this.data = (eList.concat(inList)).concat(outList);
						_this.data = eList;
					}
				})
			},
      getFunds() {
      	let _this = this;
      	let datas = {
      		account: _this.account
      	}
      	_this.$post("/api/hpp_strategy_bal_history/findByAccount", datas).then((res) => {
      		// console.log(res)
      		if(res.data) {
      			let list = res.data.list;
      			let eList = [];
      			let inList = [];
      			let outList = [];
      			list.forEach((item) => {
      				if (item.equery) {
                eList.push({
                  year: item.days,
                  value: item.equery,
                  country: '余额',
                  percent: item.equery/(item.equery+item.inAmount+(-item.outAmount))
                });
      				}
//       				if (item.inAmount) {
//       					inList.push({
//       						year: item.days,
//       						value: item.inAmount,
//       						country: '入金',
//                   percent: item.inAmount/(item.equery+item.inAmount+(-item.outAmount))
//       					});
//       				}
      				if (item.outAmount) {
      					outList.push({
      						year: item.days,
      						value: item.outAmount,
      						country: '出金',
                  percent: item.outAmount/(item.equery+item.inAmount+(-item.outAmount))
      					});
      				}
      				
      				
      			})
      			
      			_this.data3 = (eList.concat(inList)).concat(outList);
      		}
      	})
      }
		},
		mounted () {
      let w = document.documentElement.clientWidth || document.body.clientWidth;
      this.lineWidth = w-15;
      
			this.getGrowthRate();
			this.getVarietyRatio();
      this.getFunds();
		}
	}
</script>

<style scoped>
	.title_box {
		height: 45px;
		padding: 0 0.2rem;
		border-bottom: 1px solid #e6e6e6;
	}
	
	.title_box span {
		border-left: 0.08rem solid #e40b2f;
		padding-left: 0.2666rem;
		font-size: 0.3466rem;
		color: #333;
		line-height: 45px;
	}
	
	.title_box i {
		font-size: 0.25rem;
		color: #999;
		margin-left: 0.1333rem;
	}
	
	.title_box button {
		background-color: transparent;
		font-size: 0.2666rem;
		color: #666;
	}
	
	.chart_sec {
		margin: 0.2666rem;
		background-color: #fff;
		box-shadow: 0px 3px 4px #ededed;
		border-radius: 0.15rem;
		overflow: hidden;
	}
	
	.dot_box {
		text-align: center;
		padding-bottom: 0.2666rem;
	}
	.dot_box span {
		display: inline-block;
		width: 0.6rem;
		height: 0.1rem;
		background-color: #ededed;
		border-radius: 0.2666rem;
		margin: 0 0.2rem;
	}
	.dot_box span.active {
		background-color: #4b84d5;
	}
</style>