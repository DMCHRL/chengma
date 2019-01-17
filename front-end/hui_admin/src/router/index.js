import Vue from 'vue'
import Router from 'vue-router'

import ContentBox from '@/components/ContentBox'
import LoginPage from '@/components/LoginPage'
import RegisterPage from '@/components/RegisterPage'
import HomePage from '@/components/HomePage'

//hui任务
import ApplicationTask from '@/components/task/ApplicationTask'
import StrategyTask from '@/components/task/StrategyTask'
import FollowTask from '@/components/task/FollowTask'

//hui用户
import BrokerWork from '@/components/user/BrokerWork'
import AppUsers from '@/components/user/AppUsers'
import IntegralDetail from '@/components/user/IntegralDetail'


import NewsList from '@/components/news/NewsList'
import AddNews from '@/components/news/AddNews'



import RecordPage from '@/components/record/RecordPage'
import PositionRecord from '@/components/record/PositionRecord'

import UserMessage from '@/components/other/UserMessage'
import GetCustomers from '@/components/other/GetCustomers'


//汇拼拼
import OnlineVideo from '@/components/online/OnlineVideo'
import VideoList from '@/components/online/VideoList'

import TrainingCourses from '@/components/train/TrainingCourses'
import ApplyTraining from '@/components/train/ApplyTraining'
import SaveTraining from '@/components/train/SaveTraining'
import OrderList from '@/components/order/OrderList'
import TradeList from '@/components/strategy/TradeList'
import AccountList from '@/components/strategy/AccountList'
//考證
import ExamCourses from '@/components/exam/TrainingCourses'
import ApplyExam from '@/components/exam/ApplyTraining'
import SaveExam from '@/components/exam/SaveTraining'

import StrategyList from '@/components/strategy/StrategyList'
import ExchangerPage from '@/components/exchanger/ExchangerPage'

import BannerManagement from '@/components/banner/BannerManagement'

import ProtocolPage from '@/components/protocol/ProtocolPage'
import SaveProtocol from '@/components/protocol/SaveProtocol'

import NoticePush from '@/components/push/NoticePush'

import HomeManagement from '@/components/home/HomeManagement'

//直播管理
import LiveManage from '@/components/live/LiveManage'

//联系客服
import Contact from '@/components/contact/Contact'

Vue.use(Router)

export default new Router({
	routes: [
	{
		path: '/',
		component: ContentBox,
		children: [
      {
				path: '/',
				name: 'HomeManagement',
				component: HomeManagement
			},
			{
				path: '/video',
				name: 'OnlineVideo',
				component: OnlineVideo
			},
			{
				path: '/videolist',
				name: 'VideoList',
				component: VideoList
			},
			{
				path: '/train',
				name: 'TrainingCourses',
				component: TrainingCourses
			},
			{
				path: '/apply',
				name: 'ApplyTraining',
				component: ApplyTraining
			},
			{
				path: '/strategy',
				name: 'StrategyList',
				component: StrategyList
			},
      {
        path: '/accountList',
        name: 'accountList',
        component: AccountList
      },
			{
				path: '/exchanger',
				name: 'ExchangerPage',
				component: ExchangerPage
			},
					
				//任务
				{
					path: '/task',
					name: 'ApplicationTask',
					component: ApplicationTask
				},
				{
					path: '/task1',
					name: 'StrategyTask',
					component: StrategyTask
				},
				{
					path: '/task2',
					name: 'FollowTask',
					component: FollowTask
				},
				//用户
				{
					path: '/user',
					name: 'brokeruser',
					component: BrokerWork
				},
				{
					path: '/appuser',
					name: 'AppUsers',
					component: AppUsers
				},
        {
          path: '/integralDetail',
          name: 'IntegralDetail',
          component: IntegralDetail
        },
			 
				{
					path: '/news',
					name: 'NewsList',
					component: NewsList
				},
			{
					path: '/addnews',
					name: 'AddNews',
					component: AddNews
				},
			{
				path: '/record',
				name: 'RecordPage',
				component: RecordPage
			},
			{
				path: '/position',
				name: 'PositionRecord',
				component: PositionRecord
			},
			{
				path: '/get',
				name: 'GetCustomers',
				component: GetCustomers
			},
			{
				path: '/message',
				name: 'UserMessage',
				component: UserMessage
			},
			{
				path: '/banner',
				name: 'BannerManagement',
				component: BannerManagement
			},
			{
				path: '/protocol',
				name: 'ProtocolPage',
				component: ProtocolPage
			},
			{
				path: '/saveprotocol',
				name: 'SaveProtocol',
				component: SaveProtocol
			},
			{
				path: '/savetrain',
				name: 'SaveTraining',
				component: SaveTraining
			},
			{
				path: '/push',
				name: 'NoticePush',
				component: NoticePush
			},
			{
				path: '/examcourses',
				name: 'ExamCourses',
				component: ExamCourses
			},
			{
				path: '/applyexam',
				name: 'ApplyExam',
				component: ApplyExam
			},
			{
				path: '/saveexam',
				name: 'SaveExam',
				component: SaveExam
			},
			{
				path: '/live',
				name: 'LiveManage',
				component: LiveManage
			},
      {
        path: '/orderList',
        name: 'OrderList',
        component: OrderList
      },
      {
        path: '/tradeList',
        name: 'TradeList',
        component: TradeList
      },
			{
				path: '/contact',
				name: 'Contact',
				component: Contact
			},
		]
	},
	{
		path: '/login',
		name: 'LoginPage',
		component: LoginPage
	},
	{
		path: '/regiter',
		name: 'RegisterPage',
		component: RegisterPage
	}
	]
})
