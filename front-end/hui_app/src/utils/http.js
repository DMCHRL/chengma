import axios from 'axios';
import router from '../router/index.js';
import store from '../store'
import { getCookie,host } from './util'; //引用刚才我们创建的util.js文件，并使用getCookie方法

//huipinpin

const instance = axios.create({
  baseURL: host,
//	timeout: 5000,
  headers: {'Content-Type': 'application/json; charset=utf-8'}
});

//http request 拦截器
instance.interceptors.request.use(
	config => {
		
		if(!store.state.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
			store.dispatch('actionSetToken');
		}
		config.headers.Authorization = `Bearer ${store.state.token}`;
		return config;
	},
	error => {
		return Promise.reject(err);
	}
);

//http response 拦截器
instance.interceptors.response.use(
	response => {
		if(response.status == 401) {
			router.replace({
				path: "/login",
				querry: {
					redirect: router.currentRoute.fullPath
				} //从哪个页面跳转
			})
			return;
		}
		return response;
	},
	error => {
		if (error.response) {
        switch (error.response.status) {
            case 401:
                // 返回 401 清除token信息并跳转到登录页面
							router.replace({
								path: "/login",
								querry: {
									redirect: router.currentRoute.fullPath
								} //从哪个页面跳转
							})
						default:
							
        }
    }else {
    	return Promise.reject(error.response.data)
    }
	}
)

/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */

export function fetch(url, params = {}) {
	return new Promise((resolve, reject) => {
		instance.get(url, {
				params: params
			})
			.then(response => {
				resolve(response.data);
			})
			.catch(err => {
				reject(err)
			})
	})
}

/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */

export function post(url, data = {}) {
	return new Promise((resolve, reject) => {
		instance.post(url, data)
			.then(response => {
				resolve(response.data);
			})
			.catch(err => {
				reject(err)
			})
			
	})
}

/**
 * 封装patch请求
 * @param url
 * @param data
 * @returns {Promise}
 */

export function patch(url, data = {}) {
	return new Promise((resolve, reject) => {
		instance.patch(url, data)
			.then(response => {
				resolve(response.data);
			})
			.catch(err => {
				reject(err)
			})
	})
}

/**
 * 封装put请求
 * @param url
 * @param data
 * @returns {Promise}
 */

export function put(url, data = {}) {
	return new Promise((resolve, reject) => {
		instance.put(url, data)
			.then(response => {
				resolve(response.data);
			})
			.catch(err => {
				reject(err)
			})
	})
}