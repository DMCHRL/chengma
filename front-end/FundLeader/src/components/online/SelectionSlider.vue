<template>
	<div class="">
		<scroller lock-y :scrollbar-x=false>
	      <div class="box1" :style="{width: box1Width + 'px'}">
	        <template v-for="item in list">
	        	<div :class="['box1-item',{backimg2: item.id==id}]">
		        	<router-link :to="'/vdetails?id='+item.id">
		        		<p>【第{{item.sortNum}}期】</p>
								<p class="name">{{item.videoName}}</p>
		        	</router-link>
		        </div>
	        </template>
	      </div>
	    </scroller>
	</div>
</template>

<script>
	import { Scroller } from 'vux'

	export default {
		props: ['videoTypeId','id'],
		data() {
			return {
				box1Width: 1200,
				list: []
			}
		},
		watch: {
			
		},
		components: {
			Scroller
		},
		methods: {
			initPage () {
				let _this = this;
				if (_this.videoTypeId) {
					_this.$fetch("/api/hpp_video/findByTypeId/"+this.videoTypeId).then((res) => {
						// console.log(res)
						_this.list = res.data.list;
						if (res.data.list.length) {
							_this.box1Width = 125*res.data.list.length;
						}
					})
				}else {
					setTimeout(function () {
						_this.initPage();
					},1500)
				}
				
			}
		},
		mounted() {
			this.initPage();
		}
	}
</script>

<style scoped>
.box1 {
  height: 1.6rem;
  width: 1200px;
  position: relative;
  padding: 0 0.2666rem;
  padding-bottom: 0.2666rem;
}
.box1-item {
  width: 2.6666rem;
  height: 1.7rem;
  box-sizing: border-box;
  background-color: #f4f4f4;
  border-radius: 0.1rem;
  float: left;
  display:inline-block;
  margin-left: 0.2rem;
  text-align: center;
  padding-top: 0.4rem;
}
.box1-item:first-child {
  margin-left: 0;
}
.box1-item p {
	font-size: 0.35rem;
	color: #333;
}
.box1-item p.name {
	font-size: 0.3rem;
	color: #666;
}
</style>