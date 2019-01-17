<template>
    <div class="content_box">

        <div class="titling ">
            <span>USDT通道</span>
            <el-switch v-model="isUse" @change="ChangeUse"></el-switch><span>是否启用</span>
        </div>
        <div class="message_box">

            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
                <el-form-item label="商户号：" prop="userId">
                    <el-input v-model="ruleForm.userId"></el-input>
                </el-form-item>
                <el-form-item label="密钥：" prop="channelKey">
                    <el-input v-model="ruleForm.channelKey"></el-input>
                </el-form-item>
                <el-form-item label="支付url：" prop="payUrl">
                    <el-input v-model="ruleForm.payUrl"></el-input>
                </el-form-item>
                <el-form-item label="异步通知url：" prop="resultUrl">
                    <el-input v-model="ruleForm.resultUrl"></el-input>
                </el-form-item>
                <el-form-item label="同步通知url：" prop="notifyUrl">
                    <el-input v-model="ruleForm.notifyUrl"></el-input>
                </el-form-item>
                <el-form-item label="货币类型：" prop="channelId">
                    <!-- <el-input v-model="ruleForm.channelId"></el-input> -->
                    <el-select v-model="ruleForm.channelId" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="兑美元汇率：" prop="usdRate">
                    <el-input v-model="ruleForm.usdRate"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                </el-form-item>
            </el-form>

        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                isUse: false,
                ruleForm: {
                    userId: '',//	是	string	商户号
                    channelKey: '',//	是	string	密钥
                    payUrl: '',//	是	string	支付url
                    resultUrl: '',//	是	string	异步通知
                    notifyUrl: '',//	否	string	同步通知
                    channelId: 'Tether',//	否	string	货币类型 3 Bitcoin比特币, 6 Litecoin莱特币, 7 Tether泰达币
                    usdRate: '',//	否	string	兑美元汇率
                },
                options: [
                    {value: 3,label: '比特币'},
                    {value: 6,label: '莱特币'},
                    {value: 7,label: '泰达币'},
                ],
                rules: {
                    userId: [{
                        required: true,
                        message: '请输入商户号',
                        trigger: 'blur'
                    }],
                    channelKey: [{
                        required: true,
                        message: '请输入密钥',
                        trigger: 'blur'
                    }],
                    payUrl: [{
                        required: true,
                        message: '请输入支付url',
                        trigger: 'blur'
                    }],
                    resultUrl: [{
                        required: true,
                        message: '请输入异步通知',
                        trigger: 'blur'
                    }],
                    notifyUrl: [{
                        required: true,
                        message: '请输入同步通知',
                        trigger: 'blur'
                    }],
                    usdRate: [{
                        required: true,
                        message: '请输入兑美元汇率',
                        trigger: 'blur'
                    }],
                    
                }
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
        components: {},
        methods: {
            ChangeUse () {
                let _this = this;
                this.fetch("/api/digital_currency/changFlag/"+_this.ruleForm.id, function(res) {
                    if (res.statusCode == '0000') {
                        _this.hasUsdt();
                    }
                })
            },
            submitForm(formName) {
                let _this = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        
                        this.post("/api/digital_currency/saveDigitalCurrency", this.ruleForm, function(res) {
                            if (res.statusCode == '0000') {
                                _this.$message({
                                    message: '保存成功',
                                    type: 'success'
                                });
                            } else {
                                _this.$message({
                                    message: res.data.msg,
                                    type: 'warning'
                                });
                            }
                        })
                        
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            initData () {
                let _this = this;
                _this.fetch("/api/digital_currency/findAll", function(res) {
                    // console.log(res)
                    if (res.statusCode == '0000') {
                        _this.ruleForm = res.data.list[0];
                    } else {
                        _this.$message({
                            message: res.data.msg,
                            type: 'warning'
                        });
                    }
                })
            },
            //检查usdt是否可用
            hasUsdt() {
                let _this = this;
                _this.fetch("/api/digital_currency/findNew", function(res) {
                    // console.log(res)
                    if (res.statusCode == "0000") {
                        if (res.data) {
                            _this.isUse = true;
                        }else {
                            _this.isUse = false;
                        }
                    }
                })
            }
        },
        mounted() {
            this.initData();
            this.hasUsdt();
        }
    }
</script>

<style scoped>
    .message_box {
        margin: 20px;
        width: 1000px;
    }
</style>
