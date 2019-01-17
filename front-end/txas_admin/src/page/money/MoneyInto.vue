<template>
    <div class="content_box">

        <!--绑定银行卡弹窗-->
        <el-dialog custom-class="" :visible.sync="dialogVisible" width="20%">
            <add-card @close="dialogVisible=false;" :banklist="banklist"></add-card>
        </el-dialog>


        <div class="titling clearfix">
            <span>账户充值</span>
            <img src="../../assets/img/timg.jpg" />
            <img src="../../assets/img/w_p.jpg" />
            <img src="../../assets/img/z_p.png" />
        </div>
        <div class="message_box">
            <div class="input_box">
                <span>充值账户</span>
                <!--<select name="" v-model="account">
					<option  v-for="item in acclist" :value="item.account">{{item.account}}</option>
				</select>-->
                <el-autocomplete class="inline-input" v-model="account" :fetch-suggestions="querySearch" placeholder="请输入账户"
                    @select="handleSelect" clearable>
                    <template slot-scope="{ item }">
                        <div class="name">{{ item.account }} &nbsp;&nbsp;&nbsp;<span class="addr">{{ item.accountName }}</span></div>
                    </template>
                </el-autocomplete>
            </div>
            <div class="input_box">
                <span>账户姓名</span>
                <el-input class="inline-input" type="text" placeholder="请输入账户姓名" v-model="accName"></el-input>
            </div>
            <!--<div class="input_box">
				<span>到账货币</span>
				<input type="text"  value="USD（美元）" disabled/>
			</div>-->
            <div class="input_box">
                <span class=" ">充值金额（美元）</span>
                <!--<input type="number" id="2" placeholder="请输入美金金额" v-model="usd" />-->
                <el-input class="inline-input" type="number" placeholder="请输入美金金额" v-model="usd" @change="usdChange"></el-input>
                <!--<p>最低存款金额1000美金  最高存款金额25000美金</p>-->
            </div>
            <div class="input_box">
                <span class=" ">支付平台</span>
                <el-radio-group v-model="pay_type">
                    <template v-for="item in pays">
            
                        <el-radio-button :label="item.value">{{item.label}}</el-radio-button>
            
                    </template>
            
                </el-radio-group>
            </div>
            <div class="input_box" v-if="pay_type =='1' || pay_type =='3'">
                <span>支付金额（人民币）</span>
                <el-input class="inline-input" type="text" placeholder="将根据充值金额自动计算" disabled v-model="cny"></el-input>
            </div>
            <div class="input_box" v-if="pay_type =='5'">
                <span>泰达币（USDT）</span>
                <el-input class="inline-input" type="text" placeholder="将根据充值金额自动计算" disabled v-model="usd"></el-input>
            </div>
            
            <div v-show="pay_type == '2'">
                <div class="input_box">
                    <button @click="dialogVisible = true">+绑定银行卡</button>
                </div>
                <div class="input_box">
                    <span>开户银行</span>
                    <!--<select name="" v-model="bankname">
						<option  v-for="item in banklist" :value="item.value">{{item.label}}</option>
					</select>-->
                    <el-select v-model="bankname" placeholder="请选择">
                        <el-option v-for="item in banklist" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>

                </div>
                <div class="input_box">
                    <span>银行卡号</span>
                    <!--<input type="number"  placeholder="" v-model="banknum"/>-->
                    <el-input type="number" placeholder="" v-model="banknum" />
                    </el-input>
                </div>
            </div>
            <div class="input_box">
                <p v-if="pay_type=='1'">* 注：购汇到账时间为工作时间一个小时内，请您耐心等候。</p>
                <p v-if="pay_type=='3'">* 注：支付成功后将支付凭证截图给在线客服，到账时间为工作日两个小时内，请您耐心等候。</p>
                <p v-if="pay_type=='4'">* 注：该充值货币为美元， 汇款成功后将支付凭证截图给在线客服，到账时间为工作日两个小时内，请您耐心等候。</p>
                <p v-if="pay_type=='5'">* 注：无需审核，2小时内到账。</p>
            </div>
            <!--<div v-show = "pay_type == '3' || pay_type == '4'">
				<div class="input_box clearfix">
					
					<div class="pull-left">
						<img :src="storeQrcode"/>
					</div>
					
					<div class="pull-left trips_box">
						<h4>支付宝/微信支付流程：</h4>
						<p>1，输入美金换算人民币金额</p>
						<p>2，支付宝/微信扫码支付(人民币)，截图转账记录</p>
						<p>3，联系客服QQ:2352406183，发送截图。</p>
					</div>
				</div>
			</div>-->


            <div class="btn_box">
                <button @click="Confirm">确认</button>
            </div>
        </div>
    </div>
</template>

<script>
    import AddCard from "@/components/dialog/AddCard"
    export default {
        data() {
            return {
                pay_type: null, //付款方式
                usd: '',
                account: '',
                acclist: [],
                accName: '',
                rate: 7,
                banknum: '',
                bankname: '104100000004',
                banklist: [],
                beizhu: '',
                orderid: '',
                dialogVisible: false,
                storeQrcode: '',
                pays: []
            }
        },
        components: {
            AddCard
        },
        computed: {
            cny() {
                let usd = parseFloat(this.usd);
                if (usd) {
                    let rate = parseFloat(this.rate);
                    return usd * rate;
                } else {
                    return ''
                }
            },
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
        watch: {

        },
        methods: {
            usdChange(val) {
                if (val > 7000) {
                    this.$message({
                        message: "充值金额最高为7000美元",
                        type: 'warning'
                    });
                    this.usd = 7000;
                }
            },
            querySearch(queryString, cb) {
                var acclist = this.acclist;
                var results = queryString ? acclist.filter(this.createFilter(queryString)) : acclist;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                    return (restaurant.account.indexOf(queryString) === 0);
                };
            },
            handleSelect(item) {
                this.account = item.account;
                this.accName = item.accountName;
            },
           
            sendCode(val) {
                let _this = this;
                let datas = {
                    "payOrderId": _this.orderid,
                    "smscode": val
                };
                _this.$until.superPost("/api/xxpay/pay_confirm", datas, function(res) {
                    //console.log(res)
                })
            },
            QuitPay() { //快捷支付
                let _this = this;

                let datas = {
                    "account": _this.account,
                    "amount": _this.cny * 100,
                    "payType": "fastPay",
                    "body": "姓名：" + _this.accName + " ,美金金额： " + _this.usd + ",备注：" + _this.beizhu,
                    "currency": "cny",
                    "banksn": _this.banknum,
                    "bankId": _this.bankname,
                    "usd": _this.usd
                };
                _this.$until.superPost("/api/xxpay/createOrder", datas, function(res) {
                    if (res.statusCode == '0000') {
                        if (res.data.code == '0000') {
                            _this.$prompt('请输入短信验证码', '', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',

                            }).then(({
                                value
                            }) => {

                                _this.sendCode(value);

                            }).catch(() => { //取消
                            });

                        } else {
                            _this.$message({
                                message: res.data.msg,
                                type: 'warning'
                            });
                        }
                    } else {
                        _this.$message({
                            message: "服务升级中...",
                            type: 'warning'
                        });
                    }
                })

            },
            
            CreateUsdtOrder () {
                let _this = this;
                let datas = {
                    faceValue: _this.usd,//	是	string	usdt值
                    account: _this.account//	是	string	交易账号
                };
                _this.$until.superPost("/api/usdtOrder/createOrder", datas, function(res) {
                    console.log(res)
                    if (res.statusCode == '0000') {
                        location.href = res.data;
                        // window.open(res.data)
//                         var tempwindow=window.open('_blank');
//                         tempwindow.location=res.data;
                        
                    } else {
                        _this.$message({
                            message: res.data.msg,
                            type: 'warning'
                        });
                    }
                })
            },
            Confirm() { //确认支付
                let _this = this;

                if (_this.account == '') {
                    _this.$message({
                        message: "账户不能为空",
                        type: 'warning'
                    });
                    return;
                }
                if (_this.acc == 0.00) {
                    _this.$message({
                        message: "系统升级，请稍后再试",
                        type: 'warning'
                    });
                    return;
                }

                if (_this.cny == '') {
                    _this.$message({
                        message: "金额不能为空",
                        type: 'warning'
                    });
                    return;
                }

                if (_this.pay_type == null) {
                    _this.$message({
                        message: "请选择支付平台",
                        type: 'warning'
                    });
                    return;
                }

                if (_this.pay_type == '1') {
                    if (_this.usd < 100 || _this.usd > 7000) {
                        _this.$message({
                            message: "充值金额最小100美元，最大7000美元",
                            type: 'warning'
                        });
                        return;
                    }
                    _this.$router.push({
                        path: '/unionpay?cny=' + _this.cny + '&usd=' + _this.usd + '&account=' + _this.account
                    })

                } else if (_this.pay_type == '2') {
                    _this.QuitPay();
                } else if (_this.pay_type == '4') {
                    _this.$router.push({
                        path: '/usdpay?cny=' + _this.cny + '&usd=' + _this.usd + '&account=' + _this.account
                    })
                } else if (_this.pay_type == '3') {

                    if (_this.usd < 100 || _this.usd > 1000) {
                        _this.$message({
                            message: "充值金额最小100美元，最大1000美元",
                            type: 'warning'
                        });
                        return;
                    }

                    _this.$router.push({
                        path: '/qrpay?cny=' + _this.cny + '&usd=' + _this.usd + '&account=' + _this.account
                    })
                } if (_this.pay_type == '5') {
                    _this.CreateUsdtOrder();
                } else {

                }
            },
            getRate() { //获取汇率
                let _this = this;
                _this.$until.superGet("/api/tlb-rate/realTimeRate", function(res) {
                    if (res.statusCode == "0000") {
                        var data = JSON.parse(res.data);
                        _this.rate = data.result[0].result;
                    }
                })
            },
            pushAccount() {
                let _this = this;
                let initlist = [];
                let acclist = [];

                if (_this.user.department == "admin" || _this.user.department == "service") {
                    let datas = {
                        "page_number": 1, //页码
                        "page_size": 10000, // 每页条数
                        "formParams": {}
                    };
                    let url = "/api/tlb-account/pageList";

                    _this.$until.superPost(url, datas, function(res) {
                        if (res.statusCode == "0000") {
                            initlist = res.data.list;
                            initlist.forEach(function(item) {
                                acclist.push({
                                    "account": item.account,
                                    "accountName": item.accountName
                                })
                            })
                            _this.acclist = acclist;
                        }
                    })
                } else {
                    let list = localStorage.getItem("accList");
                    if (!list) return;
                    list = JSON.parse(localStorage.getItem("accList"));
                    list.forEach(function(item) {
                        acclist.push({
                            "account": item.account,
                            "accountName": item.accountName
                        })
                    })
                    _this.acclist = acclist;
                }
            },
            //检查国际是否可用
            findWay() {
                let _this = this;
                _this.$until.superGet("/api/receivables_en/findNew", function(res) {
                    // console.log(res)
                    if (res.statusCode == "0000") {
                        if (res.data) {
                            _this.pays.push({
                                value: '4',
                                label: '国际汇款(USD)'
                            });
                        }
                    }
                })
            },
            //检查银联支付是否可用
            findNew() {
                let _this = this;
                _this.$until.superGet("/api/receivables/findNew", function(res) {
                    if (res.statusCode == "0000") {
                        if (res.data) {
                            _this.pays.push({
                                value: '1',
                                label: '银联支付(CNY)'
                            });
                        }
                    }
                })
            },
            //检查微信是否可用
            findNewImg() {
                let _this = this;
                _this.$until.superGet("/api/payment_img/findNewImg", function(res) {
                    // console.log(res)
                    if (res.statusCode == "0000") {
                        if (res.data) {
                            _this.pays.push({
                                value: '3',
                                label: '微信/支付宝(CNY)'
                            });
                        }
                    }
                })
            },
            //检查usdt是否可用
            hasUsdt() {
                let _this = this;
                _this.$until.superGet("/api/digital_currency/findNew", function(res) {
                    // console.log(res)
                    if (res.statusCode == "0000") {
                        if (res.data) {
                            _this.pays.push({
                                value: '5',
                                label: '数字货币(USDT)'
                            });
                        }
                    }
                })
            },
            
        },
        mounted() {
            this.pushAccount();
            
            this.findNew();
            this.findNewImg();
            this.findWay();
            this.hasUsdt();
        }
    }
</script>

<style scoped>
    .titling img {
        height: 50px;
        margin-left: 20px;
    }

    .message_box {
        padding: 20px 0;
    }

    .message_box .input_box {
        margin-bottom: 25px;
        position: relative;
    }

    .message_box .input_box span {
        display: inline-block;
        width: 12%;
        text-align: right;
        padding-right: 10px;
        color: #000;
    }

    .message_box .input_box span.import:before {
        content: '*  ';
        color: #F14B3B;
    }

    .message_box .input_box p {
        margin-left: 10%;
        font-size: 14px;
        line-height: 30px;
        color: #999999;
    }

    .message_box .input_box h4 {
        margin-left: 10%;
        font-size: 20px;
        line-height: 50px;
        color: #FF4A4A;
    }

    .message_box .input_box input,
    .message_box .input_box select {
        border: 1px solid #ccc;
        width: 20%;
        padding: 5px 10px;
        border-radius: 5px;
    }

    .message_box .input_box img {
        width: 160px;
        margin: 10px auto;
        margin-left: 164px;
    }

    .message_box .input_box p {
        color: #FF2222;
    }

    .btn_box {
        padding: 20px 0;
    }

    .btn_box button {
        background-color: #F14B3B;
        border-radius: 20px;
        padding: 8px 40px;
        color: #fff;
        font-size: 14px;
        margin-left: 10%;
    }

    .input_box .el-radio-button__orig-radio:checked+.el-radio-button__inner {
        background-image: url(../../assets/img/btn_1.png);
        background-position: right bottom;
        background-repeat: no-repeat;
    }

    .input_box button {
        background-color: #fff;
        margin-left: 200px;
        color: #FF3C3C;
    }

    .el-input {
        width: 250px;
    }

    .el-autocomplete {
        width: 250px;
    }

    .trips_box {
        text-align: ;
        width: 50%;
    }
</style>
