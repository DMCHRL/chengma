<template>
    <div class="content_box">

        <div class="titling ">
            <span>MT4设置</span>
        </div>
        <div class="message_box">

            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="250px" class="demo-ruleForm">
                <el-form-item label="MT4地址：" prop="server">
                    <el-input v-model="ruleForm.server"></el-input>
                </el-form-item>
                <el-form-item label="MT4开户用户名：" prop="uid">
                    <el-input v-model="ruleForm.uid"></el-input>
                </el-form-item>
                <el-form-item label="MT4开户密码：" prop="password">
                    <el-input v-model="ruleForm.password"></el-input>
                </el-form-item>
                <!-- <el-form-item label="MT4交易用户名：" prop="tname">
                    <el-input v-model="ruleForm.tname"></el-input>
                </el-form-item>
                <el-form-item label="MT4交易密码：" prop="tpass">
                    <el-input v-model="ruleForm.tpass"></el-input>
                </el-form-item>
                <el-form-item label="MT4前三位（共8位）：" prop="num">
                    <el-input v-model="ruleForm.num"></el-input>
                </el-form-item> -->

                <el-form-item label="客户注册账号是否自动审核并自动注册MT4账号：" prop="bool">
                    <el-switch v-model="ruleForm.bool"></el-switch><span>勾选后自动审核、自动注册MT4账号</span>
                </el-form-item>
                <el-form-item label="客户中心密码与MT4密码是否一致：" prop="bool2">
                    <el-switch v-model="ruleForm.bool2"></el-switch><span>勾选后客户中心和MT4密码一致，并强制客户中心密码复杂度高</span>
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
</style>
