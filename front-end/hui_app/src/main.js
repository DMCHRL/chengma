// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

import VueVideoPlayer from 'vue-video-player'
Vue.use(VueVideoPlayer)

router.afterEach((to,from,next) => {
	if (document.getElementById('hui-content')) {
		document.getElementById('hui-content').scrollTop = 0;
	}
});

import './assets/css/common.css';
import './assets/js/flexible'

import  { ToastPlugin,LoadMore,Spinner,LoadingPlugin } from 'vux'
Vue.use(ToastPlugin,{position: 'bottom',time: 3000})
Vue.use(LoadingPlugin)
Vue.component('load-more', LoadMore)
Vue.component('spinner', Spinner)


import  MyHeader from '@/components/common/Header.vue'
Vue.component('my-header', MyHeader)
//import '../static/js/mui.min'


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

Vue.prototype.setCookie=setCookie;
Vue.prototype.getCookie=getCookie;
Vue.prototype.delCookie=delCookie;

import './utils/notice'

/* eslint-disable no-new */

new Vue({
  el: '#app',
  render: h => h(App),
  router, //使用上vue-router
  store, // 使用上vuex
})
