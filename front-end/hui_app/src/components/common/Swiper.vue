<template>
	<div id="" v-show="show" >
		<swiper loop auto :aspect-ratio="150/375" dots-class="dots-class">
	      
	      <template v-for="item in list">
	      	
	      	<swiper-item class="swiper-demo-img" >
		      	<img :src="item.img"/>
		    </swiper-item>
	      
	      </template>
	    </swiper>
	</div>
</template>

<script>
	import { Swiper, SwiperItem } from 'vux'
	export default {
		props: ["list"],
		data() {
			return {
				show: true
			}
		},
		components: {
			Swiper,
			SwiperItem
		},
		methods: {
			
		},
		mounted () {
			
		}
	}
</script>

<style scoped>
	.swiper-demo-img img {
		width: 100%;
	}
	.vux-indicator.dots-class>a>i{
		background-color: #fff !important;
	}
</style>