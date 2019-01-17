<template>
	<div class="cover_box">
		<div class="loading_box">
			<div class="spinner">
			  <div class="dot1"></div>
			  <div class="dot2"></div>
			</div>
			<div class="trip_box">
				<slot name="text"></slot>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				
			}
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
	.cover_box {
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background-color: rgba(0,0,0,.1);
		z-index: 9;
	}
	.loading_box {
		width: 3rem;
  		height: 3rem;
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
	  	margin: auto;
	  	z-index: 99;
	}
	/*加载中动画*/
.spinner {
  width: 2.4rem;
  height: 2.4rem;
  text-align: center;
   position: relative;
  -webkit-animation: rotate 2.0s infinite linear;
  animation: rotate 2.0s infinite linear;
  z-index: 999;
}
 
.dot1, .dot2 {
  width: 60%;
  height: 60%;
  display: inline-block;
  position: absolute;
  top: 0;
  background-color: #f3917f;
  border-radius: 100%;
   
  -webkit-animation: bounce 2.0s infinite ease-in-out;
  animation: bounce 2.0s infinite ease-in-out;
}
 
.dot2 {
  top: auto;
  bottom: 0px;
  -webkit-animation-delay: -1.0s;
  animation-delay: -1.0s;
}
 
@-webkit-keyframes rotate { 100% { -webkit-transform: rotate(360deg) }}
@keyframes rotate { 100% { transform: rotate(360deg); -webkit-transform: rotate(360deg) }}
 
@-webkit-keyframes bounce {
  0%, 100% { -webkit-transform: scale(0.0) }
  50% { -webkit-transform: scale(1.0) }
}
 
@keyframes bounce {
  0%, 100% {
    transform: scale(0.0);
    -webkit-transform: scale(0.0);
  } 50% {
    transform: scale(1.0);
    -webkit-transform: scale(1.0);
  }
}
</style>