// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'

import './element-ui/index.css';
import './assets/css/common.css';

import axios from 'axios'
Vue.prototype.$axios = axios;

import {post,fetch,strike,postForExcel} from './utils/http'
Vue.prototype.$post=post;
Vue.prototype.$fetch=fetch;//get
Vue.prototype.$strike=strike;//delete
Vue.prototype.$postForExcel=postForExcel;//postForExcel

import util from './utils/util.js'
Vue.prototype.$until = util;

import Vuex from 'vuex';
import store from './store/store.js'
Vue.prototype.$store = store;

import Blob from './excel/Blob.js'
import Export2Excel from './excel/Export2Excel.js'


Vue.use(Vuex);
Vue.use(ElementUI);

Vue.config.productionTip = false;
Vue.config.debug = true;


/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
