import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/components/home/HomePage'
import StrategyPage from '@/components/strategy/StrategyPage'
import ExchangerPage from '@/components/exchanger/ExchangerPage'
import MinePage from '@/components/mine/MinePage'

import NewsList from '@/components/home/NewsList'
import NewsDetails from '@/components/home/NewsDetails'

import OnlineVideo from '@/components/online/OnlineVideo'
import VideoList from '@/components/online/VideoList'
import VideoDetails from '@/components/online/VideoDetails'

import TrainingCourses from '@/components/training/TrainingCourses'
import TrainingDetails from '@/components/training/TrainingDetails'
import ApplyCourses from '@/components/training/ApplyCourses'


import TraderExam from '@/components/exam/TraderExam'
//import RegisterExam from '@/components/exam/RegisterExam'
import ExamDetails from '@/components/exam/ExamDetails'
import ApplyExam from '@/components/exam/ApplyExam'

import LivePage from '@/components/live/LivePage'

import StrategyDetails from '@/components/strategy/StrategyDetails'
import MyStrategy from '@/components/strategy/MyStrategy'
import BindAccount from '@/components/strategy/BindAccount'
import ReleaseOrder from '@/components/strategy/ReleaseOrder'
import Application from '@/components/strategy/Application'
import HistoryList from '@/components/strategy/HistoryList'


import ExchangerDetails from '@/components/exchanger/ExchangerDetails'
import AccountOpen from '@/components/exchanger/AccountOpen'

import NoticePage from '@/components/mine/NoticePage'
import PointWallet from '@/components/mine/PointWallet'
import SettingInformation from '@/components/mine/SettingInformation'
import ChangeDetails from '@/components/mine/ChangeDetails'
import AccountPage from '@/components/mine/AccountPage'

import LoginPage from '@/components/other/LoginPage'
import WebViewBox from '@/components/other/WebViewBox'
import AgreementPage from '@/components/other/AgreementPage'

import Order from '@/components/Order/Order'
import Settlement from '@/components/Order/Settlement'
import SettlementOne from '@/components/Order/SettlementOne'

Vue.use(Router)

export default new Router({
	routes: [{
			path: '/',
			name: 'HomePage',
			component: HomePage,
			meta: {index: 1, navShow: true, keepAlive: true}
		},
		{
			path: '/strategy',
			name: 'StrategyPage',
			component: StrategyPage,
			meta: {index: 2, navShow: true, keepAlive: true}
		},
		{
			path: '/exchanger',
			name: 'ExchangerPage',
			component: ExchangerPage,
			meta: {index: 2, navShow: true, keepAlive: true}
		},
		{
			path: '/mine',
			name: 'MinePage',
			component: MinePage,
			meta: {index: 2, navShow: true, keepAlive: true, headOption: {ishide: true,showBack: false}}
		},
		{
			path: '/online',
			name: 'OnlineVideo',
			component: OnlineVideo,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/vlist',
			name: 'VideoList',
			component: VideoList,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/vdetails',
			name: 'VideoDetails',
			component: VideoDetails,
			meta: {index: 5, navShow: false, keepAlive: false}
		},
		{
			path: '/training',
			name: 'TrainingCourses',
			component: TrainingCourses,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/tdetails',
			name: 'TrainingDetails',
			component: TrainingDetails,
			meta: {index: 4, navShow: false, keepAlive: true}
		},
		{
			path: '/exam',
			name: 'TraderExam',
			component: TraderExam,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/examdetails',
			name: 'ExamDetails',
			component: ExamDetails,
			meta: {index: 4, navShow: false, keepAlive: true}
		},
		{
			path: '/apply-exam',
			name: 'ApplyExam',
			component: ApplyExam,
			meta: {index: 5, navShow: false, keepAlive: false}
		},
		{
			path: '/live',
			name: 'LivePage',
			component: LivePage,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/newslist',
			name: 'NewsList',
			component: NewsList,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/news-details',
			name: 'NewsDetails',
			component: NewsDetails,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/strategy-details',
			name: 'StrategyDetails',
			component: StrategyDetails,
			meta: {index: 3, navShow: false, keepAlive: false}
		},
		{
			path: '/mystrategy',
			name: 'MyStrategy',
			component: MyStrategy,
			meta: {index: 3, navShow: false, keepAlive: false}
		},
		{
			path: '/application',
			name: 'Application',
			component: Application,
			meta: {index: 4, navShow: false, keepAlive: true}
		},
		{
			path: '/ech-details',
			name: 'ExchangerDetails',
			component: ExchangerDetails,
			meta: {index: 3, navShow: false, keepAlive: true, headOption: {ishide: true,showBack: false}}
		},
		{
			path: '/open',
			name: 'AccountOpen',
			component: AccountOpen,
			meta: {index: 4, navShow: false, keepAlive: false}
		},
		{
			path: '/notice',
			name: 'NoticePage',
			component: NoticePage,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		
		{
			path: '/point',
			name: 'PointWallet',
			component: PointWallet,
			meta: {index: 3, navShow: true, keepAlive: true,headOption: {ishide: true,showBack: false}}
		},
		{
			path: '/set',
			name: 'SettingInformation',
			component: SettingInformation,
			meta: {index: 3, navShow: true, keepAlive: true}
		},
		{
			path: '/change',
			name: 'ChangeDetails',
			component: ChangeDetails,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/login',
			name: 'LoginPage',
			component: LoginPage,
			meta: {index: 2, navShow: false, keepAlive: true,headOption: {ishide: true,showBack: false}}
		},
		{
			path: '/webview',
			name: 'WebViewBox',
			component: WebViewBox,
			meta: {index: 4, navShow: false, keepAlive: true,headOption: {ishide: true,showBack: false}}
		},
		{
			path: '/bind',
			name: 'BindAccount',
			component: BindAccount,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/release',
			name: 'ReleaseOrder',
			component: ReleaseOrder,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/history',
			name: 'HistoryList',
			component: HistoryList,
			meta: {index: 4, navShow: true, keepAlive: true}
		},
		{
			path: '/apply-train',
			name: 'ApplyCourses',
			component: ApplyCourses,
			meta: {index: 5, navShow: true, keepAlive: false}
		},
		{
			path: '/agreement',
			name: 'AgreementPage',
			component: AgreementPage,
			meta: {index: 5, navShow: false, keepAlive: false}
		},
		{
			path: '/account',
			name: 'AccountPage',
			component: AccountPage,
			meta: {index: 3, navShow: false, keepAlive: true}
		},
		{
			path: '/order',
			name: 'Order',
			component: Order,
			meta: {index: 3, navShow: true, keepAlive: false}
		},
		{
			path: '/settle',
			name: 'Settlement',
			component: Settlement,
			meta: {index: 4, navShow: false, keepAlive: false}
		},
		{
			path: '/settle-one',
			name: 'SettlementOne',
			component: SettlementOne,
			meta: {index: 4, navShow: false, keepAlive: false}
		},
	]
})