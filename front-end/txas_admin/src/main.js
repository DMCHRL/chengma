// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import './element-ui/index.css';
import './assets/css/common.css';


import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';

import axios from './until/http.js'
import until from './until/until.js'

import Vuex from 'vuex';
import store from './store/store.js'


import Pagination from "@/components/common/Pagination"
import FilterWork from "@/components/common/FilterWork"

Vue.component('pagination', Pagination)
Vue.component('filter-work', FilterWork)


Vue.use(Vuex);
Vue.use(ElementUI);
Vue.prototype.$axios = axios;
Vue.prototype.$until = until;

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
