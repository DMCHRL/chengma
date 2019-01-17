import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/home')), 'home');
const addFund = r => require.ensure([], () => r(require('@/page/addFund')), 'addFund');
const fundList = r => require.ensure([], () => r(require('@/page/fundList')), 'fundList');
const addPowerUser = r => require.ensure([], () => r(require('@/page/addPowerUser')), 'addPowerUser');
const addAdminUser = r => require.ensure([], () => r(require('@/page/addAdminUser')), 'addAdminUser');
const addAccount = r => require.ensure([], () => r(require('@/page/addAccount')), 'addAccount');
const flowRecord = r => require.ensure([], () => r(require('@/page/flowRecord')), 'flowRecord');
const accountList = r => require.ensure([], () => r(require('@/page/accountList')), 'accountList');
const receiptList = r => require.ensure([], () => r(require('@/page/receiptList')), 'receiptList');
const addReceiptAccount = r => require.ensure([], () => r(require('@/page/addReceiptAccount')), 'addReceiptAccount');
const banner = r => require.ensure([], () => r(require('@/page/banner')), 'banner');
const addBanner = r => require.ensure([], () => r(require('@/page/addBanner')), 'addBanner');
const agreement = r => require.ensure([], () => r(require('@/page/agreement')), 'agreement');
const addAgreement = r => require.ensure([], () => r(require('@/page/addAgreement')), 'addAgreement');

const userList = r => require.ensure([], () => r(require('@/page/userList')), 'userList');
const foodList = r => require.ensure([], () => r(require('@/page/foodList')), 'foodList');
const adminList = r => require.ensure([], () => r(require('@/page/adminList')), 'adminList');
const visitor = r => require.ensure([], () => r(require('@/page/visitor')), 'visitor');
const newMember = r => require.ensure([], () => r(require('@/page/newMember')), 'newMember');
const uploadImg = r => require.ensure([], () => r(require('@/page/uploadImg')), 'uploadImg');
const vueEdit = r => require.ensure([], () => r(require('@/page/vueEdit')), 'vueEdit');
const adminSet = r => require.ensure([], () => r(require('@/page/adminSet')), 'adminSet');
const sendMessage = r => require.ensure([], () => r(require('@/page/sendMessage')), 'sendMessage');
const explain = r => require.ensure([], () => r(require('@/page/explain')), 'explain');




const routes = [
	{
		path: '/',
		component: login
	},
	{
		path: '/manage',
		component: manage,
		name: '',
		children: [{
			path: '',
			component: home,
			meta: [],
		},{
			path: '/addFund',
			component: addFund,
			meta: ['基金管理', '添加基金'],
		},{
			path: '/addPowerUser',
			component: addPowerUser,
			meta: ['用户管理', '添加发起人/资产管理人'],
		},{
			path: '/addAdminUser',
			component: addAdminUser,
			meta: ['用户管理', '添加系统用户'],
		},{
			path: '/userList',
			component: userList,
			meta: ['用户管理', '用户列表'],
		},{
			path: '/flowRecord',
			component: flowRecord,
			meta: ['资金管理', '出入金记录'],
		},{
			path: '/addAccount',
			component: addAccount,
			meta: ['交易账户', '添加交易账户'],
		},{
			path: '/accountList',
			component: accountList,
			meta: ['交易账户', '账户列表'],
		},{
			path: '/receiptList',
			component: receiptList,
			meta: ['收款管理', '收款列表'],
		},{
			path: '/addReceiptAccount',
			component: addReceiptAccount,
			meta: ['收款管理', '新增收款账户'],
		},{
			path: '/fundList',
			component: fundList,
			meta: ['基金管理', '基金列表'],
		},{
			path: '/banner',
			component: banner,
			meta: ['轮播图管理', '轮播图展示'],
		},{
			path: '/addBanner',
			component: addBanner,
			meta: ['轮播图管理', '新增轮播图'],
		},{
			path: '/agreement',
			component: agreement,
			meta: ['协议管理', '协议展示'],
		},{
			path: '/addAgreement',
			component: addAgreement,
			meta: ['协议管理', '新增协议'],
		},{
			path: '/adminList',
			component: adminList,
			meta: ['数据管理', '管理员列表'],
		},{
			path: '/visitor',
			component: visitor,
			meta: ['图表', '用户分布'],
		},{
			path: '/newMember',
			component: newMember,
			meta: ['图表', '用户数据'],
		},{
			path: '/uploadImg',
			component: uploadImg,
			meta: ['文本编辑', 'MarkDown'],
		},{
			path: '/vueEdit',
			component: vueEdit,
			meta: ['编辑', '文本编辑'],
		},{
			path: '/adminSet',
			component: adminSet,
			meta: ['设置', '管理员设置'],
		},{
			path: '/sendMessage',
			component: sendMessage,
			meta: ['设置', '发送通知'],
		},{
			path: '/explain',
			component: explain,
			meta: ['说明', '说明'],
		}]
	}
]

export default new Router({
	routes,
	strict: process.env.NODE_ENV !== 'production',
})
