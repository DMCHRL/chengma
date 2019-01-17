<template>
	<div class="content_box">
		<my-header :leftOptions="headOption"></my-header>
        
        <div class="hui_content" id="hui-content">
        
		<ul>
			<template v-for="(items,indexs) in list">
				<li class="item_box" :key="items.id">
					<p>{{items.day}}</p>

					<swipeout>

						<swipeout-item transition-mode="follow" v-for="(item,index) in items.arr" :key="index">
							<div slot="content" class="mess_box flex_bet flex_align_center" @click="showDetails(item)">
								<div class="icon_box">
									<!-- <img src="../../assets/img/mess_01.png" /> -->
									<img v-if="item.status == 'Y'" src="../../assets/img/mess_05.png" />
									<img v-else src="../../assets/img/mess_04.png" />
								</div>
								<div class="text_box flex_bet flex_one flex_align_center">
									<div class="con_box">
										<h3>{{item.type}}</h3>
										<p>{{item.context | contextFilter}}</p>
									</div>

									<div class="time_box flex_align_center">
										<span v-text="item.sendTime.slice(10)"></span>
									</div>
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
					<img src="../../assets/img/mess_04.png" />
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
	import SpinnerLoading from "../common/SpinnerLoading"
	export default {
		directives: {
			TransferDom
		},
		data() {
			return {
				headOption: {
					title: '消息通知',
					backText: '',
					showBack: true
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
			SpinnerLoading,
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
                _this.$fetch("/api/hpp_notice_sign/readOne/"+data.signId).then()
                _this.getNoticeNum();
			},
			Delete(id, index, arr, indexs) {
				let _this = this;
				_this.$fetch("/api/hpp_notice_sign/delete/" + id).then(res => {
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
				_this.$fetch("/api/hpp_notice_sign/read/" + _this.userInfo.mobile).then((res) => {
                    // console.log(res)
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
	.item_box>p {
		font-size: 0.3466rem;
		color: #929292;
		line-height: 0.8533rem;
		padding: 0 0.2666rem;
	}

	.mess_box {
		background-color: #fff;
		height: 2rem;
	}

	.mess_box img {
		width: 1rem;
		height: 1rem;
		margin: 0 0.3rem;
	}

	.mess_box .text_box {
		height: inherit;
		border-bottom: 1px solid #ededed;
	}

	.text_box {
		box-sizing: border-box;
		padding: 0.1333rem 0;
	}

	.con_box {
		width: 6rem;
	}

	.con_box h3 {
		font-size: 0.38rem;
		color: #333;
		margin-bottom: 0.1rem;
		width: 100%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.con_box p {
		font-size: 0.3rem;
		color: #666;
		width: 100%;
		display: -webkit-box;
		/*将对象转为弹性盒模型展示*/
		-webkit-box-orient: vertical;
		/*设置弹性盒模型子元素的排列方式*/
		-webkit-line-clamp: 3;
		/*限制文本行数*/
		overflow: hidden;
		text-overflow: ellipsis;
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
