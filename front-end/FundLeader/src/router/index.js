import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'


Vue.use(Router)

export default new Router({
	routes: [{
			path: '/',
			name: 'Home',
			component: Home,
			meta: {index: 1, navShow: true, keepAlive: true}
		},
		{
			path: '/fund',
			name: 'JoinFund',
			component: () => import('../views/JoinFund.vue'),
			meta: {index: 2, navShow: true, keepAlive: true}
		},
        {
        	path: '/join-details',
        	name: 'JoinDetails',
        	component: () => import('../views/JoinDetails.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
		{
			path: '/mine',
			name: 'Mine',
			component: () => import('../views/Mine.vue'),
			meta: {index: 2, navShow: true, keepAlive: true}
		},
        {
            path: '/notice',
            name: 'Notice',
            component: () => import('../views/Notice.vue'),
            meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/login',
        	name: 'Login',
        	component: () => import('../views/Login.vue'),
        	meta: {index: 2, navShow: false, keepAlive: true}
        },
        {
        	path: '/myjoin',
        	name: 'MyJoin',
        	component: () => import('../views/MyJoin.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/fund-details',
        	name: 'FundDetails',
        	component: () => import('../views/FundDetails.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/recharge',
        	name: 'Recharge',
        	component: () => import('../views/Recharge.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/withdraw',
        	name: 'Withdraw',
        	component: () => import('../views/Withdraw.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/account',
        	name: 'Account',
        	component: () => import('../views/Account.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/verified',
        	name: 'Verified',
        	component: () => import('../views/account/Verified.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/bindemail',
        	name: 'BindEmail',
        	component: () => import('../views/account/BindEmail.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/paypass',
        	name: 'PaymentPassword',
        	component: () => import('../views/account/PaymentPassword.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/confirm-join',
        	name: 'ConfirmJoin',
        	component: () => import('../views/ConfirmJoin.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/addBankCard',
        	name: 'addBankCard',
        	component: () => import('../views/addBankCard.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/about',
        	name: 'About',
        	component: () => import('../views/About.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/userinfo',
        	name: 'UserInfo',
        	component: () => import('../views/UserInfo.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/mybankcard',
        	name: 'MyBankCard',
        	component: () => import('../views/MyBankCard.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/ConfirmRecharge',
        	name: 'ConfirmRecharge',
        	component: () => import('../views/ConfirmRecharge.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
        {
        	path: '/agree',
        	name: 'Agree',
        	component: () => import('../views/Agree.vue'),
        	meta: {index: 3, navShow: false, keepAlive: true}
        },
//         {
//             path: '/open',
//             name: 'AccountOpen',
//             component: AccountOpen,
//             meta: {index: 4, navShow: false, keepAlive: false}
//         },
// 		{
// 			path: '/online',
// 			name: 'OnlineVideo',
// 			component: OnlineVideo,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/vlist',
// 			name: 'VideoList',
// 			component: VideoList,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/vdetails',
// 			name: 'VideoDetails',
// 			component: VideoDetails,
// 			meta: {index: 5, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/training',
// 			name: 'TrainingCourses',
// 			component: TrainingCourses,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/tdetails',
// 			name: 'TrainingDetails',
// 			component: TrainingDetails,
// 			meta: {index: 4, navShow: false, keepAlive: true}
// 		},
// 		{
// 			path: '/exam',
// 			name: 'TraderExam',
// 			component: TraderExam,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/examdetails',
// 			name: 'ExamDetails',
// 			component: ExamDetails,
// 			meta: {index: 4, navShow: false, keepAlive: true}
// 		},
// 		{
// 			path: '/apply-exam',
// 			name: 'ApplyExam',
// 			component: ApplyExam,
// 			meta: {index: 5, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/live',
// 			name: 'LivePage',
// 			component: LivePage,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/newslist',
// 			name: 'NewsList',
// 			component: NewsList,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/news-details',
// 			name: 'NewsDetails',
// 			component: NewsDetails,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/strategy-details',
// 			name: 'StrategyDetails',
// 			component: StrategyDetails,
// 			meta: {index: 3, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/mystrategy',
// 			name: 'MyStrategy',
// 			component: MyStrategy,
// 			meta: {index: 3, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/application',
// 			name: 'Application',
// 			component: Application,
// 			meta: {index: 4, navShow: false, keepAlive: true}
// 		},
// 		{
// 			path: '/ech-details',
// 			name: 'ExchangerDetails',
// 			component: ExchangerDetails,
// 			meta: {index: 3, navShow: false, keepAlive: true, headOption: {ishide: true,showBack: false}}
// 		},
// 		
// 		
// 		
// 		{
// 			path: '/point',
// 			name: 'PointWallet',
// 			component: PointWallet,
// 			meta: {index: 3, navShow: true, keepAlive: true,headOption: {ishide: true,showBack: false}}
// 		},
// 		{
// 			path: '/set',
// 			name: 'SettingInformation',
// 			component: SettingInformation,
// 			meta: {index: 3, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/change',
// 			name: 'ChangeDetails',
// 			component: ChangeDetails,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/login',
// 			name: 'LoginPage',
// 			component: LoginPage,
// 			meta: {index: 2, navShow: false, keepAlive: true,headOption: {ishide: true,showBack: false}}
// 		},
// 		{
// 			path: '/webview',
// 			name: 'WebViewBox',
// 			component: WebViewBox,
// 			meta: {index: 4, navShow: false, keepAlive: true,headOption: {ishide: true,showBack: false}}
// 		},
// 		{
// 			path: '/bind',
// 			name: 'BindAccount',
// 			component: BindAccount,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/release',
// 			name: 'ReleaseOrder',
// 			component: ReleaseOrder,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/history',
// 			name: 'HistoryList',
// 			component: HistoryList,
// 			meta: {index: 4, navShow: true, keepAlive: true}
// 		},
// 		{
// 			path: '/apply-train',
// 			name: 'ApplyCourses',
// 			component: ApplyCourses,
// 			meta: {index: 5, navShow: true, keepAlive: false}
// 		},
// 		{
// 			path: '/agreement',
// 			name: 'AgreementPage',
// 			component: AgreementPage,
// 			meta: {index: 5, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/account',
// 			name: 'AccountPage',
// 			component: AccountPage,
// 			meta: {index: 3, navShow: false, keepAlive: true}
// 		},
// 		{
// 			path: '/order',
// 			name: 'Order',
// 			component: Order,
// 			meta: {index: 3, navShow: true, keepAlive: false}
// 		},
// 		{
// 			path: '/settle',
// 			name: 'Settlement',
// 			component: Settlement,
// 			meta: {index: 4, navShow: false, keepAlive: false}
// 		},
// 		{
// 			path: '/settle-one',
// 			name: 'SettlementOne',
// 			component: SettlementOne,
// 			meta: {index: 4, navShow: false, keepAlive: false}
// 		},
	]
})