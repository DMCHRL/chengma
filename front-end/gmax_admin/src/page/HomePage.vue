<template>
    <div class="content_box">
        <div class="row">

            <div class="col-md-3">
                <div class="item_box">
                    <div class="_top clearfix two">
                        <div class="pull-left">
                            <p>盈利总金额</p>
                            <p>$<span>{{someData.totalGain}}</span></p>

                        </div>
                        <div class="pull-right">
                            <p>佣金总金额</p>
                            <p><span>{{someData.totalCommission}}</span></p>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="col-md-3">
                <div class="item_box">
                    <div class="_top clearfix third">
                        <div class="pull-left">
                            <p>入金总额</p>
                            <p>$<span>{{someData.totalIn}}</span></p>
                        </div>
                        <div class="pull-right">
                            <p>出金总额</p>
                            <p>$<span>{{someData.totalOut}}</span></p>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="col-md-3">
                <div class="item_box">
                    <div class="_top clearfix four">
                        <div class="pull-left">
                            <p>交易总手数</p>
                            <p><span>{{someData.totalLots}}</span>手</p>
                        </div>
                        <div class="pull-right">
                            <p>交易者总数</p>
                            <p><span>{{someData.totalAccount}}</span>人</p>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="col-md-3">
                <div class="item_box">
                    <div class="_top clearfix one">
                        <div class="pull-left">
                            <p>总胜率</p>
                            <p><span>{{someData.totalSuccessRate}}</span>%</p>
                        </div>
                        <div class="pull-right">
                            <p>人均交易单数</p>
                            <p><span>{{someData.avgTradeCount}}</span>单</p>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    // 引入基本模板
//     let echarts = require('echarts/lib/echarts')
//     // 引入折线组件
//     require('echarts/lib/chart/line')
//     // 引入提示框和title组件
//     require('echarts/lib/component/tooltip')
//     require('echarts/lib/component/title')
//     require('echarts/lib/component/datazoom')
    export default {
        name: 'homepage',
        data() {
            return {
                someData: {},
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
            eurusdPrice(value) {
                return parseFloat(value).toFixed(5).toString().substr(0, 6);
            },
            eurusdPriceSup(value) {
                return parseFloat(value).toFixed(5).toString().substr(6, 1);
            },
            xauusdPrice(value) {
                return parseFloat(value).toFixed(2).toString().substr(0, 6);
            },
            xauusdPriceSup(value) {
                return parseFloat(value).toFixed(2).toString().substr(6, 1);
            },
        },
        methods: {
            
            initPage() {
                let _this = this;
                _this.fetch('/api/tlb-trade/getTlbData', function(res) {
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
                        _this.someData = res.data;
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
        },
        beforeDestroy() {
            
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .content_box {
        /* background-color: transparent; */
    }

    .content_box .row {
        margin-left: -25px;
        margin-top: -25px;
    }

    .content_box .col-md-3 {
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
