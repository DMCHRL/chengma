<template>
    <div class="page_box">
        <fund-header :leftOptions="headOption"></fund-header>

        <div class="body_box box_pt150" id="my-box">
            <div class="con_box">
                <ul>
                    <template v-for="(items,indexs) in list">
                        <li class="item_box">
                            <swipeout>

                                <swipeout-item transition-mode="follow">
                                    <div slot="content" class="mess_box">
                                        <div class="flex_bet">
                                            <span class="title">{{item.type}}</span>
                                            <span class="time"> {{item.sendTime}}"</span>
                                        </div>
                                        <div class="text_box">
                                            <p>{{item.context}}</p>
                                        </div>

                                    </div>
                                    <div slot="right-menu">
                                        <swipeout-button type="warn">
                                            <div @click="Delete(item.signId,index,items.arr,indexs)">
                                                删除
                                            </div>
                                        </swipeout-button>
                                    </div>
                                </swipeout-item>


                            </swipeout>

                        </li>
                    </template>

                </ul>

                <div v-transfer-dom class="weui-mask" v-show="Show">
                    <div class="weui-dialog">
                        <div class="dialog_icon_box">
                            <!-- <img src="../../assets/img/mess_04.png" /> -->
                        </div>
                        <div class="dialog_cancel_box" @click="Show = false">
                            <icon type="cancel"></icon>
                        </div>
                        <div class="dialog_text">
                            <h3>{{notice.type}}</h3>
                            <p class="time">{{notice.sendTime}}</p>
                            <p>{{notice.context}}</p>
                        </div>

                    </div>
                </div>

                <!-- <spinner-loading v-show="isloading">
			<p slot='text'>努力加载中...</p>
		</spinner-loading> -->

                <!-- <load-more></load-more> -->

                <div class="trip_box">
                    <spinner v-show="isloading" :type="'ios'" size='30px'></spinner>

                    <p v-show="notrip">暂无消息！</p>
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
        Swipeout,
        SwipeoutItem,
        SwipeoutButton,
        XButton,
        Icon,
        TransferDomDirective as TransferDom
    } from 'vux'
    export default {
        directives: {
            TransferDom
        },
        data() {
            return {
                headOption: {
                    title: '消息通知',
                    backText: '',
                    showBack: true,
                    text: '重要消息，不容错过'
                },
                initList: [],
                list: [],
                isloading: false,
                notrip: false,
                Show: false,
                notice: {
                    msgType: '策略跟单',
                    type: '',
                    context: '',
                    sendTime: ''
                }
            }
        },
        filters: {
            contextFilter: function(value) {
                if (value.length > 36) {
                    return value.slice(0, 36) + '...';
                } else {
                    return value;
                }
            }
        },
        computed: {
            ...mapState(['userInfo']),
        },
        components: {
            Swipeout,
            SwipeoutItem,
            SwipeoutButton,
            XButton,
            Icon,
        },
        methods: {
            ...mapActions(['getNoticeNum']),
            showDetails(data) {
                let _this = this;
                data.status = 'Y';
                _this.notice = data;
                _this.Show = true;
                _this.$fetch("/api/hpp_notice_sign/readOne/" + data.signId).then()
                _this.getNoticeNum();
            },
            Delete(id, index, arr, indexs) {
            	let _this = this;
            	_this.$fetch("/api/notice_sign/delete/" + id).then(res => {
            		arr.splice(index, 1);
            		if (arr.length <= 0) {
            			_this.list.splice(indexs, 1);
            		}
            		if (_this.list.length <= 0) {
            			_this.notrip = true
            		}
            	});
            },
            quchong(arr) {
                var len = arr.length;
                arr.sort();
                for (var i = len - 1; i > 0; i--) {
                    if (arr[i] == arr[i - 1]) {
                        arr.splice(i, 1);
                    }
                }
                return arr;
            },
            reloadArray(arr) {
                let _this = this;
                let timeArr = [];
                arr.forEach((item) => {
                    timeArr.push(item.sendTime.slice(0, 10))
                })
                timeArr = _this.quchong(timeArr);

                let list = [];
                timeArr.forEach(it => {
                    let res = {};
                    res.day = it;
                    res.arr = arr.filter((item) => {
                        return item.sendTime.slice(0, 10) == it;
                    })
                    list.push(res);
                })

                _this.list = list.reverse();
            },
            initPage() {
                let _this = this;
                let timer = setTimeout(function() {
                    _this.isloading = true;
                }, 1000)
                _this.$fetch("/api/notice_sign/read/" + _this.userInfo.mobile).then((res) => {
                    console.log(res)
                    clearTimeout(timer)
                    _this.isloading = false;
                    _this.initList = res.data.list;
                    _this.reloadArray(res.data.list);
                    if (res.data.list.length) {
                        _this.notrip = false
                    } else {
                        _this.notrip = true
                    }
                })
            }
        },
        activated() {
            this.initPage();
        }
    }
</script>

<style scoped>
    .mess_box {
        background-color: #f4f5ff;
        padding: 0.266rem;
        border-bottom: 2px solid #fff;
    }

    .mess_box span.title {
        font-size: 0.373rem;
        color: #202966;
        font-weight: bold;
    }

    .mess_box span.title::before {
        content: '';
        display: inline-block;
        width: 6px;
        height: 6px;
        background-color: #202966;
        border-radius: 6px;
        position: relative;
        top: -4px;
        margin-right: 6px;
    }

    .mess_box span.time {
        font-size: 0.32rem;
        color: #888888;
    }

    .text_box p {
        font-size: 0.32rem;
        color: #202966;
    }


    .text_box span {
        font-size: 0.3466rem;
        color: #666;
    }

    .time_box {
        padding: 0 0.2666rem;
    }


    .weui-dialog {
        overflow: auto;
        padding-top: 0.8666rem;
    }

    .dialog_icon_box {
        position: absolute;
        top: -0.7rem;
        width: 1.4rem;
        left: 0;
        right: 0;
        margin: 0 auto;
    }

    .dialog_icon_box img {
        width: 100%;
    }

    .dialog_cancel_box {
        position: absolute;
        top: 0.2rem;
        right: 0.15rem;
        z-index: 50001;
    }

    .weui-icon-cancel {
        color: #666;
    }

    .dialog_text {
        padding: 0.2666rem;
        max-height: 450px;
        overflow-y: scroll;
    }

    .dialog_text p {
        text-align: left;
        text-indent: 2em;
        color: #666666;
        font-size: 0.3466rem;
        line-height: 0.6rem;
    }

    .dialog_text h3 {
        font-size: 0.5rem;
        color: #333;
        font-weight: normal;
    }

    .dialog_text p.time {
        color: #999;
        font-size: 0.33rem;
        line-height: 0.8rem;
        text-align: right;
    }

    .dialog_btn_box {
        overflow: hidden;
        border-radius: 0 0 0.0666rem 0.0666rem;
    }

    .dialog_btn_box button {
        background-color: #ff5752;
        width: 49.8%;
        line-height: 1.0666rem;
        color: #fff;
    }
</style>
