<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table :data="tableData" :row-key="row => row.index" style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="mt4密码">
                                <span>{{ props.row.mt4Password }}</span>
                            </el-form-item>
                            <el-form-item label="观摩密码">
                                <span>{{ props.row.seePassword }}</span>
                            </el-form-item>

                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="账户号" prop="account">
                </el-table-column>
                <el-table-column label="账户名" prop="accountName">
                </el-table-column>
                <el-table-column label="交易状态" prop="enableTrade">
                </el-table-column>
                <el-table-column label="创建时间" prop="createAt">
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">

                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage" :page-size="page.size"
                    layout="total, prev, pager, next" :total="page.count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {
        getAccountList,
        deleteAccount
    } from '@/api/getData'
    export default {
        data() {
            return {
                tableData: [],
                page: {
                    size: 20,
                    count: 0,
                    currentPage: 1,
                },

                currentRow: null,
                offset: 0,
                limit: 20,
                count: 0,
                currentPage: 1,
                restaurant_id: null,
                expendRow: [],
            }
        },
        components: {
            headTop,
        },
        created() {
            // this.restaurant_id = this.$route.query.restaurant_id;
            this.initData();
        },
        mounted() {

        },
        methods: {
            async initData() {
                try {
                    const res = await getAccountList({
                        page_number: this.page.currentPage, //	是	string	页码
                        page_size: this.page.size, //	是	string	每页大小
                        formParams: {}
                    });
                    console.log(res)
                    if (res.statusCode == '0000') {

                        this.tableData = res.data.list;
                        this.page.count = res.data.total;

                    } else {
                        throw new Error('获取数据失败');
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.page.currentPage = val;
                this.initData()
            },
            async handleDelete(index, row) {
                try {
                    const res = await deleteAccount(row.id);
                    if (res.statusCode == '0000') {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.tableData.splice(index, 1);
                    } else {
                        this.$message({
                            type: 'error',
                            message: err.message
                        });
                    }
                } catch (err) {

                    console.log('删除失败', err)
                }
            },
            async getOrders() {
                const Orders = await getOrderList({
                    offset: this.offset,
                    limit: this.limit,
                    restaurant_id: this.restaurant_id
                });
                this.tableData = [];
                Orders.forEach((item, index) => {
                    const tableData = {};
                    tableData.id = item.id;
                    tableData.total_amount = item.total_amount;
                    tableData.status = item.status_bar.title;
                    tableData.user_id = item.user_id;
                    tableData.restaurant_id = item.restaurant_id;
                    tableData.address_id = item.address_id;
                    tableData.index = index;
                    this.tableData.push(tableData);
                })
            },
            async expand(row, status) {
                if (status) {
                    const restaurant = await getResturantDetail(row.restaurant_id);
                    const userInfo = await getUserInfo(row.user_id);
                    const addressInfo = await getAddressById(row.address_id);

                    this.tableData.splice(row.index, 1, { ...row,
                        ...{
                            restaurant_name: restaurant.name,
                            restaurant_address: restaurant.address,
                            address: addressInfo.address,
                            user_name: userInfo.username
                        }
                    });
                    this.$nextTick(() => {
                        this.expendRow.push(row.index);
                    })
                } else {
                    const index = this.expendRow.indexOf(row.index);
                    this.expendRow.splice(index, 1)
                }
            },
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';

    .table_container {
        padding: 20px;
    }

    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>
