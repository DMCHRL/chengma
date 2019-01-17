<template>
	<div class="content_box">
		<my-header :leftOptions="headOption"></my-header>
		<div class="hui_content">
			<ul class="list_body">
				<li v-for='(item,index) in list'>
					<router-link :to="'/tdetails?id='+ item.id">
						<div class="item_box flex_bet flex_align_center">
							<div class="img_box">
								<img :src="item.img" />
								<!--</router-link>-->
							</div>
							<div class="con_box flex_one">
								<!--<router-link :to="'/tdetails?id='+ item.id">-->
								<h3>{{item.trainName}}</h3>
								<!--</router-link>-->
								<div class="flex_bet">
									<div class="flex_col">
										<span>{{item.teacher}}</span>
										<span>{{item.trainTime}}</span>
									</div>
									<!--<router-link :to="'/tdetails?id='+ item.id">-->
									<button class="myback">查看详情</button>
								</div>
							</div>
						</div>
					</router-link>
				</li>

			</ul>

			<shop-select :show="show12" :train="train" @ToBuy="ToBuy" @cancle="show12 = false"></shop-select>
            
            <div v-show="isloading"  class="trip_box">
            	<spinner :type="'ios'" size='30px'></spinner>
            </div>
		</div>
	</div>
</template>

<script>
	import ShopSelect from "../other/ShopSelect"
	export default {
		data() {
			return {
				headOption: {
					title: '交易面对面',
					backText: '',
					showBack: true
				},
				list: [],
				show12: false,
				train: {},
                isloading: false
			}
		},
		components: {
			ShopSelect
		},
		methods: {
			ToBuy() {

			},
			showSelect(index) {
				this.train = this.list[index];
				this.show12 = true;
			},
			initPage() {
				let _this = this;
                _this.isloading = true;
				_this.$fetch("/api/hpp_course/findAll").then((res) => {
                    _this.isloading = false;
					_this.list = res.data.list;
				})
			}
		},
		activated() {
			this.initPage();
		}
	}
</script>

<style scoped>
	.list_body {
		background-color: #fff;
	}

	.item_box {
		padding: 0.3333rem;
		border-bottom: 1px solid #ccc;
	}

	.item_box .img_box {
		width: 3.1733rem;
		height: 2.2133rem;
		border-radius: 0.1333rem;
		overflow: hidden;
	}

	.item_box .con_box {
		padding-left: 0.2666rem;
	}

	.item_box h3 {
		font-size: 0.4rem;
		color: #333;
		font-weight: normal;
		margin-bottom: 0.1333rem;
	}

	.item_box span {
		font-size: 0.2933rem;
		color: #999;
	}

	.item_box button {
		color: #fff;
		font-size: 0.3rem;
		background-color: #fff;
		border-radius: 0.4rem;
		padding: .15rem 0.4rem;
	}

	.item_box span.xian {
		color: #28c76f;
		font-size: 0.4rem;
		line-height: 0.6666rem;
	}

	.item_box span.price {
		color: #ff5752;
		font-size: 0.45rem;
		line-height: 0.6666rem;
	}
</style>
