<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table :data="list" highlight-current-row style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="交易平台">
                                <span>{{ props.row.platform }}</span>
                            </el-form-item>
                            <el-form-item label="投资策略">
                                <span>{{ props.row.strategy }}</span>
                            </el-form-item>
                            <el-form-item label="买卖方向">
                                <span>{{ props.row.direction }}</span>
                            </el-form-item>
                            <el-form-item label="投资范围">
                                <span>{{ props.row.range }}</span>
                            </el-form-item>
                            <el-form-item label="产品描述">
                                <span>{{ props.row.remark }}</span>
                            </el-form-item>
                            <el-form-item label="目标募集金额">
                                <span>{{ props.row.targetFund}}</span>
                            </el-form-item>
                            <el-form-item label="预期收益率">
                                <span>{{ props.row.yearProfit1}}%+ {{props.row.yearProfit2}}%</span>
                            </el-form-item>
                            <el-form-item label="募集进度">
                                <span>{{ props.row.progress}}%</span>
                            </el-form-item>
                            <el-form-item label="合伙人最小等级">
                                <span>{{ props.row.minLevel}}</span>
                            </el-form-item>
                            <el-form-item label="最小入伙金额">
                                <span>{{ props.row.minFund}}</span>
                            </el-form-item>
                            <el-form-item label="最大风险">
                                <span>{{ props.row.risk}}%</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>

                <el-table-column property="account" label="账户">
                </el-table-column>
                <el-table-column property="name" label="名称">
                </el-table-column>
                <el-table-column property="managerName" label="资金管理人">
                </el-table-column>
                <el-table-column property="originatorName" label="发起人">
                </el-table-column>

                <el-table-column property="operationAt" label="发起时间">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>

                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>

            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage" :page-size="page.size"
                    layout="total, prev, pager, next" :total="page.count">
                </el-pagination>
            </div>


            <el-dialog title="修改基金信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="基金名称" label-width="100px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="买卖方向" label-width="100px">
                        <el-input v-model="selectTable.direction"></el-input>
                    </el-form-item>
                    <el-form-item label="交易平台" label-width="100px">
                        <el-input v-model="selectTable.platform"></el-input>
                    </el-form-item>
                    <el-form-item label="投资范围" label-width="100px">
                        <el-input v-model="selectTable.range"></el-input>
                    </el-form-item>
                    <el-form-item label="产品描述" label-width="100px">
                        <el-input v-model="selectTable.remark"></el-input>
                    </el-form-item>
                    <el-form-item label="投资策略" label-width="100px">
                        <el-input v-model="selectTable.strategy"></el-input>
                    </el-form-item>
                    <el-form-item label="状态" label-width="100px">
                        <el-input v-model="selectTable.status"></el-input>
                    </el-form-item>
                    <el-form-item label="yearProfit1" label-width="100px">
                        <el-input v-model="selectTable.yearProfit1"></el-input>
                    </el-form-item>
                    <el-form-item label="yearProfit2" label-width="100px">
                        <el-input v-model="selectTable.yearProfit2"></el-input>
                    </el-form-item>


                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirm">确 定</el-button>
                </div>
            </el-dialog>

        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {
        deleteFund,
        getFundList
    } from '@/api/getData'
    export default {
        data() {
            return {
                list: [],
                selectTable: {},
                dialogFormVisible: false,
                page: {
                    size: 20,
                    count: 0,
                    currentPage: 1,
                }

            }
        },
        components: {
            headTop,
        },
        created() {
            this.initData();
        },
        methods: {
            async initData() {
                try {
                    const res = await getFundList({
                        page_number: this.page.currentPage, //	是	string	页码
                        page_size: this.page.size, //	是	string	每页大小
                        formParams: {}
                    });
                    console.log(res)
                    if (res.statusCode == '0000') {
                        this.list = res.data.list;
                        this.page.count = res.data.total;
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleEdit(index, row) {
                // console.log(JSON.stringify(row))
                this.selectTable = JSON.parse(JSON.stringify(row));
                
                this.$router.push({
                    path: '/addFund?id='+this.selectTable.id
                })
//                 this.dialogFormVisible = true;

            },
            confirm() {

            },
            async handleDelete(index, row) {
                try {
                    const res = await deleteFund(row.id);
                    if (res.statusCode == '0000') {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.list.splice(index, 1);
                    } else {
                        throw new Error(res.message)
                    }
                } catch (err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除失败')
                }
            },

            handleCurrentChange(val) {
                this.page.currentPage = val;
                this.initData()
            }
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';

    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 120px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }

    .table_container {
        padding: 20px;
    }
</style>
