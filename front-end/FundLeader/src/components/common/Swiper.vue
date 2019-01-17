<template>
	<div id="swiper_box" v-show="show" >
		<swiper loop auto dots-class="dots-class">
	      
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
  .swiper-demo-img {
    width: 90%;
    height: 4rem;
    overflow: hidden;
    background-color: #eee;
  }
	.swiper-demo-img img {
		width: 100%;
    min-height: 100%;
	}
	.vux-indicator.dots-class>a>i{
		background-color: #fff !important;
	}
  
  #swiper_box {
    margin: 0 10px;
  }
</style>