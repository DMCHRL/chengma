<template>
	<div class="comment-list">
		<ul class="list_box">
			<li class="item_box flex_bet flex_align_center" v-for="item in commentList">
				<div class="flex_row flex_align_center">
					<div class="img_box">
						<img :src="item.headImg"/>
					</div>
					<span>{{item.username}}</span>
				</div>
				<div class="flex_one content flex_align_center">
					<p>{{item.context}}</p>
				</div>
				<span class="text_right">{{item.createAt}}</span>
			</li>
		</ul>
		<div class="trip_box" v-show="!commentList.length">
			<img src="../../assets/img/no_1.png"/>
			<p>暂无评论，赶紧抢沙发吧！</p>
		</div>
	</div>
</template>

<script>
	export default {
		props: ['commentList'],
		data() {
			return {
				
			}
		},
		watch: {
			
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
	.item_box {
		padding: 0.2rem 0.2666rem;
		border-top: 1px solid #cccccc;
	}
	.item_box:last-of-type {
		border-bottom: 1px solid #cccccc;
	}
	.item_box img {
		width: 0.8rem;
		height: 0.8rem;
	}
	.item_box span {
		margin: 0 0.2666rem;
		color: #999;
	}
	.item_box p,
	.item_box span {
		font-size: 0.2933rem;
		color: #666;
	}
	.item_box span.text_right {
		float: right;
	}
	
</style>