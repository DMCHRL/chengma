<template>
	<div class="content_box">
		<my-header :leftOptions="headOption" ></my-header>
		<div class="hui_content">
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/8_02.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="" value="" v-model="name" placeholder="输入您的姓名"/>
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box">
				<img src="../../assets/img/8_01.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="number" name="" id="" value="" v-model="phone" placeholder="请输入手机号" />
			</div>
		</div>
		<div class="item_box flex_bet flex_align_center">
			<div class="icon_box ">
				<img src="../../assets/img/8_03.png"/>
			</div>
			<div class="text_box flex_one flex_align_center">
				<input type="text" name="" id="" value="" v-model="email" placeholder="输入您的邮箱"/>
			</div>
		</div>
		
		<div class="btn_box">
			<button class="myback" @click="Commit" :disabled="isDisable">立即报名</button>
		</div>
		
    </div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				headOption: {title: '课程报名',backText: '',showBack: true},
				isDisable: false,
				phone: '',
				email: '',
				name: '',
				pays: [
					{id: 'integral',description: '积分'}
				],
				w: null, //请求中
			}
		},
		computed: {
			id () {
				return this.$route.query.id;
			}
		},
		components: {
		},
		methods: {
			
			Commit () {
				let _this= this;
				
				if (_this.name == '') {
					_this.$vux.toast.text('请输入姓名','middle')
					return;
				}
				if (_this.phone == '') {
					_this.$vux.toast.text('请输入手机号','middle')
					return;
				}
				if (_this.email == '') {
					_this.$vux.toast.text('请输入邮箱','middle')
					return;
				}
				
				_this.$router.push({
					path: '/settle-one?type=course&phone='+_this.phone+'&email='+_this.email+'&name='+_this.name+'&id='+_this.id
				})
				
			}
		},
		mounted () {
		}
	}
</script>

<style scoped>
	.vux-x-icon {
	  fill: #666;
	}
	.dialog-title {
		font-size: 0.373rem;
		color: #666;
		line-height: 1rem;
	}
	.dialog-body {
		padding: .6rem 0;
	}
	.dialog-body button {
		color: #00FF00;
		border: 1px solid #00FF00;
		padding: 2px 20px;
		border-radius: 20px;
		background-color: #fff;
		margin: 0 10px;
		font-size: 0.4rem;
	}
	.dialog-body button.integral {
		color: #f3917f;
		border-color: #f3917f ;
	}
	
	.item_box {
		height: 1.3rem;
		background-color: #fff;
	}
	.icon_box {
		width: 1.4666rem;
		text-align: center;
	}
	.icon_box {
		line-height: 1rem;
	}
	.icon_box img {
		width: 0.64rem;
		height: 0.64rem;
	}
	.icon_box_2 img {
		width: 0.5rem;
		height: 0.5rem;
	}
	
	.text_box {
		height: inherit;
		border-bottom: 1px solid #ededed;
	}
	.text_box input {
		line-height: 1rem;
		color: #666;
		width: 100%;
	}
	.btn_box {
		padding: 1.3333rem 0;
		text-align: center;
		padding-top: 3rem;
	}
	.btn_box button {
		color: #fff;
		font-size: 0.3466rem;
		padding: 0 1.3333rem;
		line-height: 1.0666rem;
		border-radius: 0.8rem;
	}
	
	.text_box p {
		line-height: 1rem;
		font-size: 0.3733rem;
		color: #666;
	}
	._radio_box span{
		font-size: 0.3466rem;
		color: #333;
		line-height: 1rem;
	}
	._radio_box span:before {
		content: "";
		display: inline-block;
		width: 5px;
		height: 5px;
		border: 1px solid #666;
		border-radius: 50%;
		margin-right: 10px;
		position: relative;
		bottom: 2px;
	}
	._radio_box span.active:before {
		border-color: #FF0000;
	} 
</style>