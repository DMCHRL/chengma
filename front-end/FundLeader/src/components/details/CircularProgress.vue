<template>
	<div class="outer">
		<div class="left"></div>
		<div class=" right " :style="[rotateStyle, percent>50?{background:'greenyellow'}:null]"></div>
		<div class="inner">
			<span>{{percent}}%</span>
			<p>募集进度</p>
		</div>
		<!-- <span>{{percent}}</span>
		<div class="button" @click="test">前进</div> -->
	</div>
</template>

<script>
	export default {
		name: 'CircularProgress',
		props: {
			percent: {
				// type: Object
			},
		},
		data() {
			return {
				
			}
		},
		computed: {
            rotateStyle() {
            	var deg = (this.percent / 100) * 360;
            	return {
            		transform: `rotate(${deg > 180 ? deg - 180 : deg}deg)`,
            	};
            },
        },
		components: {},
		methods: {
            test() {
            	this.percent = this.percent > 100 ? this.percent : (this.percent + 5) % 100;
            },
		},

	}
</script>

<style scoped>
	.outer {
	  position: relative;
	  width: 3.733rem;
	  height: 3.733rem;
	  margin: auto;
	  background: #fe9f02;
	  border-radius: 50%;
	}
	.left {
	  width: 3.733rem;
	  height: 3.733rem;
	  position: absolute;
	  margin: auto;
	  background: #28347e;
	  border-radius: 50%;
	  clip: rect(auto, 1.866rem, auto, auto);
	}
	.right {
	  width: 3.733rem;
	  height: 3.733rem;
	  position: absolute;
	  margin: auto;
	  background: #28347e;
	  border-radius: 50%;
	  clip: rect(auto, auto, auto, 1.866rem);
	  transition: transform 0.2s;
	}
	.inner {
	  width: 3.4rem;
	  height: 3.4rem;
	  left: 50%;
	  top: 50%;
	  transform: translate(-50%, -50%);
	  position: absolute;
	  background: #fff;
	  border-radius: 50%;
	  text-align: center;
	  padding-top: 1rem;
	}
	.inner span {
	  font-size: 0.6rem;
	  font-weight: bold;
	  color: #202a67;
	}
    .inner p {
        font-size: 0.346rem;
        color: #202a67;
    }
</style>
