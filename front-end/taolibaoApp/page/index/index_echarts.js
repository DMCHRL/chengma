
var myChart = echarts.init(document.getElementById('main'));
var upColor = '#ff6850';
var downColor = '#34ca9e';

var index_time = 30;//选择K线时间
var page_size = 100;//k线条数

var Xdata = [];
var Kdata = [];



//x轴 时间
//k线数据 [open,colse(buy),low,height]

var option = {
	backgroundColor: '#fff',
	color: ['#ffad27', '#23a0ff', '#fb3fd2'],
	legend: {
		data: [{
				name: 'MA5',
				icon: 'circle',
				textStyle: {
					color: '#ffad27'
				}
			},
			{
				name: 'MA10',
				icon: 'circle',
				textStyle: {
					color: '#23a0ff'
				}
			},
			{
				name: 'MA20',
				icon: 'circle',
				textStyle: {
					color: '#fb3fd2'
				}
			}
		],
		left: 2,
		top: 10,
		inactiveColor: '#777',
		itemWidth: 8,
		itemHeight: 8,
		textStyle: {
			fontSize: 10,
			color: '#999'
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			animation: false,
			type: 'cross',
			lineStyle: {
				color: '#376df4',
				width: 1,
				opacity: 1
			}
		},
		position: [-10000, -10000]
	},
	xAxis: {
		type: 'category',
		data: [],
		axisLine: {
			lineStyle: {
				color: '#8392A5'
			}
		}
	},
	yAxis: {
		show: false,
		position: 'right',
		scale: true,
		axisLine: {
			lineStyle: {
				color: '#8392A5'
			}
		},
		splitLine: {
			show: false
		}
	},
	grid: {
		top: 30,
		bottom: 40,
		left: 10,
		right: 10
	},
	dataZoom: [{
		type: 'inside',
		start: 80
	}],
	animation: false,
	series: [{
			type: 'candlestick',
			name: '日K',
			data: [],
			itemStyle: {
				normal: {
					color: '#35b793',
					color0: '#ff5156',
					borderColor: '#35b793',
					borderColor0: '#ff5156'
				}
			},
			markLine: {
                
            }
		},
		{
			name: 'MA5',
			type: 'line',
			data: [],
			smooth: true,
			showSymbol: false,
			lineStyle: {
				normal: {
					width: 1,
					color: '#ffad27'
				},
			},
			symbolSize: 0
		},
		{
			name: 'MA10',
			type: 'line',
			data: [],
			smooth: true,
			showSymbol: false,
			lineStyle: {
				normal: {
					width: 1
				}
			},
			symbolSize: 0
		},
		{
			name: 'MA20',
			type: 'line',
			data: [],
			smooth: true,
			showSymbol: false,
			lineStyle: {
				normal: {
					width: 1
				}
			},
			symbolSize: 0
		}
	]
};

myChart.setOption(option);

function calculateMA(dayCount, datas) {
	var result = [];
	for(var i = 0, len = datas.length; i < len; i++) {
		if(i < dayCount) {
			result.push('-');
			continue;
		}
		var sum = 0;
		for(var j = 0; j < dayCount; j++) {
			sum += datas[i - j][1];
		}
		result.push(sum / dayCount);
	}
	return result;
}


function AssemblyData (ajaxData) {
	Xdata = ajaxData.map(function(item) {
		if(index_time >= 1440) {
			return item['time'].substring(0, 10);
		} else {
			return item['time'].substr(11, 8);
		}
	});
	
	Kdata = ajaxData.map(function(item) {
		return [+item['open'], +item['close'], +item['low'], +item['high']];
	});

	updatelast({});

}

var pusharr = [];

function updatelast(data) {//更新K线数据

	if(data.buy != undefined) {
//		console.log(data)
		var obj = {};
		if(index_time == 30) {
			obj.open = data.open30;
			obj.close = data.buy;
			obj.high = data.high30;
			obj.low = data.low30;
		} else if(index_time == 60) {
			obj.open = data.open60;
			obj.close = data.buy;
			obj.high = data.high60;
			obj.low = data.low60;
		} else {
			obj.open = data.open1440;
			obj.close = data.buy;
			obj.high = data.high1440;
			obj.low = data.low1440;
		}
		
		pusharr = [parseFloat(obj.open),parseFloat(obj.close),parseFloat(obj.low),parseFloat(obj.high)];
		//每次截取100条加一条即时数据
		Kdata = Kdata.slice(0,page_size).concat([pusharr]);
		Xdata = Xdata.slice(0,page_size).concat(['-']);
//		console.log(Xdata)
//		console.log(Kdata)
	}
	
	myChart.setOption({
		xAxis: {
			data: Xdata,
		},
		series: [{
				data: Kdata,
			},
			{
				data: calculateMA(5, Kdata)
			},
			{
				data: calculateMA(10, Kdata)
			},
			{
				data: calculateMA(20, Kdata)
			}
		]
	})

}

function updatak(time) {
	myChart.showLoading({
		text: '加載中...',
		color: '#ff5655',
		textColor: '#666',
		maskColor: 'rgba(255, 255, 255, 0.5)',
		zlevel: 9
	});
	
	isbool = false;
	index_time = time;
	
	var sym = getSymbol();
	
	var datas = {
		"page_number":1,
	    "page_size":page_size,
	    "formParams":{
	         "cycle": time,  // 30 ： 半个小时， 60: 一个小时， 1440， 一天
	         "symbol": sym
	    }
	}
	
	superPost("/api/tlb-history-k/pageList",datas,function (res) {
//		console.log(res)
		myChart.hideLoading();
		if (res.statusCode == "0000") {
			AssemblyData(res.data.list.reverse());
		}
	})
	
}

$(function() {
	updatak(30);
		
	setInterval(function() {
		updatak(index_time)
	}, 60000*15);
	

})