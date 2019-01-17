import axios from 'axios';
import store from "../store/store.js"
import router from '../router/index.js'
import util from './util.js'
import { Loading, Message } from 'element-ui'

//hui_admin
let timer = null;
let loadingInstance = null;

const instance = axios.create({
	baseURL: util.host,
	//timeout: 5000,
	withCredentials: true, //携带cookie，验证码验证
	headers: {
		'Content-Type': 'application/json; charset=utf-8'
	}
});

// 引入instance以及element ui中的loading和message组件
// http request 拦截器
instance.interceptors.request.use(
	config => {
		
		timer = setTimeout(function () {
			loadingInstance = Loading.service({
				lock: true,
	      text: 'Loading',
	      spinner: 'el-icon-loading',
				background: 'rgba(255, 255, 255, 0)'
			});
		},1000)
		
		if(!store.state.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
			store.dispatch('actionSetToken');
		}
		config.headers.Authorization = `Bearer ${store.state.token}`;
		return config;
	},
	err => {
		return Promise.reject(err);
	});

// http response 拦截器
instance.interceptors.response.use(
	response => {
		
		clearTimeout(timer);
		setTimeout(function () {
			clearTimeout(timer);
			if (loadingInstance) {
				loadingInstance.close();
			}
		},2000)
	  

		if(response == undefined) {
			Message.error({
				message: '服务升级中...'
			})
			return;
		}
		return response;
	},
	error => {
		clearTimeout(timer);
		setTimeout(function () {
			clearTimeout(timer);
			if (loadingInstance) {
				loadingInstance.close();
			}
		},2000)
		
		//console.log(error)
		if(error.response) {
			switch(error.response.status) {
				case 401:
					// 返回 401 清除token信息并跳转到登录页面
					store.commit("setToken", "");
					localStorage.removeItem("user_token");
					Message.error({
						message: '登录失效'
					})
					router.replace({
						path: '/login',
					})
					break;
				case 404:
					console.log('404')
					break;
				case 500:
					console.log('500')
					break;
				default:
					Message.error({
						message: '服务升级中...'
					})
					console.log("网络错误")
			}
		}
		return error.response // 返回接口返回的错误信息
	});

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
 * 封装delete方法
 * @param url
 * @param data
 * @returns {Promise}
 */

export function strike(url, params = {}) {
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

/**
 * 封装post请求,到处excel
 * @param url
 * @param data,
 * @fileName 导出文件名
 * @returns {Promise}
 */

export function postForExcel(url, data = {},fileName) {
	return new Promise((resolve, reject) => {
		
		if(!store.state.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
			store.dispatch('actionSetToken');
		}
		
		axios({
					method:'post',
					url: util.host + url,
					headers:{
						'Authorization': 'Bearer '+ store.state.token
					},
					responseType: 'arraybuffer',
					data: data
			})
			.then((res) => {
				//console.log(res);
					let blob = new Blob([res.data],{type:"application/vnd.ms-excel"});
					let obj = URL.createObjectURL(blob);
					let a = document.createElement('a');   // 转换完成，创建一个a标签用于下载
					
					let myDate = new Date();
					let today = myDate.getFullYear() +'-'+ (myDate.getMonth()+1) +'-'+ myDate.getDate();
					
					a.download = today+fileName+'.xls';
					a.href = obj;
					$("body").append(a);    // 修复firefox中无法触发click
					a.click();
					$(a).remove();
					/*console.log(window.navigator.msSaveBlob);
					window.navigator.msSaveOrOpenBlob(blob, 'aa.xls');
					console.log(obj);*/
					//window.location.href = obj;
			})
			.catch(function (err) {
					console.log('导出失败',err);
			});
			
	})
}