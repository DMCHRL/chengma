// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import './element-ui/index.css';
import './assets/css/common.css';


import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import Vuex from 'vuex';
Vue.use(Vuex);
Vue.use(ElementUI);
import store from '@/store/store'

import Pagination from "@/components/common/Pagination"
import FilterWork from "@/components/common/FilterWork"

Vue.component('pagination', Pagination)
Vue.component('filter-work', FilterWork)

import {post,fetch} from '@/until/until';
Vue.prototype.fetch=fetch;
Vue.prototype.post=post;


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
