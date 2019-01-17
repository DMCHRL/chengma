import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    token: ''
  },
  mutations: {
    setToken (state,userToken) {
      state.token = userToken;
    },
    localToken (state) {
      state.token = localStorage.getItem("id_token");
    } 
  },
  actions: {
    actionSetToken ({commit}) {
      commit('localToken')
    }
  }
})
export default store