import Vue from 'vue'
import Router from 'vue-router'


//报表
// import AccountReport from '@/components/reports/AccountReport'
// import BrokeReport from '@/components/reports/BrokeReport'
// import ScoreReport from '@/components/reports/ScoreReport'



Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            component: () => import("@/page/ContentBox"),
            children: [
                {
                    path: '/',
                    name: 'HomePage',
                    component: () => import("@/page/HomePage")
                },
                //任务
                {
                    path: '/task',
                    name: 'TaskService',
                    component: () => import("@/page/TaskService")
                },
                {
                    path: '/tasked',
                    name: 'TaskFinance',
                    component: () => import("@/page/TaskFinance")
                },
                //用户
                {
                    path: '/user',
                    name: 'brokeruser',
                    component: () => import("@/page/BrokerWork")
                },
                //会员
                {
                    path: '/member',
                    name: 'MemberPage',
                    component: () => import("@/page/MemberPage")
                },
                //账户
                {
                    path: '/account',
                    name: 'AccountList',
                    component: () => import("@/page/AccountList")
                },
                //报表
//                 {
//                     path: '/report1',
//                     name: 'AccountReport',
//                     component: AccountReport
//                 },
//                 {
//                     path: '/report2',
//                     name: 'BrokeReport',
//                     component: BrokeReport
//                 },
//                 {
//                     path: '/report3',
//                     name: 'ScoreReport',
//                     component: ScoreReport
//                 },
                {
                    path: '/feed',
                    name: 'FeedBack',
                    component: () => import("@/page/FeedBack")
                },
                {
                    path: '/broke',
                    name: 'BrokeRage',
                    component: () => import("@/page/BrokeRage")
                },
                {
                    path: '/record',
                    name: 'RecordPage',
                    component: () => import("@/page/RecordPage")
                },
                {
                    path: '/position',
                    name: 'RecordTrading',
                    component: () => import("@/page/RecordTrading")
                },
                {
                    path: '/get',
                    name: 'GetCustomers',
                    component: () => import("@/page/GetCustomers")
                },
                {
                    path: '/message',
                    name: 'UserMessage',
                    component: () => import("@/page/UserMessage")
                },
                {
                    path: '/manage',
                    name: 'AccountManage',
                    component: () => import("@/page/AccountManage")
                },
                {
                    path: '/fundDetail',
                    name: 'FundDetail',
                    component: () => import("@/page/FundDetail")
                },
                {
                    path: '/into',
                    name: 'MoneyInto',
                    component: () => import("@/page/money/MoneyInto")
                },
                {
                    path: '/out',
                    name: 'MoneyOut',
                    component: () => import("@/page/money/MoneyOut")
                },
                {
                    path: '/store',//二维码通道
                    name: 'StoreQrcode',
                    component: () => import("@/page/money/StoreQrcode")
                },
                {
                    path: '/union1',//银联通道
                    name: 'StoreUnionPay',
                    component: () => import("@/page/money/StoreUnionPay")
                },
                {
                    path: '/union2', //国际汇款通道
                    name: 'StoreUsdPay',
                    component: () => import("@/page/money/StoreUsdPay")
                },
                {
                    path: '/usdt', //usdt汇款通道
                    name: 'UsdtSetting',
                    component: () => import("@/page/money/UsdtSetting")
                },
                {
                    path: '/push',
                    name: 'PushPage',
                    component: () => import("@/page/PushPage")
                },
                {
                    path: '/mt4',
                    name: 'Mt4Setting',
                    component: () => import("@/page/Mt4Setting")
                },
                {
                    path: '/protocol',
                    name: 'ProtocolPage',
                    component: () => import("@/page/protocol/ProtocolPage")
                },
                {
                    path: '/addProtocol',
                    name: 'SaveProtocol',
                    component: () => import("@/page/protocol/SaveProtocol")
                },{
                    path: '/deposit',
                    name: 'DepositSetting',
                    component: () => import("@/page/DepositSetting")
                },
            ]
        },
        {
            path: '/login',
            name: 'LoginPage',
            component: () => import("@/page/LoginPage")
        },
        {
            path: '/regiter',
            name: 'RegisterPage',
            component: () => import("@/page/RegisterPage")
        },
        {
            path: '/unionpay',
            name: 'GetUnionPay',
            component: () => import("@/page/money/GetUnionPay")
        },
        {
            path: '/qrpay',
            name: 'GetStoreQrcode',
            component: () => import("@/page/money/GetStoreQrcode")
        },
        {
            path: '/usdpay',
            name: 'GetUsdPay',
            component: () => import("@/page/money/GetUsdPay")
        }
    ]
})
