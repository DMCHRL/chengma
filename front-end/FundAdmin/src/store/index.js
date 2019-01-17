import Vue from 'vue'
import Vuex from 'vuex'
import {setStore,getStore} from '@/config/mUtils'

Vue.use(Vuex)

const state = {
    token: null,
	adminInfo: {},
}

const mutations = {
    saveToken(state, token){
    	state.token = token;
    },
	saveAdminInfo(state, adminInfo){
		state.adminInfo = adminInfo;
	}
}

const actions = {
	async getAdminData({commit}){
		try{
			const token = getStore('f_token');
			const adminInfo = JSON.parse(getStore('f_admin_info'));
            
			if (token && adminInfo) {
				commit('saveToken', token);
				commit('saveAdminInfo', adminInfo);
			}else{
				console.error('noToken')
			}
		}catch(err){
			console.log('您尚未登陆或者session失效')
		}
	}
}

export default new Vuex.Store({
	state,
	actions,
	mutations,
})