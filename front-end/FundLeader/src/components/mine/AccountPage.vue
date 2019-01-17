<template>
	<div class="content_box">
		<my-header :leftOptions="headOption"></my-header>
        
        <div class="hui_content" id="hui-content">
        
		<div class="list_box">
			<swipeout>

				<swipeout-item transition-mode="follow" v-for="(item,index) in list" :key="index">

					<div slot="content" class="swiper_box myback flex_bet flex_align_center">
						<div class="left_box">
							<div class="img_box">
								<img src="../../assets/img/8_02.png" />
							</div>
						</div>
						<div class="mid_box flex_one">
							<p>{{item.account}}</p>
							<div class="flex_row">
								<span v-text="item.monthProfit ? item.monthProfit: '0.00'"></span>
								<div class="small_box">
									<p>累计收益率</p>
									<p>%</p>
								</div>
							</div>
							<p v-if="item.strategyName">跟单策略：{{item.strategyName}} |
								<router-link :to="'/strategy-details?account='+ item.strategyAccount">
									<img src="../../assets/img/2_02.png" />
								</router-link>
							</p>
							<p v-else>跟单策略：暂无跟单策略</p>
						</div>
						<div class="right_box">
							<router-link :to="'/mystrategy?account='+ item.account">
								<span>我的账号</span>
							</router-link>
						</div>
					</div>
					<div slot="right-menu">
						<swipeout-button type="warn">
							<div @click="Delete(item.id)">
								删除
							</div>
						</swipeout-button>
					</div>
				</swipeout-item>

			</swipeout>

		</div>
		<div v-transfer-dom class="weui-mask" v-show="Show">
			<div class="weui-dialog">
				<div class="dialog_icon_box">
					<img v-if="notice.msgType=='策略跟单'" src="../../assets/img/mess_01.png" />
					<img v-else src="../../assets/img/mess_04.png" />
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

			<p v-show="notrip">暂无账号！</p>
		</div>
        
        </div>
        
	</div>
</template>

<script>
	import {
		Swipeout,
		SwipeoutItem,
		SwipeoutButton,
		XButton,
		Icon,
		TransferDomDirective as TransferDom
	} from 'vux'
	import SpinnerLoading from "../common/SpinnerLoading"
	export default {
		directives: {
			TransferDom
		},
		data() {
			return {
				headOption: {
					title: '我的账户',
					backText: '',
					showBack: true
				},
				// initList: [],
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
			user() {
				let userStr = localStorage.getItem("user");
				if (userStr) {
					let user = JSON.parse(userStr);
					return user;
				} else {
					return null;
				}
			}
		},
		components: {
			SpinnerLoading,
			Swipeout,
			SwipeoutItem,
			SwipeoutButton,
			XButton,
			Icon,
		},
		methods: {
			showDetails(data) {
				let _this = this;
				_this.notice = data;
				_this.Show = true;
			},
			Delete(id) {
				let _this = this;
				_this.$fetch("/api/tlb-account/delete/" + id).then(res => {
					// console.log(res)
					if (res.statusCode == '0000') {
						_this.initPage();
					} else {
						_this.$vux.toast.text(res.msgCode, 'middle')
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
				_this.$fetch("/api/tlb-account/findByMobile").then((res) => {
					// console.log(res)
					clearTimeout(timer)
					_this.isloading = false;
					_this.list = res.data.list;

					if (res.data.list.length) {
						// _this.reloadArray(res.data.list)
						_this.notrip = false;
					} else {
						_this.notrip = true;
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
	.swiper_box {
		position: relative;
		height: 100px;
		border-radius: 10px;
		margin: 10px;
	}

	.left_box {
		padding: 0.6666rem;
	}

	.left_box .img_box {
		width: 1.8666rem;
		height: 1.8666rem;
		border: 0.1066rem solid #fcc8c8;
		border-radius: 50%;
		overflow: hidden;
	}

	.mid_box p {
		color: #fff;
		font-size: 0.3733rem;
	}

	.mid_box p:last-of-type {
		font-size: 0.2933rem;
	}

	.small_box p {
		font-size: 0.2rem;
		line-height: 0.5rem;
		margin-top: 0.1rem;
	}

	.mid_box span {
		color: #fff;
		font-size: 0.96rem;
		margin: 0 0.1333rem;
	}

	.mid_box img {
		position: relative;
		top: -1px;
		width: 0.28rem;
		height: 0.28rem;
	}

	.right_box {
		position: absolute;
		right: 5px;
		top: 5px;
	}

	.right_box span {
		color: #eb0021;
		font-size: 0.2933rem;
		background-color: #fff;
		display: inline-block;
		padding: 0.1333rem 0.6666rem;
		border-radius: 0.3666rem;
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
