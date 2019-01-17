/*
通过mutation间接更新state的多个方法的对象
 */
// import {
//   RECEIVE_TOKEN,
//   RECEIVE_USERMESS,
//   RECEIVE_USERINFO,
// } from './mutation-types'
// import {
//   reqAddress,
//   reqFoodCategorys,
//   reqShops,
//   reqUserInfo,
//   reqLogout,
//   reqShopRatings,
//   reqShopGoods,
//   reqShopInfo,
//   reqSearchShop
// } from '../api'

import {post,fetch,patch,put} from '../utils/http'


export default {
	actionSetToken ({commit}) {
		commit('localToken')
	},
	
	setUserInfo ({commit},userInfo) {
		commit('set', {userInfo})
	},
  
  async getUserMess({commit, state}) {
		fetch("/api/hpp_mobile_user/get/"+state.userInfo.mobile).then((res) => {
			// console.log(res)
			if (res.statusCode == '0000') {
				const userMess = res.data
				localStorage.setItem('userMess',JSON.stringify(res.data))
				commit('setUserMess', userMess)
			}
		})

  },
	async getNoticeNum({commit, state}) {
			fetch("/api/hpp_notice_sign/unreadNum/"+state.userInfo.mobile).then((res) => {
				// console.log(res)
				if (res.statusCode == '0000') {
					commit('setNoticeNum', res.data.unreadNum)
				}
			})
	
	  },

//   // 异步获取食品分类列表
//   async getCategorys({commit}) {
//     // 发送异步ajax请求
//     const result = await reqFoodCategorys()
//     // 提交一个mutation
//     if (result.code === 0) {
//       const categorys = result.data
//       commit(RECEIVE_CATEGORYS, {categorys})
//     }
//   },
// 
//   // 异步获取商家列表
//   async getShops({commit, state}) {
//     // 发送异步ajax请求
//     const {longitude, latitude} = state
//     const result = await reqShops(longitude, latitude)
//     // 提交一个mutation
//     if (result.code === 0) {
//       const shops = result.data
//       commit(RECEIVE_SHOPS, {shops})
//     }
//   },
// 
//   // 同步记录用户信息
//   recordUser({commit}, userInfo) {
//     commit(RECEIVE_USER_INFO, {userInfo})
//   },
// 
//   // 异步获取用户信息
//   async getUserInfo({commit}) {
//     const result = await reqUserInfo()
//     if (result.code === 0) {
//       const userInfo = result.data
//       commit(RECEIVE_USER_INFO, {userInfo})
//     }
//   },
// 
//   // 异步登出
//   async logout({commit}) {
//     const result = await reqLogout()
//     if (result.code === 0) {
//       commit(RESET_USER_INFO)
//     }
//   },
// 
//   // 异步获取商家信息
//   async getShopInfo({commit}) {
//     const result = await reqShopInfo()
//     if (result.code === 0) {
//       const info = result.data
//       commit(RECEIVE_INFO, {info})
//     }
//   },
// 
//   // 异步获取商家评价列表
//   async getShopRatings({commit}, callback) {
//     const result = await reqShopRatings()
//     if (result.code === 0) {
//       const ratings = result.data
//       commit(RECEIVE_RATINGS, {ratings})
//       // 数据更新了, 通知一下组件
//       callback && callback()
//     }
//   },
// 
//   // 异步获取商家商品列表
//   async getShopGoods({commit}, callback) {
//     const result = await reqShopGoods()
//     if (result.code === 0) {
//       const goods = result.data
//       commit(RECEIVE_GOODS, {goods})
//       // 数据更新了, 通知一下组件
//       callback && callback()
//     }
//   },
// 
//   // 同步更新food中的count值
//   updateFoodCount({commit}, {isAdd, food}) {
//     if (isAdd) {
//       commit(INCREMENT_FOOD_COUNT, {food})
//     } else {
//       commit(DECREMENT_FOOD_COUNT, {food})
//     }
//   },
// 
//   // 同步清空购物车
//   clearCart({commit}) {
//     commit(CLEAR_CART)
//   },
// 
//   // 异步获取商家商品列表
//   async searchShops({commit, state}, keyword) {
// 
//     const geohash = state.latitude + ',' + state.longitude
//     const result = await reqSearchShop(geohash, keyword)
//     if (result.code === 0) {
//       const searchShops = result.data
//       commit(RECEIVE_SEARCH_SHOPS, {searchShops})
//     }
//   },
}