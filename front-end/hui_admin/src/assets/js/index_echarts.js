$(function () {
	
	var myChart1 = echarts.init(document.getElementById('echarts1'));
	option1 = {
		title: {
			text: '新增交易者',
			textStyle: {
				color: '#19183e',
				fontSize: 14
			}
		},
		grid: {
			top: '35px',
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
	        axisTick: {//坐标刻度
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
	        axisTick: {//坐标刻度
	        	show: false
	        },
//	        axisLabel: {
//	        	show: false
//	        },
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
	myChart1.setOption(option1);//
	
	var myChart2 = echarts.init(document.getElementById('echarts2'));
	option2 = {
		title: {
			text: '交易量',
			textStyle: {
				color: '#19183e',
				fontSize: 14
			}
		},
		grid: {
			top: '25px',
			bottom: '30px',
			left: '30px',
			right: '30px',
			borderColor: '#fff'
		},
	    xAxis: {
	        type: 'category',
	        data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00','07:00','08:00','09:00','10:00','11:00','12:00','00:00'],
	        nameTextStyle: {
	        	color: "#111",
	        	fontSize: '15px'
	        },
	        axisLine: {
	        	lineStyle: {
	        		color: '#ccc'
	        	}
	        },
	        axisTick: {//坐标刻度
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
	        axisTick: {//坐标刻度
	        	show: false
	        },
//	        axisLabel: {
//	        	show: false
//	        },
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
	myChart2.setOption(option2);//
	
	var myChart3 = echarts.init(document.getElementById('echarts3'));
	option3 = {
		title: {
			text: '出入金',
			textStyle: {
				color: '#19183e',
				fontSize: 14
			}
		},
		grid: {
			top: '25px',
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
	        axisTick: {//坐标刻度
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
	        axisTick: {//坐标刻度
	        	show: false
	        },
//	        axisLabel: {
//	        	show: false
//	        },
	        splitLine: {
	        	show: false
	        }
	    },
	    color: ['#37a0f4','#fd7c4a'],
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
		        data: [3,4,5,7,3,8,12,17],
		        type: 'line',
		        showSymbol: false,
		        lineStyle: {
		        	color: '#fd7c4a'
		        }
		    }
	    ],
	    animation: true
	};
	myChart3.setOption(option3);//
	
	var myChart4 = echarts.init(document.getElementById('echarts4'));
	option4 = {
		title: {
			text: '佣金',
			textStyle: {
				color: '#19183e',
				fontSize: 14
			}
		},
		grid: {
			top: '25px',
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
	        axisTick: {//坐标刻度
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
	        axisTick: {//坐标刻度
	        	show: false
	        },
//	        axisLabel: {
//	        	show: false
//	        },
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
	myChart4.setOption(option4);//
	
	
	var myChart5 = echarts.init(document.getElementById('echarts5'));
	option5 = {
		title: {
			text: '今日交易量',
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
	        data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00','24:00','00:00'],
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
	        axisTick: {//坐标刻度
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
	        axisTick: {//坐标刻度
	        	show: false
	        },
//	        axisLabel: {
//	        	show: false
//	        },
	        splitLine: {
	        	show: false
	        }
	    },
	    series: [{
	    	name: '交易量',
	        data: [0.0, 0.3, 0.6, 0.7, 0.9, 0.7, 0.8, 0.9, 0.8, 0.4, 0.6, 0.5, 0.4, 0.6, 1, 1.2, 0.3, 0.5, 0.6, 0.8, 0.9, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.3, 0.5, 0.6, 0.8, 0.9],
	        type: 'line',
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
	myChart5.setOption(option5);//
})
