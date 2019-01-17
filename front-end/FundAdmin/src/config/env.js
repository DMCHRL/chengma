/**
 * 配置编译环境和线上环境之间的切换
 * 
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * baseImgPath: 图片存放地址
 * 
 */
// let baseUrl = 'http://192.168.1.135:8090/lhfz';
let baseUrl = ''; 
let routerMode = 'history';
let baseImgPath = '../assets/img/';

console.log(process.env.NODE_ENV)
if (process.env.NODE_ENV == 'development') {
    // baseUrl = 'http://192.168.1.135:8090/lhfz';
    baseUrl = 'http://47.52.199.109:8083/lhfz';
}else{
	baseUrl = 'http://47.52.199.109:8083/lhfz';
}

export {
	baseUrl,
	routerMode,
	baseImgPath
}