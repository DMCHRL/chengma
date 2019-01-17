<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
        
        <div class="hui_content">
        
		<div class="banner_box">
			<img :src="train.img"/>
		</div>
		<div class="sec_box sec_1">
			<h3>{{train.trainName}}</h3>
			<div class="item_mess flex_row flex_align_center">
				<span class="name">价格：</span>
				<span class="price">{{meal.price}}元</span>
			</div>
			
			<div class="item_mess flex_row flex_align_center">
				<span class="name">套餐选择：</span>
				<div class="radio_groud flex_wrap">
					<template v-for="(item,index) in train.mealList">
						<span @click="change(index)" :class="[{'active':index == mealIndex},'radio_gui']">{{item.name}}</span>
					</template>
				</div>
			</div>
			<div class="item_mess flex_row flex_align_center">
				<span class="name">套餐包含：</span>
				<span>{{meal.include}}</span>
			</div>
			<div class="item_mess flex_row flex_align_center">
				<span class="name">累计报名：</span><span class="red">{{train.total}}人 </span>
				<span class="name">近日报名：</span><span class="red">{{train.monthTotal}}人  </span>
				<!-- <span class="name">好评率：</span><span class="red">99.9%</span> -->
			</div>
		</div>
		<!--<div class="sec_button flex_bet flex_align_center" @click="show12 = true">
			<span>选择课程套餐</span>
			<div class="arrow_right"></div>
		</div>-->
		<div class="sec_box sec_2">
			<div class="sec_title">
				<span>考证细节</span>
			</div>
			<div class="con_box">
				<div class="flex_bet">
					<div class="half_width">
						<span class="name">证书类型：</span>
						<span>{{train.type}}</span>
					</div>
					<div class="half_width">
						<span class="name">考证地点：</span>
						<span>{{train.address}}</span>
					</div>
				</div>
				<div class="flex_bet">
					<div class="half_width">
						<span class="name">考证时间：</span>
						<span>{{train.trainTime}}</span>
					</div>
					<div class="half_width">
						<span class="name">热门标签：</span>
						<span>{{train.hotTag}}</span>
					</div>
				</div>
				<div class="flex_bet">
						<span class="name name_1">证书简介：</span>
						<p class="flex_one">{{train.introduction}}</p>
				</div>

			</div>
			<div class="sec_title">
				<span>考证介绍</span>
			</div>
			<div class="art_body" v-html="train.context">
				
			</div>
			
		</div>
        
        
        </div>
		<div class="bottom_btn myback" @click="ToBuy">
			<button>立即报名缴费</button>
		</div>
		
		<!--<shop-select :show="show12" :train="train" @ToBuy="ToBuy" @cancle="show12 = false"></shop-select>-->
		
	</div>
</template>

<script>
	import ShopSelect from "../other/ShopSelect"
	export default {
		data() {
			return {
				headOption: {title: '考证详情',backText: '',showBack: true},
				train: {},
				mealIndex: 0,
				show12: false
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			},
			meal () {
				if (this.train.mealList) {
					return this.train.mealList[this.mealIndex]
				}else {
					return {
						name: '',
						price: '',
						include: ''
					}
				}
			}
		},
		components: {
			ShopSelect
		},
		methods: {
			ToBuy () {
				this.$router.push({
					path: '/apply-exam?id='+this.train.mealList[this.mealIndex].id
				})
			},
			change(i) {
				this.mealIndex = i;
			},
			initPage () {
				let _this = this;
				_this.$fetch("/api/hpp_exam/get/"+_this.id).then((res) => {
					// console.log(res)
					_this.train = res.data;
				})
			}
		},
		mounted () {
			this.initPage();
		}
	}
</script>

<style scoped>
	
	.sec_title  {
		padding: 0.2666rem 0;
	}
	.sec_title span {
		font-size: 0.4rem;
		color: #333;
		border-left: 3px solid #FF2222;
		padding-left: 0.2666rem;
	}
	.sec_box {
		background-color: #fff;
		margin-bottom: 0.2666rem;
		padding: 0 0.2666rem;
	}
	.sec_box h3 {
		font-size: 0.4rem;
		color: #333;
		padding: 0.2666rem 0;
	}
	.sec_box span {
		line-height: 0.6rem;
	}
	.sec_box span.name {
		color: #999;
		font-size: 0.3333rem;
		white-space: nowrap;
	}
	
	.half_width {
		width: 50%;
	}
	.item_mess {
		padding: 0.2rem 0;
		border-bottom: 1px solid #ccc;
	}
	.item_mess span.price {
		color: #FF5053;
		font-size: 0.5rem;
		margin-right: 0.2666rem;
	}
	.item_mess span.red {
		color: #FF5053;
		margin-right: .3rem;
	} 
	.radio_groud span.radio_gui {
		border: 1px solid #999;
		font-size: 0.3rem;
		color: #666;
		padding: 0.1333rem 0.2rem;
		line-height: 0.4rem;
		margin: 0.1333rem;
	}
	.radio_groud span.active {
		border: 1px solid #FF3C3C;
	}
	
	.bottom_btn {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 50px;
		text-align: center;
	}
	.bottom_btn span {
		line-height: 50px;
		font-size: 0.3466rem;
		color: #fff;
	}
	.sec_button {
		background-color: #fff;
		margin-bottom: 0.2666rem;
		padding: .1rem .2rem;
	}
	.sec_button span {
		font-size: 0.3333rem;
		color: #666;
		line-height: 30px;
	}
</style>