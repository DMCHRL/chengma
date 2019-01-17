<template>
    <div>
        <v-chart ref="demo" :data="dataArray">
            <v-bar :colors="'#ffa01e'" />
            <v-tooltip :show-item-marker="false" />
            <v-legend show-x-value />
        </v-chart>
    </div>
</template>

<script>
    import {
        mapState,
        mapActions
    } from 'vuex'
    import {
        VChart,
        VLine,
        VArea,
        VBar,
        VPie,
        VPoint,
        VScale,
        VAxis,
        VGuide,
        VTooltip,
        VLegend
    } from 'vux'
    export default {
        name: 'chart',
        props: {},
        data() {
            return {
                dataArray: [{
                        year: '入伙产品',
                        sales: 0
                    },
                    {
                        year: '发起合伙',
                        sales: 0
                    },
                    {
                        year: '资产管理',
                        sales: 0
                    }
                ]
            }
        },
        computed: {
            ...mapState(['userInfo', 'userMess', 'noticeNum']),
        },
        components: {
            VChart,
            VLine,
            VArea,
            VBar,
            VPie,
            VPoint,
            VScale,
            VAxis,
            VGuide,
            VTooltip,
            VLegend
        },
        methods: {
            whatName(name) {
                if (!name) return;
                switch (name) {
                    case 'currentFund':
                        return '入伙产品';
                    case 'originatorFund':
                        return '发起合伙';
                    case 'managerFund':
                        return '资产管理';

                    default:
                        break;
                }
            },
            initData(obj) {
                let arr = [];
                let i = {};
                for (let item in obj) {
                    i = {};
                    i.year = this.whatName(item);
                    i.sales = obj[item];
                    arr.push(i)
                }
                this.dataArray = arr;
                this.$refs.demo.rerender();
            },
            async initChart() {
                try {
                    const res = await this.fetch("/api/user/showUserChart/" + this.userMess.userId);
                    // console.log(res)
                    if (res.statusCode == '0000') {
                        this.initData(res.data)
                    } else {
                        this.chartData = {};
                    }
                } catch (err) {
                    console.log('获取数据失败', err);
                }
            },
        },
        mounted() {},
        activated() {
            this.initChart();
        }
    }
</script>

<style scoped>

</style>
