<template>
	<div class="content_box no_footer">
		<my-header :leftOptions="headOption"></my-header>
    
    <div class="hui_content" id="hui-content">
    
		<div class="sec_box sec_1">
			<p>商品类型：{{type | TypeLabel}}</p>
		</div>
		<ul class="table_body flex_col">

			<li class="item_box">
				
				<div class="item_mid flex_bet" @click="toDetail(item)">
					<div class="img_box">
						<img :src="item.img" />
					</div>
					<div class="mid_box flex_one">
						<h4>{{item.videoName}}</h4>
						<p>{{item.videoText}}</p>
					</div>
					<div class="right_box flex_col flex_center">
						<span class="price">￥{{item.price}}</span>
					</div>
				</div>
			</li>

		</ul>
		
		<div class="sec_box" v-if="integral.balance != 0 && voucher != 0">
			<!-- v-show="item.mealList[0].price > integral.balance" -->
			<div class="radio_item flex_bet" @click="isPointDeduction = !isPointDeduction">
				<span>积分抵扣{{voucher}}元<i>（可用积分{{integral.balance}}）</i></span>
				<span v-if="isPointDeduction" class="flex_align_center"><x-icon type="ios-checkmark-outline" size="25"></x-icon></span>
				<span v-else class="flex_align_center"><x-icon type="ios-circle-outline" size="25"></x-icon></span>
			</div>
		</div>
		
		<div class="sec_3">
			<div class="radio_item flex_bet" @click="isWeChat = !isWeChat">
				<span>微信支付</span>
				<span v-if="isWeChat" class="flex_align_center"><x-icon type="ios-checkmark-outline" size="25"></x-icon></span>
				<span v-else class="flex_align_center"><x-icon type="ios-circle-outline" size="25"></x-icon></span>
			</div>
			<div v-show="item.price <= integral.balance" class="radio_item flex_bet" @click="isPointPay = !isPointPay">
				<span>积分兑换<i>（剩余积分{{integral.balance}}）</i></span>
				<span v-if="isPointPay" class="flex_align_center"><x-icon type="ios-checkmark-outline" size="25"></x-icon></span>
				<span v-else class="flex_align_center"><x-icon type="ios-circle-outline" size="25"></x-icon></span>
			</div>
		</div>
		
		<div v-show="isloading"  class="trip_box">
			<spinner :type="'ios'" size='30px'></spinner>
		</div>
		
    </div>
    
		<div class="bottom_btn">
			<span>合计：<i class="price">￥{{totalPrice}}</i></span>
			<button class="myback" type="primary" :disabled="isDisable" @click="Commit">提交订单</button>
		</div>
	</div>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		data() {
			return {
				headOption: {
					title: '确认订单',
					backText: '',
					showBack: true
				},
				isloading: false,
				pays: [], //支付方式
				isDisable: false, //请求中
				
				item: {},
				isPointDeduction: false,//是否选择积分抵扣
				isWeChat: false,//是否选择微信支付
				isPointPay: true,//是否选择积分支付
				integral: {},//积分信息
			};
		},
		computed: {
			...mapState(['userInfo']),
			type() {
				return this.$route.query.type;
			},
			id() {
				return this.$route.query.id;
			},
			totalPrice () {
				if (this.isPointDeduction) {
          let total = this.item.price - this.integral.balance;
          if (total < 0) {
            return 0
          }else {
            return total
          }
				}else {
					return this.item.price
				}
			},
			payType () {
				
				if (this.isPointDeduction) {
					return 'mix'
				}else {
					if (this.isWeChat) {
						return 'weChat'
					}else {
						return 'integral'
					}
				}
				
			},
      //可抵扣金额
      voucher () {
      	let _this = this;
      		
      	let sort = null;//套餐类别
      	let price = this.item.price;//商品价格
      	let balance = _this.integral.balance;//可用积分
      	
      	let MaximumDeductible = 0;//最大可抵扣积分
      		
      	if (this.type == 'course') {
      		switch (sort){
      			case 1:
      				MaximumDeductible = 5000;
      				break;
      			case 2:
      				MaximumDeductible = 4000;
      				break;
      			case 3:
      				MaximumDeductible = 3000;
      				break;
      			default:
      				break;
      		}
      	}else {
      		MaximumDeductible = 1000;
      	}
      	
      	if (price>balance) {
      		if (balance>MaximumDeductible) {
      			return MaximumDeductible
      		}else {
      			return balance
      		}
      	}else {
      		return MaximumDeductible
      	}
      	
      }
		},
		filters: {
			TypeLabel: function(value) {
				switch (value) {
					case 'video':
						return '交易战法视频'
						break;
					case 'integral':
						return '积分兑换'
						break;
					default:
						break;
				}
			}
		},
		watch: {
			isWeChat (bool) {
				if (bool) this.isPointPay = false
			},
			isPointPay (bool) {
				if (bool) this.isWeChat = false
			},
		},
		components: {
		},
		methods: {
			// 获取支付通道
			plusReady() {
				let _this = this;
				plus.payment.getChannels(function(channels) {
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
			Commit() {
				let _this = this;
				
				if (_this.item.price > _this.integral.balance) {
					if (_this.isPointPay) {
						_this.$vux.toast.text('请选择支付方式','middle')
						return;
					}
				}
				
				let datas = {
					body: 'video',//	是	string	商品类型 video,course,exam
					id: _this.id,//	是	string	视频id，套餐id
					payType: _this.payType
				}
				
				_this.isDisable = true;
				_this.$vux.loading.show({
				 text: 'Loading'
				})
				
				_this.$post('/api/pay/createOrder',datas).then((res) => {
					_this.isDisable = false;
					_this.$vux.loading.hide();
					
					
					if (res.statusCode == '0000') {
						
						if (_this.payType == 'integral') {
							//积分支付
							_this.$vux.toast.text('支付成功','middle')
							
              _this.$router.push({
                path: '/order'
              })
							
						}else {
							//微信支付
							var order = JSON.parse(res.data.wxPayOrderString);
							let channel = null;
							let list = _this.pays;
							for (var i = 0; i < list.length; i++) {
								if (list[i].id == 'wxpay') {
									channel = list[i];
								}
							}
							
							plus.payment.request(channel, order, function(result) {
								// alert(JSON.stringify(result));
                
								_this.$vux.toast.text('支付成功','middle')
								
								_this.$vux.loading.show({
									text: 'Loading'
								})
								setTimeout(function () {
									_this.$vux.loading.hide();
									_this.$router.push({
										path: '/order'
									})
								},3000)
								
							}, function(e) {
								// alert('[' + e.code + ']：' + e.message);
								if (e.code == -100) {
									plus.nativeUI.alert('支付取消',function () {
										_this.$router.push({
											path: '/order'
										})
									},'');
								}else {
									plus.nativeUI.alert('支付失败'+e.code,function () {
											_this.$router.push({
												path: '/order'
											})
									},'');
								}
							});
						}
					}else {
						_this.$vux.toast.text(res.msgCode,'middle')
						_this.$router.push({
							path: '/order'
						})
					}
				})
				
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
			
			getUserPoint () {
				let _this = this;
				_this.$fetch("/api/hpp_integral/get/"+_this.userInfo.mobile).then((res) => {
					// console.log(res)
					if (res.data) {
						_this.integral = res.data;
					}
				})
			},
			initPage() {
				let _this = this;
				let timer = setTimeout(function () {
					_this.isloading = true;
				},1000)
				
				_this.$fetch("/api/hpp_video/showOnApp/" + _this.id).then((res) => {
					// console.log(res)
					
					clearTimeout(timer)
					_this.isloading = false;
					
					_this.item = res.data;
					
				})
				
			},

		},
		mounted() {
			let _this = this;
			_this.initPage();
			_this.getUserPoint();
			
			document.addEventListener('plusready', this.plusReady, false);
			this.plusReady();
		}
	};
</script>

<style scoped>
	.sec_2 {
		padding: 0.266rem;
	}
	.sec_2>div {
		padding: 0 0.4rem;
	}
	.sec_2 p{
		font-size: 0.373rem;
		color: #333;
		line-height: 0.666rem;
	}
	.sec_2 span {
		font-size: 0.32rem;
		color: #666;
		line-height: 0.666rem;
	}
	
	.sec_3 {
		margin-top: 0.266rem;
	}
	
	
	.vux-x-icon {
	  fill: #f3917f;
	}
	
	.radio_item {
		padding: 0 0.3rem;
    margin-bottom: .1rem;
    background-color: #fff;
	}
	.radio_item span {
		line-height: 1.066rem;
		color: #333;
		font-size: 0.35rem;
	}
  .radio_item span i {
  	color: #999;
  	font-size: .3rem;
  }
	
	.bottom_btn {
		text-align: right;
		border-top: 1px solid #ddd;
	}
	.bottom_btn span {
		font-size: 0.373rem;
		color: #333;
		margin-right: 0.266rem;
	}
	.bottom_btn span i.price {
		color: #ff5752;
	}
	.bottom_btn button {
		width: 30%;
		display: inline-block;
	}
	
	.sec_box {
		background-color: #fff;
	}
	.sec_1 {
		padding: 0 0.3rem;
	}
	.sec_1 p {
		font-size: 0.35rem;
		color: #333;
		line-height: 1rem;
	}
	.item_box {
		padding: 0.266rem;
	}
	.item_box span.price {
		color: #ff5752;
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
		font-size: 0.2933rem;
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
