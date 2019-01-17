
var lineChart = echarts.init(document.getElementById('line_main'));
var pieChart = echarts.init(document.getElementById('pie_main'));

var lineXdata = [];
var lineData = [];

var option = {
	backgroundColor: '#fff',
	title: {
		text: '获利',
		top: 5,
		left: '4%',
		textStyle: {
			color: '#666',
			fontSize:12,
		}
	},
	tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0]-80, '10%'];
        }
    },
	grid: {
		top: 40,
		bottom: 30,
		left: 55,
		right: 10
	},
	animation: false,
    xAxis: {
    	axisTick: false,
        type: 'category',
        data: [],
        axisLine: {
        	lineStyle: {
        		color: '#999'
        	}
        },
        boundaryGap: false,
        splitLine: {
        	show: false
        }
    },
    yAxis: {
    	axisTick: false,
        type: 'value',
        axisLine: {
        	lineStyle: {
        		color: '#999'
        	}
        },
        splitLine: {
        	lineStyle: {
        		color: '#ededed',
        		type: 'dotted'
        	}
        }
    },
    series: [{
    	name:'$',
        data: [],
        type: 'line',
        lineStyle: {
//      	color: '#34bbff'
        	color: '#c0878d'
        },
        itemStyle: {
        	color: '#c0878d'
        },
        areaStyle: {
//      	color: '#91a7e4'
        	color: '#fbd1d2'
        },
//      smooth: true
    }]
};

var legendList = [];
var pieData = [];

var pieOption = {
	backgroundColor: '#fff',
	title: {
		text: '交易品种',
		left: '4%',
		textStyle: {
			color: '#666',
			fontSize:12,
		},
//		subtext: '副标'
	},
	color:['#f45940','#3269ff', '#61a0a8', '#d48265','#749f83','#6e7074', '#546570'],
    tooltip : {
    	show: false,
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    	show: false,
        orient: 'vertical',
        left: 'left',
        data: []
    },
    series : [
        {
        	selectedMode: 'single',
            name: '访问来源',
            type: 'pie',
            radius : '80%',
            center: ['50%', '55%'],
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            label: {
                normal: {
                    position: 'inner',
                    formatter: "{b} \n {c}手 \n ({d}%)"
                }
            },
        }
    ]
};

lineChart.setOption(option);
pieChart.setOption(pieOption);


function getLineData (month) {
	if(!month){
		month = '';
		$('#btn_text').text('全部');
	}else {
		$('#btn_text').text(month);
	}
	
	
	var accObj = getAccount();
	var datas = {
		"account": accObj.account,
    	"month": month
	}
	
	lineChart.showLoading({
		text: '加載中...',
		color: '#ff5655',
		textColor: '#666',
		maskColor: 'rgba(255, 255, 255, 0.5)',
		zlevel: 9
	});
	
	superPost("/api/tlb-trade/getTlbTradeProfit",datas,function (res) {
//		console.log(res)
		if (res.statusCode == "0000") {
			var list = res.data;
			
			lineXdata = [];
			lineData = [];
			
			if (list == null) {
				mui.toast('无数据',{ duration:'long', type:'div' });
				
			}else {
				list.forEach(item => {
					lineXdata.push(item.date);
					lineData.push(item.gainMoney);
				})
			}
			
			lineChart.setOption({
				xAxis: {
					data: lineXdata,
				},
				series: [{
					data: lineData,
				}]
			});
			
		}
		lineChart.hideLoading();
		
	})
}

function getPieData () {
	var accObj = getAccount();
	
	pieChart.showLoading({
		text: '加載中...',
		color: '#ff5655',
		textColor: '#666',
		maskColor: 'rgba(255, 255, 255, 0.5)',
		zlevel: 9
	});
	
	superGet("/api/tlb-trade/getTlbTradeSymbol/" + accObj.account,function (res) {
//		console.log(res)
		if (res.statusCode == "0000") {
			var list = res.data;
			
			legendList = [];
			pieData = [];
	
			if (list == null) {
				mui.toast('无数据',{ duration:'long', type:'div' });
				
			}else {
				list.forEach(item => {
					legendList.push(item.symbol);
					pieData.push({
						value: item.lots,
						name: item.symbol
					});
				})
			}
			
			pieChart.setOption({
				legend: {
					data: legendList,
				},
				series: [{
					data: pieData,
				}]
			});
		}
		pieChart.hideLoading();
	})
}
