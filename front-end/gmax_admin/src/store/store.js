import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router/index.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    token: null
  },
  mutations: {
    setToken (state,userToken) {
      state.token = userToken;
    },
    localToken (state) {
      let token = localStorage.getItem("id_token");
      state.token = token;
    } 
  },
  actions: {
    actionSetToken ({commit}) {
      commit('localToken')
    }
  }
})
export default store