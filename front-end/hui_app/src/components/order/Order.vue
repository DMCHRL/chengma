<template>
	<div class="content_box">
		<my-header :leftOptions="headOption"></my-header>
        
        <div class="sec_1 flex_bet">
        	<div class="" @click="initFilter(0,'ALL')">
        		<span :class="swiper_index==0? 'active': ''">全部</span>
        	</div>
        	<div class="" @click="initFilter(1,'N')">
        		<span :class="swiper_index==1? 'active': ''">未付款</span>
        	</div>
        	<div class="" @click="initFilter(2,'Y')">
        		<span :class="swiper_index==2? 'active': ''">已完成</span>
        	</div>
        </div>
        
        <div class="hui_content" id="hui-content">
        
		<ul class="table_body flex_col">

			<li class="item_box" v-for="item in list">
				<div class="item_top flex_bet">
					<span class="price">{{item.status | statusLabel }}</span>
					<span>{{item.payType | payTypeLabel }}</span>
					<span>{{item.timeStart}}</span>
				</div>
				<div class="item_mid flex_bet" @click="toDetail(item)">
					<div class="img_box">
						<img :src="item.bodyImg" />
					</div>
					<div class="mid_box flex_one">
						<h4>{{item.bodyDetail}}</h4>
						<p>{{item.bodyName}}</p>
					</div>
					<div class="right_box flex_col flex_center">
						<span class="price">￥{{item.totalFee}}</span>
					</div>
				</div>
				<div class="item_bottom flex_bet">
					<span>订单号：{{item.outTradeNo}}</span>
					<button @click="Delete(item)" v-show="item.status == 'N'" type="primary">删除订单</button>
					<button v-if="item.status == 'DISABLE'" type="primary">已失效</button>
					<button v-else @click="showPay(item)" v-show="item.status == 'N'" type="primary" class="myback buy">立即付款</button>
                    
				</div>
			</li>

		</ul>
		
		
        
            <!-- <p v-show="isNoMore" class="trip_p">没有更多了~~</p> -->
            
            <div v-show="isloading"  class="trip_box">
                <spinner :type="'ios'" size='30px'></spinner>
            </div>
            
            <div v-show="notrip" class="trip_box" >
                <img src="../../assets/img/no_1.png"/>
                <p>暂无该状态订单记录</p>
            </div>
		
<!--            <div v-transfer-dom>
                <x-dialog v-model="showBox" class="dialog-demo">
                <p class="dialog-title">请选择支付方式</p>
                <div class="dialog-body">
                    <template v-for="item in pays">
                        <button :key="item.id" :class="item.id" @click="pay(item)">{{item.description}}支付</button>
                    </template>
                </div>
                <div @click="showBox=false">
                    <x-icon type="ios-close-outline" size="30"></x-icon>
                </div>
                </x-dialog>
            </div>
 -->		
        </div>
        
	</div>
</template>

<script>
	import {mapState} from 'vuex'
	import {Swiper,	SwiperItem,	XDialog, TransferDomDirective as TransferDom } from "vux";
	export default {
		directives: {
			TransferDom
		},
		data() {
			return {
				headOption: {
					title: '我的订单',
					backText: '',
					showBack: true
				},
				notrip: false,
				// isNoMore: true,
				isloading: false,
				swiper_index: 0,
				list: [],
				order_type: 'ALL',
				pays: [], //支付方式
				w: null, //请求中
				showBox: false,
				order: null
			};
		},
		computed: {
			id() {
				return this.$route.query.id;
			},
			...mapState(['userInfo']),
		},
		filters: {
			payTypeLabel: function(value) {
				switch (value) {
					case 'weChat':
						return '微信支付'
						break;
					case 'integral':
						return '积分兑换'
						break;
					default:
						break;
				}

			},
			statusLabel: function(value) {
				switch (value) {
					case 'Y':
						return '已完成'
						break;
					case 'N':
						return '待支付'
						break;
					default:
						break;
				}
			}
		},
		watch: {
			
		},
		components: {
			Swiper,
			SwiperItem,
			XDialog
		},
		methods: {
			Delete (item) {
				let _this = this;
				_this.$fetch("/api/pay/delete/"+item.id).then(res => {
					// console.log(res);
					if (res.statusCode == "0000") {
						_this.$vux.toast.text("取消成功", "middle");
						_this.initPage();
					} else {
						_this.$vux.toast.text(res.msgCode, "middle");
					}
				});
			},
			showPay (item) {//立即付款
                let _this = this;
                _this.$fetch("/api/pay/rePay/"+item.id).then((res) => {
                    // console.log(res)
                    if (res.statusCode == '0000') {
                        
                        _this.pay(JSON.parse(res.data.wxPayOrderString));
                        
                    }else {
                        _this.$vux.toast.text(res.msgCode);
                        setTimeout(function () {
                        	_this.initPage();
                        },2000)
                    }
                })
			},
			// 获取支付通道
			plusReady() {
				let _this = this;
				plus.payment.getChannels(function(channels) {
					// alert(JSON.stringify(channels))
					for (var i in channels) {
						var channel = channels[i];
						if (channel.id == 'qhpay' || channel.id == 'qihoo') { // 过滤掉不支持的支付通道：暂不支持360相关支付
							continue;
						}
						
						_this.pays.push(channel);

					}
				}, function(e) {
					alert('获取支付通道失败：' + e.message);
				});
			},
			//支付
			pay(order) {
				let _this = this;
				// _this.showBox = false;
                let channels = null;
                let list = _this.pays;
                for (var i = 0; i < list.length; i++) {
                	if (list[i].id == 'wxpay') {
                		channels = list[i];
                	}
                }
				plus.payment.request(channels, order, function(result) {
					// alert(JSON.stringify(result));
					plus.nativeUI.alert('购买成功', function() {
                        setTimeout(function () {
                            _this.initPage();
                        },2000)
					}, '');
				}, function(e) {
					// alert('[' + e.code + ']：' + e.message);
					plus.nativeUI.alert('购买失败：' + e.code,null);
				});
			},
			initFilter (i,val) {
				let _this = this;
				_this.swiper_index = i;
				_this.order_type = val;
				_this.initPage();
			},
			toDetail (item) {
				let _this = this;
				switch (item.body){
					case 'video':
						_this.$router.push({
							path: '/vdetails?id='+item.bodyId
						})
						break;
					default:
						break;
				}
			},
			Commit() {
				let _this = this;
				let datas = {
					context: _this.commentText,
					videoId: _this.id,
					mobile: _this.userInfo.mobile
				};
				_this.isDisable = true;
				_this.$post("/api/hpp_comment/saveHppComment", datas).then(res => {
					// console.log(res);
					_this.isDisable = false;
					if (res.statusCode == "0000") {
						_this.$vux.toast.text("评论成功", "middle");
						_this.commentText = "";
						_this.getCommentList();
					} else {
						_this.$vux.toast.text(res.msgCode, "middle");
					}
				});
			},
			initPage() {
				let _this = this;
                _this.notrip = false;
                
                if (_this.isloading) return;
                
				_this.list = [];
				_this.isloading = true;
				_this.$fetch("/api/pay/findPayOrder/"+_this.order_type).then(res => {
					// console.log(res);
                    
					_this.isloading = false;
					_this.list = res.data.list;
					
					if (res.data.list.length) {
						_this.notrip = false;
					}else {
						_this.notrip = true;
					}
				});
			},

		},
		mounted() {
			let _this = this;
			_this.initPage();
			
			document.addEventListener('plusready', _this.plusReady, false);
			_this.plusReady();
		}
	};
</script>

<style scoped>
    
    .hui_content {
        padding-top: 115px;
    }
    
	.vux-x-icon {
		fill: #666;
	}
	.dialog-title {
		font-size: 0.373rem;
		color: #666;
		line-height: 1rem;
	}
	.dialog-body {
		padding: .6rem 0;
	}
	.dialog-body button {
		color: #00FF00;
		border: 1px solid #00FF00;
		padding: 2px 20px;
		border-radius: 20px;
		background-color: #fff;
		margin: 0 10px;
		font-size: 0.4rem;
	}
	.dialog-body button.integral {
		color: #f3917f;
		border-color: #f3917f ;
	}
	
	
	.sec_1 {
        width: 100%;
        position: absolute;
        top: 75px;
        left: 0;
        z-index: 99;
		background-color: #fff;
		padding: 0 0.3rem;
		margin-bottom: 0.1rem;
	}

	.sec_1>div {
		width: 33.3%;
        height: 40px;
		text-align: center;
	}

	.sec_1 span {
		display: block;
		line-height: 40px;
		border-bottom: 0.0533rem solid #fff;
		font-size: 0.4rem;
	}

	.sec_1 span.active {
		color: #ff3c3c;
		border-bottom: 0.0533rem solid #ff3c3c;
	}

	.table_body {
		padding: 0 0.133rem;
	}
	.item_box {
		margin-bottom: 0.266rem;
		padding: 0.266rem;
		background-color: #fff;
		border-radius: 0.266rem;
	}
	.item_box span.price {
		color: #ff5752;
	}

	.item_top {
		padding-bottom: 0.133rem;
	}
	
	.item_mid {
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		padding: 0.133rem 0;
		/* background-color: #f1f1f1; */
	}

	.item_bottom {
		padding-top: 0.133rem;
	}
	.item_bottom span {
		line-height: 0.53rem;
		color: #666;
		font-size: 0.3rem;
	}
	.item_bottom button {
		border-radius: 0.266rem;
		background-color: #ddd;
		padding: 2px 0.266rem;
		margin-left: 0.133rem;
		font-size: 0.293rem;
		color: #666;
	}

	.item_bottom button.buy {
		color: #fff;
	}

	.item_box img {
		width: 2.6666rem;
		height: 1.8666rem;
		border-radius: 0.1333rem;
	}

	.item_box .mid_box {
		padding: 0 0.2666rem;
		position: relative;
	}

	.item_box .mid_box h4 {
		font-size: 0.3466rem;
		color: #666;
		line-height: 0.6666rem;
	}

	.item_box .mid_box p {
		font-size: 0.3rem;
		color: #999;
	}

	.right_box span {
		font-size: 0.2933rem;
		color: #999;
		line-height: 0.6666rem;
		text-align: right;
	}

	.right_box span.price {
		font-size: 0.4rem;
	}
</style>
