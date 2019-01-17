<template>
    <div class="page_box">
        <my-header :leftOptions="headOption"></my-header>

        <div class="body_box box_pb0" id="my-box">

            <div class="con_box">

                <div class="sec_1">

                    <div class="item_box flex_bet flex_align_center" @click="toVerified">
                        <div class="left_box">
                            <i class="iconfont icon-right-bottom-triangle"></i>
                            <span>实名认证</span>
                        </div>
                        <div class="right_box">
                            <span v-if="userMess.status == 'AUTHORIZED'" class="green">已实名认证</span>
                            <span v-if="userMess.status == 'APPLYING'" class="yellow">认证审核中...</span>
                            <span v-if="userMess.status == 'UNAUTHORIZED'">未认证，点击认证</span>
                            <i class="iconfont icon-xiangyou"></i>
                        </div>
                    </div>

                    <div class="item_box flex_bet flex_align_center">
                        <div class="left_box">
                            <i class="iconfont icon-right-bottom-triangle"></i>
                            <span>我的手机</span>
                        </div>
                        <div class="right_box">
                            <span>{{userMess.mobile | formattPhone}}</span>
                            <i class="iconfont icon-xiangyou"></i>
                        </div>
                    </div>

                    <router-link to="/bindemail">
                        <div class="item_box flex_bet flex_align_center">
                            <div class="left_box">
                                <i class="iconfont icon-right-bottom-triangle"></i>
                                <span>我的邮箱</span>
                            </div>
                            <div class="right_box">
                                <span v-if="userMess.email">{{userMess.email}}</span>
                                <span v-else>未绑定，点击绑定</span>
                                <i class="iconfont icon-xiangyou"></i>
                            </div>
                        </div>
                    </router-link>

                    <div class="item_box flex_bet flex_align_center" @click="toPayPass">
                        <div class="left_box">
                            <i class="iconfont icon-right-bottom-triangle"></i>
                            <span>支付密码</span>
                        </div>
                        <div class="right_box">
                            <span v-if="userMess.password">已设置，点击修改</span>
                            <span v-else>未设置，点击设置</span>
                            <i class="iconfont icon-xiangyou"></i>
                        </div>
                    </div>

                    <router-link to="/mybankcard">
                        <div class="item_box flex_bet flex_align_center">
                            <div class="left_box">
                                <i class="iconfont icon-right-bottom-triangle"></i>
                                <span>我的银行卡</span>
                            </div>
                            <div class="right_box">
                                <i class="iconfont icon-xiangyou"></i>
                            </div>
                        </div>
                    </router-link>

                    <!-- <div class="item_box flex_bet flex_align_center" @click="signOut">
            <div class="left_box">
              <i class="iconfont icon-right-bottom-triangle"></i>
              <span>退出登录</span>
            </div>
            <div class="right_box">
              <i class="iconfont icon-xiangyou"></i>
            </div>
          </div> -->


                </div>

                <div class="signout_btn">
                    <button @click="signOut">退出登录</button>
                </div>

            </div>


        </div>

    </div>
</template>

<script>
    import {
        mapState,
        mapActions
    } from 'vuex'
    import {
        formatMobile
    } from '@/utils/util'
    export default {
        directives: {},
        data() {
            return {
                headOption: {
                    title: '账户与安全',
                    backText: '',
                    showBack: true,
                    text: '为了您的账户安全，建议您完善以下设置'
                },
                subbtn: {
                    text: '退出登录',
                    disabled: false
                },
            }
        },
        filters: {
            formattPhone: function(value) {
                return formatMobile(value)
            },
        },
        computed: {
            ...mapState(['userMess']),
        },
        components: {

        },
        methods: {
            ...mapActions(['getUserMess']),
            signOut() {
                localStorage.clear();
                this.$store.commit("setToken",'');
                this.$store.commit("setUserInfo",{});
                this.$router.replace({
                    path: '/login'
                })
            },
            toVerified() {
                if (this.userMess.status == 'UNAUTHORIZED') {
                    this.$router.push({
                        path: '/verified'
                    })
                }

            },
            toPayPass() {
                // if (this.userMess.password == null) {
                this.$router.push({
                    path: '/paypass'
                })
                // }
            }

        },
        activated() {
            this.getUserMess();
        }
    }
</script>

<style scoped>
    .con_box {
        background-color: #f8f9fd;
        position: relative;
    }

    .item_box {
        margin: 0 0.346rem;
        border-bottom: 1px solid #ededed;
        height: 1.2rem;
    }

    .left_box span {
        font-size: 0.373rem;
        color: #28347e;
    }

    .left_box i {
        color: #fe9f02;
        font-size: .3rem;
        margin-right: 0.1rem;
    }

    .right_box span {
        color: #888;
        font-size: 0.32rem;
    }

    .right_box span.yellow {
        color: #fe9f02;
    }

    .right_box span.green {
        color: green;
    }

    .right_box i {
        color: #888;
        font-size: .3rem;
        margin-left: 0.1rem;
    }

    .right_box img {
        border-radius: 50%;
        width: .8rem;
        height: .8rem;
    }

    .signout_btn {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 50px 60px;
    }

    .signout_btn button {
        font-size: .34rem;
        color: #fff;
        padding: 8px 30px;
        background-color: #202a67;
        width: 100%;
    }
</style>
