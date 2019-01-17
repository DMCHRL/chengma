<template>
    <div class="page_box">
        <my-header :leftOptions="headOption"></my-header>

        <div class="body_box box_pb0" id="my-box">

            <div class="con_box">


                <div class="sec_list">

                    <template v-for="(item,index) in list">

                        <fund-details-item :key="index" :item="item"></fund-details-item>

                    </template>
                    <p class="trip_p" v-if="!list.length">暂无资金记录</p>
                </div>


            </div>


        </div>

    </div>
</template>

<script>
    import {
        mapState,
        mapActions
    } from 'vuex'
    import FundDetailsItem from '@/components/FundDetailsItem'
    export default {
        data() {
            return {
                headOption: {
                    title: '资金明细',
                    backText: '',
                    showBack: true,
                    text: '重要消息，不容错过'
                },
                list: [],

            }
        },
        filters: {

        },
        computed: {
            ...mapState(['userMess']),
        },
        components: {
            FundDetailsItem
        },
        methods: {
            async initData() {
                try {
                    const res = await this.fetch("/api/asset_detail/loadMyAssetDetail");
                    console.log(res)
                    if (res.statusCode == '0000') {
                        let list = res.data.list;
                        list.forEach(item => {
                            item.showBottom = false;
                        })
                        this.list = list;
                    } else {
                        this.list = [];
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
        },
        activated() {
            this.initData();
        }
    }
</script>

<style scoped>
    .sec_tab {
        margin: 10px;
        background-color: #fff;
        position: relative;
        top: -20px;
    }

    .sec_tab .item_box {
        line-height: 40px;
        width: 33.33%;
        text-align: center;
    }

    .sec_tab .item_box.xl {
        width: 27%;
    }

    .sec_tab .item_box span {
        color: #9a9a9a;
        font-size: 0.346rem;
    }

    .sec_tab .item_box span.active {
        color: #1e275e;
    }

    .sec_tab .item_box i.iconfont {
        color: #1e275e;
        font-size: 0.3rem;
        margin-left: 0.1rem;
    }
</style>
