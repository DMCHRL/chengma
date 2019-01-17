<template>
	<div v-transfer-dom>
      <popup v-model="show" position="bottom" height="65%" :is-transparent='true' :hide-on-blur='false'>
        
        <div class="main_box">
        	<div class="img_box">
        		<img :src="train.img"/>
        	</div>
    		<div class="cancel_box" @click="cancle">
    			<x-icon type="ios-close-outline" size="25"></x-icon>
    		</div>
    		<div class="price_box">
    			<div class="item_mess flex_col">
					<span class="price">￥{{meal.price}}</span>
					<span>{{train.trainName}}</span>
				</div>
    		</div>
        	<div class="content_box">
				<!--<div class="item_mess flex_row flex_align_center">
					<span class="name">课程名称：</span>
					<span>{{train.trainName}}</span>
				</div>
				<div class="item_mess flex_row flex_align_center">
					<span class="name">授课时长：</span>
					<span>{{train.trainTime}}</span>
				</div>-->
				<div class="item_mess flex_col">
					<span class="name">套餐选择：</span>
					<div class="radio_groud flex_one flex_wrap">
						<template v-for="(item,index) in train.mealList">
							<span @click="change(index)" :class="[{'active':index == mealIndex},'radio_gui']">{{item.name}}</span>
						</template>
					</div>
				</div>
				<div class="item_mess flex_col">
					<span class="name">套餐包含：</span>
					<span>{{meal.include}}</span>
				</div>
				
				<router-link :to="'/apply-train?id='+train.id">
					<div class="bottom_btn myback">
						<button>确定</button>
					</div>
				</router-link>
        	</div>
        	
        </div>
        
      </popup>
    </div>
</template>

<script>
	import { Popup,XHeader, Icon, TransferDomDirective as TransferDom } from 'vux'
	export default {
		props: ['show','train'],
		directives: {
	    	TransferDom
	  	},
		data() {
			return {
				mealIndex: 0
			}
		},
		computed: {
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
			Popup
		},
		methods: {
			change(i) {
				this.mealIndex = i;
			},
			readAndAgreen() {
				this.$emit('ToBuy')
			},
			cancle() {
				this.$emit("cancle")
			}
		},
		mounted () {
		}
	}
</script>

<style scoped>
	.main_box {
		width: 100%;
		height: 100%;
		position: relative;
		padding-top: .5rem;
	}
	.img_box {
		width: 3rem;
		overflow: hidden;
		position: absolute;
		top: 0;
		left: 0.5rem;
		border: 2px solid #fff;
		border-radius: .1rem;
		box-shadow: 0px 0px 2px #999;
		z-index: 9;
	}
	.img_box img {
		width: 100%;
	}
	.price_box {
		position: absolute;
		top: .6rem;
		left: 3.4rem;
		z-index: 9;
	}
	.content_box {
		position: relative;
		background-color: #fff;
		height: 100%;
		width: 100%;
		padding-top: 1.5rem;
		padding-bottom: 60px;
		overflow-y: scroll;
	}
	.cancel_box {
		position: absolute;
		right: .2rem;
		top: .7rem;
		z-index: 9;
	}
	.item_mess {
		padding: 0.2rem 0.4rem;
	}
	.item_mess span {
		color: #666;
		font-size: 0.35rem;
	}
	.item_mess span.name {
		line-height: 0.7rem;
		color: #999;
	}
	.item_mess span.price {
		color: #FF5053;
		font-size: 0.5rem;
		margin-right: 0.2666rem;
	}
	
	.radio_groud span.radio_gui {
		border: 1px solid #999;
		font-size: 0.3rem;
		color: #666;
		padding: 0.1333rem;
		line-height: 0.4rem;
		margin: 0.1333rem;
	}
	.radio_groud span.active {
		border-color: #FF3C3C;
		background-image: url(http://www.txasfx.com/hui/img/ron.png);
		background-repeat: no-repeat;
		background-position: right bottom;
	}
</style>