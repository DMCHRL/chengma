<template>
    <div>
        <head-top></head-top>
        <el-row style="margin-top: 20px;">
            <el-col :span="12" :offset="4">
                <el-form :model="formData" :rules="rules" ref="formData" label-width="110px" class="demo-formData">
                    <div class="sec_box">
                        <p>基本信息</p>

                        <el-form-item label="基金名称" prop="name">
                            <el-input v-model="formData.name"></el-input>
                        </el-form-item>
                        <el-form-item label="产品描述" prop="remark">
                            <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="formData.remark">
                            </el-input>
                            <!-- <el-input v-model="formData.remark"></el-input> -->
                        </el-form-item>
                        
                        <el-form-item label="投资策略" prop="strategy">
                            <el-input v-model="formData.strategy"></el-input>
                        </el-form-item>
                        <el-form-item label="	买卖方向" prop="direction">
                            <el-input v-model="formData.direction"></el-input>
                        </el-form-item>
                        <el-form-item label="	投资范围" prop="range">
                            <el-input v-model="formData.range"></el-input>
                        </el-form-item>
                        <el-form-item label="发起人" prop="mobile">
                            <el-select v-model="formData.originatorId" placeholder="请选择">
                                <el-option v-for="item in originators" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        
                        <el-form-item label="资金管理人" prop="range">
                            <el-select v-model="formData.managerId" placeholder="请选择">
                                <el-option v-for="item in managers" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </div>

                    <div class="sec_box">
                        <p>交易与入伙</p>

                        <el-form-item label="交易账号" prop="account">
                            <el-input v-model.number="formData.account"></el-input>
                        </el-form-item>
                        <el-form-item label="交易平台" prop="remark">
                            <el-input v-model="formData.platform"></el-input>
                        </el-form-item>
                        
                        <el-form-item label="金额量" style="white-space: nowrap;">
                            <span>目标募集金额</span>
                            <el-input-number v-model="formData.targetFund" :min="0" :step="100000"></el-input-number>
                            <span>最小入伙金额 </span>
                            <el-input-number v-model="formData.minFund" :min="0" :step="10000"></el-input-number>
                        </el-form-item>

                        <el-form-item label="合伙人最大风险" prop="risk">
                            <el-input-number v-model="formData.risk" :min="0" :step="0.01"></el-input-number>
                        </el-form-item>
                        <el-form-item label="合伙人最小等级" prop="minLevel">
                            <el-select v-model="formData.minLevel" placeholder="请选择">
                                <el-option v-for="item in levelList" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                            <!-- <el-input v-model="formData.minLevel"></el-input> -->
                        </el-form-item>

                        <el-form-item label="操作时间" style="white-space: nowrap;">

                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="formData.operationAt" type="datetime"
                                placeholder="选择开始日期时间" default-time="12:00:00">
                            </el-date-picker>
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="formData.endAt" type="datetime"
                                placeholder="选择结束日期时间" default-time="12:00:00">
                            </el-date-picker>
                        </el-form-item>

                        <el-form-item label="操作细节" style="white-space: nowrap;">
                            <span>募集成功提前进入操作期</span>
                            <el-switch on-text="" off-text="" v-model="formData.successFlag"></el-switch>
                            <span>合伙人的收益达预期提前清盘 </span>
                            <el-switch on-text="" off-text="" v-model="formData.partnerFlag"></el-switch>
                        </el-form-item>
                    </div>
                    <div class="sec_box">
                        <p>收益及分配</p>
                        <el-form-item label="预期总收益" style="white-space: nowrap;">
                            <span>基本收益</span>
                            <el-input-number v-model="formData.yearProfit1" :min="0" :step="0.01"></el-input-number>
                            <span>浮动收益</span>
                            <el-input-number v-model="formData.yearProfit2" :min="0" :step="0.01"></el-input-number>
                        </el-form-item>
                        
                        

                        <el-form-item label="风险及预期收益">
                            <el-select v-model="activityValue" @change="centerDialogVisible = true" placeholder="请选择">
                                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-table :data="formData.expectRiskProfitDTOList" style="min-width: 600px;margin-bottom: 20px;" align="cneter"
                            :row-class-name="tableRowClassName">
                            <el-table-column prop="role" label="身份" align="cneter" width="120">
                            </el-table-column>
                            <el-table-column prop="risk" label="风险" align="cneter" width="120">
                            </el-table-column>
                            <el-table-column prop="preProfit" label="预期收益" align="cneter">
                            </el-table-column>
                            <el-table-column label="操作" width="120">
                                <template slot-scope="scope">
                                    <el-button size="small" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                        <el-dialog title="风险及预期收益" :visible.sync="centerDialogVisible" center @open="risk = null;preProfit = null">

                            <el-form-item label="风险" prop="risk">
                                <el-input v-model="risk"></el-input>
                            </el-form-item>
                            <el-form-item label="预期收益" prop="preProfit">
                                <el-input v-model="preProfit"></el-input>
                            </el-form-item>

                            <span slot="footer" class="dialog-footer">
                                <el-button @click="centerDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="selectActivity">确 定</el-button>
                            </span>
                        </el-dialog>


                        <el-form-item label="收益分配">
                            <el-select v-model="incomeValue" @change="centerDialogVisible2 = true" placeholder="请选择">
                                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-table :data="formData.allotRuleDTOList" style="min-width: 600px;margin-bottom: 20px;" align="cneter"
                            :row-class-name="tableRowClassName">
                            <el-table-column prop="role" label="身份" align="cneter" width="120">
                            </el-table-column>
                            <el-table-column prop="rate" label="比例" align="cneter" width="120">
                            </el-table-column>
                            <el-table-column prop="remark" label="说明" align="cneter">
                            </el-table-column>
                            <el-table-column label="操作" width="120">
                                <template slot-scope="scope">
                                    <el-button size="small" type="danger" @click="incomeDelete(scope.$index)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                        <el-dialog title="收益分配" :visible.sync="centerDialogVisible2" center @open="remark = null;rate = null">

                            <el-form-item label="说明" prop="remark">
                                <el-input v-model="remark"></el-input>
                            </el-form-item>
                            <el-form-item label="比例" prop="rate">
                                <el-input v-model="rate"></el-input>
                            </el-form-item>

                            <span slot="footer" class="dialog-footer">
                                <el-button @click="centerDialogVisible2 = false">取 消</el-button>
                                <el-button type="primary" @click="selectIncome">确 定</el-button>
                            </span>
                        </el-dialog>
                    </div>
                    <el-form-item class="button_submit">
                        <el-button type="primary" @click="submitForm('formData')" v-text="id? '保存':'立即创建'"></el-button>
                    </el-form-item>

                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import headTop from '@/components/headTop'
    import {
        addFund,
        loadUsers,
        getFundDetail
    } from '@/api/getData'
    import {
        baseUrl,
        baseImgPath
    } from '@/config/env'
    export default {
        data() {
            return {
                formData: {
                    name: '', //	是	string	名称
                    yearProfit1: '', //	是	double	预期收益1
                    yearProfit2: '', //	否	double	预期收益2
                    risk: '', //	否	double	合伙人最大风险(%)
                    targetFund: '', //	否	double	目标募集金额
                    minFund: '', //	否	double	最小入伙金额
                    minLevel: '', //	否	int	合伙人最小等级
                    operationAt: '', //	否	String	操作开始时间
                    endAt: '', //	否	String	结束操作时间
                    successFlag: true, //	否	String	募集成功提前进入操作期 （Y、N）
                    partnerFlag: true, //	否	String	合伙人的收益达预期可提前清盘 （Y、N）
                    account: '', //	否	String	交易账号
                    originatorId: '', //	否	String	发起人id
                    managerId: '', //	否	String	资金管理人id
                    remark: '', //	否	String	产品描述
                    platform: '', //	否	String	交易平台
                    strategy: '', //	否	String	投资策略
                    direction: '', //	否	String	买卖方向
                    range: '', //	否	String	投资范围
                    expectRiskProfitDTOList: [], //	否	json数组	风险及预期收益
                    allotRuleDTOList: [], //	否	json数组	收益分配
                },
                rules: {
                    name: [{
                        required: true,
                        message: '请输入基金名称',
                        trigger: 'blur'
                    }, ],

                    account: [{
                            required: true,
                            message: '请输入交易账户'
                        }
                    ],
                },
                levelList: [{
                    value: '1',
                    label: '一级'
                }, {
                    value: '2',
                    label: '二级'
                }, {
                    value: '3',
                    label: '三级'
                }],
                options: [{
                    value: 'partner',
                    label: '合伙人'
                }, {
                    value: 'originator',
                    label: '发起人'
                }, {
                    value: 'manager',
                    label: '资金管理人'
                }],
                activityValue: '',
                incomeValue: '',
                centerDialogVisible: false,
                centerDialogVisible2: false,

                risk: null,
                preProfit: null,

                remark: null,
                rate: null,

                originators: [], //发起人
                managers: [], //资产管理人

                baseUrl,
                baseImgPath,
                selectedCategory: ['快餐便当', '简餐']
            }
        },
        computed: {
        	id() {
        		return this.$route.query.id 
        	}
        },
        filters: {

        },
        components: {
            headTop,
        },
        activated () {
            console.log(this.id)
            if (this.id) {
                this.initData();
            }
        },
        mounted() {
            this.loadOriginators();
            this.loadManagers();
        },
        methods: {
            async loadOriginators() {
                try {
                    const res = await loadUsers('originator');
                    if (res.statusCode == '0000') {
                        let list = res.data.list;
                        if (list.length) {
                            list.forEach(item => {
                                item.value = item.id,
                                item.label = item.firstName + "-" + item.mobile
                            })
                        }
                        console.log(list)
                        this.originators = list;
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            async loadManagers() {
                try {
                    const res = await loadUsers('manager');
                    if (res.statusCode == '0000') {
                        let list = res.data.list;
                        if (list.length) {
                            list.forEach(item => {
                                item.value = item.id,
                                    item.label = item.firstName + "-" + item.mobile
                            })
                        }
                        console.log(list)
                        this.managers = list;
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },

            async initData() {
                try {
                    const res = await getFundDetail(this.id);
                    console.log(res)
                    if (res.statusCode == '0000') {
                        this.formData = res.data;
                        this.formData.successFlag = this.formData.successFlag == 'Y' ? true : false;
                        this.formData.partnerFlag = this.formData.partnerFlag == 'Y' ? true : false;
                    }
                } catch (err) {
                    console.log(err);
                }
            },
            async querySearchAsync(queryString, cb) {
                if (queryString) {
                    try {
                        const cityList = await searchplace(this.city.id, queryString);
                        if (cityList instanceof Array) {
                            cityList.map(item => {
                                item.value = item.address;
                                return item;
                            })
                            cb(cityList)
                        }
                    } catch (err) {
                        console.log(err)
                    }
                }
            },

            tableRowClassName(row, index) {
                if (index === 1) {
                    return 'info-row';
                } else if (index === 3) {
                    return 'positive-row';
                }
                return '';
            },
            selectActivity() {
                if (this.risk == null) {
                    this.$message({
                        type: 'info',
                        message: '请输入风险值'
                    });
                    return
                }
                if (this.preProfit == null) {
                    this.$message({
                        type: 'info',
                        message: '请输入预期收益'
                    });
                    return
                }
                let newObj = {};

                switch (this.activityValue) {
                    case 'partner':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "合伙人",
                            "roleFlag": "partner",
                            "risk": this.risk,
                            "preProfit": this.preProfit
                        }
                        break;
                    case 'originator':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "发起人",
                            "roleFlag": "originator",
                            "risk": this.risk,
                            "preProfit": this.preProfit
                        }
                        break;
                    case 'manager':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "资金管理人",
                            "roleFlag": "manager",
                            "risk": this.risk,
                            "preProfit": this.preProfit
                        }
                        break;

                }
                this.formData.expectRiskProfitDTOList.push(newObj);
                this.centerDialogVisible = false;
            },
            selectIncome() {
                if (this.remark == null) {
                    this.$message({
                        type: 'info',
                        message: '请输入收益说明'
                    });
                    return
                }
                if (this.rate == null) {
                    this.$message({
                        type: 'info',
                        message: '请输入收益计算比例'
                    });
                    return
                }
                let newObj = {};

                switch (this.incomeValue) {
                    case 'partner':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "合伙人",
                            "roleFlag": "partner",
                            "remark": this.remark,
                            "rate": this.rate
                        }
                        break;
                    case 'originator':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "发起人",
                            "roleFlag": "originator",
                            "remark": this.remark,
                            "rate": this.rate
                        }
                        break;
                    case 'manager':
                        newObj = {
                            "id": "",
                            "fundSignalId": "",
                            "role": "资金管理人",
                            "roleFlag": "manager",
                            "remark": this.remark,
                            "rate": this.rate
                        }
                        break;

                }
                this.formData.allotRuleDTOList.push(newObj);
                this.centerDialogVisible2 = false;
            },
            handleDelete(index) {
                this.formData.expectRiskProfitDTOList.splice(index, 1)
            },
            incomeDelete(index) {
                this.formData.allotRuleDTOList.splice(index, 1)
            },
            submitForm(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        if (this.id) {
                            this.formData.id = this.id;
                        }
                        Date.prototype.format = function(format) {
                            var o = {
                                "M+": this.getMonth() + 1, //month
                                "d+": this.getDate(), //day
                                "h+": this.getHours(), //hour
                                "m+": this.getMinutes(), //minute
                                "s+": this.getSeconds(), //second
                                "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
                                "S": this.getMilliseconds() //millisecond
                            }
                            if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
                                (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                            for (var k in o)
                                if (new RegExp("(" + k + ")").test(format))
                                    format = format.replace(RegExp.$1,
                                        RegExp.$1.length == 1 ? o[k] :
                                        ("00" + o[k]).substr(("" + o[k]).length));
                            return format;
                        }
                        let d1 = new Date(this.formData.operationAt);
                        let d2 = new Date(this.formData.endAt);

                        this.formData.operationAt = d1.format('yyyy-MM-dd hh:mm:ss');
                        this.formData.endAt = d2.format('yyyy-MM-dd hh:mm:ss');
                        this.formData.successFlag = this.formData.successFlag ? 'Y' : 'N';
                        this.formData.partnerFlag = this.formData.partnerFlag ? 'Y' : 'N';


                        try {
                            let res = await addFund(this.formData);
                            if (res.statusCode == '0000') {
                                this.$message({
                                    type: 'success',
                                    message: '保存成功'
                                });

                                this.formData = {
                                    name: '', //	是	string	名称
                                    yearProfit1: '', //	是	double	预期收益1
                                    yearProfit2: '', //	否	double	预期收益2
                                    risk: '', //	否	double	合伙人最大风险(%)
                                    targetFund: '', //	否	double	目标募集金额
                                    minFund: '', //	否	double	最小入伙金额
                                    minLevel: '', //	否	int	合伙人最小等级
                                    operationAt: '', //	否	String	操作开始时间
                                    endAt: '', //	否	String	结束操作时间
                                    successFlag: '', //	否	String	募集成功提前进入操作期 （Y、N）
                                    partnerFlag: '', //	否	String	合伙人的收益达预期可提前清盘 （Y、N）
                                    account: '', //	否	String	交易账号
                                    originatorId: '', //	否	String	合伙人id
                                    managerId: '', //	否	String	资金管理人id
                                    remark: '', //	否	String	产品描述
                                    platform: '', //	否	String	交易平台
                                    strategy: '', //	否	String	投资策略
                                    direction: '', //	否	String	买卖方向
                                    range: '', //	否	String	投资范围
                                    expectRiskProfitDTOList: [], //	否	json数组	风险及预期收益
                                    allotRuleDTOList: [], //	否	json数组	收益分配
                                }

                            } else {
                                this.$message({
                                    type: 'error',
                                    message: res.msgCode
                                });
                            }
                            console.log(res)
                        } catch (err) {
                            console.log(err)
                        }
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: '请检查输入是否正确',
                            offset: 100
                        });
                        return false;
                    }
                });
            },
        }
    }
</script>

<style lang="less">
    @import '../style/mixin';

    .button_submit {
        text-align: center;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }

    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }

    .el-table .info-row {
        background: #c9e5f5;
    }

    .el-table .positive-row {
        background: #e2f0e4;
    }

    .el-dialog--small {
        width: 30%;
    }

    .sec_box {
        margin-bottom: 60px;
    }

    .sec_box>p {
        font-size: 18px;
        color: #20a0ff;
        line-height: 50px;
    }
</style>
