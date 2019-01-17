<template>
	<div class="content_box">
        
		<div class="mine_top backimg6">
			<x-header :left-options="headOption">{{headOption.title}}</x-header>
			<div class="m_text">
				<p>当前积分</p>
				<h3>{{mess.balance}}</h3>
				<div class="right_box">
					<router-link to="/agreement?type=POINT_COMMON&name=积分说明">
						<span>积分说明</span>
					</router-link>
				</div>
			</div>
		</div>
        
        
		<div class="sec_1">
            <div class="con_box">
                <div class="title_box clearfix">
                	<h3>积分兑换</h3>
                	<router-link to="/change">
                		<span class="dui_span">积分明细</span>
                	</router-link>
                </div>
                <div class="list_body">
                    
                    <template v-for="item in list">
                        <div class="item_box flex_bet">
                            <div class="img_box " @click="linkToVideo(item)">
                                <img :src="item.img"/>
                            </div>
                            <div class="text_box flex_one" @click="linkToVideo(item)">
                                <p>{{item.type | typeOfName}}</p>
                                <h3>{{item.name}}</h3>
                                <span>已{{item.exchangeNum}}人兑换</span>
                            </div>
                            <div class="btn_box flex_col">
                                <span class="price">{{item.price}} 积分</span>
                                <button v-if="item.status == 'Y'" type="primary">兑换成功</button>
                                <button v-else class="myback" @click="exchangeProduct(item)">立即兑换</button>
                            </div>
                        </div>
                    </template>
                    
                    <div v-show="isloading"  class="trip_box">
                    	<spinner :type="'ios'" size='30px'></spinner>
                    </div>
                    
                    <div class="trip_box" v-show="notrip">
                    	<img src="../../assets/img/no_1.png"/>
                    	<p>暂无数据</p>
                    </div>
                	<p class="trip_p" v-show="!notrip">没有更多了~~</p>
                </div>
            </div>
			
		</div>
        
        
	</div>
</template>

<script>
	import {mapState} from 'vuex'
	import { XHeader } from 'vux'
	export default {
		data() {
			return {
				headOption: {backText: '',	showBack: true,	title: '积分钱包'},
				mess: {
					balance: 0
				},
				list: [],
                headShow: false,
                isloading: false,
                notrip: false
			}
		},
		filters: {
			typeOfName: function(value) {
				switch (value){
					case 'video':
						return '交易战法视频'
						break;
					case 'course':
						return '交易课程'
						break;
					case 'exam':
						return '交易师考证'
						break;
					default:
						break;
				}
			}
		},
		computed: {
			...mapState(['userInfo']),
		},
		components: {
		    XHeader
		},
		methods: {
            linkToVideo (item) {
                if (item.type == 'video') {
                    this.$router.push({
                        path: '/vdetails?id='+item.id
                    })
                }
            },
			exchangeProduct (obj) {
				let _this = this;
				let datas = {
					body: obj.type,//	是	string	兑换类别
					id: obj.id, //	是	string	id
					payType: 'integral'//string	integral(积分兑换) or weChat(微信支付)
				};
				_this.$post("/api/pay/createOrder",datas).then( res => {
					// console.log(res)
					if (res.statusCode == '0000') {
						_this.getUserPoint();
						_this.$vux.toast.text('兑换成功','middle')
						_this.$router.push({
							path: '/change'
						})
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
					}
				})
			},
			getUserPoint () {
				let _this = this;
				_this.$fetch("/api/hpp_integral/get/"+_this.userInfo.mobile).then((res) => {
					// console.log(res)
					if (res.data) {
						_this.mess = res.data;
					}
				})
			},
			initPage () {
				let _this = this;
                _this.isloading = true;
				_this.$fetch("/api/hpp_integral/findList").then((res) => {
					//console.log(res)
                    _this.isloading = false;
					if (res.data) {
						_this.list = res.data.list;
                        if (res.data.list) {
                            _this.notrip = false;
                        }else {
                            _this.notrip = true;
                        }
					}
				})
			}
		},
		activated () {
			this.getUserPoint();
			this.initPage();
		}
	}
</script>

<style scoped>
	.mine_top {
        position: absolute;
        width: 100%;
        top: 0;
        left: 0;
        z-index: 99;
		box-sizing: border-box;
		height: 272px;
		padding-top: 30px;
		background-size: 100% 100%;
	}
	.m_text{
		position: relative;
		width: 100%;
		height: 200px;
		text-align: center;
		box-sizing: border-box;
		padding-top: 62px;
	}
	.m_text p {
		font-size: 0.4rem;
		color: #fff;
	}
	.m_text h3 {
		font-size: 1.1733rem;
		color: #fff;
		font-weight: normal;
	}
	.right_box {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	.right_box span {
		color: #eb0021;
		font-size: 0.2933rem;
		background-color: #fff;
		display: inline-block;
		padding: 0.1333rem 0.6666rem;
		border-radius: 0.3666rem 0 0 0.3666rem;
	}
	
	.sec_1 {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
		padding: 0.1333rem;
        padding-top: 272px;
        padding-bottom: 50px;
	}
    
    .con_box {
        height: 100%;
        background-color: #fff;
        border-radius: 10px;
        margin-bottom: 0.2666rem;
    }
    .list_body {
        height: 100%;
        padding-bottom: 50px;
        overflow-y: scroll;
    }
    
	.title_box {
		text-align: center;
		position: relative;
	}
	.title_box h3 {
		font-size: 0.4266rem;
		color: #eb0021;
		line-height: 1rem;
	}
	.title_box span.dui_span {
		line-height: 0.6666rem;
		color: #eb0021;
		position: absolute;
		top: 10px;
		right: 10px;
	}
	.item_box {
		padding: 0.1333rem;
	}
	.item_box .img_box{
		width: 2.2rem;
        height: 2rem;
		margin-right: 0.2666rem;
        border-radius: 8px;
        overflow: hidden;
	}
	.item_box p {
		font-size: 0.2933rem;
		color: #333;
		line-height: 0.7rem;
	}
	.item_box h3 {
		font-size: 0.35rem;
		color: #333;
	}
	.item_box span {
		font-size: 0.25rem;
		color: #999;
		line-height: 0.8rem;
	}
	.item_box span.price {
		font-size: 0.3rem;
		color: #e51434;
        text-align: center;
	}
	.item_box button {
		font-size: 0.2666rem;
		color: #fff;
		border-radius: 0.4rem;
		padding: 0.1rem 0.4rem;
	}
    .btn_box {
        padding-top: 10px;
    }
</style>