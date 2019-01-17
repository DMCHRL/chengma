// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import './assets/css/base.css'
import './assets/css/common.css'
import './utils/notice'


import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'


router.afterEach((to,from,next) => {
	if (document.getElementById('my-box')) {
		document.getElementById('my-box').scrollTop = 0;
	}
});



import  { ToastPlugin,LoadMore,Spinner,LoadingPlugin,Icon  } from 'vux'
Vue.use(ToastPlugin,{position: 'bottom',time: 3000})
Vue.use(LoadingPlugin)
Vue.component('load-more', LoadMore)
Vue.component('spinner', Spinner)
Vue.component('icon', Icon)


import  MyHeader from '@/components/common/Header.vue'
import  FundHeader from '@/components/common/FundHeader.vue'
import  SubBtn from '@/components/common/SubBtn.vue'
Vue.component('my-header', MyHeader)
Vue.component('fund-header', FundHeader)
Vue.component('sub-btn', SubBtn)


Vue.config.productionTip = false
Vue.prototype.$devicePixelRatio = 2


//import axios from 'axios'
//Vue.prototype.$axios=axios;

import {post,fetch,patch,put} from './utils/http'
import {setCookie,getCookie,delCookie} from './utils/util'
//定义全局变量
Vue.prototype.$post=post;
Vue.prototype.$fetch=fetch;//get
Vue.prototype.$patch=patch;
Vue.prototype.$put=put;

import fetch1 from '@/utils/fetch';
Vue.prototype.fetch=fetch1;

Vue.prototype.setCookie=setCookie;
Vue.prototype.getCookie=getCookie;
Vue.prototype.delCookie=delCookie;


/* eslint-disable no-new */

new Vue({
  el: '#app',
  render: h => h(App),
  router, //使用上vue-router
  store, // 使用上vuex
})
