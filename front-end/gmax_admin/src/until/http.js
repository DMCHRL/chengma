
import store from "../store/store.js"
import router from '../router/index.js'
import axios from 'axios'
import { Loading, Message } from 'element-ui'
/**
 * http配置
 */
axios.defaults.withCredentials=true;

let timer = null;
let loadingInstance = null;

// 引入axios以及element ui中的loading和message组件
// http request 拦截器
axios.interceptors.request.use(
    config => {
//  	timer = setTimeout(function () {
//			loadingInstance = Loading.service({
//				lock: true,
//		        text: 'Loading...',
//		        spinner: 'el-icon-loading',
//				background: 'rgba(255, 255, 255, 0.3)'
//			});
//		},1000)
    	
        if (!store.state.token) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
            store.dispatch('actionSetToken');
        }
        config.headers.Authorization = `Bearer ${store.state.token}`;
        return config;
    },
    err => {
        return Promise.reject(err);
    });
 
// http response 拦截器
axios.interceptors.response.use(
    response => {
//      console.log(response)

//		clearTimeout(timer);
//		setTimeout(function () {
//			clearTimeout(timer);
//			if (loadingInstance) {
//				loadingInstance.close();
//			}
//		},2000)


		if (response.data.statusCode == undefined) {
			Message.error({
              message: '服务升级中...'
            })
			return;
		}
        return response.data;
    },
    error => {
//      console.log(error)
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    // 返回 401 清除token信息并跳转到登录页面
                    store.commit("setToken",null);
                    localStorage.clear();
                    router.replace({
                        path: '/login',
                    })
                    Message.error({
                        message: '登录失效'
                    })
                    break;
                default  :
                	Message.error({
                      message: '服务升级中...'
                    })
                    console.log("网络错误")
            }
        }
        return error.response  // 返回接口返回的错误信息
    });

export default axios