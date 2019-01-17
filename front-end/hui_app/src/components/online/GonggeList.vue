<template>
	<div class="sec_box sec_1" v-if="isHide">
		<div class="titling">
			<slot name="title">
				<span>每日推荐</span>
			</slot>
		</div>
			<ul class="flex_wrap">
				<template v-for="item in list">
					<li class="item_box">
						<router-link :to="'/vlist?id='+item.id">
						  	<div class="flex_col flex_center">
						  		<div class="img_box">
						  			<img :src="item.img"/>
						  		</div>
						  		<p>{{item.videoTypeName}}</p>
						  		<div class="flex_bet">
						  			<span>共{{item.phase}}期</span>
						  			<!-- <span class="xian" v-if="item.dayPush == 'NO'">限免</span>
					  				<span class="fu" v-else>付费</span> -->
						  		</div>
						  	</div>
					  	</router-link>
					</li>
				</template>
				
			</ul>
		</div>
</template>

<script>
	export default {
		props: ['list'],
		data() {
			return {
				isHide: true
			}
		},
        watch: {
        	list(newValue, oldValue) {
                let _this = this;
        		if (newValue.length<=0) {
                    _this.isHide = false;
                }
        	}
        },
		computed: {
			
		},
		components: {
			
		},
		methods: {
			
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	.sec_box {
		background-color: #fff;
		margin-bottom: 0.2666rem;
	}
	.titling {
		padding: 0.2666rem 0.4rem;
	}
	.titling span{
		font-size: 0.4rem;
		color: #333333;
	}
	.item_box {
		width: 33.33%;
		text-align: center;
		box-sizing: border-box;
		padding: 0 0.3333rem;
		padding-bottom: 0.2666rem;
	}
	.item_box .img_box{
		width: 2.6666rem;
		height: 1.8666rem;
		border-radius: 0.1333rem;
        overflow: hidden;
	}
	.item_box p {
		text-align: left;
		font-size: 0.3466rem;
		color: #333;
		line-height: 0.6666rem;
		width: 100%;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.item_box span {
		font-size: 0.3rem;
		color: #999;
	}
	.item_box span.xian{
		color: #28c76f;
	}
	.item_box span.fu{
		color: #ff5752;
	}
	
</style>