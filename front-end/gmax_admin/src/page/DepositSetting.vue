<template>
    <div class="content_box">

        <div class="titling ">
            <span>出入金设置</span>
        </div>
        <div class="message_box">

            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="250px" class="demo-ruleForm">
                <p>账户入金设置</p>
                <el-form-item label="汇率方式：" prop="server">
                    <el-switch v-model="ruleForm.bool"></el-switch><span>实时汇率</span>
                    <div class="item_box"><span>汇率：</span><el-input v-model="input" ></el-input></div>
                    <div class="item_box"><span>加：</span><el-input v-model="input" ></el-input><span>失败时：</span><el-input v-model="input" ></el-input></div>
                    <div class="item_box"><span>单笔最小：</span><el-input v-model="input" ></el-input><span>单笔最大：</span><el-input v-model="input" ></el-input></div>
                </el-form-item>
                <el-form-item label="功能控制：" prop="server">
                    <el-switch v-model="ruleForm.bool"></el-switch><span>需校验码</span>
                    <el-switch v-model="ruleForm.bool"></el-switch><span>邮件通知</span>
                </el-form-item>
                <p>账户入金设置</p>
                <el-form-item label="汇率方式：" prop="server">
                    <el-switch v-model="ruleForm.bool"></el-switch><span>实时汇率</span>
                    <div class="item_box"><span>汇率：</span><el-input v-model="input" ></el-input></div>
                    <div class="item_box"><span>减：</span><el-input v-model="input" ></el-input><span>失败时：</span><el-input v-model="input" ></el-input></div>
                    <div class="item_box"><span>单笔最小：</span><el-input v-model="input" ></el-input><span>单笔最大：</span><el-input v-model="input" ></el-input></div>
                </el-form-item>
                <el-form-item label="功能控制：" prop="server">
                    <el-switch v-model="ruleForm.bool"></el-switch><span>需校验码</span>
                    <el-switch v-model="ruleForm.bool"></el-switch><span>邮件通知</span>
                </el-form-item>
                
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
                ruleForm: {
                    server: '',
                    uid: '',
                    password: '',
                    tname: '',
                    tpass: '',
                    num: '',
                    bool: false,
                    bool2: false,
                },
                rules: {
                    address: [{
                        required: true,
                        message: '请输入MT4地址',
                        trigger: 'blur'
                    }],
                    name: [{
                        required: true,
                        message: '请输入MT4开户用户名',
                        trigger: 'blur'
                    }],
                    pass: [{
                        required: true,
                        message: '请输入MT4开户密码',
                        trigger: 'blur'
                    }],
                    //                     tname: [{
                    //                         required: true,
                    //                         message: '请输入MT4交易用户名',
                    //                         trigger: 'blur'
                    //                     }],
                    //                     tpass: [{
                    //                         required: true,
                    //                         message: '请输入MT4交易密码',
                    //                         trigger: 'blur'
                    //                     }],
                    //                     num: [{
                    //                         required: true,
                    //                         message: '请输入MT4前三位',
                    //                         trigger: 'blur'
                    //                     }],

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
            async initData() {
                try {
                    const res = await this.fetch("/api/mt4_configure/get");
                    console.log(res)
                    if (res.statusCode == '0000') {
                        this.ruleForm = res.data;
                    } else {
                        this.datas = {};
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            submitForm(ruleForm) {
            	this.$refs[ruleForm].validate(async (valid) => {
            		if (valid) {
            			const params = {
            				...this.ruleForm
            			}
            			try {
            				const result = await this.fetch("/api/mt4_configure/createMt4Configure",params,"POST");
            					
            				if (result.statusCode == '0000') {
            					this.$message({
            						type: 'success',
            						message: '保存成功'
            					});
            					
            				} else {
            					this.$message({
            						type: 'error',
            						message: result.msgCode
            					});
            				}
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
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        mounted() {
            this.initData();
        }
    }
</script>

<style scoped>
    .message_box {
        margin: 20px;
        width: 1000px;
    }
    .demo-ruleForm .el-input {
        width: 180px;
    }
    .item_box span {
        display: inline-block;
        width: 100px;
        padding-left: 10px;
    }
</style>
