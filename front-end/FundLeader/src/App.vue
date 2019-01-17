<template>
    <div id="app" :style="{backgroundImage:url_back_img}">
        <view-box ref="viewBox" :body-padding-top="0">

            <!--<x-header slot="header" style="width:100%;position:absolute;left:0;top:0;z-index:100;"></x-header>-->
            <!-- <my-header :leftOptions="$route.meta.headOption" slot="header"></my-header> -->
            <!-- <transition name="fade"> -->
            <keep-alive>
                <router-view v-if="$route.meta.keepAlive" class="router-view"></router-view>
            </keep-alive>
            <!-- </transition> -->
            <!-- <transition :name="transitionRight"> -->
            <router-view v-if="!$route.meta.keepAlive" class="router-view"></router-view>
            <!-- </transition> -->
            <my-footer v-if="$route.meta.navShow" :routerName="$route.name" slot="bottom"></my-footer>
        </view-box>


        <div v-transfer-dom>
            <confirm v-model="upShow" :title="'发现新版本'" theme="android" @on-cancel="onCancel" @on-confirm="onConfirm">
                <p style="text-align:center;">{{upText}}</p>
            </confirm>
        </div>

    </div>
</template>

<script>
    import {
        mapState
    } from 'vuex'
    import MyHeader from "./components/common/Header"
    import MyFooter from "./components/common/Footer"
    import {
        ViewBox,
        XHeader,
        Confirm,
        TransferDomDirective as TransferDom
    } from 'vux'
    import axios from 'axios'
    import {
        updateLink,
        _api_key,
        appKey
    } from './utils/util'
    export default {
        name: 'App',
        directives: {
            TransferDom
        },
        components: {
            MyHeader,
            MyFooter,
            ViewBox,
            XHeader,
            Confirm
        },
        data() {
            return {
                upShow: false,
                upText: '',
                transitionName: 'slide-right',
                transitionRight: 'slide-right',
                url_back_img: "url(" + require('./assets/img/f_back_01.png') + ")",
                version: null
            }
        },
        computed: {
            ...mapState(['userInfo']),
        },
        watch: { //使用watch 监听$router的变化
            // 			$route(to, from) {
            // 				//如果to的索引值为0，不添加任何动画；如果to索引大于from索引,判断为前进状态,反之则为后退状态
            // 				if (to.meta.index > 0) {
            // 					if (to.meta.index < from.meta.index) {
            // 						this.transitionName = 'slide-right';
            // 					} else {
            // 						this.transitionName = 'slide-left';
            // 					}
            // 				} else if (to.meta.index == 0 && from.meta.index > 0) {
            // 					this.transitionName = 'slide-right';
            // 				}
            // 
            // 				//当然，如果你没有需要设置索引值为0的页面可以直接用着一段
            // 				/*if( to.meta.index < from.meta.index){
            // 							this.transitionName = 'slide-right';
            // 				}else{
            // 							this.transitionName = 'slide-left';
            // 				}*/
            // 			}
        },
        methods: {

            onCancel() {

            },
            onConfirm() {
                plus.runtime.openURL(updateLink);
            },

            checkIsNew() { //检查app新版本
                let _this = this;
                let url = 'https://www.pgyer.com/apiv2/app/check';
                let fd = new FormData();
                fd.append('_api_key', _api_key);
                fd.append('appKey', appKey);

                axios.post(url, fd, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(response => {


                    let buildUpdateDescription = response.data.data.buildUpdateDescription;
                    let buildVersion = response.data.data.buildVersion;
                    let upUrl = response.data.data.downloadURL;

                    if (_this.version) {
                        if (_this.version < buildVersion) {
                            _this.upText = buildUpdateDescription;
                            _this.upShow = true;
                        }
                    }

                }).catch(error => {
                    console.log(error);
                });

            }
        },
        mounted() {
            let _this = this;
            mui.plusReady(function() {
                _this.version = plus.runtime.version;
                _this.checkIsNew();
            });
        }
    }
</script>

<style scoped>
    #app {
        width: 100%;
        height: 100%;
        background-repeat: no-repeat;
        background-size: 100%;
        background-color: #fff;
    }

    .slide-right-enter-active,
    .slide-right-leave-active,
    .slide-left-enter-active,
    .slide-left-leave-active {
        will-change: transform;
        transition: all .2s;
        position: absolute;
        width: 100%;
        left: 0;
    }

    .slide-right-enter {
        transform: translateX(-100%);
    }

    .slide-right-leave-active {
        transform: translateX(100%);
    }

    .slide-left-enter {
        transform: translateX(100%);
    }

    .slide-left-leave-active {
        transform: translateX(-100%);
    }


    .fade-enter-active,
    .fade-leave-active {
        transition: opacity .3s;
    }

    .fade-enter,
    .fade-leave-to

    /* .fade-leave-active below version 2.1.8 */
        {
        opacity: 0;
    }
</style>
